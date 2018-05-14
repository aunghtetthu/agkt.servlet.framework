package com.ak.test;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aungkhant.controller.AbstractServlet;

@WebServlet("/test/loop.extension")
public class Test2FrameWorkLoop extends AbstractServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2388156297396066024L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			List<People> peopleList = new ArrayList<>();
			for(int i = 0;i<=10; i++) {
				People people = new People();
				people.setName("Name     " + i);
				people.setAge(i);
				people.setAddress("Address     "+i);
				people.setStaff(i%2==0);
				peopleList.add(people);
			}
			this.addViewObject("helloMessage", "This is Hello Message");
			this.addViewObject("peopleList",peopleList);
			
			showView(request, response);
		}catch (Exception e) {
			handleException(response, e);
		}
		
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

}
