<c:choose>
	<c:when test="${isConnected}">
		<%@ include file="connectedbanner.jsp"%>
	</c:when>
	<c:otherwise>
		<%@ include file="unconnectedbanner.jsp"%>
	</c:otherwise>
</c:choose>
