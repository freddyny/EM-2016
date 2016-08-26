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
    <p>Mer info om hvem som kan møtes, under next-knappen</p>
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

       <div class ="knockout-guess" style="display: none;">
            <p>Dette er det du tippet på forrige side.</p>
            <table class="table knockout-guess-table">
               <thead>
                   <tr>
                       <th>#</th>
                       <th>H-Lag</th>
                       <th>B-Lag</th>
                       <th>Resultat</th>
                       <th>HUB</th>
                   </tr>
               </thead>
               <tbody></tbody>
            </table>
       </div>


       <div class ="groups" style="display: none;">
           <div class="row">
               <div class = "col-md-4">
                   <h3>Group A</h3>
                   <table class="table groupA-table">
                       <thead>
                          <tr>
                              <th>#</th>
                              <th>Lag</th>
                          </tr>
                       </thead>
                   <tbody></tbody>
                   </table>

               </div>
               <div class = "col-md-4">
                   <h3>Group B</h3>
                   <table class="table groupB-table">
                       <thead>
                          <tr>
                              <th>#</th>
                              <th>Lag</th>
                          </tr>
                       </thead>
                   <tbody></tbody>
                   </table>

               </div>
               <div class = "col-md-4">
                   <h3>Group C</h3>
                   <table class="table groupC-table">
                       <thead>
                          <tr>
                              <th>#</th>
                              <th>Lag</th>
                          </tr>
                       </thead>
                   <tbody></tbody>
                   </table>

               </div>
           </div>

           <div class="row">
               <div class = "col-md-4">
                   <h3>Group D</h3>
                   <table class="table groupD-table">
                       <thead>
                          <tr>
                              <th>#</th>
                              <th>Lag</th>
                          </tr>
                       </thead>
                   <tbody></tbody>
                   </table>

               </div>
               <div class = "col-md-4">
                   <h3>Group E</h3>
                   <table class="table groupE-table">
                       <thead>
                          <tr>
                              <th>#</th>
                              <th>Lag</th>
                          </tr>
                       </thead>
                   <tbody></tbody>
                   </table>

               </div>
               <div class = "col-md-4">
                   <h3>Group F</h3>
                   <table class="table groupF-table">
                       <thead>
                          <tr>
                              <th>#</th>
                              <th>Lag</th>
                          </tr>
                       </thead>
                   <tbody></tbody>
                   </table>

               </div>
           </div>

       </div>

    </form:form>

    <script src='js/jquery-sortable.js'></script>
    <script>
        var json = ${json};
        var knockout = "${knockout}";
        var groups = ${groups};
        var lastGuess = ${lastGuess};

    </script>
    <script src="<c:url value="/js/knockout.js"/>"></script>
    <script src="<c:url value="/js/bootstrap-select.js"/>"></script>


</body>
</html>



