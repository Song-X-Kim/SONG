import java.util.*;
class Solution {
    public String solution(int n) {
        int[] rule = {1, 2, 4};
        int[] rawA = new int[(int)(Math.log(n) / Math.log(3)) + 1];
        Arrays.fill(rawA, 1);
        int target = n - ((int) Math.pow(3, rawA.length) - 1) / 2;
        if (n < 0) n += Math.pow(3, rawA[rawA.length - 1]);
        for (int i = rawA.length - 1; i >= 0; i--) {
            int cur = (int) Math.pow(3, i);
            rawA[i] += target / cur;
            target = target % cur;
        }
        var sb = new StringBuilder();
        for (int i : rawA) {
            if (i <= 0) continue;
            sb.append(rule[i - 1]);
        }
        return sb.toString();
    }
}

// 수학 너무 어려우
---

import java.util.*;
class Solution {
    public String solution(int n) {
        int[] rule = {1, 2, 4};

      // 로그로 구하는게 아니라 등비수열의 합 이용해서 자릿수 파악
        int len = 1;
        while ((Math.pow(3, len + 1) - 3) / 2 < n) len++;

      // 0 이 없는 나라여서 모든 자리마다 1 배치
        int[] rawA = new int[len];
        Arrays.fill(rawA, 1);

      // n 에서 rawA 의 값 빼주기
        int target = n - (int) ((Math.pow(3, len) - 1) / 2);

      // 나머지 계산
        for (int i = len - 1; i >= 0; i--) {
            int cur = (int) Math.pow(3, i);
            rawA[i] += target / cur;
            target %= cur;
        }

      // 역순 조립
        var sb = new StringBuilder();
        for (int i = len - 1; i >= 0; i--) {
            sb.append(rule[rawA[i] - 1]);
        }

        return sb.toString();
    }
}

// 이게 뭔가 생각한대로 구현을 자꾸 제대로 못하니까 슬프네영

---

class Solution {
    public String solution(int n) {
        String[] rule = {"4", "1", "2"};   // 나머지 0→"4", 1→"1", 2→"2"
        StringBuilder sb = new StringBuilder();

        while (n > 0) {
            int r = n % 3;
            sb.append(rule[r]);
            n = (r == 0) ? n / 3 - 1 : n / 3;   // 나머지 0이면 윗자리에서 1 빌려옴
        }

        return sb.reverse().toString();
    }
}

// 정석
