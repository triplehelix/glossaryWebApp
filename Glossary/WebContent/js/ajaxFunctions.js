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
	wordsDialogue(); //startup javascript for handling the wordsDialogue
	projectsDialogue(); //startup javascript for handling the projectsDialogue()
	$("#wordsTable tr").click(function() {
	    var selected = $(this).hasClass("highlight");
	    $("#wordsTable tr").removeClass("highlight");
	    if(!selected)
	            $(this).addClass("highlight");
	});

    $('select[id="mainProjectsDropdown"]').change(function(){
        selectedProject_g = $( "#mainProjectsDropdown" ).val();
        console.log("Selected Project Id: " + selectedProject_g);
        loadWords(selectedProject_g);
    });
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
	});
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
		if(JSONobj.error == false){
			//success reload words
			loadWords(selectedProject_g);
		}else{
			//TODO display error popup
			alert("ERROR inserting word");
			console.log("ERROR: " + JSONobj.errorMsg);
			//loadWords(selectedProject_g);
		}
		unLockUI();		
	});
}

/*
 * function used to add a project to the database
 * example projectObj object:
 * {} TODO
 */
function addProjectAJAX(projectObj){
	console.log("addProjectAJAX() called.");
	xmlhttp = initXmlHTTP();
	var paramsString = JSON.stringify(projectObj);
	lockUI();
	$.post('./AddProject', 'params=' + paramsString, function(data){
		var JSONobj = JSON.parse(data); 
		if(JSONobj.error == false){
			//success reload projects
			loadProjects();
		}else{
			//TODO display error popup
			alert("ERROR inserting Project.");
			console.log("ERROR: " + JSONobj.errorMsg);
		}
		unLockUI();		
	});
}

function editWordAJAX(wordObj){
	console.log("editWordAJAX() called.");
    xmlhttp = initXmlHTTP();
    var paramsString = JSON.stringify(wordObj);
    lockUI();
    $.post('./EditWord', 'params=' + paramsString, function(data){
        var JSONobj = JSON.parse(data);
        if(JSONobj.error == false){
            //success reload words
            loadWords(selectedProject_g);
        }else{
            //TODO display error popup
            alert("ERROR editing word");
            console.log("ERROR: " + JSONobj.errorMsg);
            //loadWords(selectedProject_g);
        }
        unLockUI();
    });
}

function deleteWordAJAX(wordObj){
    console.log("deleteWordAJAX() called.");
    xmlhttp = initXmlHTTP();
    var paramsString = JSON.stringify(wordObj);
    lockUI();
    $.post('./DeleteWord', 'params=' + paramsString, function(data){
        var JSONobj = JSON.parse(data);
        if(JSONobj.error == false){
            //success reload words
            loadWords(selectedProject_g);
        }else{
            //TODO display error popup
            alert("ERROR deleting word.");
            console.log("ERROR: " + JSONobj.errorMsg);
            //loadWords(selectedProject_g);
        }
        unLockUI();
    });
}

function editProjectAJAX(projectObj){
    console.log("editProjectAJAX() called.");
    xmlhttp = initXmlHTTP();
    var paramsString = JSON.stringify(projectObj);
    lockUI();
    $.post('./EditProject', 'params=' + paramsString, function(data){
        var JSONobj = JSON.parse(data);
        if(JSONobj.error == false){
            //success reload projects & words
            loadProjects();
            loadWords(selectedProject_g);
        }else{
            //TODO display error popup
            alert("ERROR editing project");
            console.log("ERROR: " + JSONobj.errorMsg);
        }
        unLockUI();
    });
}

function deleteProjectAJAX(projectObj){
    console.log("deleteWordAJAX() called.");
    xmlhttp = initXmlHTTP();
    var paramsString = JSON.stringify(projectObj);
    lockUI();
    $.post('./DeleteProject', 'params=' + paramsString, function(data){
        var JSONobj = JSON.parse(data);
        if(JSONobj.error === false){
            //success reload all projects and words
            selectedProject_g = -1;
            loadProjects();
            loadWords(selectedProject_g);
        }else{
            //TODO display error popup
            alert("ERROR deleting word.");
            console.log("ERROR: " + JSONobj.errorMsg);
        }
        unLockUI();
    });
}

function loadProjects(){
	console.log("loadProjects()");
	xmlhttp = initXmlHTTP();
	lockUI();
	$.get('./GetProjects', function(data){
		var JSONobj = JSON.parse(data); 
		var projectArray = JSONobj.projectList; //array of jsonstrings
		if(JSONobj.error == false){
			outputProjects(projectArray);
			console.log("DEBUG: projectArray: " + projectArray);
		}else{
			console.log("ERROR: " + JSONobj.errorMsg);
		}
		unLockUI();		
	});
	
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