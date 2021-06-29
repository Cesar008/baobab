/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.security.util.Password;
/**
 *
 * @author admincofcg
 */
public class database {
    String url = "jdbc:mysql://localhost:3306/baobab";
    String username = "root";
    String password = "";
    java.sql.Connection conn;

    public database() {
        try {
            //        this.url = "jdbc:mysql://localhost:3306/baobab";
            //        this.username = "root";
            //        this.password = "";
            this.conn = DriverManager.getConnection(url, username, password);
            
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Connect(){
        if (conn != null) {
            System.out.println("Connected");
        }
        
    }
    
    /**
     * 
     * @param lastname
     * @param firstname
     * @param sexe
     * @param phoneNumber
     * @param email
     * @param address
     * @param city
     * @param country
     * @param birthday
     * @return 
     */
    public String SetCustomer(String lastname, String firstname, String sexe, String phoneNumber, String email, String address, String city, String country, String birthday){
        String sql = "INSERT INTO customer (lastname, firstname, birthday, sexe, phoneNumber, address, email, city, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement;
        
        String message;
        
        try {
            statement = getConn().prepareStatement(sql);
            statement.setString(1, lastname);
            statement.setString(2, firstname);
            statement.setString(3, birthday);
            statement.setString(4, sexe);
            statement.setString(5, phoneNumber);
            statement.setString(6, address);
            statement.setString(7, email);
            statement.setString(8, city);
            statement.setString(9, country);
            
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                
                return message ="Le client cree avec success";
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message ="Erreur";
    }
    
    /**
     * getCustomerID()
     * @param phoneNumber
     * @return 
     */
    public int getCustomerID(String phoneNumber){
        String SQL = "SELECT * FROM customer WHERE phoneNumber = ?";
        PreparedStatement statement;
        try {
            statement = getConn().prepareStatement(SQL);
            statement.setString(1, phoneNumber);
            
            ResultSet result = statement.executeQuery();
            
            if(result.next()){
                return result.getInt("id");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    /**
     * getCustomer()
     * @param phoneNumber
     * @return 
     */
    public ResultSet getCustomer(String phoneNumber){
        String SQL = "SELECT * FROM customer WHERE phoneNumber = ?";
        PreparedStatement statement;
        try {
            statement = getConn().prepareStatement(SQL);
            statement.setString(1, phoneNumber);
            
            ResultSet result = statement.executeQuery();
            
            return result;
            
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * getCustomerCount()
     * @param phoneNumber
     * @return 
     */
    public int getCustomerCount(String phoneNumber){
        String SQL = "SELECT COUNT(*) AS total FROM customer WHERE phoneNumber = ?";
        PreparedStatement statement;
        try {
            statement = getConn().prepareStatement(SQL);
            statement.setString(1, phoneNumber);
            
            ResultSet result = statement.executeQuery();
            
            if(result.next()){
                return result.getInt("total");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    /**
     * getCustomerCountByNumber()
     * @param phoneNumber
     * @return 
     */
    public int getCustomerCountByNumber(String number){
        String SQL = "SELECT COUNT(*) AS total FROM customer C, account A WHERE C.id = A.customer AND A.number = ?";
        PreparedStatement statement;
        try {
            statement = getConn().prepareStatement(SQL);
            statement.setString(1, number);
            
            ResultSet result = statement.executeQuery();
            
            if(result.next()){
                return result.getInt("total");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    /**
     * getAllCustomerAndCount()
     * @return 
     */
    public ResultSet getAllCustomerAndCount(){
        String SQL = "SELECT C.*, A.type, A.number, A.balance FROM customer C, account A WHERE C.id = A.customer";
        PreparedStatement statement;
        try {
            statement = getConn().prepareStatement(SQL);
            
            ResultSet result = statement.executeQuery();
            
            return result;
            
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * checkAccount()
     * @param number
     * @return 
     */
    public ResultSet checkAccount(String number){
        String SQL = "SELECT C.*, A.id As _account,A.type, A.number, A.balance FROM customer C, account A WHERE C.id = A.customer AND A.number = ?";
        PreparedStatement statement;
        try {
            statement = getConn().prepareStatement(SQL);
            statement.setString(1, number);
            
            ResultSet result = statement.executeQuery();
            
            return result;
            
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * withDrawal()
     * @param number
     * @return 
     */
    public String withDrawal(int balance, int id){
        String SQL = "UPDATE account SET balance = ? WHERE id = ?";
        PreparedStatement statement;
        
        String message;
        
        try {
            statement = getConn().prepareStatement(SQL);
            statement.setInt(1, balance);
            statement.setInt(2, id);
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                
                return message ="Opéreation effectuer avec succès";
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message ="Erreur";
    }
    
    /**
     * setAccount()
     * @param phoneNumber
     * @return 
     */
    public String setAccount(String phoneNumber){
        String SQL = "INSERT INTO account (type, number, balance, customer) VALUES (?, ?, ?, ?)";
        PreparedStatement statement;
        try {
            statement = getConn().prepareStatement(SQL);
            int id = this.getCustomerID(phoneNumber);
            
            if(id > 0){
                String[] tab = {"Epargne", "Courant"};

                for (int i = 0; i < tab.length; i++) {
                    String type = tab[i];
                    double balance = 0.0;
                    int customer = id;
                    String number;
                    String prefix;

                    if (id < 10) {
                        prefix = "000" + id;
                    } else if (id < 100) {
                        prefix = "00" + id;
                    } else if (id < 1000) {
                        prefix = "0" + id;
                    } else {
                        prefix = String.valueOf(id);
                    }

                    if (type == "Epargne") {
                        number = "LIV" + prefix;
                    } else {
                        number = "CCO" + prefix;
                    }

                    statement = getConn().prepareStatement(SQL);
                    statement.setString(1, type);
                    statement.setString(2, number);
                    statement.setDouble(3, balance);
                    statement.setInt(4, id);

                    int _rowsInserted = statement.executeUpdate();
                    if (_rowsInserted > 0) {
                        System.out.println("Compte "+type+" cree avec succes");
                    }

                }
            }
            
            
            String message;
            
            return message = "Compte cree avec succes";
            
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public java.sql.Connection getConn() {
        return conn;
    }

    public void setConn(java.sql.Connection conn) {
        this.conn = conn;
    }
    
    
    
}
