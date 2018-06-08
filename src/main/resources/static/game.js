const app = {
	winners : [],
	gameID : null,
	attempt: 0,
	baseURL : 'rest/api/games'
};

let data = [];

function initGame() {
	element('attempt').addEventListener("keyup", function(event) {
		event.preventDefault();
		if (event.keyCode === 13) {
		    document.getElementById("check").click();
		    event.keyCode = null;
		}
	});

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
	hide(element("newGame"));
	
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
	if( data.result.isWinner == true) {
		alert("Congrats, " + data.result.userName + "! You are WIN from " + data.result.attempsCounter + " attemps!");
		element("check").disabled = true;
		element("attempt").disabled = true;
		hide(element("check"));
		element("name").disabled = true;
		hide(element("addNameBtn"));
		getWinnersTable();
		show(element("newGame"));
		
	}
}

function checkMaxAttemps() {
	if( data.result.isWinner == false && data.result.isEnded == true) {
		alert("Sorry, " + data.userName + ", but you lost! Maximum " + data.result.attempsCounter + " attemps!");
		element("check").disabled = true;
		element("attempt").disabled = true;
		hide(element("check"));
		element("name").disabled = true;
		hide(element("addNameBtn"));
		getWinnersTable();
		show(element("newGame"));
	}
}

//Get wrapped game object each time.
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
			data = data[0]['result'];

			if(data.errorMsg != null){
				alert("Game Server say: - " + data.errorMsg);
				return;
			}
			
			createLogTable();
			checkWinner();
			checkMaxAttemps();
			
			if(data.userMsg != null && data.userMsg != '') {
				alert("Game Server say: - " + data.userMsg);
			}
		
		} else if (this.readyState == 4 && this.status != 200) {
			let r = JSON.parse(this.responseText);
			alert("Game Server Error: - Status: " + this.status + ", error: "+ r.error + ", \n message: " + r.message);
		}
	};
	
	let url = app.baseURL + "/check-attempt/" + app.gameID + "/" + element("attempt").value;
	xhttp.open("GET", url , true);
	xhttp.send();
}

function createLogTable() {
	var g = data.result.gameLog;
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
			
			data = [];
			data.push(JSON.parse(this.responseText));
			
			if(data.errorMsg != null){
				alert("Game Server say: - " + data.errorMsg);
				return;
			}
			
			data = data[0]['result'];
			app.winners = data;
			createWinnersTable();
			element("winnersTable").style['z-index'] = 1;
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
				initGame();
			} else {
				alert("Wrong password!!");
			}
		}
	};
	let url = app.baseURL + "/clear-win-table/" + element("password").value;
	xhttp.open("POST", url, true);
	xhttp.send();
}

function showPassInput() {
	if(!element("showAdminPass")) {
		//let t = '<div class="modal" id="popup1"><div class="modal-content"><span class="close" onClick="PopUpHide()">&times;</span>'
		element("popup1").innerHTML += 
			'<div id="showAdminPass" class="modal-content2"><span class="close" onClick=hideORshow("showAdminPass")>&times;</span><input type="password" id="password" placeholder="Enter password "><button id="clearWinnersTable" onclick="clearWinnersTable()" class="addBtn">Submit</button></div>';
	} else {
		hideORshow("showAdminPass");
	}
}

function createWinnersTable() {
	if (app.winners.length == 0) {
		element("winnersTable").innerHTML = "<h1>No winners found !!</h1>";
		return;
	}
	let t = '<div class="modal" id="popup1"><div class="modal-content"><span class="close" onClick="PopUpHide()">&times;</span>'
		+'<h3>Top Score Table</h3> '
		+ "<table>";
	t += "<tr>";
	t += "<th>#</th>";
	t += "<th>Name</th>";
	t += "<th>Attempts</th>";
	t += "<th>Date</th>";
	t += "</tr>";
	
	for (let i = 0; i < app.winners.length; i++) {
		const winner = app.winners[i];
		winner.log = JSON.parse(winner.log);
		t += '<tr id = "game_' + i + '" onClick = hideORshow("gameLog_'+i+'") title="Click to show game log" class="pointer">';
		t += "<td>" + (i+1) + "</td>";
		t += "<td>" + winner.name + "</td>";
		t += "<td>" + winner.attempts + "</td>";
		t += "<td>" + winner.gameDate + "</td>";
		t += "</tr>";
		t += '<tr id="gameLog_' + i + '" class="logHidden" ><td colspan="4">';
		
			let log = winner.log;
			if ( log && log.length != 0) {
				
				t += "<table>";
				t += "<tr>";
					t += "<th>ID</th>";
					t += "<th>Guess</th>";
					t += "<th>Attempt Result</th>";
				t += "</tr>";
				//create game log 
				for (let i = 0; i < log.length; i++) {
					t += "<tr>";
						t += "<td>" + log[i].logID + "</td>";
						t += "<td>" + log[i].guess.join("") + "</td>";
						t += "<td>";   
							for(let j = 0; j < log[i].result.length; j++) {
								t += '<span id="' +(log[i].result[j] == 0? "yellow":"green")+ '">o</span>'
							}
						t += "</td>";
					t += "</tr>";
				}
				t += "</table>";
			} else {
				t += '<td><span>No game log found !!</span></td>';
			}
			
		t += '</td></tr>';
		
	}
	t += "</table>" +
			'<div id="clear"><a href="javascript:showPassInput()">Clear Table</a> | ';
	t += '<a href="javascript:PopUpHide()">Close Table</a></div></div></div>';
	element("winnersTable").innerHTML = t;
}

function hideORshow(id) {
    let x = document.getElementById(id);
    if (x.style.display != "block") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function PopUpHide() {
	//hide(element("popup1"));
	element("popup1").style.display = "none";
	element("winnersTable").style['z-index'] = -1;
}

function passHide() {
	element("showAdminPass").style.display = "none";
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




