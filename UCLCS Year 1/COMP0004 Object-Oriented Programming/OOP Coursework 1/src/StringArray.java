public class StringArray {
    private String[] wordList;
    private String[] tempWordList;
    private int index;

    public StringArray() {
        this.wordList = new String[100];
    }

    public StringArray(StringArray a) {
        this.wordList = a.getWordList().clone();
    }

    private String[] getWordList() {
        return wordList;
    }

    public int size() {
        int count = 0;
        for (String s : wordList) {
            if (s == null) {
                break;
            }
            count++;
        }
        return count;
    }

    public boolean isEmpty() {
        return wordList[0] == null;
    }

    public String get(int index) {
        if (!isEmpty()) {
            return wordList[index];
        }
        return null;
    }

    public void set(int index, String s) {
        if (!isEmpty()) {
            wordList[index] = s;
        }
    }

    public void add(String s) {
        int size = size();
        if (size == wordList.length) {
            createNewArray();
        }
        wordList[size] = s;
    }

    public void insert(int index, String s) {
        int size = size();
        int length = wordList.length;
        if (size == length) {
           createNewArray();
        }
        if (size >= index) {
            System.arraycopy(wordList, index, wordList, index + 1, size - index);
        }
        wordList[index] = s;
    }

    public void remove(int index) {
        int size = size();
        if (index >= size) {
            return;
        }
        if (size - index >= 0) {
            System.arraycopy(wordList, index + 1, wordList, index, size - index);
        }
    }

    public boolean contains(String s) {
        int size = size();
        s = s.toLowerCase();
        tempWordList = wordList.clone();
        for(int i = 0; i < size; i++) {
            tempWordList[i] = tempWordList[i].toLowerCase();
            if(tempWordList[i].compareTo(s) == 0) {
                index = i;
                return true;
            }
        }
        return false;
    }

    public boolean containsMatchingCase(String s) {
        int size = size();
        String[] newArray = wordList.clone();
        for(int i = 0; i < size; i++) {
            if(newArray[i].compareTo(s) == 0) {
                index = i;
                return true;
            }
        }
        return false;
    }

    public int indexOf(String s) {
        if (contains(s)) {
            return index;
        }
        return -1;
    }

    public int indexOfMatchingCase(String s) {
        if (containsMatchingCase(s)) {
            return index;
        }
        return -1;
    }

    private void createNewArray() {
        tempWordList = wordList.clone();
        this.wordList = new String[wordList.length*2];
        if (wordList.length >= 0) {
            System.arraycopy(tempWordList, 0, wordList, 0, tempWordList.length);
        }
    }
}