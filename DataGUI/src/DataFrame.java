import java.util.ArrayList;

public class DataFrame {
    private final ArrayList<Column> dataFrame;

    public DataFrame() {
        this.dataFrame = new ArrayList<>();
    }

    public void addColumn(String name) {
        Column newColumn = new Column(name);
        dataFrame.add(newColumn);
    }

    public ArrayList<String> getColumnNames() {
        ArrayList<String> columnNames = new ArrayList<>();
        for (Column column : dataFrame) {
            String columnName = column.getName();
            columnNames.add(columnName);
        }
        return columnNames;
    }

    public int getRowCount() {
        return dataFrame.get(0).getSize();
    }

    public String getValue(String name, int row) {
        for (Column column : dataFrame) {
            if (isEquals(name, column)) {
                return column.getRowValue(row);
            }
        }
        return null;
    }

    public void putValue(String name, int row, String value) {
        for (Column column : dataFrame) {
            if (isEquals(name, column)) {
                column.setRowValue(row, value);
            }
        }
    }

    public void addValue(String name, String value) {
        for (Column column : dataFrame) {
            if (isEquals(name, column)) {
                column.addRowValue(value);
            }
        }
    }

    private boolean isEquals(String name, Column column) {
        return name.equals(column.getName());
    }
}


