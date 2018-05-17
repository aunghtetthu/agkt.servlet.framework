package com.ak.test;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aungkhant.controller.AbstractServlet;
import com.aungkhant.model.EntityManager;


@WebServlet("/tes")
public class TestFrameWork extends AbstractServlet{

	@Override
	public void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			EntityManager<People> entityManager = (EntityManager<People>) getEntityManager();
			People p = new People()
;			entityManager.createTable(p);
		this.showView(request, response);
		
		}catch(Exception e){
			handleException(response, e);
		}
		
	}

	@Override
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	
}
