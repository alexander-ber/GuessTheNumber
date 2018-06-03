package com.game.GuessTheNumber;

import java.util.ArrayList;

public class Game {
	private Integer userId;
	private Integer attempsCounter;
	private ArrayList<Integer> secretNumber;
	private ArrayList<GameLog> gameLog;
	private String userName;
	private Boolean isWinner;
	private Boolean isEnded;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public Game(Game g) {
		this.userId = g.userId;
		this.attempsCounter = g.attempsCounter;
		this.gameLog = this.cloneArrayListGameLog(g.getGameLog()); 
		this.userName = g.userName;
		this.isWinner = g.isWinner;
		this.isEnded = g.isEnded;
	}
	
	protected ArrayList<GameLog> cloneArrayListGameLog(ArrayList<GameLog> glList) {
		ArrayList<GameLog> clonedGlList = new ArrayList<GameLog>(glList.size());
	    for (GameLog gl : glList) {
	    	clonedGlList.add(new GameLog(gl));
	    }
	    return clonedGlList;
	}

	public ArrayList<Integer> checkAttemp(String attemp) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		String[] strArray = attemp.split("");
		for (int i = 0; i < 4; i++) {
			int ind = secretNumber.indexOf( Integer.parseInt(strArray[i]));
			if(ind > -1 ) {
				if(ind == i) {
					result.add(1);
				} else {
					result.add(0);
				}
			}
		}
		
		// convert string to List of integers
		ArrayList<Integer> intList = new ArrayList<Integer>();
		for(String s : strArray) intList.add(Integer.valueOf(s));
		attempsCounter++;
		this.gameLog.add(new GameLog(attempsCounter,intList,result));
		return result;
	}
	
	@Override
	public String toString() {
		return "Game [ userName= "+ userName + ", "
				+ "attempsCounter=" + attempsCounter + ", userId=" + userId + ", secretNumber=" + secretNumber + "]";
	}

	public Integer getAttempsCounter() {
		return attempsCounter;
	}

	public void setAttempsCounter(Integer trysCounter) {
		this.attempsCounter = trysCounter;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public ArrayList<Integer> getSecretNumber() {
		return secretNumber;
	}

	public void setSecretNumber(ArrayList<Integer> secretNumber) {
		this.secretNumber = secretNumber;
	}

	public Game(Integer userId, ArrayList<Integer> secretNumber, String userName) {
		super();
		this.userId = userId;
		this.attempsCounter = 0;
		this.secretNumber = secretNumber;
		this.gameLog = new ArrayList<GameLog>();
		this.userName = userName;
		this.isEnded = false;
		this.isWinner = false;
	}

	public Boolean getIsEnded() {
		return isEnded;
	}

	public void setIsEnded(Boolean isEnded) {
		this.isEnded = isEnded;
	}
	

	public Boolean getIsWinner() {
		return isWinner;
	}

	public void setIsWinner(Boolean isWinner) {
		this.isWinner = isWinner;
	}

	public ArrayList<GameLog> getGameLog() {
		return gameLog;
	}

	public void setGameLog(ArrayList<GameLog> gameLog) {
		this.gameLog = gameLog;
	}
}
