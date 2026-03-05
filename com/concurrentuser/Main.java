package com.concurrentuser;

// Main.java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; 

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserValidator validator = new UserValidator();
        UserDataStore dataStore = new UserDataStore();

        List<Thread> threadList = new ArrayList<>();
       
       
        // Use a lock for synchronization
        Object lock = new Object();

        boolean addMoreUsers = true;

        while (addMoreUsers) {
            // Simulate multiple users entering data concurrently
            for (int i = 0; i < 3; i++) {
                // Get user input
                System.out.print("Enter name for User " + (i + 1) + ": ");
                String name = scanner.nextLine();

                System.out.print("Enter age for User " + (i + 1) + ": ");
                int age = scanner.nextInt();

                // Create a UserProcessor and submit it to the thread pool
                UserProcessor processor = new UserProcessor(validator, dataStore, lock, name, age);
                //executorService.submit(processor);
                Thread t = new Thread(processor);
                threadList.add(t);

                // Consume the newline character left by nextInt
                scanner.nextLine();
            }

            // Ask if the user wants to add more users
            System.out.print("Do you want to add more users? (yes/no): ");
            String addMore = scanner.nextLine().toLowerCase();

            addMoreUsers = addMore.equals("yes");
        }

        // Shut down the thread pool
        //executorService.shutdown();
       
        for (Thread t : threadList)
        {
            t.start();
            try {
                t.join();
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
       

        // Display all users
        System.out.println("All Users: " + dataStore.getAllUsers());
    }
}