package org.gl.ceir.CeirPannelCode.Feignclient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@FeignClient(url = "${feignClientPath}",value = "dsjs" )
public interface FeignClientImplementation {
	 
	
	//download file(Error or Uploaded file) feign  controller
	@RequestMapping(value="/Download/uploadFile" ,method=RequestMethod.GET) 
		public @ResponseBody String downloadFile(@RequestParam("txnId") String txnId,@RequestParam("fileType") String fileType,@RequestParam("fileName") String fileName);

	
	
	
	//download file(Error or Uploaded file) feign  controller
		@RequestMapping(value="/stoke/Download/SampleFile" ,method=RequestMethod.GET) 
			public @ResponseBody String downloadSampleFile(@RequestParam("samplFileType") String fileType);

		       
		
		
	
	    	     
	/*
	 * @PostMapping("/MobileOperators/") public void saveOperator(@RequestBody
	 * Operator operator);
	 */ 

	/*
	 * @PutMapping("/MobileOperators/{id}") void
	 * editOperatorById(@PathVariable("id") int id,@RequestBody Operator op);
	 * 
	 * @DeleteMapping("/MobileOperators/{id}") void
	 * deleteOperatorById(@PathVariable("id") int id);
	 * 
	 * @GetMapping("/MediationSource/") public List<Mediation> getMediationData();
	 * 
	 * @PostMapping("/MediationSource/") public void addMediationData(@RequestBody
	 * Mediation mediation);
	 * 
	 * @PutMapping("/MediationSource/{id}") public void editMediation(@PathVariable
	 * int id,Mediation mediation);
	 * 
	 * @DeleteMapping("/MediationSource/{id}") public void
	 * deleteMediation(@PathVariable("id") int id);
	 */
}

