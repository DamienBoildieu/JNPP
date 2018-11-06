<%@ include file="../include/jsptags.jsp"%>
<html>
    <head>
        <%@ include file="../include/head.jsp"%>
    </head>
    <body>
        <header>
            <%@ include file="../include/banner.jsp"%>
        </header>
        <main>
            <%@ include file="../include/alerts.jsp"%>
            <div class="row">
            <div class="col s2 center-align">
                <div class="card-panel white">
                    <div clas="row">
                        <a class="btn blue" href="<c:url value='/openaccount.htm' />">Ouvrir un compte</a>
                    </div>
                    <div style="margin-bottom: 5px;"></div>
                    <div clas="row">
                        <a class="btn blue" href="<c:url value='/transaction.htm' />">Faire une transaction</a>
                    </div>
                </div>
            </div>
            <div class="container">              
                     <div class='col s10 offset-s1 center-align'>
                        <c:forEach items="${accounts}" var="element">
                            <div class="col s12 m4">
                                <div class="card blue">
                                    <div class="card-content white-text">
                                        <span class="card-title center-align">${element.rib}</span>
                                        <div class="row">
                                            <div class="col s12">
                                                <p>
                                                <c:choose>
                                                    <c:when test="${element.type == 'SAVING'}">
                                                       ${element.getSavingBook().getName()}
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${accountsMap[element.type]}                                                   
                                                    </c:otherwise>
                                                </c:choose>
                                                </p>
                                            </div>
                                        </div>
                                        <c:if test="${!(element.type == 'SHARE')}">
                                            <div class="card white s12">
                                                <c:choose>
                                                    <c:when test="${element.money>0}">
                                                        <h4 class="green-text">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <h4 class="red-text">
                                                    </c:otherwise>
                                                </c:choose>
                                                ${element.money}${currencyMap[element.currency]} </h4>
                                            </div>
                                        </c:if>                                        
                                        <div class="row">
                                            <div class="col s12">
                                                <a class="white-text hoverable" href="<c:url value='/account.htm' />?id=${element.rib}"><b>Détails</b></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <%@ include file="../include/notif.jsp"%>
        </main>
        <footer>
        </footer>
        <%@ include file="../include/javascript.jsp"%>
        <%@ include file="../include/commonscripts.jsp"%>
    </body>
</html>