package org.datastreamtodb.main;

import java.util.concurrent.Callable;

import org.datastreamtodb.main.Data.TypeOfEvent;

public class MyCallable implements Callable<Data> {
	private Data data;
	@Override
    public Data call() throws Exception {
		Data data = this.data;
		if(data.getEventType() == TypeOfEvent.valueOf("A")) {
			Thread.sleep(1000);
			data.setOccurence(9999999);
			data.setProcessed(true);
	        return data;
		}
		else {
			Thread.sleep(1);
			data.setOccurence(999);
			data.setProcessed(true);
	        return data;
		}
		
    }
	
	public MyCallable(Data data) {
		this.data = data;
	}
}