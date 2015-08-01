package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.repository.Stage2Repository;
import demo.service.Stage2Service;

@Controller
@RequestMapping("/stage")
public class StageController {

	@Autowired
	Stage2Service service;

	@RequestMapping("/2")
	public String stage2(Model model)
	{
		model.addAttribute("stage2", service.getOne("1"));
		return "stage2";
	}
	
	@RequestMapping("/2with")
	public String stage2with(Model model, @RequestParam String keyword){
		model.addAttribute("stage2page", service.getByContentKeyword(keyword));
		return "stage2";
	}
	
	

	@RequestMapping("/3")
	public String stage3()
	{
		return "stage3";
	}
	
	@RequestMapping("/3/{locationname}")
	public String stage3location(Model model, @PathVariable String locationname){
		model.addAttribute("people", service.getNearPeopleWithLocation(locationname));
		return "stage3";
	}
	

}
