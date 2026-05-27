# ORS — Online Result System

## Project Overview

**ORS (Online Result System)** is a full-stack Enterprise Academic Management Web Application built using **Core Java** and **Jakarta EE** technologies.  
The application is designed to manage the complete academic lifecycle of an educational institution — including student enrollment, academic records, faculty management, marksheet generation, merit ranking, and role-based administration.

The project follows a clean, modular, and scalable architecture using industry-standard enterprise design principles.

---

# Technologies Used

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Platform | Jakarta EE 6 (Servlet 6.0, JSP 3.1) |
| Server | Apache Tomcat 11 |
| Database | MySQL 8 |
| Database Access | Pure JDBC with C3P0 Connection Pooling |
| Build Tool | Apache Maven (WAR Packaging) |
| Logging | Apache Log4j 2 |
| JSON / REST | Jackson 2.15 |
| Testing | JUnit 5 + Mockito |
| UI Framework | Bootstrap 5, Bootstrap Icons |
| Custom Tags | Custom JSP Tag Library (`ors-tag-lib`) |
| Internationalization | PropertyReader + ResourceBundle (EN / HI) |

---

# Key Features

## Academic Management Modules

- College, Course, Subject, and Faculty master management
- Student registration linked with colleges and courses
- Marksheet entry for:
  - Physics
  - Chemistry
  - Mathematics
- Auto-generated Merit List with:
  - Rank calculation
  - Gold / Silver / Bronze badges
- Individual student marksheet retrieval

---

## User & Access Management

- Role-based authentication:
  - Admin
  - Student
- User registration and login
- Profile management
- Forgot Password & Change Password workflows

---

## REST API Layer

- JSON-based REST endpoints for:
  - Student
  - User
  - Role
- REST APIs coexist alongside the traditional web interface
- Supports third-party integration capability

---

## Internationalization (i18n)

- All labels, validations, and buttons externalized into `.properties` files
- Bilingual support:
  - English
  - Hindi
- Custom `<ors:message>` JSP tag for locale-based rendering

---

## Robustness & Quality Features

- Centralized exception handling:
  - `ApplicationException`
  - `DuplicateRecordException`
- Custom 404 and application error pages
- Full audit tracking on all entities:
  - `createdBy`
  - `modifiedBy`
  - timestamps
- Input validation before persistence operations
- Unit and integration testing support using:
  - JUnit 5
  - Mockito

---

# Architecture & Design

```text
Browser → Servlet Controller → Model (JDBC DAO) → MySQL
                  ↓
             JSP View (Bootstrap UI)
```

---

# Design Patterns Used

| Pattern | Implementation |
|---|---|
| MVC | Servlets as Controllers, JDBC DAOs as Models, JSP as Views |
| Generic Template | `BaseCtl<B,M>` and `BaseModel<T>` remove repetitive CRUD logic |
| DAO Pattern | Dedicated DAO/Model class for each entity |
| DTO / Bean | Typed JavaBeans transfer data across layers |
| Connection Pooling | C3P0 manages optimized JDBC connection pools |
| REST + MVC Hybrid | Traditional web UI and REST APIs coexist |

---

# Modules at a Glance

| Module | Operations |
|---|---|
| College | Add, Edit, Delete, Search, Pagination |
| Course | Add, Edit, Delete, Search, Pagination |
| Subject | Add, Edit, Delete, Search, Pagination |
| Faculty | Add, Edit, Delete, Search, Pagination |
| Student | Add, Edit, Delete, Search, Pagination |
| Marksheet | Add, Edit, Delete, Search, Merit List, Get by Student |
| Role | Add, Edit, Delete |
| User | Register, Login, Profile, Change/Forgot Password |
| REST API | Student, User, Role JSON APIs |

---

# What Makes This Project Stand Out

## 1. Pure Core Java & Jakarta EE Discipline

No Spring Boot.  
No Hibernate.  
Every layer is handcrafted using Core Java and Jakarta EE APIs, demonstrating strong foundational engineering skills.

---

## 2. Generic Architecture

A single generic `BaseCtl` and `BaseModel` power all modules using Java Generics, making the codebase:

- DRY (Don't Repeat Yourself)
- Reusable
- Highly maintainable
- Easy to extend

---

## 3. Enterprise-Level Practices

The project incorporates production-grade engineering standards:

- JDBC connection pooling
- Structured logging
- Audit tracking
- Centralized exception handling
- Modular architecture

---

## 4. REST + Traditional Web Hybrid

The system supports:

- User-facing Bootstrap-based web UI
- Machine-readable JSON REST APIs

This mirrors real-world enterprise application architecture.

---

## 5. Internationalization First

Complete bilingual support (English/Hindi) is built into the system architecture using:

- Resource Bundles
- Properties files
- Custom JSP tag library

---

## 6. Testing Infrastructure

Integrated testing support using:

- JUnit 5
- Mockito

Testing is built into the project from the beginning — not added later.

---

# Built With

**Built by:** Sunil Sahu  
**Technology Stack:** Java 17 · Jakarta EE · MySQL · Tomcat 11
