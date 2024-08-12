package org.gl.ceir.CeirPannelCode.Service;

import javax.servlet.http.HttpSession;

import org.gl.ceir.CeirPannelCode.Feignclient.UserProfileFeignImpl;
import org.gl.ceir.CeirPannelCode.Model.Password;
import org.gl.ceir.CeirPannelCode.Model.UserStatus;
import org.gl.ceir.CeirPannelCode.Util.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
	@Autowired
	UserProfileFeignImpl userProfileFeignImpl;

	private final Logger log = LoggerFactory.getLogger(getClass());

	private Integer id;	

	public HttpResponse changePassword(Password password,HttpSession session) {
		log.info("inside change password controller");
		password.setPublicIp(session.getAttribute("publicIP").toString());
		password.setBrowser(session.getAttribute("browser").toString());
		log.info("password data is :  "+password);                 
		Integer userid=(Integer)session.getAttribute("userid");
		log.info("userid from session:  "+userid);
		password.setUserid(userid);      
		if(password.getPassword().equals(password.getConfirmPassword())) {
			HttpResponse response=new HttpResponse();             
			response=userProfileFeignImpl.changePassword(password);
			log.info("response got:  "+response);
			return response; 	
		}
		else {    
			HttpResponse response=new HttpResponse("Both Passwords do the match",500,"password_mismatch");   
			return response; 
		}

	} 

	public HttpResponse updateUSerStatus(UserStatus userStatus,HttpSession session) {
		log.info("inside update userStatus controller");
		Integer userid=(Integer)session.getAttribute("userid");
		log.info("userid from session:  "+userid);
		String username=(String)session.getAttribute("username");
		log.info("username fom session: "+username);
		userStatus.setUserId(userid); 
		userStatus.setPublicIp(session.getAttribute("publicIP").toString());
		userStatus.setBrowser(session.getAttribute("browser").toString());
		log.info("userStatus data is :  "+userStatus);
		HttpResponse response=new HttpResponse();             
		response=userProfileFeignImpl.updateUserStatus(userStatus);
		return response;  
	}               




	public HttpResponse adminApprovalService(UserStatus userStatus,HttpSession session) {
		log.info("inside update userStatus controller");
		Integer userid= userStatus.getUserId();
		Integer id= userStatus.getId();
		log.info("userid from session:  "+userid);
		userStatus.setId(id);
		userStatus.setUserId(userid); 
		userStatus.setPublicIp(session.getAttribute("publicIP").toString());
		userStatus.setBrowser(session.getAttribute("browser").toString());
		log.info("userStatus data is :  "+userStatus);
		HttpResponse response=new HttpResponse();             
		response=userProfileFeignImpl.adminUserApproval(userStatus);
		return response;  
	} 



	public HttpResponse changeUserStatusService(UserStatus userStatus,HttpSession session) {
		log.info("inside changeUserStatus Service");
		//Integer userid= userStatus.getUserId();
		//Integer id= userStatus.getId();
		//log.info("userid from session:  "+userid);
		//userStatus.setUserId(userid); 
		//userStatus.setId(id);
		log.info("userStatus data is :  "+userStatus);
		userStatus.setPublicIp(session.getAttribute("publicIP").toString());
		userStatus.setBrowser(session.getAttribute("browser").toString());
		HttpResponse response=new HttpResponse();             
		response=userProfileFeignImpl.changeUserStatusFeign(userStatus);
		return response;  
	} 


}
