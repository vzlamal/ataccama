package com.example.ataccama.service;


import com.example.ataccama.domain.Entry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
        ResultSet resultSet = connection.getMetaData().getColumns(null, null, table, "%");

        while (resultSet.next()) {
            String columnName = resultSet.getString("COLUMN_NAME");
            String dataType = resultSet.getString("TYPE_NAME");
            String columnSize = resultSet.getString("COLUMN_SIZE");
            result.add(columnName + ", " + dataType + ", " + columnSize);
        }
        connection.close();
        return result;
    }

    public List<List<String>> getPreview(Entry entry, String table) throws SQLException {
        List<String> allowList = List.of("entry", "meh", "test_table");
        if (allowList.contains(table)) {
            Connection connection = connect(entry);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  * FROM " + allowList.get(allowList.indexOf(table)));
            int nColumns = resultSet.getMetaData().getColumnCount();

            List<List<String>> result = new ArrayList<>();
            while (resultSet.next()) {
                List<String> tmp = new ArrayList<>();
                for (int i = 1; i <= nColumns; i++) {
                    tmp.add(resultSet.getString(i));
                }
                result.add(tmp);
            }
            connection.close();
            return result;
        } else {
            throw new RuntimeException("Requested information from table that is not on allow list.");
        }
    }
}
