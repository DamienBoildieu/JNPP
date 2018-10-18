<%@ include file="include/header.jsp"%>

<!-- <div class="row">
    <div class="col s12">
      <ul class="tabs">
        <li class="tab col s3"><a href="#test1">Test 1</a></li>
        <li class="tab col s3"><a class="active" href="#test2">Test 2</a></li>
        <li class="tab col s3 disabled"><a href="#test3">Disabled Tab</a></li>
        <li class="tab col s3"><a href="#test4">Test 4</a></li>
      </ul>
    </div>
    <div id="test1" class="col s12">Test 1</div>
    <div id="test2" class="col s12">Test 2</div>
    <div id="test3" class="col s12">Test 3</div>
    <div id="test4" class="col s12">Test 4</div>
  </div>	-->
<div class="container valign-wrapper" style="height: 75vh;">
	<div class="row">
		<form method="POST" action="personalsignup.htm">
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
						<input type="submit" value="S'inscrire" class="btn blue" />
					</div>
				</div>
			</div>
		</form>
	</div>
</div>

<%@ include file="include/footer.jsp"%>
