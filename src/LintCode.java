import java.util.*;

/**
 * LintCode，大体按简单到困难 + 出现频率排序
 * FIXME 标记思路、总结，高亮，好看，美！
 *
 * TODO　两个数组、链表一起遍历的问题
 * TODO　链表的遍历问题
 * TODO　合并排序问题
 * TODO　顺序、对称判定问题（回文数、有效括号组）
 */

/**
 * 数组相关
 */
class LintCodeArray {

    /* 56. 两数之和
     * @param numbers: An array of Integer
     * @param target: target = numbers[index1] + numbers[index2]
     * @return: [index1 + 1, index2 + 1] (index1 < index2)
     */
//    FIXME 56 数组问题，高效取出满足条件的两个数
    public int[] twoSum(int[] numbers, int target) {
        // 2025ms，用一个容器将寻找到的信息存储下来
        Map<Integer, Integer> disc = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            Integer n = disc.get(numbers[i]);//从容器直接取满足条件的数，相当与第二次遍历干的活
            if (n != null) {
                return new int[]{n, i};
            } else {
                disc.put(target - numbers[i], i);
            }
        }
        // 2600ms
        // for(int i = 0; i < numbers.length -1; i++){
        //     for(int j = i+1; j < numbers.length; j++){
        //         if(numbers[i] + numbers[j] == target){
        //             return new int[]{i,j};
        //         }
        //     }
        // }
        return null;
    }


    /*
     * @param s: A string
     * @return: A string
     */
    // FIXME 53 其实只是个数组翻转问题
    // 倒序遍历添加到第二个容器
    public String reverseWords(String s) {
        String[] words = s.split(" ");
        StringBuilder sbResult = new StringBuilder();
        if (words.length == 0) {
            return s;
        }
        for (int i = words.length - 1; i >= 0; i--) {
            sbResult.append(words[i]);
            sbResult.append(' ');
        }
        return sbResult.toString();
    }

    /*
     * @param A: An integer array
     * @return: An integer
     */
    // FIXME 82 数组中找数字问题，其实是找两个符合条件的数的变种
    // 直接思路是两次遍历，第一次找第一个数，第二次找匹配的数
    // 用容器记录遍历信息，省去第二次遍历
    public int singleNumber(int[] A) {

        List<Integer> list = new ArrayList<>();

        for (int i : A) {
            int index = list.indexOf(i);
            if (index < 0) {
                list.add(i);
            } else {
                list.remove(index);
            }
        }

        return list.get(0);

    }


    /*
     * @param n: the integer to be reversed
     * @return: the reversed integer
     */
    // FIXME 413 看作数组倒置问题，数组的元素靠现取（取余），递增靠整除
    // 暴力解法有一个溢出问题，我这里直接用long了
    public int reverseInteger(int n) {

        long result = 0;
        while (Math.abs(n) >= 1) {
            // 结果向后推一位（*10）再加上源的末位
            result = result * 10 + n % 10;
            n /= 10;
            // 源舍去末位
        }
        if (result > Integer.MAX_VALUE) {
            return 0;
        }
        return (int) result;
    }

    /*
     * @param nums: a list of integers
     * @return: find a  majority number
     */
    // FIXME 46 数组找数问题
    // 时间O(n)可以，空间O(1)看怎么用一个变量代替Map
    public int majorityNumber(List<Integer> nums) {
        int result = nums.size()/2;
        Map<Integer,Integer> map = new HashMap<>();
        Integer temp;
        for(int n:nums){
            if((temp = map.get(n)) != null){
                map.put(n,++temp);
                if(temp>result){
                    return n;
                }
            }else {
                map.put(n,1);
            }
        }

        return 0;
    }

    /*
     * @param s: A string
     * @return: whether the string is a valid parentheses
     */
    // FIXME 423 还是一个数组找数问题，结合了顺序判定
    // 需要两个额外空间，一个字典，记录不同字符可供计算的权值
    // 另外一个栈，用来表示后进先出的匹配规则，并存放权值
    public boolean isValidParentheses(String s) {

        if(s == null || s.isEmpty()){
            return false;
        }

        Map<Character,Integer> disc = new HashMap<>();
        disc.put('(',1);
        disc.put(')',-1);
        disc.put('{',2);
        disc.put('}',-2);
        disc.put('[',3);
        disc.put(']',-3);

        char[] words = s.toCharArray();
        if(words.length %2 != 0 || disc.get(words[0])<0){
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        char word;
        int value;
        for (int i = 0; i < words.length; i++) {
            word = words[i];
            value = disc.get(word);
            if(value > 0){
                stack.push(value);
            }else {
                if(stack.isEmpty() || stack.pop() + value != 0){
                    return false;
                }
            }

        }
        return stack.isEmpty();
    }

    /*
     * @param digits: a number represented as an array of digits
     * @return: the result
     */
    // FIXME 407 数组表示整数的加法问题
    // 一次遍历，外部一个变量存进位数，超过位数了就拓展、复制下数组
    public int[] plusOne(int[] digits) {
        int carry = 1;
        int num;
        for(int i = digits.length-1; i >= 0; i--){
            num = digits[i]+ carry;
            carry = num/10;
            if(carry == 0){
                break;
            }
        }
        if(carry != 0){
            int[] result = new int[digits.length+1];
            result[0] = carry;
            System.arraycopy(digits,0,result,1,digits.length);
            return result;
        }

        return digits;
    }

    /*
     * @param num: a positive number
     * @return: true if it's a palindrome or false
     */
    // FIXME 491 对称问题，数组双向遍历？字符串？
    // 直接解析成char数组，双向遍历判断对称
    // TODO 双向遍历有个奇偶问题
    public boolean isPalindrome(int num) {
        char[] values = String.valueOf(num).toCharArray();
        int length = values.length;

        // 单数搞点骚操作
        if(length%2 == 1){
            int harf = Math.round(length/2.0f);

            int temp = (int)Math.pow(10.0,harf);
            int head = num / temp;
            temp/=10;
            int footer = num % temp;

            num = (head * temp) + footer;
            length --;
            values = String.valueOf(num).toCharArray();
        }
        for(int i = 1; i <= length/2;i++){
            if(values[i-1] != values[length - i]){
                return false;
            }
        }
        return true;
    }

    /*
     * @param nums: A list of integers
     * @return: A integer indicate the sum of max subarray
     */
    // FIXME 41 高级点的数组找数问题、数组求和问题
    // 需要第二次遍历求子数组的和，怎么做到O(n)？
    public int maxSubArray(int[] nums) {
        int sum = Integer.MIN_VALUE;
        int tempSum = 0;
        for(int i = 0; i < nums.length; i++){
            for(int j = i; j < nums.length; j++){

                tempSum += nums[j];
                if(tempSum > sum){
                    sum = tempSum;
                }

            }
            tempSum = 0;
        }

        return sum;
    }

    /*
     * @param n: the nth
     * @return: the nth sequence
     */
    // FIXME 420 数组找数问题？字符串拆分 + 翻译问题（字符串转换）
    // 分解成拆分和翻译两步，按给定次数执行
    public String countAndSay(int n) {
        String str = "1";
        for (int i = 1; i < n; i++) {
            str = say(count(str));
        }
        return str;
    }
    // 相同的一组字符拆分成一个词儿
    private List<String> count(String src) {
        StringBuilder sb = null;
        List<String> numList = new ArrayList<>();
        char[] chars = src.toCharArray();
        char prev = '+';//加减号标记开头结尾
        char c;
        for (int i = 0; i <= chars.length; i++) {
            c = i == chars.length ? '-' : chars[i];
            if (c != prev) {// 切割
                if (sb != null) numList.add(sb.toString());// 这里用null来标记第一次，第一次不添加
                sb = new StringBuilder();
            }
            sb.append(c);
            prev = c;
        }

        return numList;
    }
    // 遍历翻译
    private String say(List<String> list){
        StringBuilder sb = new StringBuilder();
        for (String s:list){
            char[] chars = s.toCharArray();
            if(chars.length > 0){
                sb.append(chars.length);
                sb.append(chars[0]);
            }
        }
        return sb.toString();
    }

    /*
     * @param A: A list of integers
     * @param elem: An integer
     * @return: The new length after remove
     */
    // FIXME 172 数组遍历、删除
    public int removeElement(int[] A, int elem) {
        int size = A.length;
        for(int i = 0; i < size; i++){
            int numMoved = A.length - i - 1;
            if(A[i] == elem){
                System.arraycopy(A,i+1,A,i,numMoved);
                i--;
                A[--size] = 0;
            }
        }
        return size;
    }

    /*
     * @param nums: An ineger array
     * @return: An integer
     */
    // FIXME 100 数组的遍历、删除重复数
    int size;
    public int removeDuplicates(int[] nums) {
        size = nums.length;
        if(size < 2){
            return size;
        }
        int prev = nums[0];
        for(int i = 1; i < size; i++){
            if(nums[i] == prev){
                removeElement(i,nums);
                i--;
            }else{
                prev = nums[i];
            }
        }
        return size;
    }

    private void removeElement(int i,int[] nums) {
        int numMoved = size - i - 1;
        System.arraycopy(nums,i+1,nums,i,numMoved);
        nums[--size] = 0;

    }

    /*
     * @param A: sorted integer array A which has m elements, but size of A is m+n
     * @param m: An integer
     * @param B: sorted integer array B which has n elements
     * @param n: An integer
     * @return: nothing
     */
    // FIXME 64 合并排序数组问题、双向遍历问题
    // 开新空间存排序后的数组，再拷贝过去
    // 能不能直接排？
    public void mergeSortedArray(int[] A, int m, int[] B, int n) {
        int[] result = new int[m+n];
        int i=0,j=0;
        int small;
        while(i < m || j < n){
            if(i >= m){
                small = B[j++];
            }else if(j >= n){
                small = A[i++];
            }else{
                if(A[i] < B[j]){
                    small = A[i++];
                }else{
                    small = B[j++];
                }
            }
            // 这里的i、j必有一个已经递增了，所以减1解决
            result[i+j-1] = small;
        }
        System.arraycopy(result,0,A,0,m+n);
    }

}

