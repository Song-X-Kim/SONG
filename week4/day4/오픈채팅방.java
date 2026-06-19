import java.util.*;

class Solution {
    public String[] solution(String[] record) {
        // uid 매핑 -> HashMap
        // 요소 하나하나 넣으면서 해야할 일
        // 1. uid 매핑 있으면 갱신 없으면 추가 (put 쓰면 덮어쓰니 put 이면 될듯)
        // 2. split(" "); 으로 해서 1, 2 만 배열에 넣음
        // 3. 이후 하나씩 빼서 0님이 1(나갔습니다 들어왔습니다.) change 는 continue 로
        Map<String, String> map = new HashMap<>();
        String[][] line = new String[record.length][];
        List<String> answer = new ArrayList<>();
        for (int i = 0; i < record.length; i++) {
            line[i] = record[i].split(" ");
            if (line[i].length > 2) {
                map.put(line[i][1], line[i][2]);
            }
        }
        for (int i = 0; i < line.length; i++) {
            String uid = line[i][1];
            switch(line[i][0]) {
                    case "Enter" -> answer.add(map.get(uid) + "님이 들어왔습니다.");
                    case "Leave" -> answer.add(map.get(uid) + "님이 나갔습니다.");
            }
        }
        return answer.toArray(new String[0]);
    }
}
