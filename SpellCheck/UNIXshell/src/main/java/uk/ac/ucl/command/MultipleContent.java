package uk.ac.ucl.command;

import uk.ac.ucl.applications.CurrentDirectory;
import uk.ac.ucl.visitor.Visitor;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MultipleContent implements  Command{
    List<String> ls = new ArrayList<>();

    public MultipleContent(String nonSpecial, CurrentDirectory currentDirectory) throws IOException {
        // i is position of last "/" char
        int i = nonSpecial.lastIndexOf('/');
        String fullDir;
        String filePattern;
        String prefix = "";

        // no "/" present
        if (i == -1){
            fullDir = currentDirectory.getCurrentDirectory();
            filePattern = nonSpecial;
        }
        else{
            // prefix should be nonspecial minus file pattern
            prefix = nonSpecial.substring(0,i)+"/";
            fullDir = currentDirectory.getCurrentDirectory()+ "/" + prefix;

            if (i < nonSpecial.length()-1) {
                filePattern = nonSpecial.substring(i+1);
            } else{
                filePattern = "";
            }
        }

        List<String> globbingResult = getGlobbingResult(fullDir, filePattern, prefix, nonSpecial);

        ls.addAll(globbingResult);
    }

    private List<String> getGlobbingResult(String fullDir, String filePattern, String prefix, String nonSpecial) throws IOException {
        List<String> globbingResult = new ArrayList<>();
        Path dir = Paths.get(fullDir);
        DirectoryStream<Path> stream = Files.newDirectoryStream(dir,filePattern);
        for (Path entry : stream) {
            // prefix is path from current directory to file
            globbingResult.add(prefix + entry.getFileName().toString());
        }
        if (globbingResult.isEmpty()) {
            globbingResult.add(nonSpecial);
        }
        return globbingResult;
    }

    public List<String> getLs(){
        return ls;
    }

    @Override
    public void accept(Visitor visitor) throws IOException {

    }
}
