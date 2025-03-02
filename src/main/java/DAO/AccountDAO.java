package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    /**
     * Retrieve an account from the account table, identified by its username.
     * @param username
     * @return an account identified by username
     */
    public Account getAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where username=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));           
                return account;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieve an account from the account table, identified by its account_id.
     * @param id
     * @return an account identified by id
     */
    public Account getAccountById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where account_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));           
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Insert a new account into the account table.
     * @param account
     * @return the inserted account
     */
    public Account registerAccount(Account account) {
        if ((account.username.length() > 0) && (account.password.length() > 4)) {
            Connection connection = ConnectionUtil.getConnection();
            try {
                String sql = "insert into account (username, password) values (?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, account.username);
                preparedStatement.setString(2, account.password);

                preparedStatement.executeUpdate();
                return account;
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        } 
        return null;
    }
}
