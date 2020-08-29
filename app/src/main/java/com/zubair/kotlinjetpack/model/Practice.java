package com.zubair.kotlinjetpack.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

//Calculator 1 2 3
@RequiresApi(api = Build.VERSION_CODES.N)
public class Practice {

    public int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        // remove leading and trailing spaces and white spaces.
        //
        s = s.trim().replaceAll("[ ]+", "");

        if (s == null || s.length() == 0) {
            return 0;
        }

        Stack<Character> opStack = new Stack<>();
        Stack<Integer> numStack = new Stack<>();

        int i = 0;
        while (i < s.length()) { //this will iterate over all of the string
            //create the calculate method after this

            //start block for adding numbers
            if (Character.isDigit(s.charAt(i))) {
                int num = 0;
                //if the next characters are numbers just add it
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + Character.getNumericValue(s.charAt(i));
                    i++; // move to next index
                }
                numStack.push(num);
            }//end block for adding numbers


            else {//characters are symbol block
                char op = s.charAt(i);
                if (opStack.isEmpty()) { //if empty push
                    opStack.push(op);
                    i++;
                }

                //plus or minus scenario
                else if (op == '+' || op == '-') {
                    char top = opStack.peek();

                    if (top == '(') { //count for parenthesis
                        opStack.push(op);
                        i++;
                    } else {
                        calculate(numStack, opStack);
                    }


                }


                //multiply or division scenario
                else if (op == '*' || op == '/') {
                    char top = opStack.peek();

                    if (top == '(') {
                        opStack.push(op);
                        i++;
                    }
                    else if (top == '*' || top == '/') {
                        calculate(numStack, opStack);
                    }

                    else if (top == '+' || top == '-') {
                        opStack.push(op);
                        i++;
                    }
                }


                //open parenthesis scenario
                else if (op == '(') {
                    opStack.push(op);
                    i++;
                }

                //closed parenthesis scenario
                else if (op == ')') {
                    while (opStack.peek() != '(') {
                        calculate(numStack, opStack);
                    }
                    opStack.pop();
                    i++;
                }
            } //characters are symbol block ends
        }


        while (!opStack.isEmpty()) {
            calculate(numStack, opStack);
        }

        return numStack.peek();
    }

    private void calculate(Stack<Integer> numStack, Stack<Character> opStack) {
        int num2 = numStack.pop();
        int num1 = numStack.pop();

        char op = opStack.pop();

        int ans = 0;

        switch(op) {
            case '+':
                ans = num1 + num2;
                break;
            case '-':
                ans = num1 - num2;
                break;
            case '*':
                ans = num1 * num2;
                break;
            case '/':
                ans = num1 / num2;
                break;
        }

        numStack.push(ans);
    }

}

class LRUCache {
    //1.Create this first
    class Node {
        int key;
        int val;
        Node prev;
        Node next;
        public Node(int k, int v) {
            key = k;
            val = v;
        }
    }
    //1.ends

    //2. starts ere
    Node head;
    Node tail;
    Map<Integer, Node> map;
    int capacity;
    int size;
    public LRUCache(int capacity) {
        head = new Node(0, 0);
        tail = new Node(0, 0);
        map = new HashMap<>();
        head.next = tail;
        tail.prev = head;
        this.capacity = capacity;
        size = 0;
    }
    //2 ends here

    //3. starts
    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            remove(key);
            addHead(key, node.val);
            return node.val;
        } else return -1;
    }
    //3. ends

    //4. starts
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            remove(key);
        }
        addHead(key, value);
    }
    //4. ends

    //5. starts
    private void remove(int key) {
        Node cur = map.get(key);
        Node prev = cur.prev;
        Node next = cur.next;
        prev.next = next;
        next.prev = prev;
        size--;
        map.remove(key);
    }

    private void addHead(int key, int val) {
        Node node = new Node(key, val);
        Node next = head.next;
        head.next = node;
        node.next = next;
        next.prev = node;
        node.prev = head;
        map.put(key, node);
        size++;
        if (size > capacity) {
            Node preTail = tail.prev;
            remove(preTail.key);
        }
    }
    //5.ends
}

class LFUCache {

    //1.create this first
    class Node {
        int key, val;
        int cnt;
        Node prev, next;
        public Node(int k, int v) {
            key = k;
            val = v;
            cnt = 1;
        }
    }//1. ends

    //2.create this
    class DLList {
        Node head, tail;
        int len;
        public DLList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            len = 0;
        }

