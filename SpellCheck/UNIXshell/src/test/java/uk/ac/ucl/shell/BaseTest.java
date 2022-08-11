package uk.ac.ucl.shell;

import org.junit.Before;

import java.io.*;
import java.util.ArrayList;

public class BaseTest {
    PipedInputStream in;
    PipedOutputStream out;
    ArrayList<String> appArgs;
    OutputStreamWriter writer;
    BufferedReader input;

    @Before
    public void setup() throws Exception {
        in = new PipedInputStream();
        out = new PipedOutputStream(in);
        appArgs = new ArrayList<>();
        writer = new OutputStreamWriter(out);
        input = new BufferedReader(new InputStreamReader(in));

    }
}
