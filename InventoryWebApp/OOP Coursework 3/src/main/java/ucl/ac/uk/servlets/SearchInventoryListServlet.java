package ucl.ac.uk.servlets;

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
import java.lang.reflect.Array;
import java.util.ArrayList;

@WebServlet("/searchInventoryListServlet.html")
public class SearchInventoryListServlet extends HttpServlet {
    private InventoryLists inventoryLists;

    public SearchInventoryListServlet() {
        this.inventoryLists = new InventoryLists();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        inventoryLists = new InventoryLists();

        String name = request.getParameter("listName");
        loadInventoryLists(request);
        assert inventoryLists != null;
        int index = filterByName(name);

        request.setAttribute("listName", inventoryLists.getListsNames().get(index));
        request.setAttribute("listIndex", index+1);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/searchedLists.jsp");
        dispatch.forward(request, response);
    }

    private void loadInventoryLists(HttpServletRequest request) {
        try {
            inventoryLists.loadLists();
        } catch (IOException e) {
            System.out.println("Error. File cannot be found.");
        }
    }

    private int filterByName(String listName) {
        return inventoryLists.getListsNames().indexOf(listName);
    }
}