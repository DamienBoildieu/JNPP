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
                            <a class="btn blue" href="<c:url value='/movement.htm' />">Faire une transaction</a>
                        </div>
                        <div style="margin-bottom: 5px;"></div>
                        <form method="POST" action="closeaccount.htm">
                            <input type="hidden" id="rib" name="rib" value=${account.rib}>
                            <div class="row">
                                <div class="col s12">
                                    <input type="submit" value="Fermer le compte" class="btn blue" />
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="card-panel blue">
                        <div clas="row">
                            <p class="white-text"> Cartes : </p>
                        </div>
                        <c:forEach items="${cards}" var="card">
                            <div clas="row">
                                <p class="white-text"> ${card.id} ${statusMap[card.status]} </p>
                            </div>
                        </c:forEach>
                        <form method="POST" action="commandcard.htm">
                            <input type="hidden" id="rib" name="rib" value=${account.rib}>
                            <div class="row">
                                <div class="col s12">
                                    <input type="submit" value="Commander" class="btn blue" />
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="card-panel blue">
                        <div clas="row">
                            <p class="white-text"> Chéquiers : </p>
                        </div>
                        <c:forEach items="${checkbooks}" var="checkbook">
                            <div clas="row">
                                <p class="white-text"> ${checkbook.id} ${statusMap[checkbook.status]} </p>
                            </div>
                        </c:forEach>
                        <form method="POST" action="commandcheckbook.htm">
                            <input type="hidden" id="rib" name="rib" value=${account.rib}>
                            <div class="row">
                                <div class="col s12">
                                    <input type="submit" value="Commander" class="btn blue" />
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="container">
                    <div class="row">
                        <div class='card blue col s10 offset-s1 center-align'>
                            <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                                 <div class="card-content white-text">
                                    <span class="card-title center-align">${account.rib}</span>
                                    <div class="row">
                                        <div class="col s12">
                                            <p>
                                                ${accountsMap[account.type]}                                                   
                                            </p>
                                        </div>
                                    </div>
                                <div class="card white s12">
                                        <c:choose>
                                            <c:when test="${account.money>0}">
                                                <h4 class="green-text">
                                            </c:when>
                                            <c:otherwise>
                                                <h4 class="red-text">
                                            </c:otherwise>
                                        </c:choose>
                                        ${account.money}${currencyMap[account.currency]} </h4>
                                    </div>
                                    <table>
                                        <tbody>
                                            <c:forEach items="${movements}" var="movement">
                                                <tr>
                                                    <td>
                                                        ${movement.day}/${movement.month}/${movement.year}
                                                    </td>
                                                    <td>
                                                        ${movement.otherAccount}
                                                    </td>
                                                    <td>
                                                        ${movement.label}
                                                    </td>
                                                    <td>
                                                        ${movement.value}
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                 </div>
                            </div>
                        </div>
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