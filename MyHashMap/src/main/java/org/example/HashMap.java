package org.example;

import java.util.Objects;

public class HashMap<K, V> {
    // 哈希表的初始容量
    static final int initial_capacity = 4;
    // 加载的因子
    float load_factor = 0.75f;
    // 记录entry的数量
    int count = 0;

    Entry<K, V>[] table;

    public K put(K key, V value) {
        Entry<K, V> newEntry = new Entry(key, value);
        int hash = hash(key);
        if (table == null) {
            table = new Entry[initial_capacity];
            count++;
        }
        Entry<K, V> head = table[hash];
        //执行扩容，
        if (count > initial_capacity * load_factor) {
            resize();
        }
        if (head == null) {
            table[hash] = newEntry;
            count++;
            return key;
        } else {
            Entry tail = new Entry<K, V>();
            if (head.next == null) {
                head.next = newEntry;
            } else {
                do {
                    tail = head;
                } while ((head = head.next) != null);
                tail.next = newEntry;
            }
            count++;
            return key;
        }
    }

    public V get(K key) {
        Entry<K, V> entry;
        return (entry = getEntry(hash(key), key)) == null ? null : entry.value;
    }

    public Entry<K, V> getEntry(int hash, K key) {
        Entry<K, V> entry = table[hash];
        if (entry == null) {
            return null;
        } else if (entry.next == null) {
            return entry;
        } else {
            do {
                if (hash == hash(entry.key) &&
                        (Objects.equals(key, entry.key))) {
                    return entry;
                }
            } while ((entry = entry.next) != null);
            return entry;
        }
    }

    public int resize() {
        int newCapacity = initial_capacity << 2;
        Entry[] newTable = new Entry[newCapacity];
        System.arraycopy(table, 0, newTable, 0, table.length);
        this.table = newTable;
        return newCapacity;
    }

    public final int hash(K key) {
        // key.hashCode可能产生负值，执行一次key.hashCode()& 0x7FFFFFFF操作，
        // 变为整数，这里产生hash值直接模4， 保证产生的hash值不会因扩容而产生变化
        return (key == null) ? 0 : (key.hashCode() & 0x7FFFFFFF % initial_capacity);
    }


    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name1", "严嵩");
        map.put("name2", "高拱");
        map.put("name3", "徐阶");
        map.put("name4", "张居正");
        map.put("name5", "申时行");
        System.out.println("map当前容量:" + map.count);
        System.out.println(map.get("name1"));
        System.out.println(map.get("name5"));
    }


}
