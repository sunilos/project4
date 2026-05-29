<%@page import="com.sunilos.p4.ctl.CollegeListCtl"%>
    <%@page import="com.sunilos.p4.ctl.BaseCtl"%>
        <%@page import="com.sunilos.p4.ctl.ORSView"%>
            <%@page import="com.sunilos.p4.util.ServletUtility"%>
                <%@page import="com.sunilos.p4.bean.CollegeBean"%>
                    <%@page import="java.util.List"%>
                        <%@page import="java.util.Iterator"%>

                            <%
    int pageNo   = ServletUtility.getPageNo(request);
    int pageSize = ServletUtility.getPageSize(request);
    int index    = ((pageNo - 1) * pageSize) + 1;
    List list    = ServletUtility.getList(request);
    Iterator<CollegeBean> it = list.iterator();
    String _err  = ServletUtility.getErrorMessage(request);
%>

                                <div class="container-fluid px-4 py-4" style="max-width:1100px;">
                                    <div class="card border-0 shadow-sm rounded-4 overflow-hidden">

                                        <div class="card-header text-white border-0 py-3 px-4 d-flex justify-content-between align-items-center" style="background:linear-gradient(135deg,#0d2137 0%,#1565c0 100%);">
                                            <h5 class="mb-0 fw-bold"><i class="bi bi-bank2 me-2"></i> College List</h5>
                                            <div class="d-flex gap-2">
                                              <a href="<%=ORSView.COLLEGE_REPORT_CTL%>" target="_blank" class="btn btn-sm btn-warning fw-semibold">
                                                <i class="bi bi-file-earmark-pdf me-1"></i> Print PDF
                                              </a>
                                              <a href="<%=ORSView.COLLEGE_REPORT_CTL%>?type=doc" target="_blank" class="btn btn-sm btn-info fw-semibold">
                                                <i class="bi bi-file-earmark-word me-1"></i> Print DOC
                                              </a>
                                              <a href="CollegeCtl" class="btn btn-sm btn-light text-primary fw-semibold">
                                                <i class="bi bi-plus-circle me-1"></i> Add College
                                              </a>
                                            </div>
                                        </div>

                                        <form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="POST">
                                            <input type="hidden" name="pageNo" value="<%=pageNo%>">
                                            <input type="hidden" name="pageSize" value="<%=pageSize%>">

                                            <!-- Search bar -->
                                            <div class="p-3 bg-light border-bottom d-flex flex-wrap gap-2 align-items-center">
                                                <input type="text" name="name" class="form-control form-control-sm" style="max-width:200px;" placeholder="Search by Name" value="<%=ServletUtility.getParameter("name", request)%>">
                                                <input type="text" name="city" class="form-control form-control-sm" style="max-width:180px;" placeholder="Search by City" value="<%=ServletUtility.getParameter("city", request)%>">
                                                <button type="submit" name="operation" value="<%=BaseCtl.OP_SEARCH%>" class="btn btn-primary btn-sm">
          <i class="bi bi-search me-1"></i> Search
        </button>
                                                <button type="submit" name="operation" value="<%=BaseCtl.OP_DELETE%>" class="btn btn-danger btn-sm ms-auto" onclick="return confirm('Delete selected colleges?')">
          <i class="bi bi-trash me-1"></i> Delete Selected
        </button>
                                            </div>

                                            <% if(_err != null && !_err.isEmpty()) { %>
                                                <div class="alert alert-danger py-2 mx-3 mt-3"><i class="bi bi-exclamation-triangle-fill me-2"></i>
                                                    <%=_err%>
                                                </div>
                                                <% } %>

                                                    <!-- Table -->
                                                    <div class="table-responsive">
                                                        <table class="table table-hover align-middle mb-0">
                                                            <thead class="table-light">
                                                                <tr>
                                                                    <th width="40"><input type="checkbox" onclick="document.querySelectorAll('input[name=ids]').forEach(c=>c.checked=this.checked)"></th>
                                                                    <th>#</th>
                                                                    <th>ID</th>
                                                                    <th>Name</th>
                                                                    <th>Address</th>
                                                                    <th>State</th>
                                                                    <th>City</th>
                                                                    <th>Phone No</th>
                                                                    <th>Action</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <% while(it.hasNext()) { CollegeBean bean = it.next(); %>
                                                                    <tr>
                                                                        <td><input type="checkbox" name="ids" value="<%=bean.getId()%>"></td>
                                                                        <td class="text-muted small">
                                                                            <%=index++%>
                                                                        </td>
                                                                        <td class="text-muted small">
                                                                            <%=bean.getId()%>
                                                                        </td>
                                                                        <td class="fw-semibold">
                                                                            <%=bean.getName()%>
                                                                        </td>
                                                                        <td>
                                                                            <%=bean.getAddress()%>
                                                                        </td>
                                                                        <td>
                                                                            <%=bean.getState()%>
                                                                        </td>
                                                                        <td>
                                                                            <%=bean.getCity()%>
                                                                        </td>
                                                                        <td>
                                                                            <%=bean.getPhoneNo()%>
                                                                        </td>
                                                                        <td>
                                                                            <a href="CollegeCtl?id=<%=bean.getId()%>" class="btn btn-sm btn-outline-primary">
                                                                                <i class="bi bi-pencil"></i> Edit
                                                                            </a>
                                                                            <a href="<%=ORSView.COLLEGE_LIST_CTL%>?id=<%=bean.getId()%>&operation=<%=BaseCtl.OP_DELETE%>" class="btn btn-sm btn-outline-danger" onclick="return confirm('Delete this college?')">
                                                                                 <i class="bi bi-trash"></i> Delete
                                                                            </a>
                                                                        </td>
                                                                    </tr>
                                                                    <% } %>
                                                            </tbody>
                                                        </table>
                                                    </div>

                                                    <!-- Pagination -->
                                                    <div class="p-3 border-top">
                                                        <%@ include file="ListFooter.jsp"%>
                                                    </div>
                                        </form>
                                    </div>
                                </div>