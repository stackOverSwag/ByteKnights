package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
  private Niveau level;
  private ArrayList<Parser> parsers;

  public Game(Niveau level) {
    this.level = level;
    this.parsers = new ArrayList<Parser>();
    this.parsers.add(new Parser(this.level, this.level.getExa1()));
    this.parsers.add(new Parser(this.level, this.level.getExa2()));
  }

  public void step() {
    for (Parser p : this.parsers) {
      try {
        p.step();

      } catch (IllegalArgumentException e) {
        System.out.println("line " + p.getCurLine() + ": " + e.getMessage());
      }
    }
  }

}
