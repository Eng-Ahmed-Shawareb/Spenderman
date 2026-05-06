package com.spenderman.DAO.InterfaceClass;

import com.spenderman.model.ClsCategory;
import java.util.List;

/**
 * Class representing ICateogryDAO.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public interface ICateogryDAO extends IRepository<ClsCategory> {

    /**
     * Method to getByUserID.
     *
     * @param userID the userID
     * @return the List<ClsCategory>
     */
    public List<ClsCategory> getByUserID(int userID);
}
