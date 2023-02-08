package com.QuestionPro.newsApi.Dao;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.QuestionPro.newsApi.model.Comment;
import com.QuestionPro.newsApi.model.Story;

// This class is used to get stories & comments from hacker news API. 
@Component
public class APICallDaoImplementation implements APICallDao {
	
	private final static String GET_TOP_STORIES_URL="https://hacker-news.firebaseio.com/v0/topstories.json";
	private final static String GET_ITEM_URL="https://hacker-news.firebaseio.com/v0/item/";
	private final static String EXTENSION=".json";
	
	RestTemplate restTemplate=new RestTemplate();

	// This will return top 500 item including jobs & stories.
	@Override
	public List<Integer> getTopStories() {
		Integer[] result=this.restTemplate.getForObject(GET_TOP_STORIES_URL, Integer[].class);
		List<Integer> val= Arrays.asList(result);
		return val;
	}

	// This will return story of given Id.
	@Async
	@Override
	public CompletableFuture<Story> getStory(int id) {
		Story story = this.restTemplate.getForObject(GET_ITEM_URL+id+EXTENSION, Story.class);
		return CompletableFuture.completedFuture(story);
	}

	// This will return comment of given Id.
	@Async
	@Override
	public CompletableFuture<Comment> getComment(int id) {
		Comment comment = this.restTemplate.getForObject(GET_ITEM_URL+id+EXTENSION, Comment.class);
		return CompletableFuture.completedFuture(comment);
	}

}
