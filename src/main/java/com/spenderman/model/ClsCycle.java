package com.spenderman.model;

import com.spenderman.model.StatusEnums.EnCycleState;
import java.time.LocalDateTime;

/**
 * Class representing ClsCycle.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsCycle {

    private int _cycleID;

    private int _userID;

    private double _budgetAmount;

    private LocalDateTime _startDate;

    private LocalDateTime _endDate;

    private EnCycleState _state;

    public ClsCycle(int _userID, int _cycleID, double _budgetAmount, LocalDateTime _startDate, LocalDateTime _endDate, EnCycleState _state) {
        this._userID = _userID;
        this._cycleID = _cycleID;
        this._budgetAmount = _budgetAmount;
        this._startDate = _startDate;
        this._endDate = _endDate;
        this._state = _state;
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
     * Method to get_cycleID.
     *
     * @return the int
     */
    public int get_cycleID() {
        return _cycleID;
    }

    /**
     * Method to get_state.
     *
     * @return the EnCycleState
     */
    public EnCycleState get_state() {
        return _state;
    }

    /**
     * Method to set_state.
     *
     * @param _state the _state
     */
    public void set_state(EnCycleState _state) {
        this._state = _state;
    }

    /**
     * Method to set_cycleID.
     *
     * @param _cycleID the _cycleID
     */
    public void set_cycleID(int _cycleID) {
        this._cycleID = _cycleID;
    }

    /**
     * Method to get_budgetAmount.
     *
     * @return the double
     */
    public double get_budgetAmount() {
        return _budgetAmount;
    }

    /**
     * Method to set_budgetAmount.
     *
     * @param _budgetAmount the _budgetAmount
     */
    public void set_budgetAmount(double _budgetAmount) {
        this._budgetAmount = _budgetAmount;
    }

    /**
     * Method to get_startDate.
     *
     * @return the LocalDateTime
     */
    public LocalDateTime get_startDate() {
        return _startDate;
    }

    /**
     * Method to set_startDate.
     *
     * @param _startDate the _startDate
     */
    public void set_startDate(LocalDateTime _startDate) {
        this._startDate = _startDate;
    }

    /**
     * Method to get_endDate.
     *
     * @return the LocalDateTime
     */
    public LocalDateTime get_endDate() {
        return _endDate;
    }

    /**
     * Method to set_endDate.
     *
     * @param _endDate the _endDate
     */
    public void set_endDate(LocalDateTime _endDate) {
        this._endDate = _endDate;
    }
}
