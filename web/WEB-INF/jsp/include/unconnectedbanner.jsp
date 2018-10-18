<%-- 
    Document   : unconnectedbanner
    Created on : 28 sept. 2018, 19:44:20
    Author     : damien
--%>

<div class="navbar-fixed">
	<nav>
		<div class="nav-wrapper blue">
			<a href="<c:url value='/index.htm' />" class="brand-logo center">JNPP</a>
			<a href="#" data-target="side" class="sidenav-trigger"><i
				class="material-icons">menu</i></a>
			<ul class="right hide-on-med-and-down">
				<li><a href="<c:url value='/connect.htm' />">Connexion</a></li>
				<li><a href="<c:url value='/signup.htm' />">S'inscrire</a></li>
			</ul>
		</div>
	</nav>
</div>
<ul class="sidenav" id="side">
	<li><a href="<c:url value='/connect.htm' />">Connexion</a></li>
	<li><a href="<c:url value='/signup.htm' />">S'inscrire</a></li>
</ul>
