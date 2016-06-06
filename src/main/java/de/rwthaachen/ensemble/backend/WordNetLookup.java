package de.rwthaachen.ensemble.backend;

import edu.smu.tspell.wordnet.*;

/**
 * Created by fp on 6/1/16.
 */
public class WordNetLookup {

    public String lookup(String word) {
        NounSynset nounSynset;
        NounSynset[] hyponyms;

        WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset[] synsets = database.getSynsets(word, SynsetType.NOUN);
        for (int i = 0; i < synsets.length; i++) {
            nounSynset = (NounSynset) (synsets[i]);
            hyponyms = nounSynset.getHyponyms();
            System.err.println(nounSynset.getWordForms()[0] +
                    ": " + nounSynset.getDefinition() + ") has " + hyponyms.length + " hyponyms");

            System.out.println("ME:");
            for (String syn : nounSynset.getWordForms()) {
                System.out.println(syn);
            }
            System.out.println("Hyponyms:");
            for (NounSynset ns : hyponyms) {
                System.out.println(ns.getWordForms()[0]);
            }
        }
        return word;
    }
}
