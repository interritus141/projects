package uk.ac.ucl.shell;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

import org.junit.Test;

import uk.ac.ucl.applications.Sort;

public class SortTest extends BaseTest {
    @Test
    public void Sort() throws Exception {
        
        appArgs.add("testfile2.txt");
        Sort sort = new Sort();
        sort.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "Italy\nLondon\nchina\ngermany\nlondon\nmalaysia\nmalaysia\nportugal\nportugal\nsingapore\nspain");
    }    
    
    @Test
    public void SortRev() throws Exception {
        
        appArgs.add("-r");
        appArgs.add("testfile2.txt");
        Sort sort = new Sort();
        sort.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "spain\nsingapore\nportugal\nportugal\nmalaysia\nmalaysia\nlondon\ngermany\nchina\nLondon\nItaly");
    }    
    
    @Test
    public void SortStdIn() throws Exception {
        
        BufferedReader reader = new BufferedReader(new FileReader("testfile2.txt"));
        Sort sort = new Sort();
        sort.exec(appArgs, reader, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "Italy\nLondon\nchina\ngermany\nlondon\nmalaysia\nmalaysia\nportugal\nportugal\nsingapore\nspain");
    }      
    
    @Test
    public void SortStdInRev() throws Exception {
        
        BufferedReader reader = new BufferedReader(new FileReader("testfile2.txt"));
        appArgs.add("-r");
        Sort sort = new Sort();
        sort.exec(appArgs, reader, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "spain\nsingapore\nportugal\nportugal\nmalaysia\nmalaysia\nlondon\ngermany\nchina\nLondon\nItaly");
    }  

    // Test Exceptions

    @Test(expected = RuntimeException.class)
    public void SortTooManyArgs() throws Exception {
        appArgs.add("20");
        appArgs.add("testfile2.txt");
        appArgs.add("20");
        Sort sort = new Sort();
        sort.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void SortWrongArg() throws Exception {
        appArgs.add("-r");
        appArgs.add("20");
        Sort sort = new Sort();
        sort.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void SortWrongNumOfArgs() throws Exception {
        appArgs.add("-r");
        Sort sort = new Sort();
        sort.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void SortIllegalOption() throws Exception {
        appArgs.add("-n");
        appArgs.add("testfile2.txt");
        Sort sort = new Sort();
        sort.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void SortFileDoesntExist() throws Exception {
        appArgs.add("-n");
        appArgs.add("20");
        appArgs.add("thisfiledoesntexist.txt");
        Sort sort = new Sort();
        sort.exec(appArgs, null, writer);
        out.close();
    }
}
