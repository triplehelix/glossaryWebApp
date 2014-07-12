/** General Functions for WebApplication
 *  By: Mike Hedden
 *  Date: 2014-07-11  
 */
 var wordsArray_g;
 var isSetWordsArray_g = false;
 var selectedWord_g = -1;
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
		console.log("debug: " + wordsArray[i]); //TODO remove
		var wordObj = JSON.parse(wordsArray[i]);
		console.log("debug: " + wordObj.word);
		outString += "<tr><td onclick=\"loadWord(" + wordObj.word_id + ");\">" +
					wordObj.word + 
					"</td></tr>";
	}	
	//output to html
	$("#wordsTable").html(outString);
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