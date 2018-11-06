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
                        <th>Taux %</th>
                        <th>Calculs par année</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${savingbooks}" var="savingbook">
                    <tr>
                        <td>${savingbook.name}</td>
                        <td>${savingbook.moneyRate}</td>
                        <td>${savingbook.timeRate}</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="container">
            <form method="POST" action="/JNPP/banquier/livrets.htm" class="card">
                <table class="responsive-table centered striped card">
                    <tbody>
                        <tr>
                            <td><input type="text" name="name" class="center-align"></td>
                            <td><input type="number" step=any name="money_rate" class="center-align"></td>
                            <td><input type="number" step=any name="time_rate" class="center-align"></td>
                        </tr>
                        <tr>
                            <td colspan="3"><input type="submit" value="Ajouter" class="waves-effect waves-light btn-small"></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </body>
</html>