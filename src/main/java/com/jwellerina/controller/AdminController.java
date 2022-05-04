package com.jwellerina.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwellerina.documents.Category;
import com.jwellerina.documents.Item;
import com.jwellerina.model.EmailRequest;
import com.jwellerina.model.ServerResponse;
import com.jwellerina.service.EmailService;
import com.jwellerina.service.ItemsService;
import com.jwellerina.utils.ErrorCodeConstants;
import com.jwellerina.utils.GeneralConstants;
import com.jwellerina.utils.UtilityFunctions;

@RestController
public class AdminController {


	@Autowired
	UtilityFunctions utilityFunctions;
	
	@Autowired
	ItemsService itemsService;
	
	@Autowired
	EmailService emailService;
	
	//twillo account password is 
	// bcepr5029m210990
	// abhirait874@gmail.com
	// 7021719654 regis mobile
//	@GetMapping(value = "/sendSMS")
//    public ResponseEntity<String> sendSMS() {
//
//            Twilio.init("AC03cea71e19482a68b8ea273b26dd52a0", "8a7839a593a152e982269ede690cb06f");
//
//            Message.creator(new PhoneNumber("+917021719654"),
//                            new PhoneNumber("3254137353"), "Greetings from Jwellerina ! Your acocunt is successfully created. Start placing your orders, get your jwelleries to dorn. ").create();
//
//            return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
//    }
	
	  @GetMapping(value = "/fast2sms")
	  public ResponseEntity<String> sendSMS() {
		  	String  message   = "";
	          String apiKey = "vpw1jqmfU3NT4xosLIAhb0YDrntRPdgiByazKCFHc5MQX6e9ZJmXYOPGFfb3NaoenlruTsDd8v4LJAj9";
	          String sendId = "FastSM";
	          try {
				 message = URLEncoder.encode("Jwellerina! Jwellerina! Greetings from Jwellerina ! Your acocunt is successfully created. Start placing your orders, get your jwelleries to dorn. ",
						  "UTF-8");
				 String language = "english";
				 String route = "p";
				 String number ="9665282117";
				 String myUrl ="https://www.fast2sms.com/dev/bulkV2?authorization="+apiKey+"&sender_id="+
				   sendId + "&message="+message+"&language="+language+"&route="+route+"&numbers="+number;
				 System.out.println(myUrl);
				 
				 URL url = new URL(myUrl);
				 HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
				 
				 con.setRequestMethod("GET");
				 con.setRequestProperty("User-Agent", "Mozilla/5.0");
				 con.setRequestProperty("cache-control", "no-cache");
				 
				 int code =  con.getResponseCode();
				 
				 System.out.println("Response code "+code);
				 
				 StringBuffer res = new StringBuffer();
				 BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				 
				 while(true) {
					 String line = br.readLine();
					 
					 if(line == null) {
						 break;
					 }
					 res.append(line);
				 }
				 System.out.println(res);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
	  }
	
	@RequestMapping(value = "/addEditCategory", method = RequestMethod.POST)
	public ServerResponse addEditCategory(@RequestBody Category c) throws Exception {
	
		String code = null;
		
		try {
			code = itemsService.addEditCategory(c);
		
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_ADD_CATEGORY,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE,null);
	}
	
	@RequestMapping(value = "/addEditItem", method = RequestMethod.POST)
	public ServerResponse addEditItem(@RequestBody Item c) throws Exception {

		String code = null;
		
		try {
			code = itemsService.addEditItem(c);
		
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_ADD_ITEM,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE, null);
	}

	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public ServerResponse sendEmail(@RequestBody EmailRequest c) throws Exception {
		
		System.out.println(c);
		emailService.sendMail(c.getTo(), c.getSubject(), c.getMessage());
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE, null);
	}
	
	@RequestMapping(value = "/items/{categoryId}", method = RequestMethod.GET)
	public ServerResponse getItems(@PathVariable Integer categoryId) throws Exception {

		Map<String, Object> resultJson = new HashMap<>();		
		String code = null;
		
		try {
			resultJson = itemsService.getItems(categoryId);
			code = (String) resultJson.get(GeneralConstants.CODE);
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_GET_ITEM,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE,resultJson.get(GeneralConstants.DATA));
	}
	
	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.GET)
	public ServerResponse getItem(@PathVariable Integer itemId) throws Exception {

		Map<String, Object> resultJson = new HashMap<>();		
		String code = null;
		
		try {
			resultJson = itemsService.getItem(itemId);
			code = (String) resultJson.get(GeneralConstants.CODE);
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_GET_ITEM,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE,resultJson.get(GeneralConstants.DATA));
	}
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ServerResponse getAllCategories() throws Exception {

		Map<String, Object> resultJson = new HashMap<>();		
		String code = null;
		
		try {
			resultJson = itemsService.getCategories();
			code = (String) resultJson.get(GeneralConstants.CODE);
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.FAILED_TO_GET_ITEM,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE,resultJson.get(GeneralConstants.DATA));
	}
}
