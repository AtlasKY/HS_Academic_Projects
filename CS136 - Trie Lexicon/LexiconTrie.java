import structure5.*;
import java.util.Iterator;
import java.util.Scanner;

public class LexiconTrie implements Lexicon {

    //Instance of the root of the trie
    private LexiconNode root;
    //store the number of words
    private int numWords = 0;
    //global boolean for in-method usage
    private boolean flag = false;

    /*
    * The constructor initializes a newly allocated lexicon
    * to represent an empty word list.
    */
    public LexiconTrie() {
      root = new LexiconNode(' ', false);
    }

    /*
    * This member function adds the specified word to this lexicon.
    * It returns true if the word was added (i.e. previously did
    * not appear in this lexicon) and false otherwise. The
    * word is expected to contain only lowercase letters.
    */
    public boolean addWord(String word) {
      return addHelper(word, root);
    }

    private boolean addHelper(String word, LexiconNode ln){
      char temp = word.charAt(0);
      if(!ln.contains(temp)){
        return blankAdd(word, ln);
      }
      else if(word.length() == 1 && ln.contains(temp)){
        if(ln.getChild(temp).isWord()) { return false;}
        else{ numWords++; return ln.getChild(temp).isWord(true);}
      }
      else{
        return addHelper(word.substring(1), ln.getChild(temp));
      }
    }
    private boolean blankAdd(String word, LexiconNode ln){
      char temp = word.charAt(0);
      if(word.length() == 1){
        ln.addChild(new LexiconNode(temp, true));
        numWords++;
        return true;
      }
      else{
        ln.addChild(new LexiconNode(temp, false));
        return blankAdd(word.substring(1), ln.getChild(temp));
      }
    }

     /*
    * This member function opens the specified file, expecting a
    * text file containing one word per line, and adds each word
    * to this lexicon. The value returned is the count of
    * new words that were added. If the file doesn't exist or was unable
    * to be opened, the function returns 0.
    */
    public int addWordsFromFile(String filename) {
      Scanner sc = new Scanner(new FileStream(filename));
      if(sc.ioException() != null){ return -1;}
      else{
        int count = 0;
        while(sc.hasNext()){
          addWord(sc.next());
          count++;
        }
        return count;
      }
    }

    /*
    * This member function attempts to remove a specified word from
    * this lexicon. If the word appears in the lexicon, it is removed and true is returned.
    * If the word was not contained in the lexicon, the lexicon is
    * unchanged and false is returned.
    */
    public boolean removeWord(String word) {
      flag = false;
      return removeHelper(word, root, word.charAt(0));
    }

    private boolean removeHelper(String word, LexiconNode ln, char c){
      char temp = ' ';
      if(word.length() != 0){ temp = word.charAt(0);}
      if(word.length() != 0 && !ln.contains(temp)){ return false; }
      else if(word.length() == 0 && ln.getLetter() == c){
        if(ln.isWord()){
          numWords--;
          if(ln.hasChild()){ return !ln.isWord(false);}
          else{
            System.out.println("Flag it!");
            flag = true;
            return !ln.isWord(false);
          }
        }
      }
      else{
        boolean r = removeHelper(word.substring(1), ln.getChild(temp), word.charAt(0));
        if(flag && !ln.getChild(temp).isWord() && !ln.getChild(temp).hasChild()){
          ln.removeChild(temp);
        }
        return r;
      }
      return false;
    }


    /*
    * This member function returns the number of words contained
    * in this lexicon.
    */
    public int numWords() { return numWords;}

    /*
    * This member function returns true if the specified
    * word exists in this lexicon, false otherwise.
    */
    public boolean containsWord(String word){
      return containsHelper(word, root);
    }

    private boolean containsHelper(String word, LexiconNode ln){
      System.out.println("Contains Test: " + word);
      if(word.length() == 0){
        if(ln.isWord()){ return true;}
        else{ return false;}
      }
      else if(!ln.contains(word.charAt(0))){
        return false;
      }
      else{
        return containsHelper(word.substring(1), ln.getChild(word.charAt(0)));
      }
    }

    /*
    * This member function returns true if any words in
    * the lexicon begin with the specified prefix, false
    * otherwise. A word is defined to be a prefix of itself
    * and the empty string is a prefix of everything.
    */
    public boolean containsPrefix(String prefix){
      return prefixHelper(prefix, root);
    }
    private boolean prefixHelper(String prefix, LexiconNode ln){
      if(prefix.length() == 0){ return true;}
      else if(!ln.contains(prefix.charAt(0))){ return false;}
      else{ return containsHelper(prefix.substring(1), ln.getChild(prefix.charAt(0)));}
    }

    /*
    * This member function returns an iterator over all
    * words contained in the lexicon. Accessing the
    * words from the iterator will retrieve them in
    * alpahbetical order.
    */
    public Iterator<String> iterator() {
      return new LexiconIterator(root);
    }

    /*
    * (OPTIONAL:) This member function returns a pointer to a set of strings,
    * where each entry is a suggested correction for the target.
    * All words in the lexicon with a distance to the target that
    * is less than or equal to the parameter distance should be in
    * the returned set.
    */
    public Set<String> suggestCorrections(String target, int maxDistance) {
      SetVector<String> set = new SetVector<String>();
      set.addAll(suggestHelper(target, maxDistance, target.length()));
      return set;
    }
    private Set<String> suggestHelper(String target, int maxDistance, int wLimit){
      SetVector<String> set = new SetVector<String>();
      if(maxDistance != 0){
        for (int i = 0; i < target.length(); i++) {
          String reg = target.substring(0,i) + "?" + target.substring(i+1);
          set.addAll(inRegex(reg, wLimit));
          set.addAll(suggestHelper(reg, maxDistance-1, wLimit));
        }
      }
      return set;
    }

    /*
    * (OPTIONAL:) This member function returns a pointer to a set of strings,
    * where each entry is match for the regular expression pattern.
    * All words in the lexicon that match the pattern should be in
    * the returned set.
    */
    public Set<String> matchRegex(String pattern){
      return inRegex(pattern, 0);
    }

    //regex relayer with a word limit parameter for use with suggest method
    private Set<String> inRegex(String pattern, int wLimit){
      SetVector<String> set = new SetVector<String>();
      set.addAll(regexHelper(pattern, root, "", wLimit));
      return set;
    }

    //Post: returns the set of strings that matches the regex and is length wLimit if wLimit != 0
    private Set<String> regexHelper(String pat, LexiconNode ln, String st, int wLimit){
      SetVector<String> set = new SetVector<String>();

      if(pat.length() != 0 && ln.hasChild()){
        Iterator<LexiconNode> it = ln.iterator();
        while(it.hasNext()){
          LexiconNode cn = it.next();
          if(pat.charAt(0)== cn.getLetter()){
            set.addAll(regexHelper(pat.substring(1), cn, st + (cn.getLetter()), wLimit));
          }
          if(pat.charAt(0) == '?'){
            if(pat.matches(".?") && cn.isWord() && (wLimit == 0 || st.trim().length() == wLimit)){ set.add(st);}
            set.addAll(regexHelper(pat.substring(1), cn, st + (cn.getLetter()), wLimit));
          }
          if(pat.charAt(0) == '*'){
            set.addAll(regexHelper("?" + pat, cn, st + (cn.getLetter()), wLimit));
            set.addAll(regexHelper(pat.substring(1), cn, st, wLimit));
          }
        }
      } else if(ln.isWord() && (wLimit == 0 || st.trim().length() == wLimit)){
        set.add(st);
      }
      return set;
    }
}
