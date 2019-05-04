import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import rita.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TurnArt extends PApplet {


//TURN IMAGE (as input) INTO A CURATED ART PIECE
//Text displayed, and saved in output.txt



//FOR NOW ONLY use "association"

//Rita Library


//INITIALIZATION
int whichl1=1;
int whichl2=1;
int whichl3=1;
PFont font;
PFont font2;
PFont font3;
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

public void setup() {
  //drawing surface size
  frameRate(5);
  font = loadFont("Futura-14.vlw");//or DINBold or FuturaKoyu  // font=createFont("FuturaKoyu.ttf", 18);
  font3= createFont("FuturaLight.ttf", 20);
  font2= createFont("DIN.ttf", 18);//Or DINLight

  lTitle=loadStrings("str_title.txt");
  lSeed = loadStrings("input.txt");
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



public void draw() {
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
  textFont(font, 24);
  textAlign(RIGHT);
  fill(0);
  text(title, 100, 130, 1000, 1000);
  textFont(font3, 24);
  fill(0);
  text(text, 100, 170, 1000, 1000);   //x,y,witdth, height
  println(text);

  save("output.jpg");
  String[] textList= new String[2]; 
  textList[0]= title; 
  textList[1]= text;
  saveStrings("output.txt", textList);
}
//Here decide according nb characters, not number sentence
public String FewSentences(int llmin) { 
  String text_="";
  text_=OneSentence();
  while (text_.length()<llmin) {
    text_+=" "+OneSentence();
  }
  return check(text_);
}


public String OneSentence() { 
  //Pick a seed
  currentSeed=PickSeed();
  //Pick a structure
  int whichS=PApplet.parseInt(random(0, lStr.length));
  //split if choice with *
  lst=split(lStr[whichS], "*");
  int which2=floor(random(0, lst.length));

  //read the structure and it gives a sentence
  return check(read(lst[which2]));
}

public String MakeTitle() {
  currentSeed=PickSeed();
  int ind=PApplet.parseInt(random(0, lTitle.length));
  String titl=check(read(lTitle[ind]));
  return titl;
}




//PICK a new seed and update seed lists and isA, isN, isV
//BEWARE, MODIFY SEED LIST!
public String PickSeed() {
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
public String[] ReadSeed(String[] lSee) {
  String ll1=lSee[0].substring(22, lSee[0].length()-3);
  String[] ll2=split(ll1, "},{");
  nseed=ll2.length;
  String[] llSee=new String[nseed];

  for (int k = 0; k < nseed; k ++) {
    llSee[k]=extractS(ll2[k], k);
  }
  return llSee;
}

public String extractS ( String ss, int ii) {
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
      PSeed[ii]=PApplet.parseFloat(pstring); //string to an int, update proba
    }
  }
  return sss;
}




public void mousePressed() {
  redraw();
  mouse=true;
}
public void mouseReleased() {
  mouse=false;
}

//STUPID PROCEDURES

//CHECK procedure for capital letter, punctuation space etc

public String check (String ss) {
  String[] ll=split(ss, " ");
  String sf="";
  int i=0;
  String el2="";
  String el_=ll[0];
  String sub="";
  int beg=0;
  while (el_.equals(" ")) {i++;   el_=ll[i];
    }  
  //Capitalize
  if (ll[i].length()>1) {
    sub=ll[i].substring(beg, 1);
    while (sub.equals(" ")) { beg++;   sub=ll[i].substring(beg, 1);}
    sf+=ll[i].substring(beg, 1).toUpperCase()+ll[i].substring(beg+1);
  } else {
    sf+=ll[i].toUpperCase();
  }
  while (i < ll.length-2) {
    i++;
    el_=ll[i];
    if ((el_.equals("")) ||(el_.equals(" "))||(el_.equals("  "))) {
    }
    //For article 'an"
    else if (((el_.equals("a"))||(el_.equals("A"))) &&(ll[i+1].length()>1) &&((ll[i+1].substring(0, 1).equals("a"))||(ll[i+1].substring(0, 1).equals("e"))||(ll[i+1].substring(0, 1).equals("i"))||(ll[i+1].substring(0, 1).equals("u"))||(ll[i+1].substring(0, 1).equals("y")))) {
      i++;
      sf+=" "+el_+"n "+ll[i];
    } else if ((el_.equals(","))||(el_.equals("!"))||(el_.equals("."))||(el_.equals("?"))) {
      sf+=ll[i];
    } else {
      sf+=" "+ll[i];
    }
  }

  return sf;
}

 
//Split according to >>>
public String[] resplit3 (String[] ll) {
  String ss="";
  String[] LL;
  for (int i = 0; i < ll.length; i ++) {
      ss+=ll[i];
    }
  LL=split(ss, ">>>");//split according to >>> different choices
  return LL;
}

//Has to check no space
//remove last anbd first lines because empty (because?)
public String[] extract3 (String ss) {
  String[] LL; String[] LL_;
 LL_=cleanList(split(ss, ","));//remove empty lines and spaces before or after words from a list of words
  
  LL=new String[LL_.length-2];
    for (int i = 0; i < LL_.length-2; i ++) {
     LL[i]=LL_[i+1]; 
    }
  return LL;
}

public String[] cleanList(String[] ll) {
  String[] LL;
  String ss="";
  for (int i = 0; i < ll.length; i ++) {
     if (ll[i].equals("")) {}
     else if (ll[i].equals(" ")) {}
      else if (i==0) {ss+=cleanString(ll[i]);}
     else {ss+=">"+cleanString(ll[i]);}
    }
  LL=split(ss, ">");
  return LL;
}

//remove space before (and after?? for now just before) word . not empty
public String cleanString(String ww) {
  String www="";
 int ibegin=0; int iend=ww.length();
 while (ww.substring(ibegin,ibegin+1).equals(" ")) {
 ibegin++;
 }
  while (ww.substring(iend-1,iend).equals(" ")) {
 iend+=-1;
 }
 
 www=ww.substring(ibegin, iend);
  return www;
}

public String cleanStringEnd(String ww) {
  String www=ww;
  int len=ww.length();
  String first=www.substring(0,1);
 while (first.equals(" ")) {
 len+=-1; www=www.substring(1,len+1);//cut head of one.
 first=www.substring(0,1);
 }
  return www;
}

//Split according to space
public String[] resplit2 (String[] ll) {
  String ss="";
  String[] LL;
  for (int i = 0; i < ll.length; i ++) {
    if (ll[i].equals("")) {//if empty lines
      ss+="°";
    } 
    else {
      ss+=ll[i];
      ss+=" * ";
    }//reput lines all together but with symbols
  }
  LL=split(ss, "°");//split according to >>> different choices
  return LL;
}
//READ a structure, pick up words accordingly and return a text
//recursive as each element may be...
public String read(String ss) {
  String text_="";//empty one
  //Split structure into list things
  String[] sslist = split(ss, ' ');
  String[] ssl1;
  String[] ssl2;
  String elt_;
  String word_="";
  String[] elt;
  int imemo=0;

  for (int i = 0; i < sslist.length; i ++) {
    //For agregates elements may be choice: //...//...
    ssl1=split(sslist[i], "//"); //CHOOSE ONE HERE
    which0=floor(random(0, ssl1.length));
    //and then may be .../.../...
    ssl2=split(ssl1[which0], "/"); //DO ALL of them
    for (int k = 0; k < ssl2.length; k ++) {
      //Remains the case where the element has a number in it where is ..._i , number i between 0 and 9
      elt=split(ssl2[k], "_");
      elt_=elt[0];
      if (elt.length>1) {
        imemo = PApplet.parseInt(elt[1]);  //the  number?
        if (mem[imemo].equals("")) {
          word_=read0(elt_);
          mem[imemo]=word_;
        } else {//memory not empty
          word_=mem[imemo];
        }
      } else {
        word_=read0(elt_);
      }
      text_+=word_;
      text_+=" ";
    }
  }

  return text_;
}

//read something simple, without _i neither // neither /
public String read0(String ss) {
  String word_="";
  String elt_=ss;
  int index_;

  if (elt_.equals("")) {
  } else if (elt_.equals(" ")) {
  } else if (elt_.equals("N")) {
    if ((!(usedSeed))&&(isN)) { word_=currentSeed; usedSeed=true; }
    else {
    index_=PApplet.parseInt(random(0, lN.length));
    word_=lN[index_];
    }
  } else if (elt_.equals("Na")) {
    index_=PApplet.parseInt(random(0, lNa.length));
    word_=read(lNa[index_]);
  } else if (elt_.equals("Nfa")) {
    index_=PApplet.parseInt(random(0, lNfa.length));
    word_=read(lNfa[index_]);
  } else if (elt_.equals("N2")) {
    index_=PApplet.parseInt(random(0, lN2.length));
    word_=lN2[index_];
  } else if (elt_.equals("Nf")) {
    index_=PApplet.parseInt(random(0, lNf.length));
    word_=read(lNf[index_]);
  } else if (elt_.equals("N2s")) {
    index_=PApplet.parseInt(random(0, lN2.length));
    word_=RiTa.singularize(lN2[index_]);
  } else if (elt_.equals("N2p")) {
    index_=PApplet.parseInt(random(0, lN2.length));
    word_=RiTa.pluralize(lN2[index_]);
  } else if (elt_.equals("Ns")) {
     if ((!(usedSeed))&&(isN)) { word_=currentSeed; usedSeed=true; }
    else {
    word_=read("Ns0//Ns0//Ns0//Ns0//Na//Pf/Na");}
  } else if (elt_.equals("Ns0")) {
    index_=PApplet.parseInt(random(0, lN.length));
    word_=RiTa.singularize(lN[index_]);
  } else if (elt_.equals("Np")) {
    index_=PApplet.parseInt(random(0, lN.length));
    word_=RiTa.pluralize(lN[index_]);
  } else if (elt_.equals("Y")) {//three different possibilities
    word_=read("Nf//Nfa//Nf//Nfa//the/A/N//the/Na/P/N//the/Na/P/X//the/Ns/N2//the/A/Ns/N2//the/X/P/X//the/X/P0/X//the/Vg/X//X/,/X/and/X//both/X/and/X");
  } else if (elt_.equals("X")) {
    //EITHER N EITHER A N EITHER Ns N2 or A Ns N2and sometimes DUO (rarer)
    word_=read("N//Na//N/and/N//N2/P0/N//Pf/Na//Na/P0/N//A/A/N//A/N//Ns/N2//N2//N//A/N//Ns/N2//N2//N//A/N//Ns/N2//N//A/N//Ns/N2//N//A/N//Ns/N2//N//A/N//Ns/N2//N//A/N//Ns/N2//N//A/N//Ns/N2//N//A/N//Ns/N2//A/N2//A/N2//A/N2//D//Da");
  } else if (elt_.equals("Xs")) {
    //EITHER N EITHER A N EITHER NS N2
    word_=read("Ns//A/Ns//Ns/N2s//Na/P0/Ns//A/Na");
  } else if (elt_.equals("Xp")) {
    word_=read("Np//A/Np//Ns/N2p//Np/P0/Na");
  } else if (elt_.equals("ISM")) {
    index_=PApplet.parseInt(random(0, lISM.length));
    word_=lISM[index_];
  } else if (elt_.equals("QU")) {
    index_=PApplet.parseInt(random(0, lQU.length));
    word_=read(lQU[index_]);
  } else if (elt_.equals("TION")) {
    index_=PApplet.parseInt(random(0, lTION.length));
    word_=lTION[index_];
  } else if (elt_.equals("ASA")) {
    index_=PApplet.parseInt(random(0, lASA.length));
    word_=lASA[index_];
  } else if (elt_.equals("CON")) {
    index_=PApplet.parseInt(random(0, lCON.length));
    word_=lCON[index_];
  }
   else if (elt_.equals("XSED")) {//if noun!
    word_=read("SED//A/SED//SED/N2");
  }
  else if (elt_.equals("YSED")) {//three different possibilities
    word_=read("the/A/SED//the/A/SED/P0/N//the/A/SED/P/Na//the/A/Ns/SED//the/A/Na/SED//the/Vg/SED");
  }
  else if (elt_.equals("SED")) {//if noun!
   while (!(isN)) {Seed=lastSeed; currentSeed=PickSeed();}//try another one but reset since has not used it
    word_=currentSeed; currentSeed=PickSeed();
  }
  //variations on adjectives
  else if (elt_.equals("A")) {
     if ((!(usedSeed))&&(isA)) { word_=currentSeed; usedSeed=true; }
    else {
    word_=read("A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0//A0/A0//A0/A0//A0/A0/A0//Ad2/A0//A0//Ad2/A0//Ad2/A0//A0/A0//still/A0//A0/yet/A0//yet/A0//soon/A0//somehow/A0//already/A0");
    }
  } else if (elt_.equals("A0")) {
    index_=PApplet.parseInt(random(0, lA.length));
    word_=read(lA[index_]);
  } else if (elt_.equals("WA")) {//W and adjective
    word_=read("Ad2/V//Ad2/Vt/X//V/Ad3//Vt/X/Ad3");
  } else if (elt_.equals("W")) {
    word_=read("V//Vt/X");
  } else if (elt_.equals("Wd")) {
    word_=read("Vd//Vtd/X");
  } else if (elt_.equals("Wg")) {
    word_=read("Vg//Vtg/X");
  } else if (elt_.equals("XWg")) {
    word_=read("Wg//X//X");
  } else if (elt_.equals("D")) {
    index_=floor(random(0, lD.length));
    word_=lD[index_];
  } else if (elt_.equals("Da")) {//DUO
    index_=floor(random(0, lDa.length));
    word_=read(lDa[index_]);
  } else if (elt_.equals("V")) {
     if ((!(usedSeed))&&(isV)) { word_=Conj(currentSeed); usedSeed=true; }//SHOULD TEST IF TRANSITIVE VERB OR NOT
    else {   
    index_=floor(random(0, lV.length));
    word_=Conj(lV[index_]);
  }
  } else if (elt_.equals("Vt")) {
    index_=floor(random(0, lVt.length));
    word_=Conj(lVt[index_]);
  } else if (elt_.equals("Vd")) {//PARTICIPE PASSE
    index_=floor(random(0, lV.length));
    word_=getPastParticiple(lV[index_]);
  } else if (elt_.equals("Va")) {
    index_=floor(random(0, lVa.length));
    word_=Conj(lVa[index_]);
  }
    else if (elt_.equals("Vad")) {
    index_=floor(random(0, lVa.length));
    word_=getPastParticiple(lVa[index_]);
  }
  else if (elt_.equals("Vag")) {
    index_=floor(random(0, lVa.length));
    word_=getPresentParticiple(lVa[index_]);
  } else if (elt_.equals("Vtd")) {//PARTICIPE PASSE transitive verb
    index_=floor(random(0, lVt.length));
    word_=getPastParticiple(lVt[index_]);
  } else if (elt_.equals("Vg")) {
    index_=floor(random(0, lV.length));
    word_=getPresentParticiple(lV[index_]);
  } else if (elt_.equals("Vtg")) {
    index_=PApplet.parseInt(random(0, lVt.length));
    word_=getPresentParticiple(lVt[index_]);
  } else if ((elt_.equals("MA"))||(elt_.equals("MA"))) {
    index_=floor(random(0, lMa.length));
    word_=lMa[index_];
  } else if (elt_.equals("P")) {
    index_=floor(random(0, lP.length));
    word_=lP[index_];
  } else if (elt_.equals("P0")) {
    index_=floor(random(0, lP0.length));
    word_=lP0[index_];
  } else if (elt_.equals("Pf")) {
    index_=floor(random(0, lPf.length));
    word_=lPf[index_];
  } else if (elt_.equals("Ad1")) {
    index_=floor(random(0, lAd1.length));
    word_=lAd1[index_];
  } else if (elt_.equals("Ad2")) {
    index_=floor(random(0, lAd2.length));
    word_=lAd2[index_];
  } else if (elt_.equals("Ad3")) {
    index_=floor(random(0, lAd3.length));
    word_=lAd3[index_];
  } else if (elt_.equals("D")) {//Double!!
    index_=floor(random(0, lD.length));
    word_=read(lD[index_]);
  } else if (elt_.equals("S")) {
    index_=floor(random(0, lS.length));
    word_=lS[index_];
  } else if (elt_.equals("Sc")) {
    index_=floor(random(0, lSc.length));
    word_=lSc[index_];
  } else if (elt_.equals("Ns")) {
    index_=floor(random(0, lN.length));
    word_= RiTa.singularize(lN[index_]);
  } else if (elt_.equals("Np")) {
    index_=floor(random(0, lN.length));
    word_= RiTa.pluralize(lN[index_]);
  } else if (elt_.equals("ESS")) {
    index_=floor(random(0, lESS.length));
    word_=lESS[index_];
  } else if (elt_.equals("ABL")) {
    index_=floor(random(0, lABL.length));
    word_= lABL[index_];
  } else if (elt_.equals("PR0")) {
    index_=PApplet.parseInt(random(0, lPR0.length));
    word_=read(lPR0[index_]);
  } else if (elt_.equals("PR1")) {
    word_=read("PR10//PR10//PR10//PR10//PR10//PR10//Ad1/PR10//Ad2/PR10//Ad2/PR10");
  } else if (elt_.equals("PR10")) {
    index_=PApplet.parseInt(random(0, lPR1.length));
    word_=read(lPR1[index_]);
  } else if (elt_.equals("PR1a")) {
    word_=read("PR1a0//PR1a0//PR1a0//PR1a0//PR1a0//PR1a0//Ad1/PR1a0//Ad2/PR1a0//Ad2/PR1a0");
  } else if (elt_.equals("PR1a0")) {
    index_=PApplet.parseInt(random(0, lPR1a.length));
    word_=read(lPR1a[index_]);
  } else if (elt_.equals("PR0a")) {
    index_=PApplet.parseInt(random(0, lPR0a.length));
    word_=read(lPR0a[index_]);
  } else if (elt_.equals("Aa")) {
    index_=PApplet.parseInt(random(0, lAa.length));
    word_=read(lAa[index_]);
  } else if (elt_.equals("PRO")) {
    word_=read("S/V//S/Vt/X//X/V//N/Vt/X//S/V/P0/X");
  } else {
    word_=elt_;
  } //else add text as it is
  
lastword=word_;//update last word
  return word_;
  
}


//get present participle and past participle, in case verb with particles:
public String getPresentParticiple(String ss) {
  //case where verb with a particle:
  String [] ll=split(ss, " ");
  String sf="";
  sf+=RiTa.getPresentParticiple(ll[0]);
    if (ll.length>1) {
      for (int i = 1; i < ll.length; i ++) {
        sf+=" "+read(ll[i]);
      }
  }
  return sf;
}

//Say if singular or not. you does not count beware, cas noun too!
public boolean isSingularS(String ss){
  //in case different words, has to split
 boolean isS=false;
 String [] ll=split(ss, " ");
 String ss_=ll[ll.length-1];
 int nn=ss_.length();
      
 if ((ss_.equals("")) && (ll.length>1)){ ss_=ll[ll.length-2];}//in case last empty
 if (ss_.equals("")) {isS=false;}//by default say not singular if do not know last word
 else if ((ss_.equals("he"))||(ss_.equals("she"))||(ss_.equals("it"))||(ss_.equals("somebody"))||(ss_.equals("anybody"))) {isS=true;} 
 else if (!(RiTa.getPosTags(ss_)[0].equals("nn"))) {isS=false;}//test if nound
 else {
   
 if ((ss_.equals("you"))||(ss_.equals("we"))||(ss_.equals("they"))||(ss_.equals("people"))||(ss_.equals("to"))) {isS=false;}
 else if (ss_.substring(nn).equals("s")) { isS=false;} 
 else {isS=true;}
 }
 return isS;
}
//Add an s at the end of verb possibly if last word...
public String Conj(String ss) {
String [] ll=split(ss, " ");
String sf=ss;//by default
if (isSingularS(lastword)) {  
 if (ll[0].substring(ll[0].length()).equals("s"))  {sf=ll[0];}//case where already end by s
 else {sf=ll[0]+"s";}
  if (ll.length>1) {
      for (int i = 1; i < ll.length; i ++) {
        sf+=" "+read(ll[i]);
      }
  }}
  return read(sf);
}

public String getPastParticiple(String ss) {
  //case where verb with a particle:
  String [] ll=split(ss, " ");
  String sf="";
  sf+=RiTa.getPastParticiple(ll[0]);
  if (ll.length>1) {//the rest does not change
      for (int i = 1; i < ll.length; i ++) {
        sf+=" "+read(ll[i]);
      }
  }
  return sf;
}
  public void settings() {  size(2000, 1500); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TurnArt" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
