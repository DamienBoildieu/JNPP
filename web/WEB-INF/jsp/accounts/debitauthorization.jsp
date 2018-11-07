<div class="container">
    <table class="responsive-table centered striped card">
        <thead>
            <tr>
                <th>Mes comptes</th>
                <th>Comptes autorisés</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${authorizations}" var="authorization">
            <tr>
                <td>${authorization.ribFrom}</td>
                <td>${authorization.ribTo}</td>
                <td>
                    <form method="POST" action="deleteDebitAuthorization.htm" name="deleteDebitAuthorization">
                        <input type="hidden" name="ribFrom" value="${authorization.ribFrom}">
                        <input type="hidden" name="ribTo" value="${authorization.ribTo}">
                        <button class="btn waves-effect blue" type="submit" name="deleteDebitAuthorization">
                            Supprimer
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="container">
    <form method="POST" action="addDebitAuthorization.htm" class="card" name="addDebitAuthorization">
        <table class="responsive-table centered striped card">
            <tbody>
                <tr>
                    <td>                              
                        <select name="ribFrom" form="addDebitAuthorization" required>
                            <c:forEach items="${ribs}" var="rib">
                                <option value="${rib}" selected>${rib}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input type="text" name="ribTo" class="center-align"></td>
                    <td>                        
                        <button class="btn waves-effect blue" type="submit" name="addDebitAuthorization">
                            Ajouter
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>