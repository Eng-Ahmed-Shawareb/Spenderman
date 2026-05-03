package com.spenderman.service;

import com.spenderman.DAO.ClsCycleDAO;
import com.spenderman.DAO.InterfaceClass.ICycleDAO;
import com.spenderman.model.ClsCycle;
import com.spenderman.model.StatusEnums.EnCycleState;

import java.util.List;
import java.util.Optional;

public class ClsCycleService {
    private ICycleDAO _cycleDAO;
    private ClsTransactionService _transactionService;

    public ClsCycleService() {
        this._cycleDAO = new ClsCycleDAO();
        this._transactionService = new ClsTransactionService();
    }

    public Optional<ClsCycle> getActiveCycle(int userID) {
        return _cycleDAO.findActiveCycle(userID);
    }

    public List<ClsCycle> getByUser(int userID) {
        return _cycleDAO.findByUserID(userID);
    }

    public boolean createCycle(ClsCycle cycle) {
        return _cycleDAO.save(cycle);
    }

    public boolean closeCycle(int cycleID) {
        Optional<ClsCycle> optionalCycle = _cycleDAO.findByID(cycleID);
        if (optionalCycle.isPresent()) {
            ClsCycle cycle = optionalCycle.get();
            cycle.set_state(EnCycleState.PAST);
            return _cycleDAO.update(cycle);
        }
        return false;
    }

    public double getTotalSpent(int cycleID) {
        double totalSpent = 0;
        Optional<ClsCycle> optionalCycle = _cycleDAO.findByID(cycleID);
        if (optionalCycle.isPresent()) {
            ClsCycle cycle = optionalCycle.get();
            totalSpent = _transactionService.getTotalExpensesBetweenDates(
                    cycle.get_userID(),
                    cycle.get_startDate(),
                    cycle.get_endDate());
        }
        return totalSpent;
    }

    public double getRemainingBudget(int cycleID) {
        double remainingBudget = 0;
        Optional<ClsCycle> optionalCycle = _cycleDAO.findByID(cycleID);
        if (optionalCycle.isPresent()) {
            ClsCycle cycle = optionalCycle.get();
            double totalSpent = getTotalSpent(cycleID), budget = cycle.get_budgetAmount();
            remainingBudget = -totalSpent + budget;
        }
        return remainingBudget;
    }

    public double getSpentPercentage(int cycleID) {
        Optional<ClsCycle> optionalCycle = _cycleDAO.findByID(cycleID);
        double spentPercentage = 0.0;
        if (optionalCycle.isPresent()) {
            ClsCycle cycle = optionalCycle.get();
            double budget = cycle.get_budgetAmount();

            if (budget <= 0)
                return spentPercentage;

            double spent = getTotalSpent(cycleID);
            spentPercentage = (spent / budget) * 100.0;
        }
        return spentPercentage;
    }

    public Optional<ClsCycle> getCycleByID(int cycleID){
        return _cycleDAO.findByID(cycleID);
    }
}
