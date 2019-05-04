//READ a structure, pick up words accordingly and return a text
//recursive as each element may be...
String read(String ss) {
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
        imemo = int(elt[1]);  //the  number?
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
String read0(String ss) {
  String word_="";
  String elt_=ss;
  int index_;

  if (elt_.equals("")) {
  } else if (elt_.equals(" ")) {
  } else if (elt_.equals("N")) {
    if ((!(usedSeed))&&(isN)) { word_=currentSeed; usedSeed=true; }
    else {
    index_=int(random(0, lN.length));
    word_=lN[index_];
    }
  } else if (elt_.equals("Na")) {
    index_=int(random(0, lNa.length));
    word_=read(lNa[index_]);
  } else if (elt_.equals("Nfa")) {
    index_=int(random(0, lNfa.length));
    word_=read(lNfa[index_]);
  } else if (elt_.equals("N2")) {
    index_=int(random(0, lN2.length));
    word_=lN2[index_];
  } else if (elt_.equals("Nf")) {
    index_=int(random(0, lNf.length));
    word_=read(lNf[index_]);
  } else if (elt_.equals("N2s")) {
    index_=int(random(0, lN2.length));
    word_=RiTa.singularize(lN2[index_]);
  } else if (elt_.equals("N2p")) {
    index_=int(random(0, lN2.length));
    word_=RiTa.pluralize(lN2[index_]);
  } else if (elt_.equals("Ns")) {
     if ((!(usedSeed))&&(isN)) { word_=currentSeed; usedSeed=true; }
    else {
    word_=read("Ns0//Ns0//Ns0//Ns0//Na//Pf/Na");}
  } else if (elt_.equals("Ns0")) {
    index_=int(random(0, lN.length));
    word_=RiTa.singularize(lN[index_]);
  } else if (elt_.equals("Np")) {
    index_=int(random(0, lN.length));
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
    index_=int(random(0, lISM.length));
    word_=lISM[index_];
  } else if (elt_.equals("QU")) {
    index_=int(random(0, lQU.length));
    word_=read(lQU[index_]);
  } else if (elt_.equals("TION")) {
    index_=int(random(0, lTION.length));
    word_=lTION[index_];
  } else if (elt_.equals("ASA")) {
    index_=int(random(0, lASA.length));
    word_=lASA[index_];
  } else if (elt_.equals("CON")) {
    index_=int(random(0, lCON.length));
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
    index_=int(random(0, lA.length));
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
    index_=int(random(0, lVt.length));
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
    index_=int(random(0, lPR0.length));
    word_=read(lPR0[index_]);
  } else if (elt_.equals("PR1")) {
    word_=read("PR10//PR10//PR10//PR10//PR10//PR10//Ad1/PR10//Ad2/PR10//Ad2/PR10");
  } else if (elt_.equals("PR10")) {
    index_=int(random(0, lPR1.length));
    word_=read(lPR1[index_]);
  } else if (elt_.equals("PR1a")) {
    word_=read("PR1a0//PR1a0//PR1a0//PR1a0//PR1a0//PR1a0//Ad1/PR1a0//Ad2/PR1a0//Ad2/PR1a0");
  } else if (elt_.equals("PR1a0")) {
    index_=int(random(0, lPR1a.length));
    word_=read(lPR1a[index_]);
  } else if (elt_.equals("PR0a")) {
    index_=int(random(0, lPR0a.length));
    word_=read(lPR0a[index_]);
  } else if (elt_.equals("Aa")) {
    index_=int(random(0, lAa.length));
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
String getPresentParticiple(String ss) {
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
boolean isSingularS(String ss){
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
String Conj(String ss) {
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

String getPastParticiple(String ss) {
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
