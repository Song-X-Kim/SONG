# JadenCase 풀이 중 막혔던 메서드들 보충 노트
### String 불변성 · 메서드 반환값 · 컴파일 에러 · 상태머신 정석 풀이

> 프로그래머스 [JadenCase 문자열 만들기](https://school.programmers.co.kr/learn/courses/30/lessons/12951)를 풀면서
> 막혔던 모든 지점을 "왜 안 됐는지 → 어떻게 고쳤는지" 순서로 정리한 노트.

---

## 목차

1. [한눈에 보기](#1-한눈에-보기)
2. [논리적으로 실수한 부분](#2-논리적으로-실수한-부분)
3. [메서드를 잘못 사용한 부분](#3-메서드를-잘못-사용한-부분)
4. [컴파일이 안 되게 만든 부분](#4-컴파일이-안-되게-만든-부분)
5. [split(" ", -1) 시각 정리](#5-split--1-시각-정리)
6. [핵심: String 불변과 객체 생성](#6-핵심-string-불변과-객체-생성)
7. [정석 풀이: 상태머신 + StringBuilder](#7-정석-풀이-상태머신--stringbuilder)
8. [❌ 하지 말 것](#8--하지-말-것)
9. [코테에서 바로 쓰는 패턴](#9-코테에서-바로-쓰는-패턴)
10. [참고 문서](#10-참고-문서)

---

## 1. 한눈에 보기

| 막힌 지점 | 종류 | 핵심 원인 | 해결 |
|---|---|---|---|
| `str.charAt(0) = '1';` | 논리/문법 | String 불변 + 메서드 결과는 변수 아님 | 대입 불가, 새 문자열 생성 |
| `first > 'a' && first < 'Z'` | 논리 | `'Z'`(90) < `'a'`(97) → 항상 false | `>= 'a' && <= 'z'` |
| `str.replaceFirst(...)` 반환값 버림 | 논리 | String 불변 → 원본 안 바뀜 | `sArr[i] = sArr[i].replaceFirst(...)` |
| `charAt(0)` 먼저, `isEmpty()` 가드 나중 | 논리 | 가드가 폭탄 뒤에 있음 | 가드를 본문 맨 앞으로 |
| `trim()`으로 끝 공백 제거 | 논리 | 양 끝 공백 전부 삭제 → 끝 공백 보존 실패 | `String.join` 또는 상태머신 |
| `first.toUpperCase()` | 메서드 | `char`는 메서드 없는 기본형 | `Character.toUpperCase(first)` |
| `replaceFirst(char, ...)` | 메서드 | 인자가 `String`인데 `char` 전달 | `String.valueOf(c)` 또는 substring |
| `new String(arr)` (`arr`=`String[]`) | 컴파일 | `String[]` 받는 생성자 없음 | `String.join(" ", arr)` |
| `for (String s : arr)` 변수명 충돌 | 컴파일 | 파라미터 `s`와 동일 이름 | 변수명 변경 |
| `valueOf(...toUpperCase(c)` | 컴파일 | 괄호 짝 안 맞음 | 괄호 1개 추가 |

> **한 줄 요약**: String은 불변이라 "변형 메서드"는 전부 *새 문자열을 반환*한다 —
> 받아서 담지 않으면 아무 일도 안 일어난다. 정석은 split/join 대신 **문자 순차검사 + StringBuilder**.

---

## 2. 논리적으로 실수한 부분

### 2-1. ASCII 범위를 거꾸로 쓴 조건문

```java
if (first > 'a' && first < 'Z') {   // ❌ 항상 false
```

ASCII 값을 보면 이유가 명확하다.

| 문자 | ASCII |
|---|---|
| `'A'` ~ `'Z'` | 65 ~ 90 |
| `'a'` ~ `'z'` | 97 ~ 122 |

`'Z'`(90)가 `'a'`(97)보다 **작다**. 그래서 `first > 'a'`(97 초과)이면서 동시에 `first < 'Z'`(90 미만)인 문자는 존재할 수 없다 → 조건이 영원히 false → `if` 안으로 진입 자체가 안 됨.

```java
if (first >= 'a' && first <= 'z') {  // ✅ 소문자 판별
```

### 2-2. 불변 객체의 반환값을 버린 것

```java
sArr[i].replaceFirst(...);            // ❌ 결과를 버림 → 원본 그대로
sArr[i] = sArr[i].replaceFirst(...);  // ✅ 반환값을 다시 담음
```

`String`의 모든 변형 메서드(`toLowerCase`, `substring`, `replace`, `replaceFirst`...)는 원본을 고치지 않고 **새 문자열을 반환**한다. 받아서 담지 않으면 계산만 하고 버려진다. (바로 위 줄의 `toLowerCase`는 `sArr[i] = sArr[i].toLowerCase()`로 제대로 담았으면서, `replaceFirst`만 안 담은 게 실수 포인트.)

### 2-3. 가드(방어 코드)를 위험한 호출 뒤에 둔 것

```java
if (sArr[i].charAt(0) >= 'a' && ...) {   // ❌ 여기서 먼저 charAt(0) → 빈 문자열이면 폭발
    if (sArr[i].isEmpty()) continue;     //    가드가 너무 늦음
```

`isEmpty()` 가드가 `charAt(0)` **뒤에** 있어서, 빈 문자열일 때 가드에 도달하기도 전에 `StringIndexOutOfBoundsException`이 터진다. 방어 코드는 위험한 호출보다 **먼저** 실행돼야 의미가 있다.

```java
for (int i = 0; i < sArr.length; i++) {
    if (sArr[i].isEmpty()) continue;   // ✅ 본문 맨 앞: 빈 칸이면 즉시 스킵
    char first = sArr[i].charAt(0);     //    여기 도달 = 최소 1글자 보장
    ...
}
```

### 2-4. `trim()`으로 끝 공백을 날린 것

단어마다 `append(" ")`로 공백을 붙이면 끝에 잉여 공백이 하나 생긴다. 이걸 `trim()`으로 떼려 했는데, `trim()`은 "내가 붙인 잉여 공백 하나"가 아니라 **양 끝 공백 전부**를 제거한다.

```
입력 "hello  " (끝 공백 2개)
 → 처리 후 "Hello   "
 → trim() → "Hello"      ❌ 끝 공백 다 사라짐 → 오답
```

해결: 끝에 공백을 안 붙이면(`String.join`이 하는 일) 또는 애초에 split/join을 안 쓰면(상태머신) 이 문제가 사라진다.

---

## 3. 메서드를 잘못 사용한 부분

### 3-1. `char`에 점(`.`)을 찍어 메서드 호출

```java
char first = ...;
first.toUpperCase();   // ❌ char는 기본형(primitive), 메서드 없음
```

`char`는 객체가 아니라 기본형이라 멤버 메서드가 없다. 대문자 변환은 `Character` 클래스의 **정적 메서드**를 쓴다.

```java
Character.toUpperCase(first);   // ✅ char → char 반환
Character.toLowerCase(first);   // ✅
```

> `Character.toUpperCase(char)`는 대문자 매핑이 없는 문자(숫자, 기호 등)는 **그대로 반환**한다.
> 그래서 첫 글자가 숫자여도 안전하다 (`'3'` → `'3'`).

### 3-2. `replaceFirst`에 `char`를 넘긴 것

```java
str.replaceFirst(first, ...);   // ❌ replaceFirst(String, String)인데 char 전달
```

`replaceFirst(String regex, String replacement)` — 두 인자 모두 `String`이다. `char`를 넘기면 타입 불일치. 굳이 쓰려면 `String.valueOf(char)`로 감싼다.

```java
str.replaceFirst(String.valueOf(first), String.valueOf(Character.toUpperCase(first)));
```

> ⚠️ 그런데 `replaceFirst`의 첫 인자는 **정규식(regex)** 이다. 첫 글자가 `.`, `*`, `(` 같은
> 정규식 특수문자면 의도와 다르게 동작한다. "첫 글자만 대문자"라는 의도엔
> `Character.toUpperCase(c) + 나머지` 조합이 더 직접적이고 안전하다.

### 3-3. `String.join` 인자 순서를 뒤집은 것

```java
String.join(sArr, " ");   // ❌ 순서 반대
String.join(" ", sArr);   // ✅ join(구분자, 원소들)
```

시그니처: `String.join(CharSequence delimiter, CharSequence... elements)` — **구분자가 먼저**.
그리고 구분자는 원소 **사이에만** 들어가고 끝에는 안 붙으므로, 끝 공백 잉여 문제가 없다.

| 메서드 | 소속 | 반환 | 핵심 |
|---|---|---|---|
| `Character.toUpperCase(char)` | `Character` (static) | `char` | 변환 대상 아니면 원본 반환 |
| `Character.toLowerCase(char)` | `Character` (static) | `char` | 〃 |
| `String.valueOf(char)` | `String` (static) | `String` | char 1개 → 길이 1 String |
| `String.join(delim, elems)` | `String` (static) | `String` | 구분자 먼저, 사이에만 삽입 |
| `String.substring(int)` | `String` | `String` | 새 문자열 반환 (불변) |
| `replaceFirst(regex, repl)` | `String` | `String` | 첫 인자 정규식 주의 |

---

## 4. 컴파일이 안 되게 만든 부분

실제로 받았던 에러 3종 + 추가 1종.

### 4-1. `no suitable constructor found for String(String[])`

```java
return new String(arr);   // ❌ arr는 String[]
```

`String` 생성자에는 `String[]`을 받는 게 없다 (`char[]`, `byte[]`, `StringBuilder` 등만 존재). 배열을 문자열로 합치는 건 생성자가 아니라 `String.join`.

```java
return String.join(" ", arr);   // ✅
```

### 4-2. `variable s is already defined in method solution(String)`

```java
public String solution(String s) {     // 파라미터 s
    for (String s : arr) {              // ❌ 같은 이름 s 재선언 → 충돌
```

향상된 for의 변수명을 파라미터와 다르게.

```java
for (String word : arr) { ... }   // ✅
```

### 4-3. `char cannot be dereferenced`

```java
first.toUpperCase()   // ❌ char에 . 찍음 (3-1 참고)
```

### 4-4. 괄호 짝 안 맞음

```java
str.replaceFirst(String.valueOf(first), String.valueOf(Character.toUpperCase(first));
//               (1)                    (2)              (3)              닫기: (3)(1) 만 있고 (2) 없음 ❌
```

`String.valueOf(` 를 여는 괄호에 대응하는 닫는 `)`가 하나 부족. 여는 괄호와 닫는 괄호 개수를 세는 습관.

```java
str.replaceFirst(String.valueOf(first), String.valueOf(Character.toUpperCase(first)));  // ✅ )) 두 개
```

---

## 5. split(" ", -1) 시각 정리

`split`은 `limit` 인자에 따라 **뒤쪽 빈 토큰**의 운명이 갈린다.

```
입력: "Hi bye "   (끝에 공백 1개)

split(" ")        →  ["Hi", "bye"]          길이 2   ← 끝 "" 버려짐
split(" ", -1)    →  ["Hi", "bye", ""]      길이 3   ← 끝 "" 보존됨
```

### 왜 둘이 세트로 움직이나

| 코드 | 역할 |
|---|---|
| `split(" ", -1)` | 빈 칸("")을 **만들어낸다** (공백 배치 보존) |
| `if (sArr[i].isEmpty()) continue;` | 그 빈 칸 때문에 `charAt(0)`이 **안 터지게 막는다** |

- `-1`만 넣고 가드를 안 하면 → 빈 칸이 더 생겨서 `charAt(0)` 예외 위험 ↑
- 가드만 하고 `-1`을 안 하면 → 끝 공백이 사라져서 결과 오답

> `limit < 0` 이면 trailing empty string을 버리지 않는다 (Oracle 공식 문서 명시).

### 더 깔끔한 길

split/join 방식은 이 "빈 토큰 + 가드" 조합을 늘 신경 써야 한다.
**문자 순차검사(상태머신)** 로 가면 split 자체를 안 쓰니 이 문제가 원천적으로 사라진다 → 7번.

---

## 6. 핵심: String 불변과 객체 생성

> 네 직관이 정확했다 — "String은 불변 객체라 새로 만들면 객체가 계속 생성된다."

`String`은 **불변(immutable)**: 한 번 만들어지면 내부 문자를 바꿀 수 없다.
그래서 "변형"처럼 보이는 모든 연산은 사실 **새 String 객체를 생성**해서 반환한다.

```java
String s = "abc";
s.toUpperCase();      // "ABC"라는 새 객체를 만들어 반환 (s 자체는 "abc" 그대로)
s = s.toUpperCase();  // 받아서 담아야 비로소 s가 "ABC"를 가리킴
```

### 반복문 안에서 += 로 문자열을 이어붙이면?

```java
String result = "";
for (char c : arr) {
    result += c;      // ❌ 매 반복마다 새 String 객체 생성 → O(n²)
}
```

`result += c`는 내부적으로 매번 새 String을 만든다. 길이 n이면 1+2+...+n ≈ **O(n²)** 시간 + 버려지는 중간 객체 n개.

### 그래서 StringBuilder

```java
StringBuilder sb = new StringBuilder();
for (char c : arr) {
    sb.append(c);     // ✅ 내부 가변 버퍼에 추가 → 분할상환 O(1)
}
String result = sb.toString();   // 마지막에 한 번만 String 생성
```

`StringBuilder`는 **가변(mutable)** 버퍼라 제자리에서 자란다. 전체 O(n), 객체 생성도 마지막 `toString()` 한 번뿐.

| | String += | StringBuilder |
|---|---|---|
| 가변성 | 불변 (매번 새 객체) | 가변 (버퍼 재사용) |
| 시간복잡도 | O(n²) | O(n) |
| 중간 객체 | n개 생성/폐기 | 없음 |
| 용도 | 1~2번 결합 | 반복 누적 |

> 결론: **반복으로 문자열을 쌓을 땐 무조건 StringBuilder.** 이게 정석 풀이가 sb를 쓰는 이유.

---

## 7. 정석 풀이: 상태머신 + StringBuilder

split도 join도 안 쓰고, 문자를 하나씩 보며 **"지금이 단어의 첫 글자인가?"** 상태만 추적한다.

```java
class Solution {
    public String solution(String s) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;              // 단어의 첫 글자인가?
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                sb.append(c);
                isFirst = true;              // 공백 다음은 새 단어 시작
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
```

### 왜 이게 정석인가

| 장점 | 이유 |
|---|---|
| 공백 100% 보존 | 원본 char를 그대로 따라가므로 연속 공백·끝 공백 자동 유지 |
| 예외 불가능 | `charAt(0)` 같은 인덱스 접근 없음 → 빈 토큰 예외 원천 차단 |
| split/join 불필요 | 중간 배열 생성 없음 |
| 단일 패스 | 문자열을 한 번만 훑음 |

### 상태 전이 (한눈에)

```
                  공백 만남
        ┌───────────────────────┐
        ▼                       │
 ┌─────────────┐  글자(첫)  ┌──────────────┐
 │ isFirst=true │──────────▶│ isFirst=false │
 │ (새 단어 대기)│  대문자    │ (단어 진행 중) │
 └─────────────┘           └──────────────┘
        ▲                       │
        │      글자(첫 아님)→소문자 │
        └───────────(루프)───────┘
```

- `' '` 만나면 → 공백 그대로 append + `isFirst = true` (다음은 새 단어)
- `isFirst == true` 인 글자 → 대문자로 append + `isFirst = false`
- `isFirst == false` 인 글자 → 소문자로 append

### 복잡도

- **시간** O(n): 문자열 길이 n을 한 번 순회.
- **공간** O(n): 결과를 담는 StringBuilder 버퍼.

---

## 8. ❌ 하지 말 것 (오늘 헤맨 실수 모음)

```java
// ❌ 1. 메서드 호출 결과에 대입 (불변 + 좌변이 변수 아님)
str.charAt(0) = '1';

// ❌ 2. char에 점 찍어 메서드 호출
first.toUpperCase();                       // → Character.toUpperCase(first)

// ❌ 3. 변형 메서드 반환값 버리기
sArr[i].replaceFirst(a, b);                // → sArr[i] = sArr[i].replaceFirst(a, b)

// ❌ 4. ASCII 범위 거꾸로
if (c > 'a' && c < 'Z')                     // 항상 false → if (c >= 'a' && c <= 'z')

// ❌ 5. 가드를 위험한 호출 뒤에 배치
if (s.charAt(0) ...) { if (s.isEmpty()) continue; }   // 가드를 맨 앞으로

// ❌ 6. String[] 을 String 생성자에
new String(arr);                            // → String.join(" ", arr)

// ❌ 7. join 인자 순서 반대
String.join(arr, " ");                      // → String.join(" ", arr)

// ❌ 8. 끝 공백을 trim()으로 제거
sb.toString().trim();                       // 양 끝 공백 다 날림 → join/상태머신

// ❌ 9. 반복문에서 String += 누적
result += c;                                // O(n²) → StringBuilder.append
```

---

## 9. 코테에서 바로 쓰는 패턴

```java
// 단어별 첫 글자 대문자 (split 방식, join으로 끝 공백 안전)
String[] w = s.toLowerCase().split(" ", -1);
for (int i = 0; i < w.length; i++)
    if (!w[i].isEmpty())
        w[i] = Character.toUpperCase(w[i].charAt(0)) + w[i].substring(1);
return String.join(" ", w);

// 문자 순차검사 (상태머신, 가장 견고 — 권장)
StringBuilder sb = new StringBuilder();
boolean isFirst = true;
for (char c : s.toCharArray()) {
    if (c == ' ')          { sb.append(c); isFirst = true; }
    else if (isFirst)      { sb.append(Character.toUpperCase(c)); isFirst = false; }
    else                   { sb.append(Character.toLowerCase(c)); }
}
return sb.toString();

// 반복 문자열 누적은 항상 StringBuilder
StringBuilder sb = new StringBuilder();
for (...) sb.append(x);
String out = sb.toString();
```

> **선택 기준**: 공백 보존·엣지 케이스가 까다로운 문제 → 상태머신.
> 단어 단위 가공이 명확하고 공백이 단순 → split + join.

---

## 10. 참고 문서

- [String (불변성) — Java SE 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html) — "Strings are constant; their values cannot be changed after they are created"
- [String.split(String, int) — Java SE 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#split(java.lang.String,int)) — limit 음수 시 trailing empty string 보존
- [String.join(CharSequence, CharSequence...) — Java SE 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#join(java.lang.CharSequence,java.lang.CharSequence...))
- [String.substring(int) — Java SE 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#substring(int))
- [String.replaceFirst(String, String) — Java SE 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#replaceFirst(java.lang.String,java.lang.String))
- [String.isEmpty() — Java SE 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#isEmpty())
- [String.charAt(int) — Java SE 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#charAt(int))
- [String.toCharArray() — Java SE 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#toCharArray())
- [Character.toUpperCase(char) — Java SE 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Character.html#toUpperCase(char))
- [StringBuilder — Java SE 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/StringBuilder.html)
- [JadenCase 문자열 만들기 — 프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/12951)

---

*Programmers Java 100일 · 보충 노트*
