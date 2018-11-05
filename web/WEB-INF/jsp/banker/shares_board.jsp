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
                        <th>Nom</th>
                        <th>Valeur</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${shares}" var="share">
                    <tr>
                        <td>${share.name}</td>
                        <td>${share.value}</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="container">
            <form method="POST" action="/JNPP/banquier/actions.htm" class="card">
                <table class="responsive-table centered striped card">
                    <tbody>
                        <tr>
                            <td><input type="text" name="name" class="center-align"></td>
                            <td><input type="number" step=any name="value" class="center-align"></td>
                        </tr>
                        <tr>
                            <td colspan="2"><input type="submit" value="Ajouter" class="waves-effect waves-light btn-small"></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </body>
</html>