package com.spenderman.service;

import com.spenderman.DAO.ClsTransactionDAO;
import com.spenderman.DAO.InterfaceClass.ITransactionDAO;
import com.spenderman.model.ClsTransaction;
import com.spenderman.model.StatusEnums.EnTransactionType;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ClsTransactionService {
    private ITransactionDAO _transactionDAO;
    private ClsWalletService _walletService;
    private ClsSavingGoalService _savingGoalService;

    public ClsTransactionService() {
        this._transactionDAO = new ClsTransactionDAO();
        this._walletService = new ClsWalletService();
        this._savingGoalService = new ClsSavingGoalService();
    }

    public boolean addTransaction(ClsTransaction transaction){
        boolean success = _transactionDAO.save(transaction);
        if (success) {
            applyTransactionEffect(transaction, false);
        }
        return success;
    }

    public boolean updateTransaction(ClsTransaction transaction){
        Optional<ClsTransaction> oldTransactionOpt = _transactionDAO.findByID(transaction.get_transactionID());
        if (oldTransactionOpt.isEmpty()) {
            return false;
        }

        boolean success = _transactionDAO.update(transaction);
        if (success) {
            applyTransactionEffect(oldTransactionOpt.get(), true); // Revert old
            applyTransactionEffect(transaction, false); // Apply new
        }
        return success;
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
        Optional<ClsTransaction> oldTransactionOpt = _transactionDAO.findByID(transactionID);
        if (oldTransactionOpt.isEmpty()) {
            return false;
        }

        boolean success = _transactionDAO.delete(transactionID);
        if (success) {
            applyTransactionEffect(oldTransactionOpt.get(), true);
        }
        return success;
    }

    public double getTotalExpensesBetweenDates(int userID, LocalDate startDate, LocalDate endDate) {
        return _transactionDAO.getTotalExpensesBetweenDates(userID , startDate , endDate);
    }

    private void applyTransactionEffect(ClsTransaction transaction, boolean isRevert) {
        double amount = transaction.get_amount();
        int multiplier = isRevert ? -1 : 1;
        
        if (transaction.get_walletID() > 0) {
            if (transaction.get_type() == EnTransactionType.EXPENSE) {
                _walletService.updateBalance(transaction.get_walletID(), -amount * multiplier);
            } else if (transaction.get_type() == EnTransactionType.DEPOSIT) {
                _walletService.updateBalance(transaction.get_walletID(), amount * multiplier);
            }
        } else if (transaction.get_savingGoalID() > 0) {
            if (transaction.get_type() == EnTransactionType.DEPOSIT) {
                _savingGoalService.adjustAmount(transaction.get_savingGoalID(), amount * multiplier);
            } else if (transaction.get_type() == EnTransactionType.EXPENSE) {
                _savingGoalService.adjustAmount(transaction.get_savingGoalID(), -amount * multiplier);
            }
        }
    }
}
