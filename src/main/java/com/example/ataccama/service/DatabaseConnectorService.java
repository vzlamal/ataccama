package com.example.ataccama.service;


import com.example.ataccama.domain.Entry;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DatabaseConnectorService {


    private Connection connect(Entry entry) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(entry.getConnectionString(), entry.getName(), entry.getPass());

            if (connection != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public Set<String> getTables(Entry entry) throws SQLException {
        Connection connection = connect(entry);
        ResultSet tables = connection.getMetaData().getTables(null, "public", "%", new String[]{"TABLE"});

        Set<String> result = new HashSet<>();
        while (tables.next()) {
            System.out.println(tables.getString(3));
            result.add(tables.getString(3));
        }
        connection.close();
        return result;
    }

    public Set<String> getColumns(Entry entry,String table) throws SQLException {
        Connection connection = connect(entry);
        Set<String> result = new HashSet<>();
        ResultSet rs = connection.getMetaData().getColumns(null,null,table ,"%");

        while(rs.next()){
            result.add(rs.getString("COLUMN_NAME"));
        }
        return result;
    }


    public static void main(String[] args) throws SQLException {
        DatabaseConnectorService databaseConnectorService = new DatabaseConnectorService();
        Entry e = new Entry("app", "ataccama", "ataccama", "jdbc:postgresql://localhost", 5432);
//        Set<String> schema = databaseConnectorService.getSchema(new Entry("app", "ataccama", "ataccama", "jdbc:postgresql://localhost", 5432));
        Connection conn = databaseConnectorService.connect(e);
        DatabaseMetaData dmd = conn.getMetaData();
        ResultSet rs1 = dmd.getSchemas();

            ResultSet rs2 = dmd.getTables(null, "public", "%", new String[]{"TABLE"});
            while (rs2.next())
                System.out.println(rs2.getString(3) + " " + rs2.getString(4));

        conn.close();
        System.out.println("___________");
        Set<String> schema = databaseConnectorService.getTables(e);
        for (String s : schema) {
            System.out.println(s);
        }
    }


}
