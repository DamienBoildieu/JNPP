<%@ include file="../include/jsptags.jsp" %>
<html>
    <head>
        <%@ include file="../include/materialize.jsp" %>
        <title>Gestion des actions</title>
    </head>
    <body>
        <%@ include file="banker_header.jsp" %>
        <div class="container">
            <table class="responsive-table centered striped card">
                <thead>
                    <tr>
                        <th>Numero</th>
                        <th>Client</th>
                        <th>Compte</th>
                        <th>Type</th>
                        <th>Statut</th>
                        <th>Promouvoir</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${paymentmeans}" var="paymentmean">
                    <tr>
                        <td>${paymentmean.id}</td>
                        <td>${paymentmean.login}</td>
                        <td>${paymentmean.rib}</td>
                        <td>${paymentmean.type}</td>
                        <td>${paymentmean.status}</td>
                        <td>
                            <c:if test="${paymentmean.status ne 'DELIVERED'}">
                            <form method="POST" action="/JNPP/banquier/commandes.htm">
                                <input type="hidden" name="id" value="${paymentmean.id}">
                                <button class="btn waves-effect blue" type="submit" name="action">
                                    ${paymentmean.status.next()}
                                    <i class="material-icons right">send</i>
                                </button>
                            </form>
                            </c:if>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>        
        <%@ include file="../include/javascript.jsp"%>
        <%@ include file="../include/commonscripts.jsp"%>
    </body>
</html>