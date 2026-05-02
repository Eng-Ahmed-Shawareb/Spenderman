package com.spenderman.DAO.InterfaceClass;

import com.spenderman.model.ClsTransaction;

import java.time.LocalDate;
import java.util.List;

public interface ITransactionDAO extends IRepository<ClsTransaction> {
    public List<ClsTransaction> getByWallet(int walletID);

    public List<ClsTransaction> getByGoal(int goalID);

    public List<ClsTransaction> getByUser(int userID);

    public double getTotalExpensesBetweenDates(int userID , LocalDate startDate , LocalDate endDate);
}
