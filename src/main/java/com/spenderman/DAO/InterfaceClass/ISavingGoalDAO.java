package com.spenderman.DAO.InterfaceClass;

import com.spenderman.model.ClsSavingGoal;

import java.util.List;

public interface ISavingGoalDAO extends IRepository<ClsSavingGoal> {
    List<ClsSavingGoal> findByUserID(int userID);
}
