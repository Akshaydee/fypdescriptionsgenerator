package common.uitils;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.BorderRadius;
import com.itextpdf.layout.property.TextAlignment;
import common.constant.Constant;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The pdf utils<br>
 *
 * @author Zihao Long
 * @version 1.0, 2021-09-27 05:53
 * @since excelToPdf 0.0.1
 */
public class PdfUtils {

    /**
     * The content fontsize
     */
    public static int CONTENT_FONTSIZE = 12;

    /**
     * Generate base font<br>
     *
     * @param []
     * @return com.itextpdf.kernel.font.PdfFont
     * @author Zihao Long
     */
    public static PdfFont genBaseFont() {
        try {
            return PdfFontFactory.createFont(StandardFonts.HELVETICA);
        } catch (IOException e) {
            Constant.logger.error(e.toString());
            return null;
        }
    }

    /**
     * Generate windings font<br>
     *
     * @param []
     * @return com.itextpdf.kernel.font.PdfFont
     * @author Zihao Long
     */
    public static PdfFont genWingdingsFont () {
        try {
            InputStream fontInputSteam = PdfUtils.class.getResourceAsStream("/font/Wingdings.ttf");
            FontProgram fontProgram = FontProgramFactory.createFont(PdfUtils.toByteArray(fontInputSteam));
            return PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H, false);
        } catch (IOException e) {
            Constant.logger.error(e.toString());
            return null;
        }
    }

    /**
     * InputStream to byte array<br>
     *
     * @param [input]
     * @return byte[]
     * @author Zihao Long
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    /**
     * add header<br>
     *
     * @param [doc, text]
     * @return void
     * @author Zihao Long
     */
    public static void addHeader(Document doc, String text) {
        Paragraph paragraph = new Paragraph();
        paragraph.setMarginBottom(20f);
        paragraph.add(text).setFont(PdfUtils.genBaseFont()).setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER);
        doc.add(paragraph);
    }

    /**
     * add title<br>
     *
     * @param [doc, text]
     * @return void
     * @author Zihao Long
     */
    public static void addTitle(Document doc, String text) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(text)
                .setFont(PdfUtils.genBaseFont())
                .setBold()
                .setFontSize(20)
                .setMarginBottom(20)
                .setTextAlignment(TextAlignment.CENTER);
        doc.add(paragraph);
    }

    /**
     * gen a bold text cell with default font size<br>
     *
     * @param [text]
     * @return com.itextpdf.layout.element.Cell
     * @author Zihao Long
     */
    public static Cell genBoldCell(String text) {
        Paragraph paragraph = new Paragraph(text);
        paragraph.setBold()
                .setFontSize(Constant.DEFAULT_TEXT_FONT_SIZE)
                .setFont(PdfUtils.genBaseFont());
        Cell cell = new Cell();
        cell.add(paragraph);
        cell.setBorderLeft(Border.NO_BORDER);
        cell.setBorderRight(Border.NO_BORDER);
        return cell;
    }

    /**
     * gen header cell<br>
     *
     * @param [text]
     * @return com.itextpdf.layout.element.Cell
     * @author Zihao Long
     */
    public static Cell genHeaderCell(String text) {
        Cell cell = PdfUtils.genBoldCell(text);
        cell.setHeight(20f);
        return cell;
    }

    /**
     * gen a normal text cell with the font size 10<br>
     *
     * @param [text]
     * @return com.itextpdf.layout.element.Cell
     * @author Zihao Long
     */
    public static Cell genNormalCell(String text) {
        Paragraph paragraph = new Paragraph(text);
        paragraph.setFontSize(10)
                .setFont(PdfUtils.genBaseFont());
        Cell cell = new Cell();
        cell.add(paragraph);
        cell.setBorderLeft(Border.NO_BORDER);
        cell.setBorderRight(Border.NO_BORDER);
        return cell;
    }

    /**
     * generate sub title paragraph<br>
     *
     * @param [text]
     * @return com.itextpdf.layout.element.Paragraph
     * @author Zihao Long
     */
    public static Paragraph genSubTitle(String text) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(text)
                .setFont(PdfUtils.genBaseFont())
                .setBold()
                .setFontSize(12);
        return paragraph;
    }

    /**
     * Create paragraph with text and font size<br>
     *
     * @param [text, fontSize]
     * @return com.itextpdf.layout.element.Paragraph
     * @author Zihao Long
     */
    public static Paragraph genParagraph(String text, int fontSize) {
        if (text == null) {
            text = "";
        }
        Paragraph paragraph = new Paragraph();
        paragraph.add(text)
                .setFont(PdfUtils.genBaseFont())
                .setFontSize(fontSize);
        return paragraph;
    }

    /**
     * Generate a paragraph with border<br>
     *
     * @param [text, border]
     * @return com.itextpdf.layout.element.Paragraph
     * @author Zihao Long
     */
    public static Paragraph genParagraphWithBorder(String text, Border border) {
        if (StringUtils.isEmpty(text)) {
            text = "\n";
        }
        Paragraph paragraph = genParagraph(text, CONTENT_FONTSIZE);
        paragraph.setBorder(border);
        paragraph.setPaddingLeft(5);
        paragraph.setPaddingRight(5);
        paragraph.setBorderRadius(new BorderRadius(3f));
        return paragraph;
    }

    /**
     * Set grey color to cell<br>
     *
     * @param [cell]
     * @return void
     * @author Zihao Long
     */
    public static void setGreyColorToCell(Cell cell) {
        DeviceRgb processColor = new DeviceRgb(235,235,235);
        cell.setBackgroundColor(processColor);
    }

    /**
     * hide horizontal border between the top cell and bottom cell<br>
     *
     * @param [topCell, bottomCell]
     * @return void
     * @author Zihao Long
     */
    public static void hideHzBorderBtCell(Cell topCell, Cell bottomCell) {
        topCell.setBorderBottom(Border.NO_BORDER);
        bottomCell.setBorderTop(Border.NO_BORDER);
    }

    /**
     * add sub title cell to table<br>
     *
     * @param [table, paragraph, setGreyColor]
     * @return com.itextpdf.layout.element.Cell
     * @author Zihao Long
     */
    public static Cell addSubTitleCell(Table table, IBlockElement paragraph, boolean setGreyColor) {
        Cell cell = new Cell();
        cell.add(paragraph);
        cell.setPadding(-1);
        cell.setPaddingLeft(5);
        cell.setPaddingRight(5);
        if (setGreyColor) {
            PdfUtils.setGreyColorToCell(cell);
        }
        table.addCell(cell);
        return cell;
    }

    /**
     * Set margin bottom to paragraph<br>
     *
     * @param [paragraph]
     * @return void
     * @author Zihao Long
     */
    public static void setMarginBottom(Paragraph paragraph) {
        paragraph.setMarginBottom(15f);
    }

    /**
     * Generate a checkbox<br>
     *
     * @param [isChecked]
     * @return com.itextpdf.layout.element.Paragraph
     * @author Zihao Long
     */
    public static Text genCheckbox(boolean isChecked) {
        // The default is checked
        char checkChar = '\u00FE';
        if (!isChecked) {
            checkChar = '\u00A8';
        }
        Text text = new Text(String.valueOf(checkChar));
        text.setFont(PdfUtils.genWingdingsFont());
        text.setFontSize(14);
        return text;
    }

    /**
     * Generate a checkbox<br>
     *
     * @param [isChecked]
     * @return com.itextpdf.layout.element.Paragraph
     * @author Zihao Long
     */
    public static Text genRadioInput(boolean isChecked) {
        // The default is checked
        char checkChar = '\u00A3';
        if (!isChecked) {
            checkChar = '\u00A1';
        }
        Text text = new Text(String.valueOf(checkChar));
        text.setFont(PdfUtils.genWingdingsFont());
        text.setFontSize(14);
        return text;
    }
}
