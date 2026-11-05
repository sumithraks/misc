#include <vector>
#include <iostream>

using namespace std;

void setZeroes(vector<vector<int>>& matrix) {
    bool first_row_zero=false;
    bool first_col_zero=false;

    vector<int> row = matrix[0];
    cout << "Row size " << row.size() << endl;
    for (int col=0; col < row.size();++col) {
        if (row[col]==0) {
            first_row_zero=true;
            break;
        }
    }

    cout << "Matrix size " << matrix.size() << endl;
    for (int r=0; r < matrix.size();++r) {
        if (matrix[r][0]==0) {
            first_col_zero=true;
            break;
        }
    }
    
    for (int r=1;r<matrix.size();r++) {
        for (int c=1;c<matrix[r].size();c++) {
            if (matrix[r][c]==0) {
                matrix[0][c]=0;
                matrix[r][0]=0;
            }
        }
    }

    for (int r=1;r<matrix.size();r++) {
        for (int c=1;c<matrix[r].size();c++) {
            if (matrix[0][c]==0 || matrix[r][0]==0) {
                matrix[r][c]=0;
            }
        }
    }

    if (first_col_zero) {
        for (int c=0;c<matrix.size();c++) {
            matrix[c][0]=0;
        } 
    }

    if (first_row_zero) {
        vector<int> row = matrix[0];
        for (int c=0; c < row.size();++c) {
            matrix[0][c]=0;
        }
    }
}
