/*
 * Author:          Annie Wu
 * Assignment:      1
 *
 * Class:           CS 4310 - Operating Systems
 * Instructor:      Dominick Atanasio
 * Date:            24 Feb 2019 
 *
 * Purpose:         This class contains all the process information.
 *
 */

import java.util.*;
import java.io.*;

public class ProcessInfo {

    //array list of all entries of information
    public ArrayList<Info> allInfo = new ArrayList<>(); 

    //add info entry to array list
    public void add(int cpuTime, int pID, int startBurst, int endBurst, int completionTime) {
        Info info = new Info(cpuTime, pID, startBurst, endBurst, completionTime);
        allInfo.add(info);
    }
    
    //write to file with print writer
    public void writeToFile(String fileName) throws FileNotFoundException {
        PrintWriter write = new PrintWriter(fileName);
        write.println("CpuTime, PID, StartingBurstTime, EndingBurstTime, CompletionTime,");
        //System.out.println("CpuTime PID StartingBurstTime EndingBurstTime CompletionTime");

        //format spaces in between numbers
        for (Info entry : allInfo) {
            write.println(entry.cpuTime +","+ entry.pid +","+ entry.startBurst +","+ entry.endBurst +","+ entry.completionTime +",");
            //System.out.printf("%7d %3d %17d %15d %14d\n", entry.cpuTime, entry.pid, entry.startBurst, entry.endBurst, entry.completionTime);
        }
        write.flush();
        write.close();
    }
 
    //compute average time
    public double computeAvg() {
        double sum = 0;
        
        //compute sum
        for (Info entry : allInfo) {
            sum += entry.completionTime;
        }
        
        //compute average by dividing sum by number of entries
        double avg = sum / allInfo.size();
        
        return avg;
    }
}