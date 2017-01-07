package xm.ik.segment;

import xm.ik.collection.Lexeme;
import xm.ik.dic.Dictionary;
import xm.ik.dic.Hit;
import xm.ik.util.CharacterUtil;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Numerals and Quantifiers
 *
 * @author xuming
 */
public class QuantifierSegmenter implements ISegmenter {
    private static final String SEGMENTER_NAME = "QUANTIFIER_SEGMENTER";
    private static String CHN_NUM = "一二两三四五六七八九十零壹贰叁肆伍陆柒捌玖拾百千万亿拾佰仟萬億兆卅廿";
    private static Set<Character> chnNumChars = new HashSet<>();

    static {
        for (char c : CHN_NUM.toCharArray()) chnNumChars.add(c);
    }

    private int start;
    private int end;
    private List<Hit> hits;

    public QuantifierSegmenter() {
        this.start = -1;
        this.end = -1;
        this.hits = new LinkedList<>();
    }

    @Override
    public void analyze(AnalyzerContext context) {
        processChnNumerals(context);
        processChnQuantifiers(context);
        // buffer lock
        if (start == -1 && end == -1 && hits.isEmpty())
            context.unlockBuffer(SEGMENTER_NAME);
        else
            context.lockBuffer(SEGMENTER_NAME);
    }

    @Override
    public void reset() {
        start = -1;
        end = -1;
        hits.clear();
    }

    private void processChnNumerals(AnalyzerContext context) {
        if (start == -1 && end == -1) {
            if (CharacterUtil.CHAR_CHINESE == context.getCurrentCharType()
                    && chnNumChars.contains(context.getCurrentChar())) {
                start = context.getCursor();
                end = context.getCursor();
            }
        } else {
            if (CharacterUtil.CHAR_CHINESE == context.getCurrentCharType()
                    && chnNumChars.contains(context.getCurrentChar()))
                end = context.getCursor();
            else {
                outputNumLexeme(context);
                // reset start and end
                start = -1;
                end = -1;
            }
        }
        // buffer consumed
        if (context.isBufferConsumed()) {
            if (start != -1 && end != -1) {
                outputNumLexeme(context);
                start = -1;
                end = -1;
            }
        }
    }

    private void outputNumLexeme(AnalyzerContext context) {
        if (start > -1 && end > -1) {
            Lexeme newLexeme = new Lexeme(context.getBuffOffset(), start, end - start + 1,
                    Lexeme.TYPE_CNUM);
            newLexeme.setLexemeText(String.valueOf(context.getSegmentBuff(),
                    newLexeme.getBegin(), newLexeme.getLength()));
            context.addLexeme(newLexeme);
        }
    }

    private void processChnQuantifiers(AnalyzerContext context) {
        if (!hasChnQuantifiers(context)) return;
        if (CharacterUtil.CHAR_CHINESE == context.getCurrentCharType()) {
            if (!hits.isEmpty()) {
                // hits priority
                Hit[] hitArray = hits.toArray(new Hit[hits.size()]);
                for (Hit hit : hitArray) {
                    hit = Dictionary.getSingleton().matchWithHit(context.getSegmentBuff(),
                            context.getCursor(), hit);
                    if (hit.isMatch()) {
                        // output matched word
                        Lexeme newLexeme = new Lexeme(context.getBuffOffset(), hit.getBegin(),
                                context.getCursor() - hit.getBegin() + 1, Lexeme.TYPE_COUNT);
                        newLexeme.setLexemeText(String.valueOf(context.getSegmentBuff(),
                                newLexeme.getBegin(), newLexeme.getLength()));
                        context.addLexeme(newLexeme);
                        if (!hit.isPrefix())
                            hits.remove(hit);
                    } else if (hit.isUnmatch())
                        hits.remove(hit);
                }
            }
            // single word
            Hit singleCharHit = Dictionary.getSingleton().matchInQuantifierDictionary(
                    context.getSegmentBuff(), context.getCursor(), 1);
            if (singleCharHit.isMatch()) {
                Lexeme newLexeme = new Lexeme(context.getBuffOffset(), context.getCursor(), 1,
                        Lexeme.TYPE_COUNT);
                newLexeme.setLexemeText(String.valueOf(context.getSegmentBuff(),
                        newLexeme.getBegin(), newLexeme.getLength()));
                context.addLexeme(newLexeme);
                if (singleCharHit.isPrefix()) {
                    hits.add(singleCharHit);
                }
            } else if (singleCharHit.isPrefix())
                hits.add(singleCharHit);

        } else
            hits.clear();

        if (context.isBufferConsumed())
            hits.clear();
    }

    private boolean hasChnQuantifiers(AnalyzerContext context) {
        if ((start != -1 && end != -1) || !hits.isEmpty()) return true;
        if (!context.getOriginalLexemes().isEmpty()) {
            Lexeme lexeme = context.getOriginalLexemes().peekLast();
            if (Lexeme.TYPE_CNUM == lexeme.getLexemeType()
                    || Lexeme.TYPE_ARABIC == lexeme.getLexemeType()) {
                if (lexeme.getBegin() + lexeme.getLength() == context.getCursor())
                    return true;
            }
        }
        return false;
    }
}
