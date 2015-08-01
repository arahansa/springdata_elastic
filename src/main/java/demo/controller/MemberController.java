package demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import scala.reflect.macros.internal.macroImpl;
import demo.domain.Member;
import demo.service.MemberService;

@Controller
public class MemberController {
	
	private static final String INDEX = "index";
	@Autowired MemberService service;
	
	@RequestMapping("/")
	public String index(){
		return INDEX;
	}
	
	
	@RequestMapping("/members")
	public String members(Model model){
		Iterable<Member> members = service.getAll();
		model.addAttribute("members", members.iterator());
		return INDEX;
	}
	@RequestMapping("/members/{page}")
	public String membersPage(Model model, @PathVariable int page){
		Page<Member> pages = service.getAllWithPage(page-1); //주의.막코딩임.
		model.addAttribute("pages", pages);
		return INDEX;
	}
	
	@RequestMapping(value="/member/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public String delete(@PathVariable String id){
		service.delete(id);
		return "OK";
	}
	
	// 쓰기
	@RequestMapping(value="/member", method=RequestMethod.POST)
	public String Member(Member member){
		service.save(member);
		return "redirect:/members";
	}
	
	// 특정 이상형으로 골라서 검색
	@RequestMapping("/membersideal")
	public String membersByIdeal(Model model, @RequestParam String ideal){
		List<Member> membersIdeal = service.getByIdeal(ideal);
		model.addAttribute("members", membersIdeal);
		return INDEX;
	}
	
	// 페이셋 통계 
	@RequestMapping("/memberterms/{field}")
	public String memberByTerms(Model model, @PathVariable String field){
		List<Term> terms = service.getMemberfacetTermsByField(field);
		model.addAttribute("terms", terms);
		
		return INDEX;
	}
	

}
