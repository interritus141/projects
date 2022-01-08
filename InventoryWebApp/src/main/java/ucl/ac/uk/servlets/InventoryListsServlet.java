package ucl.ac.uk.servlets;

import ucl.ac.uk.model.InventoryLists;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/listofitemsservlet.html")
public class InventoryListsServlet extends HttpServlet {
    private final InventoryLists inventoryLists;

    public InventoryListsServlet() {
        this.inventoryLists = new InventoryLists();
        loadLists();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String categoryName = request.getParameter("categoryName");

        if (categoryName != null && !categoryName.equals("") && inventoryLists.size() < 10) {
            inventoryLists.addNewList(categoryName);
            inventoryLists.saveFiles(categoryName);
        }

        setFileNames(request);

        request.setAttribute("listNames", getListOfNames());

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/listofitems.jsp");
        dispatch.forward(request, response);
    }

    private void setFileNames(HttpServletRequest request) {
        for (int i = 0; i < inventoryLists.getListsNames().size(); i++) {
            String fileName = "List" + (i+1);
            request.getSession().setAttribute(fileName, inventoryLists.getListsNames().get(i));
        }
    }

    private void loadLists() {
        try {
            inventoryLists.loadLists();
        } catch (IOException e) {
            System.out.println("Error. File not found.");
        }
    }

    private ArrayList<String> getListOfNames() {
        loadLists();
        return inventoryLists.getListsNames();
    }
}
