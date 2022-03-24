/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDAccess;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;


/**
 *
 * @author ajd34
 */
public class Log {

    //filename and path 
    private static final String filename = "log.txt";

    public static void log(String userName) {
        try {


            //filewriter object 
            FileWriter outFile = new FileWriter(filename, true);
            //create and open file 
            PrintWriter pw = new PrintWriter(outFile); 
            pw.append( userName + " logged in at: " + LocalDateTime.now() + "\n");
            pw.flush();
            pw.close(); 
                }
        catch(IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
