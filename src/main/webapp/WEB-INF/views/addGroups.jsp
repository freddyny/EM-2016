<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>

       <title>Add Match Result</title>

</head>
<body>
    <h3>${msg}</h3>

    <p>Legg til kampresultat</p>

    <form:form method="POST" action="/addGroupsPOST">
       <table class="table groups-table">
           <thead>
               <tr>
                   <th>Group</th>
                   <th>First Place</th>
                   <th>Second Place </th>
                   <th>Third Place</th>
                   <th>Forth Place</th>
               </tr>
           </thead>
           <tbody></tbody>
       </table>
       <input class="btn btn-default" type="submit" value="Neste"/>

    </form:form>
    <script src="<c:url value="/js/addGroups.js"/>"></script>

</body>
</html>
