<%@ include file="../include/jsptags.jsp" %>
<html>
    <head>
        <%@ include file="../include/materialize.jsp" %>
        <title>Gestion des indentifiants</title>
    </head>
    <body>
        <%@ include file="banker_header.jsp" %>
        <div class="container">
            <table class="responsive-table centered striped card">
                <thead>
                    <tr>
                        <th>Identifiant</th>
                        <th>Mot de passe</th>
                        <th>Type</th>
                        <th>Nom</th>
                        <th>Email</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${logins}" var="login">
                    <tr>
                        <td>${login.login}</td>
                        <td>${login.password}</td>
                        <td>${login.type}</td>
                        <td>${login.name}</td>
                        <td>${login.email}</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>        
        <%@ include file="../include/javascript.jsp"%>
        <%@ include file="../include/commonscripts.jsp"%>
    </body>
</html>
