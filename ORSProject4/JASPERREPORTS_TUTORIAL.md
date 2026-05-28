# JasperReports Integration Tutorial
## Online Result System (ORSV0) — Student List PDF Report

---

## What is JasperReports?

JasperReports is the most widely used open-source Java reporting library. It lets you:
- Define a report layout visually in an XML file (`.jrxml`)
- Feed it data (from a database, Java beans, JSON, etc.)
- Export the result to **PDF**, Excel, HTML, CSV, and more

The flow is always the same three steps:

```
JRXML template  →  Fill with data  →  Export to PDF
(design)           (JasperPrint)      (bytes to browser)
```

---

## Project Stack (ORSV0)

| Layer | Technology |
|---|---|
| Server | Apache Tomcat 11 (Jakarta EE 10) |
| Language | Java 17 |
| Build | Maven |
| Persistence | Raw JDBC + C3P0 connection pool |
| View | JSP |
| Entity used | `StudentBean` |

---

## Overview of What We Will Build

A **Print PDF** button on the Student List page that opens a PDF report in the browser containing all students with their college, name, DOB, mobile, and email.

```
Student List page
  └─ [Print PDF] button
       └─ GET /ctl/StudentReportCtl
            ├─ Fetch all students (StudentModel)
            ├─ Compile StudentListReport.jrxml
            ├─ Fill report with JRBeanCollectionDataSource
            └─ Stream PDF to browser
```

---

## Step 1 — Add the Maven Dependency

Open `pom.xml` and add JasperReports inside `<dependencies>`:

```xml
<!-- JasperReports - PDF report generation -->
<dependency>
  <groupId>net.sf.jasperreports</groupId>
  <artifactId>jasperreports</artifactId>
  <version>6.20.6</version>
</dependency>
```

> **Why no exclusions?**
> JasperReports internally uses `commons-logging` to bootstrap itself
> (`JRLoader`, `DefaultJasperReportsContext`). Do **not** exclude it even
> if your app uses Log4j — both can coexist on the classpath.

After editing, reload Maven in your IDE (right-click `pom.xml` → Maven → Reload).

---

## Step 2 — Create the JRXML Report Template

Create the directory and file:

```
src/
└─ main/
   └─ resources/
      └─ reports/
         └─ StudentListReport.jrxml   ← create this
```

> **Why `src/main/resources`?**
> Maven copies everything here into `WEB-INF/classes/` inside the WAR.
> The file ends up on the Java classpath so the servlet can load it with
> `getClass().getResourceAsStream("/reports/StudentListReport.jrxml")`.
> It is NOT publicly accessible via a URL — only server-side code can read it.

### Understanding JRXML structure

A JRXML file is an XML description of the report. It has these sections in order:

```
<jasperReport>
  ├─ <parameter>   — inputs passed in at fill time (e.g. title string)
  ├─ <field>       — columns from the data source (matches bean getters)
  ├─ <variable>    — computed values (row counter, sum, etc.)
  │
  ├─ <title>       — printed once at the very top
  ├─ <pageHeader>  — printed at top of every page
  ├─ <columnHeader>— printed once per page above the rows (table headers)
  ├─ <detail>      — repeated once per row in the data source
  ├─ <pageFooter>  — printed at bottom of every page
  └─ <summary>     — printed once at the very end
```

> **Important:** The bands MUST appear in the order shown above.
> The JasperReports XSD enforces this. A common mistake is placing
> `<summary>` before `<pageFooter>` — this causes a SAXParseException.

### Field names must match bean getter names

JasperReports calls getters via reflection. If the bean has `getFirstName()`,
the JRXML field name must be `firstName` (remove "get", lowercase first letter).

| StudentBean getter | JRXML `<field name="...">` |
|---|---|
| `getId()` | `id` |
| `getFirstName()` | `firstName` |
| `getLastName()` | `lastName` |
| `getDob()` | `dob` |
| `getMobileNo()` | `mobileNo` |
| `getEmail()` | `email` |
| `getCollegeName()` | `collegeName` |