/**
 * 各种树，基本靠递归 ( ﹁ ﹁ ) ~→
 */
class LintCodeTree {

    // 树神镇楼！
    public static class TreeNode {
        public int val;
        public TreeNode left, right;

        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }

    /**
     * @param root: The root of binary tree.
     * @return: An integer.
     */
    // FIXME 97 数问题基本靠遍历
    // 根叶之间有数据依赖，所以另外封装方法，传个值去计数
    // 循环不太好做，毕竟多向的不是单向→↗↘
    public int maxDepth(TreeNode root) {
        return getMaxDepth(root, 0);
    }

    private int getMaxDepth(TreeNode node, int max) {
        if (node == null) {
            return max;
        }
        return Math.max(getMaxDepth(node.left, max + 1),
                getMaxDepth(node.right, max + 1));
    }

    /*
     * @param root: a TreeNode, the root of the binary tree
     * @return: nothing
     */
    // FIXME 175 简单的数递归
    // TODO 不用递归怎么弄？
    public void invertBinaryTree(TreeNode root) {
        if(root != null){
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
            invertBinaryTree(root.left);
            invertBinaryTree(root.right);
        }
    }


}

/**
 * 链表
 */
class LintCodeList {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    /*
     * @param l1: the first list
     * @param l2: the second list
     * @return: the sum list of l1 and l2
     */
    // FIXME 167 数组链表的加法问题，主要解决逢十进一的问题，外面拿个变量标记进位数就好了
    // 二就是链表的遍历问题，有点小烦
    // 联想下，如果题目给的参数是顺着的（3->1->5 = 315），那样一个遍历转换成数字，相加再转回去会不会快？不会好像
    //1、遍历，单个计算、转换，2、转换好后相加再转换
    public ListNode addLists(ListNode l1, ListNode l2) {
        ListNode next1 = new ListNode(-99);
        next1.next = l1;
        ListNode next2 = new ListNode(-99);
        next2.next = l2;
        ListNode result = null;
        ListNode nextR = null;
        int carry = 0;
        while (true) {

            if (next1 != null) {
                next1 = next1.next;
            }

            if (next2 != null) {
                next2 = next2.next;
            }

            if (next1 == null && next2 == null && carry == 0) {
                return result;
            }

            int sum = carry
                    + (next1 != null ? next1.val : 0)
                    + (next2 != null ? next2.val : 0);
            int current = sum % 10;
            carry = sum / 10;

            if (nextR == null) {
                nextR = new ListNode(current);
                result = nextR;
            } else {
                nextR.next = new ListNode(current);
                nextR = nextR.next;
            }

        }

    }

