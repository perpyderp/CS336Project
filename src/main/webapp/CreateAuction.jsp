<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import = "com.cs336.user.User" import = "java.util.Date" import = "java.text.SimpleDateFormat"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css" />
<meta charset="ISO-8859-1">
<title>Create Auction</title>
</head>
<body>
<%
	User currentUser = (User) session.getAttribute("currentUser");
	String ERROR = (String) session.getAttribute("ERROR");
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	Date date = new Date();
	String today = formatter.format(date);
%>
	<div class="headerbar">
	<div class="headerbar-right">
	<% if(currentUser == null) { 
		response.sendRedirect("Login.jsp");
	} 
  	else { %>
		<a href="CreateAuction.jsp">Create Auction</a>
  			<div class="dropdown">
    		<button class="dropbtn">User: <% out.print(currentUser.getUsername()); %> 
      			<i class="fa fa-caret-down"></i>
    		</button>
    		<div class="dropdown-content">
    			<a href="Account.jsp">My Account</a>
      			<a href="${pageContext.request.contextPath}/logout">Logout</a>
      		</div>
      		</div>
  	<% } %>
  	</div>
  	</div>
  	<div class="container">
		<a href="Home.jsp"><img src="auctionlogo.png"></a>
		
	</div>
	<% if(ERROR != null) { %>
		<div style="color:red;"> <% out.println(ERROR); %> </div>
	<% session.removeAttribute("ERROR"); } %>
	
	<div>
		<form method = "post" action = "<%=request.getContextPath()%>/createAuction">
		<input type="hidden" name="username" value="${currentUser.getUsername()}" />
		<table>
			<tr>
				<td>Item Name: <input type="text" name="itemName" step="0.01" size="25" maxlength="20" required></td>
			</tr>
			<tr>
			<label for = "categoryButtons">
				Choose a category:
				<label for="technology">
    				<input type="radio" id="technology" name="category" onclick="ShowHideDiv()" />
    				Technology
				</label>
				<label for="fashion">
    				<input type="radio" id="fashion" name="category" onclick="ShowHideDiv()" />
    				Fashion
				</label>
				<label for="collectibles">
    				<input type="radio" id="collectibles" name="category" onclick="ShowHideDiv()" />
    				Collectibles
				</label>
				<label for="sporting">
    				<input type="radio" id="sporting" name="category" onclick="ShowHideDiv()" />
    				Sporting Goods
				</label>
			</label>
    				<div id="technologyCategories" style="display: none">
					<label for="technologyCategories">Sub-category:</label>
  					<select name="technologyCategories" id="subcat">
    					<option value="Phone, Watches, & Accessories">Phone, Watches, & Accessories</option>
    					<option value="Computers & Laptops">Computers & Laptops</option>
    					<option value="TV, Cables, & Speakers">TV, Cables, & Speakers</option>
 				 	</select>
				</div>
				<div id="fashionCategories" style="display: none">
					<label for="fashionCategories">Sub-category:</label>
  					<select name="fashionCategories" id="subcat">
    					<option value="Men's Clothing">Men's Clothing</option>
    					<option value="Women's Clothing">Women's Clothing</option>
    					<option value="Accessories">Accessories</option>
 				 	</select>
				</div>
    				<div id="collectiblesCategories" style="display: none">
					<label for="collectiblesCategories">Sub-category:</label>
  					<select name="collectiblesCategories" id="subcat">
    					<option value="Pottery">Pottery</option>
    					<option value="Antiques">Antiques</option>
    					<option value="Pokemon Cards">Pokemon Cards</option>
 				 	</select>
				</div>
    				<div id="sportingCategories" style="display: none">
					<label for="sportingCategories">Sub-category:</label>
  					<select name="sportingCategories" id="subcat">
    					<option value="Football">Football</option>
    					<option value="Fishing">Fishing</option>
    					<option value="Fitness">Fitness Gear</option>
 				 	</select>
				</div>
			</tr>
			<tr>    
				<td><label for="starttime">Start Date:</label>
				<input type="datetime-local" id="starttime" placeholder="yyyy-MM-dd'T'HH:mm" value = <%= today  %> name=starttime maxlength="17" size="20"></td>
			</tr>
			<tr>
				<td><label for="closetime">Close Date:</label>
				<input type="datetime-local" id="closetime" placeholder="yyyy-MM-dd'T'HH:mm" value = "" name="closetime" maxlength="17" size="20"></td>
			</tr>
			<tr>
				<td>Starting Price: $<input type="number" name="initialPrice" placeholder="0.00" step="0.01" size="10" maxlength="7" required></td>
			</tr>
			<tr>
				<td>Minimum Increment Bid: $<input type="number" name="min_Increment" placeholder="0.00" step="0.01" size="10" maxlength="7" required></td>
			</tr>
			<tr>
				<td>Hidden Minimum Bid (Optional): $<input type="number" name="hidden_min_Increment" placeholder="0.00" step="0.01" size="10" maxlength="7"></td>
			</tr>
			<tr>
				<td><textarea name="description" cols="50" rows="10" placeholder="Description... (Optional)"></textarea></td>
			</tr>
		</table>
		
		<input type = "submit" value = "Post Auction" width="50" height="50">
		</form>
	
	</div>
	
	<script>
		function ShowHideDiv() {
        	var tech = document.getElementById("technology");
        	var fash = document.getElementById("fashion");
        	var collect = document.getElementById("collectibles");
        	var sport = document.getElementById("sporting");
        	var technologyCategories = document.getElementById("technologyCategories");
        	var fashionCategories = document.getElementById("fashionCategories");
        	var collectiblesCategories = document.getElementById("collectiblesCategories");
        	var sportingCategories = document.getElementById("sportingCategories");
        	
        	technologyCategories.style.display = tech.checked ? "block" : "none";
        	fashionCategories.style.display = fash.checked ? "block" : "none";
        	collectiblesCategories.style.display = collect.checked ? "block" : "none";
        	sportingCategories.style.display = sport.checked ? "block" : "none";
    	}
	</script>
	
</body>
</html>