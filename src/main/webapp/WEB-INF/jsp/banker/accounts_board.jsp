<%@ include file="../include/jsptags.jsp" %>
<html>
    <head>
        <%@ include file="../include/materialize.jsp" %>
        <title>Gestion des comptes</title>
    </head>
    <body>
        <%@ include file="banker_header.jsp" %>
        <div class="container">
            <table class="responsive-table centered striped card">
                <thead>
                    <tr>
                        <th>RIB</th>
                        <th>Type</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${accounts}" var="account">
                    <tr>
                        <td>${account.rib}</td>
                        <td>${account.type}</td>
                    </tr>
                </c:forEach>
                </tbody>    
            </table>
        </div>        
        <%@ include file="../include/javascript.jsp"%>
        <%@ include file="../include/commonscripts.jsp"%>
    </body>
</html>