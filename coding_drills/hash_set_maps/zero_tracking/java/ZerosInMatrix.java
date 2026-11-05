public class ZerosInMatrix {
    public void setZeroes(int[][] matrix) {
        if (matrix == null || matrix[0] == null)
            return;
        int numRows = matrix.length;
        int numCols = matrix[0].length;

        boolean firstRowZero=false;
        for (int c=0;c<numCols;c++) {
            if (matrix[0][c]==0) {
                firstRowZero=true;
                break;
            }
        }

        boolean firstColZero=false;
        for (int r=0;r<numCols;r++) {
            if (matrix[r][0]==0) {
                firstColZero=true;
                break;
            }
        }

        for (int r=1;r<numRows;++r) {
            for (int c=1;c<numCols;++c) {
                if (matrix[r][c]==0) {
                    matrix[r][0]=0;
                    matrix[0][c]=0;
                }
            }
        }

        for (int r=1;r<numRows;++r) {
            for (int c=1;c<numCols;++c) {
                if (matrix[0][c]==0 || matrix[r][0]==0) {
                    matrix[r][c]=0;
                }
            }
        }

        if (firstColZero) {
            for (int r=0;r<numCols;r++) {
                matrix[r][0]=0;
            }
        }

        if (firstRowZero) {
            for (int c=0;c<numRows;c++) {
                matrix[0][c]=0;
            }
        }
    }
}
