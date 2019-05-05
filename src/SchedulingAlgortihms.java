/**
 * Author:          Annie Wu
 * Assignment:      1
 *
 * Class:           CS 4310 - Operating Systems
 * Instructor:      Dominick Atanasio
 * Date:            24 Feb 2019 
 *
 * Purpose:         This class contains the scheduling algorithms.
 *
 */

import java.util.*;
import java.io.*;

public class SchedulingAlgortihms {
    
    final static int cost = 3; //3 units of cpu time to switch a process
    //Assume that, if the same process continues to run after a context switch, 
    //there will still be a context-switching cost.

    //processes arrive in the order in which are read from the file
    public static ProcessInfo firstComeFirstServe( File file, Process[] processes) throws FileNotFoundException {
        int cpuTime = 0;
        ProcessInfo info = new ProcessInfo();
        //add runtime and cost to cpu time
        for (Process p : processes) {
            cpuTime += p.computeTime(info, cpuTime);
            cpuTime += cost;
        }
        System.out.println("\nRunning First Come First Serve...");
        String csv = "-" + file.getName().split("\\.(?=[^\\.]+$)")[0] + ".csv";
        info.writeToFile("First_Come_First_Serve" + csv);
        System.out.println("Output to First_Come_First_Serve" + csv);
        return info;
    }
    
    public static ProcessInfo shortestJobFirst(File file, Process[] processes) throws FileNotFoundException {
        int cpuTime = 0;
        //sort the processes
        Arrays.sort(processes);
        ProcessInfo info = new ProcessInfo();
        //add process runtime and cost to cpu time
        for (Process p : processes) {
            cpuTime += p.computeTime(info, cpuTime);
            cpuTime += cost;     
        }
        System.out.println("\nRunning Shortest Job First...");
        String csv = "-" + file.getName().split("\\.(?=[^\\.]+$)")[0] + ".csv";
        info.writeToFile("Shortest_Job_first" + csv);
        System.out.println("Output to Shortest_Job_First" + csv);
        return info; 
    }

    public static ProcessInfo roundRobin(File file, Process[] processes, int quantum) throws FileNotFoundException {
        int cpuTime = 0;
        //put array of processes into array list 
        ArrayList<Process> processes_list = new ArrayList<>(Arrays.asList(processes));
        ProcessInfo info = new ProcessInfo();
        int index = 0;
        
        while (!processes_list.isEmpty()) {
            Process p = processes_list.get(index % processes_list.size());
            //add runtime and cost to cpu time
            cpuTime += p.computeTime(info, cpuTime, quantum);
            cpuTime += cost;
            //
            if (p.endTime != 0) {
                processes_list.remove(p);
            }
            index++;   
        }
        System.out.println("\nRunning Round Robin " + quantum + "...");
        String csv = "-" + file.getName().split("\\.(?=[^\\.]+$)")[0] + ".csv";
        info.writeToFile("Round_Robin_" + quantum + csv);        
        System.out.println("Output to Round_Robin_" + quantum + csv);
        return info;
    }

    public static ProcessInfo lottery(File file, Process[] processes, int quantum) throws FileNotFoundException {
        int cpuTime = 0;
        Random r = new Random();
        //put array of processes into array list 
        ArrayList<Process> processes_list = new ArrayList<>(Arrays.asList(processes));
        ProcessInfo info = new ProcessInfo();

        while (!processes_list.isEmpty()) {
            int prioritySum = 0;
            for (Process p : processes_list) {
                prioritySum += p.priority;
            }
            //random lottery number
            int i = r.nextInt(prioritySum);
            //reset priority sum
            prioritySum = 0;
            for (int j = 0; j < processes_list.size(); j++) {
                prioritySum += processes_list.get(j).priority;
                if (prioritySum >= i) {
                i = j;
                break;
                }
            }
            //get randomly chosen process 
            Process p = processes_list.get(i);
            //add runtime and cost to cpu time
            cpuTime += p.computeTime(info, cpuTime, quantum);
            cpuTime += cost;
            
            if (p.endTime != 0) {
                processes_list.remove(p);
            }
        }
        System.out.println("\nRunning Lottery...");
        String csv = "-" + file.getName().split("\\.(?=[^\\.]+$)")[0] + ".csv";
        info.writeToFile("Lottery" + csv);
        System.out.println("Output to Lottery" + csv);
        return info;
    }
}
