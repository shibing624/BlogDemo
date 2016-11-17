package xm.ngram;

import java.util.ArrayList;

/**
 * Created by xuming
 */
public class NgramMaker {

    public static void main(String[] args){
        String s = "nihao你好，你是是？你想怎么样";
        String ss = "是是？你想怎么样";
//        GenerateNGrams(s,s.length());
        ComputeNGramSimilarity(s,ss,2);
    }

    public static ArrayList GenerateNGrams(String text, int gramLength) {
        if (text == null || text.length() == 0)
            return null;

        ArrayList grams = new ArrayList();
        int length = text.length();
        if (length < gramLength) {
            String gram;
            for (int i = 1; i <= length; i++) {
                gram = text.substring(0, (i) - (0));
                if (grams.indexOf(gram) == -1)
                    grams.add(gram);
            }

            gram = text.substring(length - 1, (length) - (length - 1));
            if (grams.indexOf(gram) == -1)
                grams.add(gram);

        } else {
            for (int i = 1; i <= gramLength - 1; i++) {
                String gram = text.substring(0, (i) - (0));
                if (grams.indexOf(gram) == -1)
                    grams.add(gram);

            }

            for (int i = 0; i < (length - gramLength) + 1; i++) {
                String gram = text.substring(i, (i + gramLength) - (i));
                if (grams.indexOf(gram) == -1)
                    grams.add(gram);
            }

            for (int i = (length - gramLength) + 1; i < length; i++) {
                String gram = text.substring(i, (length) - (i));
                if (grams.indexOf(gram) == -1)
                    grams.add(gram);
            }
        }
        return grams;
    }

    public static float ComputeNGramSimilarity(String text1, String text2, int gramlength) {
        if ((Object) text1 == null || (Object) text2 == null || text1.length() == 0 || text2.length() == 0)
            return 0.0F;
        String[] grams1 = (String[]) GenerateNGrams(text1, gramlength).toArray();
        String[] grams2 = (String[]) GenerateNGrams(text2, gramlength).toArray();
        int count = 0;
        for (int i = 0; i < grams1.length; i++) {
            for (int j = 0; j < grams2.length; j++) {
                if (!grams1[i].equals(grams2[j]))
                    continue;
                count++;
                break;
            }
        }

        float sim = (2.0F * (float) count) / (float) (grams1.length + grams2.length);
        return sim;
    }

    public static float GetBigramSimilarity(String text1, String text2) {
        return ComputeNGramSimilarity(text1, text2, 2);
    }

    public static float GetTrigramSimilarity(String text1, String text2) {
        return ComputeNGramSimilarity(text1, text2, 3);
    }

    public static float GetQuadGramSimilarity(String text1, String text2) {
        return ComputeNGramSimilarity(text1, text2, 4);
    }


}
