<%@ include file="include/header.jsp"%>

<div class="container" style="margin-top: 10%">
    <div class="row">
        <div class='card-panel white col s6 offset-s3 center-align'>
            <div class='container' style="margin-bottom: 40px; margin-top: 40px;">
                <h3>Inscrivez-vous !</h3>
                <!--<div class='card-panel red lighten-1 white-text left-align' style="font-size: 20px;">Mot de passe erroné <i class="material-icons right">warning</i></div>-->
                <form method="POST" action="personalsignup.htm">
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
                            <input type="submit" value="S'isncrire" class="btn blue" />
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="include/footer.jsp"%>
