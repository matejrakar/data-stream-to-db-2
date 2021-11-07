package org.datastreamtodb.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataMap {

	private Map<Integer, List<Data>> dataMap;

	/**
	 * Adds data to this.dataMap. It retrieves MatchId from given Data object and then either creates new list of Data object for this MatchId or adds Data object to an existing one.
	 * @param data Data object to be added to DataMap.
	 */
	public void addToDataMap(Data data) throws Exception {
		try{
			int matchId = data.getMatchId();
			if(this.dataMap.containsKey(matchId)) {
				List<Data> temp = this.dataMap.get(matchId);
				data.setInputNumber(temp.size());
				temp.add(data);
				dataMap.put(matchId, temp);
			}
			else {
				List<Data> temp = new ArrayList<Data>();
				data.setInputNumber(0);
				temp.add(data);
				dataMap.put(matchId, temp);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Prints out whole dataMap object. For demo purposes.
	 */
	public void print() {
		try {
			for (Map.Entry<Integer, List<Data>> entry : this.dataMap.entrySet()) {
				Integer key = entry.getKey();
				List<Data> list = entry.getValue();
				System.out.println("MATCH ID: " + String.valueOf(key));
				for (int i = 0; i < list.size(); i++) {
					Data data = list.get(i);
					System.out.println("Input number: " + data.getInputNumber());
					System.out.println("Market ID: " + data.getMarketId());
					System.out.println("Match ID: " + data.getMatchId());
					System.out.println("Occurence: " + data.getOccurence());
					System.out.println("Outcome ID: " + data.getOutcomeId());
					System.out.println("Specifiers: " + data.getSpecifiers());
					System.out.println("Event type: " + data.getEventType());
					System.out.println("Processed: " + data.isProcessed());
					System.out.println("Inserted: " + data.isInserted());
					System.out.println("********************************");
				}
				System.out.println("--------------------------------");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public DataMap() {
		this.dataMap = new HashMap<Integer, List<Data>>();
	}

	public Map<Integer, List<Data>> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<Integer, List<Data>> dataMap) {
		this.dataMap = dataMap;
	}

}
