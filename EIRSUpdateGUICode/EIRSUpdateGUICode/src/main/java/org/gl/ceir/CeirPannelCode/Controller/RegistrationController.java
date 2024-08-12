package org.gl.ceir.CeirPannelCode.Controller;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gl.ceir.CeirPannelCode.Feignclient.FeignCleintImplementation;
import org.gl.ceir.CeirPannelCode.Feignclient.FeignClientImplementation;
import org.gl.ceir.CeirPannelCode.Feignclient.UserRegistrationFeignImpl;
import org.gl.ceir.CeirPannelCode.Model.Dropdown;
import org.gl.ceir.CeirPannelCode.Model.FeatureDropdown;
import org.gl.ceir.CeirPannelCode.Model.FilterRequest;
import org.gl.ceir.CeirPannelCode.Model.GenricResponse;
import org.gl.ceir.CeirPannelCode.Model.Usertype;
import org.gl.ceir.CeirPannelCode.Service.LoginService;
import org.gl.ceir.CeirPannelCode.Service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller 
public class RegistrationController {
	@Autowired    
	RegistrationService registrationService;
	@Autowired
	UserRegistrationFeignImpl userRegistrationFeignImpl;
	@Autowired
	FeignClientImplementation feignImplementation;
	@Autowired
	FeignCleintImplementation feignCleintImplementation;

	
	@Autowired
	LoginService loginService;
	private final Logger log = LoggerFactory.getLogger(getClass());	

@RequestMapping("DMC")
	/*
	 * public ModelAndView index(HttpServletRequest request,HttpSession session){
	 * 
	 * log.info("inside index controller "); ModelAndView mv=new ModelAndView();
	 * Integer userid=(Integer)session.getAttribute("userid");
	 * log.info("userid::::::::::"+userid); loginService.sessionRemoveCode(userid,
	 * session); mv.setViewName("index"); return mv; }
	 */  
	public ModelAndView index(HttpServletRequest request,HttpSession session,HttpServletRequest request1){
	log.info("inside index controller ");
	ModelAndView mv=new ModelAndView();
	Integer userid=(Integer)session.getAttribute("userid");
	String defaultLink = (String)session.getAttribute("defaultLink");
	if( Objects.nonNull(userid) && !(userid.equals(0) || userid.equals(-1))) {
		log.info("userid::::::::::"+userid);
		if( Objects.nonNull(defaultLink) )
			//return new ModelAndView("redirect:"+defaultLink);
			return new ModelAndView("redirect:./?lang="+(String)session.getAttribute("language"));
		else {
			//mv.setViewName("login");
			loginService.sessionRemoveCode(userid, session,request1);
			mv.setViewName("index");
		}
	}else {
		loginService.sessionRemoveCode(userid, session,request1);
		mv.setViewName("index");
	}
	return mv;      
} 

	@ResponseBody
	@GetMapping("asTypeData/{tagId}")
	public List<Dropdown> asTypeDropdown(@PathVariable("tagId") String tag ) {
		List<Dropdown> dropdown = feignCleintImplementation.taxPaidStatusList(tag);
		return dropdown;
	}





	@RequestMapping(value = "/usertypeList",method = {RequestMethod.GET})
	@ResponseBody  
	public List<Usertype> usertypeList(){ 
		List<Usertype> response =userRegistrationFeignImpl.userypeList();
		return response;          
	} 

	


	@RequestMapping(value = "/registration")
	public String registration(@RequestParam(name = "type",required =false) String usertype,Model model,HttpSession session) {
		return registrationService.registrationView(usertype,model,session);
	}

	@RequestMapping(value = "/importorRegistration",method = {RequestMethod.GET})
	public ModelAndView importorRegistration(@RequestParam(name = "usertypeId",required =false,defaultValue="0") Integer usertypeId	) throws IOException{
		ModelAndView mv=registrationService.ImporterRegistrationView(usertypeId);

		return mv; 
	} 
	@RequestMapping(value = "/customRegistration",method = {RequestMethod.GET})
	public ModelAndView customRegistration(@RequestParam(name = "usertypeId",required =false,defaultValue ="0") Integer usertypeId	) throws IOException{
		ModelAndView mv=registrationService.customRegistrationView(usertypeId);
		return mv; 
	} 

	@RequestMapping(value = "/operatorRegistration",method = {RequestMethod.GET})
	public ModelAndView operatorRegistration(@RequestParam(name = "usertypeId",required =false,defaultValue ="0") Integer usertypeId) throws IOException{
		ModelAndView mv=registrationService.operatorRegistrationView(usertypeId);
		return mv; 
	} 

	



	@RequestMapping(value = "/verifyOtpPage",method = {RequestMethod.GET})
	public ModelAndView verifyOtpPage(){
		ModelAndView mv=new ModelAndView();
		log.info("inside verifyOtp page controller ");
		mv.setViewName("verifyOtp");
		return mv;      
	} 

	@RequestMapping(value = "/securityQuestionList/{username}",method = {RequestMethod.GET})
	@ResponseBody 
	public GenricResponse questionList(@PathVariable("username")String username){
		GenricResponse response =registrationService.securityQuestionList(username);
		return response;         
	}

	
	
	


	
	
	



	@GetMapping("/editProfile")
	public ModelAndView editProfile() {
		ModelAndView mv = new ModelAndView();
		log.info(" editProfile entry point..");
		mv.setViewName("editProfile");
		log.info("editProfile exit point..");
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/captcha")
	@ResponseBody
	public void captcha(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException{

		registrationService.captcha(request, response, session);
	} 

	@RequestMapping(method = RequestMethod.GET,value = "/serverTime")
	@ResponseBody
	public long getServerTime(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException{

	return new Date().getTime();
	}
	
	@RequestMapping(value = "/registrationUserType",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody  
	public List<Usertype> userTypeDropdown(@RequestParam(name="type",required = false) Integer type){ 
		List<Usertype> response =userRegistrationFeignImpl.userRegistrationDropdown(type);
		return response;          
	}
	
	
	@RequestMapping(value = "/getAllfeatures",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody  
	public List<FeatureDropdown> allFeatureDropdown(){ 
		List<FeatureDropdown> response =userRegistrationFeignImpl.userAllFeatureDropdown();
		return response;          
	}
	
	
	
	@RequestMapping(value = "/getsubfeatures",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody  
	public List <FeatureDropdown> allSubFeatureDropdown(){ 
		List<FeatureDropdown> response =userRegistrationFeignImpl.userAllSubFeatureDropdown();
		return response;          
	}
	
	@PostMapping("/getAddDeleteRoles") 
	public @ResponseBody GenricResponse getRoleTypeDropdown (@RequestBody FilterRequest filterRequest)  {
		log.info("request send to the getRoleTypeDropdown api="+filterRequest);
		GenricResponse response= userRegistrationFeignImpl.getAddDeleteRoleFeign(filterRequest);
		log.info("response from getRoleTypeDropdown api "+response);
		return response;

		}
	
	
	
	@RequestMapping(value = "/registrationUserTypeGrievance",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody  
	public List<Usertype> registrationUserTypeGrievance(){ 
		List<Usertype> response =userRegistrationFeignImpl.userRegistrationDropdownGrievance();
		log.info("grievance userType dropdown  "+response);
		return response;          
	}
}
