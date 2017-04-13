/** General Functions for WebApplication
 *  By: Mike Hedden
 *  Date: 2014-07-11  
 */
 var wordsArray_g; //jsonarray of jsonobjs
 var isSetWordsArray_g = false;
 var projectsArray_g; //jsonarray of jsonobjs
 var isSetProjectsArray_g = false;
 var selectedWord_g = -1; //word_id
 var selectedProject_g = -1; //project_id
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
	for( var i = 0; i < wordsArray.length; i++){
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
	console.log("outputProjects() called.");
	projectsArray_g = projectsArray;
	isSetProjectsArray_g = true;
	
	var outString = "<option value=\"-1\">General Words</option>";
	//Load each project one at a time into dropdown
	for( var i = 0; i < projectsArray.length; i++){
		var projectObj = JSON.parse(projectsArray[i]);
		outString += "<option value=\"" + projectObj.project_id + "\"";
		if (selectedProject_g === projectObj.project_id){
		    outString += " selected=\"selected\" ";
        }
        outString += ">" + projectObj.project_name + "</option>";
	}
	
	//output to html
	$("#addWordProjectsDropdown").html(outString);
    $("#editWordProjectsDropdown").html(outString);
	$("#mainProjectsDropdown").html(outString);
}

/*
 * wordJSON is the json object of the word in the array
 */
function loadWord(word_id){
	selectedWord_g = word_id;
	console.log("loadWord() called. " + word_id);
	if(isSetWordsArray_g){
		for( var i = 0; i < wordsArray_g.length; i++){ //horribly inefficient
			var wordObj = JSON.parse(wordsArray_g[i]);
			if(parseInt(wordObj.word_id) === parseInt(word_id)){
				$("#defText").html(wordObj.definition);
				$("#notesText").html(wordObj.notes);
			}
			
		}
	}
	
}

