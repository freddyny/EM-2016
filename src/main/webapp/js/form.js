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
var jsonData = JSON.stringify(data);

// Now we can render our data using for instance a function:
renderData(jsonData);

function renderData(jsonData) {
    // First parse it from json:
    var matchData = JSON.parse(jsonData);
    var matches = matchData.match;

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
    console.debug(matchData);

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
        tr.append('<td>'+match.matchNumber+'</td>');

        // We should separate our logic in order to
        // extract our date and time, we can do this
        // by creating two additional functions,
        // getDateString(date) and getTimeString(date)
        tr.append('<td>'+getDateString(match.dateTime)+'</td>');
        tr.append('<td>'+getTimeString(match.dateTime)+'</td>');

        // Previously, we have appended each 'td' simply with strings,
        // we can also create them as nodes before appending:
        var tvChannelTD = $('<td>').append(match.tvChannel);
        tr.append(tvChannelTD);

        // Or in one line:
        tr.append($('<td>').append(match.homeTeam));
        tr.append($('<td>').append(match.awayTeam));

        // For more complex html, a good idea is to create each
        // node for readability:
        var homeGoalsInput = $('<input>');

        // We set attributes to the input usning .attr(field, value)
        homeGoalsInput.attr('type', 'number').attr('name', 'home-goals');

        // Classes are added using .addClass()
        homeGoalsInput.addClass('form-control');

        // All of these can of course be chained:
        var awayGoalsInput = $('<input>')
            .attr('type', 'number')
            .attr('name', 'away-goals')
            .addClass('form-control');

        // Note that even though we created the input elements,
        // they have not yet been appended to our table row!
        tr
            .append(homeGoalsInput)
            .append('-') // Don't forget the separator ;)
            .append(awayGoalsInput)

        // And lastly, we must not forget to append our table row
        // to our table, this should be placed withing <tbody>
        $("table.match-table > tbody").append(tr);
    }
}




// There are a lot of libraries that can help you with date
// formatting, you will probably never really do this
// yourself in a production environment.



// Code from #1 below:
$("button").bind('click', getDataAndSend);
function getDataAndSend() {
    var bettingData = [];
    $("table.match-table > tbody > tr").each(function() {
        var matchBet = {};
        var tr = $(this);
        matchBet.matchNumber = tr.attr('match-no');
        matchBet.homeGoals = tr.find('input[name="home-goals"]').val();
        matchBet.awayGoals = tr.find('input[name="away-goals"]').val();
        bettingData.push(matchBet);
    });
    console.debug(bettingData);
}