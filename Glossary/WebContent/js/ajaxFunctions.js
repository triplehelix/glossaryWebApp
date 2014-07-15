/**
 * Ajax functions used to communicate between javascript frontend
 * and java backend.
 * 
 * All communications utilize json.
 */

function start() {
	console.log("start() called");
	loadWords(-1); //preload words with no project
	loadProjects(); // populate projects dropdown
}

/*
 * function used to load specific words based on project selection
 */
function loadWords(project_id){
	console.log("loadWords() called.");
	xmlhttp = initXmlHTTP();
	var params = { project_id: project_id};
	var paramsString = JSON.stringify(params);
	lockUI();
	$.get('./GetWords', 'params=' + paramsString, function(data){
		var JSONobj = JSON.parse(data); 
		var wordArray = JSONobj.wordList;//array of jsonstrings
		if(JSONobj.error == false){
			outputWords(wordArray);
		}else{
			console.log("ERROR: " + JSONobj.errorMsg);
		}
		unLockUI();		
	})
}

/*
 * function used to add a word to the database
 * example wordObj object:
 * {} TODO
 */
function addWordAJAX(wordObj){
	console.log("addWordAJAX() called.");
	xmlhttp = initXmlHTTP();
	var paramsString = JSON.stringify(wordObj);
	lockUI();
	$.post('./AddWord', 'params=' + paramsString, function(data){
		var JSONobj = JSON.parse(data); 
		var wordArray = JSONobj.wordList;//array of jsonstrings
		if(JSONobj.error == false){
			//success reload words
			loadWords(selectedProject_g);
		}else{
			//TODO display error popup
			alert("ERROR inserting word");
			console.log("ERROR: " + JSONobj.errorMsg);
			loadWords(selectedProject_g);
		}
		unLockUI();		
	})
}

function editWordAJAX(wordObj){
	console.log("editWordAJAX() called.");
}

function loadProjects(){
	console.log("loadProjects()");
	//TODO
	
}

/*
 * UI responsiveness function to stop input from user while ajax is working
 */
function lockUI(){
	console.log("Locking UI");
	$("body").addClass("loading");
}

/*
 * unlocks input from user initiated by lockUI()
 */
function unLockUI(){
	console.log("Unlocking UI");
	$("body").removeClass("loading");
}

/*
 * function used to initialize an xmlhttp request
 */
function initXmlHTTP(){
	console.log("Creating xmlHttpRequest object...");
	if (window.XMLHttpRequest)
	  {// code for IE7+, Firefox, Chrome, Opera, Safari
	  return new XMLHttpRequest();
	  }
	else
	  {// code for IE6, IE5
	  return new ActiveXObject("Microsoft.XMLHTTP");
	  }
}