package com.QuestionPro.newsApi.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.QuestionPro.newsApi.model.Story;

//This class is used to get stories stored in database. 
@Component
public class DBDaoImplementatiom implements DBDao {
	
	private static String tableName="story";
	private static String getAllStoriesQuery="select * from "+tableName;
	private static String addBlogQuery="insert into "+tableName+" ( id, title, url, score, time, username) values(?,?,?,?,?,?)";
	private static String deleteBlogQuery="delete from "+tableName;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}
	
	private RowMapperImplementation getRowMapper() {
		return new RowMapperImplementation();
	}
	
	// This will return all the stories stored in DataBase.
	@Override
	public List<Story> getStories() {
		List<Story> list = this.getJdbcTemplate().query(getAllStoriesQuery, this.getRowMapper());
		return list;
	}

	// This will stored all the stories in DataBase.
	@Override
	public int addStories(List<Story> stories) {
		
		List<Object[]> batchListData = new ArrayList<Object[]>();
		
		for(Story story : stories) {
			Object[] object= {story.getId(),story.getTitle(), story.getUrl(),story.getScore(), story.getTime(), story.getBy() };
			batchListData.add(object);
		}
		
		int[] batchUpdate = this.getJdbcTemplate().batchUpdate(addBlogQuery, batchListData);
		
		Arrays.asList(batchUpdate).stream().forEach(i-> System.out.println("value of i "+i));
		
		return 1;
	}

	// This will delete all the stories stored in DataBase.
	@Override
	public int deleteAllStories() {
		int row = this.getJdbcTemplate().update(deleteBlogQuery);
		return row;
	}

}

class RowMapperImplementation implements RowMapper<Story> {

	@Override
	public Story mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Story story = new Story();
		story.setTitle(rs.getString(1));
		story.setUrl(rs.getString(2));
		story.setScore(rs.getInt(3));
		story.setTime(rs.getInt(4));
		story.setBy(rs.getString(5));
		story.setId(rs.getInt(6));
		
		return story;
	}
	
}
