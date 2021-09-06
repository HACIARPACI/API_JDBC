package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.*;

public class dynamic_list {
    String dbUrl = "jdbc:oracle:thin:@54.237.118.39:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void MetaDataExample2() throws SQLException {
        //create connection
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from countries");

        //get the resultset object metadata
        ResultSetMetaData rsMetadata = resultSet.getMetaData();

        List<Map<String, Object>> querryData = new ArrayList<>();

        //number of columns
        int colCount = rsMetadata.getColumnCount();

        //loopthrough each row
        while (resultSet.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i < colCount; i++) {
                row.put(rsMetadata.getColumnName(i), resultSet.getObject(i));
            }
            //adding map to list
            querryData.add(row);
        }
        //print the result
        for (Map<String, Object> row : querryData) {
            System.out.println(row.toString());// burda Map içini alt alta gösteriri
        }
        System.out.println("*****************");
        System.out.println(querryData);// burda list i gösteriri yanyana



        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }
}
