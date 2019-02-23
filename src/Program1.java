/*
 * Author:          Annie Wu
 * Assignment:      1
 *
 * Class:           CS 4310 - Operating Systems
 * Instructor:      Dominick Atanasio
 * Date:            24 Feb 2019 
 *
 * Purpose:         This class loads process information from an input file and
 *                  calls the scheduling algorithms, which outputs the scheduler details.
 *
 */

import java.util.*;
import java.io.*;

public class Program1 {

    //ask user to enter file name 
    //i.e. testdata1.txt
    private static File getFileName() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("Enter data file name: ");
            String fileName = scan.nextLine();
            File file = new File(fileName);
            //return file if it exists
            if (file.exists()) {
                return file;
            }
            System.out.println(fileName + " is invalid.\n");
        }
    }
    
    //load process info from input file
    private static Process[] loadFromFile(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        ArrayList<Process> processList = new ArrayList<>();
        //get pid, burst time, priority 
        while (scan.hasNextInt()) { 
            int pid = scan.nextInt();
            int burst = scan.nextInt();
            int priority = scan.nextInt();
            //create a Process with that info 
            Process process = new Process(pid, burst, priority);
            processList.add(process); 
            //System.out.println(process);
        }
        Process[] p = new Process[processList.size()];
        Process[] processArray = processList.toArray(p);
        return processArray;
    }
    
    //write the average times into a file with print writer
    private static void averages (String file_name, ProcessInfo first, ProcessInfo shortest, ProcessInfo rr20, ProcessInfo rr40, ProcessInfo lottery) throws FileNotFoundException {
        PrintWriter write = new PrintWriter(file_name);
        write.println("Algorithm, AverageCompletionTime");
        write.println("First, " + first.computeAvg());
        write.println("Shortest, " + shortest.computeAvg());
        write.println("RR20, " + rr20.computeAvg());
        write.println("RR40, " + rr40.computeAvg());
        write.println("Lottery, " + lottery.computeAvg());
        write.close(); //close print writer
    }
    
    //create deep clones of processes array so the info can be used for multiple algorithms
    private static Process[] clone(Process[] process) {
        Process[] copyOfData = new Process[process.length];
        for (int i = 0; i < process.length; i++) {
            copyOfData[i] = process[i].clone();
        }
        return copyOfData;
    }
   
    public static void main(String[] args) throws FileNotFoundException {
        //get file
        File file = args.length != 0 ? new File(args[0]) : getFileName();
        //get processes from file
        Process[] process = loadFromFile(file);
        
        //run algorithms with copies/deep clones of the process info
        ProcessInfo first = SchedulingAlgortihms.firstComeFirstServe(file ,clone(process));
        ProcessInfo shortest = SchedulingAlgortihms.shortestJobFirst(file ,clone(process));
        ProcessInfo rr20 = SchedulingAlgortihms.roundRobin(file, clone(process), 20); //quantum 20  
        ProcessInfo rr40 = SchedulingAlgortihms.roundRobin(file, clone(process), 40); //quantum 40 
        ProcessInfo lottery = SchedulingAlgortihms.lottery(file, clone(process), 40); //quantum 40 
               
        //name each output file: scheduler_name-testfile_name.csv
        String csv = "-" + file.getName().split("\\.(?=[^\\.]+$)")[0] + ".csv";
        //write averages to file
        averages("Averages" + csv, first, shortest, rr20, rr40, lottery);
    }
}
