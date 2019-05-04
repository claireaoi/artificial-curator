
//STUPID PROCEDURES

//CHECK procedure for capital letter, punctuation space etc

String check (String ss) {
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
String[] resplit3 (String[] ll) {
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
String[] extract3 (String ss) {
  String[] LL; String[] LL_;
 LL_=cleanList(split(ss, ","));//remove empty lines and spaces before or after words from a list of words
  
  LL=new String[LL_.length-2];
    for (int i = 0; i < LL_.length-2; i ++) {
     LL[i]=LL_[i+1]; 
    }
  return LL;
}

String[] cleanList(String[] ll) {
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
String cleanString(String ww) {
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

String cleanStringEnd(String ww) {
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
String[] resplit2 (String[] ll) {
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
