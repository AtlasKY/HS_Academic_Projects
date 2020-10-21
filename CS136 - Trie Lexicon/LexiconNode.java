import structure5.*;
import java.util.Iterator;

/*
 * TODO: implement and document this class
 * You may add helper methods and member variables as you see fit.
 */
class LexiconNode implements Comparable<LexiconNode> {

    /* single letter stored in this node */
    protected char letter;

    /* true if this node ends some path that defines a valid word */
    protected boolean isWord;

    /* TODO: a data structure for keeping track of the children of
    this LexiconNode */

    OrderedVector<LexiconNode> children = new OrderedVector<LexiconNode>();

    /**
     * TODO: Constructor
     */
    public LexiconNode(char letter, boolean isWord){
      this.letter = letter;
      this.isWord = isWord;
    }

    /**
     * TODO: Compare this LexiconNode to another.
     * (You should just compare the characters stored at the Lexicon
     * Nodes.)
     */
    public int compareTo(LexiconNode o) {
      return Character.compare(letter, o.getLetter());
    }

    /**
     * TODO: Add LexiconNode child to correct position in child data
     * structure
     */
    public void addChild(LexiconNode ln){
      if(!children.contains(ln)){
        children.add(ln);
      }
    }

    //Pre: requested children exists in the list
    //Post: returns the requested children
    public LexiconNode getChild(char ch) {
      Assert.pre(contains(ch), "Illegal getChild operation!");
      AbstractIterator<LexiconNode> it = (AbstractIterator<LexiconNode>) iterator();
      LexiconNode temp = new LexiconNode(' ', false);
      while(it.hasNext()){
        temp = it.next();
        if(temp.compareTo(new LexiconNode(ch,false)) == 0){ break;}
      }
      return temp;
    }

    //Pre: Node is present in the children
    //Post: removes the given child from the lexicon
    public void removeChild(char ch) {
      Assert.pre(contains(ch), "Illegal remove operation!");
      children.remove(new LexiconNode(ch, false));
     }

     public boolean contains(char ch){
       return contains(new LexiconNode(ch, true));
     }
     public boolean contains(LexiconNode ln){
       return children.contains(ln);
     }

     public boolean equals(Object other){ return this.getLetter() == ((LexiconNode) other).getLetter();}

    /**
     * TODO: create an Iterator that iterates over children in
     * alphabetical order
     */
    public Iterator<LexiconNode> iterator() { return children.iterator(); }

    public char getLetter(){ return letter;}
    public boolean isWord(){ return isWord;}
    public boolean isWord(boolean b){
      isWord = b;
      return isWord;
    }
    public boolean hasChild(){ return !children.isEmpty();}
}
