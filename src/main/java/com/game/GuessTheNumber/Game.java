package com.game.GuessTheNumber;

import java.util.ArrayList;

public class Game {
	private Integer userId;
	private Integer attempsCounter;
	private ArrayList<Integer> secretNumber;
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList<Integer> checkAttemp(String attemp) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		String[] strArray = attemp.split("");
		for (int i = 0; i < strArray.length; i++) {
			int ind = secretNumber.indexOf( Integer.parseInt(strArray[i]));
			if(ind > -1 ) {
				if(ind == i) {
					result.add(1);
				} else {
					result.add(0);
				}
			}
		}
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
		this.attempsCounter = 0;
		this.userId = userId;
		this.secretNumber = secretNumber;
		this.userName = userName;
	}
}
