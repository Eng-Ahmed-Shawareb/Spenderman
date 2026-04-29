package com.spenderman.DAO;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.Singleton.ClsDatabaseConnection;
import com.spenderman.model.ClsWallet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClsWalletDAO implements IRepository<ClsWallet> {
    private ClsDatabaseConnection _databaseConnection;

    public ClsWalletDAO(){
        this._databaseConnection = ClsDatabaseConnection.getInstance();
    }

    @Override
    public Optional<ClsWallet> findByID(int ID) {
        Connection connection = _databaseConnection.getConnection();

        String query = "SELECT * FROM Wallet WHERE ID = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1 , ID);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return Optional.of(new ClsWallet(resultSet.getInt("ID") ,
                        resultSet.getInt("FK_UserID") ,
                        resultSet.getString("wallet_name")
                        , resultSet.getDouble("balance")));
            }
        }catch (SQLException es){
            System.out.println("Exception : " + es.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<ClsWallet> findAll() {
        List<ClsWallet> resultList = new ArrayList<>();

        Connection connection = _databaseConnection.getConnection();

        String query = "SELECT * FROM Wallet";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                ClsWallet wallet = new ClsWallet(resultSet.getInt("ID") ,
                        resultSet.getInt("FK_UserID") ,
                        resultSet.getString("wallet_name") ,
                        resultSet.getDouble("balance"));
                resultList.add(wallet);
            }
        }catch (SQLException es){
            System.out.println("Exception : " + es.getMessage());
        }
        return resultList;
    }

    @Override
    public boolean save(ClsWallet entity) {
        Connection connection = _databaseConnection.getConnection();

        String query = "INSERT INTO Wallet (FK_UserID , wallet_name , balance) VALUES(? , ? , ?)";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            statement.setInt(1 , entity.get_userTD());
            statement.setString(2 , entity.get_name());
            statement.setDouble(3 , entity.get_balance());

            if(statement.executeUpdate() > 0){
                return true;
            }

        }catch (SQLException es){
            System.out.println("Exception : " + es.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(ClsWallet entity) {
        Connection connection = _databaseConnection.getConnection();

        String query = "UPDATE Wallet SET FK_UserID = ? , wallet_name = ? , balance = ? WHERE ID = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            statement.setInt(1 , entity.get_userTD());
            statement.setString(2 , entity.get_name());
            statement.setDouble(3 , entity.get_balance());
            statement.setInt(4 , entity.get_walletID());

            if(statement.executeUpdate() > 0){
                return true;
            }

        }catch (SQLException es){
            System.out.println("Exception : " + es.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int ID) {
        Connection connection = _databaseConnection.getConnection();

        String query = "DELETE FROM Wallet WHERE ID = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            statement.setInt(1 , ID);

            if(statement.executeUpdate() > 0){
                return true;
            }

        }catch (SQLException es){
            System.out.println("Exception : " + es.getMessage());
        }
        return false;
    }
}
