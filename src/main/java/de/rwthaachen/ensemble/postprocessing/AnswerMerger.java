package de.rwthaachen.ensemble.postprocessing;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Stream;

/**
 * Created by fp on 5/25/16.
 */
public class AnswerMerger {

    // Take similarity metrics as features and train classifier to decide whether to merge or not
    // Should also be able to rename answers, so we can execute majority vot or count for merged answers how many were merged

    private Strategy strategy;
    public Strategy getStrategy() {
        return strategy;
    }
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Set<char[]> mergeAnswers(String s1, String s2) {
        // Return longest common substring, cmp. https://en.wikipedia.org/wiki/Longest_common_substring_problem
        // Generalized suffix tree => Theta(n+m); dynamic programming => Theta(nm)

        int[][] look = new int[s1.length()][s2.length()];
        char[] a1 = s1.toCharArray();
        char[] a2 = s2.toCharArray();
        Set<char[]> longestCommonSubstrings = new HashSet<>();

        int lengthOfLongestSubstringFound = 0;
        for(int i = 0; i<s1.length(); i++) {
            for (int j=0; j<s2.length(); j++) {
                if (a1[i] == a2[j]) {
                    if (i==0 || j==0) {
                        look[i][j] = 1;
                    } else {
                        look[i][j] = look[i-1][j-1] + 1;
                    }

                    if (look[i][j] > lengthOfLongestSubstringFound) {
                        lengthOfLongestSubstringFound = look[i][j];
                        longestCommonSubstrings = new HashSet<>();
                        longestCommonSubstrings.add(Arrays.copyOfRange(a1, i-lengthOfLongestSubstringFound+1, i+1));
                    } else if (look[i][j] == lengthOfLongestSubstringFound) {
                        longestCommonSubstrings.add(Arrays.copyOfRange(a1, i-lengthOfLongestSubstringFound+1, i+1));
                    }
                } else {
                    look[i][j] = 0;
                }
            }
        }

        return longestCommonSubstrings;
    }
}
