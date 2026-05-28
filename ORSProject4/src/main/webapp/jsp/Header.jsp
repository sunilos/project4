<%@page import="com.sunilos.p4.bean.RoleBean"%>
    <%@page import="com.sunilos.p4.ctl.LoginCtl"%>
        <%@page import="com.sunilos.p4.bean.UserBean"%>
            <%@page import="com.sunilos.p4.ctl.ORSView"%>
                <%
    MessageSource ms    = MessageSource.getInstance();
    String locale       = ms.getLanguage();
    UserBean userBean   = (UserBean) session.getAttribute("user");
    boolean loggedIn    = userBean != null;
    boolean isAdmin     = loggedIn && userBean.getRoleId() == RoleBean.ADMIN;
    boolean isStudent   = loggedIn && userBean.getRoleId() == RoleBean.STUDENT;
    String  displayName = loggedIn
        ? userBean.getFirstName() + " (" + session.getAttribute("role") + ")"
        : "Guest";
%>

                    <nav class="navbar navbar-expand-lg navbar-dark py-0" style="background:linear-gradient(135deg,#0d2137 0%,#1565c0 100%); min-height:58px;">
                        <div class="container-fluid px-4">

                            <!-- ===== Brand ===== -->
                            <a class="navbar-brand fw-bold d-flex align-items-center gap-2 py-2" href="<%=ORSView.WELCOME_CTL%>">
      <img src="<%=ORSView.APP_CONTEXT%>/img/customLogo.jpg" alt="ORS"
           height="34" class="rounded" style="object-fit:contain;">
      <span>ORS</span>
    </a>

                            <!-- Mobile toggler -->
                            <button class="navbar-toggler border-0" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav" aria-controls="mainNav" aria-expanded="false">
      <span class="navbar-toggler-icon"></span>
    </button>

                            <div class="collapse navbar-collapse" id="mainNav">

                                <!-- ===== Left nav — grouped dropdowns (logged-in only) ===== -->
                                <% if (loggedIn) { %>
                                    <ul class="navbar-nav me-auto gap-0">

                                        <!-- ---- Marksheet ---- -->
                                        <li class="nav-item dropdown">
                                            <a class="nav-link dropdown-toggle px-3 py-3" href="#" data-bs-toggle="dropdown" aria-expanded="false">
                                                <i class="bi bi-file-earmark-text me-1"></i> Marksheet
                                            </a>
                                            <ul class="dropdown-menu dropdown-menu-dark shadow border-0 rounded-3" style="min-width:200px; background:#1a2e4a;">
                                                <li>
                                                    <h6 class="dropdown-header text-uppercase text-info small">
                                                        <i class="bi bi-search me-1"></i> Query
                                                    </h6>
                                                </li>
                                                <li>
                                                    <a class="dropdown-item" href="<%=ORSView.GET_MARKSHEET_CTL%>">
                                                        <i class="bi bi-search me-2 text-info"></i> Get Marksheet
                                                    </a>
                                                </li>
                                                <li>
                                                    <a class="dropdown-item" href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">
                                                        <i class="bi bi-trophy me-2 text-warning"></i> Merit List
                                                    </a>
                                                </li>
                                                <% if (isAdmin) { %>
                                                    <li>
                                                        <hr class="dropdown-divider border-secondary">
                                                    </li>
                                                    <li>
                                                        <h6 class="dropdown-header text-uppercase text-info small">
                                                            <i class="bi bi-gear me-1"></i> Manage
                                                        </h6>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.MARKSHEET_CTL%>">
                                                            <i class="bi bi-plus-circle me-2 text-success"></i> Add Marksheet
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.MARKSHEET_LIST_CTL%>">
                                                            <i class="bi bi-list-ul me-2 text-primary"></i> Marksheet List
                                                        </a>
                                                    </li>

                                                    <% } %>
                                            </ul>
                                        </li>

                                        <!-- ---- Academics: Colleges / Courses / Subjects (Admin only) ---- -->
                                        <% if (isAdmin) { %>
                                            <li class="nav-item dropdown">
                                                <a class="nav-link dropdown-toggle px-3 py-3" href="#" data-bs-toggle="dropdown" aria-expanded="false">
                                                    <i class="bi bi-mortarboard me-1"></i> Academics
                                                </a>
                                                <ul class="dropdown-menu dropdown-menu-dark shadow border-0 rounded-3" style="min-width:210px; background:#1a2e4a;">

                                                    <!-- Colleges -->
                                                    <li>
                                                        <h6 class="dropdown-header text-uppercase text-info small">
                                                            <i class="bi bi-bank2 me-1"></i> Colleges
                                                        </h6>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.COLLEGE_CTL%>">
                                                            <i class="bi bi-plus-circle me-2 text-success"></i> Add College
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.COLLEGE_LIST_CTL%>">
                                                            <i class="bi bi-building me-2 text-primary"></i> College List
                                                        </a>
                                                    </li>

                                                    <li><hr class="dropdown-divider border-secondary"></li>

                                                    <!-- Courses -->
                                                    <li>
                                                        <h6 class="dropdown-header text-uppercase text-info small">
                                                            <i class="bi bi-journal-bookmark me-1"></i> Courses
                                                        </h6>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.COURSE_CTL%>">
                                                            <i class="bi bi-plus-circle me-2 text-success"></i> Add Course
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.COURSE_LIST_CTL%>">
                                                            <i class="bi bi-list-ul me-2 text-primary"></i> Course List
                                                        </a>
                                                    </li>

                                                    <li><hr class="dropdown-divider border-secondary"></li>

                                                    <!-- Subjects -->
                                                    <li>
                                                        <h6 class="dropdown-header text-uppercase text-info small">
                                                            <i class="bi bi-card-text me-1"></i> Subjects
                                                        </h6>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.SUBJECT_CTL%>">
                                                            <i class="bi bi-plus-circle me-2 text-success"></i> Add Subject
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.SUBJECT_LIST_CTL%>">
                                                            <i class="bi bi-list-ul me-2 text-primary"></i> Subject List
                                                        </a>
                                                    </li>
                                                </ul>
                                            </li>

                                            <!-- ---- People: Students / Faculty / Users / Roles (Admin only) ---- -->
                                            <li class="nav-item dropdown">
                                                <a class="nav-link dropdown-toggle px-3 py-3" href="#" data-bs-toggle="dropdown" aria-expanded="false">
                                                    <i class="bi bi-people me-1"></i> People
                                                </a>
                                                <ul class="dropdown-menu dropdown-menu-dark shadow border-0 rounded-3" style="min-width:210px; background:#1a2e4a;">

                                                    <!-- Students -->
                                                    <li>
                                                        <h6 class="dropdown-header text-uppercase text-info small">
                                                            <i class="bi bi-mortarboard me-1"></i> Students
                                                        </h6>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.STUDENT_CTL%>">
                                                            <i class="bi bi-person-plus me-2 text-success"></i> Add Student
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.STUDENT_LIST_CTL%>">
                                                            <i class="bi bi-people me-2 text-primary"></i> Student List
                                                        </a>
                                                    </li>

                                                    <li><hr class="dropdown-divider border-secondary"></li>

                                                    <!-- Faculty -->
                                                    <li>
                                                        <h6 class="dropdown-header text-uppercase text-info small">
                                                            <i class="bi bi-person-badge me-1"></i> Faculty
                                                        </h6>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.FACULTY_CTL%>">
                                                            <i class="bi bi-plus-circle me-2 text-success"></i> Add Faculty
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.FACULTY_LIST_CTL%>">
                                                            <i class="bi bi-person-lines-fill me-2 text-primary"></i> Faculty List
                                                        </a>
                                                    </li>

                                                    <li><hr class="dropdown-divider border-secondary"></li>

                                                    <!-- Users -->
                                                    <li>
                                                        <h6 class="dropdown-header text-uppercase text-info small">
                                                            <i class="bi bi-person-gear me-1"></i> Users
                                                        </h6>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.USER_CTL%>">
                                                            <i class="bi bi-person-plus me-2 text-success"></i> Add User
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.USER_LIST_CTL%>">
                                                            <i class="bi bi-person-lines-fill me-2 text-primary"></i> User List
                                                        </a>
                                                    </li>

                                                    <li><hr class="dropdown-divider border-secondary"></li>

                                                    <!-- Roles -->
                                                    <li>
                                                        <h6 class="dropdown-header text-uppercase text-info small">
                                                            <i class="bi bi-shield-check me-1"></i> Roles
                                                        </h6>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.ROLE_CTL%>">
                                                            <i class="bi bi-plus-circle me-2 text-success"></i> Add Role
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.ROLE_LIST_CTL%>">
                                                            <i class="bi bi-shield-fill-check me-2 text-primary"></i> Role List
                                                        </a>
                                                    </li>
                                                </ul>
                                            </li>

                                            <!-- ---- Reports (Admin only) ---- -->
                                            <li class="nav-item dropdown">
                                                <a class="nav-link dropdown-toggle px-3 py-3" href="#" data-bs-toggle="dropdown" aria-expanded="false">
                                                    <i class="bi bi-file-earmark-bar-graph me-1"></i> Reports
                                                </a>
                                                <ul class="dropdown-menu dropdown-menu-dark shadow border-0 rounded-3" style="min-width:230px; background:#1a2e4a;">

                                                    <!-- Academics -->
                                                    <li>
                                                        <h6 class="dropdown-header text-uppercase text-warning small">
                                                            <i class="bi bi-mortarboard me-1"></i> Academics
                                                        </h6>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.COLLEGE_REPORT_CTL%>" target="_blank">
                                                            <i class="bi bi-file-earmark-pdf me-2 text-warning"></i> College List
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.COURSE_REPORT_CTL%>" target="_blank">
                                                            <i class="bi bi-file-earmark-pdf me-2 text-warning"></i> Course List
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.SUBJECT_REPORT_CTL%>" target="_blank">
                                                            <i class="bi bi-file-earmark-pdf me-2 text-warning"></i> Subject List
                                                        </a>
                                                    </li>

                                                    <li><hr class="dropdown-divider border-secondary"></li>

                                                    <!-- People -->
                                                    <li>
                                                        <h6 class="dropdown-header text-uppercase text-warning small">
                                                            <i class="bi bi-people me-1"></i> People
                                                        </h6>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.STUDENT_REPORT_CTL%>" target="_blank">
                                                            <i class="bi bi-file-earmark-pdf me-2 text-warning"></i> Student List
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.FACULTY_REPORT_CTL%>" target="_blank">
                                                            <i class="bi bi-file-earmark-pdf me-2 text-warning"></i> Faculty List
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.USER_REPORT_CTL%>" target="_blank">
                                                            <i class="bi bi-file-earmark-pdf me-2 text-warning"></i> User List
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.ROLE_REPORT_CTL%>" target="_blank">
                                                            <i class="bi bi-file-earmark-pdf me-2 text-warning"></i> Role List
                                                        </a>
                                                    </li>

                                                    <li><hr class="dropdown-divider border-secondary"></li>

                                                    <!-- Results -->
                                                    <li>
                                                        <h6 class="dropdown-header text-uppercase text-warning small">
                                                            <i class="bi bi-clipboard-data me-1"></i> Results
                                                        </h6>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="<%=ORSView.MARKSHEET_REPORT_CTL%>" target="_blank">
                                                            <i class="bi bi-file-earmark-pdf me-2 text-warning"></i> Marksheet List
                                                        </a>
                                                    </li>

                                                </ul>
                                            </li>
                                            <% } %>
                                                <%-- end isAdmin --%>

                                    </ul>
                                    <% } else { %>
                                        <ul class="navbar-nav me-auto"></ul>
                                        <% } %>

                                            <!-- ===== Right side ===== -->
                                            <ul class="navbar-nav align-items-lg-center gap-1 ms-2">

                                                <!-- Language -->
                                                <li class="nav-item">
                                                    <form class="d-flex align-items-center m-0">
                                                        <select class="form-select form-select-sm border-0 py-1" name="lang" id="lang" onchange="this.form.submit()" style="width:120px; background:rgba(255,255,255,0.12);
                           color:#fff; font-size:0.82rem; cursor:pointer;">
              <option value="en" <%=("en".equals(locale))?"selected":""%>
                      style="background:#0d2137; color:#fff;">&#127758; English</option>
              <option value="hi" <%=("hi".equals(locale))?"selected":""%>
                      style="background:#0d2137; color:#fff;">&#127470;&#127475; Hindi</option>
            </select>
                                                    </form>
                                                </li>

                                                <!-- Home -->
                                                <li class="nav-item">
                                                    <a class="nav-link px-2" href="<%=ORSView.WELCOME_CTL%>">
                                                        <i class="bi bi-house-fill"></i>
                                                        <span class="d-lg-none ms-1">Home</span>
                                                    </a>
                                                </li>

                                                <!-- My Account dropdown (logged-in) -->
                                                <% if (loggedIn) { %>
                                                    <li class="nav-item dropdown">
                                                        <a class="nav-link dropdown-toggle d-flex align-items-center gap-1 px-2" href="#" data-bs-toggle="dropdown" aria-expanded="false">
                                                            <span class="rounded-circle bg-white text-primary d-flex align-items-center justify-content-center fw-bold" style="width:28px;height:28px;font-size:0.75rem;flex-shrink:0;">
              <%=userBean.getFirstName().substring(0,1).toUpperCase()%>
            </span>
                                                            <span class="d-none d-lg-inline small"><%=userBean.getFirstName()%></span>
                                                        </a>
                                                        <ul class="dropdown-menu dropdown-menu-dark dropdown-menu-end shadow border-0 rounded-3" style="min-width:210px; background:#1a2e4a;">
                                                            <li>
                                                                <div class="px-3 py-2 border-bottom border-secondary">
                                                                    <div class="text-white fw-semibold small">
                                                                        <%=displayName%>
                                                                    </div>
                                                                    <div class="text-white-50" style="font-size:0.75rem;">
                                                                        <%=userBean.getLogin()%>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item mt-1" href="<%=ORSView.MY_PROFILE_CTL%>">
                                                                    <i class="bi bi-person-circle me-2 text-info"></i> My Profile
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="<%=ORSView.CHANGE_PASSWORD_CTL%>">
                                                                    <i class="bi bi-key me-2 text-warning"></i> Change Password
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="<%=ORSView.JAVA_DOC_VIEW%>">
                                                                    <i class="bi bi-book me-2 text-success"></i> Java Doc
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <hr class="dropdown-divider border-secondary">
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item text-danger" href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">
                                                                    <i class="bi bi-box-arrow-right me-2"></i> Logout
                                                                </a>
                                                            </li>
                                                        </ul>
                                                    </li>

                                                    <% } else { %>
                                                        <!-- Login button (guest) -->
                                                        <li class="nav-item">
                                                            <a class="btn btn-sm btn-outline-light ms-1 px-3" href="<%=ORSView.LOGIN_CTL%>">
                                                                <i class="bi bi-box-arrow-in-right me-1"></i> Login
                                                            </a>
                                                        </li>
                                                        <% } %>

                                            </ul>
                            </div>
                        </div>
                    </nav>