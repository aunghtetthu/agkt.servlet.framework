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
			People p = new People();
			p.setId(2);
			p.setName("hello");

			People p2 = entityManager.findById(p);
			System.out.println(p2.getId());
			System.out.println(p2.getName());
			System.out.println(p2.getAge());
			System.out.println(p2.getStaff());
			
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
