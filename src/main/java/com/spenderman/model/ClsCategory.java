package com.spenderman.model;

import com.spenderman.model.StatusEnums.EnTransactionType;

public class ClsCategory {
    private int _categoryID;
    private int _userID;
    private String _name;
    private String _hexColor;
    private EnTransactionType _type;

    public ClsCategory(int _categoryID, int _userID, String _name, String _hexColor,EnTransactionType _type) {
        this._categoryID = _categoryID;
        this._userID = _userID;
        this._name = _name;
        this._hexColor = _hexColor;
        this._type = _type;
    }

    public EnTransactionType get_type() {
        return _type;
    }

    public void set_type(EnTransactionType _type) {
        this._type = _type;
    }

    public int get_categoryID() {
        return _categoryID;
    }

    public void set_categoryID(int _categoryID) {
        this._categoryID = _categoryID;
    }

    public int get_userID() {
        return _userID;
    }

    public void set_userID(int _userID) {
        this._userID = _userID;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_hexColor() {
        return _hexColor;
    }

    public void set_hexColor(String _hexColor) {
        this._hexColor = _hexColor;
    }
}
