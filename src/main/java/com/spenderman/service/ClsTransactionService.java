package com.spenderman.service;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsTransaction;

import java.time.LocalDate;

public class ClsTransactionService {
    private IRepository<ClsTransaction> transaction_DAO;

    public ClsTransactionService(IRepository<ClsTransaction> transaction_DAO) {
        this.transaction_DAO = transaction_DAO;
    }

    public double getTotalExpensesBetweenDates(int userID, LocalDate startDate, LocalDate endDate) {
        return 0;
    }
}