        public void addHead(Node node) {
            Node next = head.next;
            head.next = node;
            node.prev = head;
            node.next = next;
            next.prev = node;
            map.put(node.key, node); //create 3.
            len++;
        }

        public void remove(Node node) {
            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
            len--;
            map.remove(node.key);
        }

        public void removeTail() {
            Node prevTail = tail.prev;
            remove(prevTail);
        }
    }//2.ends

    //3.create this
    Map<Integer, Node> map;
    Map<Integer, DLList> freq;
    int size, capacity;
    int maxFreq;
    //3 ends included in 2 step

    //4. constructor creation
    public LFUCache(int capacity) {
        map = new HashMap<>();
        freq = new HashMap<>();
        this.capacity = capacity;
        size = 0;
        maxFreq = 0;
    }
    //4. ends

    //5.starts
    @RequiresApi(api = Build.VERSION_CODES.N)
    public int get(int key) {
        if (map.get(key) == null) return -1;
        Node node = map.get(key);
        int prevFreq = node.cnt;
        DLList prevList = freq.get(prevFreq);
        prevList.remove(node);

        int curFreq = prevFreq + 1;
        maxFreq = Math.max(maxFreq, curFreq); //7 starts here
        DLList curList = freq.getOrDefault(curFreq, new DLList());
        node.cnt++;
        curList.addHead(node);

        freq.put(prevFreq, prevList);
        freq.put(curFreq, curList);
        return node.val;
    }
    //5.ends

    //6.starts
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void put(int key, int value) {
        if (capacity == 0) return;
        if (map.get(key) != null) {
            map.get(key).val = value;
            get(key);
            return;
        }

        Node node = new Node(key, value);
        DLList curList = freq.getOrDefault(1, new DLList());
        curList.addHead(node);
        size++;

        if (size > capacity) {
            if (curList.len > 1) {
                curList.removeTail();
            } else {
                for (int i = 2; i <= maxFreq; i++) {
                    if (freq.get(i) != null && freq.get(i).len > 0) {
                        freq.get(i).removeTail();
                        break;
                    }
                }
            }

            size--;
        }

        freq.put(1, curList);
    }
    //look for 7 in get
    //6 ends
}



/*
*
* Implement a SnapshotArray that supports the following interface:

SnapshotArray(int length) initializes an array-like data structure with the given length.  Initially, each element equals 0.
void set(index, val) sets the element at the given index to be equal to val.
int snap() takes a snapshot of the array and returns the snap_id: the total number of times we called snap() minus 1.
int get(index, snap_id) returns the value at the given index, at the time we took the snapshot with the given snap_id
* */

class SnapshotArray {
    //1. first thing
    private List<TreeMap<Integer, Integer>> map;
    private int snapId;
    //1 ends

    //2 starts
    public SnapshotArray(int length) {
        snapId = 0;
        map = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            map.add(new TreeMap<Integer, Integer>());
            map.get(i).put(0, 0);
        }
    }
    //2 ends

    //3 starts
    public void set(int index, int val) {
        map.get(index).put(snapId, val);
    }
    //3 ends

    //4 starts
    public int snap() {
        return snapId++;
    }
    //4 ends

    public int get(int index, int snap_id) {
        return map.get(index).floorEntry(snap_id).getValue();
        //It returns a key-value mapping associated with the greatest key
        // less than or equal to the given key, or null if there is no such key.
        // floorKey() : It returns the greatest key less than or equal to the given key,
        // or null if there is no such key.
    }
}


//max points in a line maxpoint
/*
hash set
implements the Set interface, backed by a hash table
does not guarantee the constant order of elements over time
 null element permitted
 constant time performance for the basic operations like add, remove, contains and size

                Number of stored elements in the table
   load factor = -----------------------------------------
                        Size of the hash table
* */
class Solution {

