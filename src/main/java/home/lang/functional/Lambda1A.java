package home.lang.functional;

public abstract class Lambda1A<A1, R> {
    /* new Lambda1A<A1, R>() {
     *                @Override public R reduceLambda(A1 a1) {
     *                   R r = null;
     *                   return r;
     *                }
     *           };
     */
    
    public abstract R reduceLambda(A1 a1);
    
    public Lambda0A<R> reduceTo0A(final A1 a1) {
        final Lambda1A<A1, R> upper_la = this;
        return new Lambda0A<R>() {
            @Override public R reduceLambda() {
                return upper_la.reduceLambda(a1);
            }
        };
    }
}
