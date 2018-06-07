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
	@Column(name = "date")
	private String gameDate;
	private String log;
	

	public Integer getAttempts() {
		return attempts;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

	public Winners() {
		super();
	}

	public Winners(String name, Integer attempts, String gameDate, String log) {
		super();
		this.name = name;
		this.gameDate = gameDate;
		this.attempts = attempts;
		this.log = log;
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
