package com.example.ataccama.service;


import com.example.ataccama.domain.Entry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatabaseConnectorService {


    private Connection connect(Entry entry) throws SQLException {
        return DriverManager.getConnection(entry.getConnectionString(), entry.getName(), entry.getPass());
    }

    public List<String> getTables(Entry entry) throws SQLException {
        Connection connection = connect(entry);
        ResultSet tables = connection.getMetaData().getTables(null, "public", "%", new String[]{"TABLE"});

        List<String> result = new ArrayList<>();
        while (tables.next()) {
            result.add(tables.getString(3));
        }
        connection.close();
        return result;
    }

    public List<String> getColumns(Entry entry, String table) throws SQLException {
        Connection connection = connect(entry);
        List<String> result = new ArrayList<>();
        ResultSet rs = connection.getMetaData().getColumns(null, null, table, "%");

        while (rs.next()) {
            result.add(rs.getString("COLUMN_NAME"));
        }
        connection.close();
        return result;
    }

    public List<List<String>> getPreview(Entry entry, String table) throws SQLException {
        List<String> allowList = List.of("entry", "meh");
        if (allowList.contains(table)) {
            Connection connection = connect(entry);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT  * FROM " + table);
            int nColumns = rs.getMetaData().getColumnCount();

            List<List<String>> result = new ArrayList<>();
            while (rs.next()) {
               List<String > tmp = new ArrayList<>();
                for (int i = 1; i <= nColumns; i++) {
                    tmp.add(rs.getString(i));
                }
                result.add(tmp);
            }
            connection.close();
            return result;
        } else {
            throw new RuntimeException("Requested information from table that is not on allow list.");
        }
    }


    public static void main(String[] args) throws SQLException {
        DatabaseConnectorService databaseConnectorService = new DatabaseConnectorService();
        Entry e = new Entry("app", "ataccama", "ataccama", "jdbc:postgresql://localhost", 5432);

    }


}
