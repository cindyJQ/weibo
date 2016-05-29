package com.jiaqi.control;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Blob;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jiaqi.dao.UserDAO;
import com.jiaqi.model.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(name="/UserServlet",urlPatterns={"/user"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static UserDAO dao=new UserDAO();
	

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		String action=request.getParameter("action");
		if(action.equals("register")){
			register(request,response);
		}else{
			if(action.equals("login")){
				login(request, response);
				
			}
			else if(action.equals("readpic")){
				radPic(request,response);
			}else if(action.equals("selectfen")){
				selectfen(request,response);
			}else if(action.equals("selectfri")){
				selectfri(request,response);
			}else if(action.equals("addfri")){
				addfri(request,response);
			}else if(action.equals("update")){
				update(request,response);
			}
		}
		
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String username=null;
		String sex=null;
		String realname=null;
		try {
			username = URLEncoder.encode(request.getParameter("t_UserName"), "utf-8");
			sex=URLEncoder.encode(request.getParameter("rb_Sex"), "utf-8");
			realname=URLEncoder.encode(request.getParameter("iptName"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String password=request.getParameter("t_RePass");
		 
		String email=request.getParameter("t_Email");
		
		
		
		User user=new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setRealname(realname);
		user.setSex(sex);
		
		if(user.getUsername()!=null){

			if(dao.plogin(user)){     //用户名没有被注册

				User u2=(User)request.getSession().getAttribute("user");//当前登陆用户的信息
				if(dao.update(user,u2.getUserid())){
					

					out.print("<script>alert('修改成功,请重新登陆！');window.location.href='loginout.jsp';</script>");
				}
				else{
					out.print("<script>alert('修改失败！');window.location.href='update.jsp';</script>");

				}

			}else{   //用户名被注册
				out.print("<script>alert('该用户名已被注册过！');window.location.href='update.jsp';</script>");
			}
		}
		
	}
		
		
	

	private void addfri(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    boolean flag1=false;//没有关注过好友
		User my=(User)request.getSession().getAttribute("user");
		if(my!=null){
			int fri=Integer.parseInt(request.getParameter("fri"));
			
			ArrayList<User> list=dao.selectfri(my.getUserid());
			for(User l:list){
				if(l.getUserid()==fri){
					flag1=true;
				}
			}
			if(flag1){
				
				out.println("<script>alert('已关注过！');window.location.href='index.jsp';</script>");
			}else{
				boolean flag=dao.addfri(my,fri);
				
				if(flag){
					
					out.println("<script>alert('关注成功！');window.location.href='mypage.jsp';</script>");
				}
			}
		}else{
			out.println("<script>alert('请先登录！');window.location.href='index.jsp';</script>");
		}
	}

	private void selectfri(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user=(User)request.getSession().getAttribute("user");
		ArrayList<User> list=new ArrayList<User>();
		list=dao.selectfri(user.getUserid());
		request.getSession().setAttribute("frilist",list);
		RequestDispatcher rd=request.getRequestDispatcher("/mypage.jsp");
		rd.forward(request,response);
	}

	private void selectfen(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		User user=(User)request.getSession().getAttribute("user");
		ArrayList<User> list=new ArrayList<User>();
		list=dao.selectfen(user.getUserid());
		request.getSession().setAttribute("fenlist",list);
		RequestDispatcher rd=request.getRequestDispatcher("/myfen.jsp");
		rd.forward(request,response);
		
	}

	private void radPic(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id=Integer.parseInt(request.getParameter("userid"));
		Blob b=dao.readPic(id);
		
		try {
			OutputStream sos=response.getOutputStream();
			
			byte[] buffer=b.getBytes(1, (int) b.length());
			response.setContentType("image/jpeg");
			sos.write(buffer);
			sos.flush();
			sos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

	private void login(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String check=request.getParameter("check");
		
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		
		User user=new User();
		user.setUsername(username);
		user.setPassword(password);
		
		User u=dao.login(user);
		if(u!=null){
			if(check!=null){
				Cookie cu=null;
				Cookie cp=null;
				try {
					cu=new Cookie("cu",URLEncoder.encode(username, "utf-8"));
					cp=new Cookie("cp",password);
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				cu.setMaxAge(3600*24*7);
				cp.setMaxAge(3600*4*7);
				response.addCookie(cu);
				response.addCookie(cp);
			}
			request.getSession().setAttribute("user", u);
			RequestDispatcher dispatcher=null;
			try {
				dispatcher=request.getRequestDispatcher("mypage.jsp");
				dispatcher.forward(request, response);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else{
			PrintWriter out=null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print("<script>alert('登陆失败！请检查用户名和密码是否正确！');window.location.href='login.jsp';</script>");
				
		}
		
		
	}

	private void register(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//User user=dao.uploadPic(request);
		String username=null;
		String sex=null;
		String realname=null;
		try {
			username = URLEncoder.encode(request.getParameter("t_UserName"), "utf-8");
			sex=URLEncoder.encode(request.getParameter("rb_Sex"), "utf-8");
			realname=URLEncoder.encode(request.getParameter("iptName"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String password=request.getParameter("t_RePass");
		 
		String email=request.getParameter("t_Email");
		
		
		
		User user=new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setRealname(realname);
		user.setSex(sex);
		
		if(user.getUsername()!=null){

			if(dao.plogin(user)){     //用户名没有被注册


				if(dao.register(user)){
					

					out.print("<script>alert('注册成功！');window.location.href='index.jsp';</script>");
				}
				else{
					out.print("<script>alert('注册失败！');window.location.href='register.jsp';</script>");

				}

			}else{   //用户名被注册
				out.print("<script>alert('该用户名已被注册！');window.location.href='register.jsp';</script>");
			}
		}
		
	}

}
