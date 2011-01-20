package home.utils.reflection;

import home.lang.Either;
import home.lang.Tuple;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ObjectsHierarchy {
    private static final
            ConcurrentMap
               < Tuple< Class<?>
                      , List< Either< Class<?>
                                    , ObjectsHierarchy
                      >     >       >
               , Tuple< Tuple< Class<?>
                             , List< Either< Class<?>
                                           , ObjectsHierarchy
                             >     >       >
                      , String
                      >
               > OH_SIGS =
            new ConcurrentHashMap
                       < Tuple< Class<?>
                              , List< Either< Class<?>
                                            , ObjectsHierarchy
                              >     >       >
                       , Tuple< Tuple< Class<?>
                                     , List< Either< Class<?>
                                                   , ObjectsHierarchy
                                     >     >       >
                              , String
                              >
                       >();

    private final Tuple< Class<?>
                       , List< Either< Class<?>
                                     , ObjectsHierarchy
                       >     >       > id;
    private final String sig;    

    public ObjectsHierarchy(Tuple<Class<?>, List<Either<Class<?>, ObjectsHierarchy>>> oh_tu) {
        Tuple< Tuple< Class<?>
                    , List< Either< Class<?>
                                  , ObjectsHierarchy
                    >     >       >
             , String
             > val = OH_SIGS.get(oh_tu);
        if(val == null) {
            val = OH_SIGS.putIfAbsent(oh_tu, new Tuple(oh_tu, this.sig));
            if(val != null) {
                this.sig = val.getSecond();
                this.id = val.getFirst();
            } else {
                this.sig = generateClassSimpleNameWithGenerics(oh_tu);
                this.id = oh_tu;
            }
        } else {
            this.sig = val.getSecond();
            this.id = val.getFirst();
        }
    }

    public ObjectsHierarchy(Class<?> head, Either<Class<?>,ObjectsHierarchy>... til) {
        Tuple<Class<?>, List<Either<Class<?>, ObjectsHierarchy>>> oh_tu =
                new Tuple( head
                         , Collections.addAll( new LinkedList<Either<Class<?>,ObjectsHierarchy>>()
                                             , til
                                             )
                         );
        Tuple< Tuple< Class<?>
                    , List< Either< Class<?>
                                  , ObjectsHierarchy
                    >     >       >
             , String
             > val = OH_SIGS.get(oh_tu);
        if(val == null) {
            val = OH_SIGS.putIfAbsent(oh_tu, new Tuple(oh_tu, this.sig));
            if(val != null) {
                this.sig = val.getSecond();
                this.id = val.getFirst();
            } else {
                this.sig = generateClassSimpleNameWithGenerics(oh_tu);
                this.id = oh_tu;
            }
        } else {
            this.sig = val.getSecond();
            this.id = val.getFirst();
        }
    }

    public ObjectsHierarchy(Class head, Class... til) {
        Tuple<Class<?>, List<Either<Class<?>, ObjectsHierarchy>>> oh_tu =
                new Tuple( head
                         , Either.allLefts( new LinkedList<Either<Class<?>,ObjectsHierarchy>>()
                                          , til
                                          )
                         );
        Tuple< Tuple< Class<?>
                    , List< Either< Class<?>
                                  , ObjectsHierarchy
                    >     >       >
             , String
             > val = OH_SIGS.get(oh_tu);
        if(val == null) {
            val = OH_SIGS.putIfAbsent(oh_tu, new Tuple(oh_tu, this.sig));
            if(val != null) {
                this.sig = val.getSecond();
                this.id = val.getFirst();
            } else {
                this.sig = generateClassSimpleNameWithGenerics(oh_tu);
                this.id = oh_tu;
            }
        } else {
            this.sig = val.getSecond();
            this.id = val.getFirst();
        }
    }

    public ObjectsHierarchy(Class<?> head) {
        Tuple<Class<?>, List<Either<Class<?>, ObjectsHierarchy>>> oh_tu =
                new Tuple(head, null);
        Tuple< Tuple< Class<?>
                    , List< Either< Class<?>
                                  , ObjectsHierarchy
                    >     >       >
             , String
             > val = OH_SIGS.get(oh_tu);
        if(val == null) {
            val = OH_SIGS.putIfAbsent(oh_tu, new Tuple(oh_tu, this.sig));
            if(val != null) {
                this.sig = val.getSecond();
                this.id = val.getFirst();
            } else {
                this.sig = generateClassSimpleNameWithGenerics(oh_tu);
                this.id = oh_tu;
            }
        } else {
            this.sig = val.getSecond();
            this.id = val.getFirst();
        }
    }

    public Tuple<Class<?>, List<Either<Class<?>, ObjectsHierarchy>>> getId() {
        return id;
    }

    public String getSig() {
        return sig;
    }

    private String generateClassSimpleNameWithGenerics(Tuple<Class<?>, List<Either<Class<?>, ObjectsHierarchy>>> oh_tu) {
        String s = oh_tu.getFirst().getSimpleName();
        Iterator<Either<Class<?>, ObjectsHierarchy>> it = oh_tu.getSecond().iterator();
        boolean hasParams = it.hasNext();

        if(hasParams) {
            s += "<";
            s += _gener(it.next());
        }
        while(it.hasNext()) {
            s += ",";
            s += _gener(it.next());
        }
        if(hasParams) s += ">";
        return s;
    }

    private  String _gener(Either<Class<?>, ObjectsHierarchy> elem) {
        if(elem.isLeft()) {
            return elem.getLeft().getSimpleName();
        } else {
            return elem.getRight().getSig();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ObjectsHierarchy other = (ObjectsHierarchy) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
