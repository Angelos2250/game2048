package spw4.game2048;

import java.util.Arrays;
import java.util.Random;

public class GameImpl implements Game {
  private int score = 0;
  private int moves = 0;
  private int[][] board = new int[4][4];
  private final int GAME_WON = 2048;

  public GameImpl() {
  }

  public GameImpl(int[][] board) {
    this.board = board;
    this.score = score;
    this.moves = moves;
  }

  public int getMoves() {
    return moves;
  }

  public int getScore() {
    return score;
  }

  public int getValueAt(int x, int y) {
    return board[x][y];
  }

  public boolean isOver() {
    return !movesAvailable() || isWon();
  }

  public boolean isWon() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j] == GAME_WON) {
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Moves: %d    Score: %d\n", moves, score));

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (board[i][j] == 0) {
          sb.append(".");
        } else {
          sb.append(board[i][j]);
        }
        sb.append("     ");
      }
      sb.append("\n");
    }

    return sb.toString();
  }

  public void initialize() {
    Arrays.stream(board).forEach(a -> Arrays.fill(a, 0));
    spawnTile();
    spawnTile();
  }

  public void move(Direction direction) {
    boolean horizontally = direction == Direction.left || direction == Direction.right;
    int row = 0;
    int col = 0;

    ++moves;

    for (int i = 0; i < board.length; i++) {
      int[] numbers = new int[4];
      for (int j = 0; j < board[i].length; j++) {
        row = horizontally ? i : j;
        col = horizontally ? j : i;

        if (horizontally) {
          numbers[col] = getValueAt(row, col);
        } else {
          numbers[row] = getValueAt(row, col);
        }
      }

      shiftTiles(numbers, direction);

      if (horizontally) {
        for (int j = 0; j < numbers.length; j++) {
          board[i][j] = numbers[j];
        }
      } else {
        for (int j = 0; j < numbers.length; j++) {
          board[j][i] = numbers[j];
        }
      }
    }
    spawnTile();
  }

  private void shiftTiles(int[] numbers, Direction direction) {
    int[] merged = {0, 0, 0, 0};
    int count = numbers.length - 1;

    while (count > 0) {
      // swipe left and up
      if (direction == Direction.left || direction == Direction.up) {
        for (int i = 0; i < numbers.length - 1; ++i) {
          if (numbers[i] == 0) {
            if (merged[i + 1] == 1) {
              merged[i] = 1;
              merged[i + 1] = 0;
            }

            numbers[i] = numbers[i + 1];
            numbers[i + 1] = 0;
          } else if (numbers[i + 1] == numbers[i] && merged[i] != 1 && merged[i + 1] != 1) {
            numbers[i] *= 2;
            numbers[i + 1] = 0;
            merged[i] = 1;
            score += numbers[i];
            break;
          }
        }
      } else {
        // swipe right and down
        for (int i = numbers.length - 1; i > 0; --i) {
          if (numbers[i] == 0) {
            if (merged[i - 1] == 1) {
              merged[i] = 1;
              merged[i - 1] = 0;
            }

            numbers[i] = numbers[i - 1];
            numbers[i - 1] = 0;
          } else if (numbers[i - 1] == numbers[i] && merged[i] != 1 && merged[i - 1] != 1) {
            numbers[i] *= 2;
            numbers[i - 1] = 0;
            merged[i] = 1;
            score += numbers[i];
            break;
          }
        }
      }
      count--;
    }
  }

  private boolean movesAvailable() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j] == 0) {
          return true;
        }
      }
    }

    for (int i = 0; i < board.length - 1; i++) {
      for (int j = 0; j < board[i].length - 1; j++) {
        if (board[i][j] == board[i][j + 1] || board[i][j] == board[i + 1][j]) {
          return true;
        }
      }
    }

    return false;
  }

  private void spawnTile() {
    int x = (int) (Math.random() * 4);
    int y = (int) (Math.random() * 4);

    while (board[x][y] != 0) {
      x = (int) (Math.random() * 4);
      y = (int) (Math.random() * 4);
    }

    int min = 0;
    int max = 100;
    Random random = new Random();

    int value = random.nextInt(max + min) + min;

    if (value < 90) {
      board[x][y] = 2;
    } else {
      board[x][y] = 4;
    }
  }
}
