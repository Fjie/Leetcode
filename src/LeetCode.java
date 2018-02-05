
public class LeetCode {

    public static void main(String[] args) {

        System.out.println("reverse = " + reverse(125484));

    }

    public static int reverse(int src) {
        int result = 0;
        while (Math.abs(src) >= 1) {
            // 结果向后推一位（*10）再加上源的末位
            result = result * 10 + src % 10;
            src /= 10;
            // 源舍去末位
        }
        return result;
    }
}
