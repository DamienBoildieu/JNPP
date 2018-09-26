<%-- 
    Document   : connect
    Created on : 26 sept. 2018, 08:51:25
    Author     : damien
--%>
<%@ include file = "header.jsp" %>

<div class="container valign-wrapper" style="height:100vh;">
  <div class="row">
    <form method="post" action="connected.htm">
      <div class="col s6">
        <input type="submit" value="Se connecter" class="btn blue" />
      </div>
          <div class="col s6">
            <div class="row">
              <div class="col s12">
                <div class="input-field inline"> 
                  <label for="account">
                    Identifiant
                  </label>
                  <input type="text" class="validate" name="account" id="account">
                </div>
              </div>    
            </div>
            <div class="row">
              <div class="col s12">
                <div class="input-field inline"> 
                  <label for="psswd">
                    Mot de passe
                  </label>
                  <input type="password" class="validate" name="password" id="password">
                </div>
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                  <select id="accountType">
                    <option value="0">Particulier</option>
                    <option value="1">Professionnel</option>
                  </select>
              </div>    
            </div>
      </div>
  </form>
  </div>
</div>
        
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script>
  $(document).ready(function(){
    $('select').formSelect();
  });
</script>
    
<%@ include file = "footer.jsp" %>
