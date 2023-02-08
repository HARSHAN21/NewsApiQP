package com.QuestionPro.newsApi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

// This is model (POJO) class of comment
public class Comment implements Comparable<Comment> {
	
	private String by;
	private String text;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private int id;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<Integer> kids= new ArrayList<Integer>();
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private int childNumber=0;
	
	public Comment() {
	}

	public Comment(String by, String text, int id, List<Integer> kids) {
		this.by = by;
		this.text = text;
		this.id = id;
		this.kids = kids;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getKids() {
		return kids;
	}

	public void setKids(List<Integer> kids) {
		this.kids = kids;
	}
	
	public int getChildNumber() {
		return getKids().size();
	}

	@Override
	public int compareTo(Comment o) {
		return o.getChildNumber() - this.getChildNumber();
	}

	@Override
	public String toString() {
		return "Comment [by=" + by + ", text=" + text + ", id=" + id + ", kids=" + kids + "]";
	}

}
