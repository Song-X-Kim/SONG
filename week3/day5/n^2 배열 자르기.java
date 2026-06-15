class Solution {
    public int[] solution(int n, long left, long right) {
        int[] start = {(int) left / n, (int) left % n};
        int[] end = {(int) right / n, (int) right % n};
        int[] answer = new int[(int)(right - left + 1)];
        // 각 행은 n + 1 이 n + 1 번 나온 뒤 n + 1 + 1 식으로 증가함
        // 각 행의 어떤 열인지는 / n = 행 % n = 열로 나타낼 수 있음
        // 각 행은 n 이 (행) 번 나온 뒤 n + 1 된다
        // 각 행렬을 파악한 뒤에 규칙대로 배열에 추가해주면 된다
        // 정답 배열의 크기는 right - left + 1
        for(int i = start[0]; i < end[0]; i++) {
            int idx = 0;
            int s = 0;
            int e = n;
            if (i == start[0]) s = start[1];
            if (i == end[0]) e = end[1]; 
            for(int j = s; j < e; j++) {
                if (j < i) {
                    answer[idx] = i + 1;
                } else answer[idx] = j + 1;
                idx++;
            }
        }
        return answer;
    }
}

---

class Solution {
    public int[] solution(int n, long left, long right) {
        int[] start = {(int)(left / n),  (int)(left % n)};   // 캐스트 우선순위 수정
        int[] end   = {(int)(right / n), (int)(right % n)};
        int[] answer = new int[(int)(right - left + 1)];
        // 각 행은 n + 1 이 n + 1 번 나온 뒤 n + 1 + 1 식으로 증가함
        // 각 행의 어떤 열인지는 / n = 행 % n = 열로 나타낼 수 있음
        // 각 행은 n 이 (행) 번 나온 뒤 n + 1 된다
        // 각 행렬을 파악한 뒤에 규칙대로 배열에 추가해주면 된다
        // 정답 배열의 크기는 right - left + 1
        int idx = 0;
        for(int i = start[0]; i <= end[0]; i++) {
            int s = 0;
            int e = n - 1;
            if (i == start[0]) s = start[1];
            if (i == end[0]) e = end[1]; 
            for(int j = s; j <= e; j++) {
                if (j < i) {
                    answer[idx] = i + 1;
                } else answer[idx] = j + 1;
                idx++;
            }
        }
        return answer;
    }
}

// 범위 설정 버그 수정
