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
	public Iterable<Winners> getWinnersTable() {
		return dao.findTop10ByOrderByAttemptsAsc();
	}
	
	@Override 
	public Integer getNewGameId() {
		
		Integer uniqueId = this.gameId.getAndIncrement();		
		ArrayList<Integer> arl = this.generateSecretNumber();
		
		this.hmap.put(uniqueId, new Game(uniqueId, arl, "Anonymous"));
		
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
	
	@Override
	public Game getGameDetails(Integer gameId) {
		return this.hmap.get(gameId);
	}

	protected boolean updateCounter(Integer gameId) {		
		Game g = this.hmap.get(gameId);
		Integer attempsCounter = g.getAttempsCounter();
		attempsCounter++;
		
		if(attempsCounter == this.maxAttemps) {
			g.setIsEnded(true);
			return false;
		}
		
		g.setAttempsCounter(attempsCounter);
		this.hmap.put(gameId, g);
		return true;
	}

	@Override
	public Boolean setGamerName(Integer gameId, String name) {
		Game g = this.hmap.get(gameId);
		g.setUserName(name);
		this.hmap.put(gameId, g);
		
		return true;
	}

	@Override
	public Game checkAttempt(Integer gameId, String attempt) {
		Game go = this.hmap.get(gameId);
		
		
		if(!go.getIsEnded()) {
			if (attempt.length() != 4) {
				Game g = new Game(go);
				return g;
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
				Winners w = new Winners(go.getUserName(), go.getAttempsCounter(), df.format(dateobj).toString());
				dao.save(w);
				go.setIsWinner(true);
				go.setIsEnded(true);
			}
			
			if(go.getAttempsCounter() == this.maxAttemps) {
				go.setIsEnded(true);
			}
			Game g = new Game(go);
			return g;
		}
		Game g = new Game(go);
		return g;
	}

	@Override
	public Boolean clearWinTable(String password) {
		if(password.equals(this.adminPass)) {
			dao.deleteAll();
			return true;
		}
		return false;
	}
	
	protected  boolean uniqueCharacters(String str)  {
        // If at any time we encounter 2 same
        // characters, return false
        for (int i=0; i<str.length(); i++)
            for (int j=i+1; j<str.length(); j++)
                if (str.charAt(i) == str.charAt(j))
                    return false;
 
        // If no duplicate characters encountered,
        // return true
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
	public ResponseWrapper checkAttempt2(Integer gameId, String attempt) {
		Game go = this.hmap.get(gameId);
		ResponseWrapper rw = new ResponseWrapper();
	
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
				Winners w = new Winners(go.getUserName(), go.getAttempsCounter(), df.format(dateobj).toString());
				dao.save(w);
				go.setIsWinner(true);
				go.setIsEnded(true);
			}
			
			if(go.getAttempsCounter() == this.maxAttemps) {
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