### Full JRXML file

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports
              http://jasperreports.sourceforge.net/xsd/jasperreport.xsd"
              name="StudentListReport"
              pageWidth="842" pageHeight="595"
              orientation="Landscape"
              columnWidth="782"
              leftMargin="30" rightMargin="30"
              topMargin="20" bottomMargin="20">

    <!-- Input parameter passed from Java code -->
    <parameter name="reportTitle" class="java.lang.String">
        <defaultValueExpression><![CDATA["Student List"]]></defaultValueExpression>
    </parameter>

    <!-- One <field> per StudentBean property used in the report -->
    <field name="id"          class="java.lang.Long"/>
    <field name="firstName"   class="java.lang.String"/>
    <field name="lastName"    class="java.lang.String"/>
    <field name="dob"         class="java.util.Date"/>
    <field name="mobileNo"    class="java.lang.String"/>
    <field name="email"       class="java.lang.String"/>
    <field name="collegeName" class="java.lang.String"/>

    <!-- Title band: printed once at the start of the report -->
    <title>
        <band height="60">
            <rectangle>
                <reportElement x="0" y="0" width="782" height="50"
                               backcolor="#1565C0" forecolor="#1565C0"/>
                <graphicElement><pen lineWidth="0.0"/></graphicElement>
            </rectangle>
            <staticText>
                <reportElement x="0" y="5" width="782" height="40" forecolor="#FFFFFF"/>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font size="18" isBold="true"/>
                </textElement>
                <text><![CDATA[Student List Report]]></text>
            </staticText>
        </band>
    </title>

    <!-- Page header: printed at the top of every page -->
    <pageHeader>
        <band height="20">
            <staticText>
                <reportElement x="0" y="0" width="391" height="20" forecolor="#666666"/>
                <textElement verticalAlignment="Middle"><font size="8"/></textElement>
                <text><![CDATA[Online Result System]]></text>
            </staticText>
            <textField pattern="dd-MM-yyyy HH:mm">
                <reportElement x="391" y="0" width="391" height="20" forecolor="#666666"/>
                <textElement textAlignment="Right" verticalAlignment="Middle">
                    <font size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[new java.util.Date()]]></textFieldExpression>
            </textField>
        </band>
    </pageHeader>

    <!-- Column header: the table heading row -->
    <columnHeader>
        <band height="28">
            <rectangle>
                <reportElement x="0" y="0" width="782" height="28"
                               backcolor="#0D2137" forecolor="#0D2137"/>
                <graphicElement><pen lineWidth="0.0"/></graphicElement>
            </rectangle>
            <staticText>
                <reportElement x="0" y="0" width="30" height="28" forecolor="#FFFFFF"/>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font size="9" isBold="true"/>
                </textElement>
                <text><![CDATA[#]]></text>
            </staticText>
            <staticText>
                <reportElement x="30" y="0" width="50" height="28" forecolor="#FFFFFF"/>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font size="9" isBold="true"/>
                </textElement>
                <text><![CDATA[ID]]></text>
            </staticText>
            <staticText>
                <reportElement x="80" y="0" width="130" height="28" forecolor="#FFFFFF"/>
                <textElement verticalAlignment="Middle">
                    <font size="9" isBold="true"/>
                </textElement>
                <text><![CDATA[College]]></text>
            </staticText>
            <staticText>
                <reportElement x="210" y="0" width="110" height="28" forecolor="#FFFFFF"/>
                <textElement verticalAlignment="Middle">
                    <font size="9" isBold="true"/>
                </textElement>
                <text><![CDATA[First Name]]></text>
            </staticText>
            <staticText>
                <reportElement x="320" y="0" width="110" height="28" forecolor="#FFFFFF"/>
                <textElement verticalAlignment="Middle">
                    <font size="9" isBold="true"/>
                </textElement>
                <text><![CDATA[Last Name]]></text>
            </staticText>
            <staticText>
                <reportElement x="430" y="0" width="90" height="28" forecolor="#FFFFFF"/>
                <textElement verticalAlignment="Middle">
                    <font size="9" isBold="true"/>
                </textElement>
                <text><![CDATA[Date of Birth]]></text>
            </staticText>
            <staticText>
                <reportElement x="520" y="0" width="100" height="28" forecolor="#FFFFFF"/>
                <textElement verticalAlignment="Middle">
                    <font size="9" isBold="true"/>
                </textElement>
                <text><![CDATA[Mobile No]]></text>
            </staticText>
            <staticText>
                <reportElement x="620" y="0" width="162" height="28" forecolor="#FFFFFF"/>
                <textElement verticalAlignment="Middle">
                    <font size="9" isBold="true"/>
                </textElement>
                <text><![CDATA[Email ID]]></text>
            </staticText>
        </band>
    </columnHeader>

    <!-- Detail band: repeated once for every StudentBean in the list -->
    <detail>
        <band height="24">
            <!-- Alternating stripe: grey background on even rows -->
            <rectangle>
                <reportElement x="0" y="0" width="782" height="24" backcolor="#EEF2F7">
                    <printWhenExpression><![CDATA[$V{REPORT_COUNT} % 2 == 0]]></printWhenExpression>
                </reportElement>
                <graphicElement><pen lineWidth="0.0"/></graphicElement>
            </rectangle>

            <!-- $V{REPORT_COUNT} is a built-in variable: auto-incrementing row counter -->
            <textField>
                <reportElement x="0" y="0" width="30" height="24" forecolor="#888888"/>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[$V{REPORT_COUNT}]]></textFieldExpression>
            </textField>

            <!-- $F{fieldName} reads the value from the current StudentBean -->
            <textField>
                <reportElement x="30" y="0" width="50" height="24" forecolor="#888888"/>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{id}]]></textFieldExpression>
            </textField>
            <textField isBlankWhenNull="true">
                <reportElement x="80" y="0" width="130" height="24"/>
                <textElement verticalAlignment="Middle"><font size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{collegeName}]]></textFieldExpression>
            </textField>
            <textField isBlankWhenNull="true">
                <reportElement x="210" y="0" width="110" height="24"/>
                <textElement verticalAlignment="Middle">
                    <font size="9" isBold="true"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{firstName}]]></textFieldExpression>
            </textField>
            <textField isBlankWhenNull="true">
                <reportElement x="320" y="0" width="110" height="24"/>
                <textElement verticalAlignment="Middle"><font size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{lastName}]]></textFieldExpression>
            </textField>

            <!-- pattern="dd-MM-yyyy" formats the java.util.Date -->
            <textField isBlankWhenNull="true" pattern="dd-MM-yyyy">
                <reportElement x="430" y="0" width="90" height="24" forecolor="#555555"/>
                <textElement verticalAlignment="Middle"><font size="8"/></textElement>
                <textFieldExpression><![CDATA[$F{dob}]]></textFieldExpression>
            </textField>
            <textField isBlankWhenNull="true">
                <reportElement x="520" y="0" width="100" height="24"/>
                <textElement verticalAlignment="Middle"><font size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{mobileNo}]]></textFieldExpression>
            </textField>
            <textField isBlankWhenNull="true">
                <reportElement x="620" y="0" width="162" height="24" forecolor="#1565C0"/>
                <textElement verticalAlignment="Middle"><font size="8"/></textElement>
                <textFieldExpression><![CDATA[$F{email}]]></textFieldExpression>
            </textField>
        </band>
    </detail>

    <!-- Page footer: printed at the bottom of every page -->
    <!-- MUST come before <summary> in the JRXML -->
    <pageFooter>
        <band height="22">
            <line>
                <reportElement x="0" y="0" width="782" height="1" forecolor="#CCCCCC"/>
            </line>
            <staticText>
                <reportElement x="0" y="4" width="391" height="16" forecolor="#888888"/>
                <textElement><font size="8"/></textElement>
                <text><![CDATA[Online Result System - Student List Report]]></text>
            </staticText>
            <textField>
                <reportElement x="391" y="4" width="200" height="16" forecolor="#888888"/>
                <textElement textAlignment="Right"><font size="8"/></textElement>
                <textFieldExpression><![CDATA["Page " + $V{PAGE_NUMBER}]]></textFieldExpression>
            </textField>
        </band>
    </pageFooter>

    <!-- Summary band: printed once at the very end of the report -->
    <summary>
        <band height="35">
            <line>
                <reportElement x="0" y="5" width="782" height="1" forecolor="#CCCCCC"/>
            </line>
            <textField>
                <reportElement x="0" y="12" width="400" height="20" forecolor="#1565C0"/>
                <textElement><font size="10" isBold="true"/></textElement>
                <textFieldExpression><![CDATA["Total Students: " + $V{REPORT_COUNT}]]></textFieldExpression>
            </textField>
        </band>
    </summary>

