package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;

public class jdbs_example {
    String dbUrl = "jdbc:oracle:thin:@54.237.118.39:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";


    @Test
    public void test1() throws SQLException {
        //create connection
        Connection connection= DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //creating statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        // run querry and get the result in resultset object
        ResultSet resultSet= statement.executeQuery("select * from departments");

        //how to find how many rows we have for querry
        //go to last  row
        resultSet.last();
        //get the row count
        int row = resultSet.getRow();
        System.out.println("row = " + row);

        //basa döner before first bunu yazmazsak cursor sonda olduğu için bişey yazdırmaz
        resultSet.beforeFirst();
        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }
        //=======================================================

        //farklı bir sorgu için statement.execute buraya yazılablir
        resultSet= statement.executeQuery("select * from regions");
        while(resultSet.next()){
            System.out.println(resultSet.getString(2));


        }


        // close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }


    @Test
    public void MetaDataExamples() throws SQLException {
        //create connection
        Connection connection= DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //creating statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        // run querry and get the result in resultset object
        ResultSet resultSet= statement.executeQuery("select * from employees");

        //get the database related data inside the dbMetadata object
        DatabaseMetaData dbMetadata = connection.getMetaData();
        System.out.println("User ="+dbMetadata.getUserName());
        System.out.println("Database Product Name="+dbMetadata.getDatabaseProductName());
        System.out.println("Database production Version="+dbMetadata.getDatabaseProductVersion());
        System.out.println("Driver name ="+dbMetadata.getDriverName());
        System.out.println("Driver version="+dbMetadata.getDriverVersion());

        //*********************************************************************************************

        //get the resultset object metadata
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        //how may columns we have
        int colCount=rsMetaData.getColumnCount();
        System.out.println("colCount = " + colCount);

        //how to get column names
        System.out.println(rsMetaData.getColumnName(1));
        System.out.println(rsMetaData.getColumnName(2));

        // rsMetadata.getColumnName(1); --> Column Name
        // resultSet.getString(1); --> column value

        //print all the column names dynamicallys
      //  for (int i = 1; i <=rsMetaData.getColumnCount() ; i++) {
           for (int i = 1; i <=colCount ; i++) {
            System.out.println(rsMetaData.getColumnName(i));
        }





        // close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }
}
