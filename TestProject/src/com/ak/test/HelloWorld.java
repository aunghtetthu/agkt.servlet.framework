package com.ak.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloWorld extends HttpServlet{

	private String message;
	

	   public void init() throws ServletException {
	      // Do required initialization
	      message = "Hello World";
	   }

	   public void doGet(HttpServletRequest request, HttpServletResponse response){
		   
		   try {
			PrintWriter writer = response.getWriter();
			writer.print("Hello This is new Project");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	   }

	   public void destroy() {
	      // do nothing.
	   }

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response){
		// TODO Auto-generated method stub
		
	}
	

	
	
}
