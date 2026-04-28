package com.spenderman.model;

import com.spenderman.model.StatusEnums.EnTransactionType;

import java.time.LocalDateTime;

public class ClsTransaction {
    private int _transactionID;
    private int _walletID;
    private int _savingGoalID;
    private int _categoryID;
    private double _amount;
    private LocalDateTime _localDateTime;
    private EnTransactionType _type;
    private String _note;

    public void set_transactionID(int _transactionID) {
        this._transactionID = _transactionID;
    }

    public void set_walletID(int _walletID) {
        this._walletID = _walletID;
    }

    public void set_savingGoalID(int _savingGoalID) {
        this._savingGoalID = _savingGoalID;
    }

    public void set_categoryID(int _categoryID) {
        this._categoryID = _categoryID;
    }

    public void set_amount(double _amount) {
        this._amount = _amount;
    }

    public void set_localDateTime(LocalDateTime _localDateTime) {
        this._localDateTime = _localDateTime;
    }

    public void set_type(EnTransactionType _type) {
        this._type = _type;
    }

    public void set_note(String _note) {
        this._note = _note;
    }

    public int get_transactionID() {
        return _transactionID;
    }

    public int get_walletID() {
        return _walletID;
    }

    public int get_savingGoalID() {
        return _savingGoalID;
    }

    public int get_categoryID() {
        return _categoryID;
    }

    public double get_amount() {
        return _amount;
    }

    public LocalDateTime get_localDateTime() {
        return _localDateTime;
    }

    public EnTransactionType get_type() {
        return _type;
    }

    public String get_note() {
        return _note;
    }

    public ClsTransaction(int _transactionID, int _walletID,
                          int _savingGoalID, int _categoryID,
                          double _amount, LocalDateTime _localDateTime,
                          EnTransactionType _type, String _note) {
        this._transactionID = _transactionID;
        this._walletID = _walletID;
        this._savingGoalID = _savingGoalID;
        this._categoryID = _categoryID;
        this._amount = _amount;
        this._localDateTime = _localDateTime;
        this._type = _type;
        this._note = _note;
    }

}
