package com.vietis.learnrestful.model;

public class Player {
	private String username;
	private int score;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Player(String username, int score) {
		super();
		this.username = username;
		this.score = score;
	}
	public Player() {
		super();
	}
	
	
}
