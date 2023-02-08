package com.QuestionPro.newsApi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

//This is model (POJO) class of Story
public class Story implements Comparable<Story>{
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private int id;
	
	private String title;
	private String url;
	private int score;
	private int time;
	private String by;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String type;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<Integer> kids;
	
	public Story(int id, String title, String url, int score, int time, String by, List<Integer> kids, String type) {
		this.id = id;
		this.title = title;
		this.url = url;
		this.score = score;
		this.time = time;
		this.by = by;
		this.kids = kids;
		this.type=type;
	}

	public Story() {
		
	}
	
	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Integer> getKids() {
		return kids;
	}

	public void setKids(List<Integer> kids) {
		this.kids = kids;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public int compareTo(Story o) {
		return o.getScore() - this.getScore();
	}

	@Override
	public String toString() {
		return "Story [id=" + id + ", title=" + title + ", url=" + url + ", score=" + score + ", time=" + time + ", by="
				+ by + ", type=" + type + ", kids=" + kids + "]";
	}

}
