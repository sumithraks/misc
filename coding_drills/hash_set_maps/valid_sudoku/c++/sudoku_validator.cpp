#include <iostream>
#include <set>

using namespace std;
bool sudoku_valid(vector<vector<char>>& board) {
    set<char> rows[9];
    set<char> cols[9];
    set<char> blocks[9];

    for (int r=0; r < board.size();r++) {
        vector<char> column = board[r];
        for (int c=0; c< board.size();c++) {
            if (column[c]=='.')
                continue;
            if (rows[r].find(column[c])!=rows[r].end()) {
                return false;
            }
            rows[r].insert(column[c]);
            if (cols[c].find(column[c])!=cols[c].end()) {
                return false;
            }
            cols[c].insert(column[c]);
            int index=(r/3)*3+c/3;
            if (blocks[index].find(column[c])!=blocks[index].end()) {
                return false;
            }
            blocks[index].insert(column[c]);
        }
    }
    return true;
}