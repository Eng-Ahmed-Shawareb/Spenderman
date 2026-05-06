package com.spenderman.DAO.InterfaceClass;

import com.spenderman.model.ClsSavingGoal;
import java.util.List;

/**
 * Class representing ISavingGoalDAO.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public interface ISavingGoalDAO extends IRepository<ClsSavingGoal> {

    /**
     * Method to findByUserID.
     *
     * @param userID the userID
     * @return the List<ClsSavingGoal>
     */
    List<ClsSavingGoal> findByUserID(int userID);
}
