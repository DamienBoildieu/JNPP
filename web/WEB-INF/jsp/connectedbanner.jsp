<%-- 
    Document   : connectedbanner
    Created on : 28 sept. 2018, 19:44:31
    Author     : damien
--%>
<div class="navbar-fixed">
  <nav>
    <div class="nav-wrapper blue">
      <a href="<c:url value='/index.htm' />" class="brand-logo center">JNPP</a>
      <a href="#" data-trigger="side" class="sidenav-trigger"><i class="material-icons">menu</i></a>
      <ul class="right hide-on-med-and-down">
        <li>
          ${firstName} ${lastName}
        </li>
      </ul>
    </div>
  </nav>
</div>
<ul class="sidenav" id="side">
  <li>
    ${firstName} ${lastName}
  </li>
</ul>
