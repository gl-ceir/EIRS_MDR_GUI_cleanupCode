package org.gl.ceir.CeirPannelCode.Feignclient;

import java.util.List;

import org.gl.ceir.CeirPannelCode.Model.Dropdown;
import org.gl.ceir.CeirPannelCode.Model.FileCopyToOtherServer;
import org.gl.ceir.CeirPannelCode.Model.FilterRequest;
import org.gl.ceir.CeirPannelCode.Model.GenricResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Service
@FeignClient(url="${gsmaFeignClientPath}",value = "profileUrlsgsma")
public interface GsmaFeignClient {

	//View all product Name feign controller

	@RequestMapping(value="/gsma/brandName" ,method=RequestMethod.GET) 
	public List<Dropdown> viewAllProductList();



	@RequestMapping(value="/gsma/modelName" ,method=RequestMethod.GET) 
	public List<Dropdown> viewAllmodel(@RequestParam(name="brand_id") Integer brand_id);







	@RequestMapping(value="/rule/GetfeaturebyRuleName" ,method=RequestMethod.POST) 
	public List<String> getFeatureName(@RequestParam(name = "ruleName", required = false) String ruleName);


	//---------------------------------check Msisdn Exist or not ---------------------------------

	@PostMapping(path = "gsma/CheckImeiMsisdnValues")
	public @ResponseBody String checkImeiDetails(@RequestParam(name = "imei", required = false) String imei,
			@RequestParam(name = "msisdn", required = false) String msisdn,
			@RequestParam(name="publicIp", required = false) String publicIp,
			@RequestParam(name="browser", required = false) String browser);






	//--------------------------------- schedule Report Datatable ---------------------------------


	@RequestMapping(value="/ScheduleReport/getAll" ,method=RequestMethod.POST) 
	public Object viewAllScheduleReport(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file);

	//--------------------------------- Add Schedule Report Feign ---------------------------------


	//--------------------------------- Delete Schedule Report Feign ---------------------------------

	@DeleteMapping(value="/ScheduleReport/{id}") 
	public @ResponseBody GenricResponse deleteScheduleFeign(@PathVariable("id") Integer id);




	@RequestMapping(value="/addFileToSync" ,method=RequestMethod.POST) 
	public GenricResponse saveUploadedFileOnANotherServer(@RequestBody FileCopyToOtherServer fileRequest) ;


}