</jasperReport>
```

### JRXML expression quick reference

| Expression | What it does |
|---|---|
| `$F{firstName}` | Reads the `firstName` field from the current bean |
| `$P{reportTitle}` | Reads the `reportTitle` parameter passed from Java |
| `$V{REPORT_COUNT}` | Built-in variable: row number (auto increments) |
| `$V{PAGE_NUMBER}` | Built-in variable: current page number |

### Column layout arithmetic

Page width = 842 (A4 landscape). Left margin 30 + right margin 30 = 60.  
**Column width = 842 − 60 = 782.**

All `x + width` values in each band must stay within 782:

| Column | x | width | ends at |
|---|---|---|---|
| # | 0 | 30 | 30 |
| ID | 30 | 50 | 80 |
| College | 80 | 130 | 210 |
| First Name | 210 | 110 | 320 |
| Last Name | 320 | 110 | 430 |
| Date of Birth | 430 | 90 | 520 |
| Mobile No | 520 | 100 | 620 |
| Email ID | 620 | 162 | **782** ✓ |

---

## Step 3 — Create the Report Controller Servlet

Create `StudentReportCtl.java` in the same package as all other controllers:

```
src/main/java/com/sunilos/p4/ctl/StudentReportCtl.java
```

```java
package com.sunilos.p4.ctl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.StudentBean;
import com.sunilos.p4.model.StudentModel;

