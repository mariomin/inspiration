
$(window).resize(function(){  // resize 当窗口发送变化执行
	var w=$(window).width();
	var c=w-270;
	
	$(".content").css({
		"width":c
	});
	
	var sideH=$(window).height();
	var cont_iframeH=$(window).height();
	$(".side").css({"height":sideH});
	$(".cont_iframe").css({"height":cont_iframeH});  // cont_iframe高度
});

$(document).ready(function(){
	
	var MainH=$(window).width();  //#MainH 包裹宽度 
	var sideH=$(window).height(); // sideH 的高度为窗口高度减去top
	
	var cont_iframeH=$(window).height();  // cont_iframeH宽度为窗口宽度减去 80
	$(".side").css({"height":sideH}); // 侧边栏高度
	$(".cont_iframe").css({"height":cont_iframeH});  // cont_iframe高度
	
	
	
	var w=$(window).width();
	var c=w-270;
	
	$(".content").css({
		"width":c
	});
	
	/*
	$(".level_1").click(function(){
		var flag=$(this).find("input").val();
//		alert(flag);
		if(flag==0){
			$(this).parent().find("ul").slideDown();
			$(this).find("input").val("1");
			$(this).find(".arr_b").css({"display":"block"});
			$(this).find(".arr_r").css({"display":"none"});
			$(this).css({"background-color":"#34404c"})
		}else{
			$(this).parent().find("ul").slideUp();
			$(this).find("input").val("0");
			$(this).find(".arr_b").css({"display":"none"});
			$(this).find(".arr_r").css({"display":"block"});
			$(this).css({"background-color":"#2a3642"})
		}
	*/
	$(".level_1").click(function(){
		var flag=$(this).parent().find("ul").css("display");
		if(flag=="none"){
			$(".level_2").slideUp();
			$(this).parent().find("ul").slideDown();
			$(".level_1 .arr_b").css({"display":"none"});
			$(".level_1 .arr_r").css({"display":"block"});
			$(this).find(".arr_b").css({"display":"block"});
			$(this).find(".arr_r").css({"display":"none"});
			$(".level_1").css({"background-color":"rgba(0,0,0,0)"});
			$(this).css({"background-color":"#4695d0"});
		}else{
			$(this).parent().find("ul").slideUp();
			$(".level_1 .arr_b").css({"display":"none"});
			$(".level_1 .arr_r").css({"display":"block"});
			$(this).css({"background-color":"none"});
		}
	
	$(".side .level_2 li").click(function(){
		var index=$(this).index();
		
		$(this).addClass('lev_2_on');
		$(this).siblings().removeClass('lev_2_on');
		//$("#content > div").eq(index).css({display:"block"}).siblings().css({display:"none"});
	})
	
	

		
		
//		$(".level_2").slideUp();
//	
	})
	
	$(".nav li").click(function(){
		$(".nav li a").removeClass("menu_click1");
		$(this).find("a").addClass("menu_click1");
	})

	$(".box1 .title li").click(function(){
		$(".box1 .title li a").removeClass("menu_click2");
		$(this).find("a").addClass("menu_click2");

		var flag=$(this).index();
		$(".box1 .content_in").css({"display":"none"});
		$(".box1 .content_in").eq(flag).css({"display":"block"});
	})
	
	
	$(".fun_rad ul li").click(function(){
		
		var index2=$(this).index();
		$(this).addClass('fun_rad_on');
		$(this).siblings().removeClass('fun_rad_on');
		//$("#content > div").eq(index).css({display:"block"}).siblings().css({display:"none"});
	})
	
})