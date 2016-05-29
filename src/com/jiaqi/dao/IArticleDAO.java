package com.jiaqi.dao;

import java.util.ArrayList;

import com.jiaqi.model.User;
import com.jiaqi.model.article;

public interface IArticleDAO {
	
	public ArrayList<article> getallart(User user);
	public boolean addariticle(User user, String textarea);
	public ArrayList<article> getart();
	public User fuser(int userid);
}
