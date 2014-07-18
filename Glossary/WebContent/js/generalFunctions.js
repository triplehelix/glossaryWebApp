/** General Functions for WebApplication
 *  By: Mike Hedden
 *  Date: 2014-07-11  
 */
 var wordsArray_g;
 var isSetWordsArray_g = false;
 var projectsArray_g;
 var isSetProjectsArray_g = false;
 var selectedWord_g = -1;
 var selectedProject_g = -1;
/*
 * wordsArray is an array of JSONObjects. 
 * Here is an example of an entry:
 * {\"word_id\":1,\"definition\":\"an alphabetical list of terms or words found in or relating to a specific subject, text, or dialect, with explanations; a brief dictionary.\",\"project_id\":0,\"word\":\"glossary\",\"notes\":\"n\\\/a\"}
 */
function outputWords(wordsArray){
	console.log("outputWords() called.");
	wordsArray_g = wordsArray; //bad coupling I know :(
	isSetWordsArray_g = true;
	
	var outString= "";
	//Load each row 1 at a time
	for(i = 0; i < wordsArray.length; i++){
		var wordObj = JSON.parse(wordsArray[i]);
		outString += "<tr><td onclick=\"loadWord(" + wordObj.word_id + ");\">" +
					wordObj.word + 
					"</td></tr>";
	}	
	//output to html
	$("#wordsTable").html(outString);
}

/*
 * function used to populate project dropdown with Projects from database
 * Each item in project array is a JSONObject containing a name, id, and description
 * name is displayed in dropdown. id is the values of option, and description pop up is
 *  activated on hover.
 */
function outputProjects(projectsArray){
	console.log("outputProjects() called.")
	projectsArray_g = projectsArray;
	isSetProjectsArray_g = true;
	
	var outString = "<option value=\"-1\">General Words</option>";
	//Load each project one at a time into dropdown
	for(i = 0; i < projectsArray.length; i++){
		var projectObj = JSON.parse(projectsArray[i]);
		outString += "<option value=\"" + projectObj.project_id + "\">" + projectObj.project_name + "</option>";
	}
	
	//output to html
	$("#popupProjectsDropdown").html(outString);
	$("#mainProjectsDropdown").html(outString);
	
}

/*
 * wordJSON is the json object of the word in the array
 */
function loadWord(word_id){
	selectedWord_g = word_id;
	console.log("loadWord() called. " + word_id);
	if(isSetWordsArray_g){
		for(i = 0; i < wordsArray_g.length; i++){ //horribly inefficient
			var wordObj = JSON.parse(wordsArray_g[i]);
			if(wordObj.word_id == word_id){
				$("#defText").html(wordObj.definition);
				$("#notesText").html(wordObj.notes);
			}
			
		}
	}
	
}

/*
 * function to create UI popup with form to add word. Then send to AJAX function to 
 * add the word to database.
 */
function addWord(wordObj){
	console.log("addword() called.");
	addWordAJAX(wordObj);
}

function wordsDialogue(){
	$(function() {
	    var dialog, form,
	      // From http://www.whatwg.org/specs/web-apps/current-work/multipage/states-of-the-type-attribute.html#e-mail-state-%28type=email%29
	      emailRegex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/,
	      word = $( "input#word" ),
	      description = $( "textarea#description" ),
	      notes = $( "textarea#notes" ),
	      project = $( "#popupProjectsDropdown option:selected" ),
	      allFields = $( [] ).add( word ).add( description ).add( notes ),
	      wordObj = { word: word.val(), definition: description.val(), notes: notes.val(), project_id: project.val() };
		  
	    function addTheWord() {
	      var valid = true;
	      wordObj = { word: word.val(), definition: description.val(), notes: notes.val(), project_id: project.val() }
	      allFields.removeClass( "ui-state-error" );
	 
	      // TODO validate if necessary
	      
	      if ( valid ) {
	        addWord(wordObj);
	        dialog.dialog( "close" );
	      }
	      return valid;
	    }
	 
	    dialog = $( "#dialog-word" ).dialog({
	      autoOpen: false,
	      height: 400,
	      width: 450,
	      modal: true,
	      buttons: {
	        "Add Word": addTheWord,
	        Cancel: function() {
	          dialog.dialog( "close" );
	        }
	      },
	      close: function() {
	        form[ 0 ].reset();
	        allFields.removeClass( "ui-state-error" );
	      }
	    });
	 
	    form = dialog.find( "form" ).on( "submit", function( event ) {
	      event.preventDefault();
	      addTheWord();
	    });
	 
	    $( "#addWords" ).on( "click", function() {
	      dialog.dialog( "open" );
	    });
	  });
}

/*
 * function to create UI popup with form to edit word. Then send to AJAX function to 
 * edit the word in database.
 */
function editWord(){
	console.log("editword() called.");
}

/*
 * function to create UI popup with form to edit project. Then send to AJAX function to 
 * edit the project in database.
 */
function editProject(){
	console.log("editProject() called.");
}

/*
 * function to create UI popup with form to add project. Then send to AJAX function to 
 * add the project to database.
 */
function addProject(){
	console.log("addProject() called.");
}