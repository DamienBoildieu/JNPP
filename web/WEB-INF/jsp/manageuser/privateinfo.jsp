<%@ include file="../include/jsptags.jsp"%>
<html>
    <head>
        <%@ include file="../include/head.jsp"%>
    </head>
    <body>
        <header>
            <%@ include file="../include/banner.jsp"%>
        </header>
        <main>
            <%@ include file="../include/alerts.jsp"%>
            <div class="container">
                <div class="row">
                    <div class='card-panel white col s5 offset-s1 center-align'>
                        <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                            <form method="POST" action="editinfo.htm">
                                <div class="row">
                                    <div class="col s6">
                                        <div class="row">
                                            <div class="input-field col s12">            
                                                <label>Nom</label>
                                                <input type="text" value="${client.getIdentity().getLastname()}" readonly>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="input-field col s12">            
                                                <label>Prénom</label>
                                                <input type="text" value="${client.getIdentity().getFirstname()}" readonly>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="input-field col s12">
                                                <label>Sexe</label>
                                                <input type="text" value="${gendersMap[client.getIdentity().getGender()]}" readonly>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col s12">     
                                                <label>Date de naissance</label> <input type="text" value="${birthday}" readonly>
                                            </div>
                                        </div>                                
                                        <div class="row">
                                            <div class="input-field col s12">
                                                <label for="email">Email</label> <input type="email" value="${client.email}"
                                                                                            class="validate" name="email" id="email" required>
                                            </div>
                                        </div>                                                   
                                    </div>
                                    <div class="col s6">
                                        <div class="row">
                                            <div class="input-field col s12">
                                                <label for="streetNbr">Numéro de rue</label> <input type="text" value="${client.getAddress().getNumber()}"
                                                                                                    class="validate" name="streetNbr" id="streetNbr" 
                                                                                                    pattern="[1-9][0-9]*" required>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="input-field col s12">
                                                <label for="street">Rue</label> <input type="text" value="${client.getAddress().getStreet()}"
                                                                                        class="validate" name="street" id="street" required>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="input-field col s12">
                                                <label for="city">Ville</label> <input type="text" value="${client.getAddress().getCity()}"
                                                                                        class="validate" name="city" id="city" required>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="input-field col s12">
                                                <label for="country">Pays</label> <input type="text" value="${client.getAddress().getState()}"
                                                                                        class="validate" name="country" id="country" required>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="input-field col s12">
                                                <label for="phone">Téléphone</label> <input type="tel" value="${client.phone}"
                                                                                            class="validate" name="phone" id="phone" 
                                                                                            pattern="[0-9]{10}" required>
                                            </div>
                                        </div>   
                                    </div>
                                </div>
                                                  
                                <div class="row">
                                    <div class="col s12">
                                        <input type="submit" value="Changer mes informations" class="btn blue" />
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class='card-panel white col s5 offset-s1 center-align' style="margin-top: 10%">
                        <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                            <form method="POST" action="changeprivatepassword.htm">
                                <div class="row">
                                    <div class="col s12">
                                        <div class="input-field">
                                            <label for="oldpsswd"> Ancien mot de passe </label> <input type="password"
                                                                                             class="validate" name="oldpsswd" id="oldpsswd">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12">
                                        <div class="input-field">
                                            <label for="newpsswd"> Nouveau mot de passe </label> <input type="password"
                                                                                             class="validate" name="newpsswd" id="newpsswd">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12">
                                        <div class="input-field">
                                            <label for="confirmpsswd"> Confirmer le nouveau mot de passe </label> <input type="password"
                                                                                             class="validate" name="confirmpsswd" id="confirmpsswd">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12">
                                        <input type="submit" value="Changer mon mot de passe" class="btn blue" />
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                            <form method="POST" action="closeuser.htm">
                                <div class="row">
                                    <div class="col s12">
                                        <input type="submit" value="Supprimer votre compte" class="btn blue" />
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <%@ include file="../include/notif.jsp"%>
        </main>
        <footer>
        </footer>
        <%@ include file="../include/javascript.jsp"%>
        <%@ include file="../include/commonscripts.jsp"%>
    </body>
</html>