package com.spenderman.model;

import com.spenderman.model.StatusEnums.EnTransactionType;

/**
 * Class representing ClsCategory.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsCategory {

    private int _categoryID;

    private int _userID;

    private String _name;

    private String _hexColor;

    private EnTransactionType _type;

    public ClsCategory(int _categoryID, int _userID, String _name, String _hexColor, EnTransactionType _type) {
        this._categoryID = _categoryID;
        this._userID = _userID;
        this._name = _name;
        this._hexColor = _hexColor;
        this._type = _type;
    }

    /**
     * Method to get_type.
     *
     * @return the EnTransactionType
     */
    public EnTransactionType get_type() {
        return _type;
    }

    /**
     * Method to set_type.
     *
     * @param _type the _type
     */
    public void set_type(EnTransactionType _type) {
        this._type = _type;
    }

    /**
     * Method to get_categoryID.
     *
     * @return the int
     */
    public int get_categoryID() {
        return _categoryID;
    }

    /**
     * Method to set_categoryID.
     *
     * @param _categoryID the _categoryID
     */
    public void set_categoryID(int _categoryID) {
        this._categoryID = _categoryID;
    }

    /**
     * Method to get_userID.
     *
     * @return the int
     */
    public int get_userID() {
        return _userID;
    }

    /**
     * Method to set_userID.
     *
     * @param _userID the _userID
     */
    public void set_userID(int _userID) {
        this._userID = _userID;
    }

    /**
     * Method to get_name.
     *
     * @return the String
     */
    public String get_name() {
        return _name;
    }

    /**
     * Method to set_name.
     *
     * @param _name the _name
     */
    public void set_name(String _name) {
        this._name = _name;
    }

    /**
     * Method to get_hexColor.
     *
     * @return the String
     */
    public String get_hexColor() {
        return _hexColor;
    }

    /**
     * Method to set_hexColor.
     *
     * @param _hexColor the _hexColor
     */
    public void set_hexColor(String _hexColor) {
        this._hexColor = _hexColor;
    }
}
