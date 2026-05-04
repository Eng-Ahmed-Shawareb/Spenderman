package com.spenderman.service;

import com.spenderman.DAO.ClsSavingGoalDAO;
import com.spenderman.DAO.ClsTransactionDAO;
import com.spenderman.DAO.InterfaceClass.ISavingGoalDAO;
import com.spenderman.model.ClsSavingGoal;
import com.spenderman.model.StatusEnums.EnGoalState;

import java.util.List;
import java.util.Optional;

public class ClsSavingGoalService {
    private ISavingGoalDAO _savingGoalDAO;
    private ClsTransactionDAO _transactionDAO;

    public ClsSavingGoalService() {
        this._savingGoalDAO  = new ClsSavingGoalDAO();
        this._transactionDAO = new ClsTransactionDAO();
    }

    public List<ClsSavingGoal> getByUser(int userID){

        return  _savingGoalDAO.findByUserID(userID);    }

    public boolean createGoal(ClsSavingGoal goal){
        return _savingGoalDAO.save(goal);
    }

    public boolean addAmount(int goalID,double newAmount){
        if(newAmount<0)return false;
        return adjustAmount(goalID, newAmount);
    }

    public boolean adjustAmount(int goalID, double amountDelta){
        Optional<ClsSavingGoal> goalOptional= _savingGoalDAO.findByID(goalID);
        if(goalOptional.isPresent()) {
            ClsSavingGoal goalToUpdate = goalOptional.get();
            double oldAmount = goalToUpdate.get_currentSaved();
            double newSaved = oldAmount + amountDelta;
            if (newSaved < 0) newSaved = 0; // Prevent negative balance
            goalToUpdate.set_currentSaved(newSaved);
            if (newSaved >= goalToUpdate.get_targetAmount()) {
                goalToUpdate.setStatus(EnGoalState.COMPLETED);
            } else if (goalToUpdate.getStatus() == EnGoalState.COMPLETED && newSaved < goalToUpdate.get_targetAmount()) {
                goalToUpdate.setStatus(EnGoalState.ACTIVE); // Revert status if dropped below target
            }
            return _savingGoalDAO.update(goalToUpdate);

        }else{
          return false;
        }
    }

    public boolean updateStatus(int goalID, EnGoalState newState){
        Optional<ClsSavingGoal> goalOptional= _savingGoalDAO.findByID(goalID);
        if(goalOptional.isPresent()) {
            ClsSavingGoal goalToUpdate = goalOptional.get();
            goalToUpdate.setStatus(newState);
            return _savingGoalDAO.update(goalToUpdate);
        }else{
            return false;
        }
    }
    public boolean deleteGoal(int goalID) {
        // Must remove all linked transactions first (no DB cascade on this FK)
        _transactionDAO.deleteByGoalID(goalID);
        return _savingGoalDAO.delete(goalID);
    }
}
