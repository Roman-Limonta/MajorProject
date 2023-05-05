package com.teamlimonta.majorproject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Account {

    String userLoginName;
    String password;
    File userFile;

    public Account(String userLoginName, String password) {
        this.userLoginName = userLoginName;
        this.password = password;

        try {
            FileWriter myWriter = new FileWriter("C:\\Users\\Roman\\Desktop\\MajorProject\\userFiles\\" + userLoginName + ".txt");
//            for (String string : listToWrite) {
//                myWriter.write(string);
//                myWriter.write("\r\n");
//            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            //e.printStackTrace();
        }

        System.out.println("test");

        System.out.println("New account made");
    }
}
