# JWT Authentication — ORS REST API

## Overview

The ORS REST API uses **JSON Web Tokens (JWT)** for stateless authentication.
After a successful login the server issues a signed token. The client stores this
token and sends it in the `Authorization` header of every subsequent request. The
server validates the token on each request without consulting a database or a
session store.

```
Client                          Server
  │                               │
  │── POST /rest/auth/login ──────►│  validates credentials
  │◄─ { token: "eyJ..." } ────────│  issues JWT
  │                               │
  │── GET /rest/college ──────────►│  JwtFilter validates token
  │   Authorization: Bearer eyJ.. │
  │◄─ { success: true, data:[..] }│  controller responds
```

---

## Dependencies

Added to `pom.xml`:

```xml
<!-- JWT — token generation and validation -->
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-api</artifactId>
  <version>0.12.6</version>
</dependency>
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-impl</artifactId>
  <version>0.12.6</version>
  <scope>runtime</scope>
</dependency>
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-jackson</artifactId>
  <version>0.12.6</version>
  <scope>runtime</scope>
</dependency>
```

---

## Files Created / Modified

| File | Type | Purpose |
|---|---|---|
| `src/.../util/JwtUtil.java` | New | Token generation and validation |
| `src/.../filter/JwtFilter.java` | New | Guards all `/rest/*` endpoints |
| `src/.../rest/LoginRestCtl.java` | Modified | Returns token on login; stateless logout |

---

## Token Structure

A JWT consists of three Base64-encoded parts separated by dots:

```
eyJhbGciOiJIUzI1NiJ9   ← Header  (algorithm: HS256)
.
eyJ1c2VySWQiOjEsInJvb  ← Payload (claims)
.
SflKxwRJSMeKKF2QT4fwp  ← Signature (HMAC-SHA256)
```

### Claims stored in every token

| Claim | Type | Description |
|---|---|---|
| `sub` | String | Login (email) of the user |
| `userId` | long | Primary key from the `USER` table |
| `roleId` | long | Role assigned to the user (`1`=Admin, `2`=Student) |
| `iat` | long | Issued-at timestamp (Unix seconds) |
| `exp` | long | Expiry timestamp (issued-at + 24 hours) |

Decoded payload example:

```json
{
  "sub":    "admin@ors.com",
  "userId": 1,
  "roleId": 1,
  "iat":    1717056000,
  "exp":    1717142400
}
```

---

## JwtUtil.java

Location: `com.sunilos.p4.util.JwtUtil`

```java
// Generate a token after successful authentication
String token = JwtUtil.generateToken(bean.getId(), bean.getLogin(), bean.getRoleId());

// Validate a token received from the Authorization header
Claims claims = JwtUtil.validateToken(rawToken);

// Read individual claims (after validation)
long   userId = JwtUtil.getUserId(claims);   // e.g. 1
long   roleId = JwtUtil.getRoleId(claims);   // e.g. 1
String login  = JwtUtil.getLogin(claims);    // e.g. "admin@ors.com"
```

### Key configuration

```java
// Secret key — must be at least 32 bytes. Change before production!
private static final SecretKey KEY =
    Keys.hmacShaKeyFor("ORS_JWT_SECRET_KEY_MUST_BE_32_BYTES!".getBytes());

// Token validity
private static final long EXPIRY_MS = 24L * 60 * 60 * 1000; // 24 hours
```

> **Production note**: move the secret to an environment variable or a
> properties file — never commit it to source control.

---

## JwtFilter.java

Location: `com.sunilos.p4.filter.JwtFilter`
Mapped to: `@WebFilter(urlPatterns = "/rest/*")`

### Public (token-free) endpoints

The following paths are whitelisted and pass through without any token check:

| Endpoint | Reason |
|---|---|
| `POST /rest/auth/login` | Issues the token |
| `POST /rest/auth/signup` | New user registration |
| `POST /rest/auth/forgotpassword` | Password reset by email |

### Protected endpoints

Every other `/rest/*` path requires a valid `Authorization: Bearer <token>` header.

### Filter logic (pseudocode)

