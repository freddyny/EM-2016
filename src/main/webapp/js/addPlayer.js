fixTable();
function fixTable() {
    for (i = 0; i < 3; i++) {
        var tr = $('<tr>');
        tr.append($('<td>').append((i+1)+": "));



        var tdScorer = $('<td>');
        var toppscorer = $('<input>')
                            .attr('type', 'text')
                            .attr('name', 'toppscorer'+(i+1))
                            .addClass('form-control')
                            .addClass("toppscorer");

        tr.append(tdScorer.append(toppscorer));


        var goals = $('<input>')
                    .attr('type', 'number')
                    .attr('name', 'goals'+(i+1))
                    .attr('value','0')
                    .attr('min','0')
                    .addClass('form-control goals');
        tr.append(goals);
        $("table.toppscorer-table > tbody").append(tr);

    }

     for (i = 0; i < 2; i++) {
            var tr = $('<tr>');
            tr.append($('<td>').append((i+1)+": "));



            var tdScorer = $('<td>');
            var bestPlayer = $('<input>')
                                .attr('type', 'text')
                                .attr('name', 'bestPlayer'+(i+1))
                                .addClass('form-control')
                                .addClass("bestPlayer");

            tr.append(tdScorer.append(bestPlayer));
            $("table.bestplayer-table > tbody").append(tr);

        }
}

/*
$('.player-btn').bind('click', getDataAndSend);
function getDataAndSend() {
    players = {};
    toppscorers = [];
    $("table.toppscorer-table > tbody > tr").each(function(i) {
        toppscorer = {};
        var tr = $(this);
        toppscorer.number = (i+1);
        toppscorer.player = tr.find('.toppscorer').val();
        toppscorer.goals = tr.find('.goals').val();
        toppscorers.push(toppscorer);
    });
    bestPlayerList = [];
    $("table.bestplayer-table > tbody > tr").each(function(i) {
            bestplayers = {};
            var tr = $(this);
            bestplayers.number = (i+1);
            bestplayers.player = tr.find('.bestPlayer').val();
            bestPlayerList.push(bestplayers);
    });
    players.toppscorers = toppscorers;
    players.bestPlayer = bestPlayerList;
    var json = JSON.stringify(players);
    console.debug(json);
    $('input[name="players"]').val(json);

}*/