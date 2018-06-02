package com.game.GuessTheNumber;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GameLog {
	private Integer logID;
	private ArrayList<Integer> guess;
	private ArrayList<Integer> result;
	
	public GameLog(Integer logID, ArrayList<Integer> guess, ArrayList<Integer> result) {
		super();
		this.logID = logID;	
		this.guess = guess;
		this.result = result;
	}
	
	public Integer getId() {
		return this.logID;
	}

	public ArrayList<Integer> getGuess() {
		return guess;
	}
	public void setGuess(ArrayList<Integer> guess) {
		this.guess = guess;
	}
	public ArrayList<Integer> getResult() {
		return result;
	}
	public void setResult(ArrayList<Integer> result) {
		this.result = result;
	}
	
	

}
