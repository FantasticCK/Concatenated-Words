package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        String[] words = {"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"};
        new Solution().findAllConcatenatedWordsInADict(words);
    }
}

class Solution2 {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Set<String> ws = new HashSet<>(Arrays.asList(words));
        Map<String, Boolean> memo = new HashMap<>();
        List<String> res = new ArrayList<>();

        for (String word : words) {
            if (isConcatenatedWord(word,  memo, ws)) {
                res.add(word);
            };
        }

        return res;
    }

    private boolean isConcatenatedWord(String w,  Map<String, Boolean> memo, Set<String> ws) {
        if (w.length() == 0)
            return false;

        if (memo.containsKey(w))
            return memo.get(w);

        int n = w.length();
        for (int i = 1; i < n; i++) {
            String prefix = w.substring(0, i), suffix = w.substring(i);
            if (ws.contains(prefix) ) {
                if (ws.contains(suffix) || isConcatenatedWord(suffix,  memo, ws)) {
                    memo.put(w, true);
                    break;
                }
            }
        }

        memo.putIfAbsent(w, false);
        return memo.get(w);
    }
}

class Solution {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> res = new LinkedList<>();
        Trie root = new Trie();
        for(String s: words){//build tree
            if(s.length()==0) continue; //skip "" !
            Trie p = root;
            for(char c: s.toCharArray()){
                if(p.next[c-'a']==null) p.next[c-'a'] = new Trie();
                p = p.next[c-'a'];
            }
            p.word = s;
        }
        for(String s: words)
            if(dfs(s, root, 0, root)) res.add(s); //check each word using the tree
        return res;
    }
    private boolean dfs(String s, Trie t, int p, Trie root){
        //return true, if we reach a word at the end & the original word s is a concatenated word
        if(p==s.length()) return t.word!=null && !t.word.equals(s);
        if(t.next[s.charAt(p)-'a']==null) return false;
        //reached a word, try to start from root again
        if(t.next[s.charAt(p)-'a'].word!=null && dfs(s, root, p+1, root)) return true;
        //keep going
        return dfs(s, t.next[s.charAt(p)-'a'] ,p+1, root);
    }
    class Trie{
        Trie[] next = new Trie[26];
        String word = null;
    }
}