package uk.ac.ucl.shell;
import org.junit.Test;


import uk.ac.ucl.applications.Cut;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

public class CutTest extends BaseTest {

    @Test
    public void CutWithoutRanges() throws Exception { //Tabs and backspaces are treated like as a character of 1 byte.
        appArgs.add("-b"); 
        appArgs.add("1,2,3");
        appArgs.add("testfile2.txt");
        Cut Cut = new Cut();
        Cut.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "lon\nLon\nsin\nmal\nmal\nger\nIta\nchi\npor\npor\nspa");
    }

    @Test
    public void CutRange() throws Exception { //Tabs and backspaces are treated like as a character of 1 byte.
        appArgs.add("-b"); 
        appArgs.add("1-3");
        appArgs.add("testfile2.txt");
        Cut Cut = new Cut();
        Cut.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "lon\nLon\nsin\nmal\nmal\nger\nIta\nchi\npor\npor\nspa");
    }

    @Test
    public void CutRangesWithIntersection() throws Exception {
        appArgs.add("-b"); 
        appArgs.add("1-3,1-5");
        appArgs.add("testfile2.txt");
        Cut Cut = new Cut();
        Cut.exec(appArgs, null, writer);
        out.close();
      
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "londo\nLondo\nsinga\nmalay\nmalay\ngerma\nItaly\nchina\nportu\nportu\nspain");
    }

    @Test
    public void CutRangesWithIntersectionAndFromBeginning() throws Exception {
        appArgs.add("-b"); 
        appArgs.add("-3,1-4"); //-3 indicate from 1st byte to 3rd byte of a line
        appArgs.add("testfile2.txt");
        Cut Cut = new Cut();
        Cut.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "lond\nLond\nsing\nmala\nmala\ngerm\nItal\nchin\nport\nport\nspai");
    }

    @Test
    public void CutRangesWithNoIntersection() throws Exception {
        appArgs.add("-b"); 
        appArgs.add("1-3,6-8");
        appArgs.add("testfile2.txt");
        Cut Cut = new Cut();
        Cut.exec(appArgs, null, writer);
        out.close();
       
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "lonn\nLonn\nsinpor\nmalsia\nmalsia\ngerny\nIta\nchi\nporgal\nporgal\nspa");
    }

    @Test
    public void CutFromBeginningAndToEnd() throws Exception {
        appArgs.add("-b"); 
        appArgs.add("-3,6-"); // 6- indicate from 6th byte to end byte of a line
        appArgs.add("testfile2.txt");
        Cut Cut = new Cut();
        Cut.exec(appArgs, null, writer);
        out.close();
  
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "lonn\nLonn\nsinpore\nmalsia\nmalsia\ngerny\nIta\nchi\nporgal\nporgal\nspa");
    }

    @Test
    public void CutStdin() throws Exception {
        appArgs.add("-b"); 
        appArgs.add("1-3");
        BufferedReader reader = new BufferedReader(new FileReader("testfile2.txt"));
        Cut Cut = new Cut();
        Cut.exec(appArgs, reader, writer);
        out.close();
       
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "lon\nLon\nsin\nmal\nmal\nger\nIta\nchi\npor\npor\nspa");
    } 
    
    /* BaseTest Exceptions */

    @Test(expected = RuntimeException.class)
    public void CutLessThan2Args() throws Exception {
        appArgs.add("-b"); 
        Cut cut = new Cut();
        cut.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void CutExtraArgs() throws Exception {
        appArgs.add("-b"); 
        appArgs.add("3-5");
        appArgs.add("testfile2.txt");
        appArgs.add("4");
        Cut cut = new Cut();
        cut.exec(appArgs, null, writer);
        out.close();     
    }

    @Test(expected = RuntimeException.class)
    public void CutMissingArgs() throws Exception {
        appArgs.add("-b"); 
        Cut cut = new Cut();
        cut.exec(appArgs, null, writer);
        out.close();
    }    
    
    @Test(expected = RuntimeException.class)
    public void CutMissingArgs2() throws Exception {
        appArgs.add("-n"); 
        Cut cut = new Cut();
        cut.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void CutWrongCommand() throws Exception {
        appArgs.add("-n"); 
        appArgs.add("3-5"); 
        appArgs.add("testfile2.txt"); 
        Cut cut = new Cut();
        cut.exec(appArgs, null, writer);
        out.close();       
    }

    @Test(expected = RuntimeException.class)
    public void CutWrongIntersectionFormat() throws Exception {
        appArgs.add("-b"); 
        appArgs.add("--3-5"); 
        appArgs.add("testfile2.txt"); 
        Cut cut = new Cut();
        cut.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void CutFileDoesntExist() throws Exception {
        appArgs.add("-b"); 
        appArgs.add("3-5"); 
        appArgs.add("thisfiledoesntexist.txt"); 
        Cut cut = new Cut();
        cut.exec(appArgs, null, writer);
        out.close();
    }
}
