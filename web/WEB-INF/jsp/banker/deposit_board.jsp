<form method="POST" action="/JNPP/banquier/transactions/depot.htm">
    <div>
        <div class="row valign-wrapper">
            <div class="col s10">
                <table class="centered striped">
                    <thead>
                        <tr>
                            <th>RIB</th>
                            <th>Montant</th>
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
                    <button class="btn waves-effect waves-light" type="submit">Deposer</button> 
                </div>
            </div>
        </div>
    </div>
</form>
