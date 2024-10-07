package com.rays.pro4.Util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.rays.pro4.Bean.DropdownListBean;
import com.rays.pro4.Model.BaseModel;

/**
 * HTML Utility class to produce HTML content such as Dropdown Lists, Error
 * messages, Success messages, and Submit buttons with access permissions.
 * 
 * This utility simplifies generating common HTML elements needed for web
 * applications.
 * 
 * @author Sunil OS
 *
 */
public class HTMLUtility {

    /**
     * Generates an HTML Dropdown list from a provided HashMap of key-value pairs.
     *
     * @param name        The name attribute for the HTML select tag.
     * @param selectedVal The selected option value to be pre-selected in the
     *                    dropdown.
     * @param map         The HashMap containing the options for the dropdown list
     *                    where key represents the option's value and value
     *                    represents the option's display text.
     * @return A String containing the HTML representation of the dropdown list.
     */
    public static String getList(String name, String selectedVal, HashMap<String, String> map) {

        StringBuffer sb = new StringBuffer(
                "<select style='width: 203px;  height: 23px;' class='form-control' name='" + name + "'>");

        Set<String> keys = map.keySet();
        String val = null;

        boolean select = true;
        if (select) {
            sb.append(
                    "<option style='width: 203px;  height: 30px;' selected value=''>--------------Select---------------------`</option>");
        }

        for (String key : keys) {
            val = map.get(key);
            if (key.trim().equals(selectedVal)) {
                sb.append("<option selected value='" + key + "'>" + val + "</option>");
            } else {
                sb.append("<option value='" + key + "'>" + val + "</option>");
            }
        }
        sb.append("</select>");
        return sb.toString();
    }

    /**
     * Creates an HTML SELECT list from a provided List of DropdownListBean objects.
     *
     * @param name        The name attribute for the HTML select tag.
     * @param selectedVal The selected option value to be pre-selected in the
     *                    dropdown.
     * @param list        The List of DropdownListBean objects containing the
     *                    options for the dropdown.
     * @return A String containing the HTML representation of the dropdown list.
     */
    public static String getList(String name, String selectedVal, List<DropdownListBean> list) {

        Collections.sort(list);
        StringBuffer sb = new StringBuffer(
                "<select style='width: 203px;  height: 23px;' class='form-control' name='" + name + "'>");

        boolean select = true;
        if (select) {
            sb.append(
                    "<option style='width: 203px;  height: 30px;' selected value=''>--------------Select-----------------`</option>");
        }

        for (DropdownListBean obj : list) {
            String key = obj.getkey();
            String val = obj.getValue();

            if (key.trim().equals(selectedVal)) {
                sb.append("<option selected value='" + key + "'>" + val + "</option>");
            } else {
                sb.append("<option value='" + key.trim() + "'>" + val + "</option>");
            }
        }
        sb.append("</select>");

        return sb.toString();
    }

    /**
     * Returns an error message formatted with HTML and CSS styles.
     *
     * @param request The HttpServletRequest object containing the error message.
     * @return A String containing the error message formatted with HTML tags.
     */
    public static String getErrorMessage(HttpServletRequest request) {
        String msg = ServletUtility.getErrorMessage(request);
        if (!DataValidator.isNull(msg)) {
            msg = "<p class='st-error-header'>" + msg + "</p>";
        }
        return msg;
    }

    /**
     * Returns a success message formatted with HTML and CSS styles.
     *
     * @param request The HttpServletRequest object containing the success message.
     * @return A String containing the success message formatted with HTML tags.
     */
    public static String getSuccessMessage(HttpServletRequest request) {
        String msg = ServletUtility.getSuccessMessage(request);
        if (!DataValidator.isNull(msg)) {
            msg = "<p class='st-success-header'>" + msg + "</p>";
        }
        return msg;
    }

    /**
     * Creates a submit button HTML element if the user has access permissions.
     *
     * @param label   The label for the submit button.
     * @param access  Boolean value indicating if the user has permission to access
     *                this button.
     * @param request The HttpServletRequest object.
     * @return A String containing the HTML representation of the submit button.
     */
    public static String getSubmitButton(String label, boolean access, HttpServletRequest request) {

        String button = "";

        if (access) {
            button = "<input type='submit' name='operation' value='" + label + "' >";
        }
        return button;
    }

    /**
     * Generates common hidden fields for a form, including the ID of the current
     * model.
     *
     * @param request The HttpServletRequest object.
     * @return A String containing hidden input fields for the form.
     */
    public static String getCommonFields(HttpServletRequest request) {

        BaseModel model = ServletUtility.getModel(request);

        StringBuffer sb = new StringBuffer();

        sb.append("<input type='hidden' name='id' value=" + model.getId() + ">");
        /*
         * Additional fields for createdBy, modifiedBy, etc. can be added as needed.
         */
        return sb.toString();
    }

}
