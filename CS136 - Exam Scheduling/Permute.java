import java.util.Iterator;
import structure5.*;

public class Permute<E> extends AbstractIterator<Vector<E>> {

    protected Vector<E> elems = new Vector<E>();
    protected long count = 0;
    protected long max = 1;
	
    public Permute(Iterator<E> iter) {
	super();
	while (iter.hasNext()) {
	    elems.add(iter.next());
	}
	max = fact(elems.size());
    }

    private long fact(long n) {
	if (n == 0) return 1L;
	return n * fact(n-1);
    }

    public void reset() {
	count = 0;
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
	return count < max;
    }

    protected Vector<E> generate(long perm) {
	Vector<E> v = new Vector<E>();
	Vector<E> left = new Vector<E>();
	for (E e : elems) left.add(e);
	long n = elems.size();
	for (long i = 0; i < elems.size(); i++) {
	    v.add(left.remove((int)(perm % n)));
	    perm /= n;
	    n--;
	}
	return v;
    }
	
    /* (non-Javadoc)
     * @see structure.AbstractIterator#get()
     */
    public Vector<E> get() {
	return generate(count);
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    public Vector<E> next() {
	return generate(count++);
    }

    public static void main(String args[]) {
	Vector<String> v = new Vector<String>();
	v.add("A");
        v.add("B");
        v.add("C");
        v.add("D");
        v.add("E");
        v.add("F");
        v.add("G");
        v.add("H");
        v.add("I");
        v.add("J");
//        v.add("K");
//        v.add("L");
//        v.add("M");
//         v.add("N");
//         v.add("O");
//         v.add("P");
//         v.add("Q");
//         v.add("R");
//         v.add("S");
//         v.add("T");
//         v.add("U");
//         v.add("V");
//         v.add("W");
//         v.add("X");
//         v.add("Y");
//         v.add("Z");
 	Iterator<Vector<String>> iter = new Permute<String>(v.iterator());
	System.out.println(v);
	while (iter.hasNext()) {
	    System.out.println(iter.next());
	}
    }
	
}
