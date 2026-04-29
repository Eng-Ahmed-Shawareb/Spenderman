package com.spenderman.model;
import java.time.LocalDate;
import java.util.Date;

import com.spenderman.model.StatusEnums.EnGoalState;
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

    public int get_goalID() {
        return _goalID;
    }

    public void set_goalID(int _goalID) {
        this._goalID = _goalID;
    }

    public int get_useID() {
        return _userID;
    }

    public void set_useID(int _useID) {
        this._userID = _useID;
    }

    public String get_name() {
        return _name;
    }

    public int get_userID() {
        return _userID;
    }

    public void set_userID(int _userID) {
        this._userID = _userID;
    }

    public EnGoalState getStatus() {
        return status;
    }

    public void setStatus(EnGoalState status) {
        this.status = status;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public double get_targetAmount() {
        return _targetAmount;
    }

    public void set_targetAmount(double _targetAmount) {
        this._targetAmount = _targetAmount;
    }

    public double get_currentSaved() {
        return _currentSaved;
    }

    public void set_currentSaved(double _currentSaved) {
        this._currentSaved = _currentSaved;
    }

    public LocalDate get_targetDate() {
        return _targetDate;
    }

    public void set_targetDate(LocalDate _targetDate) {
        this._targetDate = _targetDate;
    }
}
