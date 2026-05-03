package com.spenderman.DAO;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.InterfaceClass.IRepositoryUsername;
import com.spenderman.DAO.Singleton.ClsDatabaseConnection;
import com.spenderman.model.ClsUser;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClsUserDAO implements IRepositoryUsername {
    private final ClsDatabaseConnection _databaseConnection;

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

    public ClsUserDAO() {
        this._databaseConnection = ClsDatabaseConnection.getInstance();
    }

    @Override
    public Optional<ClsUser> findByID(int ID) {
        Connection connection = _databaseConnection.getConnection();
        String query = "SELECT * FROM SystemUser WHERE ID = ?";
        try (
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
        Connection connection = _databaseConnection.getConnection();
        List<ClsUser> users = new ArrayList<>();
        String query = "SELECT * FROM SystemUser";
        try (
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
        Connection connection = _databaseConnection.getConnection();
        String query = "INSERT INTO SystemUser (username, user_password, first_name, last_name, created_date) VALUES (?, ?, ?, ?, ?)";
        try (
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
        Connection connection = _databaseConnection.getConnection();
        String query = "UPDATE SystemUser SET username = ?, user_password = ?, first_name = ?, last_name = ? WHERE ID = ?";
        try (
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
        Connection connection = _databaseConnection.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, ID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error delete: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<ClsUser> findByUserName(String userName) {
        String query = "SELECT * FROM SystemUser WHERE username=?";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userName);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ClsUser user=new ClsUser(resultSet.getInt("ID"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("username")
                );
                user.setPasswordHash(resultSet.getString("user_password"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            System.err.println("Error findByID: " + e.getMessage());

        }


        return Optional.empty();
    }
}