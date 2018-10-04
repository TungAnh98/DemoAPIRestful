package com.vietis.learnrestful.model;

import java.util.ArrayList;

public class Question {
	private  int qId;
	private	String questionContent;
	private ArrayList<Answer> answers;
	public Question(int qId, String questionContent, ArrayList<Answer> answers) {
		super();
		this.qId = qId;
		this.questionContent = questionContent;
		this.answers = answers;
	}
	public Question() {
		super();
	}
	public Question(int qId, String questionContent) {
		super();
		this.qId = qId;
		this.questionContent = questionContent;
	}
	public int getqId() {
		return qId;
	}
	public void setqId(int qId) {
		this.qId = qId;
	}
	public String getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	
	
}
