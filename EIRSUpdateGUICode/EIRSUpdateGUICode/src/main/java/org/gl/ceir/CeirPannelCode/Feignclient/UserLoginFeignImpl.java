package org.gl.ceir.CeirPannelCode.Feignclient;

import java.util.List;

import org.gl.ceir.CeirPannelCode.Model.FilterRequest;
import org.gl.ceir.CeirPannelCode.Model.GenricResponse;
import org.gl.ceir.CeirPannelCode.Model.Password;
import org.gl.ceir.CeirPannelCode.Model.User;
import org.gl.ceir.CeirPannelCode.Model.UserStatus;
import org.gl.ceir.CeirPannelCode.Response.LoginResponse;
import org.gl.ceir.CeirPannelCode.Util.HttpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Service
@FeignClient(url="${apiUrl1}",value = "loginUrls")
public interface UserLoginFeignImpl { 

	@PostMapping("/Login/checkUser") 
	public LoginResponse checkUser(User user);

	@PostMapping("/Login/ipLog") 
	public LoginResponse ipLog(User user);




	@PostMapping("/Login/sessionTracking/{userid}")
	public HttpResponse sessionTracking(@PathVariable("userid") Integer userid,@RequestParam(name="publicIP") String publicIP ,@RequestParam(name="browser") String browser );



	@PostMapping("/Login/updateNewPassword")
	public HttpResponse updateNewPassword(Password forgotPassword,@RequestParam(name="publicIP") String publicIP ,@RequestParam(name="browser") String browser);

	@PostMapping("/Login/testLogin") 
	public LoginResponse searchUserDetailFeign(UserStatus userStatus);

	//*******************************District DropDown****************************************



	//*******************************Address Data Table Feign****************************************

	@RequestMapping(value="/viewAllLocality" ,method=RequestMethod.POST) 
	public Object viewAllLocality(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file);

	//***************************************************Delete Address Management Feign********************************

	@RequestMapping(value="/deleteRow" ,method=RequestMethod.DELETE) 
	public @ResponseBody GenricResponse deleteAddressFeign(@RequestBody FilterRequest filterRequest);

	//***************************************************Add Address Management Feign********************************

	@RequestMapping(value= "/saveProvince" , method=RequestMethod.POST) 
	public GenricResponse AddProvinceManagementFeign(@RequestBody FilterRequest filterRequest);

	//***************************************************Add Address Management Feign********************************

	@RequestMapping(value= "/saveDistrict" , method=RequestMethod.POST) 
	public GenricResponse AddDistrictManagementFeign(@RequestBody FilterRequest filterRequest);

	//***************************************************Add Address Management Feign********************************

	@RequestMapping(value= "/saveCommune" , method=RequestMethod.POST) 
	public GenricResponse AddCommuneManagementFeign(@RequestBody FilterRequest filterRequest);

	//***************************************************Add Address Management Feign********************************

	@RequestMapping(value= "/saveVillage" , method=RequestMethod.POST) 
	public GenricResponse AddVillageManagementFeign(@RequestBody FilterRequest filterRequest);





	@GetMapping("/getDistinctFeature")
	public @ResponseBody List<String> getDistinctFeature();



	@GetMapping("/getDistinctUserType")
	public @ResponseBody List<String> getDistinctUserType();

	@GetMapping("/getDistinctFeatureName")
	public @ResponseBody List<String> getDistinctFeatureName();




}

