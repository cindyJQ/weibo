package com.jiaqi.dao;

import java.sql.Blob;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.jiaqi.model.User;

public interface IUserDAO {
	
	public Blob readPic(int id);
	public User login(User user);
	public User uploadPic(HttpServletRequest request);
	public boolean register(User user);
	public boolean plogin(User user);
	public ArrayList<User> selectfen(int id);
	public ArrayList<User> selectfri(int id);
	public String fname(int userid);
	public boolean addfri(User my, int fri);
	public boolean update(User user,int userid);
}
