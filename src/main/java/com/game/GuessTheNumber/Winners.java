package com.game.GuessTheNumber;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "winners")
public class Winners {
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	private Integer attempts;
	
	public Integer getAttempts() {
		return attempts;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

	@Column(name = "date")
	private String gameDate;

	public Winners() {
		super();
	}

	public Winners(String name, Integer attempts, String gameDate) {
		super();
		this.name = name;
		this.gameDate = gameDate;
		this.attempts = attempts;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGameDate() {
		return gameDate;
	}

	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}

}
