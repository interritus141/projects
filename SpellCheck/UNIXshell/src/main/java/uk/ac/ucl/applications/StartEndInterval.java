package uk.ac.ucl.applications;

/**
 * Used in the Cut application to store an interval
 * @author Lo Pei Xuan
 * @author Loo Cheng
 * @author Gui Cheng Tong
*/

public class StartEndInterval {
    int start;
    int end;

    StartEndInterval(int start, int end) 
    { 
        this.start=start; 
        this.end=end; 
    }

    public int returnStart() {
            return start;
    }
    
}
