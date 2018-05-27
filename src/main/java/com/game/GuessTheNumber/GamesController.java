package com.game.GuessTheNumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/games/")
public class GamesController {
	
	@Autowired
	private GamesService services;

	@GetMapping("get-winners-table")
	public Iterable<Winners> getWinnersTable() {
		return services.getWinnersTable();
	}
	
	@GetMapping("get-new-game-id")
	public Integer newGameID() {
		return services.getNewGameId();
	}
	
	@GetMapping(value = "update-game-counter/{gameId}",  produces = "application/json")
	public String updateGameCounter(@PathVariable("gameId") Integer gameId) {
		return services.updateCounter(gameId);
	}
	
	@GetMapping(value = "get-game-details/{gameId}",  produces = "application/json") // <--- Add parameter to request
	public Object getGameDetails(@PathVariable("gameId") Integer gameId) {
		//return services.getGameDetails(gameId).toString();
		return services.getGameDetails(gameId);
	}	
	
	@GetMapping("set-gamer-name/{gameId}/{name}") // <--- Add parameter to request
	public Boolean setGamerName(@PathVariable("gameId") Integer gameId, @PathVariable("name") String name) {
		return services.setGamerName(gameId, name);
	}	

	@GetMapping(value = "check-attemp/{gameId}/{attemp}", produces = "application/json") // <--- Add parameter to request
	public Object checkAttemp(@PathVariable("gameId") Integer gameId, @PathVariable("attemp") String attemp) {
		return services.checkAttemp(gameId, attemp);
	}
	
}
