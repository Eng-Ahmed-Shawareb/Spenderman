package com.spenderman.DAO;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.InterfaceClass.ITransactionDAO;
import com.spenderman.DAO.Singleton.ClsDatabaseConnection;
import com.spenderman.model.ClsTransaction;
import com.spenderman.model.StatusEnums.EnTransactionType;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class representing ClsTransactionDAO.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsTransactionDAO implements ITransactionDAO {

    private ClsDatabaseConnection _databaseConnection;

    public ClsTransactionDAO() {
        this._databaseConnection = ClsDatabaseConnection.getInstance();
    }

    /**
     * Method to findByID.
     *
     * @param ID the ID
     * @return the Optional<ClsTransaction>
     */
    @Override
    public Optional<ClsTransaction> findByID(int ID) {
        String query = "SELECT * FROM UserTransaction WHERE ID=?";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new ClsTransaction(resultSet.getInt("ID"), resultSet.getInt("FK_WalletID"), resultSet.getInt("FK_SavingGoalID"), resultSet.getInt("FK_CategoryID"), resultSet.getDouble("amount"), resultSet.getObject("transaction_date", LocalDateTime.class), EnTransactionType.valueOf(resultSet.getString("type").toUpperCase()), resultSet.getString("note")));
            }
        } catch (SQLException es) {
            System.out.println("Exception" + es.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Method to findAll.
     *
     * @return the List<ClsTransaction>
     */
    @Override
    public List<ClsTransaction> findAll() {
        String query = "SELECT * FROM UserTransaction ORDER BY transaction_date DESC, ID DESC";
        List<ClsTransaction> transactions = new ArrayList<ClsTransaction>();
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(new ClsTransaction(resultSet.getInt("ID"), resultSet.getInt("FK_WalletID"), resultSet.getInt("FK_SavingGoalID"), resultSet.getInt("FK_CategoryID"), resultSet.getDouble("amount"), resultSet.getObject("transaction_date", LocalDateTime.class), EnTransactionType.valueOf(resultSet.getString("type").toUpperCase()), resultSet.getString("note")));
            }
            return transactions;
        } catch (SQLException es) {
            System.out.println("Exception" + es.getMessage());
        }
        return List.of();
    }

    /**
     * Method to save.
     *
     * @param entity the entity
     * @return the boolean
     */
    @Override
    public boolean save(ClsTransaction entity) {
        String query = "INSERT INTO UserTransaction(" + "FK_WalletID , FK_SavingGoalID , FK_CategoryID , amount , type , note , transaction_date) VALUES(? , ? , ? , ? , ? , ? , ?)";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (entity.get_walletID() > 0) {
                statement.setInt(1, entity.get_walletID());
            } else {
                statement.setNull(1, java.sql.Types.INTEGER);
            }
            if (entity.get_savingGoalID() > 0) {
                statement.setInt(2, entity.get_savingGoalID());
            } else {
                statement.setNull(2, java.sql.Types.INTEGER);
            }
            if (entity.get_categoryID() > 0) {
                statement.setInt(3, entity.get_categoryID());
            } else {
                statement.setNull(3, java.sql.Types.INTEGER);
            }
            statement.setDouble(4, entity.get_amount());
            statement.setString(5, entity.get_type().name());
            statement.setString(6, entity.get_note());
            statement.setTimestamp(7, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            if (statement.executeUpdate() > 0)
                return true;
        } catch (SQLException es) {
            System.out.println("Exception" + es.getMessage());
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
    public boolean update(ClsTransaction entity) {
        String query = "UPDATE UserTransaction SET FK_WalletID=?," + " FK_SavingGoalID=?," + "FK_CategoryID=?," + "amount=?," + "type=?," + "note=? " + "WHERE ID=?";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (entity.get_walletID() > 0) {
                statement.setInt(1, entity.get_walletID());
            } else {
                statement.setNull(1, java.sql.Types.INTEGER);
            }
            if (entity.get_savingGoalID() > 0) {
                statement.setInt(2, entity.get_savingGoalID());
            } else {
                statement.setNull(2, java.sql.Types.INTEGER);
            }
            if (entity.get_categoryID() > 0) {
                statement.setInt(3, entity.get_categoryID());
            } else {
                statement.setNull(3, java.sql.Types.INTEGER);
            }
            statement.setDouble(4, entity.get_amount());
            statement.setString(5, entity.get_type().name());
            statement.setString(6, entity.get_note());
            statement.setInt(7, entity.get_transactionID());
            if (statement.executeUpdate() > 0)
                return true;
        } catch (SQLException es) {
            System.out.println("Exception" + es.getMessage());
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
        String query = "DELETE FROM UserTransaction WHERE ID=?";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ID);
            if (statement.executeUpdate() > 0)
                return true;
        } catch (Exception es) {
            System.out.println("Exception" + es.getMessage());
        }
        return false;
    }

    /**
     * Method to getByWallet.
     *
     * @param walletID the walletID
     * @return the List<ClsTransaction>
     */
    @Override
    public List<ClsTransaction> getByWallet(int walletID) {
        String query = "SELECT * FROM UserTransaction WHERE FK_WalletID = ? ORDER BY transaction_date DESC, ID DESC";
        List<ClsTransaction> transactions = new ArrayList<ClsTransaction>();
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, walletID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(new ClsTransaction(resultSet.getInt("ID"), resultSet.getInt("FK_WalletID"), resultSet.getInt("FK_SavingGoalID"), resultSet.getInt("FK_CategoryID"), resultSet.getDouble("amount"), resultSet.getObject("transaction_date", LocalDateTime.class), EnTransactionType.valueOf(resultSet.getString("type").toUpperCase()), resultSet.getString("note")));
            }
            return transactions;
        } catch (SQLException es) {
            System.out.println("Exception" + es.getMessage());
        }
        return List.of();
    }

    /**
     * Method to getByGoal.
     *
     * @param goalID the goalID
     * @return the List<ClsTransaction>
     */
    @Override
    public List<ClsTransaction> getByGoal(int goalID) {
        String query = "SELECT * FROM UserTransaction WHERE FK_SavingGoalID = ? ORDER BY transaction_date DESC, ID DESC";
        List<ClsTransaction> transactions = new ArrayList<ClsTransaction>();
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, goalID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(new ClsTransaction(resultSet.getInt("ID"), resultSet.getInt("FK_WalletID"), resultSet.getInt("FK_SavingGoalID"), resultSet.getInt("FK_CategoryID"), resultSet.getDouble("amount"), resultSet.getObject("transaction_date", LocalDateTime.class), EnTransactionType.valueOf(resultSet.getString("type").toUpperCase()), resultSet.getString("note")));
            }
            return transactions;
        } catch (SQLException es) {
            System.out.println("Exception" + es.getMessage());
        }
        return List.of();
    }

    /**
     * Method to getByUser.
     *
     * @param userID the userID
     * @return the List<ClsTransaction>
     */
    @Override
    public List<ClsTransaction> getByUser(int userID) {
        String query = "SELECT * FROM UserTransaction WHERE FK_WalletID IN (SELECT ID FROM Wallet WHERE FK_UserID = ?) " + "UNION ALL SELECT * FROM UserTransaction WHERE FK_SavingGoalID IN (SELECT ID FROM SavingGoal WHERE FK_UserID = ?) " + "ORDER BY transaction_date DESC, ID DESC";
        List<ClsTransaction> transactions = new ArrayList<ClsTransaction>();
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            statement.setInt(2, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(new ClsTransaction(resultSet.getInt("ID"), resultSet.getInt("FK_WalletID"), resultSet.getInt("FK_SavingGoalID"), resultSet.getInt("FK_CategoryID"), resultSet.getDouble("amount"), resultSet.getObject("transaction_date", LocalDateTime.class), EnTransactionType.valueOf(resultSet.getString("type").toUpperCase()), resultSet.getString("note")));
            }
            return transactions;
        } catch (SQLException es) {
            System.out.println("Exception" + es.getMessage());
        }
        return List.of();
    }

    /**
     * Method to getTotalExpensesBetweenDates.
     *
     * @param userID the userID
     * @param startDate the startDate
     * @param endDate the endDate
     * @return the double
     */
    @Override
    public double getTotalExpensesBetweenDates(int userID, LocalDateTime startDate, LocalDateTime endDate) {
        double totalExpenses = 0.0;
        Connection connection = _databaseConnection.getConnection();
        String query = "SELECT amount FROM UserTransaction WHERE " + "FK_WalletID IN (SELECT ID FROM Wallet WHERE FK_UserID = ?) " + "AND type = 'EXPENSE' " + "AND transaction_date >= ? AND transaction_date <= ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            statement.setTimestamp(2, Timestamp.valueOf(startDate));
            statement.setTimestamp(3, Timestamp.valueOf(endDate));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                totalExpenses += resultSet.getDouble("amount");
            }
        } catch (SQLException se) {
            System.out.println("Exception : " + se.getMessage());
        }
        return totalExpenses;
    }

    /**
     * Method to deleteByGoalID.
     *
     * @param goalID the goalID
     * @return the boolean
     */
    @Override
    public boolean deleteByGoalID(int goalID) {
        String query = "DELETE FROM UserTransaction WHERE FK_SavingGoalID = ?";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, goalID);
            statement.executeUpdate();
            System.out.println("Deleted transactions for goalID=" + goalID);
            return true;
        } catch (SQLException es) {
            System.err.println("Exception deleteByGoalID: " + es.getMessage());
            return false;
        }
    }
}
