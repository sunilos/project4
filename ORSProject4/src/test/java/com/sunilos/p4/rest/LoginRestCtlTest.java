package com.sunilos.p4.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunilos.p4.bean.UserBean;
import com.sunilos.p4.exception.DuplicateRecordException;
import com.sunilos.p4.exception.RecordNotFoundException;
import com.sunilos.p4.model.UserModel;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link LoginRestCtl}.
 * <p>
 * Uses a testable subclass that overrides {@code getModel()} to inject a
 * {@link UserModel} mock, combined with Spring's
 * {@link MockHttpServletRequest} / {@link MockHttpServletResponse} for
 * zero-server servlet testing.
 */
class LoginRestCtlTest {

    // ── fixtures ──────────────────────────────────────────────────────────────

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private UserModel         mockModel;
    private LoginRestCtl      servlet;
    private MockHttpServletRequest  request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        mockModel = mock(UserModel.class);

        // Anonymous subclass — only getModel() is overridden; all logic is real.
        servlet = new LoginRestCtl() {
            @Override
            protected UserModel getModel() {
                return mockModel;
            }
        };

        request  = new MockHttpServletRequest("POST", "/rest/auth/login");
        response = new MockHttpServletResponse();
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    /** Sets the JSON request body and routes to the given operation path. */
    private void post(String operation, String jsonBody) throws Exception {
        request.setPathInfo("/" + operation);
        request.setMethod("POST");
        request.setContentType("application/json");
        request.setContent(jsonBody.getBytes());
        servlet.service(request, response);
    }

    /** Parses the response body as a {@link JsonNode}. */
    private JsonNode body() throws Exception {
        return MAPPER.readTree(response.getContentAsString());
    }

    /** Shortcut: asserts {@code success} field equals {@code expected}. */
    private void assertSuccess(boolean expected) throws Exception {
        assertEquals(expected, body().get("success").asBoolean(),
                "Unexpected 'success' in: " + response.getContentAsString());
    }

    /** Shortcut: asserts the {@code result.message} field contains {@code text}. */
    private void assertMessage(String text) throws Exception {
        String msg = body().path("result").path("message").asText();
        assertTrue(msg.contains(text),
                "Expected message to contain '" + text + "' but was: " + msg);
    }

    // ── doLogin ───────────────────────────────────────────────────────────────

    @Test
    void login_validCredentials_returnsSuccessAndUserBean() throws Exception {
        UserBean user = new UserBean();
        user.setId(1L);
        user.setFirstName("John");
        user.setLogin("john@example.com");

        when(mockModel.authenticate("john@example.com", "secret")).thenReturn(user);

        post("login", """
                {"login":"john@example.com","password":"secret"}
                """);

        assertSuccess(true);
        assertMessage("Login successful");

        JsonNode data = body().path("result").path("data");
        assertEquals(1L, data.path("id").asLong());
        assertEquals("John", data.path("firstName").asText());
    }

    @Test
    void login_validCredentials_storesUserInSession() throws Exception {
        UserBean user = new UserBean();
        user.setId(2L);
        when(mockModel.authenticate("a@b.com", "pass")).thenReturn(user);

        post("login", """
                {"login":"a@b.com","password":"pass"}
                """);

        HttpSession session = request.getSession(false);
        assertNotNull(session, "Session should have been created");
        assertSame(user, session.getAttribute("user"));
    }

    @Test
    void login_invalidCredentials_returnsError() throws Exception {
        when(mockModel.authenticate(anyString(), anyString())).thenReturn(null);

        post("login", """
                {"login":"a@b.com","password":"wrong"}
                """);

        assertSuccess(false);
        assertMessage("Invalid login or password");
    }

    @Test
    void login_missingLoginField_returnsValidationError() throws Exception {
        post("login", """
                {"password":"secret"}
                """);

        assertSuccess(false);
        assertMessage("Validation errors");
        assertTrue(body().path("result").path("inputErrors").has("login"));
        verify(mockModel, never()).authenticate(anyString(), anyString());
    }

    @Test
    void login_invalidEmailFormat_returnsValidationError() throws Exception {
        post("login", """
                {"login":"not-an-email","password":"secret"}
                """);

        assertSuccess(false);
        assertMessage("Validation errors");
        assertTrue(body().path("result").path("inputErrors").has("login"));
        verify(mockModel, never()).authenticate(anyString(), anyString());
    }

    @Test
    void login_missingPassword_returnsValidationError() throws Exception {
        post("login", """
                {"login":"a@b.com"}
                """);

        assertSuccess(false);
        verify(mockModel, never()).authenticate(anyString(), anyString());
    }

    @Test
    void login_modelThrowsException_returnsErrorResponse() throws Exception {
        when(mockModel.authenticate(anyString(), anyString()))
                .thenThrow(new RuntimeException("DB error"));

        post("login", """
                {"login":"a@b.com","password":"secret"}
                """);

        assertSuccess(false);
        assertMessage("DB error");
    }

    // ── doLogout ──────────────────────────────────────────────────────────────

