package org.ibs;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class BDConnection {
    static DataSource dataSource = getH2DataSource();

    public static Connection getConnection() {
        Connection connection =null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static DataSource getH2DataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(ConfProperties.getProperty("db.host"));
        dataSource.setUser(ConfProperties.getProperty("db.login"));
        dataSource.setPassword(ConfProperties.getProperty("db.password"));
        return dataSource;
    }


}
