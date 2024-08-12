package org.gl.ceir.CeirPannelCode.Controller;

import javax.servlet.http.HttpSession;

import org.gl.ceir.CeirPannelCode.Feignclient.DeviceRepositoryFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class mdrDashboardController {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	DeviceRepositoryFeign deviceRepositoryFeign;

	@RequestMapping(value = { "/Home" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public ModelAndView Home(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		log.info(" view MDR dashboard entry point.");
		mv.setViewName("mdrDashboard");
		log.info(" view MDR dashboard exit point.");
		return mv; 
	}


	//-------------------Dashboard Count Controller-------------------//

	@GetMapping("dashboardCount")
	public @ResponseBody Object getDashboardCount(@RequestParam(name = "userId", required = true) String userId,HttpSession session) {
		Object response = deviceRepositoryFeign.getDashboardCountFeign(userId);
		log.info("response from dashboard count api " + response);
		return response;
	}

}
