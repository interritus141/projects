package ucl.ac.uk.servlets;

import ucl.ac.uk.model.InventoryLists;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

@WebServlet("/editItemListServlet.html")
public class EditItemListServlet extends HttpServlet {
    private final InventoryLists inventoryLists;

    public EditItemListServlet() {
        super();
        inventoryLists = new InventoryLists();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        ArrayList<String> listNames = loadListNames();
        inventoryLists.setListsNames(listNames);
        retrieveInputs(request);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/editList.jsp");
        dispatch.forward(request, response);
    }

    private ArrayList<String> loadListNames() {
        try {
            inventoryLists.loadLists();
            return inventoryLists.getListsNames();
        } catch (IOException e) {
            System.out.println("Error. File cannot be loaded.");
        }
        return null;
    }

    private void retrieveInputs(HttpServletRequest request) {
        String listName = request.getParameter("categoryName");
        String oldListName = request.getParameter("oldCategoryName");
        String newListName = request.getParameter("newCategoryName");
        if (listName != null) {
            deleteList(listName);
        }
        else if (oldListName != null && newListName != null) {
            renameList(oldListName, newListName);
        }
    }

    private void deleteList(String listName) {
        inventoryLists.removeList(listName);
    }

    private void renameList(String oldListName, String newListName) {
        inventoryLists.renameList(oldListName, newListName);
    }
}
