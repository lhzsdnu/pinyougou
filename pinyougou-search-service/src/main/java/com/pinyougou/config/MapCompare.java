package com.pinyougou.config;

import java.util.HashMap;
import java.util.Map;

public class MapCompare {

    public static void main(String[] args) {

        Map<String, String> hs1 = new HashMap<String, String>();
        Map<String, String> hs2 = new HashMap<String, String>();

        hs1.put("001key", "001value");
        hs1.put("002key", "002value");
        hs1.put("003key", "003value");
        hs1.put("004key", "004value");
        hs1.put("005key", "005value");
        hs1.put("006key", "006value");
        hs1.put("007key", "007value");

        hs2.put("001key", "001value");
        hs2.put("002key", "002value");
        hs2.put("006key", "006value");
        hs2.put("005key", "005value");
        hs2.put("007key", "007value");
        hs2.put("004key", "004value");
        hs2.put("003key", "003value");


        boolean b1 = compare(hs1, hs2);
        System.out.println(b1);
    }

    /**
     * 判断map<=map2
     *
     * @param map1 小集合
     * @param map2 大集合
     * @return
     */
    public static boolean compare(Map<String, String> map1, Map<String, String> map2) {
        boolean flag = false;
        if(map1.size()==0){
            return  true;
        }
        flag = compareMapByEntrySet(map1, map2);
        return flag;
    }

    private static boolean compareMapByEntrySet(Map<String, String> map1, Map<String, String> map2) {

        //判断前提是map1.size()<=map2.size()
        if (map1.size()> map2.size()) {
            return false;
        }

        String tmp1;
        String tmp2;
        boolean b = false;

        for (Map.Entry<String, String> entry : map1.entrySet()) {
            if (map2.containsKey(entry.getKey())) {
                tmp1 = entry.getValue();
                tmp2 = map2.get(entry.getKey());

                if (tmp1 != null && tmp2 != null) {   //都不为null
                    if (tmp1.equals(tmp2)) {
                        b = true;
                        continue;
                    } else {
                        b = false;
                        break;
                    }
                } else if (tmp1 == null && tmp2 == null) {  //都为null
                    b = true;
                    continue;
                } else {
                    b = false;
                    break;
                }
            } else {
                b = false;
                break;
            }
        }


        return b;
    }

}
