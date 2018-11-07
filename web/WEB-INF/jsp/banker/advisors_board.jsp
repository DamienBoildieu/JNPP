<%@ include file="../include/jsptags.jsp" %>
<html>
    <head>
        <%@ include file="../include/materialize.jsp" %>
        <title>Gestion des conseillers</title>
    </head>
    <body>
        <%@ include file="banker_header.jsp" %>
        <div class="container">
            <table class="responsive-table centered striped card">
                <thead>
                    <tr>
                        <th>Nom</th>
                        <th>Prenom</th>
                        <th>Email</th>
                        <th>Telephone</th>
                        <th>Bureau</th>
                        <th>Clients</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${advisors}" var="advisor">
                    <tr>
                        <td>${advisor.identity.lastname}</td>
                        <td>${advisor.identity.firstname}</td>
                        <td>${advisor.email}</td>
                        <td>${advisor.phone}</td>
                        <td>${advisor.officeAddress}</td>
                        <td><a class="waves-effect blue btn" 
                               href="<c:url value='/banquier/conseiller/clients.htm' />?nom=${advisor.identity.lastname}&prenom=${advisor.identity.firstname}">Clients</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="container">
            <form method="POST" action="/JNPP/banquier/conseillers.htm" class="card">
                <table class="responsive-table centered striped card">
                    <tbody>
                        <tr>
                            <td><input type="text" name="lastname" class="center-align"></td>
                            <td><input type="text" name="firstname" class="center-align"></td>
                            <td><input type="text" name="email" value="${default_advisor.email}" class="center-align"></td>
                            <td><input type="text" name="phone" value="${default_advisor.phone}" class="center-align"></td>
                            <td><input type="text" name="office_address" value='${default_advisor.officeAddress}' class="center-align"></td>
                        </tr>
                        <tr>
                            <td colspan="5"><input type="submit" value="Ajouter" class="waves-effect blue btn-small"></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </body>
</html>