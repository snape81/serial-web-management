<%--
  Created by IntelliJ IDEA.
  User: claudio
  Date: 04/06/12
  Time: 11.06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% response.setHeader("Access-Control-Allow-Origin", "http://10.1.0.83:8080");%>
<html>
<head><title>Web Socket Test</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
    <link href="http://giocodigitale.nexse.com:11111/betbull/css/style.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<script type="text/javascript">
    var socket;
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        socket = new WebSocket("ws://10.1.0.83:8080/websocket");
        socket.onmessage = function (event) {


            var ta = document.getElementById('responseText');
            var lr = document.getElementById('lastRead');

            if (event.data && event.data.indexOf("PRINTED") < 0) {
                // dato letto
                var markSenseCard = jQuery.parseJSON(event.data);


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
        socket.onopen = function (event) {
            var ta = document.getElementById('responseText');
            ta.value = "Web Socket opened!";
        };
        socket.onclose = function (event) {
            var ta = document.getElementById('responseText');
            ta.value = ta.value + "Web Socket closed";
        };
    } else {
        alert("Your browser does not support Web Socket.");
    }

    function send(message) {
        if (!window.WebSocket) {
            return;
        }
        if (socket.readyState == WebSocket.OPEN) {
            socket.send(message);
        } else {
            alert("The socket is not open.");
        }
    }

    function testSchedina() {
        var contentent = "";
        for (i =1; i < 59 ;i++ ){
          contentent += '<div id="row'+ i +'" class="row">';
          for (j = 1; j < 15 ;j++ ) {
              var indiceRiga;
              if (j == 1 ) {
                  indiceRiga = 1;
              } else
              if ( j > 1 && j < 5) {
                  indiceRiga = 2;

              } else if (j > 4 && j < 9 ) {
                 indiceRiga = 3;
              } else if (j > 8 && j < 11) {
                 indiceRiga = 2;
              } else {
                  indiceRiga = 3
              }

               contentent +=  '<div class="cell cel_'+indiceRiga+' selected"></div>'
          }
          contentent += '</div>';
        }
        document.getElementById("gridContainer").innerHTML = contentent;
        document.getElementById("gridContainer").style.display ="block";
    }
</script>


<div style="display: block; width: 600px; float: left;">
    <form onsubmit="return false;">
        <h3>Last Data Read </h3>
        <textarea readonly="readonly" id="lastRead" style="width: 600px; height:50px;"
                  name="message"></textarea><br/><br/>
        <input type="button" value="PRINT" onclick="send(this.form.message.value)"/>
        Automatic print: <input type="checkbox" name="autoprint" id="autoprint"/>
        <input type="button" value="FILL" onclick="testSchedina()"/>

        <h3>Log AREA</h3>
        <textarea readonly="readonly" id="responseText" style="width: 500px; height:300px;"></textarea>
    </form>
</div>

<div class="schedina">
<div class="grid" style="display: none;" id="gridContainer">

<div id="row1" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>

<div id="row2" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row3" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row4" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row5" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row6" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row7" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row8" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row9" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row10" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row11" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row12" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row13" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row14" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row15" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row16" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id=17 class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row18" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row19" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row20" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row21" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row22" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row23" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row24" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row25" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row26" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row27" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row28" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row29" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row30" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row31" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row32" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row33" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row34" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row35" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row36" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row37" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row38" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row39" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row40" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row41" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row42" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row43" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>

<div id="row44" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row45" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row46" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row47" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row48" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row49" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row50" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row51" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row52" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row53" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row54" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row55" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row56" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row57" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
<div id="row58" class="row">
    <div class="cell cel_1"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_2"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
    <div class="cell cel_3"></div>
</div>
</div>
</div>

</body>
</html>