package com.spenderman.DAO;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.Singleton.ClsDatabaseConnection;
import com.spenderman.model.ClsUser;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClsUserDAO implements IRepository<ClsUser> {
    private final ClsDatabaseConnection _databaseConnection;

    public ClsUserDAO(ClsDatabaseConnection databaseConnection) {
        this._databaseConnection = databaseConnection;
    }

    @Override
    public Optional<ClsUser> findByID(int ID) {
        String query = "SELECT * FROM SystemUser WHERE ID = ?";
        try (Connection connection = _databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, ID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToUser(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error findByID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<ClsUser> findAll() {
        List<ClsUser> users = new ArrayList<>();
        String query = "SELECT * FROM SystemUser";
        try (Connection connection = _databaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error findAll: " + e.getMessage());
        }
        return users;
    }

    @Override
    public boolean save(ClsUser entity) {
        String query = "INSERT INTO SystemUser (username, user_password, first_name, last_name, created_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = _databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getPasswordHash());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setObject(5, entity.getCreatedDate());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error save: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(ClsUser entity) {
        String query = "UPDATE SystemUser SET username = ?, user_password = ?, first_name = ?, last_name = ? WHERE ID = ?";
        try (Connection connection = _databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getPasswordHash());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setInt(5, entity.getUserID());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int ID) {
        String query = "DELETE FROM SystemUser WHERE ID = ?";
        try (Connection connection = _databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, ID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error delete: " + e.getMessage());
            return false;
        }
    }

    private ClsUser mapResultSetToUser(ResultSet rs) throws SQLException {
        ClsUser user = new ClsUser(
                rs.getInt("ID"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("username")
        );
        user.setPasswordHash(rs.getString("user_password"));
        user.setCreatedDate(rs.getObject("created_date", LocalDateTime.class));

        return user;
    }
}