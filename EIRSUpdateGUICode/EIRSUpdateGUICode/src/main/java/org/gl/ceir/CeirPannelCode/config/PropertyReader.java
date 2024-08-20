package org.gl.ceir.CeirPannelCode.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyReader {


	@Value("${apiUrl1}")		
   public String apiUrl1;
	
	@Value("${dashBoardfeignClientPath}")
   public String dashBoardfeignClientPath;
   
	@Value("${grievanceFeignClientPath}")
	public String grievanceFeignClientPath; 
   
	@Value("${feignClientPath}")
	public String feignClientPath;
   
	@Value("${gsmaFeignClientPath}")
	public String gsmaFeignClientPath;
	
	@Value("${deviceRepositoryfeignClientPath}")
	public String deviceRepositoryfeignClientPath;
   
	@Value("${sessionLogOutTime}")
	public int sessionLogOutTime;
   
	@Value("${serverId}")
	public Integer serverId;
	
	@Value("${downloadFilePath}")
	public String downloadFilePath;
	
	@Value("${propertiesFileLocation}")
	public String propertiesFileLocation;
	
	@Value("${serverName}")
	public String serverName;
	
	@Value("${sourceServerName}")
	public String sourceServerName;
	
	@Value("${destServerName}")
	public String destServerName;
	
	@Value("${destFilePath}")
	public String destFilePath;
}