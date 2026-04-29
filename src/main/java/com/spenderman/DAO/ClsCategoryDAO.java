package com.spenderman.DAO;


import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.Singleton.ClsDatabaseConnection;
import com.spenderman.model.ClsCategory;
import com.spenderman.model.StatusEnums.EnTransactionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClsCategoryDAO implements IRepository<ClsCategory> {

    private ClsDatabaseConnection _databaseConnection;

    public ClsCategoryDAO() {
        this._databaseConnection = ClsDatabaseConnection.getInstance();
    }

    @Override
    public Optional<ClsCategory> findByID(int ID) {
        Connection connection = _databaseConnection.getConnection();

        String query = "SELECT * FROM Category WHERE ID = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){
           statement.setInt(1 , ID);
           ResultSet resultSet = statement.executeQuery();
           if(resultSet.next()){
                return Optional.of(new ClsCategory(resultSet.getInt("ID") ,
                        resultSet.getInt("FK_UserID") ,
                        resultSet.getString("category_name") ,
                        resultSet.getString("color") ,
                        EnTransactionType.valueOf(resultSet.getString("type"))));
           }
        }catch (SQLException es){
            System.out.println("Exception : " + es.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<ClsCategory> findAll() {
        List<ClsCategory> resultList = new ArrayList<>();
        Connection connection = _databaseConnection.getConnection();

        String query = "SELECT * FROM Category";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ClsCategory category = new ClsCategory(resultSet.getInt("ID") ,
                        resultSet.getInt("FK_UserID") ,
                        resultSet.getString("category_name") ,
                        resultSet.getString("color") ,
                        EnTransactionType.valueOf(resultSet.getString("type")));

                resultList.add(category);
            }
        }catch (SQLException es){
            System.out.println("Exception : " + es.getMessage());
        }
        return resultList;
    }

    @Override
    public boolean save(ClsCategory entity) {
        Connection connection = _databaseConnection.getConnection();

        String query = "INSERT INTO Category (FK_UserID , category_name , color , type) VALUES(? , ? , ? , ?)";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1 , entity.get_userID());
            statement.setString(2 , entity.get_name());
            statement.setString(3 , entity.get_hexColor());
            statement.setString(4 , String.valueOf(entity.get_type()));

            if(statement.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException es){
            System.out.println("Exception : " + es.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(ClsCategory entity) {
        Connection connection = _databaseConnection.getConnection();

        String query = "UPDATE Category SET FK_UserID = ? , " +
                       "category_name = ? , " +
                       "color = ? , " +
                       "type = ? " +
                       "WHERE ID = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1 , entity.get_userID());
            statement.setString(2 , entity.get_name());
            statement.setString(3 , entity.get_hexColor());
            statement.setString(4 , String.valueOf(entity.get_type()));
            statement.setInt(5 , entity.get_categoryID());

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

        String query = "DELETE FROM Category WHERE ID = ?";

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
