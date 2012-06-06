<%--
  Created by IntelliJ IDEA.
  User: claudio
  Date: 04/06/12
  Time: 11.06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");%>
<html><head><title>Web Socket Test</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"></script>

</head>
<body>
<script type="text/javascript">
var socket;
if (!window.WebSocket) {
  window.WebSocket = window.MozWebSocket;
}
if (window.WebSocket) {
  socket = new WebSocket("ws://10.1.0.83:8080/websocket");
  socket.onmessage = function(event) {


      var ta = document.getElementById('responseText');
      var lr = document.getElementById('lastRead');

      if (event.data && event.data.indexOf("PRINTED") <0) {
         // dato letto
          var markSenseCard =  jQuery.parseJSON(event.data);


                lr.value = markSenseCard.rawString;

                if (document.getElementById('autoprint').checked == true) {

                    send(markSenseCard.rawString);

                }
          if (markSenseCard.empty) {
              ta.value = ta.value + '\n' + markSenseCard.rawString + "(schedina vuota)";
          } else {
              ta.value = ta.value + '\n' + markSenseCard.rawString;
          }

      } else {
          ta.value = ta.value + '\n' + event.data
      }





  };
  socket.onopen = function(event) { var ta = document.getElementById('responseText'); ta.value = "Web Socket opened!"; };
  socket.onclose = function(event) { var ta = document.getElementById('responseText'); ta.value = ta.value + "Web Socket closed"; };
} else {
  alert("Your browser does not support Web Socket.");
}

function send(message) {
  if (!window.WebSocket) { return; }
  if (socket.readyState == WebSocket.OPEN) {
    socket.send(message);
  } else {
    alert("The socket is not open.");
  }
}
</script>
<form onsubmit="return false;">
<h3>Last Data Read </h3>
<textarea readonly="readonly" id="lastRead" style="width: 600px; height:50px;" name="message"></textarea><br/><br/>
<input type="button" value="PRINT" onclick="send(this.form.message.value)" />
Automatic print: <input type="checkbox" name="autoprint" id="autoprint"/>
<h3>Log AREA</h3>
<textarea readonly="readonly" id="responseText" style="width: 500px; height:300px;"></textarea>
</form>
</body>
</html>