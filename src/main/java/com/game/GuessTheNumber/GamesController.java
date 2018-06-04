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
	public Iterable<Winners> getWinnersTable() {
		return services.getWinnersTable();
	}
	
	@GetMapping("get-new-game-id")
	public Integer newGameID() {
		return services.getNewGameId();
	}
	
	@GetMapping(value = "get-game-details2/{gameId}",  produces = "application/json") // <--- Add parameter to request
	public Game getGameDetails2(@PathVariable("gameId") Integer gameId) {
		return services.getGameDetails(gameId);
		//return services.getGameDetails(gameId);
	}	
	
	@GetMapping(value = "get-game-details/{gameId}",  produces = "application/json") // <--- Add parameter to request
	public Object getGameDetails(@PathVariable("gameId") Integer gameId) {
		return services.getGameDetails(gameId).toString();
		//return services.getGameDetails(gameId);
	}	
	
	@PostMapping("set-gamer-name/{gameId}/{name}") // <--- Add parameter to request
	public Boolean setGamerName(@PathVariable("gameId") Integer gameId, @PathVariable("name") String name) {
		return services.setGamerName(gameId, name);
	}	

	@GetMapping(value = "check-attempt/{gameId}/{attempt}", produces = "application/json") // <--- Add parameter to request
	public Game checkAttemp(@PathVariable("gameId") Integer gameId, @PathVariable("attempt") String attempt) {
		return services.checkAttempt(gameId, attempt);
	}
	
	@PostMapping(value = "clear-win-table/{password}",  produces = "application/json") // <--- Add parameter to request
	public Boolean clearWinTable(@PathVariable("password") String password) {
		return services.clearWinTable(password);
	}	

	
	@GetMapping(value = "check-attempt2/{gameId}/{attempt}", produces = "application/json") // <--- Add parameter to request
	public ResponseWrapper checkAttemp2(@PathVariable("gameId") Integer gameId, @PathVariable("attempt") String attempt) {
		return new ResponseWrapper(services.checkAttempt2(gameId, attempt));
	}
	
//	public ResponseWrapper getAll() {
//		return new ResponseWrapper(services.fetchAllGames());
//	}
//	
}
