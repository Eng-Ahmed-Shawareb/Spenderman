package com.spenderman.DAO.InterfaceClass;

import com.spenderman.model.ClsWallet;

import java.util.List;

public interface IWalletDAO extends IRepository<ClsWallet>{
    public List<ClsWallet> getByUser(int userID);
    public double getTotalBalance(int userID);
    public boolean updateTotalBalance(int walletID , double amount);
}
