import java.util.*;

class Solution {
    public String solution(String s) {
	String[] arr = s.split(" ");
	for (String str : arr) {
		char first = str.charAt(0);
		if (first > 'a' && first < 'z') {
			str.replaceFirst(String.valueOf(first), String.valueOf(Character.toUpperCase(first)));
		}
	}
        return String.join(" ", arr);
    }
}

// 똥

---
import java.util.*;

class Solution {
    public String solution(String s) {
	// 뒷 공백 사라져서 -1 로 살려주는 옵션
	String[] sArr = s.split(" ", -1);
	for (int i = 0; i < sArr.length; i++) sArr[i] = sArr[i].toLowerCase();
	for (int i = 0; i < sArr.length; i++) {
	// 그냥 공백이 포함된 경우 방어해주는 코드
        if (sArr[i].isEmpty()) continue;
		if (sArr[i].charAt(0) >= 'a' && sArr[i].charAt(0) <= 'z') {
			char first = sArr[i].charAt(0);
			// char 로 하려니까 타입 변경이 필요함..
			sArr[i] = sArr[i].replaceFirst(String.valueOf(first), String.valueOf(Character.toUpperCase(first)));
		}
	}
        return String.join(" ", sArr);
    }
}

// 문자열 너무 힘들우 코드가 너무 쓰레기 같음..

---
// by claude
  // 그냥 앞에서 부터 체크해서 sb 를 통해서 새로운 String 을 하나 만들어 버리기
  // String 은 불변객체여서 앞으로는 이런 형식으로 풀수잇도록 생각할듯..
  
class Solution {
	public String solution(String s) {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true; // 단어의 첫 글자인지 체크하는 상태변수
		for (char c : s.toCharArray()) {
			if (c == ' ') {
				sb.append(c);
				isFirst = true; // 공백 다음은 새 단어 시작
			} else if (isFirst) {
				sb.append(Character.toUpperCase(c));
				isFirst = false;
			} else {
				sb.append(Character.toLowerCase(c));
			}
		}
		return sb.toString();
	}
}

// 굿
