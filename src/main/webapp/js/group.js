// If we receive some data from our server in JSON,
// we can easily render this in our HTML using jQuery.
//
// Let's say we receive this data:
/*var data = { match: [{
    matchNumber: 1,
    dateTime: new Date("2014-06-12 22:00:00"),
    tvChannel: "TV 2",
    homeTeam: "Brasil",
    awayTeam: "Kroatia"
}, {
    matchNumber: 2,
    dateTime: new Date("2014-06-13 18:00:00"),
    tvChannel: "NRK",
    homeTeam: "Mexico",
    awayTeam: "Kamerun"
}, {
    matchNumber: 3,
    dateTime: new Date("2014-06-13 21:00:00"),
    tvChannel: "NRK",
    homeTeam: "Spania",
    awayTeam: "Nederland"
}, {
    matchNumber: 4,
    dateTime: new Date("2014-06-13 23:59:00"),
    tvChannel: "TV 2",
    homeTeam: "Chile",
    awayTeam: "Australia"
}]}*/

// Let's also not forget to implement our date formatters,
// see http://www.w3schools.com/jsref/jsref_obj_date.asp
// for docs.
$(function  () {
  $("ol.sort-teams").sortable();
});

$('input[name="nextGroup"]').val(group);

function getDateString(date) {
    var year = date.getFullYear();
    var month = date.getMonth() + 1; // 0-indexed
    var day = date.getDate();
    return [day, month, year].join('.'); // The cool kids' way
    // The plebs' way is: day + '.' + month + '.' + year;
}

function getTimeString(date) {
    var hours = date.getHours();
    var minutes = date.getMinutes();
    return hours + ':' + minutes;
}

// Our functions will not "pad" our numbers, if you want
// the date 12.6.2014 to display 12.06.2014 you will need
// to improve the function, e.g.
function improvedGetDateString(date) {
    var year = date.getFullYear();
    var month = ('0' + (date.getMonth() + 1)).slice(-2);
    var day = ('0' + date.getDate()).slice(-2);
    return [day, month, year].join('.'); // The cool kids' way
}

function improvedGetTimeString(date) {
    var hours = ('0' + date.getHours()).slice(-2);
    var minutes = ('0' + date.getMinutes()).slice(-2);
    return hours + ':' + minutes;
}
// Of course, normally we would receive this as a string:
var jsonData = JSON.stringify(json);

// Now we can render our data using for instance a function:
renderData(jsonData);

addSortTeams(jsonData);

function addSortTeams(jsonData) {
    var groupData = JSON.parse(jsonData);
    var teams = groupData.teams;
    console.log("ER I SORT TEAMS: " + teams);
    for(var i = 0; i < teams.length; i++) {
        var team = teams[i]
        //<li class="list-group-item ">Albania</li>
        var li = $('<li>').addClass('list-group-item');
        li.append(team);
        $("ol.sort-teams").append(li);
    }

}

