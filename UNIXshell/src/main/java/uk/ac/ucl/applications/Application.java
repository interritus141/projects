package uk.ac.ucl.applications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public interface Application {
    CurrentDirectory directory = CurrentDirectory.getInstance();

    void exec(ArrayList<String> appArgs,  BufferedReader input, OutputStreamWriter writer) throws IOException; // add input if we need to add input

}
