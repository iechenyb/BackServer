<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>消息推送</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="<%=basePath%>push/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>push/socket.io.js"></script>
  </head>
  
  <body>
  <script type="text/javascript">
     var count =1;
	 var socket = io.connect('http://127.0.0.1:7788');
	 socket.on('pushpoint',function(msg){
		 $('#x').text(msg.x);
		 $('#y').text(msg.y); 
	     $('#con1').width(msg.x);
	     $('#con1').height(msg.y);
	     $('#con2').width(msg.x);
	     $('#con2').height(msg.y);
	     $('#con3').width(msg.x);
	     $('#con3').height(msg.y);
	     $('#con4').width(msg.x);
	     $('#con4').height(msg.y);
	      var currentTime = "<span class='time' >" + new Date() + "</span>";
	      var element = $(currentTime+":x="+msg.x+",y="+msg.y+",count="+count+++"<br>");
	      var d = document.getElementById("con1");
      	  d.style.backgroundColor = "rgb("+msg.x+","+msg.y+","+msg.z+")";
      	  var d1 = document.getElementById("con2");
    	  d1.style.backgroundColor = "rgb("+msg.x+","+msg.y+1+","+msg.z+")";
    	  var d2 = document.getElementById("con3");
    	  d2.style.backgroundColor = "rgb("+msg.x+60+","+msg.y+","+msg.z+10+")";
    	  var d3 = document.getElementById("con4");
    	  d3.style.backgroundColor = "rgb("+msg.x+20+","+msg.y+","+msg.z+")";
      	  output(element);
      	  if(count>=50){
      		clear("");
      		count=1;
      	  }
	 });
	 
	socket.on('connect',function() {
       output('Client has success connected to the server!<br>');  
    });
    socket.on('connecting',function(){
       output('Connecting...');
    });
    socket.on('connect_failed',function(){
       output('Failed to connect');
    });
    socket.on('error',function(data){
        output('error.'+data);
    });
    socket.on('anything',function(data){
       output('anything'+data);
    });
    socket.on('message',function(data){
       output('message'+data);
    });
    socket.on('connect_failed',function(){
       output('Failed to connect');
    });

    /*socket.on('chatevent', function(data) {
        output('<span class="username-msg">' + data.userName + ' : </span>'
                + data.message);
    });*/
    
    socket.on('disconnect',function() {
        output('The client has disconnected!<br>');
    });
    function sendMsgToServer(){
    //json数据和单个值都可以，最多只能接受一个参数，除了actionname
    	socket.emit('getmsg',{name1:'actionkey-1',name2:'actionkey-2'});
    }
     socket.on('getmsg',function(msg1,msg2){
     	output("the msg from server!"+msg1+","+msg2+"<br>");
     });
    function sendDisconnect() {
        socket.disconnect();
    }
	function sendConnect() {
        socket.connect();
    }
	function output(msg) {		
        $('#log').append(msg);
    }
	function clear(msg) {		
        $('#log').html(msg);
    }
</script>
       <center><font color=red size=10>服务器屏蔽了js的调用，所以本页面暂时不提供推送测试结果查看 </font></center>
   <div style="width:100%;height:100%;border:solid red 0px;">
   <div  id='log' style="width:50%;height:90%;border:solid red 0px;float:left;">
   </div>
   <div style="width:48%;height:90%;border:solid red 0px;float:left;">
	   <center>--------------------------------</center><br>
	   <center>x=<span id='x'>0</span>,y=<span id='y'>0</span></center><br>
	   <center>--------------------------------</center><br>
	   <center><div id='con1' style="width:100;height:150;background-color:rgb(56,52,3)"></div></center><br>
	   <center><div id='con2' style="width:100;height:150;background-color:rgb(56,52,3)"></div></center><br>
	    <center><div id='con3' style="width:100;height:150;background-color:rgb(56,52,3)"></div></center><br>
	   <center><div id='con4' style="width:100;height:150;background-color:rgb(56,52,3)"></div></center><br>
   </div>
   <div id='xxd' style="width:100%;height:10%;border:solid red 0px;float:left;">
   	&nbsp;	&nbsp;	&nbsp;	&nbsp;主动请求并发送给服务器的消息
	<input id="content" type="text" value=""/>
	<input type="button" value="发送" onclick="sendMsgToServer()"/>
	<input type="button" value="断开连接" onclick="sendDisconnect()"/>
	<input type="button" value="连接" onclick="sendConnect()"/>
   </div>
   </div> 
  </body>
</html>
