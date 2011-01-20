package home.lang;

import home.lang.functional.Lambda1A;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TuplesList<F,S> {
    private List<Tuple<F,S>> ptList;

    public TuplesList() {
        ptList = new LinkedList();
    }

    public TuplesList<F,S> add(F f, S s) {
        Tuple<F,S> pt = new Tuple(f, s);
        ptList.add(pt);
        return this;
    }

    public List<Tuple<F, S>> getList() {
        return ptList;
    }

    public TuplesList<F,S> setList(List<Tuple<F, S>> ptList) {
        this.ptList = ptList;
        return this;
    }

    public Map<F,S> toMap(Map<F,S> initialMap) {
        Iterator<Tuple<F, S>> it = ptList.iterator();
        Tuple<F,S> entry;
        while(it.hasNext()) {
            entry = it.next();
            initialMap.put(entry.getFirst(), entry.getSecond());
        }
        return initialMap;
    }

    public Map<F,S> toTreeMap() {
        return this.toMap(new TreeMap());
    }

    public TuplesList<F,S> zip(F[] fArr, S[] sArr) {
        int farr_len = fArr.length;
        int sarr_len = sArr.length;
        int len = Math.max(farr_len, sarr_len);
        for(int i = 0; i < len ; i++) {
            if(i >= farr_len) {
                add(null, sArr[i]);
            } else if (i >= sarr_len) {
                add(fArr[i], null);
            } else
                add(fArr[i], sArr[i]);
        }
        return this;
    }

    public TuplesList<F,S> mapZip(F[] fArr, Lambda1A<F,S> la) {
        int farr_len = fArr.length;
        for(int i = 0; i < farr_len ; i++) {
            add(fArr[i], la.reduceLambda(fArr[i]));
        }
        return this;
    }
            
}
