<%@ include file="../include/jsptags.jsp" %>
<html>
    <head>
        <%@ include file="../include/materialize.jsp" %>
        <title>Gestion des conseillers</title>
    </head>
    <body>
        <%@ include file="banker_header.jsp" %>
        <div class="container">
            <h1 align="center" class="card">${advisor_firstname} ${advisor_lastname}</h1>
        </div>
        <div class="container">
            <table class="responsive-table centered striped card">
                <thead>
                    <tr>
                        <th>Nom</th>
                        <th>Email</th>
                        <th>Messages</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${clients}" var="client">
                    <tr>
                        <td>${client.name}</td>
                        <td>${client.email}</td>
                        <td><a class="waves-effect blue btn" 
                               href="<c:url value='/banquier/conseiller/client/messages.htm' />?login=${client.login}">Messages</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
