<%-- 
    Document   : header
    Created on : 26 sept. 2018, 17:58:26
    Author     : damien
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
  "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<!--Import materialize.css-->
<link type="text/css" rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"
	media="screen,projection" />
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link type="text/css" rel="stylesheet"
	href="css/common.css"
	media="screen" />
<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JNPP Banque</title>
</head>
<body>
	<header> <c:choose>
		<c:when test="${isConnected}">
			<%@ include file="connectedbanner.jsp"%>
		</c:when>
		<c:otherwise>
			<%@ include file="unconnectedbanner.jsp"%>
		</c:otherwise>
	</c:choose> </header>
	<main>
            <c:forEach items="${alerts}" var="element">
                <c:choose>
                    <c:when test="${element.alertType == 'ERROR'}">
                        <div id="errorMessage" class='card-panel red lighten-1 white-text left-align' style="font-size: 20px;">
                            ${element.message} <i class="material-icons right">warning</i>
                        </div>
                    </c:when>
                    <c:when test="${element.alertType == 'SUCCESS'}">
                        <div id="successMessage" class='card-panel green lighten-1 white-text left-align' style="font-size: 20px;">
                            ${element.message} <i class="material-icons right">warning</i>
                        </div>
                    </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
