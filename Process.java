/*
 * Author:          Annie Wu
 * Assignment:      1
 *
 * Class:           CS 4310 - Operating Systems
 * Instructor:      Dominick Atanasio
 * Date:            24 Feb 2019 
 *
 * Purpose:         This class contains the process information from input files 
 *                  and computes the process runtime.
 *
 */

public class Process implements Comparable<Process>{
    
    int pid, burstTime, priority, endTime;
    
    Process (int pID, int burst, int p) {
        pid = pID;
        burstTime = burst;
        priority = p;
    }

    //given info and cpu time, compute time with info, cpu time, and burst time 
    //as the quantum b/c there is no quantum for some algorithms
    public int computeTime(ProcessInfo info, int cpuTime) {
        return computeTime(info, cpuTime, burstTime);
    }
    
    //computer time with info, cpu time, and quantum 
    public int computeTime(ProcessInfo info, int cpuTime, int quantum) {
        if (quantum >= burstTime) {
            endTime = cpuTime + burstTime;
            int totalTime = burstTime;
            info.add(cpuTime, pid, burstTime, 0, endTime);
            this.burstTime = 0;
            return totalTime;
            } 
        
        else {
            info.add(cpuTime, pid, burstTime, burstTime - quantum, 0);      
            burstTime -= quantum;
            return quantum;
        }
    }
    
    @Override
    //override clone to use in Program1 
    //create deep clone of a Process to be able to use in multiple algorithms 
    public Process clone() {
        return new Process(pid, burstTime, priority);
    }
    
    //format printing pID, burst time, and priority
    //PID:
    //    burst time: 
    //    priority: 
    @Override
    public String toString() {
        return "PID " + pid + ": \n" +
                "\tBurst time: " + burstTime + "\n" +
                "\tPriority: " + priority + "\n";
    }

    @Override
    //for Arrays.sort
    public int compareTo(Process p2) {
        return burstTime - p2.burstTime;
    }
}