function renderData(jsonData) {
    // First parse it from json:
    var groupData = JSON.parse(jsonData);
    var matches = groupData.match;
    console.debug(matches[0].dateTime);
    // Check your console! You will see that the date
    // of each match is just a string! We want to work
    // with JavaScript Date objects, so let's do a quick
    // map! (This is a bit complex, you can either ignore
    // it or read some docs at:
    // http://www.w3schools.com/jsref/jsref_map.asp.
    // It's pretty cool though, probably worth it!)

    matches.map(function(match) {
        match.dateTime = new Date(match.dateTime);
    })

    // Okay, furthermore: we know this is a list,
    // which means we can iterate over it the "easy" way:
    for(var i = 0; i < matches.length; i++) {
        // Now let's retrieve each match and work
        // with them individually:
        var match = matches[i];
        //alert(match.homeTeam);

        // In order to render the match, we must
        // create a jQuery element, more specifically
        // a <tr> node.
        var tr = $('<tr>');

        // Now we can append the data from 'match':
        var matchNr = $('<td>').addClass('match-nr')
            .append(match.matchNumber);
        tr.append(matchNr);

        // We should separate our logic in order to
        // extract our date and time, we can do this
        // by creating two additional functions,
        // getDateString(date) and getTimeString(date)
        tr.append('<td>'+improvedGetDateString(match.dateTime)+'</td>');
        tr.append('<td>'+improvedGetTimeString(match.dateTime)+'</td>');

        // Previously, we have appended each 'td' simply with strings,
        // we can also create them as nodes before appending:


        // Or in one line:
        tr.append($('<td>').append(match.homeTeam));
        tr.append($('<td>').append(match.awayTeam));

        // For more complex html, a good idea is to create each
        // node for readability:
        var tdResult = $('<td>');
        var homeGoalsInput = $('<input>');

        // We set attributes to the input usning .attr(field, value)
        homeGoalsInput.attr('type', 'number').attr('name', 'home-goals');

        // Classes are added using .addClass()
        homeGoalsInput.addClass('form-control');
        var homeGoalsTd = homeGoalsInput;
        // All of these can of course be chained:
        var awayGoalsInput = $('<input>')
            .attr('type', 'number')
            .attr('name', 'away-goals')
            .addClass('form-control');

        // Note that even though we created the input elements,
        // they have not yet been appended to our table row!
        tdResult
            .append(homeGoalsTd)
            .append('-') // Don't forget the separator ;)
            .append(awayGoalsInput);

        tr.append(tdResult);
        /*var radioboxDiv = $('<div>')
            .addClass("radio-div");


        var checkboxHomeLabel = $('<label>')
            .addClass('radio-inLine active');




        var checkBoxHomeInput = $('<input>')
            .attr('type','radio')
            .attr('name','HUB'+i)
            .attr('value','H')
            .attr('checked','');

        var checkboxULabel = $('<label>')
            .addClass('radio-inLine');

        var checkBoxUInput = $('<input>')
            .attr('type','radio')
            .attr('name','HUB'+i)
            .attr('value','H');

        var checkboxAwayLabel = $('<label>')
                    .addClass('radio-inLine');

        var checkBoxAwayInput = $('<input>')
            .attr('type','radio')
            .attr('name','HUB'+i)
            .attr('value','H');



        radioboxDiv.append(checkboxHomeLabel.append(checkBoxHomeInput).append('H'))
            .append(checkboxULabel.append(checkBoxUInput).append('U'))
            .append(checkboxAwayLabel.append(checkBoxAwayInput).append('B'));
        tr.append($('<td>').append(radioboxDiv));


        */
        var HUB = $('<td>').addClass('HUB');
        ['H','U','B'].forEach(function(teamValue) {
            var radio = $('<input>')
                .attr('type', 'radio')
                .attr('name', 'HUBRadio-'+i)
                .attr('value', teamValue);

            var label = $('<label>')
                .addClass('radio-inline')
                .append(radio)
                .append(teamValue);

            HUB.append(label);
        });
        tr.append(HUB);

        // And lastly, we must not forget to append our table row
        // to our table, this should be placed withing <tbody>
        $("table.match-table > tbody").append(tr);
    }
}







// Code from #1 below:
$('.input_JSON').bind('click', getDataAndSend);
function getDataAndSend() {
    console.debug("ER I GETDATAANDSEND");
    var bettingData = [];
    $("table.match-table > tbody > tr").each(function(i) {
        var matchBet = {};
        var tr = $(this);
        matchBet.matchNumber = tr.find('.match-nr').text();
        matchBet.homeGoals = tr.find('input[name="home-goals"]').val();
        matchBet.awayGoals = tr.find('input[name="away-goals"]').val();
        matchBet.HUB = tr.find('input[name="HUBRadio-'+i+'"]:checked').val();
        bettingData.push(matchBet);

    });
    group = {};
    group.matches = bettingData;
    var json = JSON.stringify(group);
    console.debug(group);
    $('input[name="group"]').val(group);
}

$('.group-button').bind('click', getDataAndSend);
function getDataAndSend() {
    console.debug("ER I GETDATAANDSEND");
    var bettingData = [];
    var validated = true;
    $("table.match-table > tbody > tr").each(function(i) {
        var matchBet = {};
        var tr = $(this);
        matchBet.matchNumber = tr.find('.match-nr').text();
        matchBet.homeGoals = tr.find('input[name="home-goals"]').val();
        matchBet.awayGoals = tr.find('input[name="away-goals"]').val();
        matchBet.HUB = tr.find('input[name="HUBRadio-'+i+'"]:checked').val();
        bettingData.push(matchBet);

    });
    teams = [];
    $( "ol li" ).each(function( index ) {
        teams.push($( this ).text());
    });
    group = {};
    group.matches = bettingData;
    group.teams = teams;
    group.name = thisGroup;
    var json = JSON.stringify(group);
    console.debug(json);
    $('input[name="group"]').val(json);
}



