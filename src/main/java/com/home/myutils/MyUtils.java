/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils.my;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MyUtils {
    // on NULL input returns NULL
    public static <T> ArrayList<T> iterator2revarrlist(Iterator<T> i) {
        if(!i.hasNext()) {
            return null;
        } else {
            T el = i.next();
            ArrayList<T> l = iterator2revarrlist(i);
            if (l == null) {
                l = new ArrayList<T>();
            }
            l.add(el);
            return l;
        }
    }

    public static <T> ArrayList<T> iterator2arrlist(Iterator<T> i) {
        ArrayList<T> l = new ArrayList<T>();
        while(i.hasNext()) {
            l.add(i.next());
        }
        return l;
    }

    public static <T extends Object> TreeMap<String, T> iterator2treemap(Iterator<T> i) {
        TreeMap<String, T> l = new TreeMap<String, T>();
        T el;
        while(i.hasNext()) {
            el = i.next();
            l.put(el.toString(), el);
        }
        return l;
    }

    public static <T extends Object> List<T> listToStrings(List<T> l) {
        List<String> ls = new ArrayList<String>();
        Iterator<T> i = l.iterator();
        while(i.hasNext()) {
            ls.add(i.next().toString());
        }
        return l;
    }

    public static boolean regexpCheck(String pattern, String target) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(target);
        return m.matches();
    }

    public static boolean regexpCheck(Pattern p, String target) {
        Matcher m = p.matcher(target);
        return m.matches();
    }

    public long sleepAtLeast(long millis) throws InterruptedException {
        long t0 = System.currentTimeMillis();
        long millisLeft = millis;
        while (millisLeft > 0) {
            Thread.sleep(millisLeft);
            long t1 = System.currentTimeMillis();
            millisLeft = millis - (t1 - t0);
        }
        return millis - millisLeft;
    }
}
