<div>
    <form method="POST" action="purchase.htm" id="purchase">
        <div class="card">
            <div class="row valign-wrapper">
                <div class="col s10">
                    <table class="centered striped">
                        <thead>
                            <tr>
                                <th>Quantite</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <input type="number" name="amount" class="center-align">
                                </td>
                                <td>
                                    <select name="share" form="purchase">
                                        <c:forEach items="${sharesToPurchase}" var="share">
                                            <option value="${share}" selected>${share}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <div class="row valign-wrapper">
                                        <div class="col s1">
                                            Libelle:
                                        </div>
                                        <div class="col s11">
                                            <input type="text" name="label" class="right-align">
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col s2">
                    <div class="center-align">
                        <button class="btn waves-effect blue" type="submit" name="purchase" value="purchase">Acheter</button> 
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>