    /*
     * @param head: n
     * @return: The new head of reversed linked list.
     */
    // FIXME 35 链表倒置问题
    // TODO　整理下链表遍历的思路
    // 原地翻转：链表的倒序可以区别数组，一次遍历，将next指针颠倒过来，以尾为头就好了
    public ListNode reverse(ListNode head) {
        if(head == null){
            return null;
        }
        ListNode next = head;
        ListNode before = null;
        ListNode temp = null;
        while(true){
            temp = next.next;
            next.next = before;
            before = next;
            if(temp == null){
                return next;
            }else{
                next = temp;
            }
        }
    }


    /*
     * @param l1: ListNode l1 is the head of the linked list
     * @param l2: ListNode l2 is the head of the linked list
     * @return: ListNode head of linked list
     */
    // FIXME 165 两个链表同时遍历问题，合并排序问题
    // 其实可以用一个新链表来存储排序，不会这么复杂
    private ListNode result = null;
    private ListNode resultTemp = null;

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode A = l1, B = l2;

        while(A!=null && B!= null){
            if(A.val < B.val){
                setListNode(A);
                A = A.next;
            }else{
                setListNode(B);
                B = B.next;
            }
            if(result == null){
                result = resultTemp;
            }
        }

        if(A != null){
            if(result == null){
                return A;
            }
            setListNode(A);
        }
        if(B != null){
            if(result == null){
                return B;
            }
            setListNode(B);
        }

