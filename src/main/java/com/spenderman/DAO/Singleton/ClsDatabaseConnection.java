package com.spenderman.DAO.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClsDatabaseConnection {
    private ClsDatabaseConnection _instance = null;
    private Connection _connection;
    private String _URL = "jdbc:sqlserver://BudgetSystemDB.mssql.somee.com:1433;" +
            "databaseName=BudgetSystemDB;" +
            "encrypt=true;" +
            "trustServerCertificate=true;";
    private String _user = "databaseforour_SQLLogin_1";
    private String _password = "9uijn9i722";

    private ClsDatabaseConnection(){
        try {
            _connection = DriverManager.getConnection(_URL, _user, _password);
        } catch (SQLException e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
    }

    public ClsDatabaseConnection getInstance(){
        if(_instance == null){
            _instance = new ClsDatabaseConnection();
        }
        return _instance;
    }

    public Connection getConnection(){
        if(_instance == null){
            _instance = new ClsDatabaseConnection();
        }
        return _connection;
    }

    public void closeConnection() {
        try{
            _connection.close();
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }

}
