# ORS REST API Documentation

Base URL: `http://localhost:8080/ORSV0`

---

## Response Format

Every endpoint returns JSON in the following envelope:

```json
{
  "success": true,
  "result": {
    "message": "Human-readable status message",
    "data":    { }
  }
}
```

| Field | Type | Description |
|---|---|---|
| `success` | boolean | `true` on success, `false` on any error |
| `result.message` | string | Status or error description |
| `result.data` | object \| array | Returned record(s); absent on error |
| `result.inputErrors` | object | Field-level validation errors (validation failures only) |
| `result.preload` | object | Dropdown reference data (preload endpoints only) |

---

## 1. Authentication — `/rest/auth`

### 1.1 Login

**`POST /rest/auth/login`**

**Request**
```json
{
  "login":    "admin@ors.com",
  "password": "admin123"
}
```

**Response — success**
```json
{
  "success": true,
  "result": {
    "message": "Login successful",
    "data": {
      "id": 1,
      "firstName": "Admin",
      "lastName": "User",
      "login": "admin@ors.com",
      "roleId": 1,
      "gender": "Male",
      "mobileNo": "9876543210"
    }
  }
}
```

**Response — invalid credentials**
```json
{
  "success": false,
  "result": { "message": "Invalid login or password" }
}
```

**Response — validation error**
```json
{
  "success": false,
  "result": {
    "message": "Validation errors",
    "inputErrors": {
      "login":    "Login must be a valid email address",
      "password": "Password is required"
    }
  }
}
```

---

### 1.2 Logout

**`POST /rest/auth/logout`**

**Request** *(empty body)*

**Response**
```json
{
  "success": true,
  "result": { "message": "Logout successful" }
}
```

---

### 1.3 Sign Up

**`POST /rest/auth/signup`**

**Request**
```json
{
  "firstName":       "John",
  "lastName":        "Doe",
  "login":           "john.doe@email.com",
  "password":        "secret123",
  "confirmPassword": "secret123",
  "gender":          "Male"
}
```

**Response — success**
```json
{
  "success": true,
  "result": {
    "message": "User registered successfully, please login",
    "data": {
      "id": 42,
      "firstName": "John",
      "lastName": "Doe",
      "login": "john.doe@email.com",
      "roleId": 2,
      "gender": "Male"
    }
  }
}
```

**Response — duplicate login**
```json
{
  "success": false,
  "result": { "message": "Login id already exists" }
}
```

---

### 1.4 Forgot Password

**`POST /rest/auth/forgotpassword`**

**Request**
```json
{
  "login": "john.doe@email.com"
}
```

**Response — success**
```json
{
  "success": true,
  "result": { "message": "Password has been sent to your email id" }
}
```

**Response — unknown email**
```json
{
  "success": false,
  "result": { "message": "No account found for the provided email" }
}
```

---

## 2. College — `/rest/college`

### 2.1 Get All

**`GET /rest/college`**

**Response**
```json
{
  "success": true,
  "result": {
    "data": [
      {
        "id": 1,
        "name": "MIT College",
        "address": "12 Main Street",
        "state": "Madhya Pradesh",
        "city": "Bhopal",
        "phoneNo": "0755-1234567"
      }
    ]
  }
}
```

### 2.2 Get by ID

**`GET /rest/college?id=1`**

**Response — found**
```json
{
  "success": true,
  "result": {
    "data": {
      "id": 1,
      "name": "MIT College",
      "address": "12 Main Street",
      "state": "Madhya Pradesh",
      "city": "Bhopal",
      "phoneNo": "0755-1234567"
    }
  }
}
```

**Response — not found**
```json
{
  "success": false,
  "result": { "message": "Record not found for id: 99" }
}
```

### 2.3 Add

**`POST /rest/college/add`**

**Request**
```json
{
  "name":    "MIT College",
  "address": "12 Main Street",
  "state":   "Madhya Pradesh",
  "city":    "Bhopal",
  "phoneNo": "0755-1234567"
}
```

**Response**
```json
{
  "success": true,
  "result": {
    "message": "Record is successfully added",
    "data": {
      "id": 1,
      "name": "MIT College",
      "address": "12 Main Street",
      "state": "Madhya Pradesh",
      "city": "Bhopal",
      "phoneNo": "0755-1234567"
    }
  }
}
```

