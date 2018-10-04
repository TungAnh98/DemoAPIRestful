package com.vietis.learnrestful.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.ListModel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vietis.learnrestful.model.Person;

@Controller
@RequestMapping("/home")
public class AjaxController {

	private List<Person> listPerson = new ArrayList<Person>();

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("test");
		System.out.println("test something");
		return mv;
	}

	@RequestMapping(value = "/addnew", method = RequestMethod.GET)
	public @ResponseBody String addNew(HttpServletRequest request) {
		String name = request.getParameter("name");
		String age = request.getParameter("age");
	
		Person person = new Person(name, Integer.parseInt(age));
		((ArrayList<Person>) listPerson).add(person);
		System.out.println("GGGGGG");
		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			ajaxResponse = mapper.writeValueAsString(person);
			System.out.println(ajaxResponse);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ajaxResponse;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody String searchPerson(HttpServletRequest request) {
		String query = request.getParameter("name");
		Person person = searchPersonByName(query);

		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			ajaxResponse = mapper.writeValueAsString(person);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ajaxResponse;
	}

	public Person searchPersonByName(String query) {
		for (Person p : listPerson) {
			if (p.getName().equals(query)) {
				return p;
			}
		}
		return null;
	}
}