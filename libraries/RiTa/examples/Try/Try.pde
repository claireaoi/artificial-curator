import rita.*;

/*
 * Note: Java-only, requires a local WordNet installation
 * Grab one here: http://rednoise.org/rita-archive/WordNet-3.1.zip
 * install it, and update the path below 
 */


String text = "Last Wednesday we decided to visit the zoo. We arrived the next morning after we breakfasted, cashed in our passes and entered. We walked toward the first exhibits. I looked up at a giraffe as it stared back at me. I stepped nervously to the next area. One of the lions gazed at me as he lazed in the shade while the others napped. One of my friends first knocked then banged on the tempered glass in front of the monkey's cage. They howled and screamed at us as we hurried to another exhibit where we stopped and gawked at plumed birds. After we rested, we headed for the petting zoo where we petted wooly sheep who only glanced at us but the goats butted each other and nipped our clothes when we ventured too near their closed pen. Later, our tired group nudged their way through the crowded paths and exited the turnstiled gate. Our car bumped, jerked and swayed as we dozed during the relaxed ride home.";
String word = "mouse";


void setup()
{
noLoop();
}

void draw() {
String nword= RiTa.pluralize(word);
println(nword);

}     