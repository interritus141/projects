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
import java.util.HashMap;

@WebServlet("/searchItemServlet.html")
public class SearchItemServlet extends HttpServlet {
    private ItemList itemList;

    public SearchItemServlet() {
        this.itemList = new ItemList();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        itemList = new ItemList();

        String name = request.getParameter("ItemName");
        ArrayList<Item> oldItemList = loadItemList(request);
        assert oldItemList != null;
        filterByName(oldItemList, name);
        setAttributes(request);

        int index = getIndexOfFile(request);

        request.setAttribute("listName", request.getParameter("listName"));
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/ItemLists/itemlist" + (index+1) + ".jsp");
        dispatch.forward(request, response);
    }

    private ArrayList<Item> loadItemList(HttpServletRequest request) {
        ItemList oldList = new ItemList();
        try {
            String listName = request.getParameter("listName");
            oldList.loadFileByName(listName);
            return oldList.getItemList();
        } catch (IOException e) {
            System.out.println("Error. File cannot be loaded.");
        }
        return null;
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

    private void filterByName(ArrayList<Item> newItemList, String name) {
        for (Item item : newItemList) {
            if (item.getName().equals(name)) {
                itemList.addItem(item);
            }
        }
    }

    private void setAttributes(HttpServletRequest request) {
        request.setAttribute("itemName", getListOfItemAttributes(itemList, "Name"));
        request.setAttribute("itemIndex", getListOfItemAttributes(itemList, "Index"));
        request.setAttribute("itemDescription", getListOfItemAttributes(itemList, "Description"));
        request.setAttribute("itemURL", getListOfItemAttributes(itemList, "URL"));
        request.setAttribute("itemImage", getListOfItemAttributes(itemList, "Image"));
        request.setAttribute("itemImageURL", getListOfItemAttributes(itemList, "ImageURL"));
    }

    private ArrayList<String> getListOfItemAttributes(ItemList itemList, String name) {
        HashMap<String, ArrayList<String>> itemAttributesMap = itemList.getItemNames();
        return itemAttributesMap.get(name);
    }
}