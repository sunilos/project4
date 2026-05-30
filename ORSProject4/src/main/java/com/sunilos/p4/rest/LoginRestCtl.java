package com.sunilos.p4.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunilos.p4.bean.ORSResponse;
import com.sunilos.p4.bean.RoleBean;
import com.sunilos.p4.bean.UserBean;
import com.sunilos.p4.exception.DuplicateRecordException;
import com.sunilos.p4.exception.RecordNotFoundException;
import com.sunilos.p4.model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import com.sunilos.p4.util.DataValidator;

/**
 * REST controller for authentication operations.
 * <p>
 * Mapped to {@code /rest/auth/*}. All operations use HTTP POST:
 * <ul>
 * <li>{@code POST /rest/auth/login} — authenticate and open session</li>
 * <li>{@code POST /rest/auth/logout} — invalidate current session</li>
 * <li>{@code POST /rest/auth/signup} — register a new user (role: Student)</li>
 * <li>{@code POST /rest/auth/forgotpassword} — email a reset password to the
 * user</li>
 * </ul>
 *
 * @author Sunil Sahu
 * @version 1.0
 */
@WebServlet(urlPatterns = { "/rest/auth/*" })
public class LoginRestCtl extends HttpServlet {

    /** Shared Jackson mapper — thread-safe and reused across all requests. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Returns a new {@link UserModel} instance; override in tests to inject a mock.
     *
     * @return new {@link UserModel}
     */
    protected UserModel getModel() {
        return new UserModel();
    }

    /**
     * Pre-processes every request by extracting the first path segment after
     * {@code /rest/auth/} and storing it as the {@code operation} request
     * attribute before delegating to {@link #doPost}.
     * <p>
     * Example: {@code POST /rest/auth/login} → {@code operation = "login"}
     *
     * @param request  the incoming HTTP request
     * @param response the HTTP response
     * @throws ServletException if an unexpected servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            request.setAttribute("operation", pathInfo.substring(1).split("/")[0]);
        }
        super.service(request, response);
    }

    /**
     * Routes POST requests to the appropriate auth handler based on the
     * {@code operation} attribute set by {@link #service}.
     * <p>
     * Supported operations: {@code login}, {@code logout}, {@code signup},
     * {@code forgotpassword}. Returns an error response for any unknown operation.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if an unexpected servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operation = (String) request.getAttribute("operation");

        if ("login".equalsIgnoreCase(operation)) {
            doLogin(request, response);
        } else if ("logout".equalsIgnoreCase(operation)) {
            doLogout(request, response);
        } else if ("signup".equalsIgnoreCase(operation)) {
            doSignUp(request, response);
        } else if ("forgotpassword".equalsIgnoreCase(operation)) {
            doForgotPassword(request, response);
        } else {
            sendResponse(new ORSResponse(false, "Unknown operation: " + operation), response);
        }
    }

    /**
     * Authenticates a user with login (email) and password.
     * <p>
     * Request body:
     * 
     * <pre>
     * { "login": "user@example.com", "password": "secret" }
     * </pre>
     * <p>
     * On success, stores the authenticated {@link UserBean} in the HTTP session
     * under the key {@code "user"} and returns the bean in the response payload.
     * Returns an error response if credentials are invalid or missing.
     *
     * @param request  the HTTP request carrying login credentials as JSON
     * @param response the HTTP response; returns the authenticated user bean as
     *                 JSON
     * @throws IOException if reading the request body or writing the response fails
     */
    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ORSResponse res;
        try {
            JsonNode json = OBJECT_MAPPER.readTree(request.getInputStream());
            String login = getJsonValue(json, "login");
            String password = getJsonValue(json, "password");

            HashMap<String, String> errors = new HashMap<>();
            if (login == null || login.isEmpty()) {
                errors.put("login", "Login (email) is required");
            } else if (!DataValidator.isEmail(login)) {
                errors.put("login", "Login must be a valid email address");
            }

            if (password == null || password.isEmpty()) {
                errors.put("password", "Password is required");
            }

            if (!errors.isEmpty()) {
                res = new ORSResponse(false, "Validation errors");
                res.addResult("inputErrors", errors);
                sendResponse(res, response);
                return;
            }

            UserBean bean = getModel().authenticate(login, password);

            if (bean == null) {
                res = new ORSResponse(false, "Invalid login or password");
            } else {
                HttpSession session = request.getSession(true);
                session.setAttribute("user", bean);
                res = new ORSResponse(true, "Login successful");
                res.addData(bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
            res = new ORSResponse(false, e.getMessage());
        }

        sendResponse(res, response);
    }

    /**
     * Logs out the current user by invalidating the HTTP session.
     * <p>
     * Request body: (none required)
     * <p>
     * Safe to call even when no session exists — returns a success response in
     * both cases.
     *
     * @param request  the HTTP request
     * @param response the HTTP response; returns a success JSON message
     * @throws IOException if writing the response fails
     */
    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        sendResponse(new ORSResponse(true, "Logout successful"), response);
    }

