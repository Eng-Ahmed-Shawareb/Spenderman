package com.spenderman.service;

import com.spenderman.DAO.ClsSavingGoalDAO;
import com.spenderman.DAO.InterfaceClass.ISavingGoalDAO;
import com.spenderman.model.ClsSavingGoal;
import com.spenderman.model.StatusEnums.EnGoalState;

import java.util.List;
import java.util.Optional;

public class ClsSavingGoalService {
    private ISavingGoalDAO _savingGoalDAO;

    public ClsSavingGoalService() {
        this._savingGoalDAO = new ClsSavingGoalDAO();
    }

    public List<ClsSavingGoal> getByUser(int userID){

        return  _savingGoalDAO.findByUserID(userID);    }

    public boolean createGoal(ClsSavingGoal goal){
        return _savingGoalDAO.save(goal);
    }

    public boolean addAmount(int goalID,double newAmount){
        if(newAmount<0)return false;
        Optional<ClsSavingGoal> goalOptional= _savingGoalDAO.findByID(goalID);
        if(goalOptional.isPresent()) {
            ClsSavingGoal goalToUpdate = goalOptional.get();
            double oldAmount = goalToUpdate.get_currentSaved();
            goalToUpdate.set_currentSaved(oldAmount + newAmount);
            if (oldAmount + newAmount >= goalToUpdate.get_targetAmount()) {
                goalToUpdate.setStatus(EnGoalState.COMPLETED);
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
}
