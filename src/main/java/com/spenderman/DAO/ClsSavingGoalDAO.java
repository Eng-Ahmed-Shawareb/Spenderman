package com.spenderman.DAO;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.InterfaceClass.ISavingGoalDAO;
import com.spenderman.DAO.Singleton.ClsDatabaseConnection;
import com.spenderman.model.ClsSavingGoal;
import com.spenderman.model.StatusEnums.EnGoalState;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class representing ClsSavingGoalDAO.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsSavingGoalDAO implements ISavingGoalDAO {

    private final ClsDatabaseConnection _databaseConnection;

    public ClsSavingGoalDAO() {
        this._databaseConnection = ClsDatabaseConnection.getInstance();
    }

    /**
     * Method to mapResultSetToSavingGoal.
     *
     * @param resultSet the resultSet
     * @return the ClsSavingGoal
     * @throws SQLException if an error occurs
     */
    private ClsSavingGoal mapResultSetToSavingGoal(ResultSet resultSet) throws SQLException {
        return new ClsSavingGoal(resultSet.getInt("ID"), resultSet.getInt("FK_UserID"), resultSet.getDouble("target_amount"), resultSet.getString("goal_name"), resultSet.getDouble("current_amount"), resultSet.getObject("target_date", LocalDate.class), EnGoalState.valueOf(resultSet.getString("state")));
    }

    /**
     * Method to findByID.
     *
     * @param ID the ID
     * @return the Optional<ClsSavingGoal>
     */
    @Override
    public Optional<ClsSavingGoal> findByID(int ID) {
        String query = "SELECT * FROM SavingGoal WHERE ID = ?";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToSavingGoal(resultSet));
                }
            }
        } catch (SQLException es) {
            System.out.println("Exception findByID: " + es.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Method to findAll.
     *
     * @return the List<ClsSavingGoal>
     */
    @Override
    public List<ClsSavingGoal> findAll() {
        Connection connection = _databaseConnection.getConnection();
        List<ClsSavingGoal> resultList = new ArrayList<>();
        String query = "SELECT * FROM SavingGoal";
        try (PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                resultList.add(mapResultSetToSavingGoal(resultSet));
            }
        } catch (SQLException es) {
            System.out.println("Exception findAll: " + es.getMessage());
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
    public boolean save(ClsSavingGoal entity) {
        Connection connection = _databaseConnection.getConnection();
        String query = "INSERT INTO SavingGoal (FK_userID, goal_name, target_amount, current_amount, target_date, state) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, entity.get_userID());
            statement.setString(2, entity.get_name());
            statement.setDouble(3, entity.get_targetAmount());
            statement.setDouble(4, entity.get_currentSaved());
            statement.setObject(5, entity.get_targetDate());
            statement.setString(6, entity.getStatus().name());
            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception save: " + es.getMessage());
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
    public boolean update(ClsSavingGoal entity) {
        Connection connection = _databaseConnection.getConnection();
        String query = "UPDATE SavingGoal SET FK_userID = ?, goal_name = ?, target_amount = ?, " + "current_amount = ?, target_date = ?, state = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, entity.get_userID());
            statement.setString(2, entity.get_name());
            statement.setDouble(3, entity.get_targetAmount());
            statement.setDouble(4, entity.get_currentSaved());
            statement.setObject(5, entity.get_targetDate());
            statement.setString(6, entity.getStatus().name());
            statement.setInt(7, entity.get_goalID());
            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception update: " + es.getMessage());
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
        String query = "DELETE FROM SavingGoal WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ID);
            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception delete: " + es.getMessage());
        }
        return false;
    }

    /**
     * Method to findByUserID.
     *
     * @param userID the userID
     * @return the List<ClsSavingGoal>
     */
    @Override
    public List<ClsSavingGoal> findByUserID(int userID) {
        List<ClsSavingGoal> resultList = new ArrayList<>();
        Connection connection = _databaseConnection.getConnection();
        String query = "SELECT * FROM SavingGoal WHERE FK_userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(mapResultSetToSavingGoal(resultSet));
                }
            }
        } catch (SQLException es) {
            System.out.println("Exception findByUserID: " + es.getMessage());
        }
        return resultList;
    }
}
