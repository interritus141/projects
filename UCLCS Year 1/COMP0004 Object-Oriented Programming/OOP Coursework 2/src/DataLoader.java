import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class DataLoader {
    private final String fileName;
    private final DataFrame dataFrame = new DataFrame();
    private String[] columnNames;
    private String[][] data;
    private int numberOfRows;

    public DataFrame getDataFrame() {
        return dataFrame;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public String[][] getData() {
        return data;
    }

    public DataLoader(String fileName) {
        this.fileName = fileName;
    }

    public void readCSVFile() throws IOException {
        BufferedReader CSVreader = new BufferedReader(new FileReader(fileName));
        String[] patient;
        String line;
        columnNames = CSVreader.readLine().split(",", -1);
        createColumnNames(columnNames);
        getRowsOfData();
        data = new String[numberOfRows-1][columnNames.length];
        int i = 0;
        while ((line = CSVreader.readLine()) != null) {
            patient = line.split(",", -1);
            for (int index = 0; index < columnNames.length; index++) {
                dataFrame.addValue(columnNames[index], patient[index]);
                data[i][index] = patient[index];
            }
            i++;
        }
        CSVreader.close();
    }

    public void readJSONFile() throws IOException {
        BufferedReader JSONreader = new BufferedReader(new FileReader(fileName));
        String line;
        getRowsOfData();
        data = new String[numberOfRows][getNumberOfColumns()];
        int i = 0;
        while ((line = JSONreader.readLine()) != null) {
            line = line.replaceAll(":", ",");
            String[] patient = line.replaceAll("[^a-zA-Z0-9, ]", "").split(",");
            for (int j = 0; j < patient.length; j++) {
                if (j % 2 != 0) {
                    dataFrame.addValue(patient[j-1], patient[j]);
                    data[i][j/2] = patient[j];
                }
            }
            i++;
        }
        JSONreader.close();
    }

    private int getNumberOfColumns() throws IOException {
        BufferedReader JSONreader = new BufferedReader(new FileReader(fileName));
        String line = JSONreader.readLine().replaceAll(":", ",");
        String[] patient = line.replaceAll("[^a-zA-Z0-9, ]", "").split(",");
        columnNames = new String[patient.length/2];
        int i = 0;
        for (int name = 0; name < patient.length; name += 2) {
            columnNames[i] = patient[name];
            i++;
        }
        createColumnNames(columnNames);
        return i;
     }

    public void getRowsOfData() throws IOException {
        BufferedReader CSVreader = new BufferedReader(new FileReader(fileName));
        numberOfRows = 0;
        while (CSVreader.readLine() != null) {
            numberOfRows++;
        }
        CSVreader.close();
    }

    public void createColumnNames(String[] names) {
        for (String name : names){
            dataFrame.addColumn(name);
        }
    }
}
