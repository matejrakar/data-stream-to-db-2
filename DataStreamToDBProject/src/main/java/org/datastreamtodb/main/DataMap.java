package org.datastreamtodb.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataMap {

	private Map<Integer, List<Data>> dataMap; //= new HashMap<Integer, List<Data>>();

	public void addToDataMap(Data data) throws Exception {
		try{
			//System.out.println("try in addtodatamap");
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

	public void print() {
		//System.out.println("PRINT dataMap");
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
