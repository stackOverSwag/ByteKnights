package projet;

import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {

  public static void main(String[] args) {
    ArrayList<ArrayList<String>> lvl = readlvl("lvl0");
    // System.out.println(lvl);

    for (ArrayList<String> salle : lvl) {

      ArrayList<String> forma = new ArrayList<String>(); // set forma
      forma.add(salle.get(1));
      forma.add(salle.get(2));
      Hosts host = new Hosts(forma, Integer.parseInt(salle.get(0))); // creat host

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

}
