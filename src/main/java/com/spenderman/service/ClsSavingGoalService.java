package com.spenderman.service;

import com.spenderman.DAO.ClsSavingGoalDAO;
import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.InterfaceClass.ISavingGoalDAO;
import com.spenderman.model.ClsSavingGoal;

import java.util.List;

public class ClsSavingGoalService {
    private ISavingGoalDAO savingGoal_DAO;

    public ClsSavingGoalService(ISavingGoalDAO savingGoal_DAO) {
        this.savingGoal_DAO = savingGoal_DAO;
    }
    public List<ClsSavingGoal> getByUser(int userID){

        return  savingGoal_DAO.findByUserID(userID);    }
    public boolean createGoal(ClsSavingGoal goal){
        return savingGoal_DAO.save(goal);
    }

}
