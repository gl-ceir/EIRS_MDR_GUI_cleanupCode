package org.gl.ceir.CeirPannelCode.Feignclient;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.gl.ceir.CeirPannelCode.Model.DeviceFilterRequest;
import org.gl.ceir.CeirPannelCode.Model.DeviceManagementRequest;
import org.gl.ceir.CeirPannelCode.Model.Dropdown;
import org.gl.ceir.CeirPannelCode.Model.GenricResponse;
import org.gl.ceir.pagination.model.DeviceManagementContentModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@FeignClient(url = "${deviceRepositoryfeignClientPath}", value = "deviceRepository" )

public interface DeviceRepositoryFeign {
	
			
			//-------------------get dash board count feign Controller-------------------//
	
			@RequestMapping(value="/getMDRDashboardData" , method=RequestMethod.GET) 
					public Object getDashboardCountFeign(@RequestParam(name = "userId", required = true) String userId);
			
			
			//-------------------get all Device details feign Controller-------------------//
	
			@RequestMapping(value="/getDevicesDetails" ,method=RequestMethod.POST) 
			public Object deviceManagementFeign(@RequestBody DeviceFilterRequest deviceManagementRequest,
					@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
					@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
					@RequestParam(value = "file", defaultValue = "0") Integer file,
					@RequestParam(name="source",defaultValue = "menu",required = false) String source) ;
			
			
			//-------------------get single device detail feign Controller-------------------//
			
			@RequestMapping(value="/getDeviceInfo" ,method=RequestMethod.POST) 
			public @ResponseBody Object getDeviceinfoFeign(
					@RequestParam(name = "deviceId", required = true) String deviceId,
					@RequestParam(name = "featureId", required = false) Integer featureId,
					@RequestParam(name = "publicIp", required = false) String publicIp,
					@RequestParam(name = "browser", required = false) String browser,
					@RequestParam(name = "userId", required = true) Integer userId,
					@RequestParam(name = "userType", required = false) String userType,
					@RequestParam(name = "userTypeId", required = false) Integer userTypeId
					);
			
			
			//-------------------Export Device details feign Controller-------------------//
			
			@RequestMapping(value="/exportData" ,method=RequestMethod.POST) 
			public Object deviceManagementExportFeign(@RequestBody DeviceFilterRequest deviceManagementRequest,
					@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
					@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
					@RequestParam(value = "file", defaultValue = "1") Integer file,
					@RequestParam(name="source",defaultValue = "menu",required = false) String source) ;
			
			
			//-------------------Update Device Management feign Controller-------------------//
			
			@RequestMapping(value="/addDevice" ,method=RequestMethod.POST) 
			public GenricResponse addDeviceInfo(@RequestBody DeviceManagementRequest deviceManagementRequest)  ;
			
			
			//-------------------Add Device Management feign Controller-------------------//
			
			@RequestMapping(value="/updateDevices" ,method=RequestMethod.POST) 
			public GenricResponse updateDeviceInfo(@RequestBody List<DeviceManagementRequest>updateRequest) ;
			
			//-------------------Delete Device feign Controller-------------------//
			
			@RequestMapping(value="/deleteDevice" ,method=RequestMethod.POST) 
			public @ResponseBody GenricResponse deleteDeviceFeign(@RequestParam(name = "deviceId", required = true) String deviceId,
					@RequestParam(name = "featureId", required = false) String featureId,
					@RequestParam(name = "publicIp", required = false) String publicIp,
					@RequestParam(name = "browser", required = false) String browser,
					@RequestParam(name = "userId", required = true) String userId,
					@RequestParam(name = "userType", required = false) String userType);
			
			//-------------------Get Device History feign Controller-------------------//
			
			@RequestMapping(value="/getDeviceHistory" ,method=RequestMethod.POST) 
			public Object getDeviceHistoryFeign(@RequestBody DeviceFilterRequest deviceManagementRequest,
					@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
					@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
					@RequestParam(value = "file", defaultValue = "0") Integer file,
					@RequestParam(name="source",defaultValue = "menu",required = false) String source) ;
			
			//-------------------Delete Device feign Controller-------------------//
			
			@RequestMapping(value="/getDeviceHistoryInfo" ,method=RequestMethod.POST) 
			public @ResponseBody Object getDeviceHistoryFeign(
					@RequestParam(name = "deviceId", required = true) String deviceId,
					@RequestParam(name = "featureId", required = false) Integer featureId,
					@RequestParam(name = "userId", required = true) Integer userId,
					@RequestParam(name = "userType", required = false) String userType,
					@RequestParam(name = "userTypeId", required = false) Integer userTypeId,
					@RequestParam(name = "rowId", required = false) Integer rowId,
					@RequestParam(name = "publicIp", required = false) String publicIp,
					@RequestParam(name = "browser", required = false) String browser
					);
			
			//-------------------Get User Agent Drop down feign Controller-------------------//
			
			@RequestMapping(value="getDistinctUserName" ,method=RequestMethod.GET) 
			public List<Dropdown> getUserAgentListFeign();
			
			//-------------------Get User Agent Drop down feign Controller-------------------//
			
			@RequestMapping(value="getDistinctDeviceType" ,method=RequestMethod.GET) 
			public List<Object> getDeviceTypetListFeign();
			
			
			@RequestMapping(value="getManufacturerCountry" ,method=RequestMethod.GET) 
			public List<Object> getManufacturerCountryListFeign();
			
}
