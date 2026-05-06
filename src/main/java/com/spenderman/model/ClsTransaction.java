package com.spenderman.model;

import com.spenderman.model.StatusEnums.EnTransactionType;
import java.time.LocalDateTime;

/**
 * Class representing ClsTransaction.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsTransaction {

    private int _transactionID;

    private int _walletID;

    private int _savingGoalID;

    private int _categoryID;

    private double _amount;

    private LocalDateTime _localDateTime;

    private EnTransactionType _type;

    private String _note;

    /**
     * Method to set_transactionID.
     *
     * @param _transactionID the _transactionID
     */
    public void set_transactionID(int _transactionID) {
        this._transactionID = _transactionID;
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
     * Method to set_savingGoalID.
     *
     * @param _savingGoalID the _savingGoalID
     */
    public void set_savingGoalID(int _savingGoalID) {
        this._savingGoalID = _savingGoalID;
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
     * Method to set_amount.
     *
     * @param _amount the _amount
     */
    public void set_amount(double _amount) {
        this._amount = _amount;
    }

    /**
     * Method to set_localDateTime.
     *
     * @param _localDateTime the _localDateTime
     */
    public void set_localDateTime(LocalDateTime _localDateTime) {
        this._localDateTime = _localDateTime;
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
     * Method to set_note.
     *
     * @param _note the _note
     */
    public void set_note(String _note) {
        this._note = _note;
    }

    /**
     * Method to get_transactionID.
     *
     * @return the int
     */
    public int get_transactionID() {
        return _transactionID;
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
     * Method to get_savingGoalID.
     *
     * @return the int
     */
    public int get_savingGoalID() {
        return _savingGoalID;
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
     * Method to get_amount.
     *
     * @return the double
     */
    public double get_amount() {
        return _amount;
    }

    /**
     * Method to get_localDateTime.
     *
     * @return the LocalDateTime
     */
    public LocalDateTime get_localDateTime() {
        return _localDateTime;
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
     * Method to get_note.
     *
     * @return the String
     */
    public String get_note() {
        return _note;
    }

    public ClsTransaction(int _transactionID, int _walletID, int _savingGoalID, int _categoryID, double _amount, LocalDateTime _localDateTime, EnTransactionType _type, String _note) {
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
