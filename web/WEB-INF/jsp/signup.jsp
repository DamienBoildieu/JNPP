<%-- 
    Document   : signup
    Created on : 27 sept. 2018, 17:35:46
    Author     : damien
--%>

<%@ include file="include/header.jsp"%>
<div class="container valign-wrapper" style="height: 75vh;">
	<div class="row">
		<div class="col s6">
			<a href="<c:url value='/personalsignup.htm' />"
				class="btn-large blue">Particulier</a>
		</div>
		<div class="col s6">
			<a href="<c:url value='/company.htm' />" class="btn-large blue">Entreprise</a>
		</div>
	</div>

	<%@ include file="include/footer.jsp"%>