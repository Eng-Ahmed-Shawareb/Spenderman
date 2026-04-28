package com.spenderman.model;

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

    public int get_walletID() {
        return _walletID;
    }

    public void set_walletID(int _walletID) {
        this._walletID = _walletID;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public double get_balance() {
        return _balance;
    }

    public void set_balance(double _balance) {
        this._balance = _balance;
    }

    public int get_userTD() {
        return _userTD;
    }

    public void set_userTD(int _userTD) {
        this._userTD = _userTD;
    }
}
