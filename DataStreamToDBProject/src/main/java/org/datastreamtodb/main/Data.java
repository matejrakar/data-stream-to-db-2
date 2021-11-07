package org.datastreamtodb.main;

public class Data {
	enum TypeOfEvent {
		A,
		B
	}
	private int matchId;
	private TypeOfEvent eventType;
	private int occurence;
	private int marketId;
	private String outcomeId;
	private String specifiers;
	private int inputNumber;
	private boolean processed;
	
	public Data(int matchId, TypeOfEvent eventType, int occurence, int marketId, String outcomeId, String specifiers, int inputNumber, boolean processed){
		this.matchId = matchId;
		this.eventType = eventType;
		this.occurence = occurence;
		this.marketId = marketId;
		this.outcomeId = outcomeId;
		this.specifiers = specifiers;
		this.inputNumber = inputNumber;
		this.processed = processed;
	}
	
	public Data(int matchId, TypeOfEvent eventType, int occurence, int marketId, String outcomeId, String specifiers, boolean processed){
		this.matchId = matchId;
		this.eventType = eventType;
		this.occurence = occurence;
		this.marketId = marketId;
		this.outcomeId = outcomeId;
		this.specifiers = specifiers;
		this.processed = processed;
	}
	
	public Data() {};

	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public TypeOfEvent getEventType() {
		return eventType;
	}

	public void setEventType(TypeOfEvent eventType) {
		this.eventType = eventType;
	}

	public int getOccurence() {
		return occurence;
	}

	public void setOccurence(int occurence) {
		this.occurence = occurence;
	}

	public int getMarketId() {
		return marketId;
	}

	public void setMarketId(int marketId) {
		this.marketId = marketId;
	}

	public String getOutcomeId() {
		return outcomeId;
	}

	public void setOutcomeId(String outcomeId) {
		this.outcomeId = outcomeId;
	}

	public String getSpecifiers() {
		return specifiers;
	}

	public void setSpecifiers(String specifiers) {
		this.specifiers = specifiers;
	}

	public int getInputNumber() {
		return inputNumber;
	}

	public void setInputNumber(int inputNumber) {
		this.inputNumber = inputNumber;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	
	
	
}