@WebServlet("/ctl/StudentReportCtl")
public class StudentReportCtl extends HttpServlet {

    private static final Logger log = Logger.getLogger(StudentReportCtl.class);

    // Key used to cache the compiled report in ServletContext
    private static final String COMPILED_REPORT_KEY = "STUDENT_LIST_COMPILED_REPORT";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // --- Step A: Get (or compile) the JasperReport object ---
            JasperReport jasperReport = getCompiledReport();

            // --- Step B: Fetch data from the database ---
            StudentModel model = new StudentModel();
            @SuppressWarnings("unchecked")
            List<StudentBean> students = model.list();

            // --- Step C: Build parameters map (matches <parameter> in JRXML) ---
            Map<String, Object> params = new HashMap<>();
            params.put("reportTitle", "Student List Report");

            // --- Step D: Wrap the Java List in a JasperReports data source ---
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);

            // --- Step E: Fill the report (merge template + data) ---
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            // --- Step F: Stream the PDF to the browser ---
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=StudentList.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        } catch (Exception e) {
            log.error("Student report generation failed", e);
            throw new ServletException("Failed to generate student report: " + e.getMessage(), e);
        }
    }

    /**
     * Compiles the JRXML the first time, then caches the JasperReport object
     * in the ServletContext so it is reused for every subsequent request.
     * Compilation is expensive; caching makes PDF generation fast.
     */
    private JasperReport getCompiledReport() throws Exception {
        JasperReport cached = (JasperReport) getServletContext().getAttribute(COMPILED_REPORT_KEY);
        if (cached != null) {
            return cached;
        }
        InputStream jrxml = getClass().getResourceAsStream("/reports/StudentListReport.jrxml");
        if (jrxml == null) {
            throw new IllegalStateException(
                "JRXML not found on classpath: /reports/StudentListReport.jrxml");
        }
        JasperReport compiled = JasperCompileManager.compileReport(jrxml);
        getServletContext().setAttribute(COMPILED_REPORT_KEY, compiled);
        return compiled;
    }
}
```

### Why this servlet works without extra security code

The URL `/ctl/StudentReportCtl` is under `/ctl/*`, which is already covered by the project's existing `FrontController` filter. Any unauthenticated request is automatically redirected to the login page before this servlet is ever reached.

### The three JasperReports engine classes used

| Class | Method | What it does |
|---|---|---|
| `JasperCompileManager` | `compileReport(InputStream)` | Parses the JRXML and produces a `JasperReport` object |
| `JasperFillManager` | `fillReport(report, params, dataSource)` | Merges data into the compiled design → produces `JasperPrint` |
| `JasperExportManager` | `exportReportToPdfStream(print, stream)` | Writes the PDF bytes to any `OutputStream` |

### What is `JRBeanCollectionDataSource`?

JasperReports supports many data source types. `JRBeanCollectionDataSource` takes any `Collection` of Java beans and iterates over it, calling the appropriate getter for each `$F{fieldName}` in the detail band.

```
List<StudentBean>  →  JRBeanCollectionDataSource  →  $F{firstName} calls bean.getFirstName()
```

This is the simplest approach when you already have the data in memory. Alternatives:
- `JRResultSetDataSource` — pass a raw JDBC `ResultSet`
- `JRXmlDataSource` — pass XML data
- `JREmptyDataSource` — used when the report has no rows (for parameter-only reports)

---

## Step 4 — Register the URL Constant

Open `ORSView.java` and add one line at the bottom with the other controller constants:

```java
public String STUDENT_REPORT_CTL = APP_CONTEXT + "/ctl/StudentReportCtl";
// expands to: /ORSV0/ctl/StudentReportCtl
```

This follows the same convention used for every other controller in the project and lets JSP pages reference the URL as `ORSView.STUDENT_REPORT_CTL` rather than hard-coding the string.

---

## Step 5 — Add the Print PDF Button to the JSP

Open `StudentListView.jsp`. Find the card header and replace the single "Add Student" button with a button group:

```jsp
<%@page import="com.sunilos.p4.ctl.ORSView"%>

<div class="card-header ... d-flex justify-content-between align-items-center" ...>
  <h5 class="mb-0 fw-bold">
    <i class="bi bi-mortarboard-fill me-2"></i> Student List
  </h5>
  <div class="d-flex gap-2">

    <!-- Opens the PDF in a new browser tab -->
    <a href="<%=ORSView.STUDENT_REPORT_CTL%>" target="_blank"
       class="btn btn-sm btn-warning fw-semibold">
      <i class="bi bi-file-earmark-pdf me-1"></i> Print PDF
    </a>

    <a href="StudentCtl" class="btn btn-sm btn-light text-primary fw-semibold">
      <i class="bi bi-plus-circle me-1"></i> Add Student
    </a>
  </div>
</div>
```

`target="_blank"` opens the PDF in a new tab so the student list page stays open.

---

## Build and Run

```bash
mvn clean install
```

Maven will:
1. Compile all Java sources (including `StudentReportCtl.java`)
2. Copy `StudentListReport.jrxml` → `WEB-INF/classes/reports/`
3. Package everything into `ORSV0.war`
4. The Ant plugin copies the WAR to Tomcat and removes the old deployment

Then open: **http://localhost:8080/ORSV0/ctl/StudentListCtl**

You will see the **Print PDF** button in the top-right corner of the student list card. Clicking it opens a PDF like this:

```
┌─────────────────────────────────────────────────────────────────────┐
│              Student List Report                                     │  ← blue title band
├────┬──────┬──────────────┬────────────┬───────────┬────────┬────────┤
│  # │  ID  │   College    │ First Name │ Last Name │  DOB   │ Email  │  ← dark header
├────┼──────┼──────────────┼────────────┼───────────┼────────┼────────┤
│  1 │  101 │ City College │ Anil       │ Sharma    │ ...    │ ...    │
│  2 │  102 │ NIT          │ Priya      │ Verma     │ ...    │ ...    │  ← striped rows
│  ...                                                                 │
├─────────────────────────────────────────────────────────────────────┤
│ Total Students: 25                                                   │  ← summary band
└─────────────────────────────────────────────────────────────────────┘
│ Online Result System - Student List Report          Page 1           │  ← footer
```

---

## Files Changed / Created

| File | Action | Purpose |
|---|---|---|
| `pom.xml` | Modified | Added `jasperreports:6.20.6` dependency |
| `src/main/resources/reports/StudentListReport.jrxml` | **Created** | Report layout/design |
| `src/main/java/.../ctl/StudentReportCtl.java` | **Created** | Servlet that generates the PDF |
| `src/main/java/.../ctl/ORSView.java` | Modified | Added `STUDENT_REPORT_CTL` constant |
| `src/main/webapp/jsp/StudentListView.jsp` | Modified | Added Print PDF button |

---

## Errors Encountered and How They Were Fixed

### Error 1 — `NoClassDefFoundError: org/apache/commons/logging/LogFactory`

**Cause:** The original dependency block excluded `commons-logging`, but JasperReports needs it internally to initialise its own context (`DefaultJasperReportsContext`).

**Fix:** Remove the `<exclusions>` block entirely. `commons-logging` and Log4j coexist without conflict.

```xml
<!-- WRONG — causes NoClassDefFoundError -->
<dependency>
  <groupId>net.sf.jasperreports</groupId>
  <artifactId>jasperreports</artifactId>
  <version>6.20.6</version>
  <exclusions>
    <exclusion>
      <groupId>commons-logging</groupId>
      <artifactId>commons-logging</artifactId>
    </exclusion>
  </exclusions>
</dependency>

<!-- CORRECT -->
<dependency>
  <groupId>net.sf.jasperreports</groupId>
  <artifactId>jasperreports</artifactId>
  <version>6.20.6</version>
</dependency>
```

---

### Error 2 — `SAXParseException: Invalid content was found starting with element 'pageFooter'. One of 'noData' is expected`

**Cause:** The JRXML bands were in the wrong order. `<summary>` was placed before `<pageFooter>`.

**Fix:** The JasperReports XSD enforces a strict band order. Always follow this sequence:

```
detail  →  columnFooter  →  pageFooter  →  lastPageFooter  →  summary  →  noData
```

```xml
<!-- WRONG -->
<detail>...</detail>
<summary>...</summary>      ← summary before pageFooter
<pageFooter>...</pageFooter>

<!-- CORRECT -->
<detail>...</detail>
<pageFooter>...</pageFooter>  ← pageFooter first
<summary>...</summary>        ← summary last
```

---

## How to Extend This Integration

### Add a search filter to the report

Pass the current search parameters as URL query params on the button:

```jsp
<a href="<%=ORSView.STUDENT_REPORT_CTL%>
         ?firstName=<%=ServletUtility.getParameter("firstName", request)%>
         &lastName=<%=ServletUtility.getParameter("lastName", request)%>"
   target="_blank" class="btn btn-sm btn-warning">
  Print PDF (filtered)
</a>
```

In the servlet, read them and call `model.search(bean)` instead of `model.list()`:

```java
StudentBean filter = new StudentBean();
filter.setFirstName(request.getParameter("firstName"));
filter.setLastName(request.getParameter("lastName"));
List<StudentBean> students = model.search(filter);
```

### Add a new report for Marksheet

Follow exactly the same five steps:
1. No new Maven dependency needed — already added.
2. Create `MarksheetReport.jrxml` with `MarksheetBean` fields (`rollNo`, `name`, `physics`, `chemistry`, `maths`).
3. Create `MarksheetReportCtl.java` using `MarksheetModel`.
4. Add `MARKSHEET_REPORT_CTL` to `ORSView.java`.
5. Add a Print PDF button to `MarksheetListView.jsp`.

### Download instead of preview

Change the `Content-Disposition` header:

```java
// inline = opens in browser PDF viewer
response.setHeader("Content-Disposition", "inline; filename=StudentList.pdf");

// attachment = forces a download dialog
response.setHeader("Content-Disposition", "attachment; filename=StudentList.pdf");
```

### Export to Excel instead of PDF

Replace the export step with:

```java
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;

response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
response.setHeader("Content-Disposition", "attachment; filename=StudentList.xlsx");

JRXlsxExporter exporter = new JRXlsxExporter();
exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
exporter.exportReport();
```
