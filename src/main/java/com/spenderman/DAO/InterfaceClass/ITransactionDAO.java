package com.spenderman.DAO.InterfaceClass;

import com.spenderman.model.ClsTransaction;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class representing ITransactionDAO.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public interface ITransactionDAO extends IRepository<ClsTransaction> {

    /**
     * Method to getByWallet.
     *
     * @param walletID the walletID
     * @return the List<ClsTransaction>
     */
    public List<ClsTransaction> getByWallet(int walletID);

    /**
     * Method to getByGoal.
     *
     * @param goalID the goalID
     * @return the List<ClsTransaction>
     */
    public List<ClsTransaction> getByGoal(int goalID);

    /**
     * Method to getByUser.
     *
     * @param userID the userID
     * @return the List<ClsTransaction>
     */
    public List<ClsTransaction> getByUser(int userID);

    /**
     * Method to getTotalExpensesBetweenDates.
     *
     * @param userID the userID
     * @param startDate the startDate
     * @param endDate the endDate
     * @return the double
     */
    public double getTotalExpensesBetweenDates(int userID, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Method to deleteByGoalID.
     *
     * @param goalID the goalID
     * @return the boolean
     */
    public boolean deleteByGoalID(int goalID);
}
