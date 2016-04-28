<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>

       <title>Gruppe ${groupName}</title>

</head>
<body>
    <h1>Gruppe ${groupName}</h1>
    <p>Legg til resultater, sorter gruppen og trykk på neste. </p>
    <form:form method="POST" class = "groupForm" action="/group${nextGroupName}">
       <table class="table match-table">
           <thead>
               <tr>
                   <th>#</th>
                   <th>Dato</th>
                   <th>Klokken</th>
                   <th>Hjemme</th>
                   <th>Borte</th>
                   <th>Resultat</th>
                   <th>HUB</th>
               </tr>
           </thead>
           <tbody></tbody>
       </table>
       <input type="hidden" class="input_JSON" name="group" value="" />
       <button class="btn btn-default" class="group-button" type="submit" name="group" >Neste</button>
    </form:form>
    <button class="btn btn-default group-test">TEEST</button>
    <script>
        var json = ${json};
        var group =  "${nextGroupName}";
    </script>
    <script src="<c:url value="/js/group.js"/>"></script>

</body>
</html>



