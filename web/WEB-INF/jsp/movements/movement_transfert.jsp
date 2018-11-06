<div>
    <form method="POST" action="transfert.htm" name="transfert">
        <div class="card">
            <div class="row valign-wrapper">
                <div class="col s10">
                    <table class="centered striped">
                        <thead>
                            <tr>
                                <th>RIB orgine</th>
                                <th>RIB destination</th>
                                <th>Montant</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <select name="ribFrom" form="transfert">
                                        <c:forEach items="${ribMoneyAccount}" var="rib">
                                            <option value="${rib}" selected>${rib}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="ribTo" class="center-align">
                                </td>
                                <td>
                                    <input type="number" name="amount" class="center-align">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">
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
                        <button class="btn waves-effect blue" type="submit">Transferer</button> 
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>