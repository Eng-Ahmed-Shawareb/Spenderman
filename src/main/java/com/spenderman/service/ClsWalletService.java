package com.spenderman.service;

import com.spenderman.DAO.ClsWalletDAO;
import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.InterfaceClass.IWalletDAO;
import com.spenderman.model.ClsWallet;
import java.util.List;

/**
 * Class representing ClsWalletService.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsWalletService {

    private IWalletDAO _walletDAO;

    public ClsWalletService() {
        this._walletDAO = new ClsWalletDAO();
    }

    /**
     * Method to getByUser.
     *
     * @param userID the userID
     * @return the List<ClsWallet>
     */
    public List<ClsWallet> getByUser(int userID) {
        return _walletDAO.getByUser(userID);
    }

    /**
     * Method to getTotalBalance.
     *
     * @param userID the userID
     * @return the double
     */
    public double getTotalBalance(int userID) {
        return _walletDAO.getTotalBalance(userID);
    }

    /**
     * Method to createWallet.
     *
     * @param wallet the wallet
     * @return the boolean
     */
    public boolean createWallet(ClsWallet wallet) {
        return _walletDAO.save(wallet);
    }

    /**
     * Method to updateBalance.
     *
     * @param walletID the walletID
     * @param amount the amount
     * @return the boolean
     */
    public boolean updateBalance(int walletID, double amount) {
        return _walletDAO.updateTotalBalance(walletID, amount);
    }

    /**
     * Method to deleteWallet.
     *
     * @param walletID the walletID
     * @return the boolean
     */
    public boolean deleteWallet(int walletID) {
        return _walletDAO.delete(walletID);
    }
}
