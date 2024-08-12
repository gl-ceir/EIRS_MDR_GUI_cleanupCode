package org.gl.ceir.CeirPannelCode.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.gl.ceir.CeirPannelCode.Feignclient.FeignCleintImplementation;
import org.gl.ceir.CeirPannelCode.Feignclient.GsmaFeignClient;
import org.gl.ceir.CeirPannelCode.Feignclient.UserLoginFeignImpl;
import org.gl.ceir.CeirPannelCode.Feignclient.UserProfileFeignImpl;
import org.gl.ceir.CeirPannelCode.Model.AddMoreFileModel;
import org.gl.ceir.CeirPannelCode.Model.Dropdown;
import org.gl.ceir.CeirPannelCode.Model.FilterRequest;
import org.gl.ceir.CeirPannelCode.Model.GenricResponse;
import org.gl.ceir.CeirPannelCode.Model.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjectDropdownController {
	@Autowired
	FeignCleintImplementation feignCleintImplementation;

	@Autowired
	GsmaFeignClient gsmaFeignClient;
	@Autowired
	UserLoginFeignImpl userLoginFeignImpl;


	@Autowired
	UserProfileFeignImpl userProfileFeignImpl;





	private final Logger log = LoggerFactory.getLogger(getClass());
	@ResponseBody
	@GetMapping("getDropdownList/{featureId}/{userTypeId}")
	public List<Dropdown> getConsignmentStatus(@PathVariable("featureId") Integer featureId,@PathVariable("userTypeId") Integer userTypeId) {
		List<Dropdown> dropdown = feignCleintImplementation.consignmentStatusList(featureId, userTypeId);
		return dropdown;
	}

	@ResponseBody
	@GetMapping("getDropdownList/{tag}")
	public List<Dropdown> getTaxPaidStatus(@PathVariable("tag") String tag) {
		List<Dropdown> dropdown = feignCleintImplementation.taxPaidStatusList(tag);
		return dropdown;
	}


	@ResponseBody
	@GetMapping("getTypeDropdownList/{tagId}/{userTypeId}")
	public List<Dropdown> asTypeDropdown(@PathVariable("tagId") String tag, @PathVariable("userTypeId") Integer userTypeId ) {
		List<Dropdown> dropdown = feignCleintImplementation.asTypeList(tag, userTypeId);
		return dropdown;
	}

	@ResponseBody
	@GetMapping("dataByTag/{tag}/")
	public Dropdown dataByTag(@PathVariable("tag") String tag) {
		Tag tagData=new Tag(tag);
		Dropdown dropdown = feignCleintImplementation.dataByTag(tagData);
		return dropdown;
	}

	@ResponseBody
	@GetMapping("getSourceTypeDropdown/{tagId}/{featureId}")
	public List<Dropdown> asRequestType(@PathVariable("tagId") String tagId, @PathVariable("featureId") Integer featureId ) {
		List<Dropdown> dropdown = feignCleintImplementation.modeType(tagId, featureId);
		return dropdown;
	}
	@ResponseBody
	@GetMapping("productList")
	public List<Dropdown> productList() {
		List<Dropdown> dropdown = gsmaFeignClient.viewAllProductList();
		return dropdown;
	}

	@RequestMapping(value="/productModelList",method ={org.springframework.web.bind.annotation.RequestMethod.GET})
	public @ResponseBody List<Dropdown> productModelList(@RequestParam(name="brand_id") Integer brand_id){
		List<Dropdown> productModelList = gsmaFeignClient.viewAllmodel(brand_id);

		return productModelList;

	}

	

	@PostMapping("/getSystemTags") 
	public @ResponseBody GenricResponse getAllTagsDropdown (@RequestBody FilterRequest filterRequest,HttpSession session)  {
		filterRequest.setPublicIp(session.getAttribute("publicIP").toString());
		filterRequest.setBrowser(session.getAttribute("browser").toString());
		log.info("request send to the getAllTagsDropdown api="+filterRequest);
		GenricResponse response= feignCleintImplementation.getAllTagsDropdowntFeign(filterRequest);
		log.info("response from getAllTagsDropdown api "+response);
		return response;

	}


	@GetMapping("/addMoreFile/{tag}") 
	public @ResponseBody AddMoreFileModel addMoreFileControler (@PathVariable("tag") String tag)  {
		log.info("request send to the addMore Count api="+tag);
		AddMoreFileModel addmore = new AddMoreFileModel();
		addmore.setTag(tag);
		AddMoreFileModel response= feignCleintImplementation.addMoreBuutonCount(addmore);
		log.info("response from addMore Count api "+response);
		return response;

	}

	/*
	 * @PostMapping("getFeatureName") public ResponseEntity<?>
	 * getFeatureName(@RequestParam(name = "ruleName", required = false) String
	 * ruleName) { List<String> list = gsmaFeignClient.getFeatureName(ruleName);
	 * return new ResponseEntity<>(list, HttpStatus.OK); }
	 */



	




	@ResponseBody
	@GetMapping("getDistinctFeatureList")
	public List<String> getDistinctFeatureList() {
		List<String> dropdown = userLoginFeignImpl.getDistinctFeature();
		return dropdown;
	}

	@ResponseBody
	@GetMapping("getDistinctUserTypeList")
	public List<String> getDistinctUserTypeList() {
		List<String> dropdown = userLoginFeignImpl.getDistinctUserType();
		return dropdown;
	}

	@ResponseBody
	@GetMapping("getDistinctFeatureNameList")
	public List<String> getDistinctFeatureNameList() {
		List<String> dropdown = userLoginFeignImpl.getDistinctFeatureName();
		return dropdown;
	}


}		
