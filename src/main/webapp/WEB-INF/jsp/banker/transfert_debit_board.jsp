<div>
    <form method="POST" action="/JNPP/banquier/transactions/transfert_debit.htm">
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
                                    <input type="text" name="rib_from" class="center-align">
                                </td>
                                <td>
                                    <input type="text" name="rib_to" class="center-align">
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
                        <button class="btn waves-effect blue" type="submit" name="transfert" value="transfert">Transferer</button> 
                        <div style="margin-bottom: 5px;"></div>
                        <button class="btn waves-effect blue" type="submit" name="debit" value="debit">Debiter</button> 
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>