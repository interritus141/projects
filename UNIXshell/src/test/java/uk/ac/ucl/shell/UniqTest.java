package uk.ac.ucl.shell;
import org.junit.Test;


import uk.ac.ucl.applications.Uniq;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

public class UniqTest extends BaseTest {

    @Test
    public void UniqCaseSensitive() throws Exception {
        appArgs.add("testfile2.txt");
        Uniq Uniq = new Uniq();
        Uniq.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"london\nLondon\nsingapore\nmalaysia\ngermany\nItaly\nchina\nportugal\nspain");
    }

    @Test
    public void UniqCaseInsensitive() throws Exception {
        appArgs.add("-i");
        appArgs.add("testfile2.txt");
        Uniq Uniq = new Uniq();
        Uniq.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"london\nsingapore\nmalaysia\ngermany\nItaly\nchina\nportugal\nspain");
    }

    @Test
    public void UniqStdinCaseSensitive() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("testfile2.txt"));
        Uniq Uniq = new Uniq();
        Uniq.exec(appArgs, reader, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"london\nLondon\nsingapore\nmalaysia\ngermany\nItaly\nchina\nportugal\nspain");
    }

    @Test
    public void UniqStdinCaseInsensitive() throws Exception {
        appArgs.add("-i");
        BufferedReader reader = new BufferedReader(new FileReader("testfile2.txt"));
        Uniq Uniq = new Uniq();
        Uniq.exec(appArgs, reader, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"london\nsingapore\nmalaysia\ngermany\nItaly\nchina\nportugal\nspain");
    }


    /*test Exceptions*/

    @Test(expected = RuntimeException.class)
    public void UniqWrongCommand() throws Exception {
        appArgs.add("-n");
        appArgs.add("testfile2.txt");
        Uniq Uniq = new Uniq();
        Uniq.exec(appArgs, null, writer);
        out.close();   
    }

    @Test(expected = RuntimeException.class)
    public void UniqWrongNumberOfArguments() throws Exception {
        appArgs.add("-i");
        appArgs.add("testfile.txt");
        appArgs.add("testfile2.txt");
        Uniq Uniq = new Uniq();
        Uniq.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void UniqFileDoesntExist() throws Exception {
        appArgs.add("-i");
        appArgs.add("thisfiledoesntexist.txt");
        Uniq Uniq = new Uniq();  
        Uniq.exec(appArgs, null, writer);
        out.close();
    }   
}
