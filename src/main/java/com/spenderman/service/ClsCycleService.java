package com.spenderman.service;

import com.spenderman.DAO.ClsCycleDAO;
import com.spenderman.DAO.InterfaceClass.ICycleDAO;
import com.spenderman.model.ClsCycle;
import com.spenderman.model.StatusEnums.EnCycleState;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Class representing ClsCycleService.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsCycleService {

    private ICycleDAO _cycleDAO;

    private ClsTransactionService _transactionService;

    public ClsCycleService() {
        this._cycleDAO = new ClsCycleDAO();
        this._transactionService = new ClsTransactionService();
    }

    /**
     * Method to getActiveCycle.
     *
     * @param userID the userID
     * @return the Optional<ClsCycle>
     */
    public Optional<ClsCycle> getActiveCycle(int userID) {
        return _cycleDAO.findActiveCycle(userID);
    }

    /**
     * Method to getByUser.
     *
     * @param userID the userID
     * @return the List<ClsCycle>
     */
    public List<ClsCycle> getByUser(int userID) {
        return _cycleDAO.findByUserID(userID);
    }

    /**
     * Method to createCycle.
     *
     * @param cycle the cycle
     * @return the boolean
     */
    public boolean createCycle(ClsCycle cycle) {
        Optional<ClsCycle> activeCycle = getActiveCycle(cycle.get_userID());
        if (activeCycle.isPresent()) {
            return false;
        }
        return _cycleDAO.save(cycle);
    }

    /**
     * Method to deleteCycle.
     *
     * @param cycleID the cycleID
     * @return the boolean
     */
    public boolean deleteCycle(int cycleID) {
        return _cycleDAO.delete(cycleID);
    }

    /**
     * Method to getTotalSpent.
     *
     * @param cycleID the cycleID
     * @return the double
     */
    public double getTotalSpent(int cycleID) {
        double totalSpent = 0;
        Optional<ClsCycle> optionalCycle = _cycleDAO.findByID(cycleID);
        if (optionalCycle.isPresent()) {
            totalSpent = getTotalSpent(optionalCycle.get());
        }
        return totalSpent;
    }

    /**
     * Method to getTotalSpent.
     *
     * @param cycle the cycle
     * @return the double
     */
    public double getTotalSpent(ClsCycle cycle) {
        return _transactionService.getTotalExpensesBetweenDates(cycle.get_userID(), cycle.get_startDate(), cycle.get_endDate());
    }

    /**
     * Method to getRemainingBudget.
     *
     * @param cycleID the cycleID
     * @return the double
     */
    public double getRemainingBudget(int cycleID) {
        double remainingBudget = 0;
        Optional<ClsCycle> optionalCycle = _cycleDAO.findByID(cycleID);
        if (optionalCycle.isPresent()) {
            ClsCycle cycle = optionalCycle.get();
            double totalSpent = getTotalSpent(cycle), budget = cycle.get_budgetAmount();
            remainingBudget = -totalSpent + budget;
        }
        return remainingBudget;
    }

    /**
     * Method to getSpentPercentage.
     *
     * @param cycleID the cycleID
     * @return the double
     */
    public double getSpentPercentage(int cycleID) {
        Optional<ClsCycle> optionalCycle = _cycleDAO.findByID(cycleID);
        double spentPercentage = 0.0;
        if (optionalCycle.isPresent()) {
            ClsCycle cycle = optionalCycle.get();
            double budget = cycle.get_budgetAmount();
            if (budget <= 0)
                return spentPercentage;
            double spent = getTotalSpent(cycle);
            spentPercentage = (spent / budget) * 100.0;
        }
        return spentPercentage;
    }

    /**
     * Method to getCycleByID.
     *
     * @param cycleID the cycleID
     * @return the Optional<ClsCycle>
     */
    public Optional<ClsCycle> getCycleByID(int cycleID) {
        return _cycleDAO.findByID(cycleID);
    }
}