        return result;
    }

    private void setListNode(ListNode node){
        if(resultTemp == null){
            resultTemp = node;
        }else{
            resultTemp.next = node;
            resultTemp = node;
        }

    }

}

public class LintCode {


    /**
     * @param n: An integer
     * @return: An integer
     */
    // FIXME 111 算什么问题？
    //用递归的思路，任意的一次踏步，都有本次踏步的两种（给定的）选择 * 剩余的阶梯总选择数量，递归下去就能求出
    // 用一个map存下已知的步数，节省递归层级，算作弊吗？
    private static Map<Integer, Integer> disc = new HashMap<>();
    static {
        disc.put(0, 0);
        disc.put(1, 1);
        disc.put(2, 2);
    }
    public int climbStairs(int n) {
        Integer count = disc.get(n);
        if (count == null) {
            count = climbStairs(n - 1) + climbStairs(n - 2);
            disc.put(n, count);
        }
        return count;
    }

    /**
     * 检查是否匹配规则
     */
    public boolean anagram(String s, String t) {
        if (s.length() != t.length()) return false;
        StringBuilder sbT = new StringBuilder(t);
        int index = -1;
        for (char c : s.toCharArray()) {
            index = sbT.indexOf(c + "");
            if (index < 0) {
                return false;
            } else {
                sbT.deleteCharAt(index);
            }

        }
        return true;
    }

    public boolean compareStrings(String A, String B) {
        StringBuilder sbA = new StringBuilder(A);
        int index = -1;
        for (char c : B.toCharArray()) {
            index = sbA.indexOf(c + "");
            if (index >= 0) {
                sbA.deleteCharAt(index);
            } else {
                return false;
            }
        }
        return true;
    }

    /*
     * @param dictionary: an array of strings
     * @return: an arraylist of strings
     */
    public List<String> longestWords(String[] dictionary) {
        List<String> result = new ArrayList<>();
        int max = 0;
        int size = 0;
        for (String s : dictionary) {
            size = s.length();
            // 找到更大尺寸就更新max
            if (size > max) {
                max = size;
                result.clear();
            }
            // 每次判断是否是max
            if (size == max) {
                result.add(s);
            }
        }
        return result;
    }

