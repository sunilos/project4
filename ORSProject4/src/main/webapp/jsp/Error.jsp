<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page isErrorPage="true"%>

<div class="container py-5" style="max-width:700px;">
  <div class="card border-0 shadow-sm rounded-4 overflow-hidden">

    <div class="card-header text-white border-0 py-4 px-4 text-center bg-danger">
      <div class="d-flex justify-content-center mb-3">
        <div class="rounded-circle d-flex align-items-center justify-content-center"
             style="width:64px;height:64px;background:rgba(255,255,255,0.2);font-size:2rem;">
          <i class="bi bi-exclamation-triangle-fill"></i>
        </div>
      </div>
      <h4 class="fw-bold mb-1">Application Error</h4>
      <p class="mb-0 opacity-75 small">An unexpected error occurred. Please try again.</p>
    </div>

    <div class="card-body px-4 py-4">
      <% if(exception != null) { %>
      <div class="mb-3">
        <h6 class="fw-semibold text-danger"><i class="bi bi-bug-fill me-2"></i>Error Details</h6>
        <pre class="bg-light rounded p-3 small text-danger" style="max-height:300px;overflow:auto;"><%=DataUtility.exceptionToString(exception)%></pre>
      </div>
      <% } %>
      <button class="btn btn-primary" onclick="history.back()">
        <i class="bi bi-arrow-left me-1"></i> Go Back
      </button>
      <a href="javascript:location.reload()" class="btn btn-outline-secondary ms-2">
        <i class="bi bi-arrow-clockwise me-1"></i> Retry
      </a>
    </div>
  </div>
</div>
