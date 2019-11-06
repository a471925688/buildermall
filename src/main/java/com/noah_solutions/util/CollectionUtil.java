package com.noah_solutions.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionUtil {
    public static List setTurnList(Set set){
        List list = new ArrayList();
        list.addAll(set);
        return list;
    }

    public static Set listTurnSet(List list){
        Set set = new HashSet();
        set.addAll(list);
        return set;
    }
}
