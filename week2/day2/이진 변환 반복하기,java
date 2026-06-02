class Solution {
    public int[] solution(String s) {
	int[] answer = new int[2];
	while(!s.equals("1")) {
		answer[1] += s.length() - Integer.bitCount(Integer.parseInt(s, 2));
		s = s.replaceAll("0", "");
		s = Integer.toString(s.length(), 2);
		answer[0]++;
	}
	return answer;
    }
}

// 이진수의 길이가 int 로 나타낼 수 있는 범위를 초과하면 초비상
// 그냥 문자열은 반복해서 세기..

---

class Solution {
    public int[] solution(String s) {
	int[] answer = new int[2];
	while(!s.equals("1")) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt[i] == '0') answer[1]++;
		}
		s = s.replaceAll("0", "");
		s = Integer.toString(s.length(), 2);
		answer[0]++;
	}
	return answer;
    }
}

// 걍 이진 관련한건 외우는게 좋을듯.. 딱히 모양이 특수한 것도 아니고
