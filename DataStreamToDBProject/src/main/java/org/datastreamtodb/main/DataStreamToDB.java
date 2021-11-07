package org.datastreamtodb.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang3.StringUtils;
import org.datastreamtodb.main.Data.TypeOfEvent;

public class DataStreamToDB {
	
	public static void main(String[] args){
		//ystem.out.println("Hello world");
		
		Connection connect = null;
		PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        
        List<Future<Data>> futureResults = new ArrayList();
	    try {
		    //Class.forName("com.mysql.cj.jdbc.Driver");
	        //connect = DriverManager.getConnection("jdbc:mysql://localhost/datastream?user=root&password=root");
	        BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\org\\datastreamtodb\\main\\fo_random_test.txt"));
	        String line = null;
	        int i = 0;
	        br.readLine();
	        DataMap dataMap = new DataMap();
	        while ((line = br.readLine()) != null)//.replaceAll("[^0-9]", "")
	        {
	        	String[] data = line.split("\\|", 4); //We split line by first 3 pipes.
	        	Data currentData = retrieveDataFromLine(data);
	        	dataMap.addToDataMap(currentData); //We add retrieved data from line to dataMap object.
        		
	        	MyCallable callable  = new MyCallable(currentData);
                Future<Data> result = executor.submit(callable);
                futureResults.add(result);
                
                for (int counter = 0; counter < futureResults.size(); counter++) { 		
                	Future<Data> tempFutureRes = futureResults.get(counter);
                    if(tempFutureRes.isDone()) {
                		List<Data> list = dataMap.getDataMap().get(((Data) tempFutureRes).getMatchId());
                		Data tempData = list.get(((Data) tempFutureRes).getMatchId());
                		if(tempData.getInputNumber() == ((Data) tempFutureRes).getInputNumber()) { //If match id and input number match, we change existing array entry with new processed one
    						dataMap.getDataMap().get(tempData.getMatchId()).set(((Data) tempFutureRes).getInputNumber(), (Data) tempFutureRes);
    					}
                		futureResults.remove(counter); //Remove from future queue
                    }
                }  
                

	        }
	        executor.shutdown();
	        /*dataMap.print();
	        Data d = new Data();
	        d.setMatchId(111);
            Runnable r = new MyRunnable(d);
            Thread t = new Thread(r);  //thread(() -> processData("a"))
            t.start();*/
	    }
	    catch(Exception e) {
	    	System.out.println("ERROR");
	    	System.out.println(e.getMessage());
	    	System.out.println("ERROR");
	    }
	}
	
	public static Data retrieveDataFromLine(String[] data) {
		try {
			Data dataObject = new Data();
			//System.out.println("retrieveDataFromLine");
			String relevantData = data[0].split(":")[2]; //We split string by colons and we take the last one on third position
	    	dataObject.setEventType(TypeOfEvent.valueOf(relevantData.replaceAll("[^A-Za-z]+", "")));//We only get event type from string.
	    	String[] temp = relevantData.split(String.valueOf(dataObject.getEventType())); //We split by event type to get matchId in first position of String array and occurence in second
	    	dataObject.setMatchId(Integer.valueOf(temp[0])); 
	    	dataObject.setOccurence(Integer.valueOf(StringUtils.chop(temp[1])));//.substring(0,temp[1].length()-1);
	    	
	    	dataObject.setMarketId(Integer.valueOf(data[1]));
	    	
	    	dataObject.setOutcomeId(data[2]);
	    	
	    	dataObject.setSpecifiers(data[3]);
	    	
	    	dataObject.setProcessed(false);
	    	
	    	return dataObject;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return new Data();
		}
	}
	
	public static void processData(String str) {
		System.out.println(str);
	}
	
}