    //1. starts
    @RequiresApi(api = Build.VERSION_CODES.N)
    public int maxPoints(int[][] points) {
        int pointsArrayLength = points.length;
        if (pointsArrayLength < 2) return pointsArrayLength; //if its 0 or 1 that's the max point

        // easier to map 1 specifc  point with a string hashset
        //using hashset cause I am gonna map one point with a string set
        Set<String> set = new HashSet<>();
        //at least one point is availale cause (pointsArrayLength < 2)
        int max = 1;

        //iterate over all the points
        //if the set already contains these two points points[i][0] + "-" + points[i][1]
        for (int i = 0; i < pointsArrayLength && !set.contains(points[i][0] + "-" + points[i][1]); i++) {
            int[] a = points[i]; //get current point
            int same = 0; //calculate the same point, currently is 0
            Map<Double, Integer> map = new HashMap<>();
            int localMax = 1; //currently it's one because I already have 1 point int [] a

            //time to iterate over the rest
            for (int j = i + 1; j < pointsArrayLength; j++) {
                //create is same
                if (isSame(a, points[j])) { //check whether two points are same
                    same++;
                    continue;
                }

                //not same create get slope blow
                double slope = getSlope(a, points[j]); //if not same
                //we add this to the map
                //if we have this slope before the default value should be 1
                //cause this map actually maps to the number of the points of this line
                //so at least it will be 1
                map.put(slope, map.getOrDefault(slope, 1) + 1);
                localMax = Math.max(localMax, map.get(slope)); // 3 points
            }

            //so we need to add this current points
            set.add(a[0] + "-" + a[1]);
            max = Math.max(max, localMax + same);
        }

        return max;
    }//1. ends

    private boolean isSame(int[] a, int[] b) {
        return a[0] == b[0] && a[1] == b[1];
    }

    private double getSlope(int[] a, int[] b) {
        if (a[0] == b[0]) return Double.MAX_VALUE; //parallel in y axis
        if (a[1] == b[1]) return 0; //parallel in x axis
        return ((double) b[0] - a[0]) / ((double) b[1] - a[1]);
    }




    //number of islands island numberIsland
    public int numIslands(char[][] grid){
        //error checking
        if(grid == null || grid.length == 0){
            return 0;
        }
        //this is the answer
        int numberOfIslands = 0;

        //need a way to traverse the island
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[j].length; j++){
                //if we are on a 1 that's where we want to run operations
                if(grid[i][j] == '1'){
                    //create exploreIslands before doing anything look below
                    numberOfIslands += exploreIslands(grid, i, j);
                }
            }
        }

        return numberOfIslands;
    }

    //we need all the neibor's of 1s and then a backtrack & not double counting
    private int exploreIslands(char[][]grid, int i, int j){
        if(i<0  /*out of the grid top*/
                || i >= grid.length /*out of the grd below*/ ||
                j <0 /*out of the grd left*/||
                j >=grid[i].length /*out of the grd right*/||
                grid[i][j] == '0' /*not an island*/){
            return 0;
        }
        //so we have to set it to 0 so that we don't double count
        grid[i][j] = '0';
        exploreIslands(grid,i+1,j);
        exploreIslands(grid,i-1,j);
        exploreIslands(grid,i,j+1);
        exploreIslands(grid,i,j-1);
        return  1;
    }

}


class TinyURL{
    private Map<String, String> encoderMap;
    private Map<String, String> decoderMap;
    private Integer encodedValue; //can go upto 2billion

    public TinyURL() {
        encoderMap = new HashMap<>();
        decoderMap = new HashMap<>();
        encodedValue = 0;
    }

    public String encode(String fullUrl){
        String encodedUrl = "http://tinyurl.com" + ++encodedValue;
        encoderMap.put(fullUrl, encodedUrl);
        decoderMap.put(encodedUrl, fullUrl);
        return encodedUrl;
    }

    public String decode(String shortUrl){
        if(decoderMap.containsKey(shortUrl)){
            return decoderMap.get(shortUrl);
        }
        else{
            return "";
        }
    }



}


/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List param_1 = obj.input(c);
 * Design a search autocomplete system for a search engine. Users may input a sentence (at least one word and end with a special character '#'). For each character they type except '#', you need to return the top 3 historical hot sentences that have prefix the same as the part of sentence already typed. Here are the specific rules:
 *
 * The hot degree for a sentence is defined as the number of times a user typed the exactly same sentence before.
 * The returned top 3 hot sentences should be sorted by hot degree (The first is the hottest one). If several sentences have the same degree of hot, you need to use ASCII-code order (smaller one appears first).
 * If less than 3 hot sentences exist, then just return as many as you can.
 * When the input is a special character, it means the sentence ends, and in this case, you need to return an empty list. Your job is to implement the following functions:
 * The constructor function:
 */


//search design search search auto autocomplete
    //create a tri node and maintain a hotlist
class AutocompleteSystem {
    //1. starts here
    class TrieNode implements Comparable {
        TrieNode[] children;
        String s; //record the whole sentance
        int times; //record the times it appears
        List<TrieNode> hot; //hot tri nodes
        //but we also need an update fun to see if something is ho more than

        public TrieNode() {
            //usually its 26 tri node array,but we use 128 ascii codes
            children = new TrieNode[128];
            s = null;
            times = 0;
            hot = new ArrayList<>();
        }

