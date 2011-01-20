package home.lang;

import java.util.Collection;
import java.util.Iterator;

public class Either<L,R> {
    private boolean isLeft;
    private L left;
    private R right;

    public Either(L lVal) {
        this.left = lVal;
        this.isLeft = true;
    }

    public Either(Nothing nothing, R rVal) {
        this.right = rVal;
        this.isLeft = false;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public boolean isRight() {
        return !isLeft;
    }


    public L getLeft() {
        if(!isLeft) { throw new IllegalStateException("Invalid lookup for Left value, when Either is Right."); }
        return left;
    }

    public void setLeft(L lVal) {
        this.left = lVal;
        isLeft = true;
    }

    public R getRight() {
        if(isLeft) { throw new IllegalStateException("Invalid lookup for Right value, when Either is Left."); }
        return right;
    }

    public void setRight(R rVal) {
        this.right = rVal;
        isLeft = false;
    }

    public static <L,R> Either<L,R>[] allLefts(L... lis) {
        Either<L,R>[] ar = new Either[lis.length];
        for(int i = 0; i < lis.length; i++ ) ar[i] = new Either(lis[i]);
        return ar;
    }

    public static <L,R> Collection<Either<L,R>> allLefts(Collection<Either<L,R>> col, L... lis) {
        for(int i = 0; i < lis.length; i++ ) col.add(new Either(lis[i]));
        return col;
    }

    public static <L,R> Collection<Either<L,R>> allLefts(Collection<Either<L,R>> col, Collection<L> lis) {
        Iterator<L> it = lis.iterator();
        while(it.hasNext()) col.add(new Either(it.next()));
        return col;
    }

    public static <L,R> Either<L,R>[] allRights(R... lis) {
        Either<L,R>[] ar = new Either[lis.length];
        for(int i = 0; i < lis.length; i++ ) ar[i] = new Either(Nothing.IS, lis[i]);
        return ar;
    }

    public static <L,R> Collection<Either<L,R>> allRights(Collection<Either<L,R>> col, R... lis) {
        for(int i = 0; i < lis.length; i++ ) col.add(new Either(Nothing.IS, lis[i]));
        return col;
    }

    public static <L,R> Collection<Either<L,R>> allRights(Collection<Either<L,R>> col, Collection<R> lis) {
        Iterator<R> it = lis.iterator();
        while(it.hasNext()) col.add(new Either(Nothing.IS, it.next()));
        return col;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Either<L, R> other = (Either<L, R>) obj;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.left != null ? this.left.hashCode() : 0);
        hash = 29 * hash + (this.right != null ? this.right.hashCode() : 0);
        hash = this.isLeft ? -hash : hash;
        return hash;
    }

}
