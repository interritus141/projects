package ucl.ac.uk.servlets.listsServlets;

import ucl.ac.uk.model.InventoryLists;
import ucl.ac.uk.model.Item;
import ucl.ac.uk.model.ItemList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/editItemServlet.html")
public class EditItemServlet extends HttpServlet {
    private final ItemList itemList;

    public EditItemServlet() {
        this.itemList = new ItemList();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        ArrayList<Item> newItemList =  loadItemList(request);
        itemList.setNewItemList(newItemList);
        retrieveInputs(request);

        saveToFile(request);
        int index = getIndexOfFile(request);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/ItemLists/editItemList" + (index+1) + ".jsp");
        dispatch.forward(request, response);
    }

    private int getIndexOfFile(HttpServletRequest request) {
        String listName = request.getParameter("listName");
        InventoryLists inventoryLists = new InventoryLists();
        try {
            inventoryLists.loadLists();
            return inventoryLists.getListsNames().indexOf(listName);
        } catch (IOException e) {
            System.out.println("Error. File cannot be found.");
        }
        return -1;
    }

    private ArrayList<Item> loadItemList(HttpServletRequest request) {
        try {
            String listName = request.getParameter("listName");
            itemList.loadFileByName(listName);
            return itemList.getItemList();
        } catch (IOException e) {
            System.out.println("Error. File cannot be loaded.");
        }
        return null;
    }

    public void saveToFile(HttpServletRequest request) {
        String listName = request.getParameter("listName");
        try {
            itemList.saveToFile(listName);
        } catch (IOException e) {
            System.out.println("Error. File cannot be created.");
        }
    }

    public void retrieveInputs(HttpServletRequest request) {
        String itemName = request.getParameter("ItemName");
        String oldItemName = request.getParameter("oldItemName");
        String newItemName = request.getParameter("newItemName");
        String newItemIndex = request.getParameter("newItemIndex");
        String newItemDescription = request.getParameter("newItemDescription");
        String newItemURL = request.getParameter("newItemURL");
        String newItemImage = request.getParameter("newItemImage");
        String newItemImageURL = request.getParameter("newItemImageURL");

        if (itemName != null) {
            if (newItemIndex != null && newItemDescription != null && newItemURL != null && newItemImage != null) {
                editItem(itemName, newItemIndex, newItemDescription, newItemURL, newItemImage, newItemImageURL);
            }
            else {
                deleteItem(itemName);
            }
        } else if (oldItemName != null && newItemName != null) {
            renameItem(oldItemName, newItemName);
        }
    }

    public void deleteItem(String itemName) {
        itemList.removeItem(itemName);
    }

    public void editItem(String name, String index, String description, String URL, String image, String imageURL) {
        itemList.editItem(name, index, description, URL, image, imageURL);
    }

    public void renameItem(String oldName, String newName) {
        itemList.renameItem(oldName, newName);
    }
}
