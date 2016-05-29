package com.jiaqi.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.jiaqi.db.DB;
import com.jiaqi.model.User;

public class UserDAO implements IUserDAO{
	
	
	private HashMap<String,String> types=new HashMap<String,String>();
	/** 初始化上传文件的临时存放目录 */  
	private File tmpDir = null;  
	/** 初始化上传文件后的保存目录 */  
	private File saveDir = null;  
	
	
	private Connection conn=null;
	public UserDAO(){
		conn=DB.getConn();
		
		types.put("image/jpeg", ".jpg");
		types.put("image/gif", ".gif");
		types.put("image/x-ms-bmp", ".bmp");
		types.put("image/png", ".png");
		
		tmpDir=new File(File.separator+"tmpDir");
		saveDir=new File(File.separator+"upload");
		if(tmpDir.isDirectory()){
			tmpDir.mkdir();
			
		}
		if(saveDir.isDirectory()){
			saveDir.mkdir();
			
		}
		
	}
	
	public Blob readPic(int id) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select pic from user where userid=?";
		Blob b=null;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			rs=pstmt.executeQuery();
			if(rs.next()){
				//InputStream is=rs.getBinaryStream("pic");//oracle
				b=rs.getBlob("pic");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null){

					rs.close();


				}
				if(pstmt!=null){
					pstmt.close();

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}
	
