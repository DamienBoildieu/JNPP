<%-- 
    Document   : connect
    Created on : 26 sept. 2018, 08:51:25
    Author     : damien
--%>
<%@ include file="include/header.jsp"%>

<div class="container" style="margin-top: 10%">
    <div class="row">
        <div class='card-panel white col s6 offset-s3 center-align'>
            <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                <p class="blue-text flow-text">Connectez-vous</p>
                <form method="POST" action="connect.htm">
                    <div class="row">
                        <div class="col s12">
                            <div class="input-field">
                                <label for="account"> Identifiant </label> <input type="text"
                                                                                  class="validate" name="account" id="account">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12">
                            <div class="input-field">
                                <label for="psswd"> Mot de passe </label> <input type="password"
                                                                                 class="validate" name="password" id="password">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12">
                            <input type="submit" value="Se connecter" class="btn blue" />
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="include/footer.jsp"%>
