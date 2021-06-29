/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;


import View.Customer;
import au.com.bytecode.opencsv.CSVWriter;
import config.database;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admincofcg
 */
public class CSV {
    
    public void Export() throws IOException{

        List<String[]> csvData;
        csvData = createCsvDataSpecial();

        // default all fields are enclosed in double quotes
        // default separator is a comma
        try (CSVWriter writer = new CSVWriter(new FileWriter("c:\\baobab\\List.csv"))) {
            writer.writeAll(csvData);
        }

    }

    private static List<String[]> createCsvDataSpecial() {

        String[] header = {"id", "Nom", "Prenom", "Sexe", "Telephone", "Ville", "Type de compte", "Num compte", "Montant"};
        
        
        int reqCount;
        
        try {
            database db = new database();
            db.Connect();
            ResultSet result = db.getAllCustomerAndCount();
            ResultSetMetaData RSMD = result.getMetaData();
            reqCount = RSMD.getColumnCount();
            
            while(result.next()){
                List<String[]> list = new ArrayList<>();
                list.add(header);
                
                for(int i=0; i<reqCount; i++){
                    
                    int id = result.getInt("id");
                    String lastname     = result.getString("lastname");
                    String firstname    = result.getString("firstname");
                    String sexe         = result.getString("sexe");
                    String phoneNumber  = result.getString("phoneNumber");
                    String city         = result.getString("city");
                    String type         = result.getString("type");
                    String number       = result.getString("number");
                    int balance         = result.getInt("balance");
                    
                    String[] record = {Integer.toString(id), lastname, firstname, sexe, phoneNumber, city, type, number, Integer.toString(balance)};
                    list.add(record);
                }
                
                return list;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
    
}
