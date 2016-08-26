<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>

       <title>Ditt Spill</title>


</head>
<body>
    <h1>Ditt Spill</h1>
    <p>Dette er hva du har tippet. Hvis infoen ikke er riktig, kan du endre infoen ved å legge til nytt spill igjen.</p>

    <h2>Gruppespill</h2>
    <table class="table groupstage-table">
        <thead>
           <tr>
               <th>#</th>
               <th>Hjemmelag</th>
               <th>Bortelag</th>
               <th>Resultat</th>
               <th>HUB</th>
           </tr>
        </thead>
    <tbody></tbody>
    </table>

    <h2>Tipping av Gruppene</h2>
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
    <h2>Knockout</h2>
    <table class="table knockout-table">
        <thead>
           <tr>
               <th>#</th>
               <th>Hjemmelag</th>
               <th>Bortelag</th>
               <th>Resultat</th>
               <th>HUB</th>
           </tr>
        </thead>
    <tbody></tbody>
    </table>

    <h2>Winner</h2>
    <h3 class="winner"><h3>
    <div class="row">
        <div class = "col-md-6">
            <h3>Toppscorer</h3>
            <table class="table toppscorer-table">
                <thead>
                   <tr>
                       <th>#</th>
                       <th>Player</th>
                       <th>Goals</th>
                   </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
        <div class = "col-md-6">
            <h3>Best Player</h3>
        <table class="table best-player-table">
        <thead>
           <tr>
               <th>#</th>
               <th>Player</th>
           </tr>
        </thead>
            <tbody></tbody>
        </table>
        </div>
    </div>


    <script>
        var json = ${json};
        var kampoppsett = ${kampoppsett};

    </script>
    <script src="<c:url value="/js/completeBet.js"/>"></script>

</body>
</html>



