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
