package uk.ac.ucl.shell;

import org.junit.Test;
import uk.ac.ucl.applications.CurrentDirectory;
import uk.ac.ucl.applications.Ls;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class LsTest extends BaseTest{
    @Test
    public void testNoPath() throws IOException {
        Ls ls = new Ls();
        String currentDir = CurrentDirectory.getInstance().getCurrentDirectory();
        File file = new File(currentDir);
        String[] directories = file.list();
        directories = Arrays.stream(directories).filter(x -> !x.startsWith(".")).toArray(String[]::new);
        ls.exec(appArgs, null, writer);

        out.close();
        String result = input.lines().collect(Collectors.joining("\n"));
        String[] resultSplit = result.split("\\s");

        assertArrayEquals(directories, resultSplit);
    }

    @Test
    public void testOnePathNoDot() throws Exception {
        Ls ls = new Ls();
        String currentDir = CurrentDirectory.getInstance().getCurrentDirectory();
        File file = new File(currentDir);
        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());
        directories = Arrays.stream(directories).filter(x -> !x.startsWith(".")).toArray(String[]::new);
        // test ls for each subdirectory
        for (String s :
                directories) {
            File subdirectory = new File(currentDir + "\\" + s);
            // get all files not starting with "." in subdirectory
            String[] subdirectoryFiles = subdirectory.list();
            if (subdirectoryFiles == null){
                continue;
            }
            subdirectoryFiles = Arrays.stream(subdirectoryFiles).filter(x -> !x.startsWith(".")).toArray(String[]::new);

            // get result from "ls (String s)"
            appArgs.add(s);
            ls.exec(appArgs, null, writer);
            out.close();
            String result = input.lines().collect(Collectors.joining("\n"));
            String[] resultSplit = result.split("\\s");

            assertArrayEquals(subdirectoryFiles, resultSplit);
            setup();
        }
    }

    @Test(expected = RuntimeException.class)
    public void TestMultiplePaths() throws  IOException{
        Ls ls = new Ls();
        String currentDir = CurrentDirectory.getInstance().getCurrentDirectory();
        File file = new File(currentDir);
        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());
        directories = Arrays.stream(directories).filter(x -> x.startsWith(".")).toArray(String[]::new);
        appArgs.addAll(Arrays.asList(directories));
        ls.exec(appArgs, null, writer);
    }

    @Test(expected = RuntimeException.class)
    public void TestWrongPath()throws IOException{
        Ls ls = new Ls();
        String wrongPath = "wrong path";
        String currentDir = CurrentDirectory.getInstance().getCurrentDirectory();
        File file = new File(currentDir);
        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());
        directories = Arrays.stream(directories).filter(x -> x.startsWith(".")).toArray(String[]::new);
        assertFalse(Arrays.stream(directories).anyMatch(wrongPath::equals));
        appArgs.add(wrongPath);
        ls.exec(appArgs, null, writer);
    }
}

