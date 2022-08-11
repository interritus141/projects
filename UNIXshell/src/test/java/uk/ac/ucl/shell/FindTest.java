package uk.ac.ucl.shell;

import java.util.stream.Collectors;

import uk.ac.ucl.applications.Find;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FindTest extends BaseTest {

    @Test
    public void Find() throws Exception {
        appArgs.add("testFolder");
        appArgs.add("-name");
        appArgs.add("testFind1.txt");
        Find find = new Find();
        find.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "testFolder/testFind1.txt");
    }    
    
    @Test
    public void FindCurrDir() throws Exception {
        appArgs.add("-name");
        appArgs.add("testfile2.txt");
        Find find = new Find();
        find.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "./testfile2.txt");
    }   
    
    @Test
    public void FindPatternSpecificDir() throws Exception {
        appArgs.add("testFolder");
        appArgs.add("-name");
        appArgs.add("*.txt");
        Find find = new Find();
        find.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "testFolder/testFind1.txt\ntestFolder/testFind2.txt\ntestFolder/testFind3.txt");
    }

   @Test
   public void FindPatternCurrDir() throws Exception {
       appArgs.add("-name");
       appArgs.add("*.txt");
       Find find = new Find();
       find.exec(appArgs, null, writer);
       out.close();

       String result = input.lines().collect(Collectors.joining("\n"));
       assertEquals(result, "./testFolder/testFind1.txt\n./testFolder/testFind2.txt\n./testFolder/testFind3.txt\n./testfile.txt\n./testfile2.txt\n./testfile3.txt");
   }

    // TEST EXCEPTIONS

    @Test(expected = RuntimeException.class)
    public void FindMissingArgs() throws Exception {
        Find find = new Find();
        find.exec(appArgs, null, writer);
        out.close();
    } 

    @Test(expected = RuntimeException.class)
    public void FindMissingNameArg() throws Exception {
        appArgs.add("*.txt");
        Find find = new Find();
        find.exec(appArgs, null, writer);
        out.close();
    }      
    
    @Test(expected = RuntimeException.class)
    public void FindMissingFileNameArg() throws Exception {
        appArgs.add("-name");
        Find find = new Find();
        find.exec(appArgs, null, writer);
        out.close();
    }  

    @Test(expected = RuntimeException.class)
    public void FindTooManyArgs() throws Exception {
        appArgs.add("comp0010");
        appArgs.add("-name");
        appArgs.add("*.txt");
        appArgs.add("test.txt");
        Find find = new Find();
        find.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void FindWrongArgs() throws Exception {
        appArgs.add("-e");
        appArgs.add("test");
        Find find = new Find();
        find.exec(appArgs, null, writer);
        out.close();
    } 
    
    @Test(expected = RuntimeException.class)
    public void FindWrongArgs2() throws Exception {
        appArgs.add("comp0010");
        appArgs.add("-r");
        appArgs.add("test.txt");
        Find find = new Find();
        find.exec(appArgs, null, writer);
        out.close();
    }     

    @Test(expected = RuntimeException.class)
    public void FindFolderDoesntExist() throws Exception {
        appArgs.add("thisfolderdoesntexist");
        appArgs.add("-name");
        appArgs.add("test.txt");
        Find find = new Find();
        find.exec(appArgs, null, writer);
        out.close();
    }      
    
    @Test(expected = RuntimeException.class)
    public void FindFileDoesntExist() throws Exception {
        appArgs.add("-name");
        appArgs.add("thisfiledoesntexist.txt");
        Find find = new Find();
        find.exec(appArgs, null, writer);
        out.close();
    }  
}
