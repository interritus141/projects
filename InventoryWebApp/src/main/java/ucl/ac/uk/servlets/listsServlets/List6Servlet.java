package ucl.ac.uk.servlets.listsServlets;

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

@WebServlet("/list6servlet.html")
public class List6Servlet extends HttpServlet {
    private final ItemList list6Items;
    private String name;

    public List6Servlet() {
        this.list6Items = new ItemList();
        loadFile();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        loadFile();
        retrieveInputs(request);
        setAttributes(request);
        createNewFile(request);

        request.setAttribute("listName", name);
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/ItemLists/itemlist6.jsp");
        dispatch.forward(request, response);
    }


    private void createNewFile(HttpServletRequest request) {
        name = (String) request.getSession().getAttribute("List6");
        try {
            list6Items.saveToFile(name);
        } catch (IOException e) {
            System.out.println("Error. Saving to a different file.");
        }
    }

    private void loadFile() {
        try {
            list6Items.loadFileByIndex(5);
        } catch (IOException e) {
            System.out.println("Error. File not found.");
        }
    }

    private void retrieveInputs(HttpServletRequest request) {
        String itemName = request.getParameter("itemName");
        String itemIndex = request.getParameter("itemIndex");
        String itemDescription = request.getParameter("itemDescription");
        String itemURL = request.getParameter("itemURL");
        String itemImage = request.getParameter("itemImage");
        String itemImageURL = request.getParameter("itemImageURL");
        addNewItem(itemName, itemIndex, itemDescription, itemURL, itemImage, itemImageURL);
    }

    private void setAttributes(HttpServletRequest request) {
        request.setAttribute("itemName", getListOfItemAttributes(list6Items, "Name"));
        request.setAttribute("itemIndex", getListOfItemAttributes(list6Items, "Index"));
        request.setAttribute("itemDescription", getListOfItemAttributes(list6Items, "Description"));
        request.setAttribute("itemURL", getListOfItemAttributes(list6Items, "URL"));
        request.setAttribute("itemImage", getListOfItemAttributes(list6Items, "Image"));
        request.setAttribute("itemImageURL", getListOfItemAttributes(list6Items, "ImageURL"));
    }

    private void addNewItem(String itemName, String itemIndex, String itemDescription, String itemURL, String itemImage, String itemImageURL) {
        if ((itemName != null) && !itemName.equals("")) {
            Item newItem = new Item.Builder(itemName)
                    .withIndex(itemIndex)
                    .withDescription(itemDescription)
                    .withURL(itemURL)
                    .withImageName(itemImage)
                    .withImageURL(itemImageURL)
                    .build();

            list6Items.addItem(newItem);
        }
    }

    private ArrayList<String> getListOfItemAttributes(ItemList itemList, String name) {
        HashMap<String, ArrayList<String>> itemAttributesMap = itemList.getItemNames();
        return itemAttributesMap.get(name);
    }
}
