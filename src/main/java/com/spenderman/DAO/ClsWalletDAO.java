package com.spenderman.DAO;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.InterfaceClass.IWalletDAO;
import com.spenderman.DAO.Singleton.ClsDatabaseConnection;
import com.spenderman.model.ClsWallet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class representing ClsWalletDAO.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsWalletDAO implements IWalletDAO {

    private ClsDatabaseConnection _databaseConnection;

    public ClsWalletDAO() {
        this._databaseConnection = ClsDatabaseConnection.getInstance();
    }

    /**
     * Method to findByID.
     *
     * @param ID the ID
     * @return the Optional<ClsWallet>
     */
    @Override
    public Optional<ClsWallet> findByID(int ID) {
        Connection connection = _databaseConnection.getConnection();
        String query = "SELECT * FROM Wallet WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new ClsWallet(resultSet.getInt("ID"), resultSet.getInt("FK_UserID"), resultSet.getString("wallet_name"), resultSet.getDouble("balance")));
            }
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Method to findAll.
     *
     * @return the List<ClsWallet>
     */
    @Override
    public List<ClsWallet> findAll() {
        List<ClsWallet> resultList = new ArrayList<>();
        Connection connection = _databaseConnection.getConnection();
        String query = "SELECT * FROM Wallet";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClsWallet wallet = new ClsWallet(resultSet.getInt("ID"), resultSet.getInt("FK_UserID"), resultSet.getString("wallet_name"), resultSet.getDouble("balance"));
                resultList.add(wallet);
            }
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return resultList;
    }

    /**
     * Method to save.
     *
     * @param entity the entity
     * @return the boolean
     */
    @Override
    public boolean save(ClsWallet entity) {
        Connection connection = _databaseConnection.getConnection();
        String query = "INSERT INTO Wallet (FK_UserID , wallet_name , balance) VALUES(? , ? , ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, entity.get_userTD());
            statement.setString(2, entity.get_name());
            statement.setDouble(3, entity.get_balance());
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return false;
    }

    /**
     * Method to update.
     *
     * @param entity the entity
     * @return the boolean
     */
    @Override
    public boolean update(ClsWallet entity) {
        Connection connection = _databaseConnection.getConnection();
        String query = "UPDATE Wallet SET FK_UserID = ? , wallet_name = ? , balance = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, entity.get_userTD());
            statement.setString(2, entity.get_name());
            statement.setDouble(3, entity.get_balance());
            statement.setInt(4, entity.get_walletID());
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return false;
    }

    /**
     * Method to delete.
     *
     * @param ID the ID
     * @return the boolean
     */
    @Override
    public boolean delete(int ID) {
        Connection connection = _databaseConnection.getConnection();
        String query = "DELETE FROM Wallet WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ID);
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return false;
    }

    /**
     * Method to getByUser.
     *
     * @param userID the userID
     * @return the List<ClsWallet>
     */
    @Override
    public List<ClsWallet> getByUser(int userID) {
        List<ClsWallet> resultList = new ArrayList<>();
        Connection connection = _databaseConnection.getConnection();
        String query = "SELECT * FROM Wallet WHERE FK_UserID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClsWallet wallet = new ClsWallet(resultSet.getInt("ID"), resultSet.getInt("FK_UserID"), resultSet.getString("wallet_name"), resultSet.getDouble("balance"));
                resultList.add(wallet);
            }
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return resultList;
    }

    /**
     * Method to getTotalBalance.
     *
     * @param userID the userID
     * @return the double
     */
    @Override
    public double getTotalBalance(int userID) {
        double totalBalance = 0.0;
        Connection connection = _databaseConnection.getConnection();
        String query = "SELECT * FROM Wallet WHERE FK_UserID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                totalBalance += resultSet.getDouble("balance");
            }
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return totalBalance;
    }

    /**
     * Method to updateTotalBalance.
     *
     * @param walletID the walletID
     * @param amount the amount
     * @return the boolean
     */
    @Override
    public boolean updateTotalBalance(int walletID, double amount) {
        Connection connection = _databaseConnection.getConnection();
        String query = "UPDATE Wallet SET balance = balance + ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, amount);
            statement.setInt(2, walletID);
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return false;
    }
}
