package org.gl.ceir.CeirPannelCode.Service;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gl.ceir.CeirPannelCode.Feignclient.FeignCleintImplementation;
import org.gl.ceir.CeirPannelCode.Feignclient.UserRegistrationFeignImpl;
import org.gl.ceir.CeirPannelCode.Model.GenricResponse;
import org.gl.ceir.CeirPannelCode.Model.UserHeader;
import org.gl.ceir.CeirPannelCode.Model.Usertype;
import org.gl.ceir.CeirPannelCode.Util.GenerateRandomDigits;
import org.gl.ceir.CeirPannelCode.Util.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.ParseException;
import com.blueconic.browscap.UserAgentParser;
import com.blueconic.browscap.UserAgentService;
@Service 
public class RegistrationService {

	//	@Value("${FilePath1}") 
	//	String filePath; 

	@Autowired
	UserRegistrationFeignImpl registrationFeignImpl;
	@Autowired
	UserRegistrationFeignImpl userRegistrationFeignImpl;
	@Autowired
	GenerateRandomDigits randomDigits;
	@Autowired
	FeignCleintImplementation feignCleintImplementation;
	UserAgentService userAgentService = new UserAgentService();
	UserAgentParser parser;
	private final Logger log = LoggerFactory.getLogger(getClass());

	public String registrationView(String usertype,Model model,HttpSession session) {
		log.info("inside registration view controller");
		log.info("usertype given: "+usertype);
		HashMap<String, String> map=new HashMap<String,String>();
		map.put("Importer", "registration");
		map.put("Distributor", "registration");
		map.put("Retailer", "registration");
		map.put("TRC", "customRegistration");
		map.put("Manufacturer", "customRegistration");
		map.put("Lawful Agency", "customRegistration");
		map.put("Custom", "customRegistration");
		map.put("Operator", "operatorRegistration");
		map.put("Immigration", "customRegistration");
		map.put("DRT", "customRegistration");
		if(usertype!=null) {
			String output= map.get(usertype);
			log.info("value for key: "+output);
			if(output==null || ("").equals(output)) {
				output="index";
			}
			else {
				Usertype usertypeData=userRegistrationFeignImpl.userypeDataByName(usertype);
				log.info("usertypeData by usertypeName"+usertypeData);
				model.addAttribute("usertypeId", usertypeData.getId());
				log.info("now check registration available or not");;
				HttpResponse response=userRegistrationFeignImpl.checkRegistration(usertypeData.getId());
				log.info("response got: "+response);
				session.removeAttribute("response");
				session.removeAttribute("statusCode");
				session.removeAttribute("usertypeId");
				session.setAttribute("tag",response.getTag());
				session.setAttribute("statusCode",response.getStatusCode());
				session.setAttribute("usertypeId",usertypeData.getId());
			}
			return output;

		}
		else {
			log.info("if usertype is null");
			return "index";
		}
	}
	public ModelAndView ImporterRegistrationView(Integer usertypeId) {
		log.info("view registration page starting point");
		log.info("usertypeId from registration page:  "+usertypeId);
		ModelAndView mv=new ModelAndView();   
		mv.setViewName("registration");
		log.info("view registration page ending point");
		return mv;                
	}

	public ModelAndView customRegistrationView(Integer usertypeId) {
		log.info("view registration page starting point");
		log.info("usertypeId from registration page:  "+usertypeId);
		ModelAndView mv=new ModelAndView();       
		mv.setViewName("customRegistration");
		log.info("view registration page ending point");
		return mv;                
	}                                                 

	public ModelAndView operatorRegistrationView(Integer usertypeId) {
		log.info("view registration page starting point");
		log.info("usertypeId from registration page:  "+usertypeId);
		ModelAndView mv=new ModelAndView();    
		mv.setViewName("operatorRegistration");
		log.info("view registration page ending point");
		return mv;                
	}                    




	public GenricResponse securityQuestionList(String username){
		log.info("inside security question controller and username: "+username);
		GenricResponse response =userRegistrationFeignImpl.securityQuestionList(username);
		return response; 
	}



	public void captcha(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException{
		log.info("inside captcha controller");
		response.setContentType("image/jpg");
		int iTotalChars = 6;
		int iHeight = 40;
		int iWidth = 150; 
		Font fntStyle1 = new Font("Arial", Font.BOLD, 30);
		Random randChars = new Random();
		String sImageCode = (Long.toString(Math.abs(randChars.nextLong()), 36)).substring(0, iTotalChars);
		BufferedImage biImage = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2dImage = (Graphics2D) biImage.getGraphics();
		int iCircle = 15;
		for (int i = 0; i < iCircle; i++) {
			g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
		}
		g2dImage.setFont(fntStyle1);
		for (int i = 0; i < iTotalChars; i++) {
			g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
			if (i % 2 == 0) {
				g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 24);
			} else {
				g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 35);
			}
		}
		OutputStream osImage = response.getOutputStream();
		ImageIO.write(biImage, "jpeg", osImage);
		g2dImage.dispose();
		session.setAttribute("captcha_security", sImageCode);
		log.info("exit from captcha controller");
	}

	public UserHeader getUserHeaders(HttpServletRequest request) {


		try {
			parser = userAgentService.loadParser();
			/*
			 * Arrays.asList(BrowsCapField.BROWSER, BrowsCapField.BROWSER_TYPE,
			 * BrowsCapField.BROWSER_MAJOR_VERSION, BrowsCapField.DEVICE_TYPE,
			 * BrowsCapField.PLATFORM, BrowsCapField.PLATFORM_VERSION,
			 * BrowsCapField.RENDERING_ENGINE_VERSION, BrowsCapField.RENDERING_ENGINE_NAME,
			 * BrowsCapField.PLATFORM_MAKER, BrowsCapField.RENDERING_ENGINE_MAKER)
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String userIp = request.getHeader("HTTP_CLIENT_IP");

		if(userIp == null) { userIp = request.getHeader("X-FORWARDED-FOR"); if(userIp
				== null) { userIp = request.getRemoteAddr(); } }

		log.info("client Ip:  "+userIp);
		final String userAgentHeader = request.getHeader("User-Agent");
		final Capabilities capabilities = parser.parse(userAgentHeader);
		// the default fields have getters
		String userAgent = request.getHeader("User-Agent");
		String browser = capabilities.getBrowser();
		if(userAgent!=null) {
			log.info("user agent: "+userAgent);
		}
		else {
			log.info("user-agent not available");
		}
		UserHeader headers=new UserHeader(userAgent,userIp,browser+"/"+capabilities.getPlatform());
		return headers;
	}
}
