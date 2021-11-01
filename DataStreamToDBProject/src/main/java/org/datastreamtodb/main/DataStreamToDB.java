package org.datastreamtodb.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataStreamToDB {
	
	public static void main(String[] args){
		System.out.println("Hello world");
		
		Connection connect = null;
		PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
	        connect = DriverManager.getConnection("jdbc:mysql://localhost/datastream?user=root&password=root");
	        
	        BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\org\\datastreamtodb\\main\\fo_random.txt"));
	        String line = null;
	        int i = 0;
	        br.readLine();//to skip first line
	        while ((line = br.readLine()) != null)//.replaceAll("[^0-9]", "")
	        {
	        	String[] data = line.split("\\|", 4);
	            i++;
	            if(i==2) {
	            	break;
	            }

	        }
	    }
	    catch(Exception e) {
	    	System.out.println(e.getMessage());
	    }
	}
	
}
