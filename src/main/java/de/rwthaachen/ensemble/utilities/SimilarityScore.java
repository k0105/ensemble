package de.rwthaachen.ensemble.utilities;

import uk.ac.shef.wit.simmetrics.similaritymetrics.*;
import uk.ac.shef.wit.simmetrics.similaritymetrics.DiceSimilarity;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;

/**
 * Created by fp on 5/25/16.
 * Levenshtein, Jaro, Jaro-Winkler, Sorensen-Dice, cosine similarity, Jaccard similarity, Matching similarity, overlap
 * n gram, q gram, Damerau-Levenshtein, optimal string alignment, longest common subsequence, metric longest common sub-
 * sequence
 *
 * Idea: Use similarity scores in classifier to train whether answers should be merged or nor
 */
public class SimilarityScore {

    public float[] similarities(String s1, String s2) {
        float[] result = new float[15];
        DiceSimilarity ds = new DiceSimilarity();
        result[0] = ds.getSimilarity(s1, s2);
        CosineSimilarity cs = new CosineSimilarity();
        result[1] = ds.getSimilarity(s1, s2);
        JaccardSimilarity js = new JaccardSimilarity();
        result[2] = js.getSimilarity(s1, s2);
        Levenshtein ls = new Levenshtein();
        result[3] = ls.getSimilarity(s1, s2);
        ChapmanOrderedNameCompoundSimilarity concs = new ChapmanOrderedNameCompoundSimilarity();
        result[4] = concs.getSimilarity(s1, s2);
        BlockDistance bd = new BlockDistance();
        result[5] = bd.getSimilarity(s1, s2);
        Jaro j = new Jaro();
        result[6] = j.getSimilarity(s1, s2);
        JaroWinkler jw = new JaroWinkler();
        result[7] = jw.getSimilarity(s1, s2);
        MongeElkan me = new MongeElkan();
        result[8] = me.getSimilarity(s1, s2);
        NeedlemanWunch nw = new NeedlemanWunch();
        result[9] = nw.getSimilarity(s1, s2);
        SmithWaterman sw = new SmithWaterman();
        result[10] = sw.getSimilarity(s1, s2);
        QGramsDistance qgd = new QGramsDistance();
        result[11] = qgd.getSimilarity(s1, s2);
        Soundex s = new Soundex();
        result[12] = s.getSimilarity(s1, s2);
        EuclideanDistance ed = new EuclideanDistance();
        result[13] = ed.getSimilarity(s1, s2);
        TagLink tl = new TagLink();
        result[14] = tl.getSimilarity(s1, s2);
        return result;
    }

    public String similaritiesVerbose(String s1, String s2) {
        DiceSimilarity ds = new DiceSimilarity();
        float resultDS = ds.getSimilarity(s1, s2);
        CosineSimilarity cs = new CosineSimilarity();
        float resultCS = ds.getSimilarity(s1, s2);
        JaccardSimilarity js = new JaccardSimilarity();
        float resultJS = js.getSimilarity(s1, s2);
        Levenshtein ls = new Levenshtein();
        float resultLS = ls.getSimilarity(s1, s2);
        ChapmanOrderedNameCompoundSimilarity concs = new ChapmanOrderedNameCompoundSimilarity();
        float resultCONCS = concs.getSimilarity(s1, s2);
        BlockDistance bd = new BlockDistance();
        float resultBD = bd.getSimilarity(s1, s2);
        Jaro j = new Jaro();
        float resultJ = j.getSimilarity(s1, s2);
        JaroWinkler jw = new JaroWinkler();
        float resultJW = jw.getSimilarity(s1, s2);
        MongeElkan me = new MongeElkan();
        float resultME = me.getSimilarity(s1, s2);
        NeedlemanWunch nw = new NeedlemanWunch();
        float resultNW = nw.getSimilarity(s1, s2);
        SmithWaterman sw = new SmithWaterman();
        float resultSW = sw.getSimilarity(s1, s2);
        QGramsDistance qgd = new QGramsDistance();
        float resultQGD = qgd.getSimilarity(s1, s2);
        Soundex s = new Soundex();
        float resultS = s.getSimilarity(s1, s2);
        EuclideanDistance ed = new EuclideanDistance();
        float resultED = ed.getSimilarity(s1, s2);
        TagLink tl = new TagLink();
        float resultTL = tl.getSimilarity(s1, s2);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Comparison of strings: \"");
        stringBuilder.append(s1);
        stringBuilder.append("\" and \"");
        stringBuilder.append(s2);
        stringBuilder.append("\"\n\nDice Similarity: ");
        stringBuilder.append(resultDS);
        stringBuilder.append("\nCosine Similarity: ");
        stringBuilder.append(resultCS);
        stringBuilder.append("\nJaccard Similarity: ");
        stringBuilder.append(resultJS);
        stringBuilder.append("\nLevenshtein Distance: ");
        stringBuilder.append(resultLS);
        stringBuilder.append("\nChapman Ordered Name Compound Similarity: ");
        stringBuilder.append(resultCONCS);
        stringBuilder.append("\nBlock Distance: ");
        stringBuilder.append(resultBD);
        stringBuilder.append("\nJaro: ");
        stringBuilder.append(resultJ);
        stringBuilder.append("\nJaro-Winkler: ");
        stringBuilder.append(resultJW);
        stringBuilder.append("\nMonge-Elkan: ");
        stringBuilder.append(resultME);
        stringBuilder.append("\nNeedleman-Wunch: ");
        stringBuilder.append(resultNW);
        stringBuilder.append("\nSmith-Waterman: ");
        stringBuilder.append(resultSW);
        stringBuilder.append("\nQGrams Distance: ");
        stringBuilder.append(resultQGD);
        stringBuilder.append("\nSoundex: ");
        stringBuilder.append(resultS);
        stringBuilder.append("\nEuclidean Distance: ");
        stringBuilder.append(resultED);
        stringBuilder.append("\nTag Link: ");
        stringBuilder.append(resultTL);
        return stringBuilder.toString();
    }
}
