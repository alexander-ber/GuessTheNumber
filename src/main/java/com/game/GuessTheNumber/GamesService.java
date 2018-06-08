package com.game.GuessTheNumber;

public interface GamesService {
	
	Integer getNewGameId();
	Game getGameDetails(Integer gameId); //For Ameed use only
	Boolean setGamerName(Integer gameId, String name);
	
	ResponseWrapper checkAttempt(Integer gameId, String attemp);
	
	ResponseWrapper getWinnersTable();
	Boolean clearWinTable(String password);
}
