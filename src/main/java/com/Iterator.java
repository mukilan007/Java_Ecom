package com;

import java.util.ArrayList;

public class Iterator<E extends Object> {
//    private E[] t = null;
//    private int size;
//    private int count;
//    public Iterator(int size) {
//        this.size = size;
//        this.t = (E[]) new Object[size];
//    }
//    public void add(E value) {
//            t[count++] = value;
//        tt.add(value);
//    }
    private ArrayList<E> tt = new ArrayList<>();
    public Iterator(){}
    public void add(E value) {
        tt.add(value);
    }
    public InnerIterator getInstance() {
        return new InnerIterator(tt);
    }
    public class InnerIterator {
//        E[] t1;
//        private int index = 0;
//        public InnerIterator(E[] t1) {
//            this.t1 = t1;
//        }
//        public boolean hasNext() {
//            return index < size;
//        }
//        public E next() {
//            return t1[index++];
//        }
        ArrayList<E> tt1 = new ArrayList<>();
        private int index = 0;
        public InnerIterator(ArrayList<E> tt) {
            this.tt1 = tt;
        }
        public boolean hasNext() {
            return index < tt1.size();
        }
        public E next() {
            return tt1.get(index++);
        }
    }

}
