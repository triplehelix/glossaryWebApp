/** General Functions for WebApplication
 *  By: Mike Hedden
 *  Date: 2014-07-11  
 */

/*
 * wordsArray is an array of JSONObjects. 
 * Here is an example of an entry:
 * {\"word_id\":1,\"definition\":\"an alphabetical list of terms or words found in or relating to a specific subject, text, or dialect, with explanations; a brief dictionary.\",\"project_id\":0,\"word\":\"glossary\",\"notes\":\"n\\\/a\"}
 */
function outputWords(wordsArray){
	console.log("outputWords() called. Param: " + wordsArray + " type: " + typeof wordsArray);
	
	var outString= "";
	//Load each row 1 at a time
	for(i = 0; i < wordsArray.length; i++){
		console.log("debug: " + wordsArray[i]); //TODO remove
		var wordObj = JSON.parse(wordsArray[i]);
		console.log("debug: " + wordObj.word);
		outString += "<tr><td onclick=\"loadWord(" + wordsArray + ", " + i + ");\">" +
					wordObj.word + 
					"</td></tr>";
	}	
	//output to html
	$("#wordsTable").html(outString);
}

/*
 * wordJSON is the json object of the word in the array
 */
function loadWord(wordArray, i){
	console.log("loadWord() called. Param " + wordArray + ", " + i);
	
}