### 2.4 Update

**`PUT /rest/college/update`**

**Request** *(id required)*
```json
{
  "id":      1,
  "name":    "MIT College Updated",
  "address": "15 New Street",
  "state":   "Madhya Pradesh",
  "city":    "Bhopal",
  "phoneNo": "0755-9999999"
}
```

**Response**
```json
{
  "success": true,
  "result": {
    "message": "Record is successfully updated",
    "data": { "id": 1, "name": "MIT College Updated" }
  }
}
```

### 2.5 Delete

**`DELETE /rest/college/delete/1`**

**Response**
```json
{
  "success": true,
  "result": {
    "message": "Record is successfully deleted",
    "data": { "id": 1, "name": "MIT College" }
  }
}
```

### 2.6 Search

**`POST /rest/college/search`**

**Request** *(partial fields as criteria)*
```json
{
  "name": "MIT",
  "city": "Bhopal"
}
```

**Response**
```json
{
  "success": true,
  "result": {
    "message": "Records found",
    "data": [ { "id": 1, "name": "MIT College", "city": "Bhopal" } ]
  }
}
```

---

## 3. Course — `/rest/course`

### 3.1 Get All

**`GET /rest/course`**

**Response**
```json
{
  "success": true,
  "result": {
    "data": [
      { "id": 1, "name": "B.Tech", "description": "Bachelor of Technology", "duration": "4 Years" }
    ]
  }
}
```

### 3.2 Get by ID

**`GET /rest/course?id=1`**

### 3.3 Add

**`POST /rest/course/add`**

**Request**
```json
{
  "name":        "B.Tech",
  "description": "Bachelor of Technology",
  "duration":    "4 Years"
}
```

### 3.4 Update

**`PUT /rest/course/update`**

**Request**
```json
{
  "id":          1,
  "name":        "B.Tech",
  "description": "Bachelor of Technology (Updated)",
  "duration":    "4 Years"
}
```

### 3.5 Delete

**`DELETE /rest/course/delete/1`**

### 3.6 Search

**`POST /rest/course/search`**

**Request**
```json
{ "name": "B.Tech" }
```

---

## 4. Subject — `/rest/subject`

### 4.1 Get Preload

**`GET /rest/subject/preload`**

**Response**
```json
{
  "success": true,
  "result": {
    "preload": {
      "courseList": [
        { "id": 1, "name": "B.Tech" },
        { "id": 2, "name": "M.Tech" }
      ]
    }
  }
}
```

### 4.2 Get All

**`GET /rest/subject`**

### 4.3 Get by ID

**`GET /rest/subject?id=1`**

### 4.4 Add

**`POST /rest/subject/add`**

**Request**
```json
{
  "name":        "Mathematics",
  "description": "Engineering Mathematics",
  "courseId":    1
}
```

**Response**
```json
{
  "success": true,
  "result": {
    "message": "Record is successfully added",
    "data": {
      "id": 1,
      "name": "Mathematics",
      "description": "Engineering Mathematics",
      "courseId": 1
    }
  }
}
```

### 4.5 Update

**`PUT /rest/subject/update`**

**Request**
```json
{
  "id":          1,
  "name":        "Mathematics II",
  "description": "Advanced Engineering Mathematics",
  "courseId":    1
}
```

### 4.6 Delete

**`DELETE /rest/subject/delete/1`**

### 4.7 Search

**`POST /rest/subject/search`**

**Request**
```json
{ "courseId": 1 }
```

---

## 5. Role — `/rest/role`

### 5.1 Get All

**`GET /rest/role`**

**Response**
```json
{
  "success": true,
  "result": {
    "data": [
      { "id": 1, "name": "Admin",   "description": "System Administrator" },
      { "id": 2, "name": "Student", "description": "Student User" }
    ]
  }
}
```

### 5.2 Get by ID

**`GET /rest/role?id=1`**

### 5.3 Add

**`POST /rest/role/add`**

**Request**
```json
{
  "name":        "Faculty",
  "description": "Faculty Member"
}
```

### 5.4 Update

**`PUT /rest/role/update`**

