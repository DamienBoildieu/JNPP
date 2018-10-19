<%-- 
    Document   : resume
    Created on : 26 sept. 2018, 16:53:08
    Author     : damien
--%>

<%@ include file="include/header.jsp"%>
<div class="container">
    <div class="row">
        <div class='col s10 offset-s1 center-align'>
            <c:forEach items="${listAccounts}" var="element">
                <div class="col s12 m4">
                    <div class="card blue">
                        <div class="card-content white-text">
                            <span class="card-title center-align">${element.ref}</span>
                            <div class="row">
                                <div class="col s12">
                                    <p>${element.type}</p>
                                </div>
                            </div>
                            <div class="card white s12">
                                <c:choose>
                                    <c:when test="${element.amount>0}">
                                        <h4 class="green-text">${element.amount}$</h4>
                                    </c:when>
                                    <c:otherwise>
                                        <h4 class="red-text">${element.amount}$</h4>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="row">
                                <div class="col s12">
                                    <a class="white-text hoverable" href="<c:url value='/account.htm' />"><b>Détails</b></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<%@ include file="include/footer.jsp"%>
