package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/6/16.
 */
public class ListPalindrome {
    /*
        list can be of even or odd length
        one, recursive approach would be to walk to the bottom of the list, carrying pointer to head
            then check data equality unfolding the stack & walking the list from beginning
     */


    private static final class Result {
        boolean known;
        boolean success;
        ListNode list;

        Result(ListNode list) {
            this.list = list;
        }

        Result step() {
            list = list.next;
            return this;
        }

        Result setSuccess() {
            known = success = true;
            return this;
        }

        Result setFailure() {
            known = true;
            return this;
        }
    }

    static Result isPalindrome(final ListNode ptr, final ListNode head) {
        if (ptr != null) {
            final Result result = isPalindrome(ptr.next, head);
            if (result.known) {
                return result;  // just propagate up
            } else if (result.list.data == ptr.data) {  // needed data equality
                if (result.list == ptr || result.list.next == ptr) {    // midpoint
                    return result.setSuccess();
                } else {
                    return result.step();
                }
            } else {
                return result.setFailure();
            }
        } else {
            return new Result(head);
        }
    }

    static boolean isPalindrome(final ListNode list) {
        System.out.println("list = " + list);
        return isPalindrome(list, list).success;
    }

    public static void main(String[] args) {
        System.out.println("r1 = " + isPalindrome(ListNode.fromArray(new int[]{1, 2, 3, 2, 1})));
        System.out.println("r1 = " + isPalindrome(ListNode.fromArray(new int[]{1, 2, 3, 2, 3})));
        System.out.println("r1 = " + isPalindrome(ListNode.fromArray(new int[]{1, 2, 3, 3, 2, 1})));
        System.out.println("r1 = " + isPalindrome(ListNode.fromArray(new int[]{1, 1})));
        System.out.println("r1 = " + isPalindrome(ListNode.fromArray(new int[]{1})));
        System.out.println("r1 = " + isPalindrome(ListNode.fromArray(new int[]{1, 2})));
        System.out.println("r1 = " + isPalindrome(ListNode.fromArray(new int[]{1, 2, 1})));
    }
}
