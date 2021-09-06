package jdbctests;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        String dbUrl = "jdbc:oracle:thin:@54.237.118.39:1521:xe";
        String dbUsername = "hr";
        String dbPassword = "hr";


        //create connection
        Connection connection= DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //creating statement object
        Statement statement = connection.createStatement();
        // run querry and get the result in resultset object
        ResultSet resultSet= statement.executeQuery("select * from departments");



//        //move pointer to the first row
//        resultSet.next();
//
//        //with column name
//        System.out.println(resultSet.getString("region_name"));
//        //with column index
//        System.out.println(resultSet.getString(2));
//        System.out.println(resultSet.getInt(1)+"-"+resultSet.getString("region_name"));
//        System.out.println(resultSet.getString("region_id"));
//
//
//
//        //move pointer to second row
//        resultSet.next();
//        //with column name
//        System.out.println(resultSet.getString("region_name"));
//        //with column index
//        System.out.println(resultSet.getString(2));
//        System.out.println(resultSet.getInt(1)+"-"+resultSet.getString("region_name"));
//
//        //move tothird row
//        resultSet.next();
//        System.out.println(resultSet.getInt(1)+"-"+resultSet.getString("region_name"));

        while(resultSet.next()){
            System.out.println(resultSet.getString(1)+" - "+resultSet.getString(2) +" - "+resultSet.getString(3)+"-"+resultSet.getString(4));

        }

        // close all connections
        resultSet.close();
        statement.close();
        connection.close();



    }
}