function wordsDialogue(){
	$(function() {
	    var dialog, editDialog, form,
            word_id = -1,
            word = $( "input#word_add" ),
	        description = $( "textarea#description_add" ),
	        notes = $( "textarea#notes_add" ),
	        project = $( "#addWordProjectsDropdown" ),
            word_edit = $( "input#word_edit" ),
            description_edit = $( "textarea#description_edit" ),
            notes_edit = $( "textarea#notes_edit" ),
            project_edit = $( "#editWordProjectsDropdown" ),
	        allFields = $( [] ).add( word_add ).add( description_add ).add( notes_add ),
            allFields_edit = $( [] ).add( word_edit ).add( description_edit ).add( notes_edit ),
	        edit = false,
	        wordObj = { word_id: word_id, word: word.val(), definition: description.val(), notes: notes.val(), project_id: project.val(), edit: edit };
		  
	    function addTheWord() {
	      var valid = true;
	      wordObj = { word: word.val(), definition: description.val(), notes: notes.val(), project_id: project.val(), edit: edit };
	      allFields.removeClass( "ui-state-error" );
	 
	      // TODO validate required fields are populated
	      
	      if ( valid ) {
	        addWordAJAX(wordObj);
	        dialog.dialog( "close" );
	      }
	      return valid;
	    }

	    function editTheWord() {
            var valid = true;
            wordObj = { word_id: word_id, word: word_edit.val(), definition: description_edit.val(), notes: notes_edit.val(),
                project_id: project_edit.val(), edit: edit };
            allFields_edit.removeClass( "ui-state-error" );

            if (word_id < 0){
                displayAlert("Word Id is invalid for this word.");
                valid = false;
            }

            if ( valid ) {
                editWordAJAX(wordObj);
            }
            editDialog.dialog( "close" );

            return valid;
		}

		function deleteTheWord() {

            var valid = true;
            wordObj = { word_id: word_id, word: word_edit.val(), definition: description_edit.val(), notes: notes_edit.val(),
                project_id: project_edit.val(), edit: edit };
            allFields_edit.removeClass( "ui-state-error" );

            if (word_id < 0){
                displayAlert("Word Id is invalid for this word.");
                valid = false;
            }

            if ( valid ) {
                deleteWordAJAX(wordObj);
            }
            editDialog.dialog("close");
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

	    editDialog = $( "#dialog-edit-word" ).dialog({
            autoOpen: false,
            height: 400,
            width: 450,
            modal: true,
            buttons: {
                "Edit Word": editTheWord,
                "Delete Word": deleteTheWord,
                Cancel: function() {
                    editDialog.dialog( "close" );
                }
            },
            close: function() {
                form[ 0 ].reset();
                allFields_edit.removeClass( "ui-state-error" );
            }
        });
	 
	    form = dialog.find( "form" ).on( "submit", function( event ) {
	      event.preventDefault();
	      addTheWord();
	    });
	 
	    $( "#addWords" ).on( "click", function() {
	      dialog.dialog( "open" );
	    });

	    $( "#editWords" ).on( "click", function() {
	    	//validate selected word
	    	if(parseInt(selectedWord_g) === -1){
	    		displayAlert("Select a word to edit.");
	    		return;
	    	}
	    	editDialog.dialog( "open" );
	    	//populate form fields with current values
	    	var selectedWordArray = wordsArray_g;
	    	var selectedWord;
	    	for(var i = 0;i<selectedWordArray.length;i++){
	    		var tmpWord = JSON.parse(selectedWordArray[i]);
	    		if(parseInt(tmpWord.word_id) === parseInt(selectedWord_g)){
	    			console.log("db: selectedWord = " + i);
	    			selectedWord = tmpWord;
	    			break;
	    		}
	    	}
	    	word_id = selectedWord.word_id;
	    	word_edit.val(selectedWord.word);
	    	description_edit.val(selectedWord.definition);
	    	notes_edit.val(selectedWord.notes);
	    	project_edit.val(selectedWord.project_id);
	    	edit = true;
	    	console.log("editWord loaded.");
	    });
	  });
}

/*
 * Function used to display and handle pop-up form for projects
 */
function projectsDialogue(){
	$(function() {
	    var dialogP, editDialogP, formP,
          project_add = $( "input#project_add" ),
	      projectDescription_add = $( "textarea#projectDescription_add" ),
	      project_edit = $( "input#project_edit" ),
          projectDescription_edit = $( "textarea#projectDescription_edit" ),
	      project_id = -1,
	      edit = false,
	      allFieldsP = $( [] ).add( project_add ).add( projectDescription_add ),
		  allFieldsP_edit = $( [] ).add( project_edit ).add( projectDescription_edit ),
	      projectObj = { project: project_add.val(), description: projectDescription_add.val() };
		  
	    function addTheProject() {
	      var valid = true;
	      projectObj = { project: project_add.val(), description: projectDescription_add.val() };
	      allFieldsP.removeClass( "ui-state-error" );
	 
	      // TODO validate if necessary
	      
	      if ( valid ) {
	    	addProjectAJAX(projectObj);
	        dialogP.dialog( "close" );
	      }
	      return valid;
	    }

        function editTheProject() {
            var valid = true;
            projectObj = { project_id: project_id, project: project_edit.val(), description: projectDescription_edit.val(), edit: edit };
            allFieldsP_edit.removeClass( "ui-state-error" );

            if (project_id < 0){
                displayAlert("Project Id is invalid for this project.");
                valid = false;
            }

            if ( valid ) {
                editProjectAJAX(projectObj);
            }
            editDialogP.dialog( "close" );

            return valid;
        }

        function deleteTheProject() {
            var valid = true;
            projectObj = { project_id: project_id, project: project_edit.val(), description: projectDescription_edit.val(), edit: edit };
            allFieldsP_edit.removeClass( "ui-state-error" );

            if (project_id < 0){
                displayAlert("Project Id is invalid for this project.");
                valid = false;
            }

            if ( valid ) {
                deleteProjectAJAX(projectObj);
            }
            editDialogP.dialog("close");
            return valid;
        }
	 
	    dialogP = $( "div#dialog-project" ).dialog({
	      autoOpen: false,
	      height: 400,
	      width: 450,
	      modal: true,
	      buttons: {
	        "Add Project": addTheProject,
	        Cancel: function() {
	          dialogP.dialog( "close" );
	        }
	      },
	      close: function() {
	        formP[ 0 ].reset();
	        allFieldsP.removeClass( "ui-state-error" );
	      }
	    });

        editDialogP = $( "div#dialog-edit-project" ).dialog({
            autoOpen: false,
            height: 400,
            width: 450,
            modal: true,
            buttons: {
                "Edit Project": editTheProject,
                "Delete Project": deleteTheProject,
                Cancel: function() {
                    editDialogP.dialog( "close" );
                }
            },
            close: function() {
                formP[ 0 ].reset();
                allFieldsP_edit.removeClass( "ui-state-error" );
            }
        });
	 
	    formP = dialogP.find( "form" ).on( "submit", function( eventP ) {
	      eventP.preventDefault();
	      addTheProject();
	    });
	 
	    $( "#addProjects" ).on( "click", function() {
	      dialogP.dialog( "open" );
	    });

        $( "#editProjects" ).on( "click", function() {
            //validate selected project
            if(selectedProject_g === -1){
                displayAlert("Select a Project to edit.");
                return;
            }
            editDialogP.dialog( "open" );
            //populate form fields with current values
            var selectedProjectsArray = projectsArray_g;
            var selectedProject;
            for(var i = 0; i < selectedProjectsArray.length; i++){
                var tmpProject = JSON.parse(selectedProjectsArray[i]);
                if(parseInt(tmpProject.project_id) === parseInt(selectedProject_g)){
                    selectedProject = tmpProject;
                    break;
                }
            }
            console.log("DEBUG-selectedProject: " + JSON.stringify(selectedProject));
            project_id = selectedProject.project_id;
            project_edit.val(selectedProject.project_name);
            projectDescription_edit.val(selectedProject.project_description);
            edit = true;
            console.log("editProject loaded with project_id : " + selectedProject.project_id);
        });

	  });
}

/*
 * function to create UI popup with form to edit word. Then send to AJAX function to 
 * edit the word in database.
 */
function editWord(){
	console.log("editword() called with selected word: " + selectedWord_g);
}

/*
 * function to create UI popup with form to edit project. Then send to AJAX function to 
 * edit the project in database.
 */
function editProject(){
	console.log("editProject() called with selected project: " + selectedProject_g);
	
}

function displayAlert(message){
    alert(message);
}