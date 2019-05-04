import rita.*;

String[] lSeed;
int nseed;
String[] seeds;
String currentSeed;
String[] Seed;
boolean usedSeed;
String tag;
boolean isA; boolean isN; boolean isV;
float[] PSeed;

void setup() {
  size(1200, 700);//drawing surface size
  frameRate(5);
  
  lSeed = loadStrings("input.txt");
  
  noLoop();

  ReadSeed(lSeed);

  //Save seeds to a text file "seed.txt"
   seeds=new String[nseed];
      for (int k = 0; k < nseed; k ++) {
      seeds[k]=Seed[k];
    }
   saveStrings("C:/Users/clair/Documents/artificial-curator/data/seeds.txt", seeds);

   //Choose a seed
  currentSeed=PickSeed();
  
exit();
}



void draw() {

}


//Actualise Seed and PSeed  and nseed
void ReadSeed(String[] lSee) {
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
String PickSeed() {
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


String extractS ( String ss, int ii) {
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
      PSeed[ii]=float(pstring); //string to an int, update proba
    }
  }
  return sss;
}
