package org.datastreamtodb.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.commons.lang3.StringUtils;
import org.datastreamtodb.main.Data.TypeOfEvent;

public class DataStreamToDB {
	
	public static void main(String[] args) throws SQLException{
		Connection connection = DBCPDataSource.getConnection();
	    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        List<Future<Data>> futureResults = new ArrayList();
	    try {
	    	DataMap dataMap = new DataMap();
	        BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\org\\datastreamtodb\\main\\fo_random_test.txt"));
	        String line = null;
	        br.readLine(); //We skip first line, which contains column ID
	        while ((line = br.readLine()) != null || 1 == 1){// 1==1 => to give a program time to finish all pending tasks even when all lines are read.
	        	if(line != null) { //As long as we are reading data, we continue to retrieve it from lines and pass it to be processed by threads.
		        	//String[] data = line.split("\\|", 4); //We split line by first 3 pipes.
		        	Data currentData = retrieveDataFromLine(line);
		        	dataMap.addToDataMap(currentData); //We add retrieved data from line to dataMap object.
	        		
		        	ProcessDataCallable callable  = new ProcessDataCallable(currentData);
	                Future<Data> result = executor.submit(callable); //We send our data to be processed in another thread.
	                
	                futureResults.add(result); //We monitor if data is processed in futureResults List.
	        	}
	        	
	        	/**
	        	 * The following for loop cycles through all our results that are yet to be inserted in DB. It makes sure that data is inserted in DB in correct (read) order based on MatchId.
	        	 * For example: If 1A1 with InputNumber 0 is not processed and inserted yet, then 1B1 with InputNumber 1 will wait as long as 1A1 will not be inserted, even though 1B1 was processed faster.
	        	 * *InputNumber = order in which data was read based on single MatchId
	        	 */
                for (int counter = 0; counter < futureResults.size(); counter++) {
                	boolean dbInsertFlag = true; //Boolean value, that indicates whether insert in DB is possible due to potential pending events with higher time difficulty (A)
                	Future<Data> tempFutureRes = futureResults.get(counter); //We get a single Future<Data> from List of Future<Data> elements
                	
                    if(tempFutureRes.isDone()) { //We check if this entry is already done executing => is already processed (processed = true)
                    	int tempMatchId = ((Data) tempFutureRes.get()).getMatchId(); //We save MatchId to temporary variable.
                		List<Data> tempList = dataMap.getDataMap().get(tempMatchId); //We get List<Data> tempList from dataMap for specific MatchId
                		int tempInputNumber = ((Data) tempFutureRes.get()).getInputNumber(); //We save InputNumber to temporary variable.
                		
                		for(int j = 0; j<tempInputNumber; j++) { //We cycle through all entries in list, which have InputNumber smaller than our entry's InputNumber => those entries have to be inserted before our entry            		
                			if(tempList.get(j).getInputNumber() < tempInputNumber) { //We check if InputNumber is for sure smaller than InputNumber of our entry
                    			if(tempList.get(j).isInserted() == false) { //If this condition, then it must mean that an entry with higher difficulty before our element is still waiting to be processed.
                					dbInsertFlag = false; //We prohibit insert to database.
                					break; //One such entry is enough that we will not be able to insert into database this time, so we break from loop with dbInsertFlag set to false.
                    			}
                			}
                			else {
                				break;
                			}
                		}
                		if(dbInsertFlag) { //If dbInsertFlag remained set to true, we can insert into database.
                			System.out.println("DB Insert MatchId: " + ((Data) tempFutureRes.get()).getMatchId() + " " + ((Data) tempFutureRes.get()).getEventType() +" InputNumber: "+ ((Data) tempFutureRes.get()).getInputNumber());
                			InsertToDBCallable insertToDBCallable  = new InsertToDBCallable(dataMap.getDataMap().get(tempMatchId).get(tempInputNumber), connection);
        	                executor.submit(insertToDBCallable);
        	                futureResults.remove(counter); //We remove this entry from futureResults as we have already processed and inserted it.
                			dataMap.getDataMap().get(tempMatchId).get(tempInputNumber).setInserted(true);//We set inserted value to true.
                		}
                    }
                } 
                
                if(futureResults.size() == 0 && line == null) { //When there is no more lines from text file and also Future<Data> array is empty, we end loop.
                	break;
            	}
	        }
	        dataMap.print();
	        executor.shutdown();
	        br.close();
	    }
	    catch(Exception e) {
	    	System.out.println(e.getMessage());
	    	//e.printStackTrace();
	    }
	}
	
	/**
	 * Retrieves data from a read line of specific shape.
	 * @param line Line read from text file
	 * @return Data object with set values to match information extracted from line.
	 */
	public static Data retrieveDataFromLine(String line) {
		try {
			Data dataObject = new Data();
			String[] data = line.split("\\|", 4); //We split line by first 3 pipes.
			String relevantData = data[0].split(":")[2]; //We split string by colons and we take the last one on third position
	    	dataObject.setEventType(TypeOfEvent.valueOf(relevantData.replaceAll("[^A-Za-z]+", "")));//We only get event type from string.
	    	String[] temp = relevantData.split(String.valueOf(dataObject.getEventType())); //We split by event type to get matchId in first position of String array and occurence in second
	    	
	    	dataObject.setMatchId(Integer.valueOf(temp[0])); 
	    	dataObject.setOccurence(Integer.valueOf(StringUtils.chop(temp[1])));
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
	
}