```
request arrives at /rest/*
  │
  ├─ path starts with PUBLIC_PATH?  ──► pass through (no token needed)
  │
  ├─ Authorization header present?
  │    No  ──► 401 "Authorization header missing or malformed"
  │
  ├─ token valid and not expired?
  │    No  ──► 401 "Invalid or expired token"
  │
  └─ store Claims in request.getAttribute("claims")
       ──► continue to controller
```

---

## Login API

### Request

```
POST /rest/auth/login
Content-Type: application/json
```

```json
{
  "login":    "admin@ors.com",
  "password": "admin123"
}
```

### Response — success

```json
{
  "success": true,
  "result": {
    "message": "Login successful",
    "token":   "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBvcnMuY29tIiwidXNlcklkIjoxLCJyb2xlSWQiOjF9.SflK",
    "data": {
      "id":        1,
      "firstName": "Admin",
      "lastName":  "User",
      "login":     "admin@ors.com",
      "roleId":    1,
      "gender":    "Male",
      "mobileNo":  "9876543210"
    }
  }
}
```

### Response — invalid credentials

```json
{
  "success": false,
  "result": { "message": "Invalid login or password" }
}
```

---

## Logout API

### Request

```
POST /rest/auth/logout
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Response

```json
{
  "success": true,
  "result": { "message": "Logout successful. Please discard your token." }
}
```

> **Note**: JWT is stateless — no server-side session is invalidated.
> The client must delete the stored token to complete logout.

---

## Using the Token in Requests

After login, include the token in every API call:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Examples

```
GET    /rest/college            Authorization: Bearer eyJ...
GET    /rest/college?id=1       Authorization: Bearer eyJ...
POST   /rest/student/add        Authorization: Bearer eyJ...
PUT    /rest/marksheet/update   Authorization: Bearer eyJ...
DELETE /rest/role/delete/3      Authorization: Bearer eyJ...
```

---

## Error Responses

### 401 — Missing or malformed header

Triggered when `Authorization` header is absent or does not start with `Bearer `.

```json
{
  "success": false,
  "result": {
    "message": "Authorization header missing or malformed. Use: Bearer <token>"
  }
}
```

### 401 — Invalid or expired token

Triggered when the token signature is wrong, the secret does not match, or the
token has passed its expiry time.

```json
{
  "success": false,
  "result": { "message": "Invalid or expired token" }
}
```

---

## Reading Claims Inside a Controller

The filter stores the decoded claims as a request attribute. Any controller can
read the authenticated user's identity without re-parsing the token:

```java
import com.sunilos.p4.util.JwtUtil;
import io.jsonwebtoken.Claims;

// Inside any doGet / doPost / doPut / doDelete method:
Claims claims = (Claims) request.getAttribute("claims");

long   userId = JwtUtil.getUserId(claims);   // authenticated user's id
long   roleId = JwtUtil.getRoleId(claims);   // user's role
String login  = JwtUtil.getLogin(claims);    // user's email
```

### Role-based access example

```java
Claims claims = (Claims) request.getAttribute("claims");
long roleId = JwtUtil.getRoleId(claims);

if (roleId != RoleBean.ADMIN) {
    ORSResponse res = new ORSResponse(false, "Access denied: Admin role required");
    sendResponse(res, request, response);
    return;
}
// proceed with admin-only logic
```

---

## Token Lifecycle

```
1. User calls POST /rest/auth/login with credentials
       │
       ▼
2. Server authenticates → generates JWT (valid 24 hours)
       │
       ▼
3. Client stores token (localStorage, sessionStorage, or mobile keychain)
       │
       ▼
4. Client sends Authorization: Bearer <token> on every request
       │
       ▼
5. JwtFilter validates token on each request (no DB call needed)
       │
       ▼
6. Token expires after 24 hours → client must login again
       │
       ▼
7. Client calls POST /rest/auth/logout → discards stored token
```

---

## Security Checklist

| Item | Status |
|---|---|
| Token signed with HMAC-SHA256 | ✅ |
| Token expiry enforced (24 h) | ✅ |
| Public endpoints whitelisted | ✅ |
| Session-free stateless design | ✅ |
| Secret key must be env variable in production | ⚠️ Change before deploy |
| HTTPS required in production | ⚠️ Configure on server |
| Token blacklisting on logout | ℹ️ Not implemented (stateless by design) |
| Refresh token support | ℹ️ Not implemented |
