<%-- 
    Document   : connect
    Created on : 26 sept. 2018, 08:51:25
    Author     : damien
--%>
<%@ include file = "header.jsp" %>
<form method="post" action="connected.htm">
  <div class="container">
    <div class="row">
      <div class="col">
        <input type="submit" value="Se connecter" class="btn btn-primary" />
      </div>
      <div class="col">
        <div class="form-group row"> 
          <label for="account" class="col-5 col-form-label" placeholder="Identifiant">
            Identifiant
          </label>
          <div class="col">
            <input type="text" class="form-control" name="account" id="account" placehodler="Identifiant">
          </div>
        </div>
        <div class="form-group row"> 
          <label for="psswd" class="col-5 col-form-label">
            Mot de passe
          </label>
          <div class="col">
            <input type="passwrd" class="form-control" name="password" id="password" placeholder="Mot de passe">
          </div>
        </div>
        <select class="form-control" name="password" id="accountType">
          <option value="0">Particulier</option>
          <option value="1">Professionnel</option>
        </select>
      </div>
    </div>
  </div>
</form>
<%@ include file = "footer.jsp" %>
