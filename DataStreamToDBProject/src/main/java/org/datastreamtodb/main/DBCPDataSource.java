package org.datastreamtodb.main;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * Connection pool class which makes use of DBCP component.
 * @author Matej
 *
 */
public class DBCPDataSource {
    
    private static BasicDataSource ds = new BasicDataSource();
    
    static {
        ds.setUrl("jdbc:mysql://localhost/datastream");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setMinIdle(16);
        ds.setMaxIdle(16);
        ds.setMaxOpenPreparedStatements(100);
    }
    
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    private DBCPDataSource(){ }
}