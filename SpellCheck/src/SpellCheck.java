import java.util.Arrays;

public class SpellCheck {
    private String text;
    private String[] stringList;
    private String[] listOfSpellingErrors;
    private StringArray spellingErrors;
    private StringArray textArray;
    private StringArray words;

    public SpellCheck(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public StringArray getSpellingErrors() {
        return spellingErrors;
    }

    public StringArray getDict() {
        return words;
    }

    public String[] getListOfSpellingErrors() {
        return listOfSpellingErrors;
    }

    public void removePunctuation() {
        String textNoPunct = text.replaceAll("[^a-zA-Z]", " ");
        stringList = textNoPunct.toLowerCase().split("\\s+");
    }

    public void storeInArray() {
        textArray = new StringArray();
        for (String s : stringList) {
            textArray.add(s);
        }
    }

    public void readDict() {
        words = new StringArray();
        FileInput input = new FileInput("words.txt");
        while (input.hasNextLine()) {
            String s = input.nextLine();
            words.add(s);
        }
        input.close();
    }

    public void checkSpelling() {
        int size = textArray.size();
        spellingErrors = new StringArray();
        for (int i = 0; i < size; i++) {
            if (!words.contains(textArray.get(i)) && !spellingErrors.contains(textArray.get(i))) {
                spellingErrors.add(textArray.get(i));
            }
        }
    }

    public void getArrayOfSpellingErrors() {
        int size = spellingErrors.size();
        listOfSpellingErrors = new String[size];
        for (int index = 0; index < size; index++) {
            listOfSpellingErrors[index] = spellingErrors.get(index);
        }
    }

    public void go() {
        removePunctuation();
        storeInArray();
        readDict();
        checkSpelling();
        getArrayOfSpellingErrors();
    }
}

class Display {
    private String text;
    private String filename;
    private Input input = new Input();

    public String getFilename() {
        return filename;
    }

    public void displayMenu() {
        System.out.println();
        System.out.println("MENU OPTIONS");
        System.out.println("1. Input via terminal.");
        System.out.println("2. Select text from file.");
        System.out.println("3. Quit.");
        System.out.println("Enter an option:");
    }

    public void getMenuOption() {
        int option;
        displayMenu();
        while (true) {
            if (input.hasNextInt()) {
                option = input.nextInt();
                input.nextLine();
                break;
            }
        input.nextLine();
        displayMenu();
        }
        executeOption(option);
    }

    public void executeOption(int option) {
        switch (option) {
            case 1 -> inputViaTerminal();
            case 2 -> inputViaFile();
            case 3 -> System.exit(0);
        }
    }

    public void inputViaTerminal() {
        System.out.println("Enter text for spellcheck:");
        text = getInput();
        this.filename = "correctedTextFromTerminal.txt";
    }

    public void inputViaFile() {
        String s = "";
        System.out.println("Enter name of file:");
        this.filename = getInput();
        FileInput input = new FileInput(filename);
        while (input.hasNextLine()) {
            s += input.nextLine();
        }
        input.close();
        text = s;
    }

    public String getInput() {
        String s = input.nextLine();
        return s;
    }

    public void checkSpelling() {
        getMenuOption();
        SpellCheck spellCheck_text = new SpellCheck(text);
        spellCheck_text.go();
        if (spellCheck_text.getListOfSpellingErrors().length == 0) {
            System.out.println("There are no spelling errors.");
            System.exit(0);
        }
        else {
            System.out.println("Spelling Errors: " + Arrays.toString(spellCheck_text.getListOfSpellingErrors()));
            correctSpelling(spellCheck_text);
        }
    }

    public void correctSpelling(SpellCheck spellCheck_text) {
        int index = 0;
        String correctedText = spellCheck_text.getText();
        String[] spellingErrorsList = spellCheck_text.getListOfSpellingErrors();
        StringArray dict = spellCheck_text.getDict();
        while (index < spellingErrorsList.length) {
            WordCorrector wrongWord = new WordCorrector(spellingErrorsList[index], dict, correctedText, filename, input);
            wrongWord.getSuggestionArray();
            correctedText = wrongWord.getCorrectedText();
            if (index == spellingErrorsList.length - 1) {
                wrongWord.getCorrectedFile().writeString(correctedText);
                wrongWord.getCorrectedFile().close();
            }
            index++;
        }
    }

    public void go() {
        checkSpelling();
    }
}

class Main {
    public static void main(String[] args) {
        new Display().go();
    }
}