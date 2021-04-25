<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import = "com.cs336.user.User" import = "java.util.Date" import = "java.text.SimpleDateFormat"
    import = "java.util.Calendar"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css" />
<meta charset="ISO-8859-1">
<title>Create Auction</title>
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container"><a href="Home.jsp"><img src="auctionlogo.png"></a></div>
<% if(currentUser == null) response.sendRedirect("Login.jsp");%>

<%
	String ERROR = (String) session.getAttribute("ERROR");
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	Calendar cal = Calendar.getInstance();
	Date date = new Date();
	Date fillerStartDate = new Date();
	Date fillercloseDate = new Date();
	String today = formatter.format(date);
%>
	<% if(ERROR != null) { %>
		<div style="color:red;"> <% out.println(ERROR); %> </div>
	<% session.removeAttribute("ERROR"); } %>
	
	<div>
		<form method = "post" action = "<%=request.getContextPath()%>/createAuction">
		<input type="hidden" name="username" value="${currentUser.getUsername()}" /></input>
		<table>
			<tr>
				<td>Item Name: <input type="text" name="itemName" step="0.01" size="25" maxlength="20" required></td>
			</tr>
			<tr>
				<td>
				<label for="technologyCategories">Category: </label>
  				<select name="subcat" id="technologyCategories">
    				<option value="Desktop Computers">Desktop Computers</option>
    				<option value="Laptops">Laptops</option>
    				<option value="Handhelds">Handhelds</option>
    				<option value="Mobile Phones">Mobile Phones</option>
    				<option value="Accessories">Accessories</option>
 				 </select>
 				</td>	 
			</tr>
			<tr>    
				<td><label for="starttime">Start Date:</label>
				<input type="datetime-local" id="starttime" placeholder="yyyy-MM-dd'T'HH:mm" value = <%= today  %> name=starttime maxlength="17" size="20"></td>
			</tr>
			<tr>
				<td><label for="closetime">Close Date:</label>
				<input type="datetime-local" id="closetime" placeholder="yyyy-MM-dd'T'HH:mm" value = <%= today  %> name="closetime" maxlength="17" size="20"></td>
			</tr>
			<tr>
				<td>Starting Price: $<input type="number" name="initialPrice" placeholder="0.00" step="0.01" size="10" maxlength="7" required></td>
			</tr>
			<tr>
				<td>Hidden Minimum Price (Optional, 0 IF NONE): $<input type="number" value = "0.00" step="0.01" name="hidden_min_price" placeholder="0.00" step="0.01" size="10" maxlength="7"></td>
			</tr>
			<tr>
				<td><textarea name="description" cols="50" rows="10" placeholder="Description... (Optional)"></textarea></td>
			</tr>
			
		</table>
		
		<input type = "submit" value = "Post Auction" width="50" height="50">
		</form>
	
	</div>
	
</body>
</html>