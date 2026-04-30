package com.spenderman.service;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsTransaction;

public class ClsTransactionService {
    private IRepository<ClsTransaction> transaction_DAO;

    public ClsTransactionService(IRepository<ClsTransaction> transaction_DAO) {
        this.transaction_DAO = transaction_DAO;
    }
}
