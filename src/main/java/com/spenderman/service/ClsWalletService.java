package com.spenderman.service;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.model.ClsWallet;

public class ClsWalletService {
    private IRepository<ClsWallet> wallet_DAO;

    public ClsWalletService(IRepository<ClsWallet> wallet_DAO) {
        this.wallet_DAO = wallet_DAO;
    }
}
