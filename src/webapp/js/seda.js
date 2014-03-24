/*
 * Parse Json message to table and insert it into page.
 */
function parseJsonToTable(strMsg) {
	var obj = jQuery.parseJSON(strMsg);

	var table = '<table class="table table-bordered table-hover">';
	table += '<tr><th>Key</th><th>Value</th></tr>';

	$.each(obj.keyvalues, function(index, item) {
		table += '<tr><td>' + item.key + '</td><td>' + item.value
				+ '</td></tr>';
	});
	table += '</table>';
	$("#result-div").html(table);
}

/*
 * During startup check if websockets are available.
 */
$(window).load(function() {
	
	$('#datacontainer').hide();
	
	if ("WebSocket" in window) {
		document.getElementById('wssupport').innerHTML = "true";
	}
})

/*
 * Setup result data after startup
 */
$(window).load(function() {
	var msg = '{"keyvalues": []}'
	parseJsonToTable(msg);
})

/*
 * WS instance variable
 */
var ws

/*
 * Btn func connect
 */
$("#btn-connect").click(function() {
	var serverIp = document.getElementById('input-serverIp-id').value
	ws = new WebSocket(serverIp);

	ws.onopen = function() {
		document.getElementById('wsconnect').innerHTML = "true";
		$('#logincontainer').hide();
		$('#datacontainer').show();
	};

	ws.onmessage = function(evt) {
		var received_msg = evt.data;
		parseJsonToTable(received_msg);
	};

	ws.onclose = function() {
		document.getElementById('wsconnect').innerHTML = "disconnected";
		$('#logincontainer').show();
		$('#datacontainer').hide();
	};
});

/*
 * Btn func add entry
 */
$("#btn-push").click(function() {
	var msg = {
		op : "push",
		key : document.getElementById("input-push-key").value,
		val : document.getElementById("input-push-value").value,
	};

	var strMsg = JSON.stringify(msg);
	ws.send(strMsg);
});

/*
 * Btn func delete entry
 */
$("#btn-delete").click(function() {
	var msg = {
		op : "delete",
		key : document.getElementById("input-delete-key").value,
	};

	var strMsg = JSON.stringify(msg);
	ws.send(strMsg);
});