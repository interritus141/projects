package uk.ac.ucl.shell;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

import org.junit.Test;

import uk.ac.ucl.applications.Grep;

public class GrepTest extends BaseTest{

    @Test
    public void GrepSingleLine() throws Exception { // standard grep format
        appArgs.add("ucl"); 
        appArgs.add("testfile.txt"); 
        Grep grep = new Grep();
        grep.exec(appArgs, null, writer);
        out.close();
        
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "ucl");
    }    
    
    @Test
    public void GrepMultLines() throws Exception { // standard grep format
        appArgs.add("s"); 
        appArgs.add("testfile2.txt"); 
        Grep grep = new Grep();
        grep.exec(appArgs, null, writer);
        out.close();
        
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "singapore\nmalaysia\nmalaysia\nspain");
    }    
    
    @Test
    public void GrepMultFiles() throws Exception { // standard grep format
        appArgs.add("ucl"); 
        appArgs.add("testfile.txt"); 
        appArgs.add("testfile3.txt");
        Grep grep = new Grep();
        grep.exec(appArgs, null, writer);
        out.close();
        
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"testfile.txt:ucl\ntestfile3.txt:uclengineering\ntestfile3.txt:uclcomputerscience\ntestfile3.txt:uclcomp0010\ntestfile3.txt:uclsoftware");
    }

    @Test
    public void GrepStdIn() throws Exception { 
        BufferedReader reader = new BufferedReader(new FileReader("testfile3.txt"));
        appArgs.add("uclc"); 
        Grep grep = new Grep();
        grep.exec(appArgs, reader, writer);
        out.close();
        
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "uclcomputerscience\nuclcomp0010");
    }     
    
    @Test
    public void GrepStdIn2() throws Exception { 
        BufferedReader reader = new BufferedReader(new FileReader("testfile2.txt"));
        appArgs.add("..."); 
        Grep grep = new Grep();
        grep.exec(appArgs, reader, writer);
        out.close();
        
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "london\nLondon\nsingapore\nmalaysia\nmalaysia\ngermany\nItaly\nchina\nportugal\nportugal\nspain");
    } 
    
    // TEST EXCEPTIONS

    @Test(expected = RuntimeException.class)
    public void GrepMissingArgs() throws Exception {
        Grep grep = new Grep();
        grep.exec(appArgs, null, writer);
        out.close();
    }       
    
    @Test(expected = RuntimeException.class)
    public void GrepWrongArgs() throws Exception {
        appArgs.add("...");
        appArgs.add("20");
        Grep grep = new Grep();
        grep.exec(appArgs, null, writer);
        out.close();
    }    

    @Test(expected = RuntimeException.class)
    public void GrepWrongArgs2() throws Exception {
        appArgs.add("...");
        appArgs.add("testFolder");
        Grep grep = new Grep();
        grep.exec(appArgs, null, writer);
        out.close();
    } 
    
    @Test(expected = RuntimeException.class)
    public void GrepFileDoesntExist() throws Exception {
        appArgs.add("...");
        appArgs.add("thisfiledoesntexist.txt");
        Grep grep = new Grep();
        grep.exec(appArgs, null, writer);
        out.close();
    }     
    
    @Test(expected = RuntimeException.class)
    public void GrepFileNotReadable() throws Exception {
        appArgs.add("...");
        appArgs.add("notreadablefile.doc");
        Grep grep = new Grep();
        grep.exec(appArgs, null, writer);
        out.close();
    } 

    @Test(expected = IOException.class)
    public void GrepStdInFileDoesntExist() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("thisfiledoesntexist.txt"));
        appArgs.add("...");
        Grep grep = new Grep();
        grep.exec(appArgs, reader, writer);
        out.close();
    }
}
