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
    <h2>${requestScope.listName}</h2>
    <p>To add, edit, or remove items from the list, use the respective links.</p>
    <p>Always refresh the page before adding or editing an item.</p>
    <form name="Refresh" method="get" action="${pageContext.request.contextPath}/list9servlet.html">
        <input type="hidden" name="listName" value="${requestScope.listName}">
        <input type="submit" value="Refresh">
    </form>
    <h4>Search for an item</h4>
    <form name="SearchItem" method="get" action="${pageContext.request.contextPath}/searchItemServlet.html">
        <input type="hidden" name="listName" value="${requestScope.listName}">
        Item Name: <input type="text" name="ItemName"/>
        <input type="submit" value="Search"/>
    </form>
    <table style="width:100%">
        <caption>Items</caption>
        <tr>
            <th>Name</th>
            <th>Index</th>
            <th>Description</th>
            <th>URL</th>
            <th>Image</th>
        </tr>
        <c:forEach items="${requestScope.itemName}" var="itemName" varStatus="status">
            <tr>
                <td>${itemName}</td>
                <td>${requestScope.itemIndex[status.index]}</td>
                <td>${requestScope.itemDescription[status.index]}</td>
                <td><a href="${requestScope.itemURL[status.index]}">${requestScope.itemURL[status.index]}</a></td>
                <td><a href="${requestScope.itemImageURL[status.index]}">${requestScope.itemImage[status.index]}</a></td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <h4>Add a new item</h4>
    <p>An item with the same name as an item on the list cannot be added. Two items cannot have the same name.</p>
    <p>When adding a URL for either item or image, include the protocol (e.g. http://www.google.com).</p>
    <form name="newItemForm" method="get" action="${pageContext.request.contextPath}/list9servlet.html">
        Item Name: <input type="text" name="itemName"/> <br/>
        Item Index: <input type="text" name="itemIndex"/>
        Item Description: <input type="text" name="itemDescription"/>
        Item URL: <input type="text" name="itemURL"/>
        Item Image Name: <input type="text" name="itemImage"/>
        Item Image URL: <input type="text" name="itemImageURL"/>
        <input type="submit" value="Add" />
    </form>
    <h4>Delete an item</h4>
    <form name="deleteItem" method="get" action="${pageContext.request.contextPath}/editItemServlet.html">
        <input type="hidden" name="listName" value="${requestScope.listName}">
        Item Name: <input type="text" name="ItemName"/>
        <input type="submit" value="Delete"/>
    </form>
    <h4>Rename an item</h4>
    <form name="renameItem" method="get" action="${pageContext.request.contextPath}/editItemServlet.html">
        <input type="hidden" name="listName" value="${requestScope.listName}">
        Old Item Name: <input type="text" name="oldItemName"/>
        New Item Name: <input type="text" name="newItemName"/>
        <input type="submit" value="Rename"/>
    </form>
    <h4>Edit an item</h4>
    <p>When adding a URL for either item or image, include the protocol (e.g. http://www.google.com).</p>
    <form name="editItem" method="get" action="${pageContext.request.contextPath}/editItemServlet.html">
        <input type="hidden" name="listName" value="${requestScope.listName}">
        Item Name: <input type="text" name="ItemName"/> <br/>
        New Item Index: <input type="text" name="newItemIndex"/>
        New Item Description: <input type="text" name="newItemDescription"/>
        New Item URL: <input type="text" name="newItemURL"/>
        New Item Image: <input type="text" name="newItemImage"/>
        New Item Image URL: <input type="text" name="newItemImageURL"/>
        <input type="submit" value="Edit"/>
    </form>
    <hr>
    <a href='${pageContext.request.contextPath}/listofitemsservlet.html'>Back to the inventory lists</a> <br/>
    <a href='../index.html'>Back to the default index.html</a>
</body>
</html>
