<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WEMAKEPRICE_LJH</title>
</head>
<body>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<h3>[입력]</h3>
<div id="url" class="url">
	<span>URL</span><input type="text" id="urlInput" name="url" class="urlInput"><br/>
	<span>Type</span>
	<select id="typeInput"  name="typeInput" class="typeInput">
    	<option value="A">HTML 태그 제외</option>
    	<option value="B">Text 전체</option>
	</select><br/>
	<span>출력단위 묶음</span><input type="number" name="num" id="outputNum" class="outputNum"><br/>
	<input type="button" id="button1" value="출력" onclick="executeButton();" />
</div>
<h3>■ 교차 출력</h3>
<div>
<span>■ 문자열 : </span><span id="htmltext" class=""></span><br/>
<span>■ 결과값 : </span><span id="result" class=""></span><br/>
<h3>■ 출력 결과</h3>
</div>
<div id="outputResult" class="outputResult">
<span>■ 몫 : </span><span id="quotient" class=""></span><br/>
<span>■ 나머지 : </span><span id="remainder" class=""></span><br/>
</div>
</body>
<script>

function executeButton(){
	var outputNum = $('#outputNum').val();
	var urlInput = $('#urlInput').val();
	var typeInput = $('#typeInput').val();
	
	/* INPUT CHECK - START */
	var reg=/^[0-9]+$/; //숫자만 입력했는지 검증할 정규표현식
	var inputNum=$("input[name=num]").val();
    var numSubmit=reg.test(inputNum);

    if(numSubmit==false){
   	alert("출력단위 묶음 : 숫자만 입력하세요.");
    $("input[name=num]").focus();return false;
    }

    regexp =  /^(?:(?:https?|ftp):\/\/)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:\/\S*)?$/;
    var urlSubmit=regexp.test(urlInput);
    
    if(urlSubmit==false){
       	alert("URL : URL 확인해주세요.");
        $("input[name=url]").focus();return false;
        }
    /* INPUT CHECK - END */
    
	$.ajax({
		type:'POST',
		url : '/callTest',
		data : {
			"outputNum" :  outputNum,
			"urlInput" :   urlInput,
			"typeInput" :  typeInput
		},
		success : function(data) {
			
			$("#htmltext").text(data.htmltext);
			$("#result").text(data.result);
			$("#quotient").text(data.quotient);
			$("#remainder").text(data.remainder);
			
	     },
		error : function(request,status,error) {
			//alert("code:"+request.status + " "+ request.responseText);
		}
	});
}
</script>
</html>
