import java.io.IOException;
import java.util.ArrayList;

public class Model {
    private DataLoader dataLoader = new DataLoader("COMP0004Data/patients100.csv");

    public Model() {}

    public String[][] getData() {
        return dataLoader.getData();
    }

    public void readCSVFile() {
        try {
            dataLoader.readCSVFile();
        }
        catch (IOException e) {
            System.out.println("Error. File not opened.");
        }
    }

    public void readJSONFile() {
        try {
            dataLoader.readJSONFile();
        }
        catch (IOException e) {
            System.out.println("Error. File not opened.");
        }
    }

    public String[] getColumnNames() {
        return dataLoader.getColumnNames();
    }

    public ArrayList<String> getColumnNamesList() {
        return dataLoader.getDataFrame().getColumnNames();
    }

    public void loadCSVFile(String name) {
        dataLoader = new DataLoader(name);
    }

    public void loadJSONFile(String name) {
        dataLoader = new DataLoader(name);
    }

    public void saveAsJSONFile(String name) {
        JSONWriter jsonWriter = new JSONWriter(name, dataLoader);
        try {
            jsonWriter.createJSONFile();
        }
        catch (IOException e) {
            System.out.println("Error. File cannot be saved.");
        }
    }
}