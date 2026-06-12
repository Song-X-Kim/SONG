import java.util.*;

class Solution {
    public String[] solution(int[][] line) {
        List<long[]> dots = new ArrayList<>();
        long minX = Long.MAX_VALUE, maxX = Long.MIN_VALUE;
        long minY = Long.MAX_VALUE, maxY = Long.MIN_VALUE;
        
        for (int i = 0; i < line.length; i++) {
            for (int j = i + 1; j < line.length; j++) {
                long[] p = cross(line[i], line[j]);
                if (p == null) continue;
                dots.add(p);
                minX = Math.min(minX, p[0]);
                maxX = Math.max(maxX, p[0]);
                minY = Math.min(minY, p[1]);
                maxY = Math.max(maxY, p[1]);
            }
        }
        
        int W = (int)(maxX - minX + 1);
        int H = (int)(maxY - minY + 1);

        char[][] grid = new char[H][W];
        for (char[] row : grid) Arrays.fill(row, '.');
        
        for (long[] p : dots) {
            int col = (int)(p[0] - minX);
            int row = (int)(maxY - p[1]);
            grid[row][col] = '*';
        }
               
        String[] answer = new String[H];
        for (int i = 0; i < H; i++) answer[i] = new String(grid[i]);
        return answer;
    }
    
    private long[] cross(int[] A, int[] B) {
        long a1 = A[0], b1 = A[1], c1 = A[2];
        long a2 = B[0], b2 = B[1], c2 = B[2];
        
        long d = a1 * b2 - a2 * b1;
        if (d == 0) return null;
        
        long x = b1 * c2 - b2 * c1;
        long y = a2 * c1 - a1 * c2;
        
        if (x % d != 0 || y % d != 0) return null;
        return new long[]{ x / d, y / d};
    }
}

// 구현이 진짜 너무 어려워요 AI 도움 받아서 구현했습니다
