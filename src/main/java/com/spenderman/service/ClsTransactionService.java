package com.spenderman.service;

import com.spenderman.DAO.ClsTransactionDAO;
import com.spenderman.DAO.InterfaceClass.ITransactionDAO;
import com.spenderman.model.ClsTransaction;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class ClsTransactionService {
    private ITransactionDAO _transactionDAO;

    public ClsTransactionService() {
        this._transactionDAO = new ClsTransactionDAO();
    }

    public boolean addTransaction(ClsTransaction transaction){
        return _transactionDAO.save(transaction);
    }

    public boolean updateTransaction(ClsTransaction transaction){
        return _transactionDAO.update(transaction);
    }

    public List<ClsTransaction> getByWallet(int walletID){
        return _transactionDAO.getByWallet(walletID);
    }

    public List<ClsTransaction> getByGoal(int goalID){
        return _transactionDAO.getByGoal(goalID);
    }

    public List<ClsTransaction> getByUser(int userID){
        return _transactionDAO.getByUser(userID);
    }

    public boolean deleteTransaction(int transactionID){
        return _transactionDAO.delete(transactionID);
    }

    public double getTotalExpensesBetweenDates(int userID, LocalDate startDate, LocalDate endDate) {
        return _transactionDAO.getTotalExpensesBetweenDates(userID , startDate , endDate);
    }
}
