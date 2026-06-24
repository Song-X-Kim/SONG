import java.util.*;

class Solution {
    public int[] solution(String s) {
        // s.split() 후 정렬 요소별 길이 기준
        // 요소마다 돌면서 idx 위치 확인
        // 중복이 없는 튜플이다(요소의) Set.contains() O(N) 활용해서 중복검사
        // answer 의 길이는 정렬된 배열 마지막 요소의 길이와 동일하다
        s = s.substring(2, s.length() - 2);
        String[] sArr = s.split("\\},\\{");
        Set<String> iSet = new HashSet<>();
        Arrays.sort(sArr, (a, b) -> a.length() - b.length());
        int[] answer = new int[sArr[sArr.length - 1].split(",").length];
        
        for (int i = 0; i < sArr.length; i++) {
            String[] temp = sArr[i].split(",");
            for (int j = 0; j < temp.length; j++) {
                if (iSet.contains(temp[j])) continue;
                iSet.add(temp[j]);
                answer[i] = Integer.parseInt(temp[j]);
            }
        }
        
        return answer;
    }
}
