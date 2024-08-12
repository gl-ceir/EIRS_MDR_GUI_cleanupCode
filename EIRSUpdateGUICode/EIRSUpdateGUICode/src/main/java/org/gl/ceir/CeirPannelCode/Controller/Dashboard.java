package org.gl.ceir.CeirPannelCode.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.gl.ceir.CeirPannelCode.Feignclient.FeignCleintImplementation;
import org.gl.ceir.CeirPannelCode.Service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Dashboard {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	FeignCleintImplementation feignCleintImplementation;

	@Autowired
	LoginService loginService;
	ModelAndView mv = new ModelAndView();

	@GetMapping("*")
	public ModelAndView openUserRegisterPage(HttpSession session,HttpServletRequest request) {
		//return loginService.dashBoard(session,request);
		return loginService.dashBoard(session);
	}

	@RequestMapping(value = { "/Home_backup" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public ModelAndView Home(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Home");
		return mv;
	}


}
