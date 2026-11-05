def setZeroes(matrix: list[list[int]]) -> None:
        """
        Do not return anything, modify matrix in-place instead.
        """
        if not matrix or not matrix[0]:
            return

        m,n=len(matrix),len(matrix[0])
        first_row_zero = False
        for col in range(n):
            if matrix[0][col]==0:
                first_row_zero = True

        first_col_zero=False
        for row in range(m):
            if matrix[row][0]==0:
                first_col_zero=True
        
        for r in range(1,m):
            for c in range(1,n):
                if matrix[r][c]==0:
                    matrix[0][c]=0
                    matrix[r][0]=0
        
        for r in range(1,m):
            for c in range(1,n):
                if matrix[0][c]==0 or matrix[r][0]==0:
                    matrix[r][c]=0
        
        if first_col_zero:
            for r in range(0,m):
                matrix[r][0]=0
        
        if first_row_zero:
            for c in range(0,n):
                matrix[0][c]=0
        
