package com.ambrosoft.exercises;

/**
 * Copyright Ambrosoft, Inc. 2020
 * User: jacek
 * Date: Jul 11, 2006
 * Time: 4:15:23 PM
 */

/*
  File: LinkedQueue.java

  Originally written by Doug Lea and released into the public domain.
  This may be used for any purposes whatsoever without acknowledgment.
  Thanks for the assistance and support of Sun Microsystems Labs,
  and everyone contributing, testing, and using this code.

  History:
  Date       Who                What
  11Jun1998  dl               Create public version
  25aug1998  dl               added peek
  10dec1998  dl               added isEmpty
  10oct1999  dl               lock on node object to ensure visibility
*/

/**
 * A linked list based channel implementation.
 * The algorithm avoids contention between puts
 * and takes when the queue is not empty.
 * Normally a put and a take can proceed simultaneously.
 * (Although it does not allow multiple concurrent puts or takes.)
 * This class tends to perform more efficently than
 * other Channel implementations in producer/consumer
 * applications.
 * <p>[<a href="http://gee.cs.oswego.edu/dl/classes/EDU/oswego/cs/dl/util/concurrent/intro.html"> Introduction to this package. </a>]
 */

final class LinkedQueue<E> implements Channel<E> {
    /**
     * A standard linked list node used in various queue classes *
     */
    private static class LinkedNode<E> {
        E value;
        LinkedNode<E> next;

        LinkedNode(E x) {
            value = x;
        }

        LinkedNode(E x, LinkedNode<E> n) {
            value = x;
            next = n;
        }
    }

    /**
     * Dummy header node of list. The first actual node, if it exists, is always
     * at head_.next. After each take, the old first node becomes the head.
     */
    protected LinkedNode<E> head_;

    /**
     * Helper monitor for managing access to last node.
     */
    protected final Object putLock_ = new Object();

    /**
     * The last node of list. Put() appends to list, so modifies last_
     */
    protected LinkedNode<E> last_;

    /**
     * The number of threads waiting for a take.
     * Notifications are provided in put only if greater than zero.
     * The bookkeeping is worth it here since in reasonably balanced
     * usages, the notifications will hardly ever be necessary, so
     * the call overhead to notify can be eliminated.
     */
    protected int waitingForTake_ = 0;

    public LinkedQueue() {
        head_ = new LinkedNode<E>(null);
        last_ = head_;
    }

    /**
     * Main mechanics for put/offer *
     */
    protected void insert(final E x) {
        synchronized (putLock_) {
            final LinkedNode<E> p = new LinkedNode<E>(x);
            synchronized (last_) {
                last_.next = p;
                last_ = p;
            }
            if (waitingForTake_ > 0) {
                putLock_.notify();
            }
        }
    }

    /**
     * Main mechanics for take/poll *
     */
    protected synchronized E extract() {
        synchronized (head_) {
            E x = null;
            final LinkedNode<E> first = head_.next;
            if (first != null) {
                x = first.value;
                first.value = null;
                head_ = first;
            }
            return x;
        }
    }

    public void put(final E x) throws InterruptedException {
        if (x == null) {
            throw new IllegalArgumentException();
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        insert(x);
    }

    public boolean offer(final E x, long msecs) throws InterruptedException {
        if (x == null) {
            throw new IllegalArgumentException();
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        insert(x);
        return true;
    }

    public E take() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        } else {
            // try to extract. If fail, then enter wait-based retry loop
            E x = extract();
            if (x != null) {
                return x;
            } else {
                synchronized (putLock_) {
                    try {
                        ++waitingForTake_;
                        for (; ; ) {
                            if ((x = extract()) != null) {
                                --waitingForTake_;
                                return x;
                            } else {
                                putLock_.wait();
                            }
                        }
                    } catch (InterruptedException ex) {
                        --waitingForTake_;
                        putLock_.notify();
                        throw ex;
                    }
                }
            }
        }
    }

    public E peek() {
        synchronized (head_) {
            final LinkedNode<E> first = head_.next;
            return first != null ? first.value : null;
        }
    }

    public boolean isEmpty() {
        synchronized (head_) {
            return head_.next == null;
        }
    }

    public E poll(final long msecs) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        } else {
            E x = extract();
            if (x != null) {
                return x;
            } else {
                synchronized (putLock_) {
                    try {
                        long waitTime = msecs;
                        long start = msecs <= 0 ? 0 : System.currentTimeMillis();
                        ++waitingForTake_;
                        for (; ; ) {
                            x = extract();
                            if (x != null || waitTime <= 0) {
                                --waitingForTake_;
                                return x;
                            } else {
                                putLock_.wait(waitTime);
                                waitTime = msecs - (System.currentTimeMillis() - start);
                            }
                        }
                    } catch (InterruptedException ex) {
                        --waitingForTake_;
                        putLock_.notify();
                        throw ex;
                    }
                }
            }
        }
    }
}


