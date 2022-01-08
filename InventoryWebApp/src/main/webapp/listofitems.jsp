<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 5px;
            text-align: left;
        }
    </style>
</head>
<body>
<h2>Inventory Categories</h2>
<p>To add a new category, use the form at the bottom.</p>
<p>Always Refresh the page before adding a new item</p>
<form name="Refresh" method="get" action="listofitemsservlet.html">
    <input type="submit" value="Refresh">
</form>
<h4>Search for an item list</h4>
<form name="SearchList" method="get" action="${pageContext.request.contextPath}/searchInventoryListServlet.html">
    Category Name: <input type="text" name="listName"/>
    <input type="submit" value="Search"/>
</form>

<table style="width:100%">
    <tr>
        <th>Categories</th>
        <th>Link to category</th>
    </tr>
    <c:forEach items="${requestScope.listNames}" var="listName" varStatus="status">
        <tr>
            <td>${listName}</td>
            <td><a href='${pageContext.request.contextPath}/ItemLists/itemlist${status.count}.jsp'>${listName}.html</a></td>
        </tr>
    </c:forEach>
</table>
<hr>
<h4>Add a new item list (max 10)</h4>
<p>Item lists cannot have the same name.</p>
<form name="newCategoryForm" method="get" action="listofitemsservlet.html">
    Category Name: <input type="text" name="categoryName"/>
    <input type="submit" value="Add" />
</form>
<h4>Delete an item list</h4>
<form name="deleteCategory" method="get" action="editItemListServlet.html">
    Category Name: <input type="text" name="categoryName"/>
    <input type="submit" value="Delete"/>
</form>
<h4>Rename an item list</h4>
<p>Item lists cannot have the same name.</p>
<form name="RenameCategory" method="get" action="editItemListServlet.html">
    Old Category Name: <input type="text" name="oldCategoryName"/>
    New Category Name: <input type="text" name="newCategoryName"/>
    <input type="submit" value="Rename"/>
</form>
<hr>
    <a href='index.html'>Back to the default index.html</a>
</body>
</html>
