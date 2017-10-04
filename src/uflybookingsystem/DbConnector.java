/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uflybookingsystem;

import java.sql.*;

/**
 *
 * @author 92016097
 */
public class DbConnector {
    public static Connection connectToDb() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/";
        // ?autoReconnect=true&useSSL=false is required to supress complaints
        String database = "ufly?useSSL=false";
        String userName = "root";
        String password = "Delancie";
        return DriverManager.getConnection(url + database, userName, password);
    }
}
