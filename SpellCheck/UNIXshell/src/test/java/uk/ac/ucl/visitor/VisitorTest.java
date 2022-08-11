package uk.ac.ucl.visitor;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.applications.CurrentDirectory;
import uk.ac.ucl.shell.Shell;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VisitorTest  {

    PipedInputStream in;
    PipedOutputStream out;
    ArrayList<String> appArgs;
    OutputStreamWriter writer;
    BufferedReader input;

    @Before
    public void setUp() throws Exception {
        in = new PipedInputStream();
        out = new PipedOutputStream(in);
        appArgs = new ArrayList<>();
        writer = new OutputStreamWriter(out);
        input = new BufferedReader(new InputStreamReader(in));
        CurrentDirectory.getInstance().setCurrentDirectory("/comp0010");

    }


    @After
    public void tearDown(){
        CurrentDirectory.getInstance().setCurrentDirectory("/");
    }

    @Test
    public void testDoublequoted() throws IOException {
        Shell.eval("echo \"foo\"", out);
        out.close();
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"foo");
    }

    @Test
    public void testNestedBackquote() throws IOException {
        Shell.eval("echo \"`echo foo`\"", out);
        out.close();
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result,"foo\n");
    }

    @Test
    public void testSeq() throws IOException {
        Shell.eval("echo foo; echo bar", out);
        out.close();
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals("foo\nbar", result);
    }

    @Test
    public void testPipeChain() throws IOException {
        Shell.eval("cat testfile.txt | cat | cat", out);
        out.close();
        String result = input.readLine();
        assertEquals(result,"ucl");
    }

    @Test
    public void testPipeInputRedir() throws IOException{
        Shell.eval("echo abc | cut -b 1", out);
        out.close();
        String result = input.readLine();
        assertEquals("a", result);

    }

    @Test
    public void testGlobbing() throws IOException{
        CurrentDirectory.getInstance().setCurrentDirectory("/");
//        Shell.eval("ls", System.out);
//        System.out.println(CurrentDirectory.getInstance().getCurrentDirectory());
//        Shell.eval("echo comp0010", System.out);
        Shell.eval("echo comp0010/*", out);
        out.close();
        String rawResult = input.readLine();
        String[] result = rawResult.split(" ");
        String[] expected = new String[] {
                "comp0010/src",
                "comp0010/doc",
                "comp0010/system_test",
                "comp0010/sh",
                "comp0010/tools",
                "comp0010/target"
        };
        for (String s :
                expected) {
            assertTrue(Arrays.asList(result).contains(s));
        }
        CurrentDirectory.getInstance().setCurrentDirectory("/comp0010");
    }


    @Test
    public void testCall() throws IOException {
        Shell.eval("echo \"foo\"", out);
        out.close();
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals("foo", result);
    }

    @Test
    public void testBackquote() throws  IOException{
        Shell.eval("echo `cat testfile.txt`", out);
        out.close();
        String result = input.readLine();
        assertEquals("ucl", result);
    }

    @Test
    public void testInputRedir() throws IOException{
        Shell.eval("cat <testfile.txt ", out);
        out.close();
        String result = input.readLine();
        assertEquals("ucl", result);
    }

    @Test
    public void testOutputRedir() throws IOException{
        Shell.eval("cat testfile.txt >file_copy.txt", out);
        Shell.eval("cat file_copy.txt", out);

        out.close();
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals("ucl", result);
    }

    @Test
    public void testRedirBeforeApp() throws IOException{
        Shell.eval(">file_copy.txt <testfile.txt cat", out);
    }

    @Test
    public void testDoublequoteEcho() throws IOException{
        Shell.eval("\"echo\" foo", out);
        out.close();
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals("foo", result);
    }

    @Test
    public void testSinglequoteEcho() throws IOException{
        Shell.eval("echo 'foo'", out);
        out.close();
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals("foo", result);
    }

    @Test(expected = RuntimeException.class)
    public void testMultipleInputRedirs() throws IOException{
        Shell.eval("<file.txt <testfile.txt cat", out);
    }

    @Test(expected = RuntimeException.class)
    public void testMultipleOutputRedirs() throws IOException{
        Shell.eval(">file.txt >file.txt cat", out);
    }

    @Test(expected = RuntimeException.class)
    public void testBadInputRedir() throws IOException{
        Shell.eval("<nonexist.txt cat", out);
    }

    @Test(expected = RuntimeException.class)
    public void testBadOutputRedir() throws IOException{
        Shell.eval(">nonexist.txt cat", out);
    }


    @Test
    public void testLs() throws IOException{
        CurrentDirectory.getInstance().setCurrentDirectory("/");
        Shell.eval("ls", out);
        out.close();
        String result = input.readLine();
        assertEquals("home\tsrv\tetc\topt\troot\tlib\tmnt\tusr\tmedia\tlib64\tsys\tdev\tsbin\tboot\tbin\trun\tproc\ttmp\tvar\tcomp0010\t", result);
        CurrentDirectory.getInstance().setCurrentDirectory("/comp0010");
    }


    @Test
    public void testCd() throws IOException{
        Shell.eval("cd src", out);
        String currentDir = CurrentDirectory.getInstance().getCurrentDirectory();
        assertEquals("/comp0010/src", currentDir);
        CurrentDirectory.getInstance().setCurrentDirectory("/comp0010");
    }

    @Test
    public void testPwd() throws IOException{
        Shell.eval("pwd", out);
        out.close();
        String result = input.readLine();
        assertEquals("/comp0010", result);
    }

    @Test
    public void testHead() throws IOException{
        Shell.eval("head testfile.txt", out);

        out.close();
        String result = input.readLine();
        assertEquals("ucl", result);
    }

    @Test
    public void testTail() throws IOException{
        Shell.eval("tail testfile.txt", out);
        out.close();
        String result = input.readLine();
        assertEquals("ucl", result);
    }

    @Test
    public void testCat() throws IOException{
        Shell.eval("cat testfile.txt", out);

        out.close();
        String result = input.readLine();
        assertEquals("ucl", result);
    }

    @Test
    public void testEcho() throws IOException{
        Shell.eval("echo foo", out);

        out.close();
        String result = input.readLine();
        assertEquals("foo", result);
    }

    @Test
    public void testGrep() throws IOException{
        Shell.eval("grep '..' testfile.txt", out);

        out.close();
        String result = input.readLine();
        assertEquals("ucl", result);
    }

    @Test
    public void testFind() throws IOException{
        Shell.eval("find -name testf*le.txt", out);

        out.close();
        String result = input.readLine();
        assertEquals("./testfile.txt", result);
    }

    @Test
    public void testCut() throws IOException{
        Shell.eval("cut -b 1 testfile.txt", out);

        out.close();
        String result = input.readLine();
        assertEquals("u", result);
    }

    @Test
    public void testSort() throws IOException{
        Shell.eval("sort testfile.txt", out);

        out.close();
        String result = input.readLine();
        assertEquals("ucl", result);
    }

    @Test
    public void testUniq() throws IOException{
        Shell.eval("uniq testfile.txt", out);

        out.close();
        String result = input.readLine();
        assertEquals("ucl", result);
    }

    @Test
    public void testUnsafe() throws IOException{
        Shell.eval("_cat testfile.txt", out);

        out.close();
        String result = input.readLine();
        assertEquals("ucl", result);
    }

    @Test(expected = RuntimeException.class)
    public void testBadApp() throws Exception{
        Shell.eval("badapp foo", out);
    }
}

