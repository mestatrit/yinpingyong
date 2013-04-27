<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/showPic.js"></script>

<script type="text/javascript">

	function countByChildren() {
		
		var bodyElement = document.getElementsByTagName("body")[0];
	
		//獲取所有子節點
		alert(bodyElement.childNodes.length);
		
		//獲取當前節點的類型：元素1 属性2 文本3
		alert(bodyElement.nodeType);
		
		alert(bodyElement.lastChild.nodeValue);
	}
	
	/*加載完之後，調用此方法*/
	window.onload = countByChildren;
</script>

</head>
<body>
	<ul>
		<li>
			<a href="images/kxt.jpg" onclick="showPic(this);return false;">图片1</a>	
		</li>
		<li>
			<a href="images/lin21.png" onclick="showPic(this);return false;">图片2</a>
		</li>
		<li>
			<img id="place" src="images/f.png">
		</li>
	</ul>
	<p>123</p>
</body>
</html>