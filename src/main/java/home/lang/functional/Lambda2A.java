package home.lang.functional;

public abstract class Lambda2A<A1, A2, R> {
    /* new Lambda1A<A1, A2, R>() {
     *                @Override public R reduceLambda(A1 a1, A2 a1) {
     *                   R r = null;
     *                   return r;
     *               }
     *           };
     */
    
    public abstract R reduceLambda(A1 a1, A2 a2);

    public Lambda1A<A2, R> reduceTo1A_rFirst(final A1 a1) {
        final Lambda2A<A1, A2, R> upper_la = this;
        return new Lambda1A<A2, R>() {
            @Override public R reduceLambda(A2 a2) {
                return upper_la.reduceLambda(a1, a2);
            }
        };
    }

    public Lambda1A<A1, R> reduceTo1A_rSecond(final A2 a2) {
        final Lambda2A<A1, A2, R> upper_la = this;
        return new Lambda1A<A1, R>() {
            @Override public R reduceLambda(A1 a1) {
                return upper_la.reduceLambda(a1, a2);
            }
        };
    }

    public Lambda0A<R> reduceTo0A(final A1 a1, final A2 a2) {
        final Lambda2A<A1, A2, R> upper_la = this;
        return new Lambda0A<R>() {
            @Override public R reduceLambda() {
                return upper_la.reduceLambda(a1, a2);
            }
        };
    }
}
