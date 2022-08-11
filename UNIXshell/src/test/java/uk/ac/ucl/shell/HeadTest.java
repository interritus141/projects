package uk.ac.ucl.shell;
import org.junit.Test;

import uk.ac.ucl.applications.Head;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;


public class HeadTest extends BaseTest{

    @Test
    public void Head() throws Exception { // standard head format
        appArgs.add("-n"); 
        appArgs.add("2"); 
        appArgs.add("testfile2.txt");
        Head Head = new Head();
        Head.exec(appArgs, null, writer);
        out.close();
        
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "london\nLondon");
    }

    @Test
    public void Head2() throws Exception { // standard prints out first 10
    
        appArgs.add("testfile2.txt");
        Head Head = new Head();
        Head.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"london\nLondon\nsingapore\nmalaysia\nmalaysia\ngermany\nItaly\nchina\nportugal\nportugal");
    }

    @Test
    public void HeadMaxLength() throws Exception { // more than actual number of lines
        appArgs.add("-n");
        appArgs.add("20");
        appArgs.add("testfile2.txt");
        Head Head = new Head();
        Head.exec(appArgs, null, writer);
        out.close();
        
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"london\nLondon\nsingapore\nmalaysia\nmalaysia\ngermany\nItaly\nchina\nportugal\nportugal\nspain");
    }

    @Test
    public void HeadStdin() throws Exception { // read stdin
        BufferedReader reader = new BufferedReader(new FileReader("testfile2.txt"));
        appArgs.add("-n"); 
        appArgs.add("2"); 
        Head Head = new Head();
        Head.exec(appArgs, reader, writer);
        out.close();
        
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "london\nLondon");
    }

    @Test
    public void HeadStdin2() throws Exception { //  stdin first 10
        BufferedReader reader = new BufferedReader(new FileReader("testfile2.txt"));
        Head Head = new Head();
        Head.exec(appArgs, reader, writer);
        out.close();
        
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"london\nLondon\nsingapore\nmalaysia\nmalaysia\ngermany\nItaly\nchina\nportugal\nportugal");
    }


    /*test Exceptions*/

    @Test(expected = RuntimeException.class)
    public void HeadTooManyArgs() throws Exception {
        appArgs.add("-n");
        appArgs.add("20");
        appArgs.add("testfile2.txt");
        appArgs.add("20");
        Head Head = new Head();
        Head.exec(appArgs, null, writer);
        out.close();
        
    }

    @Test(expected = RuntimeException.class)
    public void HeadWrongArg() throws Exception {
        appArgs.add("-4");
        appArgs.add("20");
        Head Head = new Head();
        Head.exec(appArgs, null, writer);
        out.close();
        
    }

    @Test(expected = RuntimeException.class)
    public void HeadWrongNumOfArgs() throws Exception {
        appArgs.add("-n");
        appArgs.add("testfile2.txt");
        Head Head = new Head();
        Head.exec(appArgs, null, writer);
        out.close();
       
    }

    @Test(expected = RuntimeException.class)
    public void HeadIllegalNum() throws Exception {
        appArgs.add("-n");
        appArgs.add("-2");
        appArgs.add("testfile2.txt");
        Head Head = new Head();
        Head.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void HeadWrongArgs() throws Exception {
        appArgs.add("-e");
        appArgs.add("2");
        appArgs.add("testfile2.txt");
        Head Head = new Head();
        Head.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void HeadFileDoesntExist() throws Exception {
        appArgs.add("-n");
        appArgs.add("20");
        appArgs.add("thisfiledoesntexist.txt");
        Head Head = new Head();
        Head.exec(appArgs, null, writer);
        out.close();
    }

            
    @Test(expected = IOException.class)
    public void HeadFileDoesntExistStdIn() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("thisfiledoesntexist.txt"));
        appArgs.add("-n");
        appArgs.add("20");
        Head Head = new Head();
        Head.exec(appArgs, reader, writer);
        out.close();
    }
}