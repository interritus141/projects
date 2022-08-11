package uk.ac.ucl.shell;
import org.junit.Test;


import uk.ac.ucl.applications.Cat;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class CatTest extends BaseTest {

    @Test
    public void Cat() throws Exception {
        
        appArgs.add("testfile.txt"); 
        appArgs.add("testfile2.txt");
        Cat Cat = new Cat();
        Cat.exec(appArgs, null ,writer);
        out.close();
        
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"ucl\nlondon\nLondon\nsingapore\nmalaysia\nmalaysia\ngermany\nItaly\nchina\nportugal\nportugal\nspain");
    }   

    @Test
    public void CatStdinString() throws Exception {
        
        BufferedReader reader = new BufferedReader(new StringReader("testcatstdin"));
        Cat Cat = new Cat();
        Cat.exec(new ArrayList<String>(), reader ,writer);
        out.close();
       
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "testcatstdin");
    }

    /* BaseTest Exceptions */

    @Test(expected = RuntimeException.class)
    public void CatFileDoesntExist() throws Exception {
 
        appArgs.add("testfile2.txt"); 
        appArgs.add("thisfiledoesntexist.txt");
        Cat Cat = new Cat();
        Cat.exec(appArgs, null ,writer);
        out.close();

    }
}
