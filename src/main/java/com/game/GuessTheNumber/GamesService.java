package com.game.GuessTheNumber;

public interface GamesService {
	Iterable<Winners> getWinnersTable();
	Integer getNewGameId();
	String updateCounter(Integer gameId);
	Game getGameDetails(Integer gameId);
	Boolean setGamerName(Integer gameId, String name);
	Object checkAttemp(Integer gameId, String attemp);
	Integer addWinner(Integer w);
	
}
