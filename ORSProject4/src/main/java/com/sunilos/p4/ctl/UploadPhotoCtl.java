package com.sunilos.p4.ctl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.UserBean;
import com.sunilos.p4.exception.ApplicationException;
import com.sunilos.p4.model.UserModel;
import com.sunilos.p4.util.DataUtility;
import com.sunilos.p4.util.DataValidator;
import com.sunilos.p4.util.PropertyReader;

/**
 * Upload Photo functionality Controller. Receives a photo through a
 * multipart form for the logged in User, stores it under the base path
 * configured by the <code>photoPath</code> key in system.properties using a
 * randomly generated unique file name, and updates the relative path of the
 * stored photo against the User record.
 *
 * @author Rays EdTech
 * @version 1.0
 * @Copyright (c) Rays EdTech
 */

@WebServlet("/ctl/uploadphoto")
@MultipartConfig
public class UploadPhotoCtl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Fallback photo served when the User has no photo uploaded yet, or the
	 * stored photo file can no longer be found on disk.
	 */
	private static final String DEFAULT_PHOTO_PATH = "F:/ORSProject4/image/logo.png";

	private static Logger log = Logger.getLogger(UploadPhotoCtl.class);

	/**
	 * Streams the photo of the User identified by the <code>id</code> request
	 * parameter back in the response, so it can be used as the source of an
	 * HTML <code>&lt;img&gt;</code> tag. Falls back to {@link #DEFAULT_PHOTO_PATH}
	 * when the user has no photo or the stored photo file is missing.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("UploadPhotoCtl Method doGet Started");

		long id = DataUtility.getLong(request.getParameter("id"));

		UserModel model = new UserModel();
		UserBean bean = null;

		try {
			bean = model.findByPK(id);
		} catch (ApplicationException e) {
			log.error(e);
		}

		File photoFile = null;
		String basePath = PropertyReader.getValue("photoPath");

		if (bean != null && DataValidator.isNotNull(bean.getPhoto())) {
			File candidate = new File(basePath, bean.getPhoto());
			if (candidate.exists()) {
				photoFile = candidate;
			}
		}

		if (photoFile == null) {
			photoFile = new File(basePath + "/logo.png");
			if (!photoFile.exists()) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
		}

		String contentType = getServletContext().getMimeType(photoFile.getName());
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		response.setContentType(contentType);
		response.setContentLengthLong(photoFile.length());

		try (InputStream input = new FileInputStream(photoFile);
				OutputStream output = response.getOutputStream()) {

			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = input.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
		}

		log.debug("UploadPhotoCtl Method doGet Ended");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("UploadPhotoCtl Method doPost Started");

		long id = DataUtility.getLong(request.getParameter("id"));

		String view = request.getParameter("view");

		Part part = request.getPart("photo");

		if (part == null || part.getSize() <= 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("text/plain");
			response.getWriter().print("Please select a photo to upload");
			return;
		}

		String uniqueFileName = UUID.randomUUID().toString() + getFileExtension(part);

		String basePath = PropertyReader.getValue("photoPath");
		File baseDir = new File(basePath);

		if (!baseDir.exists()) {
			baseDir.mkdirs();
		}

		File destFile = new File(baseDir, uniqueFileName);

		try (InputStream input = part.getInputStream();
				OutputStream output = new FileOutputStream(destFile)) {

			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = input.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
		}

		UserModel model = new UserModel();

		try {
			model.updatePhoto(id, uniqueFileName);
			UserBean bean = model.findByPK(id);

			HttpSession session = request.getSession(false);
			if (session != null) {
				UserBean sessionUser = (UserBean) session.getAttribute("user");
				if (sessionUser != null && sessionUser.getId() == id) {
					session.setAttribute("user", bean);
				}
			}
		} catch (ApplicationException e) {
			log.error(e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			response.getWriter().print(e.getMessage());
			return;
		}

		// redirect to ctl/UserCtl?id=7
		if ("profile".equals(view)) {
			response.sendRedirect("MyProfileCtl");
		} else {
		response.sendRedirect("UserCtl?id=" + id);
		}
		log.debug("UploadPhotoCtl Method doPost Ended");
	}

	/**
	 * Extracts file extension (including the leading dot) from the uploaded
	 * Part's original file name
	 *
	 * @param part
	 * @return
	 */
	private String getFileExtension(Part part) {

		String fileName = null;

		for (String token : part.getHeader("content-disposition").split(";")) {
			if (token.trim().startsWith("filename")) {
				fileName = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
			}
		}

		if (fileName != null && fileName.lastIndexOf('.') >= 0) {
			return fileName.substring(fileName.lastIndexOf('.'));
		}

		return "";
	}

}
