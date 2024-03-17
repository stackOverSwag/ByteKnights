package main;

import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {
  /*
  public Niveau text(String[] args) {
    
    Niveau lvl = readlvl("lvl0");
    
    for (ArrayList<String> salle : lvl) {

      ArrayList<String> forma = new ArrayList<String>(); // set forma
      forma.add(salle.get(1));
      forma.add(salle.get(2));
      Hosts host = new Hosts(forma, Integer.parseInt(salle.get(0))); // creat host
      //Nivau host = new Hosts(forma, Integer.parseInt(salle.get(0))); 
      int i = 3;

      while (isInteger(salle.get(i)) && i + 4 < salle.size()) { // set links
        host.link(new Link(Integer.parseInt(salle.get(i)), salle.get(i + 1), salle.get(i + 2), salle.get(i + 3),
            salle.get(i + 4)));
        i += 5;
      }

      while (i < salle.size() && salle.get(i).length() > 0 && salle.get(i).charAt(0) == 'X') { // set Exa
        host.add(new Exas(salle.get(i), host));
        i++;
      }

      while (i < salle.size() && salle.get(i).length() == 0) {// si pas de exas
        i++;
      }

      while (i < salle.size() && salle.get(i).length() > 0 && salle.get(i).charAt(0) == 'F') {// set files
        host.add(new Files(salle.get(i), null));
        i += 2;
      }

      host.printHost();// verifier

    }

  }

  private static boolean isInteger(String str) {
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
  /* 
  public static ArrayList<ArrayList<String>> readlvl(String name) {
    try (BufferedReader reader = new BufferedReader(new FileReader("./niveaux/" + name))) {

      ArrayList<ArrayList<String>> toutsalle = new ArrayList<ArrayList<String>>();
      String tmp = "";
      int l;
      String line = "";
      while ((line = reader.readLine()) != null) {
        ArrayList<String> salle = new ArrayList<String>();
        BufferedReader linereader = new BufferedReader(new StringReader(line));
        while ((l = linereader.read()) != -1) {
          if ((l < 58 && l > 47) || (l < 91 && l > 64) || (l < 123 && l > 96)) {
            tmp += (char) l;
          }
          if (l == ',') {
            salle.add(tmp);
            tmp = "";
          }
        }
        salle.add(tmp);
        toutsalle.add(salle);
      }
      return toutsalle;
    } catch (IOException e) {
      System.out.print("catch");
    }
    return null;
  }

      //fList.add(new Files(line.get(index+1), data(line.get(index+4))));
      //fList.get((index-9)/5).setCoordX(Integer.parseInt(line.get(index + 2)));
      //fList.get((index-9)/5).setCoordY(Integer.parseInt(line.get(index + 3)));
      //fList.get((index-9)/9).setType(get(index)); --> Pas encore fait
  */
   
  public Niveau readlvl(String name) {
    try (BufferedReader reader = new BufferedReader(new FileReader("./niveaux/" + name))){
    ArrayList<String> niveau = new ArrayList<String>();
    niveau.addAll(Arrays.asList(reader.readLine().split(",")));
    
    int h = Integer.parseInt(niveau.get(0));
    int w = Integer.parseInt(niveau.get(1));

    Exas exa1 = exa(niveau,3);
    Exas exa2 = exa(niveau,6);

    ArrayList<Files> fList = ficher(niveau,9);
    
    return new Niveau(exa1, exa2, fList);
    }catch (IOException e) {
      System.out.print("Erreur dans la lecture du niveau");
    }
    return null;
  }

  public Exas exa(ArrayList<String> line, int index){
    Exas exa = new Exas(line.get(index));
    exa.setCoordX(Integer.parseInt(line.get(index + 1)));
    exa.setCoordY(Integer.parseInt(line.get(index + 2)));
    return exa;
  }

  public ArrayList<Files> ficher(ArrayList<String> line, int index){

    ArrayList<Files> fList = new ArrayList<>();
    
    for(;index<line.size();index+=5){

      String[] data = line.get(index+4).split(" ");
      switch(line.get(index)){
        case "FIFO":
          FileFIFO fifo = new FileFIFO(line.get(index + 1), Integer.parseInt(line.get(index + 2)), Integer.parseInt(line.get(index + 3)));
          for(String val : data)
            fifo.push(val);
          fList.add(fifo);
          break;
        case "LIFO":
          FileLIFO lifo = new FileLIFO(line.get(index + 1), Integer.parseInt(line.get(index + 2)), Integer.parseInt(line.get(index + 3)));
          for(String val : data)
            lifo.push(val);
          fList.add(lifo);
          break;
        case "TD":
          FileTD td = new FileTD(line.get(index + 1), Integer.parseInt(line.get(index + 2)), Integer.parseInt(line.get(index + 3)));
          for(String val : data)
            td.push(val);
          fList.add(td);
          break;
      }
    }
    return fList;
  }

}
