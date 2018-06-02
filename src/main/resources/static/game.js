const app = {
	winners : [],
	gameID : null,
	attempt: 0,
	baseURL : 'rest/api/games'
};

let data = [];

function initGame() {
	

	if(app.gameID != null) {
		if (!confirm("Start new game?")) {
			return;
	    } 
	}
	
	app.winners = [];
	app.gameID = null;
	app.attempt = 0;
	
	show(element("addNameBtn"));
	element("name").disabled = false;
	
	element("winnersTable").innerHTML = '';
	element("logTable").innerHTML = '';
	
	element("check").disabled = false;
	show(element("check"));
	
	element("attempt").disabled = false;
	element("attempt").value = '';
	element("attempt").placeholder = "Guess 4-digits number";
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			log("gameID: " + this.responseText);
			let a = JSON.parse(this.responseText);
			app.gameID = a;
		}
	};
	xhttp.open("GET", app.baseURL + "/get-new-game-id", true);
	xhttp.send();
}

function addName() {
	if(element("name").value == '') {
		alert("Enter your name!");
		return;
	}
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			log("addName: " + this.responseText);
			app.attempt++;
			showName();
		}
	};
	let url = app.baseURL + "/set-gamer-name/" + app.gameID + "/" + element("name").value;
	xhttp.open("POST", url , true);
	xhttp.send();
}

function showName() {
	element("name").disabled = true;
	hide(addNameBtn);
}

function checkWinner() {
	if( data.isWinner == true) {
		alert("Congrats, " + data.userName + "! You are WIN from " + data.attempsCounter + " attemps!");
		element("check").disabled = true;
		element("attempt").disabled = true;
		hide(element("check"));
		element("name").disabled = true;
		hide(element("addNameBtn"));
		getWinnersTable();
	}
}

function checkMaxAttemps() {
	if( data.isWinner == false && data.isEnded == true) {
		alert("Sorry, " + data.userName + ", but you lost! Maximum " + data.attempsCounter + " attemps!");
		element("check").disabled = true;
		element("attempt").disabled = true;
		hide(element("check"));
		element("name").disabled = true;
		hide(element("addNameBtn"));
		getWinnersTable();
	}
}

function checkNumber() {
	if(element("attempt").value.length != 4 ) {
		alert("Enter 4 digits number! Not a " + element("attempt").value.length + " digits!");
		return;
	}
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			log("checkNumber: " + this.responseText);
			app.attempt++;
			data = [];
			data.push(JSON.parse(this.responseText));
			data = data[0];
			log("checkNumber Data: " + JSON.stringify(data));
			log("checkNumber Data gameLog: " + JSON.stringify(data.gameLog));
			createLogTable();
			checkWinner();
			checkMaxAttemps();
		}
	};
	let url = app.baseURL + "/check-attempt/" + app.gameID + "/" + element("attempt").value;
	xhttp.open("GET", url , true);
	xhttp.send();
}

function createLogTable() {
	var g = data.gameLog;
	if (g.length == 0) {
		element("logTable").innerHTML = "<h1>No games found !!</h1>";
		return;
	}
	let t = "<table>";
	t += "<tr>";
	t += "<th>ID</th>";
	t += "<th>Guess</th>";
	t += "<th>Attempt Result</th>";
	t += "</tr>";
	for (let i = 0; i < g.length; i++) {
		t += "<tr>";
		t += "<td>" + g[i].id + "</td>";
		t += "<td>" + g[i].guess.join("") + "</td>";
		t += "<td>";   
			for(let j = 0; j < g[i].result.length; j++) {
				t += '<span id="' +(g[i].result[j] == 0? "yellow":"green")+ '">o</span>'
			}
		t += "</td>";
		t += "</tr>";
	}
	t += "</table>";
	element("logTable").innerHTML = t;
}


function getWinnersTable() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			log("All events: " + this.responseText);
			app.winners = JSON.parse(this.responseText);
			createWinnersTable();
		}
	};
	xhttp.open("GET", app.baseURL + "/get-winners-table", true);
	xhttp.send();
}

function clearWinnersTable() {
	if(element("password").value == '') {
		alert("Enter admin password to clean table!");
		return;
	}
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			log("clearWinnersTable: " + this.responseText);
			if(JSON.parse(this.responseText) == true) {
				element("winnersTable").innerHTML = "";
				app.winners = [];
				alert("Winners table is cleared!");
			} else {
				alert("Wrong password!!");
			}
			createWinnersTable();
		}
	};
	let url = app.baseURL + "/clear-win-table/" + element("password").value;
	xhttp.open("POST", url, true);
	xhttp.send();
}

function showPassInput() {
	if(!element("showAdminPass")) {
		element("popup1").innerHTML += 
			'<div id="showAdminPass"><input type="password" id="password" placeholder="Enter password "><button id="clearWinnersTable" onclick="clearWinnersTable()" class="addBtn">Submit</button></div>';
	}
}

function createWinnersTable() {
	if (app.winners.length == 0) {
		element("winnersTable").innerHTML = "<h1>No winners found !!</h1>";
		return;
	}
	let t = '<div class="b-popup" id="popup1"> <div class="b-popup-content">' + "<table>";
	t += "<tr>";
	t += "<th>Name</th>";
	t += "<th>Attempts</th>";
	t += "<th>Date</th>";
	t += "<th></th>";
	t += "</tr>";
	for (let i = 0; i < app.winners.length; i++) {
		const winner = app.winners[i];
		t += "<tr>";
		t += "<td>" + winner.name + "</td>";
		t += "<td>" + winner.attempts + "</td>";
		t += "<td>" + winner.gameDate + "</td>";
		t += "<td onclick='removeEvent(" + winner.id +")' class='remove'>x</td>";
		t += "</tr>";
	}
	t += "</table>" +
			'<div id="clear"><a href="javascript:showPassInput()">Clear Table</a></div>';
	t += '<a href="javascript:PopUpHide()">Close Table</a></div></div>';
	element("winnersTable").innerHTML = t;
}

function PopUpHide() {
	hide(element("popup1"));
}
function removeEvent(eventId) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			log("deleted event: " + this.responseText);
			showAllEvents();
		}
	};
	xhttp.open("DELETE", app.baseURL + "/" + eventId, true);
	xhttp.send();
	
}

function addEvent() {
	hide(element('addEventBtn'));

	const event = {
		description : element("description").value,
		startDate : element("startDate").value
	}
	if (event.description == null || event.description.length == 0
			|| event.startDate == null || event.startDate.length == 0) {
		alert("Please fill all the fileds");
		show(element('addEventBtn'));
		return;
	}

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			log("new id is " + this.responseText);
			showAllEvents();
		}
	};
	xhttp.open("POST", app.baseURL, true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.send(JSON.stringify(event));
	show(element('addEventBtn'));
}

function hide(element) {
	if(element.style.visibility == '') {
		element.style.visibility = 'hidden';
	}
}

function show(element) {
	if(element.style.visibility == 'hidden') {
		element.style.visibility = '';
	}
}

function element(elementId) {
	return document.getElementById(elementId);
}

function log(item) {
	console.log(item);
}