	public User login(User user){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		User u=null;
		
		try {
			
			String sql="select * from user where username=? and password=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,user.getUsername() );
			pstmt.setString(2, user.getPassword());
			
			
			rs=pstmt.executeQuery();
			if(rs.next()){
				
				u=new User();
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setUserid((rs.getInt("userid")));
				u.setEmail(rs.getString("email"));
				u.setRealname(rs.getString("realname"));
				u.setSex(rs.getString("sex"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
				try {
					if(rs!=null){
						rs.close();
					}
					if(pstmt!=null){
						pstmt.close();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		}
		
		return u;
	}
	
	public User uploadPic(HttpServletRequest request) {
		// TODO Auto-generated method stub
		//得到Tomcat中项目的路径
		String tpath=request.getServletContext().getRealPath("/");
		//判断是否是上传的二进制数据
		User user=new User();
		if(ServletFileUpload.isMultipartContent(request)){
			
			//设置临时上传目录，和缓冲区大小
			
			DiskFileItemFactory df=new DiskFileItemFactory();
			df.setRepository(tmpDir);//设置临时目录
			df.setSizeThreshold(1024*1024*10);//设置缓存大小
			
			//设置单个文件大小，和总体文件的大小
			
			ServletFileUpload upload=new ServletFileUpload(df);
			upload.setFileSizeMax(1024*1024*5);//单个文件
			upload.setSizeMax(1024*1024*50);
			
			FileItemIterator fii=null;
			try {
				fii = upload.getItemIterator(request);
				while(fii.hasNext()){
					FileItemStream fis=fii.next();
					String contentType=fis.getContentType();
					String fieldName=fis.getFieldName();
					InputStream is=fis.openStream();
					if(!fis.isFormField()&&fis.getName().length()>0){//如果不是表单域，而且已经选择了上传文件，
						
						if(!types.containsKey(contentType)){//是否符合我要上传的文件类型
							request.setAttribute("error", "上传头像类型错误！");
							break;
						
						}
						//真正的上传
						
						BufferedInputStream bis=new BufferedInputStream(is);
						String id=request.getSession().getId();//文件名字唯一
						String path=tpath+File.separator+saveDir.getName()+File.separator+id+types.get(contentType);
						
						
						
						BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(new File(path)));
						
						
						Streams.copy(bis, bos, true);
						user.setPath(java.net.URLDecoder.decode(path,"utf-8"));
			
					}else{//表单域
						switch (fieldName) {
						case "t_UserName":
							user.setUsername(Streams.asString(is, "utf-8"));
							break;
						case "t_RePass":
							user.setPassword(Streams.asString(is, "utf-8"));
							break;
						
						case "iptName":
							user.setUsername(Streams.asString(is, "utf-8"));
							break;
						case "t_Email":
							user.setUsername(Streams.asString(is, "utf-8"));
							break;
							
						default:
							break;
						}
						
						
						
					}
					
					
				}
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		return user;
	}
	public boolean register(User user) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		boolean flag=false;
		
		try {
			String sql="insert into user(username,password,sex,realname,email) values(?,?,?,?,?) ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, URLDecoder.decode(user.getUsername(),"UTF-8"));
			pstmt.setString(2, user.getPassword());
			
//			FileInputStream fis=new FileInputStream(user.getPath());
//			pstmt.setBinaryStream(3, fis, fis.available());
			
			pstmt.setString(3, URLDecoder.decode(user.getSex(),"UTF-8"));
			pstmt.setString(4, URLDecoder.decode(user.getRealname(),"UTF-8"));
			pstmt.setString(5, user.getEmail());
			
			flag=pstmt.executeUpdate()>0?true:false;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return flag;
		
	
	}

	public boolean plogin(User user) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		boolean flag=true;   //可以，没有该用户名
		try {
			
			String sql="select userid from user where username=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,URLDecoder.decode(user.getUsername(),"utf-8") );
			rs=pstmt.executeQuery();
			if(rs.next()){
				
				 flag=false; //不可以，有该用户名
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
				try {
					
					if(pstmt!=null){
						pstmt.close();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		}
		
		return flag;
	}

	public ArrayList<User> selectfen(int id) {
		// TODO Auto-generated method stub
		ArrayList<User> list=new ArrayList<User>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select userid from friends where friendid=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs=pstmt.executeQuery();
			while(rs.next()){
				
				User user=new User();
				user.setUserid(rs.getInt("userid"));
				String username=fname(rs.getInt("userid"));
				user.setUsername(username);
				
				list.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return list;
	}
	
	public ArrayList<User> selectfri(int id) {
		// TODO Auto-generated method stub
		ArrayList<User> list=new ArrayList<User>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select friendid from friends where userid=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs=pstmt.executeQuery();
			while(rs.next()){
				
				User user=new User();
				user.setUserid(rs.getInt("friendid"));
				String username=fname(rs.getInt("friendid"));
				user.setUsername(username);
				
				list.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return list;
	}
	
	
	public String fname(int userid) {
		// TODO Auto-generated method stub
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select username from user where userid=?";
		String username=null;
		try {
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setInt(1, userid);
			 rs=pstmt.executeQuery();
			 while(rs.next()){
				 username=rs.getString("username");
			 }
			 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return username;
	}

	public boolean addfri(User my, int fri) {
		// TODO Auto-generated method stub
		boolean flag=false;
		PreparedStatement pstmt=null; 
		String sql="insert into friends(userid,friendid) values(?,?)";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, my.getUserid());
			pstmt.setInt(2, fri);
			flag=pstmt.executeUpdate()>0?true:false;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return flag;
	}

	public boolean update(User user,int userid) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		boolean flag=false;
		
		try {
			String sql="update user set username=?,password=?,sex=?,realname=?,email=? where userid=? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, URLDecoder.decode(user.getUsername(),"UTF-8"));
			pstmt.setString(2, user.getPassword());
			
//			FileInputStream fis=new FileInputStream(user.getPath());
//			pstmt.setBinaryStream(3, fis, fis.available());
			
			pstmt.setString(3, URLDecoder.decode(user.getSex(),"UTF-8"));
			pstmt.setString(4, URLDecoder.decode(user.getRealname(),"UTF-8"));
			pstmt.setString(5, user.getEmail());
			pstmt.setInt(6, userid);
			
			flag=pstmt.executeUpdate()>0?true:false;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return flag;
		
	
	}

}
