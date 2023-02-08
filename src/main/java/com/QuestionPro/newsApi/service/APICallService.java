package com.QuestionPro.newsApi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QuestionPro.newsApi.Dao.APICallDao;
import com.QuestionPro.newsApi.Dao.DBDao;
import com.QuestionPro.newsApi.model.Comment;
import com.QuestionPro.newsApi.model.Story;

// This is service class, used to provide all services.
@Service
public class APICallService {
	
	private Date timeStamp;
	
	private final static int NO_OF_STORIERS=10;
	private final static int CACHE_TIMER_DURATION=15;
	private final static int MAX_NO_OF_COMMENTS=10;
	
	@Autowired
	APICallDao apiCallDao;
	
	@Autowired
	DBDao dbDao;
	
	private List<Integer> topStoriesId;
	
	private TreeSet<Story> topAllStories;
	private List<Story> currentTop10Stories;
	
	private Set<Integer> previousAllTopStoriesIds;
	
	APICallService(){
		
		this.topStoriesId = new ArrayList<Integer>();
		
		this.topAllStories =  new TreeSet<Story>();
		this.currentTop10Stories = new ArrayList<Story>();
		
		this.previousAllTopStoriesIds = new HashSet<Integer>();
		
		this.timeStamp=null;
	}
	
	// This service method will return top 10 stories
	public List<Story> getTopStories()throws Exception  {
		
		if(this.timeStamp == null) {
			this.timeStamp = new Date();
			this.dbDao.deleteAllStories();
			this.updateStoryList();
		}
		
		Date currentTime = new Date();
		Long duration= currentTime.getTime() - this.timeStamp.getTime();
		duration = (duration / (1000 * 60)) % 60;
				
		if(duration >= CACHE_TIMER_DURATION) {
			this.updateStoryList();
		}
		
		return this.currentTop10Stories;
	}
	
	// This service method will return all stories that were served previously
	public List<Story> getPastStories(){
		
		return this.dbDao.getStories().stream().sorted().collect(Collectors.toList());
	}
	
	public List<Comment> getComment(int storyId) throws Exception {
		
		Story targetStory=new Story();
		List<Story> filteredList = new ArrayList<Story>();
		
		filteredList = this.topAllStories.stream().filter(story -> story.getId() == storyId).collect(Collectors.toList());
		
		if(filteredList.size() == 0) {
			Story story = this.apiCallDao.getStory(storyId).get();
			filteredList.add(story);
		}
		
		targetStory = filteredList.get(0);
		
		System.out.println("Calling every comment...");
		List<CompletableFuture<Comment>> intermediateComments = new ArrayList<>();
		for(Integer id:targetStory.getKids()) {
			intermediateComments.add(apiCallDao.getComment(id));
		}
		
		CompletableFuture.allOf(intermediateComments.toArray(new CompletableFuture[0])).join();
		System.out.println("completed.");
		
		List<Comment> comments = new ArrayList<Comment>();
		
		for (CompletableFuture<Comment> comment : intermediateComments) {
			comments.add(comment.get());
		}
		
		Collections.sort(comments);
		comments = comments.stream().limit(MAX_NO_OF_COMMENTS).collect(Collectors.toList());
		
		return comments;
	}
	
	// This method is used to update top 10 stories after CACHE time (15 minutes)
	private void updateStoryList() throws Exception {
		this.timeStamp = new Date();
		
		List<Story> newStory = this.currentTop10Stories.stream().filter(story -> !this.previousAllTopStoriesIds.contains(story.getId())).collect(Collectors.toList());
		this.previousAllTopStoriesIds.addAll(newStory.stream().map(story->story.getId()).collect(Collectors.toList()));
		
		if(newStory.size()!=0) {
			int rows = this.dbDao.addStories(newStory);
			System.out.println("rows "+rows);
		}
		
		this.currentTop10Stories.clear();
		this.topAllStories.clear();
		
		this.topStoriesId = apiCallDao.getTopStories();
		
		System.out.println("Calling every story...");
		List<CompletableFuture<Story>> allStory = new ArrayList<>();
		for(Integer id:this.topStoriesId) {
			allStory.add(apiCallDao.getStory(id));
		}
		
		CompletableFuture.allOf(allStory.toArray(new CompletableFuture[0])).join();
		System.out.println("completed.");
		
		for(int i=0;i<allStory.size();i++) {
			if(allStory.get(i).get().getType().equalsIgnoreCase("story")) {
				this.topAllStories.add(allStory.get(i).get());
			}
		}
		System.out.println("Total number of story are : "+topAllStories.size());

		this.currentTop10Stories = topAllStories.stream().limit(NO_OF_STORIERS).collect(Collectors.toList());
		System.out.println("Top 10 stories ID are : "+this.currentTop10Stories.stream().map(story->story.getId()).collect(Collectors.toList()));
	}

}
