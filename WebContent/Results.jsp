<%-- 
    Document   : Results
    Created on : 02-Apr-2015, 00:54:06
    Author     : Stifler
--%>

<%@page import="ju.ehealthservice.utils.ImageHandler"%>
<%@page import="ju.ehealthservice.utils.Constants"%>
<%@page import="ju.ehealthservice.beans.HealthService"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ArrayList<HealthService> ob = (ArrayList<HealthService>)request.getAttribute("patient_list");
    String Name = (String)request.getAttribute("name");
    String field = (String)request.getAttribute("field");
    String from = "";
    String to = "";
    String img = "";
    int time = 0;
    if(field.compareTo("from_to") == 0) {
        from = (String)request.getAttribute("from") ;
        to = (String)request.getAttribute("to");
    } else if(field.compareTo("show_all") == 0) {
        //do nothing
    } else {
        time = (Integer)request.getAttribute("time"); 
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Results</title>
        <link rel="shortcut icon" href="Images/Icon.ico" type="image/ico">
         <link rel="stylesheet" href="CSS/readings.css">
         <style>
             th,td {
                 width: 600px;
             }
         </style>
         <script type="text/javascript">
             function submitForm() {
                 var form = document.getElementById("submit_name");
                 form.submit();
             }
         </script>
    </head>
    <body>
        <center>
            <table>
                <tr>
                    <th>
                        Search results for patients with name : <b><%=Name%></b>
                        <br>
                        <br>
                        Click on an option on the left to view details
                    </th>
                </tr>
                <% if(ob.size() > 0) { %>
                <form name="submit_name" id="submit_name" action="ParseSubmissionRequest" method="post">
                <% for(HealthService hs : ob) { %>
                <tr>
                    <td>
                        <table>
                            <tr>
                                <td style="width: 20px;border: none;">
                                    <input onclick="submitForm()" style="cursor: pointer" type="radio" name="patient_id" value="<%=hs.getID()%>">
                                </td>
                                <td style="border: none;">
                                    <table>
                                        <tr>
                                            <td style="border: none; padding: 2px;width:150px;">ID : </td>
                                            <td style="border: none; padding: 2px;"><b><% out.print(hs.getID());%></b></td>
                                        </tr>
                                        <tr>
                                            <td style="border: none; padding: 2px;width:150px;">Name : </td>
                                            <td style="border: none; padding: 2px;"><b><% out.print(hs.getName());%></b></td>
                                        </tr>
                                        <tr>
                                            <td style="border: none; padding: 2px;width:150px;">Age : </td>
                                            <td style="border: none; padding: 2px;"><b><% out.print(hs.getAge());%></b></td>
                                        </tr>
                                        <tr>
                                            <td style="border: none; padding: 2px;width:150px;">Mobile No. : </td>
                                            <td style="border: none; padding: 2px;"><b><% out.print(hs.getMobile());%></b></td>
                                        </tr>
                                    </table>
                                </td>
                                <td style="width: 100px;border: none;margin-top: 14px;">
                                    <%;
                                    if(hs.isImage() == false) { 
                                        img = Constants.DEFAULT_IMAGE_PATH;
                                    } else {
                                        String imagePath = Constants.IMAGE_REPOSITORY_PATH 
                                                + hs.getID() + ".jpg";
                                        byte[] imageBytes = ImageHandler.getBinData(imagePath);
                                        if(imageBytes != null) {
                                            img = ImageHandler.formatString(imageBytes);
                                        }
                                    }
                                    %>
                                    <img src="<%=img%>" width="80" height="80">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <% } %>
                    <input type="hidden" name="field" value="<%=field%>">
                    <input type="hidden" name="from" value="<%=from%>">
                    <input type="hidden" name="to" value="<%=to%>">
                    <input type="hidden" name="time_int" value="<%=time%>">
                </form>
                <% } else { %>
                <tr>
                    <td><h1>No records found in the system with such name!</h1></td>
                </tr>
                <% } %>
            </table>
        </center>
    </body>
</html>
