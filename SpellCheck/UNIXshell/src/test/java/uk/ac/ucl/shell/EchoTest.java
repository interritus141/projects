package uk.ac.ucl.shell;

import org.junit.Test;
import uk.ac.ucl.applications.Echo;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class EchoTest extends BaseTest{
    
    @Test
    public void testNoArgs() throws IOException{
        Echo echo = new Echo();
        echo.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "");
    }

    @Test
    public void testOneArg() throws IOException{
        Echo echo = new Echo();
        appArgs.add("one");
        echo.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "one");
    }

    @Test
    public void testManyArgs() throws IOException{
        Echo echo = new Echo();
        appArgs.add("one");
        appArgs.add("two");
        echo.exec(appArgs, null, writer);
        out.close();

        String result = input.lines().collect(Collectors.joining("\n"));
        assertEquals(result, "one two");
    }
}
