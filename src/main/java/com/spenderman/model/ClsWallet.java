package com.spenderman.model;

/**
 * Class representing ClsWallet.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsWallet {

    private int _walletID;

    private int _userTD;

    private String _name;

    private double _balance;

    public ClsWallet(int _walletID, int _userTD, String _name, double _balance) {
        this._walletID = _walletID;
        this._userTD = _userTD;
        this._name = _name;
        this._balance = _balance;
    }

    /**
     * Method to get_walletID.
     *
     * @return the int
     */
    public int get_walletID() {
        return _walletID;
    }

    /**
     * Method to set_walletID.
     *
     * @param _walletID the _walletID
     */
    public void set_walletID(int _walletID) {
        this._walletID = _walletID;
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
     * Method to get_balance.
     *
     * @return the double
     */
    public double get_balance() {
        return _balance;
    }

    /**
     * Method to set_balance.
     *
     * @param _balance the _balance
     */
    public void set_balance(double _balance) {
        this._balance = _balance;
    }

    /**
     * Method to get_userTD.
     *
     * @return the int
     */
    public int get_userTD() {
        return _userTD;
    }

    /**
     * Method to set_userTD.
     *
     * @param _userTD the _userTD
     */
    public void set_userTD(int _userTD) {
        this._userTD = _userTD;
    }
}
