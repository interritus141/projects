package uk.ac.ucl.shell;

import org.junit.Test;

import uk.ac.ucl.applications.Cut;
import uk.ac.ucl.applications.Head;
import uk.ac.ucl.applications.Unsafe;

import static org.junit.Assert.*;

import java.util.stream.Collectors;

public class UnsafeTest extends BaseTest {

    @Test
    public void TestExceptionInUnsafe() throws Exception {
        Unsafe Unsafe = new Unsafe(new Cut());
        Unsafe.exec(appArgs,null,writer);
        out.close();
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "Index 0 out of bounds for length 0");
    }

    @Test
    public void TestHeadInUnsafe() throws Exception {
        Unsafe unsafe = new Unsafe(new Head());
        appArgs.add("testfile2.txt");
        unsafe.exec(appArgs,null,writer);
        out.close();
       
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"london\nLondon\nsingapore\nmalaysia\nmalaysia\ngermany\nItaly\nchina\nportugal\nportugal");
    }

}