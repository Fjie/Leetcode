import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by admin on 2018/1/31.
 */
public class LintCode {


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
        if(head == null){
            return null;
        }
        ListNode before = null;
        ListNode node = head;
        do{
            if(node == null){
                break;
            }
            if(node.val == val){
                if(before == null){
                    node = head = node.next;
                }else{
                    node = before.next = node.next;
                }
                continue;
            }
            before = node;
            node = node.next;
        }while(true);
        return head;
    }

    public static int[] mergeSortedArray(int[] A, int[] B) {
        int[] result = new int[A.length + B.length];
        int rIndex = 0;
        int bBeginIndex = 0;
        for(int aIndex = 0; aIndex < A.length;aIndex ++){

            int a = A[aIndex];

            for(int bIndex = bBeginIndex; bIndex < B.length; bIndex ++){

                int b = B[bIndex];

                if(a <= b){
                    result[rIndex++] = a;
                    bBeginIndex = ++bIndex;
                    result[rIndex++] = b;
                    break;
                }else{
                    result[rIndex++] = b;
                    if(bIndex == B.length -1){
                        result[rIndex++] = a;
                        bBeginIndex = ++bIndex;
                    }
                }

            }

        }

        return result;
    }


    public static int binarySearch(int[] nums, int target) {
        return search(nums,target,0,nums.length);
    }

    private static int search(int[] nums, int target,int begin,int end){
        int centerIndex = (begin + end)/2;
        if(centerIndex == 0){
            return 0;
        }
        if(target > nums[centerIndex]  ){
            return search(nums,target,centerIndex,end);
        }else if(target < nums[centerIndex]){
            return search(nums,target,begin,centerIndex);
        }else{
            return centerIndex;
        }
    }


    public static void main(String[] args) {
        List<String> strs = new ArrayList<>();

        strs.add("1");
        strs.add("2");
        strs.add("3");
        strs.add("4");
        strs.add("5");
        strs.add("6");

        Iterator<String> iterator = strs.iterator();

        while (iterator.hasNext()){
            String next = iterator.next();
            if(next.equals("3")){
                iterator.remove();
            }
            System.out.println("iterator.next() = " + next);
        }

        System.out.println("strs = " + strs);

    }

}


