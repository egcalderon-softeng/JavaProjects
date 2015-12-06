<%@ page import="egcsw.salasdeensayo.modelos.Persona" %>
<% Persona persona = (Persona) request.getAttribute("persona"); %>
<html>
<body>
	<h1>Listado de Clientes</h1>
	<table>
		<tr>
			<td>Nombre</td>
			<td>Dni</td>
			<td>Domicilio</td>
		</tr>
		<tr>
			<td><%=persona.getNombre()%></td>
			<td><%=persona.getDni()%></td>
			<td><%=persona.getDomicilio()%></td>
		</tr>
		
	</table>
</body>
</html>