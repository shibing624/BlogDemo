package xm.ik.segment;

import xm.ik.collection.Lexeme;
import xm.ik.dic.Dictionary;
import xm.ik.dic.Hit;
import xm.ik.util.CharacterUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * chinese japan korea segmenter
 *
 * @author xuming
 */
public class CJKSegmenter implements ISegmenter {
    //    Numerals and quantifiers
    public static final String SEGMENTER_NAME = "CJK_SEGMENTER";
    private List<Hit> hits;

    public CJKSegmenter() {
        this.hits = new LinkedList<>();
    }

    @Override
    public void analyze(AnalyzerContext context) {
        if (CharacterUtil.CHAR_USELESS != context.getCurrentCharType()) {
            if (!hits.isEmpty()) {
                // deal with lexeme array in hits
                Hit[] array = hits.toArray(new Hit[hits.size()]);
                for (Hit hit : array) {
                    hit = Dictionary.getSingleton().matchWithHit(context.getSegmentBuff(),
                            context.getCursor(), hit);
                    if (hit.isMatch()) {
                        // output lexeme
                        Lexeme newLexeme = new Lexeme(context.getBuffOffset(), hit.getBegin(),
                                context.getCursor() - hit.getBegin() + 1, Lexeme.TYPE_CNWORD);
                        context.addLexeme(newLexeme);
                        // not same prefix, remove hit
                        if (!hit.isPrefix()) hits.remove(hit);
                    } else if (hit.isUnmatch()) hits.remove(hit);
                }
            }
            // match single word(char)
            Hit singleCharHit = Dictionary.getSingleton().matchInMainDictionary(
                    context.getSegmentBuff(), context.getCursor(), 1);
            if (singleCharHit.isMatch()) {
                // head word is match
                Lexeme newLexeme = new Lexeme(context.getBuffOffset(), context.getCursor(), 1,
                        Lexeme.TYPE_CNWORD);
                context.addLexeme(newLexeme);
                if (singleCharHit.isPrefix()) hits.add(singleCharHit);
            } else if (singleCharHit.isPrefix()) {
                // prefix match
                hits.add(singleCharHit);
            } else {
                // clear CHAR_USELESS chars
                hits.clear();
            }
            if (context.isBufferConsumed()) hits.clear();
            if (hits.size() == 0)
                context.unlockBuffer(SEGMENTER_NAME);
            else
                context.lockBuffer(SEGMENTER_NAME);
        }
    }

    @Override
    public void reset() {
        hits.clear();
    }
}
