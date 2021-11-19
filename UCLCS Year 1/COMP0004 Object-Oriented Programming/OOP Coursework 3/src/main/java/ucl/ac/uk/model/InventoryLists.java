package ucl.ac.uk.model;

import com.sun.jdi.NativeMethodException;

import java.io.*;
import java.util.ArrayList;

public class InventoryLists {
    private ArrayList<ItemList> listOfItemLists;
    private ArrayList<String> listsNames;

    public InventoryLists() {
        this.listsNames = new ArrayList<>();
        this.listOfItemLists = new ArrayList<>();
    }

    public ArrayList<ItemList> getListOfItemLists() {
        return listOfItemLists;
    }

    public ArrayList<String> getListsNames() {
        return listsNames;
    }

    public void setListsNames(ArrayList<String> newListNames) {
        listsNames = newListNames;
    }

    public ItemList getItemList(String name) {
        for (ItemList itemList : listOfItemLists) {
            if (name.equals(itemList.getName())) {
                return itemList;
            }
        }
        return null;
    }

    public int size() {
        return listsNames.size();
    }

    public void addNewList(String name) {
        ItemList itemList = new ItemList();
        itemList.setName(name);
        listOfItemLists.add(itemList);
        if (!listsNames.contains(name)) {
            listsNames.add(name);
        }
        try {
            saveLists();
        } catch (IOException e) {
            System.out.println("Error. File cannot be accessed.");
        }
    }

    public void removeList(String name) {
        if (listsNames.contains(name)) {
            System.out.println("CONTAINS");
        }
        listsNames.remove(name);
        try {
            saveLists();
        } catch (IOException e) {
            System.out.println("Error. File cannot be accessed.");
        }
    }

    public void renameList(String oldName, String newName) {
        for (ItemList itemList : listOfItemLists) {
            if (itemList.getName().equals(oldName)) {
                itemList.setName(newName);
                break;
            }
        }
        for (int i = 0; i < listsNames.size(); i++) {
            if (oldName.equals(listsNames.get(i))) {
                listsNames.set(i, newName);
                break;
            }
        }
        try {
            saveLists();
        } catch (IOException e) {
            System.out.println("Error. File cannot be accessed.");
        }
    }

    public void saveFiles(String name) throws IOException {
        Item test = new Item.Builder("test").build();
        ArrayList<String> itemNames = test.getItemNames();
        String directory = "src/main/java/ucl/ac/uk/database";
        FileWriter csvWriter = new FileWriter(new File(directory,name + ".csv"));
        csvWriter.append(String.join(",", itemNames));
        csvWriter.append("\n");
        csvWriter.flush();
        csvWriter.close();
    }

    public void saveLists() throws IOException {
        String directory = "src/main/java/ucl/ac/uk/database";
        FileWriter csvWriter = new FileWriter(new File(directory, "listnames.csv"));
        csvWriter.append(String.join(",", listsNames));
        csvWriter.append("\n");
        csvWriter.flush();
        csvWriter.close();
    }

    public void loadLists() throws IOException {
        listsNames = new ArrayList<>();
        listOfItemLists = new ArrayList<>();
        String path = "src/main/java/ucl/ac/uk/database/";
        BufferedReader csvReader = new BufferedReader(new FileReader(path + "listnames.csv"));
        String line;
        String[] lists = new String[10];
        while ((line = csvReader.readLine()) != null) {
            lists = line.split(",", -1);
        }
        for (String list : lists) {
            addNewList(list);
        }
    }

}


