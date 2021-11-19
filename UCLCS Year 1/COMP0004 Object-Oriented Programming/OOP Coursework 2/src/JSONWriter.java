import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JSONWriter {
    private final DataLoader dataLoader;
    private final String fileName;

    public JSONWriter(String fileName, DataLoader dataLoader) {
        this.fileName = fileName;
        this.dataLoader = dataLoader;
    }

    public void createJSONFile() throws IOException {
        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file);
        DataFrame dataFrame = dataLoader.getDataFrame();
        ArrayList<String> columnNames = dataFrame.getColumnNames();
        writeToJSONFile(fileWriter, dataFrame, columnNames);
        fileWriter.write("]");
        fileWriter.flush();
        fileWriter.close();
    }

    private void writeToJSONFile(FileWriter fileWriter, DataFrame dataFrame, ArrayList<String> columnNames) throws IOException {
        fileWriter.write("[");
        for (int i = 0; i < dataFrame.getRowCount(); i++) {
            fileWriter.write("{");
            for (String columnName : columnNames) {
                fileWriter.write("\"");
                fileWriter.write(columnName);
                fileWriter.write("\": ");
                fileWriter.write("\"");
                fileWriter.write(dataFrame.getValue(columnName,i));
                fileWriter.write("\"");
                if (!columnNames.get(columnNames.size() - 1).equals(columnName)) {
                    fileWriter.write(", ");
                }
            }
            fileWriter.write("}");
            if (i != dataFrame.getRowCount()-1) {
                fileWriter.write(",\n");
            }
        }
    }
}
