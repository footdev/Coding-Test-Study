package footdev._13주차;

import java.util.*;

class PGS_수식_최대화 {

    private StringTokenizer st;

    private char[] opr = {'+', '-', '*'};

    private Deque<Long> nums1 = new ArrayDeque<>();
    private Deque<Character> oprs1 = new ArrayDeque<>();
    private Deque<Long> nums2 = new ArrayDeque<>();
    private Deque<Character> oprs2 = new ArrayDeque<>();

    private long max = Long.MIN_VALUE;

    public long solution(String exp) {
        //숫자 가공
        st = new StringTokenizer(exp, "+-*");
        while (st.hasMoreTokens()) {
            nums1.add(Long.parseLong(st.nextToken()));
        }

        //연산자 가공
        exp.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> c == '+' || c == '-' || c == '*')
                .forEach(oprs1::add);

        //순열 돌리고 계산
        perm(0);

        return max;
    }

    private void perm(int d) {
        if (d == 3) {
            Deque<Long> tn1 = new ArrayDeque<>(nums1);
            Deque<Character> to1 = new ArrayDeque<>(oprs1);
            Deque<Long> tn2 = new ArrayDeque<>(nums2);
            Deque<Character> to2 = new ArrayDeque<>(oprs2);
            for (int i = 0; i < 3; i++) {
                Deque<Long> n1 = tn1.isEmpty() ? tn2 : tn1;
                Deque<Character> o1 = to1.isEmpty() ? to2 : to1;
                Deque<Long> n2 = tn2.isEmpty() ? tn2 : tn1;
                Deque<Character> o2 = to2.isEmpty() ? to2 : to1;

                char c = opr[i];

                if ((n1.isEmpty() && n2.size() == 1) || (n2.isEmpty() && n1.size() == 1)) break;

                long len = o1.size();
                long l = n1.poll();
                long r = n1.poll();
                char o = o1.poll();

                if (len == 0) len = 1;
                for (int j = 0; j < len; j++) {
                    if (c == o) {
                        l = operate(l, r, o);
                    } else {
                        n2.add(l);
                        l = r;
                        o2.add(o);
                    }

                    if (n1.isEmpty() && o1.isEmpty()) {
                        n2.add(l);
                        break;
                    }
                    r = n1.poll();
                    o = o1.poll();
                }
            }
            //해당 연산이 끝나면 n1 or n2에서 마지막 값이 남아있음.
            max = Math.max(tn1.isEmpty() ? Math.abs(tn2.poll()) : Math.abs(tn1.poll()), max);
            return;
        }

        for (int i = d; i < 3; i++) {
            swap(i, d, opr);
            perm(d + 1);
            swap(i, d, opr);
        }
    }

    private long operate(long a, long b, char opr) {
        if (opr == '+') return a + b;
        else if (opr == '-') return a - b;
        return a * b;
    }

    private void swap(int a, int b, char[] arr) {
        char tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
