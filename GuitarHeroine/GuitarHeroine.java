public class GuitarHeroine {
  
    public static void main(String[] args) {

      // Initialize 37 GuitarStrings
      String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
      GuitarString[] gStrings = new GuitarString[keyboard.length()];
      for (int i=0; i<gStrings.length; i++) {
        gStrings[i] = new GuitarString(440 * Math.pow(1.05956, i-24));
      }

      StdDraw.picture(0.5,0.5,"keyboard.png");
      
      // the main input loop
      while (true) {

        // check if the user has typed a key, and, if so, process it
        if (StdDraw.hasNextKeyTyped()) {
 
          // the user types this character
          char key = StdDraw.nextKeyTyped();

          int index = keyboard.indexOf(key);
          if (index >= 0) {  // Valid key was pressed
            gStrings[index].pluck();
          }
          
        }

        double sample = 0.0;
        for (int i=0; i<gStrings.length; i++) sample += gStrings[i].sample();
        StdAudio.play(sample);
        for (int i=0; i<gStrings.length; i++) gStrings[i].tic();
      }
    }

}
