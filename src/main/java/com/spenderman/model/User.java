package com.spenderman.model;

import java.time.LocalDateTime;

/**
 * User model class.
 * Represents a user in the Spenderman application.
 */
public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String username;
    private String passwordHash;
    private String theme;
    private LocalDateTime createdDate;

    public User(int userID, String firstName, String lastName, String username) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.theme = "DARK";
        this.createdDate = LocalDateTime.now();
    }

    // Getters and setters
    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getInitials() {
        return (firstName.charAt(0) + "" + lastName.charAt(0)).toUpperCase();
    }
}