        public int compareTo(TrieNode o) {
            if (this.times == o.times) {
                return this.s.compareTo(o.s);
            }

            //return the differnece
            return o.times - this.times;
        }

        //when the hotlist is greater than 3
        public void update(TrieNode node) {
            if (!this.hot.contains(node)) {
                this.hot.add(node);
            }

            Collections.sort(hot);

            //remove the last one, 3 is for the top 3
            if (hot.size() > 3) {
                hot.remove(hot.size() - 1);
            }
        }

        @Override //not used
        public int compareTo(Object o) {
            return 0;
        }
    }

    //ends 1

    //2 starts
    TrieNode root; //this is the starting point
    TrieNode cur; //this is the tri node that will point to following characters
    StringBuilder sb; // record the counters


    //2.1
    public AutocompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();
        cur = root;
        sb = new StringBuilder();

       // 3.create add()
        for (int i = 0; i < times.length; i++) {
            add(sentences[i], times[i]);
        }
    }
    //2.1 ends

//3. starts
    //when I love you we need to record all the tri node 2 white space to hotlist
    public void add(String sentence, int t) {
        TrieNode tmp = root;

        //add this line later
        //we have to add this temporary to other list that was visited
        List<TrieNode> visited = new ArrayList<>();

        //and we try to every node visited
        for (char c : sentence.toCharArray()) {
            if (tmp.children[c] == null) {
                tmp.children[c] = new TrieNode();
            }

            tmp = tmp.children[c]; //last count of this sentance
            visited.add(tmp);//add this line later 2
        }

        tmp.s = sentence;
        tmp.times += t;

        //fr every tri node visited we add the temp to the hotlist
        for (TrieNode node : visited) {
            node.update(tmp);
        }
    }

    //4. whenever # appears we have to add whatever is in the sting builder -1 to tri
    //then reset
    //otherwise we just append the character to & get top 3 sentences
    public List<String> input(char c) {
        //to return result
        List<String> res = new ArrayList<>();
        //scenario for new sentence
        if (c == '#') {
            add(sb.toString(), 1);
            sb = new StringBuilder(); //reset
            cur = root;
            return res;
        }

        //otherwise we append the character  to string builder
        sb.append(c);
        if (cur != null) {
            cur = cur.children[c];
        }

        //we can not find any match
        if (cur == null) return res;

        //otherwise we add the current hot node to the result
        for (TrieNode node : cur.hot) {
            res.add(node.s);
        }

        return res;
    }
}





/*
* calendar 1 no double booking
* calendar 2 double booking allowed but not triple booking
* calendar 3 K generic numbr
* */



/*
* calendar 1
*Implement a MyCalendar1 class to store your events. A new event can be added if adding the event will not cause a double booking.

Your class will have the method, book(int start, int end). Formally, this represents a booking on the half open interval [start, end), the range of real numbers x such that start <= x < end.
* */


//TreeMap efficient way of sorting and storing the key-value pairs
class MyCalendar1 {
    private TreeMap<Integer, Integer> map;

    public MyCalendar1() {
        map = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        Integer prev = map.floorKey(start), // It returns a key-value mapping associated with the
                // greatest key less than or equal to the given key,
                // or null if there is no such key.
                next = map. ceilingKey(start);//The ceilingKey() function of TreeMap
        // Class returns the least key greater than or equal to the given key
        // or null if the such a key is absent.


        //make sure that current event has no conflict with previous event or next event
        if ((prev == null || map.get(prev) <= start)
                && (next == null || next >= end)) {
            map.put(start, end);
            return true;
        }

        return false;
    }
}



/*
calendar 2
Implement a MyCalendar2 class to store your events. A new event can be added if adding the event will not cause a triple booking.

Your class will have one method, book(int start, int end). Formally, this represents a booking on the half open interval [start, end), the range of real numbers x such that start <= x < end.

A triple booking happens when three events have some non-empty intersection (ie., there is some time that is common to all 3 events.)

For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully without causing a triple booking. Otherwise, return false and do not add the event to the calendar.

Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
**/

//TreeMap efficient way of sorting and storing the key-value pairs
@RequiresApi(api = Build.VERSION_CODES.N)
class MyCalendar2 {
    private TreeMap<Integer, Integer> map;
    public MyCalendar2() {
        map = new TreeMap<>();
    }

