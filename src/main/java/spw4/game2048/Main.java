package spw4.game2048;

import java.util.*;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String input;

    Game game = new GameImpl();
    game.initialize();
    System.out.println(game);

    while (!game.isOver()) {
      System.out.print("command [w, a, s, d, (r)estart, (q)uit, (h)elp] > ");
      input = scanner.nextLine();

      switch (input) {
        case "w":
          game.move(Direction.up);
          break;
        case "a":
          game.move(Direction.left);
          break;
        case "s":
          game.move(Direction.down);
          break;
        case "d":
          game.move(Direction.right);
          break;
        case "r":
          game.initialize();
          break;
        case "q":
          System.out.println("Ok, bye.");
          return;
        case "h":
          printHelp();
          break;
        default:
          System.out.println("Unknown command");
          break;
      }
      System.out.println(game);
    }
    System.out.println(game.isWon() ? "You win!!! :)" : "You lose. :(");
    System.out.println("Your score: " + game.getScore());
  }

  private static void printHelp() {
    System.out.println();
    System.out.println("Available commands:");
    System.out.println("-------------------");
    System.out.println("w --> move up");
    System.out.println("a --> move left");
    System.out.println("s --> move down");
    System.out.println("d --> move right");
    System.out.println("r --> restart game");
    System.out.println("q --> quit game");
    System.out.println("h --> show help");
  }
}
