package org.datastreamtodb.main;

interface Callback{
	Data callback(Data data);
}

public class MyRunnable implements Runnable{
	private Data data;
	//Callback c;
	
	MyRunnable(Data data){
		this.data = data;
		//this.c = c;
	}
	
	public void run() {
		Data data = this.getData();
		//this.c.callback(data);
		System.out.println("data match id from runnable " + data.getMatchId());
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
	/*public void callback(Data data) {
		return
	}*/
}
