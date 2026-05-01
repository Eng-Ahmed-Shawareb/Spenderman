package com.spenderman.service;

import com.spenderman.DAO.InterfaceClass.ITransactionDAO;
import com.spenderman.model.ClsTransaction;

import java.util.List;

public class ClsTransactionService {
    private ITransactionDAO _transactionDAO;
    private ClsWalletService _walletService;
    private ClsSavingGoalService _savingGoalService;

    public ClsTransactionService(ITransactionDAO _transactionDAO, ClsWalletService _walletService, ClsSavingGoalService _savingGoalService) {
        this._transactionDAO = _transactionDAO;
        this._walletService = _walletService;
        this._savingGoalService = _savingGoalService;
    }

    public boolean addTransaction(ClsTransaction transaction){
        return _transactionDAO.save(transaction);
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
}
