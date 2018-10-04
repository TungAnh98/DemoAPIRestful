package com.vietis.learnrestful.model;

public class Game implements Cloneable {
	private int id;
	private int pin;
	public Game(int id, int pin) {
		super();
		this.id = id;
		this.pin = pin;
	}
	public Game() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}

	 @Override
	 public Object clone() throws CloneNotSupportedException {
	 return super.clone();
	 }
	
}
