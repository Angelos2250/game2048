package spw4.game2048;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class Tests2048 {
  @DisplayName("game.getMoves should return the number of moves")
  @Test
  void getMovesShouldReturnNumberOfMoves() {
    Game game = new GameImpl();
    game.initialize();
    game.move(Direction.up);
    game.move(Direction.left);
    game.move(Direction.down);
    game.move(Direction.right);

    assertEquals(4, game.getMoves());
  }

  @DisplayName("game.move increases the Score")
  @Test
  void moveIncreasesScore() {
    int[][] board = new int[][] {
        {2, 2, 0, 2},
        {0, 0, 0, 0},
        {0, 0, 4, 0},
        {0, 2, 0, 0}
    };
    Game game = new GameImpl(board);
    game.move(Direction.up);


    assertEquals(4, game.getScore());
  }

  @DisplayName("game.toString returns non empty string")
  @Test
  void toStringReturnsNonEmptyString() {
    int[][] board = new int[][] {
        {2, 2, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };
    GameImpl sut = new GameImpl(board);

    assertNotEquals("", sut.toString());
  }

  @DisplayName("game.isWon returns true when game is won")
  @Test
  void isWonReturnsTrueWhenGameIsWon() {
    int[][] board = new int[][] {
        {4, 2, 2, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 2048}
    };
    GameImpl sut = new GameImpl(board);

    assertTrue(sut.isWon());
  }

  @DisplayName("game.move should move the board in the given direction")
  @Test
  void moveShouldMoveTheBoard() {
    int[][] result = new int[][]{
        {4, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };

    int[][] board = new int[][] {
        {2, 2, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };
    GameImpl sut = new GameImpl(board);

    sut.move(Direction.left);

    assertEquals(result[0][0], sut.getValueAt(0, 0));
  }

  @DisplayName("game.move should move the board horizontally in the given direction")
  @ParameterizedTest(name = "direction = {0}, col = {1}, row = {2}, result = {3}")
  @CsvSource({"left, 0, 0, 8", "right, 3, 0, 4"})
  void moveShouldMoveTheBoardHorizontally(Direction direction, int col, int row, int result) {
    int[][] board = new int[][] {
        {4, 4, 2, 2},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };
    GameImpl sut = new GameImpl(board);

    sut.move(direction);

    assertEquals(result, sut.getValueAt(row, col));
  }

  @DisplayName("game.move should move the board vertically in the given direction")
  @ParameterizedTest(name = "direction = {0}, col = {1}, row = {2}, result = {3}")
  @CsvSource({"up, 0, 0, 8", "down, 0, 3, 4"})
  void moveShouldMoveTheBoardVertically(Direction direction, int col, int row, int result) {
    int[][] board = new int[][] {
        {4, 0, 0, 0},
        {4, 0, 0, 0},
        {2, 0, 0, 0},
        {2, 0, 0, 0}
    };
    GameImpl sut = new GameImpl(board);

    sut.move(direction);

    assertEquals(result, sut.getValueAt(row, col));
  }

  @DisplayName("game.getValueAt should return the value at the given coordinates")
  @Test
  void getValueAtReturnsValueAtGivenCoordinates() {
    int[][] board = new int[][] {
        {2, 2, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };
    GameImpl sut = new GameImpl(board);

    assertEquals(2, sut.getValueAt(0, 0));
  }

  @DisplayName("game.isOver should return true when game is over")
  @Test
  void isOverShouldReturnTrueWhenGameIsOver() {
    int[][] board = new int[][] {
        {2, 4, 8, 16},
        {32, 64, 128, 256},
        {512, 1024, 2, 4},
        {8, 16, 32, 64}
    };
    GameImpl sut = new GameImpl(board);

    assertTrue(sut.isOver());
  }

  @DisplayName("game.isOver should return false when game is not over")
  @Test
  void isOverShouldReturnFalseWhenGameIsNotOver() {
    int[][] board = new int[][] {
        {4, 2, 2, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 64}
    };
    GameImpl sut = new GameImpl(board);

    assertFalse(sut.isOver());
  }
}