    public int strStr(String source, String target) {
        return source.indexOf(target);
    }


    /**
     * 下面会超时来个新的
     */
    public List<String> anagrams(String[] strs) {
        // 存放结果
        List<String> result = new ArrayList<>();
        // 存放单身狗
        HashSet<String> singles = new HashSet<>();

        /* 一个容器放结果，放的同时匹配下，记录单身狗的下标，遍历完后从后面开始移除掉单身狗 */
        for (String src : strs) {
            // 如果没找到伴儿
            if (!compareSingle(src, result, singles)) {
                singles.add(src);
            }
            result.add(src);
        }

        // 移除掉所有记录在案的单身狗
        result.removeAll(singles);

        return result;
    }

    private boolean compareSingle(String src, List<String> result, HashSet<String> singles) {
        // 遍历结果中的字符串
        for (String resultStr : result) {
            // 检查 resultStr 是否与 src 匹配
            if (anagram(resultStr, src)) {
                // 有匹配则脱单
                singles.remove(resultStr);
                return true;
            }
        }
        // 没能找到伴侣
        return false;
    }


    /**
     * @param strs: A list of strings
     * @return: A list of strings
     * TODO 会超时我日
     */
    public List<String> anagramsOld(String[] strs) {
        List<List<String>> lists = new ArrayList<>();
        // 遍历目标数组
        for (String s : strs) {
            // 尝试放入合格的容器
            if (putInRightList(s, lists)) {
                continue;
            }
            // 遍历容器没找到匹配的，就放个新的
            ArrayList<String> strings = new ArrayList<>();
            strings.add(s);
            lists.add(strings);
        }
        // 遍历容器，返回有货的
        List<String> result = new ArrayList();
        for (List<String> list : lists) {
            if (list.size() >= 2) {
                result.addAll(list);
            }
        }
        return result;
    }

    /**
     * 尝试放入正确的列表
     */
    private boolean putInRightList(String s, List<List<String>> lists) {
        // 遍历容器列表
        for (List<String> strList : lists) {
            // 如果容器不空
            if (!strList.isEmpty()) {
                // 判断是否匹配，匹配则放入
                if (anagram(s, strList.get(0))) {
                    strList.add(s);
                    // 放好后，结束
                    return true;
                }
            }
        }

        return false;
    }

    public int lengthOfLongestSubstring(String s) {

        return 0;
    }

    public int fibonacci(int n) {
        int first = 0;
        int second = 1;
        switch (n) {
            case 0:
            case 1:
                return first;
            case 2:
                return second + first;
        }
        // 3以上了
        int[] fiArr = new int[n];
        fiArr[0] = 0;
        fiArr[1] = 1;

        for (int i = 2; i < n; i++) {
            fiArr[i] = fiArr[i - 1] + fiArr[i - 2];
        }
        return fiArr[n - 1];
    }

