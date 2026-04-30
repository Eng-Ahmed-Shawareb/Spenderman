package com.spenderman.service;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsSavingGoal;

public class ClsSavingGoalService {
    private IRepository<ClsSavingGoal> savingGoal_DAO;

    public ClsSavingGoalService(IRepository<ClsSavingGoal> savingGoal_DAO) {
        this.savingGoal_DAO = savingGoal_DAO;
    }
}
