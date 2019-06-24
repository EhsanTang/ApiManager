package cn.crap.utils;

import java.util.Map;

public class MyHashMap {
    private Map<String, Object> map;

    private MyHashMap() {
    }

    public static MyHashMap getMap(Object... params) {
        MyHashMap myHashMap = new MyHashMap();
        myHashMap.map = Tools.getMap(params);
        return myHashMap;
    }

    public MyHashMap put(String key, Object obj) {
        if (MyString.isEmpty(key) || obj == null) {
            return this;
        }
        map.put(key, obj);
        return this;
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
