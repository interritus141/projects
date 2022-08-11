package uk.ac.ucl.shell;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

import org.junit.Test;

import uk.ac.ucl.applications.Tail;

public class TailTest extends BaseTest {

    @Test
    public void Tail() throws Exception { // standard tail format
        appArgs.add("-n"); 
        appArgs.add("2"); 
        appArgs.add("testfile2.txt");
        Tail tail = new Tail();
        tail.exec(appArgs, null, writer);
        out.close();
        
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "portugal\nspain");
    }

    @Test
    public void Tail2() throws Exception { // standard prints out last 10

        appArgs.add("testfile2.txt");
        Tail tail = new Tail();
        tail.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"London\nsingapore\nmalaysia\nmalaysia\ngermany\nItaly\nchina\nportugal\nportugal\nspain");
    }

    @Test
    public void TailMaxLength() throws Exception { // more than actual number of lines
        appArgs.add("-n");
        appArgs.add("20");
        appArgs.add("testfile2.txt");
        Tail tail = new Tail();
        tail.exec(appArgs, null, writer);
        out.close();
        
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"london\nLondon\nsingapore\nmalaysia\nmalaysia\ngermany\nItaly\nchina\nportugal\nportugal\nspain");
    }

    @Test
    public void TailStdin() throws Exception { // read stdin
        BufferedReader reader = new BufferedReader(new FileReader("testfile2.txt"));
        appArgs.add("-n"); 
        appArgs.add("2"); 
        Tail tail = new Tail();
        tail.exec(appArgs, reader, writer);
        out.close();
        
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "portugal\nspain");
    }

    @Test
    public void TailStdin2() throws Exception { //  stdin last 10
        BufferedReader reader = new BufferedReader(new FileReader("testfile2.txt"));
        Tail tail = new Tail();
        tail.exec(appArgs, reader, writer);
        out.close();
        
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"London\nsingapore\nmalaysia\nmalaysia\ngermany\nItaly\nchina\nportugal\nportugal\nspain");
    }


    /*test Exceptions*/

    @Test(expected = RuntimeException.class)
    public void TailTooManyArgs() throws Exception {
        appArgs.add("-n");
        appArgs.add("20");
        appArgs.add("testfile2.txt");
        appArgs.add("20");
        Tail tail = new Tail();
        tail.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void TailWrongArg() throws Exception {
        appArgs.add("-4");
        appArgs.add("20");
        Tail tail = new Tail();
        tail.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void TailWrongNumOfArgs() throws Exception {
        appArgs.add("-n");
        appArgs.add("testfile2.txt");
        Tail tail = new Tail();
        tail.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void TailIllegalNum() throws Exception {
        appArgs.add("-n");
        appArgs.add("-2");
        appArgs.add("testfile2.txt");
        Tail tail = new Tail();
        tail.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void TailWrongArgs() throws Exception {
        appArgs.add("-e");
        appArgs.add("2");
        appArgs.add("testfile2.txt");
        Tail tail = new Tail();
        tail.exec(appArgs, null, writer);
        out.close();
    }

    @Test(expected = RuntimeException.class)
    public void TailFileDoesntExist() throws Exception {
        appArgs.add("-n");
        appArgs.add("20");
        appArgs.add("thisfiledoesntexist.txt");
        Tail tail = new Tail();
        tail.exec(appArgs, null, writer);
        out.close();   
    }
        
    @Test(expected = IOException.class)
    public void TailFileDoesntExistStdIn() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("thisfiledoesntexist.txt"));
        appArgs.add("-n");
        appArgs.add("20");
        Tail tail = new Tail();
        tail.exec(appArgs, reader, writer);
        out.close();
    }
}