    //so we need to save the frequency of the event
    //so key will e start and value of it will be the frequency
    //also need to send the end time
    public boolean book(int start, int end) {
        //when we have start time we have active events increment by 1
        map.put(start, map.getOrDefault(start, 0) + 1);
        //when we have end time we have active events decrement by 1 since its ending
        map.put(end, map.getOrDefault(end, 0) - 1);

        //we simply have to check now if there is an active event count greater than =3
        int count = 0;
        for (Integer d: map.values()) {
            count += d;
            if (count >= 3) {
                //un book the event
                map.put(start, map.get(start) - 1);
                map.put(end, map.get(end) + 1);
                return false;
            }
        }

        return true;
    }
}
    

/*

calendar 3
mplement a MyCalendarThree class to store your events. A new event can always be added.

Your class will have one method, book(int start, int end). Formally, this represents a booking on the half open interval [start, end), the range of real numbers x such that start <= x < end.

A K-booking happens when K events have some non-empty intersection (ie., there is some time that is common to all K events.)

largest integer
*
* */


//TreeMap efficient way of sorting and storing the key-value pairs
@RequiresApi(api = Build.VERSION_CODES.N)
class MyCalendar3 {
    private TreeMap<Integer, Integer> map;
    public MyCalendar3() {
        map = new TreeMap<>();
    }

    //so we need to save the frequency of the event
    //so key will e start and value of it will be the frequency
    //also need to send the end time
    public int book(int start, int end) {
        //when we have start time we have active events increment by 1
        map.put(start, map.getOrDefault(start, 0) + 1);
        //when we have end time we have active events decrement by 1 since its ending
        map.put(end, map.getOrDefault(end, 0) - 1);

        //we simply have to check now if there is an active event count greater than =3
        int count = 0;
        int k = 0;  //answer
        for (Integer d: map.values()) {
            count += d;
            /*
            calendar 2
            if (count >= 3) {
                //un book the event
                map.put(start, map.get(start) - 1);
                map.put(end, map.get(end) + 1);
                return false;
            }*/

            if (count > k) {
                k = count;
            }
        }

        return k;
    }
}
/*
LeetCode – Linked List Random Node (Java) https://www.programcreek.com/2014/08/leetcode-linked-list-random-node-java/
        LeetCode – Reverse Linked List (Java) https://www.programcreek.com/2014/05/leetcode-reverse-linked-list-java/
        LeetCode – Remove Linked List Elements (Java) https://www.programcreek.com/2014/04/leetcode-remove-linked-list-elements-java/
        LeetCode – Odd Even Linked List (Java) https://www.programcreek.com/2015/03/leetcode-odd-even-linked-list-java/

*/


/*Longest increasing path in a matrix*/

class Solution2 {
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        int rows = matrix.length, cols = matrix[0].length;
        int[][] dp = new int[rows][cols];
        int res = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (dp[i][j] == 0) {
                    dfs(matrix, i, j, dp, Integer.MIN_VALUE);
                    res = Math.max(res, dp[i][j]);
                }
            }
        }

        return res;
    }

    private int dfs(int[][] matrix, int row, int col, int[][] dp, int prev) {

        /*if(i<0  *//*out of the grid top*//*
                || i >= grid.length *//*out of the grd below*//* ||
                j <0 *//*out of the grd left*//*||
                j >=grid[i].length *//*out of the grd right*//*||
                grid[i][j] == '0' *//*not an island*//*){
            return 0;
        }*/
        if (row > matrix.length - 1 || row < 0 ||
                col > matrix[0].length - 1 || col < 0 ||
                matrix[row][col] <= prev) return 0;

        if (dp[row][col] != 0) return dp[row][col];

        int left = dfs(matrix, row, col - 1, dp, matrix[row][col]);
        int right = dfs(matrix, row, col + 1, dp, matrix[row][col]);
        int up = dfs(matrix, row - 1, col, dp, matrix[row][col]);
        int down = dfs(matrix, row + 1, col, dp, matrix[row][col]);

        dp[row][col] = Math.max(left, Math.max(right, Math.max(up, down))) + 1;
        return dp[row][col];
    }


//String compression
    public int compress(char[] chars) {
        //indexRes where we point to final result
        //i where we have regular index
        int indexRes = 0, i = 0;
        while (i < chars.length) {
            //save current character
            char cur = chars[i];

            int count = 0; //count similar character

            //increase count & move index forward
            while (i < chars.length && chars[i] == cur) {
                count++;
                i++;
            }

            chars[indexRes++] = cur;
            if (count != 1) {
                for (char c : String.valueOf(count).toCharArray()) {
                    chars[indexRes++] = c;
                }
            }
        }

        return indexRes;
    }

}

