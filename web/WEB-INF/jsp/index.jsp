<%--
<%@ include file = "header.jsp" %>

<%@ include file = "footer.jsp" %>
--%><%-- 
    Document   : header
    Created on : 26 sept. 2018, 17:58:26
    Author     : damien
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"  media="screen,projection">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JNPP Banque</title>
  </head>
  
  <body>
    <header>
      <div class="navbar-fixed">
        <nav>
          <div class="nav-wrapper blue">
            <a href="<c:url value='/index.htm' />" class="brand-logo center">JNPP</a>
            <a href="#" data-trigger="side" class="sidenav-trigger"><i class="material-icons">menu</i></a>
            <ul class="right hide-on-med-and-down">
              <li>
                <a href="<c:url value='/connect.htm' />">Connexion</a>
              </li>
              <li>
                <a href="<c:url value='/signup.htm' />">S'inscrire</a>
              </li>
            </ul>
          </div>
        </nav>
      </div>
      
      <ul class="sidenav" id="side">
        <li>
          <a href="<c:url value='/connect.htm' />">Connexion</a>
        </li>
        <li>
          <a href="<c:url value='/signup.htm' />">S'inscrire</a>
        </li>
      </ul>
    </header>
    
    <main>
    </main>
    
    <footer>
    </footer>
  
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
    <script>
      document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('.sidenav');
        var instances = M.Sidenav.init(elems, options);
      });
    </script>
  </body>
</html>
