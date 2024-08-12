package org.gl.ceir.CeirPannelCode.Feignclient;
import java.util.List;

import org.gl.ceir.CeirPannelCode.Model.FeatureDropdown;
import org.gl.ceir.CeirPannelCode.Model.FilterRequest;
import org.gl.ceir.CeirPannelCode.Model.GenricResponse;
import org.gl.ceir.CeirPannelCode.Model.Usertype;
import org.gl.ceir.CeirPannelCode.Util.HttpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@FeignClient(url="${apiUrl1}",value = "registrationUrls")
public interface UserRegistrationFeignImpl {
  
	@PostMapping("/userRegistration/getUsertypes")
	public List<Usertype> userypeList();
	
	@PostMapping("/userRegistration/usertypeIdByName/{usertype}")
	public Usertype userypeDataByName(@PathVariable("usertype")String usertype);
	                                  
	@PostMapping("/userRegistration/getSecurityQuestion/{username}")
	public GenricResponse securityQuestionList(@PathVariable("username")String username);


	
	
	

	
	@PostMapping("/userRegistration/getUsertypes")                                                                                         
	public List<Usertype> userRegistrationDropdown(@RequestParam(name="type",required = false) Integer type); 
	
	                              
	@PostMapping("/userRegistration/checkAvailability/{usertypeId}")                                                                                         
	public HttpResponse checkRegistration(@PathVariable("usertypeId")Integer usertypeId); 
     
	@PostMapping("/getAllFeatures")                                                                                         
	public List<FeatureDropdown> userAllFeatureDropdown(); 
	

	
	@PostMapping("/subFeature/view")                                                                                         
	public List<FeatureDropdown> userAllSubFeatureDropdown();
	
	@PostMapping("/userProfile/getAddDeleteRoles")	
	public @ResponseBody GenricResponse getAddDeleteRoleFeign(FilterRequest filterRequest);	
	
	
	@PostMapping("/userTypeDropdownForGrievance")                                                                                         
	public List<Usertype> userRegistrationDropdownGrievance(); 
	
}
