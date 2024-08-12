package org.gl.ceir.CeirPannelCode.Feignclient;
import java.util.List;

import org.gl.ceir.CeirPannelCode.Model.AddMoreFileModel;
import org.gl.ceir.CeirPannelCode.Model.AllRequest;
import org.gl.ceir.CeirPannelCode.Model.Dropdown;
import org.gl.ceir.CeirPannelCode.Model.FileExportResponse;
import org.gl.ceir.CeirPannelCode.Model.FilterRequest;
import org.gl.ceir.CeirPannelCode.Model.GenricResponse;
import org.gl.ceir.CeirPannelCode.Model.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Service
@FeignClient(url = "${feignClientPath}",value = "dsj" )
public interface FeignCleintImplementation {



	//download file(Error or Uploaded file) feign  controller
	@RequestMapping(value="/Download/uploadFile" ,method=RequestMethod.POST) 
	public @ResponseBody FileExportResponse downloadFile(@RequestParam("txnId") String txnId,@RequestParam("fileType") String fileType,@RequestParam("fileName") String fileName,@RequestParam(name="tag",required = false) String tag,@RequestBody AllRequest allrequest);




	//download file(Error or Uploaded file) feign  controller
	@RequestMapping(value="/Download/SampleFile" ,method=RequestMethod.POST) 
	public @ResponseBody FileExportResponse downloadSampleFile(@RequestParam("featureId") int featureId,@RequestBody AllRequest allrequest);





	@RequestMapping(value="filter/stakeholder/record" ,method=RequestMethod.POST) 
	public Object stolenFilter(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file,
			@RequestParam(name="source",defaultValue = "menu",required = false) String source);





	@RequestMapping(value="/stock/record" ,method=RequestMethod.POST) 
	public Object stockFilter(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file, 
			@RequestParam(value = "source",defaultValue = "menu") String  source);





	//delete stolen recovery feign  controller
	@RequestMapping(value="/stakeholder/Delete" ,method=RequestMethod.DELETE) 
	public @ResponseBody GenricResponse deleteStolenRecovery(FilterRequest stolenRecoveryModel) ;
	/************* DROPDOWN *****************/

	@RequestMapping(value="/state-mgmt/{featureId}/{userTypeId}" ,method=RequestMethod.GET) 
	public List<Dropdown> consignmentStatusList(@PathVariable("featureId") Integer featureId,@PathVariable("userTypeId") Integer userTypeId);


	@RequestMapping(value="system-config-list/{tag}" ,method=RequestMethod.GET) 
	public List<Dropdown> taxPaidStatusList(@PathVariable("tag") String tag);

	//**************************************************************** file Stolen type ***************************************************************************************************		











	//Dashboard/Datatable Feign
	@RequestMapping(value="/v2/history/Notification" ,method=RequestMethod.GET) 
	public Object dashBoardNotification(@RequestBody FilterRequest filterRequest,
			@RequestParam Integer pageNo,
			@RequestParam Integer pageSize) ;	






	//***************************************************Admin Registration as Type Dropdown********************************


	@RequestMapping(value="/system-config-list/by-tag-and-usertype/{tagId}/{userTypeId}" ,method=RequestMethod.GET) 
	public List<Dropdown> asTypeList(@PathVariable("tagId") String tag, @PathVariable("userTypeId") Integer userTypeId);


	@PostMapping("/system/viewTag")    
	public Dropdown dataByTag(Tag tag);       





	//***************************************************Admin System message Management Feign********************************

	@RequestMapping(value="/filter/message-configuration" ,method=RequestMethod.POST) 
	public Object adminMessageFeign(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file) ;


	//***************************************************Admin System Config Management Feign********************************

	@RequestMapping(value="/filter/system-configuration" ,method=RequestMethod.POST) 
	public Object adminConfigFeign(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file) ;




	//***************************************************Admin System Config Management Feign********************************

	@RequestMapping(value="/filter/policy-configuration" ,method=RequestMethod.POST) 
	public Object policyManagementFeign(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file) ;













