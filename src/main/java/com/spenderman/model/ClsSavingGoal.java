package com.spenderman.model;

import java.time.LocalDate;
import java.util.Date;
import com.spenderman.model.StatusEnums.EnGoalState;

/**
 * Class representing ClsSavingGoal.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsSavingGoal {

    private int _goalID;

    private int _userID;

    private String _name;

    private double _targetAmount;

    private double _currentSaved;

    private LocalDate _targetDate;

    private EnGoalState status;

    public ClsSavingGoal(int _goalID, int _userID, double _targetAmount, String _name, double _currentSaved, LocalDate _targetDate, EnGoalState status) {
        this._goalID = _goalID;
        this._userID = _userID;
        this._targetAmount = _targetAmount;
        this._name = _name;
        this._currentSaved = _currentSaved;
        this._targetDate = _targetDate;
        this.status = status;
    }

    /**
     * Method to get_goalID.
     *
     * @return the int
     */
    public int get_goalID() {
        return _goalID;
    }

    /**
     * Method to set_goalID.
     *
     * @param _goalID the _goalID
     */
    public void set_goalID(int _goalID) {
        this._goalID = _goalID;
    }

    /**
     * Method to get_useID.
     *
     * @return the int
     */
    public int get_useID() {
        return _userID;
    }

    /**
     * Method to set_useID.
     *
     * @param _useID the _useID
     */
    public void set_useID(int _useID) {
        this._userID = _useID;
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
     * Method to getStatus.
     *
     * @return the EnGoalState
     */
    public EnGoalState getStatus() {
        return status;
    }

    /**
     * Method to setStatus.
     *
     * @param status the status
     */
    public void setStatus(EnGoalState status) {
        this.status = status;
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
     * Method to get_targetAmount.
     *
     * @return the double
     */
    public double get_targetAmount() {
        return _targetAmount;
    }

    /**
     * Method to set_targetAmount.
     *
     * @param _targetAmount the _targetAmount
     */
    public void set_targetAmount(double _targetAmount) {
        this._targetAmount = _targetAmount;
    }

    /**
     * Method to get_currentSaved.
     *
     * @return the double
     */
    public double get_currentSaved() {
        return _currentSaved;
    }

    /**
     * Method to set_currentSaved.
     *
     * @param _currentSaved the _currentSaved
     */
    public void set_currentSaved(double _currentSaved) {
        this._currentSaved = _currentSaved;
    }

    /**
     * Method to get_targetDate.
     *
     * @return the LocalDate
     */
    public LocalDate get_targetDate() {
        return _targetDate;
    }

    /**
     * Method to set_targetDate.
     *
     * @param _targetDate the _targetDate
     */
    public void set_targetDate(LocalDate _targetDate) {
        this._targetDate = _targetDate;
    }
}
