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
	lockUI();
	xmlhttp.open("GET","./GetWords",true);
	xmlhttp.send("param=" + JSON.stringify(params));
	var xmlDoc = null;
	xmlhttp.onreadystatechange = function() {
		console.log(xmlhttp.readyState);
		if(xmlhttp.readyState != 4) return;
		if(xmlhttp.status != 200){
			console.log("xmlRequest Failed with code: " + xmlhttp.status);
			return;
		}
		JSONDoc= JSON.parse(xmlhttp.responseText);
		if(!JSONDoc || typeof JSONDoc === 'undefined'){
			console.log("ERROR: JSONDoc is null");
			return;
		}
		var wordArray=JSONDoc.wordList; //array of jsonstrings
		console.log("wordArray[0]: " + wordArray[0]);
		for (i=0;i<wordArray.length;i++)
		  {
			
		  }
		unLockUI();
		
	}
}

function selectWord(specificWordPath){
	console.log("selectWord() called");
	
	
}

function lockUI(){
	$("body").addClass("loading");
}

function unLockUI(){
	$("body").removeClass("loading");
}