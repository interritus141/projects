package ucl.ac.uk.model;

import java.io.*;
import java.util.*;

public class ItemList {
    private String name;
    private ArrayList<Item> itemList;

    public ItemList() {
        this.itemList = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void addItem(Item item) {
        if (!itemList.isEmpty()) {
            for (Item itemPresent : itemList) {
                if (item.getName().equals(itemPresent.getName())) return;
            }
        }
        itemList.add(item);
    }

    public void setNewItemList(ArrayList<Item> newItemList) {
        this.itemList = newItemList;
    }

    public HashMap<String, ArrayList<String>> getItemNames() {
        HashMap<String, ArrayList<String>> itemMap = new HashMap<>();

        for (Item item : itemList) {
            addToList("Name", item.getName(), itemMap);
            addToList("Index", item.getIndex(), itemMap);
            addToList("Description", item.getDescription(), itemMap);
            addToList("URL", item.getURL(), itemMap);
            addToList("Image", item.getImage(), itemMap);
            addToList("ImageURL", item.getImageURL(), itemMap);
        }
        return itemMap;
    }

    public void addToList(String key, String item, HashMap<String, ArrayList<String>> items) {
        ArrayList<String> itemsList = items.get(key);

        if (itemsList == null) {
            itemsList = new ArrayList<>();
            itemsList.add(item);
            items.put(key, itemsList);
        }
        else itemsList.add(item);
    }


    public void removeItem(String name) {
        itemList.removeIf(item -> name.equals(item.getName()));
    }

    public void renameItem(String oldName, String newName) {
        if (!itemList.isEmpty()) {
            for (Item item : itemList) {
                if (oldName.equals(item.getName())) {
                    item.setName(newName);
                    break;
                }
            }
        }
    }

    public void editItem(String name, String index, String description, String URL, String image, String imageURL) {
        for (int i = 0; i < itemList.size(); i++) {
            if (name.equals(itemList.get(i).getName())) {
                Item newItem = new Item.Builder(name)
                        .withIndex(index)
                        .withDescription(description)
                        .withURL(URL)
                        .withImageName(image)
                        .withImageURL(imageURL)
                        .build();
                itemList.set(i, newItem);
                break;
            }
        }
    }

    public void saveToFile(String name) throws IOException {
        String directory = "C:\\Users\\looch\\OneDrive\\Desktop\\UCL_CS_1st_Year_Mods\\COMP0004 Object-Oriented Programming\\OOP Coursework 3\\HelloWorld_example\\src\\main\\java\\ucl\\ac\\uk\\database";
        Item test = new Item.Builder("test").build();
        ArrayList<String> itemNames = test.getItemNames();
        FileWriter csvWriter = new FileWriter(new File(directory, name + ".csv"));
        ArrayList<ArrayList<String>> newItemList = new ArrayList<>();
        csvWriter.append(String.join(",", itemNames));
        csvWriter.append("\n");
        for (Item item : itemList) {
            ArrayList<String> newItem = new ArrayList<>();
            newItem.add(item.getName());
            newItem.add(item.getIndex());
            newItem.add(item.getDescription());
            newItem.add(item.getURL());
            newItem.add(item.getImage());
            newItem.add(item.getImageURL());
            newItemList.add(newItem);
        }
        for (ArrayList<String> itemData : newItemList) {
            csvWriter.append(String.join(",", itemData));
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }

    public void loadFileByIndex(int index) throws IOException {
        itemList = new ArrayList<>();
        String path = "src/main/java/ucl/ac/uk/database/";
        String name = getNameOfFile(index, path);
        readFile(name, path);
    }

    public void loadFileByName(String name) throws IOException {
        itemList = new ArrayList<>();
        String path = "src/main/java/ucl/ac/uk/database/";
        readFile(name, path);
    }

    private void readFile(String name, String path) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(path + name + ".csv"));
        String[] item;
        String line;
        csvReader.readLine();
        while ((line = csvReader.readLine()) != null) {
            item = line.split(",", -1);
            Item newItem = new Item.Builder(item[0])
                    .withIndex(item[1])
                    .withDescription(item[2])
                    .withURL(item[3])
                    .withImageName(item[4])
                    .withImageURL(item[5])
                    .build();
            addItem(newItem);
        }
    }

    private String getNameOfFile(int index, String path) throws IOException {
        BufferedReader listNameReader = new BufferedReader(new FileReader(path + "listnames.csv"));
        String line;
        String[] listNames = new String[10];
        while ((line = listNameReader.readLine()) != null) {
            listNames = line.split(",", -1);
        }
        return listNames[index];
    }


}