	@RequestMapping(value="/system-config-list/by-tag-and-featureid/{tagId}/{featureId}" ,method=RequestMethod.GET) 
	public List<Dropdown> modeType(@PathVariable("tagId") String tagId, @PathVariable("featureId") Integer featureId);

	//******************************* Block Unblock Approve/Reject Devices Feign ********************************

	@PutMapping("/accept-reject/stolen-recovery-block-unblock")
	public @ResponseBody GenricResponse approveRejectFeign(FilterRequest FilterRequest);









	//***************************************************Audit Management Feign********************************

	@RequestMapping(value="/filter/audit-trail" ,method=RequestMethod.POST) 
	public Object auditManagementFeign(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file) ;



	//************************************ Message update Feign  *************************************************

	@PostMapping(value="/checkDevice")
	public @ResponseBody GenricResponse viewDetails(FilterRequest filterRequest);

	//************************************ manage User Feign  *************************************************

	@RequestMapping(value="/filter/end-users" ,method=RequestMethod.POST) 
	public Object manageUserFeign(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file);




	//download file(Error or Uploaded file) feign  controller
	@RequestMapping(value="/Download/manuals" ,method=RequestMethod.POST) 
	public @ResponseBody FileExportResponse manualDownloadSampleFile(@RequestBody AllRequest auditRequest);

	//******************************* Tag Updated DropDown in Field ****************************************

	@PostMapping("/projection/tags/system-config-list")
	public @ResponseBody GenricResponse getAllTagsDropdowntFeign(FilterRequest filterRequest);	


	//***************************************************Field Management Feign**********************************

	@RequestMapping(value= "/filter/system-config-list" , method=RequestMethod.POST) 
	public Object fieldManagementFeign(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file);



	//***************************************************Add Field Management Feign********************************

	@RequestMapping(value= "/save/system-config-list" , method=RequestMethod.POST) 
	public GenricResponse AddfieldManagementFeign(@RequestBody FilterRequest filterRequest);


	//***************************************************View Field Management Feign********************************

	@RequestMapping(value= "/get/system-config-list" , method=RequestMethod.POST) 
	public GenricResponse viewfieldManagementFeign(@RequestBody FilterRequest filterRequest);


	//***************************************************update Field Management Feign********************************

	@RequestMapping(value= "/system-config-list" , method=RequestMethod.PUT) 
	public GenricResponse updatefieldManagementFeign(@RequestBody FilterRequest filterRequest);


	//***************************************************Delete Field Management Feign********************************

	@RequestMapping(value="/tags/system-config-list" ,method=RequestMethod.DELETE) 
	public @ResponseBody GenricResponse deleteFieldFeign(@RequestBody FilterRequest filterRequest);

	@PostMapping("/system/viewTag")
	public @ResponseBody AddMoreFileModel addMoreBuutonCount(AddMoreFileModel addMoreCount);	









	/* Rule Feature Mapping  Feign */
	@RequestMapping(value="/filter/rule-engine-mapping" ,method=RequestMethod.POST) 
	public Object ruleFeatureMappingListFeign(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(name = "file", defaultValue = "0" ,required = false) Integer file);



	/*
	 * @DeleteMapping(value="rule-engine-mapping-userType") public @ResponseBody
	 * List<NewRule> getUserTypeByFeatureID(@RequestParam(name="featureName") String
	 * featureName) ;
	 */




	//***************************************************Admin Pending TAC List Feign********************************

	@RequestMapping(value="/filter/pending-tac-approveddb" ,method=RequestMethod.POST) 
	public Object pendingTACFeign(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file,
			@RequestParam(value = "source",defaultValue = "menu") String source) ;	


	//****************************************Pending TAC List Delete Feign********************************

	@RequestMapping(value="/pending-tac-approved" ,method=RequestMethod.DELETE) 
	public @ResponseBody GenricResponse deletePendingTac(@RequestBody FilterRequest filterRequest);

	@RequestMapping(value="/visa/view" ,method=RequestMethod.POST) 
	public Object viewVisaRequest(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file,
			@RequestParam(value = "source", defaultValue = "menu") String source);



}					





