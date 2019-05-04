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

public class seed extends PApplet {



String[] lSeed;
int nseed;
String[] seeds;
String currentSeed;
String[] Seed;
boolean usedSeed;
String tag;
boolean isA; boolean isN; boolean isV;
float[] PSeed;

public void setup() {
  //drawing surface size
  frameRate(5);
  
  lSeed = loadStrings("input.txt");
  
  noLoop();

  ReadSeed(lSeed);

  //Save seeds to a text file "seed.txt"
   seeds=new String[nseed];
      for (int k = 0; k < nseed; k ++) {
      seeds[k]=Seed[k];
    }
   saveStrings("C:/Users/clair/Documents/TurnArt/data/seeds.txt", seeds);

   //Choose a seed
  currentSeed=PickSeed();

}



public void draw() {

}


//Actualise Seed and PSeed  and nseed
public void ReadSeed(String[] lSee) {
  String ll1=lSee[0].substring(22, lSee[0].length()-3);
  String[] ll2=split(ll1, "},{");
  nseed=ll2.length; println(nseed);
 Seed= new String[nseed];
  for (int k = 0; k < nseed; k ++) {
    Seed[k]=extractS(ll2[k], k);
  }
}


//PICK a new seed and update seed lists and isA, isN, isV
//BEWARE, MODIFY SEED LIST!
public String PickSeed() {
  int ntry=40;
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
      else if (tag.equals("jj")) {
        isA=true;
      }//ADJECTIVE
      else if (tag.equals("vb")) {
        isV=true;
      }//VERB
    }
  }

  return seed_;
}


public String extractS ( String ss, int ii) {
  String sss="";
  boolean found=false;
  String[] lss= split(ss, "\"");
  int in=0;
   PSeed= new float[nseed];
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
  public void settings() {  size(1200, 700); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "seed" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
