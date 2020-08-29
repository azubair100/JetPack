package com.zubair.kotlinjetpack.model

class Practise {

    fun reverseString(str: String){
        var answer = ""
       for(i in str.indices){
           answer = str[i] + answer
       }
    }


    fun singleNumber(nums: IntArray): Int {
        var hashMap : HashMap<Int, Int> = HashMap()
        //http://zetcode.com/kotlin/maps/

        for(i in nums.indices){
            if(hashMap.containsKey(nums[i])){
                val c = hashMap[nums[i]]!!
                hashMap[nums[i]] = c + 1
            }
            else{
                hashMap[nums[i]]  = 1
            }
        }

        for(key in hashMap.keys) {
            if(hashMap[key] == 1)  return key.toInt()
        }

        return 0
    }

    class ListNode(var `val`: Int) {
             var next: ListNode? = null
        }

    fun deleteNode(node: ListNode?) {
        node?.`val` = node?.next?.`val`!!
        node.next = node?.next?.next

    }

    /*
  https://www.programcreek.com/2014/07/leetcode-delete-node-in-a-linked-list-java/

LeetCode – Linked List Random Node (Java) https://www.programcreek.com/2014/08/leetcode-linked-list-random-node-java/
LeetCode – Reverse Linked List (Java) https://www.programcreek.com/2014/05/leetcode-reverse-linked-list-java/
LeetCode – Remove Linked List Elements (Java) https://www.programcreek.com/2014/04/leetcode-remove-linked-list-elements-java/
LeetCode – Odd Even Linked List (Java) https://www.programcreek.com/2015/03/leetcode-odd-even-linked-list-java/



    * */
}