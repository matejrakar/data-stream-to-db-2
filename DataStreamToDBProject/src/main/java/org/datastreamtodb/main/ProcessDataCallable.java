package org.datastreamtodb.main;

import java.util.concurrent.Callable;
import org.datastreamtodb.main.Data.TypeOfEvent;

/**
 * Class ProcessDataCallable implements Callable and contains overriden call() function.
 * @author Matej
 *
 */
public class ProcessDataCallable implements Callable<Data> {
	private Data data;
	/**
	 * Simulates heavy task processing based on event type. After data is processed, it sets Data's processed variable to true.
	 */
	@Override
    public Data call() throws Exception {
		Data data = this.data;
		if (data.getEventType() == TypeOfEvent.valueOf("A")) {
			Thread.sleep(1000);
			data.setProcessed(true);
	        return data;
		}
		else {
			Thread.sleep(1);
			data.setProcessed(true);
	        return data;
		}
		
    }
	
	/**
	 * Constructor that passes essential data to private variable.
	 * @param data Data object
	 */
	public ProcessDataCallable(Data data) {
		this.data = data;
	}
}