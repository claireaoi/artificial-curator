
//TURN IMAGE (as input) INTO A CURATED ART PIECE
//Text displayed, and saved in output.txt



//FOR NOW ONLY use "association"

//Rita Library
import rita.*;

//INITIALIZATION
int whichl1=1;
int whichl2=1;
int whichl3=1;
PFont font1;
PFont font2;
String[] lines;
int index = 0;
int line=0;
int counter=0;
int count20=0;
int line0=0;
int shiftl=0;
int nline=30;
boolean mouse=true;

//Would serve as seed
//GENERAL SETUP of the art statement
String[] lDa; 
String[] lISM; 
String[] lTION;  
String[] lCON;
String[] lQU;
String[] lNa; 
String[] lNfa; 
String[] lAa; 
String[] lVa;
String[] lA; 
String[] lAd1; 
String[] lAd2; 
String[] lAd3;
String[] lV; 
String[] lVg; 
String[] lVt; 
String[] lVtg;
String[] lP; 
String[] lPf; 
String[] lN; 
String[] lNf;
String[] lN2; 
String[] lD;
String[] lS; 
String[] lP0; 
String[] lMa; 
String[] lASA; 
String[] lSc;  
String[] lESS;
String[] lABL;
String[] lPR0; 
String[] lPR1; 
String[] lPR0a; 
String[] lPR1a;

int whichS;  
int which0; 
int which2=0;
String line1; 
String line2; 
String line3; 
String line4; 
String line5;
String[] lStr; 
String[] lst;
String text;

//SEED
String[] lSeed; 
String[] lSeeds; 
String[] Seeds ; 
String[] Seed ; 
String[] lastSeed;//last state seeds
String[] lSeedLong; 
String[] lSeedL; 
String[] lAssociated;  
String[] lRelated; 
String[] lBroad;
float[] PSeed = new float[30];//proba of each seed do not use?
String lasttext;

int nseed=0;
String currentSeed;//the seed to use.
boolean isA=false;//if is adjective
boolean isN=false;//if is noun
boolean isV=false;//if is verb
boolean usedSeed=false;//tell each time if has used a seed.
String tag;

//MEMORY
String[] mem={"", "", "", "", "", "", "", "", "", ""};//Memory of 10 words!
String sujetmemo;
int lengthMemo=10;

//TITLE
String title;
String[] lTitle;
String titleV="";//verb in title
String[] titleN=new String[4];//Max 4 nouns from title
int ntitlen=0;//number of noun in title
String lastword="";

void setup() {
  size(2000, 1500);//drawing surface size
  frameRate(5);
  font1 = loadFont("Futura-18.vlw");//or DINBold or FuturaKoyu  
  font2= createFont("FuturaLight.ttf", 20);

  lTitle=loadStrings("str_title.txt");
  lSeed = loadStrings("C:/Users/clair/Documents/artificial-curator/data/input.txt");
  lSeedLong = loadStrings("seedsrelated.txt");

  //DIFFERENT WORDS LISTS in data
  lA = loadStrings("A.txt");
  lAd1 = loadStrings("Ad1.txt");
  lAd2 = loadStrings("Ad2.txt");
  lAd3 = loadStrings("Ad3.txt");
  lV = loadStrings("V.txt");
  lVt = loadStrings("Vt.txt");
  lP = loadStrings("P.txt");
  lPf = loadStrings("Pf.txt");//prefix
  lP0 = loadStrings("P0.txt");
  lPR0 = loadStrings("PR0.txt");
  lPR1 = loadStrings("PR1.txt");
  lN = loadStrings("N.txt"); //noun
  lN2 = loadStrings("N2.txt"); //noun second position
  lD = loadStrings("Duo.txt");
  lNf = loadStrings("Nf.txt");
  lMa = loadStrings("Ma.txt");
  lS = loadStrings("S.txt");
  lSc = loadStrings("Sc.txt");
  lASA = loadStrings("ASA.txt");
  lESS = loadStrings("ESS.txt");
  lABL = loadStrings("ABL.txt");
  lQU = loadStrings("QU.txt");//Question
  lISM = loadStrings("ism.txt");//
  lTION = loadStrings("Tion.txt");
  lDa = loadStrings("Duoa.txt");
  lNa = loadStrings("Na.txt");
  lAa = loadStrings("Aa.txt");
  lVa = loadStrings("Va.txt");
  lNfa = loadStrings("Nfa.txt");
  lPR0a = loadStrings("PR0a.txt");
  lPR1a = loadStrings("PR1a.txt");
  lStr = resplit2(loadStrings("str_art.txt"));
  //split according to space lines
  noLoop();
  mem=new String[10];

  //original seeds,   //Save seeds to a text file "seed.txt"
  lSeeds=ReadSeed(lSeed);
  Seeds=new String[nseed];
  for (int k = 0; k < nseed; k ++) {
    Seeds[k]=lSeeds[k];
  }
  saveStrings("seeds.txt", Seeds);

  //Choose a seed, and semantic field associated to it!
  lSeedL=resplit3(lSeedLong);
  lAssociated=extract3(lSeedL[0]); 
  lRelated=extract3(lSeedL[1]);
  lBroad=extract3(lSeedL[2]);
  saveStrings("0Assoc.txt", lAssociated);
  saveStrings("0Related.txt", lRelated);
  saveStrings("0Broad.txt", lBroad);
  //DIVIDE seedslongs into Associated, Related and Broad
  //ACTUAL SEEDS will consider for now: made up from seeds and associated words
  //BEWARE this list will be modified through the process. NEW SEEDS
  Seed=concat(Seeds, lAssociated);
  saveStrings("0Seeds+.txt", Seed);
  nseed=Seed.length;
}



