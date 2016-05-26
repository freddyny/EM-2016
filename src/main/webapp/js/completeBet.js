

// Of course, normally we would receive this as a string:
var jsonData = JSON.stringify(json);
var setUpData = JSON.stringify(kampoppsett);


// Now we can render our data using for instance a function:
renderData(jsonData,setUpData);

function renderData(jsonData,setUpData) {
    // First parse it from json:
    var betData = JSON.parse(jsonData);
    var matchData = JSON.parse(setUpData);
    var matches = matchData.match;
    var groupMatches = betData.groupmatches;
    var knockoutMatches = betData.knockoutMatches;
    let homeTeam;
    let awayTeam;
    let matchnr;
    homeTeams = {};
    awayTeams = {};


    for(var i = 0; i < matches.length; i++) {
        setUpMatch = matches[i];
        matchnr = setUpMatch.matchNumber;
        homeTeam = setUpMatch.homeTeam;
        awayTeam = setUpMatch.awayTeam;

        homeTeams[parseInt(matchnr)] = homeTeam;
        awayTeams[parseInt(matchnr)] = awayTeam;

    }



    for(var i = 0; i < groupMatches.length; i++) {
                       /*<th>#</th>
                       <th>Hjemmelag</th>
                       <th>Bortelag</th>
                       <th>Resultat</th>
                       <th>HUB</th>*/
        console.debug(i);
        var match = groupMatches[i];
        var tr = $('<tr>');
        var matchNr = $('<td>').addClass('match-nr').append(match.matchNumber);

        homeTeam = homeTeams[i+1];
        awayTeam = awayTeams[i+1];
        console.debug(homeTeam + " VS " + awayTeam);

        var homeTeamTD = $('<td>').addClass('homeTeam').append(homeTeam);
        var awayTeamTD = $('<td>').addClass('awayTeam').append(awayTeam);


        tr.append(matchNr);
        tr.append(homeTeamTD);
        tr.append(awayTeamTD);

        var result = "" + match.homeGoals + "-" + match.awayGoals;
        var resultTd = $('<td>').addClass('result').append(result);
        tr.append(resultTd);

        var HUBTd = $('<td>').addClass('HUB').append(match.HUB)
        tr.append(HUBTd);
        // And lastly, we must not forget to append our table row
        // to our table, this should be placed withing <tbody>
        $("table.groupstage-table > tbody").append(tr);
    }
    for(var i = 0; i < knockoutMatches.length; i++) {
                       /*<th>#</th>
                       <th>Hjemmelag</th>
                       <th>Bortelag</th>
                       <th>Resultat</th>
                       <th>HUB</th>*/
        var kmatch = knockoutMatches[i];
        var tr = $('<tr>');
        var matchNr = $('<td>').addClass('match-nr').append(kmatch.matchNumber);

        homeTeam = kmatch.homeTeam;
        awayTeam = kmatch.awayTeam;
        console.debug(homeTeam + " VS " + awayTeam);

        var homeTeamTD = $('<td>').addClass('homeTeam').append(homeTeam);
        var awayTeamTD = $('<td>').addClass('awayTeam').append(awayTeam);


        tr.append(matchNr);
        tr.append(homeTeamTD);
        tr.append(awayTeamTD);

        var result = "" + kmatch.homeGoals + "-" + kmatch.awayGoals;
        var resultTd = $('<td>').addClass('result').append(result);
        tr.append(resultTd);

        var HUBTd = $('<td>').addClass('HUB').append(kmatch.HUB)
        tr.append(HUBTd);
        // And lastly, we must not forget to append our table row
        // to our table, this should be placed withing <tbody>
        $("table.knockout-table > tbody").append(tr);
    }
    $(".winner").append(betData.winner);


    var players = betData.players;

    var toppscorers = players.toppscorers;
    var bestPlayers = players.bestPlayer;

    for (var i = 0;i<toppscorers.length;i++){
        var toppscorer = toppscorers[i];
        //players":{"toppscorers":[{"number":1,"player":"dawk","goals":0},{"number":2,"player":"awdl","goals":2},{"number":3,"player":"adw","goals":0}],"bestPlayer":[{"number":1,"player":"daw"},{"number":2,"player":"dsa"},{"number":3,"player":"sad"}]},
        var tr = $('<tr>');

        var number = $('<td>').addClass('toppscorer-number').append(toppscorer.number);

        var player = $('<td>').addClass('toppscorer-player').append(toppscorer.player);
        var goals = $('<td>').addClass('toppscorer-goals').append(toppscorer.goals);


        tr.append(number);
        tr.append(player);
        tr.append(goals);
        $("table.toppscorer-table > tbody").append(tr);


    }
    for (var i = 0;i<bestPlayers.length;i++){
        var bestPlayer = bestPlayers[i];

        var tr = $('<tr>');

        var number = $('<td>').addClass('best-player-number').append(bestPlayer.number);
        var player = $('<td>').addClass('best-player').append(bestPlayer.player);
        tr.append(number);
        tr.append(player);


        $("table.best-player-table > tbody").append(tr);


    }
    var i = 0;
    var guess = betData.groupGuess;
    ['A','B','C','D','E','F'].forEach(function(teamValue) {
        var group = guess[i];
        for (var j = 0;j<group.length;j++){

            var tr = $('<tr>');
            var number = $('<td>').addClass('group'+teamValue+'-number').append(j+1);
            var team = $('<td>').addClass('group'+teamValue+'-team').append(group[j]);
            tr.append(number);
            tr.append(team);

            $("table.group"+teamValue+"-table > tbody").append(tr);


        }


        i++;


    });

}






