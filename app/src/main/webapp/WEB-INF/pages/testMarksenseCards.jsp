<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Web Socket Test</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
    <link href="/betbull/static/css/style.css" rel="stylesheet" type="text/css"/>

</head>
<body>

<div style="display: none">
    <div id="sportwetteTemplate" >
    <h3> SPORTWETTE </h3>
            <h4> Stake <span id="stakeMsc1" style="color: blue;" ></span> &euro;</h4>
            <table class="sportwette" id="sportwetteTable">
                <thead>
                <tr>
                    <th> # </th>
                    <th>kombi</th>
                    <th>Programm</th>
                    <th>Bet Number</th>
                    <th>Description</th>
                    <th>Specials</th>
                    <th>Kurs</th>
                    <th>Ergebniswette</th>
                    <th>bank</th>
                    <th>odd</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>

</div>
</div>
<script type="text/javascript">


    var socket;
    var markSenseCard;
    var PRE_RIGA_VUOTA = '<div id="row';

    var POST_RIGA_VUOTA = '" class="row">' +
            '<div class="cell cel_1 "></div>' +
            '<div class="cell cel_2 "></div>' +
            '<div class="cell cel_2 "></div>' +
            '<div class="cell cel_2 "></div>' +
            '<div class="cell cel_3 "></div>' +
            '<div class="cell cel_3 "></div>' +
            '<div class="cell cel_3 "></div>' +
            '<div class="cell cel_3 "></div>' +
            '<div class="cell cel_2 "></div>' +
            '<div class="cell cel_2 "></div>' +
            '<div class="cell cel_3 "></div>' +
            '<div class="cell cel_3 "></div>' +
            '<div class="cell cel_3 "></div>' +
            '<div class="cell cel_3 "></div></div>';

    function getRigaVuota(index) {
        return PRE_RIGA_VUOTA + index + POST_RIGA_VUOTA;
    }

    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        socket = new WebSocket("ws://10.0.0.221:9090/websocket");
        socket.onmessage = function (event) {



            var ta = document.getElementById('responseText');
            var lr = document.getElementById('lastRead');

            if (event.data && event.data.indexOf("Printing Result") < 0) {
                $("#marksensecarddetails").show();
                           $("#marksensecarddetails").empty();
                // dato letto
                markSenseCard = jQuery.parseJSON(event.data);
                document.getElementById("schedinaContainer").style.display = "none";
                // $("#schedinaContainer").removeClass("schedinawette");
                // $("#schedinaContainer").removeClass("schedina2");
                if (markSenseCard.valid) {

                lr.value = markSenseCard.rawString;

                if (document.getElementById('autoprint').checked == true) {

                    print();

                }

                var numeroTotaleRighe;
                if (markSenseCard.typeStr == "013E30") {
                    // sportwette
                    $("#schedinaContainer").removeClass("schedina2");
                    $("#schedinaContainer").removeClass("schedina3");
                    $("#schedinaContainer").removeClass("schedina4");

                    $("#schedinaContainer").addClass("schedinawette");
                    numeroTotaleRighe = 58;

                    $("#sportwetteTemplate").clone().attr("id","clone1").appendTo("#marksensecarddetails");
                    $("div#clone1 span#stakeMsc1").text(markSenseCard.stake);



                    for (var k = 0; k < 10; k++) {
                         var currentBettedEvent = markSenseCard.bettedEvents[k];
                        if (currentBettedEvent.ticked) {

                            $("div#clone1 table#sportwetteTable").append(
                                        $('<tr>').append($('<td>').text(k + 1),
                                                $('<td>').text(currentBettedEvent.combiChecked ? 'X' : '-'),
                                                $('<td class="right">').text(currentBettedEvent.programmTick != '' ? currentBettedEvent.programmTick : '-'),
                                                $('<td class="right">').text(currentBettedEvent.marketCode != '' ? currentBettedEvent.marketCode : '-'),
                                                $('<td>').text(currentBettedEvent.presentOnDB ?(currentBettedEvent.team1 + ' vs. ' + currentBettedEvent.team2):'-'),
                                                $('<td>').text(currentBettedEvent.specials != '' ? currentBettedEvent.specials : '-'),
                                                $('<td>').text(currentBettedEvent.kurs != '' ? currentBettedEvent.kurs : '-'),
                                                $('<td>').text((currentBettedEvent.ergebnieswetteA != '' ? currentBettedEvent.ergebnieswetteA : '-') + ' : ' + (currentBettedEvent.ergebnieswetteB != '' ? currentBettedEvent.ergebnieswetteB : '-')),
                                                $('<td>').text(currentBettedEvent.bankChecked ? 'X' : '-'),
                                                $('<td>').text(currentBettedEvent.presentOnDB ? currentBettedEvent.odd :' - ')).css({'background-color':currentBettedEvent.presentOnDB ? 'white':'red'}).css({'color':currentBettedEvent.presentOnDB ? 'black':'white'})

                                )


                        }

                    }
                    $("div#clone1 table#sportwetteTable").append($('<tr><td colspan="10"></td></tr>'));
                    $("div#clone1 table#sportwetteTable").append($('<tr style="background-color:#f0ffff;"><td colspan="7" style="text-align: right;padding-right:5px"  >Total Cost <br/> Total Winning </td><td colspan="3" style="text-align: right;padding-right:5px" >'+(markSenseCard.markSenseValid ? markSenseCard.totalCost : ' - ')+' &euro;<br/>'+(markSenseCard.markSenseValid ? markSenseCard.totalwinning : ' - ')+' &euro;</td></tr>'));
                    $("#marksensecarddetails").show();

                } else if (markSenseCard.typeStr == "013630") {
                    // schedina2
                    $("#schedinaContainer").removeClass("schedinawette");
                    $("#schedinaContainer").removeClass("schedina3");
                    $("#schedinaContainer").removeClass("schedina4");
                    $("#schedinaContainer").addClass("schedina2");
                    numeroTotaleRighe = 58;
                } else if (markSenseCard.typeStr == "011D00") {
                    //schedina3
                    $("#schedinaContainer").removeClass("schedinawette");
                    $("#schedinaContainer").removeClass("schedina2");
                    $("#schedinaContainer").removeClass("schedina4");
                    $("#schedinaContainer").addClass("schedina3");
                    numeroTotaleRighe = 43;
                } else if (markSenseCard.typeStr == "010230") {
                    //schedina3
                    $("#schedinaContainer").removeClass("schedinawette");
                    $("#schedinaContainer").removeClass("schedina2");
                    $("#schedinaContainer").removeClass("schedina3");
                    $("#schedinaContainer").addClass("schedina4");
                    numeroTotaleRighe = 40;
                }


                if (markSenseCard.empty) {

                    ta.value = ta.value + '\n' + markSenseCard.rawString + "(schedina vuota)";
                    document.getElementById("gridContainer").innerHTML = '';

                } else {
                    ta.value = ta.value + '\n' + markSenseCard.rawString;
                    // caricare immagine background corretta
                    var grid = "";
                    grid += getRigaVuota(1);
                    for (var i = 2; i <= numeroTotaleRighe; i++) {
                        if (markSenseCard.listaRighe[i]) {
                            grid += generateRowSchedina(i, markSenseCard.listaRighe[i].markedCells);
                        } else {
                            grid += getRigaVuota(i);
                        }
                    }
                    document.getElementById("gridContainer").innerHTML = grid;
                }
                document.getElementById("schedinaContainer").style.display = "block";
                } else {
                    $("#schedinaContainer").removeClass("schedinawette");
                                      $("#schedinaContainer").removeClass("schedina2");
                                      $("#schedinaContainer").removeClass("schedina3");
                                      $("#schedinaContainer").removeClass("schedina4");
                    document.getElementById("gridContainer").innerHTML = '';
                    document.getElementById("schedinaContainer").style.display = "none";
                    ta.value = ta.value + '\n' + "BAD READING - PLEASE RETRY";
                }

            } else {
                ta.value = ta.value + '\n' + event.data
            }


        };
        socket.onopen = function (event) {
            var ta = document.getElementById('responseText');
            ta.value = "Web Socket opened!";
            document.getElementById("lastRead").value = "";
        };
        socket.onclose = function (event) {
            var ta = document.getElementById('responseText');
            ta.value = ta.value + "Web Socket closed";
            document.getElementById("lastRead").value = "";
        };
    } else {
        alert("Your browser does not support Web Socket.");
    }

    function print() {
        var ta = document.getElementById('responseText');
        if (!markSenseCard.empty) {
            if (markSenseCard.typeStr == "013E30") {
                if (markSenseCard.valid) {
                    send("SPORTWETTE");

                } else {
                    ta.value = ta.value + '\n' + " INVALID SPORTWETTEN MARKSENSE CARD";
                }


            } else {
                send(markSenseCard.rawString);
            }
        } else {
            ta.value = ta.value + '\n' + " EMPTY MARKSENSECARD";
        }
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

    function generateRowSchedina(index, arrayCells) {
        var contentent = "";

        contentent += '<div id="row' + index + '" class="row">';
        for (var j = 1; j < 15; j++) {
            var indiceRiga;
            if (j == 1) {
                indiceRiga = 1;
            } else if (j > 1 && j < 5) {
                indiceRiga = 2;

            } else if (j > 4 && j < 9) {
                indiceRiga = 3;
            } else if (j > 8 && j < 11) {
                indiceRiga = 2;
            } else {
                indiceRiga = 3
            }
            var selectedToken = "";

            if (arrayCells[j - 1] == true) {
                selectedToken = 'selected';
            }

            contentent += '<div class="cell cel_' + indiceRiga + ' ' + selectedToken + '"></div>';
        }
        contentent += '</div>';
        return contentent;
    }


</script>


<div style="display: block; width: auto;margin-right:15px; float: left;">
    <form onsubmit="return false;">
        <h3>Last Data Read </h3>
        <textarea readonly="readonly" id="lastRead" style="width: 600px; height:50px;"
                  name="message"></textarea><br/><br/>

        <input type="button" value="PRINT" onclick="print();"/>
        Automatic print: <input type="checkbox" name="autoprint" id="autoprint"/>

        <h3>Log AREA</h3>
        <textarea readonly="readonly" id="responseText" style="width: 500px; height:300px;"></textarea>
    </form>

    <div id="marksensecarddetails" style="display: none">


    </div>
</div>



<script type="text/javascript">

</script>

<div class="schedina" id="schedinaContainer" style="display: none;">
    <div class="grid" id="gridContainer">

    </div>
</div>

</body>
</html>