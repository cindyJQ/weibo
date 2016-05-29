package com.jiaqi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


	public class DB {
		private static Connection conn;
		public static Connection getConn() {
			
			try {
				Class.forName("org.gjt.mm.mysql.Driver");
				conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/weibo?useUnicode=true&characterEncoding=UTF-8", "root", "root");
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();

			}
			return conn;
		}

	}


