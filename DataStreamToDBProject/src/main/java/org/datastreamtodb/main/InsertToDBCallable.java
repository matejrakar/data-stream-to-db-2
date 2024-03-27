package org.datastreamtodb.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.Callable;

/**
 * Class InsertToDBCallable implements Callable and contains overriden call() function.
 * @author Matej
 *
 */
public class InsertToDBCallable implements Callable<String> {
	private Data data;
	private Connection connection;
	
	/**
	 * Insert given Data object's contents to database via PreparedStatement.
	 */
	@Override
    public String call() throws Exception {
		try {
			Data data = this.data;
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO DATA (MATCH_ID, MARKET_ID, OUTCOME_ID, SPECIFIERS) VALUES (?,?,?,?)");
		    preparedStatement.setInt(1, data.getMatchId());
		    preparedStatement.setInt(2, data.getMarketId());
		    preparedStatement.setString(3, data.getOutcomeId());
		    preparedStatement.setString(4, data.getSpecifiers());
	    	preparedStatement.executeUpdate();
			return "Success";
		}
		catch(Exception e) {
			//System.out.println("SQL Error: " + e.getMessage());
			return e.getMessage();
		}
    }
	
	/**
	 * Constructor that passes essential data to private variables.
	 * @param data Data object
	 * @param connection DBCP Connection 
	 */
	public InsertToDBCallable(Data data, Connection connection) {
		this.data = data;
		this.connection = connection;
	}
}