package com.newproject.wmp;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * wemakeprice 과제
 * url 호출 시 HTML 크롤링 후 텍스크로 영어와 숫자만 출력
 * 오름차순 후 영어 숫자 교차 출력
 * 출력묶음 단위에 대한 몫과 나머지 반환
 */
@Controller
public class CallTestController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/callTest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> test(@RequestParam("outputNum") int outputNum,@RequestParam("urlInput") String urlInput,@RequestParam(
	"typeInput") String typeInput) {
		Map<String, String> map = new HashMap<String,String>(); 

		try {
	         // 1. 화면 intput 적용
	         String URL = urlInput;
	         String type = typeInput;
	         int cnum = outputNum;
	         
	         // 2. Jsoup 크롤링 Connection 생성
	         org.jsoup.Connection conn = Jsoup.connect(URL);

	         // 3. HTML 파싱 
	         Document html = conn.get();
	         
	         // 4. .text()
	         String text;
	         // 타입에 따라 분기 처리 (태그제거와 text만 출력의 차이점없어 동일 로직 적용 중)
	         if (typeInput == "A") {
	        	 text = html.text();
	         } else {
	        	 text = html.text();
	         }
	         
	         // 5. 영문대소문자
	         String str = text.replaceAll("[^a-zA-Z]", "");
	         
	         // 6. 숫자
	         String num = text.replaceAll("[^0-9]", "");

	         // 7. 숫자정렬(오름차순)
	         String answer3 = "";
	         char[] charArr3 = num.toCharArray();
	         Arrays.sort(charArr3);
	         answer3 = new StringBuilder(new String(charArr3)).toString();

	         // 8. 영대소문자(오름차순)구분없이
	         String answer = Arrays.stream(str.split(""))
	        		 .sorted(new Comparator<String>() {
						@Override
						public int compare(String o1, String o2) {
						     int res = o1.compareToIgnoreCase(o2);
						     return (res == 0) ? o1.compareTo(o2) : res;
						 }
					})
	        		 .collect(Collectors.joining());

	         // 9. 영대소문자와 숫자 교차 출력
	         // 영문대소문자문자열의 길이
	         int a1 = answer.length();
	         // 숫자문자열의 길이
	         int a2 = answer3.length();
	         
	         // 교차출력 로직 적용 결과값
	         String result = "";
	         
	         // 영문대소문자 문자열 먼저 for 문으로 교차 출력
	         for(int i = 0; i < a1; i++) {
	             String result1 = answer.substring(i,i+1);
	             result = result + result1;
	             // 숫자문자열 for 문으로 교차 출력 적용 로직
	             for(int n = 0; n < a2; n++) {
	            	 // 같은 순번만 적용
	            	 // 이외는 다름 for문에서 적용함
	            	 if (i == n) {
	            		 String result2 = answer3.substring(n,n+1);
	                     result = result + result2;
	            	 }

	             }

	         }
	         
	         // 
	         // a1( 영문대소문자문자열 ) 이 같거나 더 긴케이스는 완료
	         // a2( 숫자문자열 ) 이 더 긴 케이스 적용
	         if (a1 < a2) {
	        	 for(int r = a1; r < a2; r++) {
	        		String result3 = answer3.substring(r,r+1);
	                result = result + result3;
	            } 
	         }

	 		System.out.println("htmltext : " + text);
	 		System.out.println("result : " + result);
	 		
	 		// html텍스트
	 		map.put("htmltext",text);
	 		// 영대소문자 숫자 교차 출력 결과
		    map.put("result", result);

	        // 10. 묶음단위 적용
	        int quotient = 0;
	        int remainder = 0;
	 		
	 		int divided = result.length();
	 		int divisor = cnum;
	 		
	 		if (divisor != 0) {
	 			quotient = (int)(divided / divisor);
	 			remainder = divided % divisor;
	 			System.out.println("divided : " + divided + " divisor : " + divisor + " quotient : " + quotient + " remainder : " + remainder);
	 			
	 			//묶음단위까지만 몫
	 			map.put("quotient",  result.substring(0,result.length() - remainder));
		        //나머지만큼 제외
	 			map.put("remainder", result.substring(result.length() - remainder, result.length()));
		        
	 			System.out.println("quotient : " + result.substring(0,result.length() - remainder));
		        System.out.println("remainder : " + result.substring(result.length() - remainder, result.length()));
	 			
	 			
	 		} else if (divisor == 0) {
	 			
	 			// 0으로 나눌시 전체 나머지
	 			map.put("quotient","");
		        map.put("remainder", result);
		        
		        System.out.println("quotient : ");
		        System.out.println("remainder : " + result);
		        
	 		} 
	 		else {
	 			System.out.println("error check");
	 		}

       
	     } catch (IOException e) {	
	         e.printStackTrace();
	     }

		return map;
	}
	
}	


