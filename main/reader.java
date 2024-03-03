package projet;


import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;


public class reader{

  public static void main(String[] args){
    ArrayList<ArrayList<String>> lvl = readlvl("lvl0");
    System.out.println(lvl);

    for(ArrayList<String> salle : lvl){
      ArrayList<String> forma = new ArrayList<String>();
      forma.add(salle.get(1));
      forma.add(salle.get(2));

      Hosts host = new Hosts(forma,Integer.parseInt(salle.get(0)));
      /*
      for(int i=3;i<salle.size();){
        if(salle.get())
      }
      */
      host.printHost();

    }


  }





  public static ArrayList<ArrayList<String>> readlvl(String name){
    try(BufferedReader reader = new BufferedReader(new FileReader("./niveaux/"+name))){

      ArrayList<String> salle = new ArrayList<String>();
      ArrayList<ArrayList<String>> toutsalle = new ArrayList<ArrayList<String>>();
      String tmp ="";
      int l;
      String line="";
      while((line=reader.readLine())!=null){
        BufferedReader linereader = new BufferedReader(new StringReader(line));
        while((l=linereader.read())!=-1){
          if( (l<58&&l>47)||(l<91&&l>64)||(l<123&&l>96) ){
            tmp+=(char)l;
          }
          if(l==','){
            salle.add(tmp);
            tmp="";
          }
        }
        salle.add(tmp);
        toutsalle.add(salle);
      }
      return toutsalle;
    }catch(IOException e){
      System.out.print("catch");
    }
      return null;
  }


}
