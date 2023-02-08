package com.QuestionPro.newsApi.Dao;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.QuestionPro.newsApi.model.Comment;
import com.QuestionPro.newsApi.model.Story;

public interface APICallDao {
	
	List<Integer> getTopStories();
	
	CompletableFuture<Story> getStory(int id);
	
	CompletableFuture<Comment> getComment(int id);
}
