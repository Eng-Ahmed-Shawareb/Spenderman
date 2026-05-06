package com.spenderman.DAO.InterfaceClass;

import com.spenderman.model.ClsUser;
import java.util.Optional;

/**
 * Class representing IRepositoryUsername.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public interface IRepositoryUsername extends IRepository<ClsUser> {

    /**
     * Method to findByUserName.
     *
     * @param userName the userName
     * @return the Optional<ClsUser>
     */
    public Optional<ClsUser> findByUserName(String userName);
}