**Request**
```json
{
  "id":          3,
  "name":        "Faculty",
  "description": "Faculty Member (Updated)"
}
```

### 5.5 Delete

**`DELETE /rest/role/delete/3`**

### 5.6 Search

**`POST /rest/role/search`**

**Request**
```json
{ "name": "Admin" }
```

---

## 6. User — `/rest/user`

### 6.1 Get Preload

**`GET /rest/user/preload`**

**Response**
```json
{
  "success": true,
  "result": {
    "preload": {
      "roleList": [
        { "id": 1, "name": "Admin" },
        { "id": 2, "name": "Student" }
      ]
    }
  }
}
```

### 6.2 Get All

**`GET /rest/user`**

### 6.3 Get by ID

**`GET /rest/user?id=1`**

**Response**
```json
{
  "success": true,
  "result": {
    "data": {
      "id":        1,
      "firstName": "Admin",
      "lastName":  "User",
      "login":     "admin@ors.com",
      "mobileNo":  "9876543210",
      "gender":    "Male",
      "roleId":    1,
      "lock":      "Active"
    }
  }
}
```

### 6.4 Add

**`POST /rest/user/add`**

**Request**
```json
{
  "firstName": "Jane",
  "lastName":  "Smith",
  "login":     "jane.smith@ors.com",
  "password":  "pass1234",
  "mobileNo":  "9876543211",
  "gender":    "Female",
  "roleId":    1
}
```

### 6.5 Update

**`PUT /rest/user/update`**

**Request**
```json
{
  "id":        1,
  "firstName": "Jane",
  "lastName":  "Smith Updated",
  "login":     "jane.smith@ors.com",
  "password":  "newpass",
  "mobileNo":  "9876500000",
  "gender":    "Female",
  "roleId":    1
}
```

### 6.6 Delete

**`DELETE /rest/user/delete/1`**

### 6.7 Search

**`POST /rest/user/search`**

**Request**
```json
{ "firstName": "Jane", "roleId": 1 }
```

---

## 7. Student — `/rest/student`

### 7.1 Get Preload

**`GET /rest/student/preload`**

**Response**
```json
{
  "success": true,
  "result": {
    "preload": {
      "collegeList": [
        { "id": 1, "name": "MIT College" },
        { "id": 2, "name": "LNCT College" }
      ]
    }
  }
}
```

### 7.2 Get All

**`GET /rest/student`**

### 7.3 Get by ID

**`GET /rest/student?id=1`**

**Response**
```json
{
  "success": true,
  "result": {
    "data": {
      "id":          1,
      "firstName":   "Raj",
      "lastName":    "Kumar",
      "email":       "raj.kumar@email.com",
      "mobileNo":    "9898989898",
      "collegeId":   1,
      "collegeName": "MIT College"
    }
  }
}
```

### 7.4 Add

**`POST /rest/student/add`**

**Request**
```json
{
  "firstName":   "Raj",
  "lastName":    "Kumar",
  "email":       "raj.kumar@email.com",
  "mobileNo":    "9898989898",
  "collegeId":   1,
  "collegeName": "MIT College"
}
```

### 7.5 Update

**`PUT /rest/student/update`**

**Request**
```json
{
  "id":          1,
  "firstName":   "Raj",
  "lastName":    "Kumar Updated",
  "email":       "raj.kumar@email.com",
  "mobileNo":    "9898989898",
  "collegeId":   1,
  "collegeName": "MIT College"
}
```

### 7.6 Delete

**`DELETE /rest/student/delete/1`**

### 7.7 Search

**`POST /rest/student/search`**

**Request**
```json
{ "collegeId": 1 }
```

---

## 8. Faculty — `/rest/faculty`

### 8.1 Get Preload

**`GET /rest/faculty/preload`**

**Response**
```json
{
  "success": true,
  "result": {
    "preload": {
      "collegeList": [
        { "id": 1, "name": "MIT College" }
      ]
    }
  }
}
```

### 8.2 Get All

**`GET /rest/faculty`**

### 8.3 Get by ID

**`GET /rest/faculty?id=1`**

