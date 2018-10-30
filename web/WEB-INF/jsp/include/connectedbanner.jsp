<div class="navbar-fixed">
    <nav>
        <div class="nav-wrapper blue">
            <a href="<c:url value='/index.htm' />" class="brand-logo center">JNPP</a>
            <a href="#" data-target="side" class="sidenav-trigger"><i
                    class="material-icons">menu</i></a>
            <div class="right">
                ${info.userName}
            </div>
            <ul class="left hide-on-med-and-down">
                <li><a href="<c:url value='/home.htm' />">Accueil</a></li>
                                    <li><a href="<c:url value='/resume.htm' />">Mes comptes</a></li>

            </ul>
            <ul class="right hide-on-med-and-down">
                <li><a href="<c:url value='/userinfo.htm' />">Mes informations</a></li>
                <li><a href="<c:url value='/advisor.htm' />">Mon conseiller</a></li>
                <li><a href="<c:url value='/disconnect.htm' />">Déconnexion</a></li>
            </ul>
        </div>
    </nav>
</div>
<ul class="sidenav" id="side">
    <li><a href="<c:url value='/home.htm' />">Accueil</a></li>
    <li><a href="<c:url value='/resume.htm' />">Mes comptes</a></li>
    <li><a href="<c:url value='/userinfo.htm' />">Mes informations</a></li>
    <li><a href="<c:url value='/advisor.htm' />">Mon conseiller</a></li>
    <li><a href="<c:url value='/disconnect.htm' />">Déconnexion</a></li>
</ul>
