package com.jiaqi.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jiaqi.dao.ArticleDAO;
import com.jiaqi.model.User;
import com.jiaqi.model.article;


@WebServlet(name="/ArticleServlet",urlPatterns={"/article"})
public class ArticleServlet extends HttpServlet {
	ArticleDAO art=new ArticleDAO();
	private static final long serialVersionUID = 922978702076462486L;

	public ArticleServlet() {
		super();
	}
	

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String action = (String) request.getParameter("action");
		if (action == null) {
		out.println("出错了！");
		} else if (action.equals("addart")) {
			try {
				addart(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if (action.equals("getallart")) {
			try {
				getallart(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if (action.equals("getfrendart")) {
			try {
				getfrendart(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if (action.equals("getart")) {
			try {
				getart(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(action.equals("zhuan")){
			zhuan(request, response);
		}
		
		
	}
private void zhuan(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		int aid=Integer.parseInt(request.getParameter("id"));
		User user=(User) request.getSession().getAttribute("user"); 
		
		if(user!=null){
			boolean flag=art.zhuan(user,aid);
			if(flag){
				 out.print("<script>alert('转发成功！');window.location.href='mypage.jsp';</script>");
			}else{
				out.print("<script>alert('转发失败！');window.location.href='index.jsp';</script>");
			}
		}else{
			out.print("<script>alert('请先登录！');window.location.href='index.jsp';</script>");
		}
	}



private void getart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/html; charset=UTF-8");
	PrintWriter out = response.getWriter();
	
	
	
	ArrayList<article> artlist=art.getart();
	request.setAttribute("artlist",artlist);
	RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");
	rd.forward(request,response);
	
	
	}



/**
 * 发布微博	
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException
 * @throws SQLException
 */
	private void addart(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException, SQLException  {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String textarea = request.getParameter("textarea");
		User user=(User) request.getSession(true).getAttribute("user"); 
		
	      boolean b=art.addariticle(user,textarea);
	      if(b){
	    	  //out.print("发布微博成功");
	      //  RequestDispatcher rd=request.getRequestDispatcher("../mypage.jsp");
	  		//rd.forward(request,response);
	    	  out.print("<script>alert('发布微博成功！');window.location.href='mypage.jsp';</script>");
	          

	      }else{
	    	  out.print("发布微博失败！");
	      }

	} 
/**
 * 获取指定用户的所有微博集合
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException
 * @throws SQLException
 */
	private void getallart(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, SQLException  {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		User user= (User) request.getSession().getAttribute("user"); 
		
		ArrayList<article> artlist=art.getallart(user);
		request.setAttribute("artlist",artlist);
		RequestDispatcher rd=request.getRequestDispatcher("/mypage.jsp");
		rd.forward(request,response);
	}
	/**
	 * 获取指定好友的所有微博集合
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
		private void getfrendart(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, SQLException  {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			User user=(User)request.getSession().getAttribute("user");
            
		    List<article> frendartlist=art.getallart(user);
			request.setAttribute("frendartlist",frendartlist);
			RequestDispatcher rd=request.getRequestDispatcher("../frenweibo.jsp");
			rd.forward(request,response);
		}
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    doPost(request,response);
}
	public void init() throws ServletException {

	}

}
