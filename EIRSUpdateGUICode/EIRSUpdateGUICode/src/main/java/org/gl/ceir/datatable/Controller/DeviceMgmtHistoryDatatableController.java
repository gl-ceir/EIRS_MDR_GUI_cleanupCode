package org.gl.ceir.datatable.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.gl.ceir.CeirPannelCode.Feignclient.DeviceRepositoryFeign;
import org.gl.ceir.CeirPannelCode.Model.DeviceFilterRequest;
import org.gl.ceir.CeirPannelCode.Model.DeviceManagementRequest;
import org.gl.ceir.CeirPannelCode.Model.Dropdown;
import org.gl.ceir.CeirPannelCode.Model.GenricResponse;
import org.gl.ceir.Class.HeadersTitle.DatatableResponseModel;
import org.gl.ceir.Class.HeadersTitle.IconsState;
import org.gl.ceir.configuration.Translator;
import org.gl.ceir.pageElement.model.Button;
import org.gl.ceir.pageElement.model.PageElement;
import org.gl.ceir.pagination.model.DeviceManagementContentModel;
import org.gl.ceir.pagination.model.DeviceManagementPaginationModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class DeviceMgmtHistoryDatatableController {
	
	@Autowired
	Translator Translator;
	@Autowired
	DatatableResponseModel datatableResponseModel;
	@Autowired
	PageElement pageElement;
	@Autowired
	Button button;
	@Autowired
	IconsState iconState;
	@Autowired
	DeviceRepositoryFeign deviceRepositoryFeign;
	@Autowired
	DeviceManagementContentModel deviceManagementContentModel;
	@Autowired
	DeviceManagementPaginationModel deviceManagementPaginationModel;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@PostMapping("getDeviceHistory")
	public ResponseEntity<?> viewDeviceHistoryRecord(
			@RequestParam(name = "type", defaultValue = "Device Management", required = false) String role,
			HttpServletRequest request, HttpSession session,
			@RequestParam(name = "source", defaultValue = "menu", required = false) String source,
			@RequestParam(name = "requestType", required = false) String requestType){
		
		int file = 0;
		// Data set on this List
		List<List<Object>> finalList = new ArrayList<List<Object>>();
		String filter = request.getParameter("filter");
		Gson gsonObject = new Gson();
		DeviceFilterRequest deviceManagementRequest = gsonObject.fromJson(filter, DeviceFilterRequest.class);

		Integer pageSize = Integer.parseInt(request.getParameter("length"));
		Integer pageNo = Integer.parseInt(request.getParameter("start")) / pageSize;
		
		String column = "0".equalsIgnoreCase(request.getParameter("order[0][column]")) ? "Created Date"
				: "1".equalsIgnoreCase(request.getParameter("order[0][column]")) ? "Modified Date"
						: "2".equalsIgnoreCase(request.getParameter("order[0][column]")) ? "TAC"
								: "3".equalsIgnoreCase(request.getParameter("order[0][column]")) ? "Device Type"
										: "4".equalsIgnoreCase(request.getParameter("order[0][column]")) ? "Brand Name"
												: "5".equalsIgnoreCase(request.getParameter("order[0][column]")) ? "Marketnig Name"
														: "6".equalsIgnoreCase(request.getParameter("order[0][column]")) ? "Model Name"
																: "7".equalsIgnoreCase(request.getParameter("order[0][column]")) ? "Status"
																		: "8".equalsIgnoreCase(request.getParameter("order[0][column]")) ? 
																				"Agent Name": "Modified Date";
			
		String order;
		if ("Modified Date".equalsIgnoreCase(column) && request.getParameter("order[0][dir]")==null) {
			order = "desc";
		} 
		else if("Modified Date".equalsIgnoreCase(column) && request.getParameter("order[0][dir]")=="asc"){
			order ="asc";
		}
		else {
			order = request.getParameter("order[0][dir]");
		}
		
		deviceManagementRequest.setOrderColumnName(column);
		deviceManagementRequest.setOrder(order);

		deviceManagementRequest.setSearchString(request.getParameter("search[value]"));
		
		log.info("Request Type for Edit=" + requestType);
		List<String> history=new ArrayList<>();
		try {
			deviceManagementRequest.setPublicIp(session.getAttribute("publicIP").toString());
			deviceManagementRequest.setBrowser(session.getAttribute("browser").toString());
			log.info("Request send to get Device API=" + deviceManagementRequest);
			//Object response = deviceRepositoryFeign.deviceManagementFeign(deviceManagementRequest, pageNo, pageSize, file, source);
			Object response = deviceRepositoryFeign.getDeviceHistoryFeign(deviceManagementRequest, pageNo, pageSize, file, source);
			Gson gson = new Gson();
			String apiResponse = gson.toJson(response);

			deviceManagementPaginationModel = gson.fromJson(apiResponse, DeviceManagementPaginationModel.class);

			List<DeviceManagementContentModel> paginationContentList = deviceManagementPaginationModel.getContent();

			if (paginationContentList.isEmpty()) {

				datatableResponseModel.setData(Collections.emptyList());
			} else {
				String modifiedOn;
				String brandName;
				String modelName;
				String os;
				for (DeviceManagementContentModel dataInsideList : paginationContentList) {
					Integer rowId=dataInsideList.getId();
					history =  dataInsideList.getUpdateColumns();
					if(history.contains("modifiedOn")) {
						modifiedOn = "<mark style='background-color:#f7ede6'>"+(String) dataInsideList.getModifiedOn()+"</mark>";
					}else {
						modifiedOn = (String) dataInsideList.getModifiedOn();	
					}
					String createdOn = (String) dataInsideList.getCreatedOn();
					String tac = dataInsideList.getDeviceId();
					if(history.contains("brandName")) {
						brandName = "<mark style='background-color:#f7ede6'>"+dataInsideList.getBrandName()+"</mark>";
					}else {
						brandName = dataInsideList.getBrandName();
					}
					String deviceType=dataInsideList.getDeviceType();
					String marketingName=dataInsideList.getMarketingName();
					if(history.contains("modelName")) {
						modelName = "<mark style='background-color:#f7ede6'>"+dataInsideList.getModelName()+"</mark>";
					}else {
						modelName = dataInsideList.getModelName();
					}
					
					/*
					 * if(history.contains("os")) { os =
					 * "<mark style='background-color:#f7ede6'>"+dataInsideList.getOs()+"</mark>";
					 * }else { os = dataInsideList.getOs(); }
					 */
					String status = dataInsideList.getStateInterp();
					Integer deviceState= dataInsideList.getDeviceState();
					String agentName=dataInsideList.getUserDisplayName();
					
					String action = iconState.iconsDeviceManagement(tac,requestType,deviceState,rowId);
					Object[] finalData = {createdOn,modifiedOn,tac,deviceType,brandName,marketingName,modelName,status,agentName,action,history};
					List<Object> finalDataList = new ArrayList<Object>(Arrays.asList(finalData));
					finalList.add(finalDataList);
					datatableResponseModel.setData(finalList);

				}
			}
			
			// data set on ModelClass
			datatableResponseModel.setRecordsTotal(deviceManagementPaginationModel.getNumberOfElements());
			datatableResponseModel.setRecordsFiltered(deviceManagementPaginationModel.getTotalElements());
			return new ResponseEntity<>(datatableResponseModel, HttpStatus.OK);
		} catch (Exception e) {
			datatableResponseModel.setRecordsTotal(null);
			datatableResponseModel.setRecordsFiltered(null);
			datatableResponseModel.setData(Collections.emptyList());
			//log.error(e.getMessage(), e);
			return new ResponseEntity<>(datatableResponseModel, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//-------------------Get Device detail History Controller-------------------//
	
	@PostMapping ("getDeviceHistoryInfo")
	public @ResponseBody Object getdeviceDetailHistory(
			@RequestParam(name = "deviceId", required = true) String deviceId,
			@RequestParam(name = "featureId", required = false) Integer featureId,
			@RequestParam(name = "userId", required = true) Integer userId,
			@RequestParam(name = "userType", required = false) String userType,
			@RequestParam(name = "userTypeId", required = false) Integer userTypeId,
			@RequestParam(name = "rowId", required = false) Integer rowId,
			HttpServletRequest request,HttpSession session) {
			String publicIp = session.getAttribute("publicIP").toString();
			String browser = session.getAttribute("browser").toString();
		log.info("request send to the getdeviceDetailHistory api with Device ID =" +deviceId+" and rowId " +rowId);
		Object response= deviceRepositoryFeign.getDeviceHistoryFeign(deviceId,featureId,userId,userType,userTypeId,rowId,publicIp,browser);
		log.info("response of Device Detail History ."+response);
		return response;

	}
	
	
	//-------------------get User Agent Drop down Controller-------------------//
		@ResponseBody
		@GetMapping("getAgentName")
		public List<Dropdown> getUserAgentList() {
			List<Dropdown> dropdown = deviceRepositoryFeign.getUserAgentListFeign();
			return dropdown;
		}
		
}

