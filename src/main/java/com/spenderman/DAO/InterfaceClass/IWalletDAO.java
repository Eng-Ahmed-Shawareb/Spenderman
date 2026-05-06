package com.spenderman.DAO.InterfaceClass;

import com.spenderman.model.ClsWallet;
import java.util.List;

/**
 * Class representing IWalletDAO.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public interface IWalletDAO extends IRepository<ClsWallet> {

    /**
     * Method to getByUser.
     *
     * @param userID the userID
     * @return the List<ClsWallet>
     */
    public List<ClsWallet> getByUser(int userID);

    /**
     * Method to getTotalBalance.
     *
     * @param userID the userID
     * @return the double
     */
    public double getTotalBalance(int userID);

    /**
     * Method to updateTotalBalance.
     *
     * @param walletID the walletID
     * @param amount the amount
     * @return the boolean
     */
    public boolean updateTotalBalance(int walletID, double amount);
}
