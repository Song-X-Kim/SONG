class Solution {
    private static final int[][] DIR = {
        {0, 1},
        {1, 0},
        {0, -1},
        {-1, 0}
    };
    
    public int[] solution(int rows, int columns, int[][] queries) {
        int[] answer = new int[queries.length];
        int[][] origin = new int[rows][columns];
        int start = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                origin[i][j] = start;
                start++;
            }
        }
        
        for (int i = 0; i < queries.length; i++) {
            answer[i] = rotate(origin, queries[i]);
        }

        return answer;
    }
    
    private int rotate(int[][] origin, int[] query) {
        int min = Integer.MAX_VALUE;
        int garo = query[3] - query[1];
        int sero = query[2] - query[0];
        int[] command = {garo, sero, garo, sero};
        int[] start = {query[0] - 1, query[1] - 1};
        int t = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < command[i]; j++) {
                int c = origin[start[0]][start[1]];
                min = Math.min(min, c);
                start[0] += DIR[i][0];
                start[1] += DIR[i][1];
                t = origin[start[0]][start[1]];
                origin[start[0]][start[1]] = c;
            }
        }
        return min;
    }
} 
// rotate 뭔가 잘못되서 고장남
---
class Solution {
    private static final int[][] DIR = {
        {0, 1},
        {1, 0},
        {0, -1},
        {-1, 0}
    };
    
    public int[] solution(int rows, int columns, int[][] queries) {
        int[] answer = new int[queries.length];
        int[][] origin = new int[rows][columns];
        int start = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                origin[i][j] = start;
                start++;
            }
        }
        
        for (int i = 0; i < queries.length; i++) {
            answer[i] = rotate(origin, queries[i]);
        }

        return answer;
    }
    
    private int rotate(int[][] origin, int[] query) {
        int garo = query[3] - query[1];
        int sero = query[2] - query[0];
        int[] command = {garo, sero, garo, sero};
        int[] start = {query[0] - 1, query[1] - 1};
	int c = origin[start[0]][start[1]];
	int min = c;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < command[i]; j++) {
                start[0] += DIR[i][0];
                start[1] += DIR[i][1];
                int t = origin[start[0]][start[1]];
                origin[start[0]][start[1]] = c;
		c = t;
		min = Math.min(min, c);
            }
        }
        return min;
    }
} 
// 현재값과 임시저장값 패턴 확실히 알아놓고 적용하기
// c 와 t 값 갱신과 적용공부 및 원본 배열참조 관련 개념잡기
