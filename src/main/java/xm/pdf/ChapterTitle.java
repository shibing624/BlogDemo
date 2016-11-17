package xm.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xuming on 2016/7/19.
 */
public class ChapterTitle {
    public static final String DEST = "results/objects/chapter_title.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChapterTitle().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        Chunk chunk = new Chunk("This is the title", chapterFont);
        Font small = new Font(Font.FontFamily.HELVETICA, 6);
        Chunk rd = new Chunk("rd", small);
        rd.setTextRise(7);
        Chunk th = new Chunk("th", small);
        th.setTextRise(7);
        Chapter chapter = new Chapter(new Paragraph(chunk), 1);
        chapter.setNumberDepth(0);
        chapter.add(new Paragraph("This is the paragraph。20160719", paragraphFont));
        document.add(chapter);
        Chunk c = new Chunk("White text on red background", paragraphFont);
        c.setBackground(BaseColor.RED);
        Paragraph p = new Paragraph(c);
        p.add(rd);p.add(" of June");
        p.add(th);
        document.add(p);
        document.close();
    }

}