    @Test
    void logout_withActiveSession_invalidatesSessionAndReturnsSuccess() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new UserBean());
        request.setSession(session);

        post("logout", "");

        assertSuccess(true);
        assertMessage("Logout successful");
        assertTrue(session.isInvalid(), "Session should have been invalidated");
    }

    @Test
    void logout_withNoSession_returnsSuccess() throws Exception {
        // No session set — servlet should handle gracefully
        post("logout", "");

        assertSuccess(true);
        assertMessage("Logout successful");
    }

    // ── doSignUp ──────────────────────────────────────────────────────────────

    @Test
    void signup_validData_returnsSuccessWithNewUser() throws Exception {
        when(mockModel.registerUser(any(UserBean.class))).thenReturn(42L);

        post("signup", """
                {
                  "firstName":"Jane",
                  "lastName":"Doe",
                  "login":"jane@example.com",
                  "password":"secret",
                  "confirmPassword":"secret",
                  "gender":"Female"
                }
                """);

        assertSuccess(true);
        assertMessage("User registered successfully");

        JsonNode data = body().path("result").path("data");
        assertEquals(42L, data.path("id").asLong());
        assertEquals("Jane", data.path("firstName").asText());
    }

    @Test
    void signup_duplicateLogin_returnsError() throws Exception {
        when(mockModel.registerUser(any(UserBean.class)))
                .thenThrow(new DuplicateRecordException("Login id already exists"));

        post("signup", """
                {
                  "firstName":"Jane","lastName":"Doe",
                  "login":"jane@example.com",
                  "password":"secret","confirmPassword":"secret","gender":"Female"
                }
                """);

        assertSuccess(false);
        assertMessage("Login id already exists");
    }

    @Test
    void signup_missingFirstName_returnsValidationError() throws Exception {
        post("signup", """
                {"lastName":"Doe","login":"jane@example.com","password":"secret","confirmPassword":"secret"}
                """);

        assertSuccess(false);
        verify(mockModel, never()).registerUser(any());
    }

    @Test
    void signup_missingLastName_returnsValidationError() throws Exception {
        post("signup", """
                {"firstName":"Jane","login":"jane@example.com","password":"secret","confirmPassword":"secret"}
                """);

        assertSuccess(false);
        verify(mockModel, never()).registerUser(any());
    }

    @Test
    void signup_missingLogin_returnsValidationError() throws Exception {
        post("signup", """
                {"firstName":"Jane","lastName":"Doe","password":"secret","confirmPassword":"secret"}
                """);

        assertSuccess(false);
        verify(mockModel, never()).registerUser(any());
    }

    @Test
    void signup_invalidLoginEmail_returnsValidationError() throws Exception {
        post("signup", """
                {"firstName":"Jane","lastName":"Doe","login":"not-email",
                 "password":"secret","confirmPassword":"secret"}
                """);

        assertSuccess(false);
        verify(mockModel, never()).registerUser(any());
    }

    @Test
    void signup_missingPassword_returnsValidationError() throws Exception {
        post("signup", """
                {"firstName":"Jane","lastName":"Doe","login":"jane@example.com","confirmPassword":"secret"}
                """);

        assertSuccess(false);
        verify(mockModel, never()).registerUser(any());
    }

    @Test
    void signup_passwordMismatch_returnsValidationError() throws Exception {
        post("signup", """
                {
                  "firstName":"Jane","lastName":"Doe","login":"jane@example.com",
                  "password":"secret","confirmPassword":"different"
                }
                """);

        assertSuccess(false);
        verify(mockModel, never()).registerUser(any());
    }

    // ── doForgotPassword ──────────────────────────────────────────────────────

    @Test
    void forgotPassword_knownEmail_returnsSuccess() throws Exception {
        when(mockModel.forgetPassword("a@b.com")).thenReturn(true);

        post("forgotpassword", """
                {"login":"a@b.com"}
                """);

        assertSuccess(true);
        assertMessage("Password has been sent to your email id");
        verify(mockModel).forgetPassword("a@b.com");
    }

    @Test
    void forgotPassword_unknownEmail_returnsError() throws Exception {
        when(mockModel.forgetPassword("unknown@b.com"))
                .thenThrow(new RecordNotFoundException("No account found for the provided email"));

        post("forgotpassword", """
                {"login":"unknown@b.com"}
                """);

        assertSuccess(false);
        assertMessage("No account found");
    }

    @Test
    void forgotPassword_missingEmail_returnsValidationError() throws Exception {
        post("forgotpassword", "{}");

        assertSuccess(false);
        assertTrue(body().path("result").path("inputErrors").has("login"));
        verify(mockModel, never()).forgetPassword(anyString());
    }

    @Test
    void forgotPassword_invalidEmailFormat_returnsValidationError() throws Exception {
        post("forgotpassword", """
                {"login":"not-an-email"}
                """);

        assertSuccess(false);
        assertTrue(body().path("result").path("inputErrors").has("login"));
        verify(mockModel, never()).forgetPassword(anyString());
    }

    // ── doPost routing ────────────────────────────────────────────────────────

    @Test
    void post_unknownOperation_returnsError() throws Exception {
        post("invalidop", "{}");

        assertSuccess(false);
        assertMessage("Unknown operation");
    }

    @Test
    void response_alwaysHasJsonContentType() throws Exception {
        when(mockModel.authenticate(anyString(), anyString())).thenReturn(null);
        post("login", """
                {"login":"a@b.com","password":"secret"}
                """);

        assertTrue(response.getContentType().contains("application/json"));
    }

}
