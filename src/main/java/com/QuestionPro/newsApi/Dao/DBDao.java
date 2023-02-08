package com.QuestionPro.newsApi.Dao;
import java.util.List;

import com.QuestionPro.newsApi.model.Story;

public interface DBDao {
	
	List<Story> getStories();
	
	int addStories(List<Story> stories);
	
	int deleteAllStories();

}
