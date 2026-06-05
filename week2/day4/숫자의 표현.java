class Solution {
public int solution(int n) {
int answer = 0;
for(int i = 1; i <= n; i++) {
for(int a = 1; a * (a+1) <= 2 * n; a++) {
int t = a * (2 * i + (a - 1)) / 2;
if (t == n) {
answer++;
break;
}
}
}
return answer;
}
}
// 케이스 1개 시간초과

class Solution {
public int solution(int n) {
int answer = 0;
int s = (int) Math.sqrt(n * 2);
for(int i = 1; i <= n; i++) {
for(int a = s; a >= 1; a--) {
int t = a * (2 * i + (a - 1)) / 2;
if (t == n) {
answer++;
s = a;
break;
}
}
}
return answer;
}
}
// 굿