**Response**
```json
{
  "success": true,
  "result": {
    "data": {
      "id":        1,
      "firstName": "Dr. Priya",
      "lastName":  "Sharma",
      "email":     "priya.sharma@mit.com",
      "mobileNo":  "9812345678",
      "address":   "45 Faculty Quarters",
      "gender":    "Female",
      "collegeId": 1
    }
  }
}
```

### 8.4 Add

**`POST /rest/faculty/add`**

**Request**
```json
{
  "firstName": "Dr. Priya",
  "lastName":  "Sharma",
  "email":     "priya.sharma@mit.com",
  "mobileNo":  "9812345678",
  "address":   "45 Faculty Quarters",
  "gender":    "Female",
  "collegeId": 1
}
```

### 8.5 Update

**`PUT /rest/faculty/update`**

**Request**
```json
{
  "id":        1,
  "firstName": "Dr. Priya",
  "lastName":  "Sharma",
  "email":     "priya.sharma@mit.com",
  "mobileNo":  "9812345678",
  "address":   "46 Faculty Quarters",
  "gender":    "Female",
  "collegeId": 1
}
```

### 8.6 Delete

**`DELETE /rest/faculty/delete/1`**

### 8.7 Search

**`POST /rest/faculty/search`**

**Request**
```json
{ "collegeId": 1, "gender": "Female" }
```

---

## 9. Marksheet — `/rest/marksheet`

### 9.1 Get Preload

**`GET /rest/marksheet/preload`**

**Response**
```json
{
  "success": true,
  "result": {
    "preload": {
      "studentList": [
        { "id": 1, "firstName": "Raj", "lastName": "Kumar" }
      ]
    }
  }
}
```

### 9.2 Get All

**`GET /rest/marksheet`**

### 9.3 Get by ID

**`GET /rest/marksheet?id=1`**

**Response**
```json
{
  "success": true,
  "result": {
    "data": {
      "id":        1,
      "rollNo":    "BT2024001",
      "name":      "Raj Kumar",
      "studentId": 1,
      "physics":   85,
      "chemistry": 78,
      "maths":     92
    }
  }
}
```

### 9.4 Add

**`POST /rest/marksheet/add`**

**Request**
```json
{
  "rollNo":    "BT2024001",
  "name":      "Raj Kumar",
  "studentId": 1,
  "physics":   85,
  "chemistry": 78,
  "maths":     92
}
```

**Response**
```json
{
  "success": true,
  "result": {
    "message": "Record is successfully added",
    "data": {
      "id":        1,
      "rollNo":    "BT2024001",
      "name":      "Raj Kumar",
      "studentId": 1,
      "physics":   85,
      "chemistry": 78,
      "maths":     92
    }
  }
}
```

### 9.5 Update

**`PUT /rest/marksheet/update`**

**Request**
```json
{
  "id":        1,
  "rollNo":    "BT2024001",
  "name":      "Raj Kumar",
  "studentId": 1,
  "physics":   90,
  "chemistry": 82,
  "maths":     95
}
```

### 9.6 Delete

**`DELETE /rest/marksheet/delete/1`**

### 9.7 Search

**`POST /rest/marksheet/search`**

**Request**
```json
{ "studentId": 1 }
```

---

## Common Error Responses

### Record not found

```json
{
  "success": false,
  "result": { "message": "Record not found for id: 99" }
}
```

### Missing or invalid ID on update

```json
{
  "success": false,
  "result": { "message": "Invalid or missing id — cannot update record" }
}
```

### Missing or invalid ID on delete

```json
{
  "success": false,
  "result": { "message": "Invalid or missing id — cannot delete record" }
}
```

### Server error

```json
{
  "success": false,
  "result": { "message": "Detailed exception message here" }
}
```

---

## HTTP Methods Reference

| Method | Purpose |
|---|---|
| `GET` | Retrieve one or all records |
| `POST /add` | Insert a new record |
| `POST /search` | Search with criteria |
| `PUT /update` | Update an existing record (id required in body) |
| `DELETE /delete/{id}` | Delete a record by id |
| `GET /preload` | Fetch reference/dropdown data |
| `HEAD` | Same as GET, headers only (no body) |
| `OPTIONS` | Returns `Allow` header listing supported methods |
| `TRACE` | Echoes request headers for diagnostics |
