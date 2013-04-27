/**
 * 基于暂占位符，实现图片的加载：不跳转它页；显示图片导航栏
 */
function showPic(picObject) {
	var attrSrc = picObject.getAttribute("href");
	var placeObject = document.getElementById("place");
	placeObject.setAttribute("src",attrSrc);
}

