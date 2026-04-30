package com.spenderman.DAO;

import com.spenderman.DAO.InterfaceClass.IRepository;
import com.spenderman.DAO.Singleton.ClsDatabaseConnection;
import com.spenderman.model.ClsTransaction;
import com.spenderman.model.StatusEnums.EnTransactionType;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClsTransactionDAO implements IRepository<ClsTransaction> {
    private ClsDatabaseConnection _databaseConnection;

    public ClsTransactionDAO(){
        this._databaseConnection = ClsDatabaseConnection.getInstance();
    }

    @Override
    public Optional<ClsTransaction> findByID(int ID)
    {
String query="SELECT * FROM UserTransaction WHERE ID=?";


        try(Connection connection=_databaseConnection.getConnection();
            PreparedStatement  statement= connection.prepareStatement(query);
        ){
            statement.setInt(1,ID);
            ResultSet resultSet=statement.executeQuery();

            if(resultSet.next()){
                return Optional.of(new ClsTransaction(resultSet.getInt("ID")
                        ,resultSet.getInt("FK_WalletID"),
                        resultSet.getInt("FK_SavingGoalID"),
                        resultSet.getInt("CateogryID"),
                        resultSet.getDouble("amount"),
                        resultSet.getObject("transaction_date", LocalDateTime.class),
                        EnTransactionType.valueOf(resultSet.getString("type").toUpperCase()),
resultSet.getString("note")
                        ));
            }
    }
catch(SQLException es){
            System.out.println("Exception"+es.getMessage());
    }
        return Optional.empty();
    }

    @Override
    public List<ClsTransaction> findAll() {
        String query="SELECT * FROM UserTransaction";
        List<ClsTransaction>transactions=new ArrayList<ClsTransaction>();
try(Connection connection=_databaseConnection.getConnection();
    PreparedStatement  statement= connection.prepareStatement(query);
    ){
ResultSet resultSet= statement.executeQuery();
while(resultSet.next()){
  transactions.add(  new ClsTransaction(resultSet.getInt("ID")
            ,resultSet.getInt("FK_WalletID"),
            resultSet.getInt("FK_SavingGoalID"),
            resultSet.getInt("CateogryID"),
            resultSet.getDouble("amount"),
            resultSet.getObject("transaction_date", LocalDateTime.class),
            EnTransactionType.valueOf(resultSet.getString("type").toUpperCase()),
            resultSet.getString("note")
    ));
}
return transactions;

}
catch(SQLException es){
    System.out.println("Exception"+es.getMessage());
}
        return List.of();
    }

    @Override
    public boolean save(ClsTransaction entity) {
        String query="INSERT INTO UserTransaction(ID" +
                ",FK_WalletID,FK_SavingGoalID,CatogryID,amount,transaction_date,type,note) VALUES(?,?,?,?,?,?,?,?)";
        try(Connection connection= _databaseConnection.getConnection();
        PreparedStatement statement=connection.prepareStatement(query);
        )
        {statement.setInt(1,entity.get_transactionID());
            statement.setInt(2,entity.get_walletID());
                statement.setInt(3,entity.get_walletID());
                statement.setInt(4,entity.get_categoryID());
                statement.setDouble(5,entity.get_amount());
                statement.setObject(6,entity.get_localDateTime());
              statement.setString(7,entity.get_type().name());
              statement.setString(8,entity.get_note());
              if(statement.executeUpdate()>0)
                  return true;

        }

            catch(SQLException es){
                System.out.println("Exception"+es.getMessage());
            }
        return false;
    }

    @Override
    public boolean update(ClsTransaction entity) {
        String query="UPDATE UserTransaction SET FK_WalletID=?," +
                " FK_SavingGoalID=?," +
                "CateogryID=?," +
                "amount=?," +
                "transaction_date=?," +
                "type=?," +
                "note=?," +
                "WHERE ID=?";
        try(Connection connection= _databaseConnection.getConnection();
        PreparedStatement statement=connection.prepareStatement(query);
        ){ statement.setInt(1,entity.get_walletID());
            statement.setInt(2,entity.get_walletID());
            statement.setInt(3,entity.get_categoryID());
            statement.setDouble(4,entity.get_amount());
            statement.setObject(5,entity.get_localDateTime());
            statement.setString(6,entity.get_type().name());
            statement.setString(7,entity.get_note());
            statement.setInt(8,entity.get_transactionID());
            if(statement.executeUpdate()>0)
                return true;
        }
        catch(SQLException es){
            System.out.println("Exception"+es.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int ID) {
        String query="DELETE FROM UserTransaction WHERE ID=?";
        try(Connection connection= _databaseConnection.getConnection();
            PreparedStatement statement= connection.prepareStatement(query);){
            statement.setInt(1,ID);
            if(statement.executeUpdate()>0)
                return true;
        } catch (Exception es) {
            System.out.println("Exception"+es.getMessage());
        }
        return false;
    }
}
