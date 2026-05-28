class Solution {
    public String solution(String s) {
    s = s.replaceAll(" ", "");
	char max = s.charAt(0);
	char min = s.charAt(0);
	StringBuilder sb = new StringBuilder();
	for (char c : s.toCharArray()) {
		if (c - max >= 0) max = c;
		if (c - min <= 0) min = c;
	}
        
        return sb.append(min + " " + max)
			.toString();
    }
}

// -4 << 두 글자로 이루어진 숫자.. 20 도 마찬가지
---
class Solution {
    public String solution(String s) {
	int min = Integer.MAX_VALUE;
	int max = Integer.MIN_VALUE;
	
	for (String token : s.split(" ")) {
		int n = Integer.parseInt(token);
		min = Math.min(min, n);
		max = Math.max(max, n);
	}

        return min + " " + max;
    }
}
