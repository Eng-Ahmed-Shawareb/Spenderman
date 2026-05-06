package com.spenderman.DAO.InterfaceClass;

import com.spenderman.model.ClsCycle;
import java.util.List;
import java.util.Optional;

/**
 * Class representing ICycleDAO.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public interface ICycleDAO extends IRepository<ClsCycle> {

    /**
     * Method to findByUserID.
     *
     * @param userID the userID
     * @return the List<ClsCycle>
     */
    List<ClsCycle> findByUserID(int userID);

    /**
     * Method to findActiveCycle.
     *
     * @param userID the userID
     * @return the Optional<ClsCycle>
     */
    Optional<ClsCycle> findActiveCycle(int userID);
}
