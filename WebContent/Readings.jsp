<%-- 
    Document   : Readings
    Created on : 08-Mar-2015, 02:10:48
    Author     : Stifler
--%>

<%@page import="ju.ehealthservice.utils.ImageHandler"%>
<%@page import="ju.ehealthservice.utils.Constants"%>
<%@page import="ju.ehealthservice.beans.HealthService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String field = (String)request.getAttribute("field");
    HealthService ob  = (HealthService)request.getAttribute("patient");
    List<String> list;
    String img = "";
    System.out.println(ob);
    if(!ob.isEmpty()) {
        list = (List<String>)request.getAttribute("list");
        if(ob.isImage() == false) { 
            img = Constants.DEFAULT_IMAGE_PATH;
        } else {
            String imagePath = Constants.IMAGE_REPOSITORY_PATH + ob.getID() + ".jpg";
            byte[] imageBytes = ImageHandler.getBinData(imagePath);
            if(imageBytes != null) {
                img = ImageHandler.formatString(imageBytes);
            }
        }
    } else {
        list = new ArrayList<String>();
        img = Constants.DEFAULT_IMAGE_PATH;
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Readings</title>
        <link rel="shortcut icon" href="Images/Icon.ico" type="image/ico">
        <link rel="stylesheet" href="CSS/readings.css">
        <script type="text/javascript" src="JS/jquery-1.7.2.min.js"></script>
        <script src="JS/script2.js">
            
        </script>
        <style>
            input[type="file"] {
                color: transparent;
             }
        </style>
    </head>
    <body>
        <center>
            <table>
                <tr>
                    <td style="text-align: center;">
                        Readings of the <b>Patient</b> 
                        
                        <%
                            if(field.compareTo("from_to") == 0) {
                                out.print("from <b>" + (String)request.getAttribute("from") 
                                        + "</b> to <b>" + (String)request.getAttribute("to") + "</b>");
                            } else if(field.compareTo("show_all") == 0) {
                                out.print("till date");
                            } else {
                                out.print("during the last <b>" + (Integer)request.getAttribute("time") 
                                        + " " + field + "</b> are");
                            }
                        %>
                    </td>
                </tr>
                <tr>
                    <td >
                        <table style="table-layout: fixed;">
                            <tr>
                                <td style="border: none; padding: 2px;">ID : </td>
                                <td style="border: none; padding: 2px;width:220px;"><b><% out.print(ob.getID());%></b></td>
                                <td style="border: none;width:250px;text-align: center" rowspan="4">
                                    <img src="<%=img%>" id="img" height="80" width="80">
                                </td>
                                <td style="border: none;" rowspan="2">
                                    <form name="upload_image" id="upload_image" enctype="multipart/form-data">
                                        <input type="file" id="image_file" name="select_image" accept="image/*" multiple="false" value="NoImageAvailable">
                                    </form>
                                </td>
                            </tr>
                            <tr>
                                <td style="border: none; padding: 2px;">Name : </td>
                                <td style="border: none; padding: 2px;width:220px;"><b><% out.print(ob.getName());%></b></td>
                            </tr>
                            <tr>
                                <td style="border: none; padding: 2px;">Age : </td>
                                <td style="border: none; padding: 2px;width:220px;"><b><% out.print(ob.getAge());%></b></td>
                            </tr>
                            <tr>
                                <td style="border: none; padding: 2px;">Mobile No. : </td>
                                <td style="border: none; padding: 2px;width:220px;">
                                    <b><% out.print(ob.getMobile());%></b>
                                </td>
                                <td style="border: none;" rowspan="2">
                                    <input type="button" onclick="validateImage('<%=ob.getID()%>')" value="Set Image">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <%
                                            if(list.size() != 0) {
                                        %>
                        <table>
                            <tr>
                                <td>
                                    <center><b>Available Readings (Date_Time)</b></center>
                                    <div class="fit">
                                        <table>    
                                                <%
                                            
                                                    for(int t=0;t<list.size();t++) {
                                                        out.println("<tr><td style='text-align:justify'>");
                                                        String x = list.get(t);
                                                        out.println("<input type='radio' name='list' value='" + x 
                                                                + "' style='margin-right : 10px' "
                                                                + "onclick='enableButton(this)'>" + x
                                                            + "&nbsp;&nbsp;&nbsp;&nbsp;"
                                                                + "<input disabled name='view' id='" + x 
                                                                + "' type='button' value='View' onclick='viewData(this,"
                                                                + ob.getID()+ ")'>");
                                                        out.println("</td></tr>");
                                                    }
                                                %>
                                        </table>
                                    </div>
                                </td>
                                <td>
                                    <center><b>Reading Values</b></center>
                                    <div id="display" class="fit"></div>
                                </td>
                            </tr>
                        </table>
                                        <% } else {
                                                out.println("<center><b>No data available!</b></center>");
                                            }
                                            %>
                    </td>
                </tr>
            </table>
        </center>
    </body>
</html>
