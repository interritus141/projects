package uk.ac.ucl.shell;

import org.junit.Test;
import uk.ac.ucl.applications.Cd;
import uk.ac.ucl.applications.CurrentDirectory;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class CdTest extends BaseTest {

    @Test(expected = RuntimeException.class)
    public void testCdNoPathGiven() throws IOException {
        Cd cd = new Cd();
        cd.exec(appArgs, null, writer);
    }

    @Test(expected = RuntimeException.class)
    public void testCdManyPathsGiven() throws IOException {
        Cd cd = new Cd();
        appArgs.add("one");
        appArgs.add("two");
        cd.exec(appArgs, null, writer);
    }

    @Test(expected = RuntimeException.class)
    public void testWrongPathGiven() throws IOException{
        Cd cd = new Cd();
        appArgs.add("wrong_path");
        cd.exec(appArgs, null, writer);
    }

    @Test
    public void testCorrectPathGiven() throws  IOException{
        Cd cd = new Cd();
        String currentDir = CurrentDirectory.getInstance().getCurrentDirectory();
        File file = new File(currentDir);
        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());
        appArgs.add(null);
        for (String s :
                directories) {
            appArgs.set(0, s);
            cd.exec(appArgs, null, writer);
            assertEquals(currentDir + "/" + s, CurrentDirectory.getInstance().getCurrentDirectory());
            CurrentDirectory.getInstance().setCurrentDirectory(currentDir);
        }
    }

    @Test (expected = RuntimeException.class)
    public void testDirNotExists() throws IOException{
        Cd cd = new Cd();
        String currentDir = CurrentDirectory.getInstance().getCurrentDirectory();
        File file = new File(currentDir);
        String[] directories = file.list((current, name) -> ! new File(current, name).isDirectory());
        appArgs.add(null);
        for (String s :
                directories) {
            appArgs.set(0, s);
            System.out.println(CurrentDirectory.getInstance().getCurrentDirectory());
            cd.exec(appArgs, null, writer);
        }
    }


}
