package com.destroyordefend.project.utility;

import java.util.HashMap;
import java.util.Map;

public class IdGenerator {
    private static Map<Class, Integer> ids = new HashMap<>();

    private IdGenerator() {
    }

    public static Integer generate(Object o) {
        Integer out = ids.get(o.getClass());
        if (out == null) {
            out = 0;
        }
        ids.put(o.getClass(), ++out);
        return out;
    }
}
