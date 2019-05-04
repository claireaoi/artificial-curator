//Artificial creator
//by Claire Aoi


//Explain the process:
//1. Trigger first analysis of the image test.jpg, which looks to label the image with a few words, then write this analysis in a text file input.txt
//Use Node Cloud Vision API for the labelling of the image: need an API Key.
//2. Clean input.txt into a seeds.txt with only the lebels: do this for now from the Processing sketch called seed
//3. Then Trigger a semantic analysis from this list of words (seeds.txt) which looks for related words.
//This write different lists of related and associated words.
//Use TWINWORD API for the analysis.
//4. Finally trigger processing sketch TurnArt to create a curating text with these words as a sort of seeding.

//To USE:
//Put your API Key
//Change the PATHS

//FIXES
// Step 2 integrate it to js
// TwinWord API seems not to work as precedently. Because Mashape now deprecated >change to RapidAPI
// Update Words


'use strict'

/////////////////////////////////////////////////////////// PART 1 LABELLING IMAGE ////////////////////////////////////                  

const vision = require('./index')
var unirest = require('unirest');//need unirest library
var fs = require('fs');
var Promise = require('promise');
// init with auth, API KEY
vision.init({auth: 'AIzaSyCr4Pd3STwACYToGLRF1FWViXNNsTos6Fs'})


// construct parameters
const req = new vision.Request({
  image: new vision.Image('C:/Users/clair/Documents/artificial-curator/data/test.jpg'),
  features: [
    new vision.Feature('FACE_DETECTION', 4),
    new vision.Feature('LABEL_DETECTION', 10),
  ]
})

// send single request
vision.annotate(req).then((res) => {
  // handling response
  var annot = JSON.stringify(res.responses);
  console.log(annot);
  var fs = require('fs');
fs.writeFile("C:/Users/clair/Documents/artificial-curator/seed/input.txt", annot, processingSeed);
}, (e) => {
  console.log('Error: ', e)
})

///////////////////////////////////////////////////////// PART 2 ////////////////////////////////////                  
// Here, launch the Processing sketch Seed, which from the input.txt create the seeds.text in the data folder

function processingSeed() {
var exec = require('child_process').exec;
var fs = require('fs');
var cmd = 'C:/Users/clair/Documents/Processing/processing-java.exe --sketch=C:/Users/clair/Documents/artificial-curator/seed --output=C:/Users/clair/Documents/artificial-curator/seed/output --force --run'; 
  exec(cmd, processing0);     
function processing0(err, data, response) {
if (err) {console.log('Something went wrong in Processing Seed.');}
else {console.log('Processing seed worked!.');
callback();
}
}
}

///////////////////////////////////////////////////////// PART 3 ////////////////////////////////////                  
// SEMANTIC ASSOCIATIONS : Look for related words, for each seed.
//BEWARE SEEDS IS CREATED FROM the PROCESSING sketch now called seeds from input.txt
//Have to Check: Loop
function callback (err, data) {
  if (err) return console.error(err);
var file1='seeds.txt';
var params= {//encoding to choose how store data file in variable
  encoding: 'utf-8'
}
//SEEDS FILE
var seeds=fs.readFileSync(file1, params).split('\n');//TEXT, split en list one element for each seed
var assocT="";
var broadT="";
var relatedT="";

lookfor();

function lookfor() {

var nseeds=seeds.slice(0,seeds.length-1);

//LOOP on each seed.
nseeds.forEach(function(element) {
// console.log(element);

//CREATE REQUEST FOR TWINWORD API with unirest
//THE ASSOCIATED WORDS and the Broad and related terms, with TwinWord API via RapidAPI

//Maybe use .assoc_word_ex instead of .assoc_word.
unirest.get("https://twinword-word-graph-dictionary.p.rapidapi.com/association/?entry="+element)
.header("X-RapidAPI-Host", "twinword-word-graph-dictionary.p.rapidapi.com")
.header("X-RapidAPI-Key", "vBXIYa6LermshXMoaglL7TiUgWF1p1S4pFSjsn9QKbqh03bLAI")
.end(function (result) {
 // console.log(result.status, result.headers, result.body);//To have all information
  //console.log("Assoc_word: "+ result.body.assoc_word);
  assocT+=", "+result.body.assoc_word;
});

unirest.get("https://twinword-word-graph-dictionary.p.rapidapi.com/reference/?entry="+element)
.header("X-RapidAPI-Host", "twinword-word-graph-dictionary.p.rapidapi.com")
.header("X-RapidAPI-Key", "vBXIYa6LermshXMoaglL7TiUgWF1p1S4pFSjsn9QKbqh03bLAI")
.end(function (result) {
 console.log(result.status, result.headers, result.body);
 if (result.body.result_code == 200) //ie success in operation
  {
  console.log("Broad Terms: "+ result.body.relation.broad_terms);
  broadT+=", "+result.body.relation.broad_terms;
  relatedT+=","+result.body.relation.related_terms;}
});

});
//Homemade. :S
setTimeout(function () {
  write();
}, 8000);//5seconds
}

//Write now all these related words, separated by a synbol (to split later on) in a new text file, seedsrelated.txt
function write() {
var bothT=assocT+" >>> "+relatedT+" >>> "+broadT;
console.log("ALL together:"+bothT);
  fs.writeFile("C:/Users/clair/Documents/artificial-curator/TurnArt/data/seedsrelated.txt", bothT, function(err) {
                if (err) {
                    console.log(err);
                } else {
                    console.log("seedsrelated saved!");

/////////////////////////////////////////////////////////////////////////// PART 4 ////////////////////////////////////                  
//TRIGGER PROCESSING SKETCH to finalise

var exec = require('child_process').exec;
var fs = require('fs');
var cmd = 'C:/Users/clair/Documents/Processing/processing-java.exe --sketch=C:/Users/clair/Documents/artificial-curator/TurnArt --output=C:/Users/clair/Documents/artificial-curator/TurnArt/output --force --run'; 

//Have to make node call this command line. processing: call back fct to execute after
  exec(cmd, processing);
   
function processing(err, data, response) {
var filename='output.jpg';
if (err) {console.log('Something went wrong in Processing TurnArt.');}
else {console.log('Processing TurnArt worked!.');}

}
                }});
}

console.log(data);
};
