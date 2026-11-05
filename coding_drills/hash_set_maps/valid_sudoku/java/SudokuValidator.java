import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class SudokuValidator {
    public boolean isValidSudoku(char[][] board) {
        HashSet<Character>[] rows = new HashSet[9];
        HashSet<Character>[] cols = new HashSet[9];
        HashSet<Character>[] blocks = new HashSet[9];

        for (int r = 0; r < 9; r++) {
            rows[r] = new HashSet<Character>();
            cols[r] = new HashSet<Character>();
            blocks[r] = new HashSet<Character>();
        }

        for (int r = 0; r < 9; r++) {
            for (int c=0; c<9;c++) {
                if (board[r][c] == '.') {
                    continue;
                }
                if (!(rows[r]).add(board[r][c])) 
                    return false;
                if (!(cols[c]).add(board[r][c])) 
                    return false;
                int block = (r/3)*3+c/3;
                if (!blocks[block].add(board[r][c])) 
                    return false;
            }
        }
        return true;
    }
}
