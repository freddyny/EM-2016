
var jsonData = JSON.stringify(json);

// Now we can render our data using for instance a function:
renderData(jsonData);
function renderData(jsonData) {
    // First parse it from json:

    var data = JSON.parse(jsonData);
    var teams = data.teams;

    var tr = $('<tr>');
    tr.append($('<td>').append("Vinner: "));

    let select = ($('<select>').attr('data-live-search','true').addClass('selectpicker'));
    for(var i = 0; i < teams.length; i++) {
        let team = teams[i];
        var option = $('<option>');
        option.append(team);
        select.append(option);

    }
    var tdVinner = $('<td>').addClass("vinner");
    tdVinner.append(select);

    tr.append(tdVinner);
    $("table.winner-table > tbody").append(tr);

}

$('.winner-btn').bind('click', getDataAndSend);
function getDataAndSend() {
    var tr = $("table.winner-table > tbody > tr")
    var winner = tr.find('.vinner>div>button').attr('title');
    $('input[name="winner"]').val(winner);
}
