<%-- 
    Document   : signup
    Created on : 27 sept. 2018, 17:35:46
    Author     : damien
--%>

<%@ include file = "header.jsp" %>
<div class="container valign-wrapper" style="height:75vh;">
    <div class="row">
      <div class="col s6">
        <a href="<c:url value='/person.htm' />" class="btn-large blue">Particulier</a>
      </div>
      <div class="col s6">
        <a href="<c:url value='/company.htm' />" class="btn-large blue">Entreprise</a>
      </div>
</div>

<%@ include file = "footer.jsp" %>
