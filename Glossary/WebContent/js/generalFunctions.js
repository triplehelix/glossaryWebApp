/** General Functions for WebApplication
 *  By: Mike Hedden
 *  Date: 2014-07-11  
 */
 var wordsArray_g;
 var isSetWordsArray_g = false;
 var selectedWord_g = -1;
 var selectedProject_g = -1;
/*
 * wordsArray is an array of JSONObjects. 
 * Here is an example of an entry:
 * {\"word_id\":1,\"definition\":\"an alphabetical list of terms or words found in or relating to a specific subject, text, or dialect, with explanations; a brief dictionary.\",\"project_id\":0,\"word\":\"glossary\",\"notes\":\"n\\\/a\"}
 */
function outputWords(wordsArray){
	console.log("outputWords() called.");
	wordsArray_g = wordsArray; //bad programming I know :(
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
function outputProjects(projectArray){
	console.log("outputProjects() called.")
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
function addWord(){
	console.log("addword() called.");
	
}

/*
 * function used to validate input, create word object, and push to addWordAJAX()
 */
function clickAddWord(){
	
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