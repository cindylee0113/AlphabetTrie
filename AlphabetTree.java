import java.util.*;
import java.io.*;

public class AlphabetTree {

    private ANode u;

    public AlphabetTree () {
	u = new ANode (' ');
	ArrayList <String> dictionary = loadDictionary ();
	addAllWords (dictionary);
    }

    public ArrayList<String> loadDictionary() {
	String s = "zzz";
	ArrayList<String> dictionary = new ArrayList<String>();
     
	try {
	    FileReader f = new FileReader("words_full.txt");
	    BufferedReader b = new BufferedReader(f);
 
	    while( s != null ) {
		s = b.readLine();
		if ( s != null )
		    dictionary.add(s);
            }
        }
	catch (IOException e) {}
   
	return dictionary;
    }

    public void add (String s) {
	char [] word = s.toCharArray ();
	add (u, word, 0);
    }

    public void add (ANode curr, char [] w, int i) {
	if (i ==  w.length) {
	    return;
	}
	else {
	    char d = w[i];
	    ANode hey = new ANode (d);
	    
	    int index = 0;
	    while (index < 26) {
		if (curr.getChild (index) == null) {
		    curr.setChild (index, hey);
		    break;
		}
		else if (curr.getChild (index).getData ()==d) {
		    break;
		}
		else {
		    index++;
		}
	    }
	    i++;
	    add (curr.getChild (index), w, i);
	}
    }

    public void addAllWords (ArrayList <String> dictionary) {
	for (int i = 0; i < dictionary.size () ; i++ ) {
	    add (dictionary.get (i));
	}
    }

    public String randWord () {
	String s = "";
	return randWordHelper (u, s);
    }

    public boolean stopCont () {
	int rand = (int)( Math.random () * 10 );
	if (rand < 5) {
	    return true;
	    //This is continuing
	}
	else {
	    return false; //this is stopping
	}
    }

    public boolean isWord (String s) {
	ArrayList <String> dictionary = loadDictionary();
	boolean hi = false;
	for (int i = 0; i <dictionary.size () ; i++ ) {
	    if (dictionary.get(i).equals (s)) {
		hi = true;
	    }
	}
	return hi;
    }

    public String randWordHelper (ANode curr, String s) {
	ArrayList <String >dictionary = loadDictionary ();
	if (curr.getNumChildren () == 0) {
	    return s;
	}
	else{
	    int index = (int)(Math.random () * curr.getNumChildren());
	    s+= curr.getChild (index).getData();
	    for (int i = 0; i < dictionary.size(); i++ ) {
		if (dictionary.get(i).equals (s)){ 
		    if (stopCont () == true) {
			break;
		    }
		    else {
			return s;
		    }
		}
	    }
	    return randWordHelper (curr.getChild (index),s);
	}
    }

    public String longestWord () {
	String s = "";
	ArrayList <String> words = new ArrayList ();
	longestWordHelper (u, words, s);
	String longest = words.get(0);
	for (int i = 1; i < words.size ();i++) {
	    if (words.get(i).length () > longest.length ()) {
		longest = words.get(i);
	    }
	}
	/*
	  Test to see what is the actual longest word of dictionary
	ArrayList <String> dictionary = loadDictionary ();
	String word = dictionary.get (0);
	for (int j = 1; j < dictionary.size(); j++ ) {
	    if (dictionary.get (j).length () > word.length () ) {
		word = dictionary.get (j);
	    }
	}
	System.out.println (word);
	*/
	return longest.substring (1);
    }

    public boolean isLeaf (ANode curr) {
	if (curr.getNumChildren () == 0) {
	    return true;
	}
	return false;
    }

    public void longestWordHelper (ANode curr, ArrayList <String> words, String s) {
	if (isLeaf (curr)) {
	    s += curr.getData();
	    words.add(s);
	}
	else { 
	    s += curr.getData ();
	    for (int i = 0; i < curr.getNumChildren () ; i++ ) {
		ANode child = curr.getChild (i);
		longestWordHelper (child, words, s);
	    }
	}
    } 
	
    public ArrayList <String> comPre (String s) {
        ArrayList <String> words = new ArrayList ();
	String v = "";
	longestWordHelper (u, words, v);
	for (int i = 0; i < words.size (); i++ ) {
	    String longword = words.get(i).substring (1);
	    words.set (i, longword);
	}
	String meep = "";
	ArrayList <String> prefix = new ArrayList ();
	for (int j = 0; j < words.size (); j++ ) {
	    String wordy = words.get (j);
	    if (wordy.indexOf (s) == 0) {
		meep = wordy;
		for (int i = s.length (); i <= meep.length (); i++) {
		    if (isWord (meep.substring (0,i))) {
			if (inArray (prefix,meep.substring (0,i)) == false) {
			    prefix.add (meep.substring (0,i));
			}
		    }
		} 
	    }
	}
	return prefix;
    }
 
    public boolean inArray (ArrayList <String> words,String s ) {
	boolean hi = false;
	for (int i = 0; i < words.size (); i++ ) {
	    if (words.get (i).equals (s)) {
		hi =  true;
	    }
	}
	return hi;
    }

    public int comPreNum (String s) {
	return comPre (s).size ();
    }

    public ArrayList <String> comSuffix (String s) {
	ArrayList <String> words = new ArrayList ();
	String v = "";
	longestWordHelper (u, words, v);
	for (int i = 0; i < words.size (); i++ ) {
	    words.set (i, words.get(i).substring (1));
	}
	String meep = "";
	ArrayList <String> suffix = new ArrayList ();
	for (int j = 0; j < words.size (); j++) {
	    String wordy = words.get(j);
	    if ((wordy.indexOf (s) != -1) && (wordy.indexOf (s) == wordy.length()-s.length())) {
		meep = wordy;
		for (int k = 0; k < meep.length (); k++) {
		    if (isWord (meep.substring (k))) {
			if (inArray (suffix, meep.substring (k))==false) {
			    suffix.add (meep.substring (k));
			}
		    }
		}
	    }
	}
	return suffix;
    } 

    public int comSuffixNum (String s) {
	return comSuffix(s).size ();
    }

    public static void main (String [] args) {
	AlphabetTree AT = new AlphabetTree ();
	System.out.println ("Random Word: " + AT.randWord());
	System.out.println ("Longest Word: " + AT.longestWord());
	ArrayList <String> coo = AT.comPre ("constr");
	System.out.println ("Number of words with the prefix constr-: " + AT.comPreNum ("constr"));
	System.out.println ( "Prefix constr- List: ");
	for (int i = 0; i < coo.size(); i++ ) {
	    System.out.println ("" + i + ": " + coo.get(i));
	}
	System.out.println ("\n");
	ArrayList<String>moo = AT.comSuffix ("sier");
	System.out.println ("Number of words with the suffix -sier: " + AT.comSuffixNum ("sier"));
	System.out.println ("Suffix -sier List: ");
	for (int i = 0; i < moo.size (); i++){
	    System.out.println ("" + i+ ": " + moo.get(i));
	}
    }
}

		    