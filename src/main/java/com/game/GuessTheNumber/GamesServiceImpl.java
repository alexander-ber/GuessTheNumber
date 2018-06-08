package com.game.GuessTheNumber;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class GamesServiceImpl implements GamesService {
	
	private AtomicInteger gameId = new AtomicInteger(100);
	private HashMap<Integer, Game> hmap = new HashMap<Integer, Game>();
	private final Integer maxAttemps = 20; //20
	private final Integer maxWinners = 10; //10
	private final String adminPass = "Nimda";
	
	public HashMap<Integer, Game> getHmap() {
		return hmap;
	}

	public void setHmap(HashMap<Integer, Game> hmap) {
		this.hmap = hmap;
	}
	
	@Autowired
	private GamesRepository dao;
	
	@Override
	public ResponseWrapper getWinnersTable() {
		Iterable<Winners> winnersTable;
		ResponseWrapper rw = new ResponseWrapper();
		try {
			winnersTable = dao.findTop10ByOrderByAttemptsAsc();
			rw.setResult(winnersTable);
		} catch(Exception e) {
			rw.setErrorMsg("DB connection error! ( Error" + e + " )");
		}
		return rw;
	}
	
	@Override 
	public Integer getNewGameId() {
		
		Integer uniqueId = this.gameId.getAndIncrement();		
		ArrayList<Integer> arl = this.generateSecretNumber();
		Game ng = new Game(uniqueId, arl, "Anonymous");

		this.hmap.put(uniqueId, ng);
		
		System.out.println("Log: getNewGameId: " + ng.toString());
		
		return uniqueId;
	}
	
	protected ArrayList<Integer> generateSecretNumber() {
		Random rand = new Random();
		ArrayList<Integer> arl = new ArrayList<Integer>(); 
		Integer rnd = 0;
		while(arl.size()<4) {
			rnd = rand.nextInt(9);
			if (!arl.contains(rnd))
				arl.add(rnd);
		}
		return arl;
	}
	
	//For Ameed only
	@Override
	public Game getGameDetails(Integer gameId) {
		return this.hmap.get(gameId);
	}

	@Override
	public Boolean setGamerName(Integer gameId, String name) {
		Game g = this.hmap.get(gameId);
		g.setUserName(name);
		try {
			this.hmap.put(gameId, g);
		} catch (Exception e) {
			return false;
		}
		System.out.println("Log: setGamerName: " + g.toString());
		return true;
	}

	@Override
	public Boolean clearWinTable(String password) {

		if(password.equals(this.adminPass)) {
			try {
				dao.deleteAll();
			} catch (Exception e) {
				return false;
			}
			System.out.println("Log: clearWinTable - cleared! ");
			return true;
		}
		System.out.println("Log: clearWinTable with password: " + password + " - bad pass!");
		return false;
	}
	
	protected boolean uniqueCharacters(String str)  {
        
		//Check for unique characters in string array
        for (int i=0; i<str.length(); i++) {
            for (int j=i+1; j<str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    return false;
                }
            }
        }    
        return true;
    }

	protected boolean validateAttemptString(String attempt) {
		
		if (attempt.length() != 4) {
			return false;
		}		
		
		for(int i = 0; i < attempt.length(); i++) {
			if (!Character.isDigit(attempt.charAt(i))) {
				return false;
			}
		} 
		
		if(!uniqueCharacters(attempt)) {
			return false;
		}
		return true;
	}
	
	@Override
	public ResponseWrapper checkAttempt(Integer gameId, String attempt) {
		//Get game object
		Game go = this.hmap.get(gameId);
		ResponseWrapper rw = new ResponseWrapper();
		
		System.out.println("Log: checkAttempt: Attempt = [" + attempt + "], game object: " + go.toString());
				
		if(!go.getIsEnded()) {
			
			if(!validateAttemptString(attempt)) {
				Game g = new Game(go);
				rw.setErrorMsg("Error, please enter 4-different-digits number!");
				rw.setResult(g);
				return rw;
			}
			
			ArrayList<Integer> result = go.checkAttemp(attempt); 
			ArrayList<Integer> winnerResult =  new ArrayList<Integer>(){{add(1);add(1);add(1);add(1);}};
			
			if(result.equals(winnerResult)) {
				//check num rows in db, if = maxWinners, delete the bigger attempts user and that add new
				int count = (int) dao.count();
				if(count == this.maxWinners) {
					dao.delete(dao.findFirstByOrderByAttemptsDesc());
				}
				// Add new row to DB
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date dateobj = new Date();
				System.out.println(df.format(dateobj));
				String jsonGameLog = new Gson().toJson(go.getGameLog());
				Winners w = new Winners(go.getUserName(), go.getAttempsCounter(), df.format(dateobj).toString(), jsonGameLog);
				
				go.setIsWinner(true);
				go.setIsEnded(true);
				
				try {
					dao.save(w);
				} catch(Exception e) {
					Game g = new Game(go);
					rw.setErrorMsg("DB connection error! ( Error" + e + " )");
					rw.setResult(g);
					return rw;
				}
			}
			
			if(go.getAttempsCounter() == this.maxAttemps || go.getIsWinner()) {
				go.setIsEnded(true);
			}
			Game g = new Game(go);
			
			int attemptsLeft = this.maxAttemps - g.getAttempsCounter();
			if(attemptsLeft < 4 && attemptsLeft!=0) {
				rw.setUserMsg("Attention! You have only " + attemptsLeft + " attempts left!");
			}
			rw.setResult(g);
			return rw;
		}
		
		Game g = new Game(go);
		rw.setErrorMsg("Error! Game ended!");
		rw.setResult(null);
		return rw;
	}


}
