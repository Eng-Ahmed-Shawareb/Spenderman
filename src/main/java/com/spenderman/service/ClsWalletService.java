package com.spenderman.service;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.InterfaceClass.IWalletDAO;
import com.spenderman.model.ClsWallet;

import java.util.List;

public class ClsWalletService {
    private IWalletDAO _walletDAO;

    public ClsWalletService(IWalletDAO _walletDAO) {
        this._walletDAO = _walletDAO;
    }

    public List<ClsWallet>  getByUser(int userID){
        return _walletDAO.getByUser(userID);
    }

    public double getTotalBalance(int userID){
        return _walletDAO.getTotalBalance(userID);
    }

    public boolean createWallet(ClsWallet wallet){
        return _walletDAO.save(wallet);
    }

    public boolean updateBalance(int walletID , double amount){
        return _walletDAO.updateTotalBalance(walletID , amount);
    }
}
