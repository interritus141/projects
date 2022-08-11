package uk.ac.ucl.shell;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.applications.CurrentDirectory;
import uk.ac.ucl.applications.Pwd;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class PwdTest {
    PipedInputStream in;
    PipedOutputStream out;
    ArrayList<String> appArgs;
    OutputStreamWriter writer;
    BufferedReader input;

    @Before
    public void setup() throws Exception {
        in = new PipedInputStream();
        out = new PipedOutputStream(in);
        appArgs = new ArrayList<String>();
        writer = new OutputStreamWriter(out);
        input = new BufferedReader(new InputStreamReader(in));

    }

    @Test
    public void testPwd() throws IOException {
        Pwd pwd = new Pwd();
        String currentDirectory = CurrentDirectory.getInstance().getCurrentDirectory();
        pwd.exec(appArgs, null, writer);
        out.close();
        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, currentDirectory);
    }
}
