<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>

       <title>Gruppe ${groupName}</title>


</head>
<body>
    <h1>Gruppe ${groupName}</h1>
    <p>Legg til resultater, sorter gruppen og trykk på neste.</p>
    <p> Man får 2 poeng for riktig resultat og 1 poeng for riktig HUB</p>
    <form:form method="POST" class = "groupForm" action="/group">
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
       <input type="hidden" class="input_JSON" name="group" required />
       <input type="hidden" class="nextGroup" name="nextGroup" value="" />

       <p>Sorter listen i den rekkefølgen du tror gruppen ender</p>
       <p>Du får 2 poeng for hvert riktig plassert lag.</p>
           <ol class='sort-teams list-group'>
           </ol>

       <button class="btn btn-default group-button" type="submit" >Neste</button>
    </form:form>



    <script src='js/jquery-sortable.js'></script>
    <script>
        var json = ${json};
        var group =  "${nextGroupName}"
        var thisGroup = "${groupName}";
    </script>
    <script src="<c:url value="/js/group.js"/>"></script>

</body>
</html>



