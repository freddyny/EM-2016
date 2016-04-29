<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>

       <title>Kampoppsett</title>
       <script src="<c:url value="/js/jquery1.11.1.min.js"/>"></script>
              <script src="<c:url value="/js/jquery.tablesorter.min.js"/>"></script>


</head>
<body>
    <h1>Kampoppsett</h1>

    <p>Oversikt over alle kampene spilt i EM.</p>
    <table id ="sortableTable"class=" table match-table">
       <thead>
           <tr>
               <th>#</th>
               <th>Kamptype</th>
               <th>Dato</th>
               <th>Klokken</th>
               <th>TV</th>
               <th>Hjemme</th>
               <th>Borte</th>

           </tr>
       </thead>
       <tbody></tbody>
    </table>

    <script>
        var json = ${data};
        var group =  "${nextGroupName}"
        var thisGroup = "${groupName}";
    </script>
    <script src="<c:url value="/js/kampoppsett.js"/>"></script>

</body>
</html>
