class Solution {
    public int[] solution(int n) {
        int[][] tri = new int[n][n];
        int num = 1;
        int top = 0;
        int bot = n - 1;
        int left = 0;
        int size = n;
        
        while (size > 0) {
            for (int r = top; r <= bot; r++) {
                tri[r][left] = num++;
            }
            
            for (int c = left + 1; c <= left + size - 1; c++) {
                tri[bot][c] = num++;
            }
            
            int r = bot - 1, c = left + size - 2;
            for (int k = 0; k < size - 2; k++) {
                tri[r][c] = num++;
                r--; c--;
            }
            
            top += 2; bot -= 1; left += 1; size -= 3;
        }
        
        int[] answer = new int[n * (n + 1) / 2];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                answer[idx++] = tri[i][j];
            }
        }
        return answer;
    }
}
