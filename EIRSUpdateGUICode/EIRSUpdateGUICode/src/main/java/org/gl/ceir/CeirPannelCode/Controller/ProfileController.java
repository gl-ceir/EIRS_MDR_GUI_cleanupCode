package org.gl.ceir.CeirPannelCode.Controller;
import javax.servlet.http.HttpSession;

import org.gl.ceir.CeirPannelCode.Model.Password;
import org.gl.ceir.CeirPannelCode.Model.UserStatus;
import org.gl.ceir.CeirPannelCode.Service.ProfileService;
import org.gl.ceir.CeirPannelCode.Util.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class ProfileController {

	@Autowired
	ProfileService profileService;

	private final Logger log = LoggerFactory.getLogger(getClass());	


	@RequestMapping(value = "changePassword",method = RequestMethod.POST)
	@ResponseBody
	public  HttpResponse changePassword(@RequestBody Password password,HttpSession session) {

		return profileService.changePassword(password,session);
	}

	@RequestMapping(value = "updateUserStatus",method = RequestMethod.POST)
	@ResponseBody
	public  HttpResponse updateUserStatus(@RequestBody UserStatus userStatus,HttpSession session) {
		return profileService.updateUSerStatus(userStatus,session);
	}  





	@RequestMapping(value ="/adminApproval",method = RequestMethod.POST)
	@ResponseBody
	public  HttpResponse adminApproval(@RequestBody UserStatus userStatus,HttpSession session) {
		return profileService.adminApprovalService(userStatus,session);

	}






	@GetMapping("/editOthersProfile")
	public ModelAndView editProfile() {
		ModelAndView mv = new ModelAndView();
		log.info(" editProfile entry point..");
		mv.setViewName("editOthersProfile");
		log.info("editProfile exit point..");
		return mv;
	}
}