void draw() {
  background(255);
  //GENERATE TITLE
    Seed=concat(Seeds, lAssociated);
    lastSeed=Seed;
    title=MakeTitle();
    text="";
    //8 sentences here but can choose
    text=OneSentence();
    text+=" "+OneSentence();
    text+=" "+OneSentence();
    text+=" "+OneSentence();
    text+=" "+OneSentence();
    text+=" "+OneSentence();
    text+=" "+OneSentence();
    text+=" "+OneSentence();
    text+=" "+OneSentence();
 

  //DISPLAY TITLE AND TEXT
  textFont(font1, 24);
  textAlign(RIGHT);
  fill(0);
  text(title, 100, 130, 1000, 1000);
  textFont(font2, 24);
  fill(0);
  text(text, 100, 170, 1000, 1000);   //x,y,witdth, height
  println(text);

  save("C:/Users/clair/Documents/artificial-curator/output.jpg");
  String[] textList= new String[2]; 
  textList[0]= title; 
  textList[1]= text;
  saveStrings("C:/Users/clair/Documents/artificial-curator/output.txt", textList);
}
//Here decide according nb characters, not number sentence
String FewSentences(int llmin) { 
  String text_="";
  text_=OneSentence();
  while (text_.length()<llmin) {
    text_+=" "+OneSentence();
  }
  return check(text_);
}


String OneSentence() { 
  //Pick a seed
  currentSeed=PickSeed();
  //Pick a structure
  int whichS=int(random(0, lStr.length));
  //split if choice with *
  lst=split(lStr[whichS], "*");
  int which2=floor(random(0, lst.length));

  //read the structure and it gives a sentence
  return check(read(lst[which2]));
}

String MakeTitle() {
  currentSeed=PickSeed();
  int ind=int(random(0, lTitle.length));
  String titl=check(read(lTitle[ind]));
  return titl;
}




//PICK a new seed and update seed lists and isA, isN, isV
//BEWARE, MODIFY SEED LIST!
String PickSeed() {
  int ntry=20; 
  isN=false;
  isA=false;
  isV=false;
  boolean found=false;
  String seed_="";
  while ((!(found))&&(ntry>0)) {
    ntry+=-1;
    int iii=floor(random(0, nseed));
    seed_=Seed[iii];
    if (!(seed_.equals(""))) {
      found=true;
      Seed[iii]="";//remove seed from this list
      usedSeed=false;//then has to use it
      tag=RiTa.getPosTags(seed_)[0];//first tag
      if (tag.equals("nn")) {
        isN=true;
      }//NOUN
      if (tag.equals("jj")) {
        isA=true;
      }//ADJECTIVE
      if (tag.equals("vb")) {
        isV=true;
      }//VERB
    }
  }
  if (!(found)) {   //reinitialise then
    Seed=concat(Seeds, lAssociated); 
    seed_=PickSeed();
  }//Say has used it if does not find any
  return seed_;
}


//
String[] ReadSeed(String[] lSee) {
  String ll1=lSee[0].substring(22, lSee[0].length()-3);
  String[] ll2=split(ll1, "},{");
  nseed=ll2.length;
  String[] llSee=new String[nseed];

  for (int k = 0; k < nseed; k ++) {
    llSee[k]=extractS(ll2[k], k);
  }
  return llSee;
}

String extractS ( String ss, int ii) {
  String sss="";
  boolean found=false;
  String[] lss= split(ss, "\"");
  int in=0;
  while (!(found)) {
    in++;
    if (lss[in].equals("description")) {
      found=true;
      sss=lss[in+2];
      String pstring=lss[in+5].substring(1, lss[in+5].length()-1);
      PSeed[ii]=float(pstring); //string to an int, update proba
    }
  }
  return sss;
}




void mousePressed() {
  redraw();
  mouse=true;
}
void mouseReleased() {
  mouse=false;
}
