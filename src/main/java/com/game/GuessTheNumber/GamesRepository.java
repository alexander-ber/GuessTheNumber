package com.game.GuessTheNumber;

import org.springframework.data.repository.CrudRepository;


public interface GamesRepository  extends CrudRepository<Winners, Integer>{
	
	Iterable<Winners> findAllOrderByAttempts(boolean desc);
	Iterable<Winners> findTop10ByOrderByAttemptsAsc();
	Winners findFirstByOrderByAttemptsDesc();
	//Iterable<Winners> findAll();
}
