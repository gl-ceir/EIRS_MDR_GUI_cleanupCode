package org.gl.ceir.CeirPannelCode.Controller;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gl.ceir.CeirPannelCode.Model.Password;
import org.gl.ceir.CeirPannelCode.Model.User;
import org.gl.ceir.CeirPannelCode.Model.UserStatus;
import org.gl.ceir.CeirPannelCode.Response.LoginResponse;
import org.gl.ceir.CeirPannelCode.Service.LoginService;
import org.gl.ceir.CeirPannelCode.Util.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;  

@Controller
public class LoginController {
	private final Logger log = LoggerFactory.getLogger(getClass());	
	@Autowired
	LoginService loginService;

	@RequestMapping(value = "/login",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView login(@RequestParam(name="isExpired",required = false) String isExpired,HttpSession session,HttpServletRequest request){
		return loginService.loginPage(isExpired,session,request);	
	} 

	@ResponseBody
	@RequestMapping(value = "/saveLogin",method = {RequestMethod.POST})
	public LoginResponse saveLogin(@RequestBody User user,HttpSession session,HttpServletRequest request){
		Base64.Decoder decoder = Base64.getDecoder();  
		// Decoding string  
		/*
		 * String dStr = new String(decoder.decode(user.getPassword()));
		 * log.info("decrypted password "+dStr);
		 */
		Base64.Encoder encoder = Base64.getEncoder();  
		String str = encoder.encodeToString(user.getPassword().getBytes()); 
		log.info("encrypted  password "+str); 
		// user.setPassword(str);
		return loginService.checkLogin(user,session,request);
	}


	@ResponseBody
	@RequestMapping(value = "/ipLogInfo",method = {RequestMethod.POST})
	public LoginResponse ipLogInfo(@RequestBody User user,HttpSession session,HttpServletRequest request){
		return loginService.ipLogInformation(user,session,request);
	}




	/*
	 * @RequestMapping(value = "/manualLogout", method = RequestMethod.POST) public
	 * ModelAndView logout(HttpSession session,HttpServletRequest request) {
	 * log.info("Coming first time in logout"); return
	 * loginService.logout(session,request);
	 * 
	 * }
	 */

	@RequestMapping(value = "/homePage", method = RequestMethod.GET)
	public void indexSessionOut(HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		loginService.indexPageSessionOut(session,response,request);
	}


	@RequestMapping(value = "/redirectToHomePage", method = RequestMethod.GET)
	public void redirectHomePage(HttpSession session,HttpServletResponse response) {
		loginService.redirectToHome(response);
	}



	@RequestMapping(value = "/forgotPassword",method = RequestMethod.GET)
	public ModelAndView forgotPassword(){ 
		log.info("inside forgotPassword controller ");
		ModelAndView mv=new ModelAndView();
		mv.setViewName("forgotPassword");
		return mv;   
	}



	@RequestMapping(value = "updateNewPassword",method = RequestMethod.POST)
	@ResponseBody
	public  HttpResponse newPassword(@RequestBody Password password,HttpServletRequest request) {
		return loginService.updateNewPassword(password,request); 
	}

	@ResponseBody
	@RequestMapping(value = "/searchUser",method = {RequestMethod.POST})
	public LoginResponse searchUserDetail(@RequestBody UserStatus userStatus,HttpSession session,HttpServletRequest request){
		return loginService.searchUserDetailService(userStatus,session,request);
	}

}
