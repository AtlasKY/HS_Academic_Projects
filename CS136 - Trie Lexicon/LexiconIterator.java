import structure5.*;
import java.util.Iterator;


//CLASS: An iterator class for the Lexicon objects that returns the words in the lexicon in alphabetical order
public class LexiconIterator extends AbstractIterator<String> {

  private LexiconNode root; //stores the root of the lexicontrie
  private SinglyLinkedList<String> list; //the list of words in alphabetical order
  private AbstractIterator<String> iter; //iterator for the elements in the list/words in lexicon

  //Constructor: initialises the list instance, fills it with lister method, initialises an iterator over the list
  public LexiconIterator(LexiconNode ln){
    root = ln;
    list = new SinglyLinkedList<String>();
    lister(root);
    iter = (AbstractIterator<String>) list.iterator();
  }
  //using the root node calls the recursive method using every starter letter that is a child of the root node
  private void lister(LexiconNode ln){
    Iterator<LexiconNode> it = ln.iterator();
    System.out.println("Constructing iterator.");
    while(it.hasNext()){
      LexiconNode ch = it.next();
      listHelper(ch, "");
    }
  }
  //recursively calls itself for every child of every node and adds the letters to a word string
  //and adds the word to the list if node is a word
  private void listHelper(LexiconNode ln, String word){
    word = word + ln.getLetter();
    if(ln.isWord()){ list.add(word); System.out.println(word);}

    if(!ln.hasChild()){ return;}

    Iterator<LexiconNode> it = ln.iterator();
    while(it.hasNext()){
      LexiconNode ch = it.next();
      listHelper(ch, word);
    }
  }
  //resets the iterator the start position
  public void reset(){ iter.reset();}
  //Post: returns true if there are more elements to be returned
  public boolean hasNext(){ return iter.hasNext();}
  //returns the current element in the lsit
  public String get(){ return iter.get();}
  //Returns the next element in the list
  public String next(){ return iter.next();}
}