    /**
     * Registers a new user account. The role is automatically set to
     * {@link RoleBean#STUDENT}.
     * <p>
     * Request body:
     * 
     * <pre>
     * {
     *   "firstName":       "John",
     *   "lastName":        "Doe",
     *   "login":           "john@example.com",
     *   "password":        "secret",
     *   "confirmPassword": "secret",
     *   "gender":          "Male"
     * }
     * </pre>
     * <p>
     * Returns an error response if any required field is missing, passwords do
     * not match, or the login id is already taken
     * ({@link DuplicateRecordException}).
     *
     * @param request  the HTTP request carrying registration details as JSON
     * @param response the HTTP response; returns the newly created user bean as
     *                 JSON
     * @throws IOException if reading the request body or writing the response fails
     */
    private void doSignUp(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ORSResponse res;
        try {
            JsonNode json = OBJECT_MAPPER.readTree(request.getInputStream());

            String firstName = getJsonValue(json, "firstName");
            String lastName = getJsonValue(json, "lastName");
            String login = getJsonValue(json, "login");
            String password = getJsonValue(json, "password");
            String confirmPassword = getJsonValue(json, "confirmPassword");
            String gender = getJsonValue(json, "gender");

            HashMap<String, String> errors = new HashMap<>();

            if (firstName == null || firstName.isEmpty()) {
                errors.put("firstName", "First name is required");
            }
            if (lastName == null || lastName.isEmpty()) {
                errors.put("lastName", "Last name is required");
            }
            if (login == null || login.isEmpty()) {
                errors.put("login", "Login (email) is required");
            } else if (!DataValidator.isEmail(login)) {
                errors.put("login", "Login must be a valid email address");
            }

            if (password == null || password.isEmpty()) {
                errors.put("password", "Password is required");
            }
            if (password != null && confirmPassword != null && !password.equals(confirmPassword)) {
                errors.put("confirmPassword", "Password and confirm password do not match");
            }

            if (!errors.isEmpty()) {
                res = new ORSResponse(false, "Validation errors");
                res.addResult("inputErrors", errors);
                sendResponse(res, response);
                return;
            }

            UserBean bean = new UserBean();
            bean.setRoleId(RoleBean.STUDENT);
            bean.setFirstName(firstName);
            bean.setLastName(lastName);
            bean.setLogin(login);
            bean.setPassword(password);
            bean.setConfirmPassword(confirmPassword);
            bean.setGender(gender);

            long pk = getModel().registerUser(bean);
            bean.setId(pk);

            res = new ORSResponse(true, "User registered successfully, please login");
            res.addData(bean);

        } catch (DuplicateRecordException e) {
            res = new ORSResponse(false, "Login id already exists");
        } catch (Exception e) {
            e.printStackTrace();
            res = new ORSResponse(false, e.getMessage());
        }

        sendResponse(res, response);
    }

    /**
     * Triggers a password reset by emailing a new password to the user's
     * registered email address.
     * <p>
     * Request body:
     * 
     * <pre>
     * { "login": "user@example.com" }
     * </pre>
     * <p>
     * Returns an error response if the login field is missing or no account is
     * found for the provided email ({@link RecordNotFoundException}).
     *
     * @param request  the HTTP request carrying the login email as JSON
     * @param response the HTTP response; returns a success or error message as JSON
     * @throws IOException if reading the request body or writing the response fails
     */
    private void doForgotPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ORSResponse res;
        try {
            JsonNode json = OBJECT_MAPPER.readTree(request.getInputStream());
            String login = getJsonValue(json, "login");
            HashMap<String, String> errors = new HashMap<>();
            if (login == null || login.isEmpty()) {
                errors.put("login", "Login (email) is required");
            } else if (!DataValidator.isEmail(login)) {
                errors.put("login", "Login must be a valid email address");
            }
            if (!errors.isEmpty()) {
                res = new ORSResponse(false, "Validation errors");
                res.addResult("inputErrors", errors);
                sendResponse(res, response);
                return;
            }
            getModel().forgetPassword(login);
            res = new ORSResponse(true, "Password has been sent to your email id");

        } catch (RecordNotFoundException e) {
            res = new ORSResponse(false, "No account found for the provided email");
        } catch (Exception e) {
            e.printStackTrace();
            res = new ORSResponse(false, e.getMessage());
        }

        sendResponse(res, response);
    }

    /**
     * Serialises an {@link ORSResponse} to JSON and writes it to the HTTP response
     * with content-type {@code application/json}.
     *
     * @param res      the response object to serialise
     * @param response the HTTP response to write to
     */
    private void sendResponse(ORSResponse res, HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(OBJECT_MAPPER.writeValueAsString(res));
            out.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Safely reads a string value from a {@link JsonNode} by key.
     *
     * @param jsonNode the parsed JSON object
     * @param key      the field name to look up
     * @return the string value, or {@code null} if the key is absent or null
     */
    private String getJsonValue(JsonNode jsonNode, String key) {
        JsonNode val = jsonNode.get(key);
        return (val != null && !val.isNull()) ? val.asText() : null;
    }

}
