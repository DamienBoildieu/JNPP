<%-- 
    Document   : resume
    Created on : 26 sept. 2018, 16:53:08
    Author     : damien
--%>

<%@ include file="include/header.jsp"%>
<div class="row">
    <c:forEach items="${listAccounts}" var="element">
        <div class="col s12 m4">
            <div class="card blue">
                <div class="card-content white-text">
                    <span class="card-title center-align">${element.ref}</span>
                    <p class="center-align">${element.type}</p>
                    <p class="center-align">${element.amount}$</p>
                    <div class="card-action center-align">
                        <a class="white-text" href="<c:url value='/account.htm' />">Détails <i class="material-icons"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<%@ include file="include/footer.jsp"%>
