import java.util.ArrayList;

public class Column {
    private final String name;
    private final ArrayList<String> columnList;

    public Column(String name) {
        this.name = name;
        this.columnList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getColumnList() {
        return columnList;
    }

    public int getSize() {
        return columnList.size();
    }

    public String getRowValue(int row) {
        return columnList.get(row);
    }

    public void setRowValue(int row, String value) {
        columnList.set(row, value);
    }

    public void addRowValue(String value) {
        columnList.add(value);
    }
}