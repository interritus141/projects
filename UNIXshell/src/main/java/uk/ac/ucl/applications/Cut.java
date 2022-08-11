package uk.ac.ucl.applications;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Cuts out sections from each line of a given file or stdin and prints the result to stdout.
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class Cut implements Application {

    /**
     * Executes Cut application.
     * @param appArgs arguments for the applications.
     * @param input standard input used if no files are provided.
     * @param writer standard output written to.
     * @throws IOException catch IO exceptions, whenever an input or output operation is failed or interpreted.
    */

    @Override
    public void exec(ArrayList<String> appArgs, BufferedReader input, OutputStreamWriter writer) throws IOException {
        String currentDirectory = directory.getCurrentDirectory();

        String regex = "(-?[1-9]+[0-9]*-?([1-9]+[0-9]*)*-?)(,-?[1-9]+[0-9]*-?([1-9]+[0-9]*)*-?)*"; // accepts the various formats of cut

        if (!(appArgs.get(0).equals("-b"))) {
            throw new RuntimeException("cut: incorrect argument" + appArgs.get(0));
        }
        if (!(appArgs.get(1).matches(regex))) {
            throw new RuntimeException("cut: wrong argument " + appArgs.get(1));
        }
        if (appArgs.size() > 2 && appArgs.size() != 3) {
            throw new RuntimeException("cut: wrong number of arguments");
        }
        if (appArgs.size() < 2) {
            throw new RuntimeException("cut: wrong number of arguments");
        }

        
        BufferedReader readfile;
        if (appArgs.size() == 3) {
            String filename = appArgs.get(2);
            File file = new File(currentDirectory + "/" + filename);
            
            if (file.exists()) {
                try {
                    readfile = new BufferedReader(new FileReader(file));
                }
                catch (IOException e) {
                    throw new RuntimeException("cannot open " + filename);
                }
            } else {
                throw new RuntimeException("file " + filename + " does not exist");
            }
        } else {
            readfile = input; // not specified, uses stdin

        } 

        String line;
        List<String> newLine = new ArrayList<>();

        while ((line = readfile.readLine()) != null) {
            String[] arguments = appArgs.get(1).split(","); // split args according to ,
            List<StartEndInterval> intervals = new ArrayList<>();

            for (String argument : arguments) {
                char[] argumentchars = argument.toCharArray();
                for (int j = 0; j < argumentchars.length; j++) {
                    int startindex = 0;
                    int lastindex = 0;
                    if (argumentchars.length == 1) {
                        startindex = Character.getNumericValue(argumentchars[j] - 1);
                        lastindex = Character.getNumericValue(argumentchars[j]);
                    } else if (argumentchars[j] == '-') {
                        if (j == 0) { // -3
                            startindex = 0;
                            lastindex = Character.getNumericValue(argumentchars[j + 1]);
                        } else if (j == argumentchars.length - 1) { // 5-
                            startindex = Character.getNumericValue(argumentchars[j - 1] - 1);
                            lastindex = line.length();
                        } else { // 1-3
                            startindex = Character.getNumericValue(argumentchars[j - 1] - 1);
                            lastindex = Character.getNumericValue(argumentchars[j + 1]);
                        }
                    }
                    if (startindex < lastindex && startindex < line.length()) {
                        StartEndInterval interval = new StartEndInterval(startindex, lastindex);
                        intervals.add(interval);
                    }
                }
            }
        
            List<StartEndInterval> combinedOverlapIntervals = checkOverlap(intervals);
            StringBuilder createNewLine = new StringBuilder();

            for (StartEndInterval interval : combinedOverlapIntervals) {
                if (interval.end > line.length()) {
                    interval.end = line.length(); // if interval is over the max length of line, just return till the end
                }
                createNewLine.append(line.substring(interval.start, interval.end)); // extract said interval from the original line and put it into a new line
            }

            newLine.add(createNewLine.toString());
            for (String lines: newLine) { 
                writer.write(lines);
                writer.write(System.getProperty("line.separator"));
                writer.flush();
            }
            newLine.clear(); 
        }
    }

    /**
     * Check for and combines overlapping range if there are.
     * @param intervals a list of {@code StartEndInterval} Objects 
     * @return a list of the resulting set of intervals
    */

    public List<StartEndInterval> checkOverlap(List<StartEndInterval> intervals) {
        List<StartEndInterval> sortedInterval = intervals.stream()
                                        .sorted(Comparator.comparing(StartEndInterval::returnStart)) // sorts the start intervals
                                        .collect(Collectors.toList());

        List<StartEndInterval> noOverlapIntervals = new ArrayList<>();
        int min = 0;
        int max = 0;
        int count = 0;

        for (StartEndInterval interval : sortedInterval) {
            if (interval.start > max) {
                if (count!= 0) {
                    noOverlapIntervals.add(new StartEndInterval(min, max)); // save the prev interval range
                }
               min = interval.start;
               max = interval.end; 
            } else if (interval.end >= max) {
                max = interval.end;
            }
            count++;
        }
        if (!noOverlapIntervals.contains(new StartEndInterval(min, max))) {
            noOverlapIntervals.add(new StartEndInterval(min, max));
        }
        return noOverlapIntervals;
    }

}