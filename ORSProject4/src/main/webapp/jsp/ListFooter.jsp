<%@page import="com.sunilos.p4.ctl.BaseCtl"%>
<div class="d-flex justify-content-center align-items-center gap-2">
  <button type="submit" name="operation" value="<%=BaseCtl.OP_PREVIOUS%>"
          class="btn btn-outline-primary btn-sm">
    <i class="bi bi-chevron-left"></i> Previous
  </button>
  <button type="submit" name="operation" value="<%=BaseCtl.OP_NEXT%>"
          class="btn btn-outline-primary btn-sm">
    Next <i class="bi bi-chevron-right"></i>
  </button>
</div>
