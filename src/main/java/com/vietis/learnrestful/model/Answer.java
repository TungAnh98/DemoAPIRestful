package com.vietis.learnrestful.model;

public class Answer {
	private int id;
	private String content;
	private Question question;
	public Answer(int id, String content, Question question) {
		super();
		this.id = id;
		this.content = content;
		this.question = question;
	}
	public Answer(int id, String content) {
		super();
		this.id = id;
		this.content = content;
	}
	public Answer() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	
}
