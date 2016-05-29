package com.jiaqi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jiaqi.db.DB;
import com.jiaqi.model.User;
import com.jiaqi.model.article;

public class ArticleDAO implements IArticleDAO{
	
	
	private Connection conn=null;
	
	public ArticleDAO(){
		conn=DB.getConn();
	}


	public ArrayList<article> getallart(User user) {//得到自己的所有微博
		// TODO Auto-generated method stub
		
		int userid=user.getUserid();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<article> list=new ArrayList<article>();
		String sql="select * from article where userid=? order by datetime desc";
		
		try {
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setInt(1, userid);
			 rs=pstmt.executeQuery();
			 while(rs.next()){
				 article art=new article();
				 
				 User user1=fuser(user.getUserid());
				 
				 art.setUser(user1);
				 
				 art.setId(rs.getInt("id"));
				 art.setContent(rs.getString("content"));
				 art.setDatetime(rs.getString("datetime"));
				 art.setRootid(rs.getInt("rootid"));
				 list.add(art);
			 }
			 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		
		return list;
	}

	public boolean addariticle(User user, String textarea) {
		// TODO Auto-generated method stub
		
		boolean flag=false;
		PreparedStatement pstmt=null;
		
		String sql="insert into article(userid,content,datetime,rootid) values(?,?,now(),0)  ";
		
		try {
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setInt(1, user.getUserid());
			 pstmt.setString(2, textarea);
			 flag=pstmt.executeUpdate()>0?true:false;
			 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return flag;
	}


	public ArrayList<article> getart() {
		// TODO Auto-generated method stub
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<article> list=new ArrayList<article>();
		String sql="select * from article order by datetime desc";
		
		try {
			 pstmt=conn.prepareStatement(sql);
			
			 rs=pstmt.executeQuery();
			 while(rs.next()){
				 
				 article art=new article();
				 art.setId(rs.getInt("id"));
				
				 User user=fuser(rs.getInt("userid"));
				 
				 art.setUser(user);
				 art.setContent(rs.getString("content"));
				 art.setDatetime(rs.getString("datetime"));
				 art.setRootid(rs.getInt("rootid"));
				 list.add(art);
			 }
			 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		
		return list;
	
	}


	public User fuser(int userid) {
		// TODO Auto-generated method stub
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		User user=new User();
		String sql="select * from user where userid=?";
		String username=null;
		String realname=null;
		String email=null;
		String sex=null;
		try {
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setInt(1, userid);
			 rs=pstmt.executeQuery();
			 if(rs.next()){
				 username=rs.getString("username");
				 realname=rs.getString("realname");
				 email=rs.getString("email");
				 sex=rs.getString("sex");
			 }
			 user.setUserid(userid);
			 user.setUsername(username);
			 user.setRealname(realname);
			 user.setEmail(email);
			 user.setSex(sex);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return user;
	}


	public boolean zhuan(User user, int aid) {
		// TODO Auto-generated method stub
		boolean flag=false;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int id=user.getUserid();
		int rootid=aid;
		String content=getOneArt(aid);
		String sql="insert into article(rootid,userid,content,datetime) values (?,?,?,now())";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, rootid);
			pstmt.setInt(2, id);
			pstmt.setString(3, content);
			flag=pstmt.executeUpdate()>0?true:false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}


	private String getOneArt(int aid) {
		// TODO Auto-generated method stub
		int id=aid;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String content=null;
		String sql="select * from article where id=?";
		
		try {
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setInt(1, id);
			 rs=pstmt.executeQuery();
			 while(rs.next()){
				 
				content=rs.getString(content);
			 }
			 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
		return content;
	}


}
