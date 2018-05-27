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
	private final Integer maxAttemps = 20;
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
		return dao.findAll();
	}
	
	@Override
	public Integer addWinner(Integer w) {
		//dao.save(w).getId();
		return 1;
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

	@Override
	public String updateCounter(Integer gameId) {		
		Game g = this.hmap.get(gameId);
		Integer attempsCounter = g.getAttempsCounter();
		attempsCounter++;
		if(attempsCounter>maxAttemps) {
			return "{'error': true; 'message': 'maximum attemps!'}";
		}
		g.setAttempsCounter(attempsCounter+1);
		this.hmap.put(gameId, g);
		return "{'error': false;}";
	}

	@Override
	public Boolean setGamerName(Integer gameId, String name) {
		Game g = this.hmap.get(gameId);
		g.setUserName(name);
		this.hmap.put(gameId, g);
		
		return true;
	}

	@Override
	public Object checkAttemp(Integer gameId, String attemp) {
		updateCounter(gameId);
		Game g = this.hmap.get(gameId);
		
		ArrayList<Integer> result = g.checkAttemp(attemp); 
		ArrayList<Integer> winnerResult =  new ArrayList<Integer>(){{add(1);add(1);add(1);add(1);}};
		if(result.equals(winnerResult)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dateobj = new Date();
			System.out.println(df.format(dateobj));
			Winners w = new Winners(g.getUserName(), g.getAttempsCounter(), df.format(dateobj).toString());
			dao.save(w);
		}
		return result;
	}

}
