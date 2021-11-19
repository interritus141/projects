public class WordCorrector {
    private String correctedText;
    private String spellingError;
    private String correctedWord;
    private StringArray suggestionArray = new StringArray();
    private StringArray words;
    private Input input;
    private FileOutput correctedFile;
    private final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public WordCorrector(String spellingError, StringArray words, String originalText, String filename, Input input) {
        this.spellingError = spellingError;
        this.words = words;
        this.correctedText = originalText;
        this.correctedFile = new FileOutput(filename);
        this.input = input;
    }

    public void getSuggestionArray() {
        addCharacter();
        removeCharacter();
        swapCharacter();
        if (getFirstSuggestion() == null) {
            getMenuOptionAlt();
        }
        else {
            getMenuOption();
        }
    }

    public void addCharacter() {
        String suggestion;
        for (char c : alphabet) {
            suggestion = spellingError + c;
            if (words.contains(suggestion)) {
                suggestionArray.add(suggestion);
            }
            suggestion = c + spellingError;
            if (words.contains(suggestion)) {
                suggestionArray.add(suggestion);
            }
        }
    }

    public void removeCharacter() {
        int len = spellingError.length();
        String suggestion;
        if (words.contains(spellingError.substring(1))) {
            suggestionArray.add(spellingError.substring(1));
        }
        for (int i = 1; i < len; i++) {
            String start = spellingError.substring(0, i);
            String end = spellingError.substring(i+1);
            suggestion = start + end;
            if (words.contains(suggestion)) {
                suggestionArray.add(suggestion);
            }
        }
        if (words.contains(spellingError.substring(0, len))) {
            suggestionArray.add(spellingError.substring(0, len));
        }
    }

    public void swapCharacter() {
        int len = spellingError.length() - 1;
        String suggestion;
        for (int i = 0; i < len; i++) {
            suggestion = spellingError.substring(0,i);
            suggestion += spellingError.charAt(i+1);
            suggestion += spellingError.charAt(i);
            suggestion += spellingError.substring(i+2);
            if (words.contains(suggestion)) {
                suggestionArray.add(suggestion);
            }
        }
    }

    public String getCorrectedWords() {
        return correctedWord;
    }

    public String getMisspelledWord() {
        return spellingError;
    }

    public String getFirstSuggestion() {
        return suggestionArray.get(0);
    }

    public String getSecondSuggestion() {
        return suggestionArray.get(1);
    }

    public String getThirdSuggestion() {
        return suggestionArray.get(2);
    }

    public void add1stSuggestion() {
        this.correctedWord = suggestionArray.get(0);
        modifyFile();
    }

    public void add2ndSuggestion() {
        this.correctedWord = suggestionArray.get(1);
        modifyFile();
    }

    public void add3rdSuggestion() {
        this.correctedWord = suggestionArray.get(2);
        modifyFile();
    }

    public void noChangeToWord() {
        this.correctedWord = spellingError;
        modifyFile();
    }

    public void modifyFile() {
        correctedText = correctedText.replaceAll(spellingError, correctedWord);
    }

    public FileOutput getCorrectedFile() {
        return correctedFile;
    }

    public String getCorrectedText() {
        return correctedText;
    }

    public void suggestedWordsDisplayMenu() {
        System.out.printf("INCORRECT WORD: %s\n", getMisspelledWord());
        System.out.println("LIST OF SUGGESTED WORDS");
        System.out.printf("1. %s\n", getFirstSuggestion());
        System.out.printf("2. %s\n", getSecondSuggestion());
        System.out.printf("3. %s\n", getThirdSuggestion());
        System.out.println("4. Skip. (word will be unchanged)");
        System.out.println("5. Quit. (Go back to original menu)");
        System.out.println("CHOOSE ONE:");
    }

    public void getMenuOption() {
        int option;
        suggestedWordsDisplayMenu();
        while (true) {
            if (input.hasNextInt()) {
                option = input.nextInt();
                break;
            }
            input.nextLine();
            suggestedWordsDisplayMenu();
        }
        executeOption(option);
    }

    public void executeOption(int option) {
        switch (option) {
            case 1 -> add1stSuggestion();
            case 2 -> add2ndSuggestion();
            case 3 -> add3rdSuggestion();
            case 4 -> noChangeToWord();
            case 5 -> System.exit(0);
        }
    }

    public void suggestedWordsDisplayMenuAlt() {
        System.out.printf("INCORRECT WORD: %s\n", getMisspelledWord());
        System.out.println("There are no suitable suggestions.");
        System.out.println("1. Skip. (word will be unchanged)");
        System.out.println("2. Quit. (Go back to original menu)");
        System.out.println("CHOOSE ONE:");
    }

    public void getMenuOptionAlt() {
        int option;
        suggestedWordsDisplayMenuAlt();
        while (true) {
            if (input.hasNextInt()) {
                option = input.nextInt();
                break;
            }
            input.nextLine();
            suggestedWordsDisplayMenuAlt();
        }
        executeOptionAlt(option);
    }

    public void executeOptionAlt(int option) {
        switch (option) {
            case 1 -> noChangeToWord();
            case 2 -> System.exit(0);
        }
    }
}