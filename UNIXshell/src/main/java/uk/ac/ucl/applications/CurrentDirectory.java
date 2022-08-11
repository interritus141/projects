package uk.ac.ucl.applications;

/**
 * Contains the current directory of this programme
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public final class CurrentDirectory {
    private String currentDirectory;
    private static CurrentDirectory instance = null;

    private CurrentDirectory(String dir){
        currentDirectory = dir;
    }

    public String getCurrentDirectory(){
        return currentDirectory;
    }

    public void setCurrentDirectory(String dir){
        currentDirectory = dir;
    }

    public static CurrentDirectory getInstance(){
        if(instance == null){
            instance = new CurrentDirectory(System.getProperty("user.dir")); 
        }
        return instance;
    }
    
}

    