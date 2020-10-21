# Lexicon Thought Questions

1. For each node in the trie, you need to store pointers to its children nodes. What data structure did you use to store the pointers to children nodes? Justify the choice you made.
   * Data structure choice: OrderedVector
   * Justification: It offers a structure in which the elements are ordered. This decrease the amount of code we need to write for operations such as adding and also walking through the structure to get the children.
2. Suppose we use an `OrderedVector` instead of a trie for storing our lexicon. Discuss how the process of searching for suggested spelling corrections would differ from our trie-based implementation. Which is more efficient? Why?
   * Searching in a trie: in a trie we only explore the paths that fits the expression. We can strike out the paths that does not fit. Impossibly rare will we need to visit every node in the trie looking for a suggestion.
   * Searching in an `OrderedVector`: we need to iterate over every element and compare their distance with the given target, in worst case we need to traverse the entirety of the vector.
   * Which is more efficient? Trie structure is more efficient. Looking for a suggestion that starts with the letter "h" in the trie structure we can eliminate all the other words that starts with another letter in constant time of at most 26 whereas in the ordered vector case we would have to travers the vector until we find the words that starts with the letter h.
