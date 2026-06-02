class Solution {
    public int solution(int n) {
        int origin = Integer.bitCount(n);
        int answer = n;
        while(true) {
            answer++;
            if (Integer.bitCount(answer) == origin) return answer;
        }
    }
}
