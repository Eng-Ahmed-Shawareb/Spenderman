package com.spenderman.model;

import java.time.LocalDateTime;

/**
 * ClsUser model class.
 * Represents a user in the Spenderman application.
 */
public class ClsUser {
    private int _userID;
    private String _firstName;
    private String _lastName;
    private String _username;
    private String _passwordHash;
    private String _theme;
    private LocalDateTime _createdDate;

    public ClsUser(int _userID, String _firstName, String _lastName, String _username) {
        this._userID = _userID;
        this._firstName = _firstName;
        this._lastName = _lastName;
        this._username = _username;
        this._theme = "DARK";
        this._createdDate = LocalDateTime.now();
    }

    // Getters and setters
    public int getUserID() { return _userID; }
    public void setUserID(int _userID) { this._userID = _userID; }

    public String getFirstName() { return _firstName; }
    public void setFirstName(String _firstName) { this._firstName = _firstName; }

    public String getLastName() { return _lastName; }
    public void setLastName(String _lastName) { this._lastName = _lastName; }

    public String getUsername() { return _username; }
    public void setUsername(String _username) { this._username = _username; }

    public String getPasswordHash() { return _passwordHash; }
    public void setPasswordHash(String _passwordHash) { this._passwordHash = _passwordHash; }

    public String getTheme() { return _theme; }
    public void setTheme(String _theme) { this._theme = _theme; }

    public LocalDateTime getCreatedDate() { return _createdDate; }
    public void setCreatedDate(LocalDateTime _createdDate) { this._createdDate = _createdDate; }

    public String getFullName() {
        return _firstName + " " + _lastName;
    }

    public String getInitials() {
        return (_firstName.charAt(0) + "" + _lastName.charAt(0)).toUpperCase();
    }
}
