package com.spenderman.model;
import com.spenderman.model.StatusEnums.EnCycleState;

import java.time.LocalDateTime;

public class ClsCycle {
    private int _cycleID;
    private int _userID;
    private double _budgetAmount;
    private LocalDateTime _startDate;
    private LocalDateTime _endDate;
    private EnCycleState _state;


    public ClsCycle(int _userID, int _cycleID, double _budgetAmount, LocalDateTime _startDate, LocalDateTime _endDate,EnCycleState _state) {
        this._userID = _userID;
        this._cycleID = _cycleID;
        this._budgetAmount = _budgetAmount;
        this._startDate = _startDate;
        this._endDate = _endDate;
        this._state=_state;
    }

    public int get_userID() {
        return _userID;
    }

    public void set_userID(int _userID) {
        this._userID = _userID;
    }

    public int get_cycleID() {
        return _cycleID;
    }

    public EnCycleState get_state() {
        return _state;
    }

    public void set_state(EnCycleState _state) {
        this._state = _state;
    }

    public void set_cycleID(int _cycleID) {
        this._cycleID = _cycleID;
    }

    public double get_budgetAmount() {
        return _budgetAmount;
    }

    public void set_budgetAmount(double _budgetAmount) {
        this._budgetAmount = _budgetAmount;
    }

    public LocalDateTime get_startDate() {
        return _startDate;
    }

    public void set_startDate(LocalDateTime _startDate) {
        this._startDate = _startDate;
    }

    public LocalDateTime get_endDate() {
        return _endDate;
    }

    public void set_endDate(LocalDateTime _endDate) {
        this._endDate = _endDate;
    }
}
