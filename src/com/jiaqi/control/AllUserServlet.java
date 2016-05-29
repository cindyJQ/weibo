package com.jiaqi.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jiaqi.dao.ArticleDAO;
import com.jiaqi.dao.UserDAO;
import com.jiaqi.model.User;
import com.jiaqi.model.article;

/**
 * Servlet implementation class AllUserServlet
 */
@WebServlet(name="/AllUserServlet",urlPatterns={"/all"})
public class AllUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static UserDAO udao=new UserDAO();
	ArticleDAO adao=new ArticleDAO();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllUserServlet() {
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
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int userid=Integer.parseInt(request.getParameter("userid"));
		String username=request.getParameter("username");
		ArrayList<article> alist=new ArrayList<article>();
		ArrayList<User> ulist=new ArrayList<User>();
		User user=null;
		
		user=adao.fuser(userid);
		
		alist=adao.getallart(user);
		
		ulist=udao.selectfri(user.getUserid());
		
		
		request.getSession().setAttribute("alist", alist);
		request.getSession().setAttribute("ulist", ulist);
		request.getSession().setAttribute("user1", user);
		RequestDispatcher rd=request.getRequestDispatcher("/userpage.jsp");
		rd.forward(request,response);
	}

}
