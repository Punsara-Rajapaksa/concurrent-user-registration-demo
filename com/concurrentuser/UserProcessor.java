package com.concurrentuser;

// UserProcessor.java
public class UserProcessor implements Runnable {
    private UserValidator validator;
    private UserDataStore dataStore;
    private Object lock;
    private String name;
    private int age;

    public UserProcessor(UserValidator validator, UserDataStore dataStore, Object lock, String name, int age) {
        this.validator = validator;
        this.dataStore = dataStore;
        this.lock = lock;
        this.name = name;
        this.age = age;
    }

    @Override
    public void run() {
        // Process user input concurrently with proper synchronization
        synchronized (lock) {
            if (validator.isValidAge(age)) {
                // Process the user input (e.g., store in data store)
                dataStore.addUser(new User(name, age));
            } else {
                // Display error message for invalid input
                System.out.println("Invalid age for User " + name);
            }
        }
    }
}
