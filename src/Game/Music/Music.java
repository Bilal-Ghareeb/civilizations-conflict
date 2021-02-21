


package Game.Music;

import java.io.File;
import java.io.IOException;
//import java.util.Scanner;
import javax.sound.sampled.*;
import java.util.Random;
public class Music {

    
    
private Random quote;
private int rand , saverand;

private File mainfile  ,mousefile  , solider1file ,solider2file  ,solider3file , builder1file,builder2file,builder3file,bgfile  ;

    private Clip main,mouseclick,s1,s2,s3,b1,b2,b3,bg;
    
    private AudioInputStream mainstream,mousestream,s1stream,s2stream,s3stream, b1stream,b2stream,b3stream,bgstream;
    
public Music () throws IOException, UnsupportedAudioFileException, LineUnavailableException{
//files
 mainfile = new File("Main_menu_theme.wav");
 mousefile = new File("mouse click.wav");
 bgfile = new File("back ground music.wav");
 
 solider1file = new File("Orders.wav");
 solider2file = new File("Ready For action.wav");
 solider3file = new File("Say The Word !.wav");
 
 builder1file = new File("yes my lord.wav");
 builder2file = new File("Ready to work.wav");
 builder3file = new File("More Work !.wav");
 
//stream
  mainstream = AudioSystem.getAudioInputStream(mainfile);
  mousestream = AudioSystem.getAudioInputStream(mousefile);
  s1stream = AudioSystem.getAudioInputStream(solider1file);
  s2stream = AudioSystem.getAudioInputStream(solider2file);
  s3stream = AudioSystem.getAudioInputStream(solider3file);
  b1stream = AudioSystem.getAudioInputStream(builder1file);
  b2stream = AudioSystem.getAudioInputStream(builder2file);
  b3stream = AudioSystem.getAudioInputStream(builder3file);
  bgstream = AudioSystem.getAudioInputStream(bgfile);
  
  
//get clip
  
 main = AudioSystem.getClip();
 mouseclick = AudioSystem.getClip();
 s1 = AudioSystem.getClip();
 s2 = AudioSystem.getClip();
 s3 = AudioSystem.getClip();
 b1 = AudioSystem.getClip();
 b2 = AudioSystem.getClip();
 b3 = AudioSystem.getClip();
 bg = AudioSystem.getClip();
      
 //open mousic files 
 
 mouseclick.open(mousestream);
 
          s1.open(s1stream);
          s2.open(s2stream);
          s3.open(s3stream);
 
          b1.open(b1stream);
          b2.open(b2stream);
          b3.open(b3stream);
          
          bg.open(bgstream);
          
      main.open(mainstream);
          
 saverand=1;
 
}

//every tarck played and stoped method



//back ground music

public void playbgmusic() throws IOException, LineUnavailableException{
   
   bg.start();
}

public void stopbgmusic() throws IOException, LineUnavailableException{
  // bg.setMicrosecondPosition(0);
  
   bg.stop();
}


//main menu
public void playmainmenumusic() throws IOException, LineUnavailableException{
  
   main.start();
}



public void stopmainmenumusic() throws IOException, LineUnavailableException{
  // main.setMicrosecondPosition(0);
 
 main.stop();
 
}



//mouse click sound play 
public void playmousemusic() throws IOException, LineUnavailableException{
  
  mouseclick.start();
 
}

//get random number for units quote
public int rando(){
    
 quote = new Random();
    
 rand =1+quote.nextInt(3); 
   saverand=rand;
  return rand ; 
  
} 



//Builder quote
public void playbuildermusic() throws IOException, LineUnavailableException{
    
    switch (rando()){
        
      case 1: 
   b1.start();break;
      
      
       case 2: 
   b2.start();break;
      
      case 3:
         
          b3.start();break;
    
    }
    
    
}



//Solider quote
public void playsolidermusic() throws IOException, LineUnavailableException{
  
    switch (rando()){
        
         case 1: 
   s1.start();break;
      
      
       case 2: 
   s2.start();break;
      
      case 3:
         
          s3.start();break;
    
    }
    
    
    }


//reseting units music so it can be played again (cant play them again unless we reset them)
public void resetsolidermusic(){
     
    switch (saverand){
        
         case 1: 
   s1.setMicrosecondPosition(0);break;
      
      
       case 2: 
   s2.setMicrosecondPosition(0);break;
      
      case 3:
         
   s3.setMicrosecondPosition(0);break;
    
    }
   
}

public void resetbuildermusic(){
    
    
 switch (saverand){
        
         case 1: 
   b1.setMicrosecondPosition(0);break;
      
      
       case 2: 
   b2.setMicrosecondPosition(0);break;
      
      case 3:
         
          b3.setMicrosecondPosition(0);break;
    
    }
    

    
    
}
}

