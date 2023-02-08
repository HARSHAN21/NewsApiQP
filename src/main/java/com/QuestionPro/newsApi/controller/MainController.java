package com.QuestionPro.newsApi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.QuestionPro.newsApi.model.Comment;
import com.QuestionPro.newsApi.model.Story;
import com.QuestionPro.newsApi.service.APICallService;

@RestController
public class MainController {
	
	@Autowired
	APICallService apiCallService;
	
	// This end point will return top 10 storis ranked by score in the last 15 minutes.
	@GetMapping("/top-stories")
	public ResponseEntity<List<Story>> getTopStories() throws Exception{
		List<Story> stories = apiCallService.getTopStories();
		if(stories.size() == 0) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<List<Story>>(stories,HttpStatus.OK);
		}
	}
	
	// This end point will return all stories that were served previously from 1 st end point (/top-stories).
	@GetMapping("/past-stories")
	public ResponseEntity<List<Story>> getPastStories(){
		List<Story> stories = apiCallService.getPastStories();
		if(stories.size() == 0) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<List<Story>>(stories,HttpStatus.OK);
		}
	}
	
	// This end point will return 10 comment (max) on a given story sorted by a total number of child comments. 
	@GetMapping("/comments/{storyId}")
	public ResponseEntity<List<Comment>> getComments(@PathVariable("storyId") int storyId) throws Exception{
		List<Comment> comments =  apiCallService.getComment(storyId);
		if(comments.size() == 0) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<List<Comment>>(comments,HttpStatus.OK);
		}
	}

}
