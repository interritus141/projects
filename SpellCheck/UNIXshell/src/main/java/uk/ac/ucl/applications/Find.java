package uk.ac.ucl.applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Recursively searches for files with matching names. 
 * Outputs the list of relative paths, each followed by a newline.
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Find implements Application {
 
    /**
     * Executes Find application.
     * @param appArgs arguments for the applications.
     * @param input standard input used if no files are provided.
     * @param writer standard output written to.
     * @throws IOException catch IO exceptions, whenever an input or output operation is failed or interpreted.
    */

    @Override
    public void exec(ArrayList<String> appArgs,BufferedReader input, OutputStreamWriter writer) throws IOException {
        String rootDirectory = directory.getCurrentDirectory();
        ArrayList<String> listOfFiles = new ArrayList<>();
        boolean pattern = false; 

        if (appArgs.isEmpty()) {
            throw new RuntimeException("find: missing arguments");
        }

        if (appArgs.size() == 1) {
            throw new RuntimeException("find: missing argument: -name or pattern");
        }       

        if (appArgs.size() == 2 && !appArgs.get(0).equals("-name")) {
            throw new RuntimeException("find: wrong argument " + appArgs.get(0));
        }
        if (appArgs.size() == 3 && !appArgs.get(1).equals("-name")) {
            throw new RuntimeException("find: wrong argument " + appArgs.get(1));
        }

        if (appArgs.size() > 3) {
            throw new RuntimeException("find: too many arguments");
        }

        if (appArgs.size() == 2) {
            listOfFiles = currDirFind(appArgs, rootDirectory, listOfFiles, pattern); 
        }

        else {
            listOfFiles = specificDirFind(appArgs, rootDirectory, listOfFiles, pattern);    
        }
        write(writer, rootDirectory, listOfFiles);
    }

    private void write(OutputStreamWriter writer, String rootDirectory, ArrayList<String> listOfFiles)
            throws IOException {
        try {
            if (listOfFiles.isEmpty()) { 
                throw new RuntimeException("no such file in directory: " + rootDirectory);
            }
            Collections.sort(listOfFiles);
            writeOutput(listOfFiles, writer);
        } catch (NullPointerException e) {
            throw new RuntimeException("find: no such directory");
        }
    }

    private ArrayList<String> specificDirFind(ArrayList<String> appArgs, String rootDirectory, ArrayList<String> listOfFiles,
            boolean pattern) {
        String fileName;
        File dir;
        dir = new File(rootDirectory, appArgs.get(0));
        if (!dir.isDirectory()) {
            throw new RuntimeException("find: " + rootDirectory + " is not an existing directory.");
        }

        fileName = appArgs.get(2);

        if (fileName.charAt(0) == '*') {
            pattern = true;
            fileName = getExtension(fileName);
        }

        listOfFiles = findFile(dir, fileName, listOfFiles, pattern, false);
        return listOfFiles;
    }

    private ArrayList<String> currDirFind(ArrayList<String> appArgs, String rootDirectory, ArrayList<String> listOfFiles,
            boolean pattern) {
        String fileName;
        File dir;
        dir = new File(rootDirectory);
        fileName = appArgs.get(1);
   
        if (fileName.charAt(0) == '*') {
            pattern = true;
            fileName = getExtension(fileName);
        }

        listOfFiles = findFile(dir, fileName, listOfFiles, pattern, true);
        return listOfFiles;
    }
    
    private String getExtension(String fileName) {
        int i = fileName.lastIndexOf(".");
        if (i > 0) {
            fileName = fileName.substring(i+1);
        }
        return fileName;
    }    

    public ArrayList<String> findFile(File root, String fileName, ArrayList<String> fileList, boolean pattern, boolean currDir) {

        File[] files = root.listFiles();
        if (files != null) {
            for (File f : files) {
                String name = f.getName();
                if (pattern == true && f.isFile()) {
                    name = getExtension(name);
                }
                if (name.matches(fileName)) {
                    addToList(fileList, currDir, f);
                }
                else if (f.isDirectory()) {
                    fileList = findFile(f, fileName, fileList, pattern, currDir);
                }
            }
        }
        return fileList;
    }

    private void addToList(ArrayList<String> fileList, boolean currDir, File f) {
        Path relpath = Paths.get(f.getAbsolutePath());
        Path basePath = Paths.get(System.getProperty("user.dir"));
        Path basePathtoPath = basePath.relativize(relpath);
        String path = basePathtoPath.toString();
        if (currDir == true) {
            path = "./" + path;
        }
        fileList.add(path);
    }

    public void writeOutput(ArrayList<String> listOfFiles, OutputStreamWriter writer) throws IOException {
        for (String fileName : listOfFiles) {
            writer.write(fileName);
            writer.write(System.getProperty("line.separator"));
            writer.flush();        
        }
    }
}
