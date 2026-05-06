package com.spenderman.DAO;

import com.spenderman.DAO.InterfaceClass.ICycleDAO;
import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.Singleton.ClsDatabaseConnection;
import com.spenderman.model.ClsCycle;
import com.spenderman.model.ClsSavingGoal;
import com.spenderman.model.StatusEnums.EnCycleState;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class representing ClsCycleDAO.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsCycleDAO implements ICycleDAO {

    private ClsDatabaseConnection _databaseConnection;

    public ClsCycleDAO() {
        this._databaseConnection = ClsDatabaseConnection.getInstance();
    }

    /**
     * Method to findByID.
     *
     * @param ID the ID
     * @return the Optional<ClsCycle>
     */
    @Override
    public Optional<ClsCycle> findByID(int ID) {
        String query = "SELECT * FROM Cycle WHERE ID=?";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new ClsCycle(resultSet.getInt("FK_UserID"), resultSet.getInt("ID"), resultSet.getDouble("budget_amount"), resultSet.getTimestamp("start_date").toLocalDateTime(), resultSet.getTimestamp("end_date").toLocalDateTime(), EnCycleState.valueOf(resultSet.getString("state").toUpperCase())));
            }
        } catch (SQLException es) {
            System.out.println("Exception" + es.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Method to findAll.
     *
     * @return the List<ClsCycle>
     */
    @Override
    public List<ClsCycle> findAll() {
        String query = "SELECT * FROM Cycle";
        List<ClsCycle> cycles = new ArrayList<ClsCycle>();
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cycles.add(new ClsCycle(resultSet.getInt("FK_UserID"), resultSet.getInt("ID"), resultSet.getDouble("budget_amount"), resultSet.getTimestamp("start_date").toLocalDateTime(), resultSet.getTimestamp("end_date").toLocalDateTime(), EnCycleState.valueOf(resultSet.getString("state").toUpperCase())));
            }
            return cycles;
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
    public boolean save(ClsCycle entity) {
        String query = "INSERT INTO Cycle(" + "FK_UserID,budget_amount,start_date,end_date,state) VALUES(?,?,?,?,?)";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, entity.get_userID());
            statement.setDouble(2, entity.get_budgetAmount());
            statement.setTimestamp(3, Timestamp.valueOf(entity.get_startDate()));
            statement.setTimestamp(4, Timestamp.valueOf(entity.get_endDate()));
            statement.setString(5, entity.get_state().name());
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
    public boolean update(ClsCycle entity) {
        String query = "UPDATE Cycle SET FK_UserID=?," + " budget_amount=?," + "start_date=?," + "end_date=?," + "state=? " + "WHERE ID=?";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, entity.get_userID());
            statement.setDouble(2, entity.get_budgetAmount());
            statement.setTimestamp(3, Timestamp.valueOf(entity.get_startDate()));
            statement.setTimestamp(4, Timestamp.valueOf(entity.get_endDate()));
            statement.setString(5, entity.get_state().name());
            statement.setInt(6, entity.get_cycleID());
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
        String query = "DELETE FROM Cycle WHERE ID=?";
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
     * Method to findByUserID.
     *
     * @param userID the userID
     * @return the List<ClsCycle>
     */
    @Override
    public List<ClsCycle> findByUserID(int userID) {
        List<ClsCycle> cycles = new ArrayList<>();
        String query = "SELECT * FROM Cycle WHERE FK_UserID = ?";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cycles.add(new ClsCycle(resultSet.getInt("FK_UserID"), resultSet.getInt("ID"), resultSet.getDouble("budget_amount"), resultSet.getTimestamp("start_date").toLocalDateTime(), resultSet.getTimestamp("end_date").toLocalDateTime(), EnCycleState.valueOf(resultSet.getString("state").toUpperCase())));
                }
            }
        } catch (SQLException es) {
            System.out.println("Exception findByUserID: " + es.getMessage());
        }
        return cycles;
    }

    /**
     * Method to findActiveCycle.
     *
     * @param userID the userID
     * @return the Optional<ClsCycle>
     */
    @Override
    public Optional<ClsCycle> findActiveCycle(int userID) {
        String query = "SELECT * FROM Cycle WHERE FK_UserID = ? AND state = 'ACTIVE'";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new ClsCycle(resultSet.getInt("FK_UserID"), resultSet.getInt("ID"), resultSet.getDouble("budget_amount"), resultSet.getTimestamp("start_date").toLocalDateTime(), resultSet.getTimestamp("end_date").toLocalDateTime(), EnCycleState.valueOf(resultSet.getString("state").toUpperCase())));
                }
            }
        } catch (SQLException es) {
            System.out.println("Exception in findActiveCycle: " + es.getMessage());
        }
        return Optional.empty();
    }
}