    // Definition for singly-linked list.
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }

    public static ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        ListNode before = null;
        ListNode node = head;
        do {
            if (node == null) {
                break;
            }
            if (node.val == val) {
                if (before == null) {
                    node = head = node.next;
                } else {
                    node = before.next = node.next;
                }
                continue;
            }
            before = node;
            node = node.next;
        } while (true);
        return head;
    }

    public static int[] mergeSortedArray(int[] A, int[] B) {
        int[] result = new int[A.length + B.length];
        int rIndex = 0;
        int bBeginIndex = 0;
        for (int aIndex = 0; aIndex < A.length; aIndex++) {

            int a = A[aIndex];

            for (int bIndex = bBeginIndex; bIndex < B.length; bIndex++) {

                int b = B[bIndex];

                if (a <= b) {
                    result[rIndex++] = a;
                    bBeginIndex = ++bIndex;
                    result[rIndex++] = b;
                    break;
                } else {
                    result[rIndex++] = b;
                    if (bIndex == B.length - 1) {
                        result[rIndex++] = a;
                        bBeginIndex = ++bIndex;
                    }
                }

            }

        }

        return result;
    }


    public static int binarySearch(int[] nums, int target) {
        return search(nums, target, 0, nums.length);
    }

    private static int search(int[] nums, int target, int begin, int end) {
        int centerIndex = (begin + end) / 2;
        if (centerIndex == 0) {
            return 0;
        }
        if (target > nums[centerIndex]) {
            return search(nums, target, centerIndex, end);
        } else if (target < nums[centerIndex]) {
            return search(nums, target, begin, centerIndex);
        } else {
            return centerIndex;
        }
    }

    /*
     * @param head: n
     * @return: The new head of reversed linked list.
     */
    public ListNode reverse(ListNode head) {

        ListNode next = head;
        ListNode before = null;
        ListNode temp = null;
        while (true) {
            temp = next.next;
            next.next = before;
            before = next;
            if (temp == null) {
                return next;
            } else {
                next = temp;
            }
        }
    }

    /*
     * @param A: an integer sorted array
     * @param target: an integer to be inserted
     * @return: An integer
     */
    public int searchInsert(int[] A, int target) {
        int low = 0;
        int high = A.length - 1;
        int index = 0;
        int current = 0;
        int flag = 0;// 一杆旗，标记结束循环的那一刻
        while (low <= high) {
            index = (low + high) / 2;
            current = A[index];

            if (target < current) {
                flag = high = index - 1;
            } else if (target > current) {
                flag = low = index + 1;
            } else {
                return index;
            }
        }
        // 走到这步说明没找到，返回结束循环的旗子
        return flag < 0 ? 0 : flag;
    }

    /*
     * @param A: An integer array
     * @return: An integer
     */
    public int singleNumber(int[] A) {

        List<Integer> list = new ArrayList<>();

        for (int i : A) {
            int index = list.indexOf(i);
            if (index < 0) {
                list.add(i);
            } else {
                list.remove(index);
            }
        }

        return list.get(0);
    }

    /*
     * @param nums: a list of integers
     * @return: find a  majority number
     */
    public int majorityNumber(List<Integer> nums) {
        int result = nums.size() / 2;
        Map<Integer, Integer> map = new HashMap<>();
        Integer temp = null;
        for (int n : nums) {

            if ((temp = map.get(n)) != null) {
                map.put(n, ++temp);
                if (temp > result) {
                    return n;
                }
                temp = null;
            } else {
                map.put(n, 1);
            }
        }

        return result;
    }

    public boolean isValidParentheses(String s) {

        if (s == null || s.isEmpty()) {
            return false;
        }

        Map<Character, Integer> disc = new HashMap<>();
        disc.put('(', 1);
        disc.put(')', -1);
        disc.put('{', 2);
        disc.put('}', -2);
        disc.put('[', 3);
        disc.put(']', -3);


        char[] words = s.toCharArray();
        if (words.length % 2 != 0) {
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        char word;
        int value;
        for (int i = 0; i < words.length; i++) {
            word = words[i];
            value = disc.get(word);
            if (value > 0) {
                stack.push(value);
            } else {
                if (stack.pop() + value != 0) {
                    return false;
                }
            }

        }

        return true;
    }


    /*
     * @param digits: a number represented as an array of digits
     * @return: the result
     */
    public int[] plusOne(int[] digits) {
        int carry = 1;
        int num;
        for (int i = digits.length - 1; i >= 0; i--) {
            num = digits[i] += carry;
            carry = num / 10;
            if (carry == 0) {
                break;
            }
        }
        if (carry != 0) {
            int[] result = new int[digits.length + 1];
            System.arraycopy(digits, 0, result, 1, digits.length);
            return result;
        }

        return digits;
    }


    /*
     * @param l1: ListNode l1 is the head of the linked list
     * @param l2: ListNode l2 is the head of the linked list
     * @return: ListNode head of linked list
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode A = l1, B = l2;
        ListNode result = null;
        ListNode resultTemp = null;

        while (A != null || B != null) {

            if (A != null && B == null) {
                setListNode(resultTemp, A);
                break;
            } else if (A == null) {
                setListNode(resultTemp, B);
                break;
            }

            if (A.val < B.val) {
                resultTemp = A;
                setListNode(resultTemp, A);
                A = A.next;
            } else {
                setListNode(resultTemp, B);
                B = B.next;
            }

            if (result == null) {
                result = resultTemp;
            }
        }

        return result;
    }

    private void setListNode(ListNode root, ListNode node) {
        if (root == null) {
            root = node;
        } else {
            root.next = node;
            root = node;
        }
    }

    /*
     * @param num: a positive number
     * @return: true if it's a palindrome or false
     */
    public static boolean isPalindrome(int num) {
        char[] values = String.valueOf(num).toCharArray();
        int length = values.length;

        // 单数搞点骚操作
        if (length % 2 == 1) {
            int harf = Math.round(length / 2.0f);

            int temp = (int) Math.pow(10.0, harf);
            int head = num / temp;
            temp /= 10;
            int footer = num % temp;

            num = (head * temp) + footer;
            length--;
            values = String.valueOf(num).toCharArray();
        }
        for (int i = 1; i <= length / 2; i++) {
            if (values[i - 1] != values[length - i]) {
                return false;
            }
        }
        return true;
    }

    /*
     * @param n: the nth
     * @return: the nth sequence
     */
    public static String countAndSay(int n) {
        String str = "1";
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < n; i++) {
            str = say(count(str));
        }
        return str;
    }

    private static List<String> count(String src) {
        StringBuilder sb = null;
        List<String> numList = new ArrayList<>();
        char[] chars = src.toCharArray();
        char prev = '+';//加减号标记开头结尾
        char c;
        for (int i = 0; i <= chars.length; i++) {
            c = i == chars.length ? '-' : chars[i];
            if (c != prev) {// 切割
                if (sb != null) numList.add(sb.toString());// 这里用null来标记第一次，第一次不添加
                sb = new StringBuilder();
            }
            sb.append(c);
            prev = c;
        }

        return numList;
    }


    private static String say(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            char[] chars = s.toCharArray();
            if (chars.length > 0) {
                sb.append(chars.length);
                sb.append(chars[0]);
            }
        }
        return sb.toString();
    }


    static int size;

    public static int removeDuplicates(int[] nums) {
        size = nums.length;
        if (size < 2) {
            return size;
        }
        int prev = nums[0];
        for (int i = 1; i < size; i++) {
            if (nums[i] == prev) {
                removeElement(i, nums);
                i--;
            } else {
                prev = nums[i];
            }
        }

        return size;
    }

    private static void removeElement(int i, int[] nums) {

        int numMoved = size - i - 1;
        System.arraycopy(nums, i + 1, nums, i, numMoved);
        nums[--size] = 0;
    }

    /*
     * @param A: sorted integer array A which has m elements, but size of A is m+n
     * @param m: An integer
     * @param B: sorted integer array B which has n elements
     * @param n: An integer
     * @return: nothing
     */


    // 形参是个引用，只能在数组内部搞，直接拷贝
    public void mergeSortedArray(int[] A, int m, int[] B, int n) {
        int[] result = new int[m + n];
        int i = 0, j = 0;
        int small;
        while (i < m || j < n) {
            if (i >= m) {
                small = B[j++];
            } else if (j >= n) {
                small = A[i++];
            } else {
                if (A[i] < B[j]) {
                    small = A[i++];
                } else {
                    small = B[j++];
                }
            }
            // 这里的i、j必有一个已经递增了，所以减1解决
            result[i + j - 1] = small;
        }
        System.arraycopy(result, 0, A, 0, m + n);

    }


    public static void main(String[] args) {
        int[] arr = {-14, -14, -13, -13, -13, -13, -13, -13, -13, -12, -12, -12, -12, -11, -10, -9, -9, -9, -8, -7, -5, -5, -5, -5, -4, -3, -3, -2, -2, -2, -2, -1, -1, -1, -1, -1, 0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 6, 6, 6, 6, 7, 8, 8, 8, 9, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 12, 13, 14, 14, 14, 14, 15, 16, 16, 16, 18, 18, 18, 19, 19, 19, 19, 20, 20, 20, 21, 21, 21, 21, 21, 21, 22, 22, 22, 22, 22, 23, 23, 24, 25, 25};
        removeDuplicates(arr);
        System.out.println("args = " + Arrays.toString(arr));
    }

}


