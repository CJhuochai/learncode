package com.example.demo.map;




/**
 * @program: limiting-demo
 * @description: 素组和链表
 * @author: Jian Chen
 * @create: 2021-12
 **/
public class MyLinkMap<K,V> {

    public static void main(String[] args) {
        MyLinkMap<Object,String> myMap = new MyLinkMap<>();
        myMap.put(97,"我是1");
        myMap.put("a","我是2");
        myMap.put(null,"我是null1");
        myMap.put(null,"我是null2");
        System.out.println(myMap.get(97));
        System.out.println(myMap.get("a"));
        System.out.println(myMap.get(null));
    }

    private MyEntry[] myEntries = new MyEntry[100];

    public MyEntry[] getMyEntries() {
        return myEntries;
    }

    public void put(K k, V v){
        final int i = k == null ? 0 : k.hashCode() % this.myEntries.length;
        if (null != this.myEntries[i]){
            MyEntry temp = this.myEntries[i];
            {
                if (null == temp.next){
                    temp.next = new MyEntry(k,v);
                }
                temp = temp.next;
            }while (temp == null);

        }else {
            this.myEntries[i] = new MyEntry(k,v);
        }
    }

    public V get(K k){
        final int i = k == null ? 0 : k.hashCode() % this.myEntries.length;
        final MyEntry oldEntry = this.myEntries[i];
        for (MyEntry m = oldEntry;m != null;m = oldEntry.next) {
            if ((k == null && m.k == null) || m.k.equals(k)){
                return (V) m.v;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "MyLinkMap{" +
                "myEntries=" + myEntries +
                '}';
    }

    static class MyEntry<K,V>{
        private K k;
        private V v;
        private MyEntry<K,V> next;

        public MyEntry(K k, V v) {
            this.k = k;
            this.v = v;
        }

        public K getK() {
            return k;
        }

        public void setK(K k) {
            this.k = k;
        }

        public V getV() {
            return v;
        }

        public void setV(V v) {
            this.v = v;
        }

        public MyEntry<K, V> getNext() {
            return next;
        }

        public void setNext(MyEntry<K, V> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "MyEntry{" +
                    "k=" + k +
                    ", v=" + v +
                    ", next=" + next +
                    '}';
        }
    }

}
