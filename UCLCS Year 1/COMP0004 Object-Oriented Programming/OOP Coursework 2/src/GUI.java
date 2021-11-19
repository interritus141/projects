import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.ArrayList;

public class GUI extends JFrame{
    private JPanel backPanel;
    private JPanel buttonPanel;
    private JPanel tablePanel;
    private JPanel cardPanel;
    private JPanel checkBoxPanel;
    private JPanel textBoxPanel;
    private JPanel bottomPanel;
    private JButton saveJSONButton;
    private JTextField textField;
    private JScrollPane scrollPane;
    private JTable table;
    private TableRowSorter<TableModel> sorter;
    private int timesLoaded = 0;
    private final ArrayList<Integer> checkBoxList = new ArrayList<>();
    private final CardLayout cardLayout = new CardLayout();
    private final Model model = new Model();

    public GUI() {
        super("patients");
        createGUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void createGUI() {
        createCardPanel();
        createTextBoxPanel();
        createButtonPanel();
        createBottomPanel();
        createBackPanel();
        add(backPanel, BorderLayout.CENTER);
    }

    public void createTablePanel() {
        tablePanel = new JPanel(new BorderLayout());
        String[][] patientData = model.getData();
        String[] columnNames = model.getColumnNames();
        table = new JTable(patientData, columnNames);
        table.setAutoCreateRowSorter(true);
        table.setPreferredScrollableViewportSize(new Dimension(400, 70));
        table.setFillsViewportHeight(true);
    }

    public void createCheckBoxPanel() {
        checkBoxPanel = new JPanel(new GridLayout(0, 1,0, 0));
        ArrayList<String> checkBoxNames = model.getColumnNamesList();
        JCheckBox[] checkBoxes = new JCheckBox[checkBoxNames.size()];
        for (int i = 0; i < checkBoxNames.size(); i++) {
            checkBoxes[i] = new JCheckBox(checkBoxNames.get(i));
            int finalI = i;
            checkBoxes[i].addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    filterByCheckbox(finalI);
                }
                else {
                    uncheckCheckbox(finalI);
                }
            });
            checkBoxPanel.add(checkBoxes[i]);
        }
    }

    private void filterByCheckbox(int finalI) {
        checkBoxList.add(finalI);
        if (checkBoxList.size() == 1) {
            for (int i = 0; i < model.getColumnNamesList().size(); i++) {
                if (checkBoxList.contains(i)) {
                    continue;
                }
                table.getColumnModel().getColumn(i).setMinWidth(0);
                table.getColumnModel().getColumn(i).setMaxWidth(0);
                table.getColumnModel().getColumn(i).setPreferredWidth(0);
            }
        }
        table.getColumnModel().getColumn(finalI).setPreferredWidth(100);
        table.getColumnModel().getColumn(finalI).setMaxWidth(10000);
        table.getColumnModel().getColumn(finalI).setMinWidth(50);
    }

    private void uncheckCheckbox(int finalI) {
        for (int i = 0; i < checkBoxList.size(); i++) {
            if (checkBoxList.get(i) == finalI) {
                checkBoxList.remove(i);
            }
        }
        if (checkBoxList.isEmpty()) {
            for (int i = 0; i < model.getColumnNamesList().size(); i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(50);
                table.getColumnModel().getColumn(i).setMaxWidth(1000);
                table.getColumnModel().getColumn(i).setMinWidth(50);
            }
        }
        else {
            table.getColumnModel().getColumn(finalI).setMinWidth(0);
            table.getColumnModel().getColumn(finalI).setMaxWidth(0);
            table.getColumnModel().getColumn(finalI).setPreferredWidth(0);
        }
    }

    public void createCardPanel() {
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
    }

    public void createTextBoxPanel() {
        textBoxPanel = new JPanel();
        textField = new JTextField(50);
        JLabel textBoxLabel = new JLabel("Filter text: ");
        textBoxPanel.add(textBoxLabel);
        textBoxPanel.add(textField);
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = textField.getText();

                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                }
                else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = textField.getText();

                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                }
                else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported.");
            }
        });
    }

    public void createButtonPanel() {
        buttonPanel = new JPanel(new GridLayout());
        JButton loadCSVButton = new JButton("Load CSV");
        JButton loadJSONButton = new JButton("Load JSON");
        saveJSONButton = new JButton("Save as JSON");
        saveJSONButton.addActionListener((ActionEvent e) -> saveJSONButtonClicked());
        loadCSVButton.addActionListener((ActionEvent e) -> loadCSVButtonClicked());
        loadJSONButton.addActionListener((ActionEvent e) -> loadJSONButtonClicked());
        buttonPanel.add(loadCSVButton);
        buttonPanel.add(saveJSONButton);
        buttonPanel.add(loadJSONButton);
        saveJSONButton.setVisible(false);
    }

    public void createBottomPanel() {
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
    }

    public void createScrollerPanel() {
        scrollPane = new JScrollPane(table);
    }

    private void createSorterObject() {
        sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
    }

    public void createBackPanel() {
        backPanel = new JPanel(new BorderLayout());
        backPanel.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));
        backPanel.add(bottomPanel, BorderLayout.PAGE_END);
    }

    public void loadCSVButtonClicked() {
        if (timesLoaded != 0) {
            resetPanel();
        }
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(null, "Opening: " + file.getName() + ".\n", "File Opening", JOptionPane.INFORMATION_MESSAGE);
            model.loadCSVFile(file.getPath());
            model.readCSVFile();
            showNewTable(file);
            timesLoaded++;
        }
    }

    public void saveJSONButtonClicked() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON", ".json");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getAbsolutePath().endsWith(".json")) {
                file = new File(fileChooser.getSelectedFile() + ".json");
            }
            JOptionPane.showMessageDialog(null, "Saving as " + file.getName());
            model.saveAsJSONFile(file.getName());
        }
    }

    public void loadJSONButtonClicked() {
        if (timesLoaded != 0) {
            resetPanel();
        }
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(null, "Opening: " + file.getName() + ".\n", "File Opening", JOptionPane.INFORMATION_MESSAGE);
            model.loadJSONFile(file.getPath());
            model.readJSONFile();
            showNewTable(file);
            timesLoaded++;
        }
    }

    private void resetPanel() {
        checkBoxPanel.removeAll();
        remove(textBoxPanel);
        remove(scrollPane);
    }

    private void showNewTable(File file) {
        createTablePanel();
        createCheckBoxPanel();
        createScrollerPanel();
        createSorterObject();
        cardPanel.add(tablePanel, file.getName());
        cardLayout.show(cardPanel, file.getName());
        addToBackPanel();
    }

    public void addToBackPanel() {
        saveJSONButton.setVisible(true);
        bottomPanel.add(textBoxPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        backPanel.add(checkBoxPanel, BorderLayout.WEST);
        backPanel.add(cardPanel, BorderLayout.CENTER);
        pack();
    }
}
