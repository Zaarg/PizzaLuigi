<%@ page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>

<!doctype html>
<html lang='nl'>
<head>
<c:import url="/WEB-INF/JSP/head.jsp">
	<c:param name='title' value="Pizza's" />
</c:import>
</head>
<body>
	<vdab:menu />
	<h1>
		Pizza's
		<c:forEach begin='1' end='5'>
    	&#9733; <%-- de HTML code van een ster --%>
		</c:forEach>
	</h1>
	<ul class='zebra'>
		<c:forEach var='pizza' items='${pizzas}'>
			<li>${pizza.id}: <c:out value='${pizza.naam}' />&nbsp;${pizza.prijs}&euro;
				<c:choose>
					<c:when test='${pizza.pikant}'>
			  pikant
			</c:when>
					<c:otherwise>
			  niet pikant
			</c:otherwise>
				</c:choose> <c:url value='/pizzas/detail.htm' var='detailURL'>
					<c:param name='id' value='${pizza.id}' />
				</c:url> <a href='${detailURL}'>Detail</a> <c:if
					test='${pizzaIdsMetFoto.contains(pizza.id)}'>
					<c:url value='/pizzafotos/${pizza.id}.jpg' var='fotoURL' />
					<a href='${fotoURL}'>Foto</a>
				</c:if>
			</li>
		</c:forEach>
	</ul>
	<script>
  	document.getElementById('toevoegform').onsubmit = function() {
    document.getElementById('toevoegknop').disabled = true;
  	};
	</script>
</body>
</html>
