/*
 * Author:          Annie Wu
 * Assignment:      1
 *
 * Class:           CS 4310 - Operating Systems
 * Instructor:      Dominick Atanasio
 * Date:            24 Feb 2019 
 *
 * Purpose:         This class creates a process information 
 *                  entry with all of the information.
 *
 */

public class Info {

    public int cpuTime, pid, startBurst, endBurst, completionTime;
    
    Info(int cpu, int p, int start, int end, int complete) {
        cpuTime = cpu;
        pid = p;
        startBurst = start;
        endBurst = end;
        completionTime = complete;
    }
}
