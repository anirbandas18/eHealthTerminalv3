<%-- 
    Document   : GUIMonitorPanel
    Created on : 17-Feb-2015, 17:10:22
    Author     : Stifler
--%>

<%@page import="java.util.Date"%>
<%@page import="ju.ehealthservice.utils.Constants"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_PICKER_FORMAT);
    Date now = new Date();
    String date = sdf.format(now);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="Images/Icon.ico" type="image/ico">
        <link rel="stylesheet" href="CSS/header-footer.css">
        <link rel="stylesheet" href="CSS/panel.css">
        <title>E-Health Terminal</title>
        <!--Noty-->
        <link href='http://fonts.googleapis.com/css?family=PT+Sans:regular,italic,bold,bolditalic&amp;subset=latin,latin-ext,cyrillic' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" type="text/css" href="CSS/buttons.css"/>
        <link rel="stylesheet" type="text/css" href="CSS/animate.css"/>
        <link rel="stylesheet" href="CSS/font-awesome.min.css"/>
        <script src="JS/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.noty.packaged.min.js"></script>
        
        <script type="text/javascript" src="JS/script1.js"></script>
    </head>
    <body onload="init()" onbeforeunload="terminateSystem()">
         <jsp:include page="HeaderPanel.jsp"></jsp:include>
        <center>
        <div class="div">
            <div style="position: absolute;top: 0px; left: -208px;">
                <img style="width: 750px; height: 333px;border: 1px solid rgb(153,204,255);" 
                     src="Images/luis_conectado_big.png">
            </div>
            <input style="position: absolute;left : 140px; top: 32px;width: 120px" type="button" 
                   onclick="recordAction()" id="cmd_record" value="BP Ready">
            <div style="position: absolute; left: 276px; top: 30px;height: 20px;width: 50px;">
                <% out.println("SYS : ");%>
            </div>
            <div id="sys" style="position: absolute; left: 321px; top: 30px;height: 20px;width: 37px;">
                <% out.println("0");%>
            </div>
            <div style="position: absolute; left: 276px; top: 50px;height: 20px;width: 50px;">
                <% out.println("DIA : ");%>
            </div>
            <div id="dia" style="position: absolute; left: 321px; top: 50px;height: 20px;width: 37px;">
                <% out.println("0");%>
            </div>
            <div style="position: absolute; left: 630px; top: 30px;height: 20px;width: 50px;">
                <% out.println("Mem : ");%>
            </div>
            <div id="mem" style="position: absolute; left: 680px; top: 30px;height: 20px;width: 50px;">
                <% out.println("0");%>
            </div>
            <div id="breathing_rate" style="cursor: pointer;position: absolute; left: 387px; top: 38px;height: 20px;width: 68px;">
                <% out.println("0");%>
            </div>
            <div style="position: absolute; left: 747px; top: 0px;height: 200px;width: 450px;">
                <canvas id="resp_rate_panel" height="200" width="400"></canvas>
            </div>
            <div id="position" style="position: absolute;left: 158px; top: 90px;height: 40px; width: 120px;">
                <% out.println("Idle");%>
            </div>
            <div id="ecg" style="cursor: pointer;position: absolute; left: 480px; top: 100px;height: 20px;width: 60px;">
                <% out.println("ECG");%>
            </div>
            <div style="position: absolute; left: 747px; top: 213px;height: 200px;width: 450px;">
                <canvas id="ecg_panel" height="200" width="400"></canvas>
            </div>
            <div style="position: absolute; left: 105px; top: 160px;height: 20px;width: 50px;">
                <% out.println("&nbsp;O2 : ");%>
            </div>
            <div id="o2" style="position: absolute; left: 150px; top: 160px;height: 20px;width: 50px;">
                <% out.println("0");%>
            </div>
            <div style="position: absolute; left: 105px; top: 185px;height: 20px;width: 50px;">
                <% out.println("BPM : ");%>
            </div>
            <div id="bpm" style="position: absolute; left: 150px; top: 185px;height: 20px;width: 50px;">
                <% out.println("0");%>
            </div>
            <div id="temperature" style="position: absolute; left: 147px; top: 275px;height: 30px;width: 40px;">
                <% out.print("0.0");%>
            </div>
            <div style="position: absolute; left: 189px; top: 275px;height: 30px;width: 25px;">
                <% out.print("°C");%>
            </div>
            <div style="position: absolute; left: 500px; top: 270px;height: 20px;width: 55px;">
                <% out.print("Cond : ");%>
            </div>
            <div id="cond" style="position: absolute; left: 555px; top: 270px;height: 20px;width: 60px;">
                <% out.print("0");%>
            </div>
            <div style="position: absolute; left: 500px; top: 290px;height: 20px;width: 55px;">
                <% out.print(" Res : ");%>
            </div>
            <div id="res" style="position: absolute; left: 555px; top: 290px;height: 20px;width: 60px;">
                <% out.print("0");%>
            </div>
            <input id="age" style="position: absolute;left: 250px; top: 370px; width: 70px;" type="number" placeholder="Age" min="1" onkeypress="return isNumericKey(event)">
            <input style="position: absolute;left: 330px; top: 370px;width:130px;" type="button" value="Reset Fields"  onclick="resetFields()">
            <div style="position: absolute;left :-258px;top : 398px;height: 20px;">
                Xively Post Interval : 
            </div>
            <input id="xively_int" style="position: absolute;left: 415px; top: 395px; width: 40px" type="text" value="5" onkeypress="return isNumericKey(event)">
            <input id="usb" style="position: absolute;left: 470px; top: 345px;height: 32px;width: 140px; text-align: center" type="button" value="Start Monitoring USB" onclick="monitorUSB(this)">
            <input id="wifi" style="position: absolute;left: 615px; top: 345px;height: 32px;width: 140px" type="button" value="Start Monitoring WiFi" onclick="monitorWiFi(this)">
            <input style="position: absolute;left: 470px; top: 383px;height: 32px;width: 140px" type="button" value="Start Xively Post" onclick="startXivelyFeed(this)">
            <input style="position: absolute;left: 615px; top: 383px;height: 32px;width: 140px" type="button" value="Save as XML" onclick="saveAsXML()">
            <div id="console" style="position: absolute;left : 2px; top: 430px;width: 1168px; height: 20px;border: 1px solid rgb(153,204,255); text-align: center;" >
                Console
            </div>
            
            <form name="search_form" action="ViewDataByID" method="post"  target="_blank">
                <input style="position: absolute;left: 95px; top: 345px; width:145px;" type="button" value="View Data by ID" onclick="checkID()">
                <input style="position: absolute;left: 330px; top: 345px;width:130px;" type="button" value="View Data by Name"  onclick="checkName()">
                <input id="name" name="patient_name" style="position: absolute;left: 250px; top: 345px; width: 70px;" type="text" placeholder="Name" onkeypress="return isAlphabetKey(event)">
                <input required id="patient_id" name="patient_id" style="position: absolute;left: 2px; top: 345px; width: 85px;" type="number" placeholder="Patient ID" min="1" onkeypress="return isNumericKey(event)">
                <div style="position: absolute; left : 1px; top: 370px;height: 20px;width: 80px;">
                    Time Int : 
                </div>
                <input id="time_int" name="time_int" disabled style="position: absolute;left: 95px; top: 370px; width: 40px" type="number" value="1" min="1" onkeypress="return isNumberKey(this.event)">
                <input disabled id="from" name="from" type="date" style="position: absolute;left: 2px; top: 395px;width:110px" max='<%=date%>' onchange="">
                <input disabled id="to" name="to" type="date" style="position: absolute;left: 125px; top: 395px;width:110px" max='<%=date%>'>
                <select id="field" name="field" style="position: absolute;left: 145px; top: 370px; width: 95px" onclick="enableFields(this)">
                    <option value="show_all">SHOW ALL</option>
                    <option value="from_to">FROM TO</option>
                    <option value="years">YEAR(S)</option>
                    <option value="days">DAY(S)</option>
                    <option value="hours">HOUR(S)</option>
                    <option value="minutes">MINUTE(S)</option>
                </select>
            </form>
        </div>
        </center>
        <jsp:include page="FooterPanel.jsp"></jsp:include>
    </body>
</html>