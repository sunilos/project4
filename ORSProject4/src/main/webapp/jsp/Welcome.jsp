<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.bean.UserBean"%>
<%@page import="com.sunilos.p4.bean.RoleBean"%>

<%
    UserBean currentUser = (UserBean) session.getAttribute("user");
    boolean loggedIn  = currentUser != null;
    boolean isStudent = loggedIn && currentUser.getRoleId() == RoleBean.STUDENT;
    boolean isAdmin   = loggedIn && currentUser.getRoleId() == RoleBean.ADMIN;
    String  firstName = loggedIn ? currentUser.getFirstName() : "Guest";
%>

<div class="container-fluid px-4 py-4" style="max-width:1100px;">

  <!-- ===== Hero Banner ===== -->
  <div class="rounded-4 text-white p-4 p-md-5 mb-4 d-flex align-items-center gap-4 shadow"
       style="background: linear-gradient(135deg, #0d2137 0%, #1565c0 100%);">
    <div class="flex-shrink-0 rounded-circle d-flex align-items-center justify-content-center"
         style="width:80px; height:80px; background:rgba(255,255,255,0.15);
                border:2px solid rgba(255,255,255,0.25); font-size:2.3rem;">
      <i class="fas fa-graduation-cap"></i>
    </div>
    <div>
      <h1 class="fw-bold mb-1 fs-2">
        Welcome<% if (loggedIn) { %>, <%=firstName%>!<% } else { %> to ORS<% } %>
      </h1>
      <p class="mb-0 opacity-75">Online Result System &mdash; Powered by Rays Technologies</p>
    </div>
  </div>

  <!-- ===== Section label ===== -->
  <p class="text-uppercase text-muted fw-semibold small mb-3" style="letter-spacing:.08em;">
    <% if (isAdmin) { %>Administration Panel
    <% } else if (isStudent) { %>Quick Access
    <% } else { %>Get Started<% } %>
  </p>

  <!-- ===== Dashboard Cards ===== -->
  <div class="row g-3">

    <% if (isStudent) { %>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.GET_MARKSHEET_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card border-top border-4 border-primary">
          <div class="fs-1 text-primary mb-2"><i class="bi bi-file-earmark-text-fill"></i></div>
          <h6 class="fw-bold mb-1">My Marksheet</h6>
          <p class="text-muted small mb-0">View your academic results</p>
        </div>
      </a>
    </div>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.MY_PROFILE_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card border-top border-4 border-success">
          <div class="fs-1 text-success mb-2"><i class="bi bi-person-circle"></i></div>
          <h6 class="fw-bold mb-1">My Profile</h6>
          <p class="text-muted small mb-0">View and edit your details</p>
        </div>
      </a>
    </div>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.CHANGE_PASSWORD_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card border-top border-4 border-warning">
          <div class="fs-1 text-warning mb-2"><i class="bi bi-key-fill"></i></div>
          <h6 class="fw-bold mb-1">Change Password</h6>
          <p class="text-muted small mb-0">Update your login password</p>
        </div>
      </a>
    </div>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card border-top border-4 border-danger">
          <div class="fs-1 text-danger mb-2"><i class="bi bi-trophy-fill"></i></div>
          <h6 class="fw-bold mb-1">Merit List</h6>
          <p class="text-muted small mb-0">View class rankings</p>
        </div>
      </a>
    </div>

    <% } else if (isAdmin) { %>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.USER_LIST_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card border-top border-4 border-primary">
          <div class="fs-1 text-primary mb-2"><i class="bi bi-people-fill"></i></div>
          <h6 class="fw-bold mb-1">Users</h6>
          <p class="text-muted small mb-0">Manage system users</p>
        </div>
      </a>
    </div>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.COLLEGE_LIST_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card border-top border-4 border-success">
          <div class="fs-1 text-success mb-2"><i class="bi bi-bank2"></i></div>
          <h6 class="fw-bold mb-1">Colleges</h6>
          <p class="text-muted small mb-0">Manage college records</p>
        </div>
      </a>
    </div>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.STUDENT_LIST_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card" style="border-top:4px solid #7c3aed;">
          <div class="fs-1 mb-2" style="color:#7c3aed;"><i class="bi bi-mortarboard-fill"></i></div>
          <h6 class="fw-bold mb-1">Students</h6>
          <p class="text-muted small mb-0">Manage student records</p>
        </div>
      </a>
    </div>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.MARKSHEET_LIST_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card border-top border-4 border-warning">
          <div class="fs-1 text-warning mb-2"><i class="bi bi-file-earmark-text-fill"></i></div>
          <h6 class="fw-bold mb-1">Marksheets</h6>
          <p class="text-muted small mb-0">Manage marksheet records</p>
        </div>
      </a>
    </div>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.ROLE_LIST_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card border-top border-4 border-danger">
          <div class="fs-1 text-danger mb-2"><i class="bi bi-shield-fill-check"></i></div>
          <h6 class="fw-bold mb-1">Roles</h6>
          <p class="text-muted small mb-0">Manage user roles</p>
        </div>
      </a>
    </div>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card" style="border-top:4px solid #0d9488;">
          <div class="fs-1 mb-2" style="color:#0d9488;"><i class="bi bi-trophy-fill"></i></div>
          <h6 class="fw-bold mb-1">Merit List</h6>
          <p class="text-muted small mb-0">View merit rankings</p>
        </div>
      </a>
    </div>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.GET_MARKSHEET_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card border-top border-4 border-primary">
          <div class="fs-1 text-primary mb-2"><i class="bi bi-search"></i></div>
          <h6 class="fw-bold mb-1">Get Marksheet</h6>
          <p class="text-muted small mb-0">Lookup a marksheet</p>
        </div>
      </a>
    </div>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.MY_PROFILE_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card" style="border-top:4px solid #374151;">
          <div class="fs-1 mb-2" style="color:#374151;"><i class="bi bi-person-gear"></i></div>
          <h6 class="fw-bold mb-1">My Profile</h6>
          <p class="text-muted small mb-0">View and edit your profile</p>
        </div>
      </a>
    </div>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.FACULTY_LIST_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card" style="border-top:4px solid #0891b2;">
          <div class="fs-1 mb-2" style="color:#0891b2;"><i class="bi bi-person-badge-fill"></i></div>
          <h6 class="fw-bold mb-1">Faculty</h6>
          <p class="text-muted small mb-0">Manage faculty records</p>
        </div>
      </a>
    </div>

    <% } else { %>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.LOGIN_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card border-top border-4 border-primary">
          <div class="fs-1 text-primary mb-2"><i class="bi bi-box-arrow-in-right"></i></div>
          <h6 class="fw-bold mb-1">Login</h6>
          <p class="text-muted small mb-0">Sign in to your account</p>
        </div>
      </a>
    </div>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.USER_REGISTRATION_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card border-top border-4 border-success">
          <div class="fs-1 text-success mb-2"><i class="bi bi-person-plus-fill"></i></div>
          <h6 class="fw-bold mb-1">Register</h6>
          <p class="text-muted small mb-0">Create a new account</p>
        </div>
      </a>
    </div>

    <div class="col-6 col-md-4 col-lg-3">
      <a href="<%=ORSView.GET_MARKSHEET_CTL%>" class="text-decoration-none">
        <div class="card h-100 border-0 shadow-sm rounded-4 text-center p-3 ors-card" style="border-top:4px solid #7c3aed;">
          <div class="fs-1 mb-2" style="color:#7c3aed;"><i class="bi bi-search"></i></div>
          <h6 class="fw-bold mb-1">Get Marksheet</h6>
          <p class="text-muted small mb-0">Search for a marksheet</p>
        </div>
      </a>
    </div>

    <% } %>

  </div><!-- /row -->
</div>
