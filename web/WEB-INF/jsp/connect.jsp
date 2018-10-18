<%-- 
    Document   : connect
    Created on : 26 sept. 2018, 08:51:25
    Author     : damien
--%>
<%@ include file="include/header.jsp"%>

<div class="container valign-wrapper" style="height: 75vh;">
	<div class="row">
		<form method="POST" action="connect.htm">
			<div class="col s12">
				<div class="row">
					<div class="col s12">
						<div class="input-field inline">
							<label for="account"> Identifiant </label> <input type="text"
								class="validate" name="account" id="account">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col s12">
						<div class="input-field inline">
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
			</div>
		</form>
	</div>
</div>

<%@ include file="include/footer.jsp"%>
