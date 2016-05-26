<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>

       <title>${knockout}</title>


</head>
<body>
    <h1>${knockout}</h1>
    <p>Legg til resultater, sorter gruppen og trykk på neste.</p>
    <p>Man må ha begge lag riktig for å få poeng for resultat og HUB.</p>
    <form:form method="POST" class = "groupForm" action="/knockout">
       <table class="table match-table">
           <thead>
               <tr>
                   <th>#</th>
                   <th>H-Lag</th>
                   <th>B-Lag</th>
                   <th>Hjemme</th>
                   <th>Borte</th>
                   <th>Resultat</th>
                   <th>HUB</th>
               </tr>
           </thead>
           <tbody></tbody>
       </table>
       <input type="hidden" class="input_JSON" name="group" required />
       <input type="hidden" class="nextKnockout" name="nextKnockout" value="${nextKnockout}" />


       <button class="btn btn-default knockout-button" type="submit" >Neste</button>
    </form:form>

    <script src='js/jquery-sortable.js'></script>
    <script>
        var json = ${json};
        var knockout = "${knockout}";
    </script>
    <script src="<c:url value="/js/knockout.js"/>"></script>
    <script src="<c:url value="/js/bootstrap-select.js"/>"></script>


</body>
</html>



