<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <form method="POST" action="/JNPP/banquier/transactions/achat_vente.htm" id="purchase_sale">
        <div class="card">
            <div class="row valign-wrapper">
                <div class="col s10">
                    <table class="centered striped">
                        <thead>
                            <tr>
                                <th>RIB</th>
                                <th>Quantite</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <input type="text" name="rib" class="center-align">
                                </td>
                                <td>
                                    <input type="number" name="amount" class="center-align">
                                </td>
                                <td>
                                    <select name="share" form="achat_vente">
                                        <c:forEach items="${shares}" var="share">
                                        <option value="${share.name}" selected>${share.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col s2">
                    <div class="center-align">
                        <button class="btn waves-effect waves-light" type="submit" name="purchase" value="purchase">Acheter</button> 
                        <div style="margin-bottom: 5px;"></div>
                        <button class="btn waves-effect waves-light" type="submit" name="sale" value="sale">Vendre</button> 
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0-alpha1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/select.js"></script>