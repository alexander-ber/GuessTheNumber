package com.game.GuessTheNumber;

public interface GamesService {
	Iterable<Winners> getWinnersTable();
	Integer getNewGameId();
	Game getGameDetails(Integer gameId);
	Boolean setGamerName(Integer gameId, String name);
	Game checkAttempt(Integer gameId, String attemp);
	Boolean clearWinTable(String password);
	
	ResponseWrapper checkAttempt2(Integer gameId, String attemp);
}
