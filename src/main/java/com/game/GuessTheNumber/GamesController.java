package com.game.GuessTheNumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/games/")
public class GamesController {
	
	@Autowired
	private GamesService services;

	@GetMapping("get-winners-table")
	public ResponseWrapper getWinnersTable() {
		return services.getWinnersTable();
	}
	
	@GetMapping("get-new-game-id")
	public Integer newGameID() {
		return services.getNewGameId();
	}
	
	@GetMapping(value = "get-game-details/{gameId}",  produces = "application/json")
	public Game getGameDetails2(@PathVariable("gameId") Integer gameId) {
		return services.getGameDetails(gameId);
	}	
	
	@PostMapping("set-gamer-name/{gameId}/{name}")
	public Boolean setGamerName(@PathVariable("gameId") Integer gameId, @PathVariable("name") String name) {
		return services.setGamerName(gameId, name);
	}	

	@PostMapping(value = "clear-win-table/{password}",  produces = "application/json")
	public Boolean clearWinTable(@PathVariable("password") String password) {
		return services.clearWinTable(password);
	}	

	@GetMapping(value = "check-attempt/{gameId}/{attempt}", produces = "application/json")
	public ResponseWrapper checkAttemp2(@PathVariable("gameId") Integer gameId, @PathVariable("attempt") String attempt) {
		return new ResponseWrapper(services.checkAttempt(gameId, attempt));
	}
	
}
