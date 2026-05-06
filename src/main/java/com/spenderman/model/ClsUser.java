package com.spenderman.model;

import java.time.LocalDateTime;

/**
 * Class representing ClsUser.
 *
 * @author Spenderman Team
 * @version 1.0
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

    /**
     * Method to getUserID.
     *
     * @return the int
     */
    public int getUserID() {
        return _userID;
    }

    /**
     * Method to setUserID.
     *
     * @param _userID the _userID
     */
    public void setUserID(int _userID) {
        this._userID = _userID;
    }

    /**
     * Method to getFirstName.
     *
     * @return the String
     */
    public String getFirstName() {
        return _firstName;
    }

    /**
     * Method to setFirstName.
     *
     * @param _firstName the _firstName
     */
    public void setFirstName(String _firstName) {
        this._firstName = _firstName;
    }

    /**
     * Method to getLastName.
     *
     * @return the String
     */
    public String getLastName() {
        return _lastName;
    }

    /**
     * Method to setLastName.
     *
     * @param _lastName the _lastName
     */
    public void setLastName(String _lastName) {
        this._lastName = _lastName;
    }

    /**
     * Method to getUsername.
     *
     * @return the String
     */
    public String getUsername() {
        return _username;
    }

    /**
     * Method to setUsername.
     *
     * @param _username the _username
     */
    public void setUsername(String _username) {
        this._username = _username;
    }

    /**
     * Method to getPasswordHash.
     *
     * @return the String
     */
    public String getPasswordHash() {
        return _passwordHash;
    }

    /**
     * Method to setPasswordHash.
     *
     * @param _passwordHash the _passwordHash
     */
    public void setPasswordHash(String _passwordHash) {
        this._passwordHash = _passwordHash;
    }

    /**
     * Method to getTheme.
     *
     * @return the String
     */
    public String getTheme() {
        return _theme;
    }

    /**
     * Method to setTheme.
     *
     * @param _theme the _theme
     */
    public void setTheme(String _theme) {
        this._theme = _theme;
    }

    /**
     * Method to getCreatedDate.
     *
     * @return the LocalDateTime
     */
    public LocalDateTime getCreatedDate() {
        return _createdDate;
    }

    /**
     * Method to setCreatedDate.
     *
     * @param _createdDate the _createdDate
     */
    public void setCreatedDate(LocalDateTime _createdDate) {
        this._createdDate = _createdDate;
    }

    /**
     * Method to getFullName.
     *
     * @return the String
     */
    public String getFullName() {
        return _firstName + " " + _lastName;
    }

    /**
     * Method to getInitials.
     *
     * @return the String
     */
    public String getInitials() {
        return (_firstName.charAt(0) + "" + _lastName.charAt(0)).toUpperCase();
    }
}
