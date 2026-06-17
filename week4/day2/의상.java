import java.util.*;

class Solution {
    public int solution(String[][] clothes) {
        int answer = 1;
        Map<String, Integer> map = new HashMap<>();
        
        for (String[] c : clothes) {
            map.merge(c[1], 1, Integer::sum);
        }
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            answer *= e.getValue() + 1;
        }
        
        return answer - 1;
    }
}
