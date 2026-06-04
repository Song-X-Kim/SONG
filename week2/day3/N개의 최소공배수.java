import java.util.*;

class Solution {
    public int solution(int[] arr) {
	int[] next = new int[arr.length];
	int answer = 1;
	Arrays.sort(arr);
	int n = arr[arr.length - 1];
	while(n > 1) {
		for(int i = 0; i< arr.length; i++) {
			if (arr[i] % n != 0) break;
			next[i] = arr[i] / n;
			if (i == arr.length - 1) {
				answer *= n;
				arr = next;
			}
		}
		n--;
	}
	for (int i : arr) answer *= i;
        return answer;
    }
}

// 2개 까진 되는데 3개는 안 됨

---

// 걍 정석 풀이입니다..

class Solution {
	public int solution(int[] arr) {
		int answer = 1;
		for (int x : arr) answer = lcm(answer, x);
		return answer;
	}
	// 최대 공약수
	private int gcd(int a, int b) {		// 유클리드 호제법(?)
		while (b != 0) {
			int t = b;
			b = a % b;
			a = t;
		}
		return a;
	}
	// 최소 공배수
	private int lcm(int a, int b) {
		return a / gcd(a, b) * b;
	}
}
