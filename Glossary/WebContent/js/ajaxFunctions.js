/**
 * 
 */

function start() {
	console.log("start() called");
	if (window.XMLHttpRequest)
	  {// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	  }
	else
	  {// code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	var params = { project_id: -1};
	var paramsString = JSON.stringify(params);
	lockUI();
	$.get('./GetWords', 'params=' + paramsString, function(data){
		console.log("response: " + data);
		var JSONobj = JSON.parse(data); 
		var wordArray = JSONobj.wordList;//array of jsonstrings
		if(JSONobj.error == false){
			outputWords(wordArray);
		}else{
			console.log("ERROR: " + JSONobj.errorMsg);
		}
		unLockUI();		
	})
	//get projects
	
	//TODO move unLockUI() down here.
}

/*
 * function used to load specific words based on project selection
 */
function loadWords(project_id){
	
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