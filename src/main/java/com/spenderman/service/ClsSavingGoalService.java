package com.spenderman.service;

import com.spenderman.DAO.ClsSavingGoalDAO;
import com.spenderman.DAO.ClsTransactionDAO;
import com.spenderman.DAO.InterfaceClass.ISavingGoalDAO;
import com.spenderman.model.ClsSavingGoal;
import com.spenderman.model.StatusEnums.EnGoalState;
import java.util.List;
import java.util.Optional;

/**
 * Class representing ClsSavingGoalService.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsSavingGoalService {

    private ISavingGoalDAO _savingGoalDAO;

    private ClsTransactionDAO _transactionDAO;

    public ClsSavingGoalService() {
        this._savingGoalDAO = new ClsSavingGoalDAO();
        this._transactionDAO = new ClsTransactionDAO();
    }

    /**
     * Method to getByUser.
     *
     * @param userID the userID
     * @return the List<ClsSavingGoal>
     */
    public List<ClsSavingGoal> getByUser(int userID) {
        return _savingGoalDAO.findByUserID(userID);
    }

    /**
     * Method to createGoal.
     *
     * @param goal the goal
     * @return the boolean
     */
    public boolean createGoal(ClsSavingGoal goal) {
        return _savingGoalDAO.save(goal);
    }

    /**
     * Method to addAmount.
     *
     * @param goalID the goalID
     * @param newAmount the newAmount
     * @return the boolean
     */
    public boolean addAmount(int goalID, double newAmount) {
        if (newAmount < 0)
            return false;
        return adjustAmount(goalID, newAmount);
    }

    /**
     * Method to adjustAmount.
     *
     * @param goalID the goalID
     * @param amountDelta the amountDelta
     * @return the boolean
     */
    public boolean adjustAmount(int goalID, double amountDelta) {
        Optional<ClsSavingGoal> goalOptional = _savingGoalDAO.findByID(goalID);
        if (goalOptional.isPresent()) {
            ClsSavingGoal goalToUpdate = goalOptional.get();
            double oldAmount = goalToUpdate.get_currentSaved();
            double newSaved = oldAmount + amountDelta;
            if (newSaved < 0)
                newSaved = 0;
            goalToUpdate.set_currentSaved(newSaved);
            if (newSaved >= goalToUpdate.get_targetAmount()) {
                goalToUpdate.setStatus(EnGoalState.COMPLETED);
            } else if (goalToUpdate.getStatus() == EnGoalState.COMPLETED && newSaved < goalToUpdate.get_targetAmount()) {
                goalToUpdate.setStatus(EnGoalState.ACTIVE);
            }
            return _savingGoalDAO.update(goalToUpdate);
        } else {
            return false;
        }
    }

    /**
     * Method to updateStatus.
     *
     * @param goalID the goalID
     * @param newState the newState
     * @return the boolean
     */
    public boolean updateStatus(int goalID, EnGoalState newState) {
        Optional<ClsSavingGoal> goalOptional = _savingGoalDAO.findByID(goalID);
        if (goalOptional.isPresent()) {
            ClsSavingGoal goalToUpdate = goalOptional.get();
            goalToUpdate.setStatus(newState);
            return _savingGoalDAO.update(goalToUpdate);
        } else {
            return false;
        }
    }

    /**
     * Method to deleteGoal.
     *
     * @param goalID the goalID
     * @return the boolean
     */
    public boolean deleteGoal(int goalID) {
        _transactionDAO.deleteByGoalID(goalID);
        return _savingGoalDAO.delete(goalID);
    }
}
