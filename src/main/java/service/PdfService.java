package service;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import common.constant.Constant;
import common.pojo.ProjectInfo;
import common.utils.PdfUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.*;

/**
 * The pdf generator<br>
 *
 * @author Zihao Long
 * @version 1.0, 2021-09-28 04:34
 * @since excelToPdf 0.0.1
 */
public class PdfService {

    /**
     * Generate pdf by data list and save to directory<br>
     *
     * @param [dataList]
     * @return void
     * @author Zihao Long
     */
    public static void genPdf(List<ProjectInfo> dataList) throws Exception {

        File file = new File(Constant.PDF_FILE_PATH);
        file.getParentFile().mkdirs();

        if (Constant.IS_STUDENT_DESC_TYPE) {
            genStudentDescPdf(dataList);
        } else if (Constant.IS_INTERNAL_CHECK_TYPE) {
            genInternalCheckPdf(dataList);
        }
    }

    /**
     * Generate the PDF of internal check vision<br>
     *
     * @param [dataList]
     * @return void
     * @author Zihao Long
     */
    private static void genInternalCheckPdf(List<ProjectInfo> dataList) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(Constant.PDF_FILE_PATH));
        Document doc = new Document(pdfDoc);
        doc.setMargins(10, 50, 0, 50);

        String pageTitle = "Project descriptions Computer Science " + Constant.ACADEMIC_YEAR;
        // define solid border
        SolidBorder solidBorder = new SolidBorder(new DeviceRgb(218, 218, 218), 1f);
        for (int i = 0; i < dataList.size(); i++) {
            if (i != 0) {
                // New page
                doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            }

            ProjectInfo data = dataList.get(i);

            // Add title for every page
            PdfUtils.addTitle(doc, pageTitle);

            // Add project title
            genLabelAndInput("Title", data.getProjectTitle(), solidBorder, doc);

            // Add project description
            genLabelAndInput("Description (plain text)", data.getProjectDesc(), solidBorder, doc);

            // Add image path
            genLabelAndInput("Single image, will be added to description, text in this box will be ignored (optional).", data.getSinglePicture(), solidBorder, doc);

            // Add reference
            genLabelAndInput("Reference or URL of supporting material", data.getReferences(), solidBorder, doc);

            // Add co-supervisor
            genLabelAndInput("Co-supervisor(s) - leave blank if project has a single supervisor", data.getCoSupervisor(), solidBorder, doc);

            // Add Languages and areas of interest
            genCheckboxList("Languages and areas of interest", data.getLaMap(), doc);

            // Add project type for grading
            genLabelAndInput("Project type for grading", data.getProjectType(), solidBorder, doc);

            // Add special hardware or software requirements
            genLabelAndInput("Special hardware or software requirements", data.getSpecialRequirements(), solidBorder, doc);

            // Add ethics
            genLabelAndInput("Ethics", data.getEthics(), solidBorder, doc);

            // Add Can the project be run remotely?
            genYesNoRadio("Can the project be run remotely?", data.getRunPrjRemotely1(), doc);

            // Add If (no) to the previous question, then please state, the requirements of the project that prevent it running remotely.
            genLabelAndInput("If (no) to the previous question, then please state, the requirements of the project that prevent it running remotely.", data.getRunPrjRemotely2(), solidBorder, doc);

            // Add If the university is placed in another lockdown is there a contingency available for the project that will allow its completion?
            genYesNoRadio("If the university is placed in another lockdown is there a contingency available for the project that will allow its completion?", data.getRunPrjRemotely3(), doc);

            // Add Meetings with the student in the first semester will be
            genLabelAndInput("Meetings with the student in the first semester will be", data.getMeetings(), solidBorder, doc);

            // Add Availability checkbox list
            genCheckboxList("Availability", data.getSuitMap(), doc);

            // Add If project has already been allocated enter Students Name and Number
            genLabelAndInput("If project has already been allocated enter Students Name and Number", data.getStudentId(), solidBorder, doc);

            // Add Contact details checkbox list
            genCheckboxList("Contact details prior to allocation", data.getCdMap(), doc);

            // Add Contact times
            genLabelAndInput("Contact times or dates, complete if \"hours on a set date\" selected. Also specify the location of these hours (e.g. via Teams) (500 characters max)", data.getContactTimes(), solidBorder, doc);
        }
        doc.close();
    }

    /**
     * Generate checkbox list<br>
     *
     * @param [labelStr, selectedStr, itemArray, doc]
     * @param map
     * @return void
     * @author Zihao Long
     */
    private static void genCheckboxList(String labelStr, Map<String, String> map, Document doc) {
        Paragraph laTitle = PdfUtils.genSubTitle(labelStr);
        doc.add(laTitle);

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            boolean isChecked = Constant.isYesInput(entry.getValue());
            Text checkBox = PdfUtils.genCheckbox(isChecked);
            Paragraph itemParagraph = PdfUtils.genParagraph("", PdfUtils.CONTENT_FONTSIZE);
            itemParagraph.add(checkBox);
            itemParagraph.add(" ");
            itemParagraph.add(entry.getKey());
            itemParagraph.setFixedLeading(14f);
            // Set margin bottom
            if (!iterator.hasNext()) {
                PdfUtils.setMarginBottom(itemParagraph);
            }
            doc.add(itemParagraph);
        }
    }

    /**
     * Generate yes and no radio inputs<br>
     *
     * @param [labelStr, yesNoValue, doc]
     * @return void
     * @author Zihao Long
     */
    private static void genYesNoRadio(String labelStr, String yesNoValue, Document doc) {
        Paragraph runRemotelyTag = PdfUtils.genSubTitle(labelStr);
        doc.add(runRemotelyTag);

        boolean isYes = Constant.isYesInput(yesNoValue);
        Text yesRadio = PdfUtils.genRadioInput(isYes);
        Paragraph yesParagraph = PdfUtils.genParagraph("", PdfUtils.CONTENT_FONTSIZE);
        yesParagraph.add(yesRadio).add(" ").add("YES ");
        Text noRadio = PdfUtils.genRadioInput(!isYes);
        yesParagraph.add(noRadio).add(" ").add("NO");
        doc.add(yesParagraph);
    }

    /**
     * Generate the label tag and input box<br>
     *
     * @param [labelStr, inputStr, solidBorder, doc]
     * @return void
     * @author Zihao Long
     */
    private static void genLabelAndInput(String labelStr, String inputStr, SolidBorder solidBorder, Document doc) {
        // Add label tag
        Paragraph label = PdfUtils.genSubTitle(labelStr);
        doc.add(label);

        // Add input box
        Paragraph input = PdfUtils.genParagraphWithBorder(inputStr, solidBorder);
        PdfUtils.setMarginBottom(input);
        doc.add(input);
    }

    /**
     * Generate the PDF of student vision<br>
     *
     * @param [dataList]
     * @return void
     * @author Zihao Long
     */
    private static void genStudentDescPdf(List<ProjectInfo> dataList) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(Constant.PDF_FILE_PATH));
        Document doc = new Document(pdfDoc);
        doc.setMargins(20, 10, 20, 10);

        // Generate contents page
        genContentsPage(doc, dataList);
        // Generate detail page
        genPrjDetailPage(doc, dataList);

        doc.close();
    }

    /**
     * Generate contents page<br>
     *
     * @param [doc]
     * @param dataList
     * @return void
     * @author Zihao Long
     */
    private static void genContentsPage(Document doc, List<ProjectInfo> dataList) {

        PdfUtils.addHeader(doc, "Computer Science Projects " + Constant.ACADEMIC_YEAR);

        Table table = new Table(new float[]{65f, 100f, 335f});
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setWidth(500f);
        table.setFixedLayout();
        table.setBorderLeft(Border.NO_BORDER);
        table.setBorderRight(Border.NO_BORDER);

        for (int i = 0; i < dataList.size(); i++) {
            // add header
            if (i == 0) {
                table.addCell(PdfUtils.genHeaderCell("Project no."));
                table.addCell(PdfUtils.genHeaderCell("Supervisor"));
                table.addCell(PdfUtils.genHeaderCell("Project title"));
            }
            int count = i + 1;
            String projectNo = Constant.PROJECT_NO_PREFIX + String.format("%03d", count);

            ProjectInfo data = dataList.get(i);
            data.setProjectNo(projectNo);
            table.addCell(PdfUtils.genBoldCell(data.getProjectNo()));
            table.addCell(PdfUtils.genNormalCell(data.getSupervisor()));
            table.addCell(PdfUtils.genNormalCell(data.getProjectTitle()));
        }
        doc.add(table);
    }

    /**
     * Generate project detail page<br>
     *
     * @param [doc, dataList]
     * @return void
     * @author Zihao Long
     */
    private static void genPrjDetailPage(Document doc, List<ProjectInfo> dataList) {
        // define blue solid border
        DeviceRgb blueColor = new DeviceRgb(68, 114, 196);
        SolidBorder solidBorder = new SolidBorder(blueColor, 1.5f);
        for (ProjectInfo data : dataList) {
            // New page
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

            // Add title for every page
            PdfUtils.addTitle(doc, "CS Project");

            // Create project detail table
            Table detailTable = new Table(1);
            detailTable.setWidth(545f);
            detailTable.setFixedLayout();
            detailTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
            detailTable.setBorder(solidBorder);

            // Add project no
            Paragraph prjNoTitle = PdfUtils.genSubTitle("Project No.: " + data.getProjectNo());
            prjNoTitle.setTextAlignment(TextAlignment.CENTER);
            Cell prjNoTitleCell = PdfUtils.addSubTitleCell(detailTable, prjNoTitle, false);
            prjNoTitleCell.setPadding(5);


            // Add literal project title
            Paragraph ltPrjTitle = PdfUtils.genSubTitle("Project Title");
            ltPrjTitle.setTextAlignment(TextAlignment.LEFT);
            Cell ltPrjTitleCell = PdfUtils.addSubTitleCell(detailTable, ltPrjTitle, true);

            // Add project title
            Paragraph prjTitle = PdfUtils.genParagraph(data.getProjectTitle(), 18);
            prjTitle.setTextAlignment(TextAlignment.CENTER);
            prjTitle.setBold();
            Cell prjTitleCell = PdfUtils.addSubTitleCell(detailTable, prjTitle, true);
            // hide border
            PdfUtils.hideHzBorderBtCell(ltPrjTitleCell, prjTitleCell);

            // Add supervisor
            Paragraph supervisorTitle = PdfUtils.genSubTitle("Supervisor");
            Paragraph supervisorName = PdfUtils.genParagraph(data.getSupervisor(), 14);
            supervisorName.setWidth(420f);
            supervisorName.setTextAlignment(TextAlignment.CENTER);
            supervisorTitle.add(supervisorName);
            PdfUtils.addSubTitleCell(detailTable, supervisorTitle, false);

            // Add co-supervisor
            String coSupervisor = data.getCoSupervisor();
            if (StringUtils.isNotEmpty(coSupervisor)) {
                Paragraph coSupervisorTitle = PdfUtils.genSubTitle("Co-Supervisor");
                Text coSupervisorName = new Text(": " + coSupervisor);
                coSupervisorName.setFontSize(Constant.DEFAULT_TEXT_FONT_SIZE);
                coSupervisorTitle.add(coSupervisorName);
                PdfUtils.addSubTitleCell(detailTable, coSupervisorTitle, false);
            }

            // Add project description title
            Paragraph prjDescTitle = PdfUtils.genSubTitle("Project Description");
            prjDescTitle.setTextAlignment(TextAlignment.LEFT);
            Cell prjDescTitleCell = PdfUtils.addSubTitleCell(detailTable, prjDescTitle, true);

            // Add project description
            Paragraph prjDesc = PdfUtils.genParagraph(data.getProjectDesc(), Constant.DEFAULT_TEXT_FONT_SIZE);
            prjDesc.setMarginTop(8f);
            Cell prjDescCell = PdfUtils.addSubTitleCell(detailTable, prjDesc, true);

            // hide border
            PdfUtils.hideHzBorderBtCell(prjDescTitleCell, prjDescCell);

            // Add project type
            Paragraph prjTypeTitle = PdfUtils.genSubTitle("Project type for grading: ");
            Text prjType = new Text(data.getProjectType());
            prjType.setFontSize(Constant.DEFAULT_TEXT_FONT_SIZE);
            prjTypeTitle.add(prjType);
            Cell prjTypeCell = PdfUtils.addSubTitleCell(detailTable, prjTypeTitle, true);

            // hide border
            PdfUtils.hideHzBorderBtCell(prjDescCell, prjTypeCell);

            // Add references title
            Paragraph refsTitle = PdfUtils.genSubTitle("References");
            Cell refsTitleCell = PdfUtils.addSubTitleCell(detailTable, refsTitle, false);

            // Add references
            String references = data.getReferences();
            Paragraph refs = PdfUtils.genParagraph(references, Constant.DEFAULT_TEXT_FONT_SIZE);
            Cell refsCell = PdfUtils.addSubTitleCell(detailTable, refs, false);
            if (StringUtils.isEmpty(references)) {
                refsCell.setPadding(10);
            }

            // hide border
            PdfUtils.hideHzBorderBtCell(refsTitleCell, refsCell);

            // Add sub title: 'Languages & Areas of interest'
            Paragraph laTitle = PdfUtils.genSubTitle("Languages & Areas of interest");
            Cell laTitleCell = PdfUtils.addSubTitleCell(detailTable, laTitle, true);

            // Add Languages & Areas of interest
            String laYesKeys = getYesKeysToStr(data.getLaMap());
            Paragraph la = PdfUtils.genParagraph(laYesKeys, Constant.DEFAULT_TEXT_FONT_SIZE);
            Cell laCell = PdfUtils.addSubTitleCell(detailTable, la, true);

            // hide border
            PdfUtils.hideHzBorderBtCell(laTitleCell, laCell);

            // Add sub title: 'Contact details'
            Paragraph cdTitle = PdfUtils.genSubTitle("Contact details");
            Cell cdTitleCell = PdfUtils.addSubTitleCell(detailTable, cdTitle, false);

            // Add contact details
            com.itextpdf.layout.element.List cdList = new com.itextpdf.layout.element.List().setSymbolIndent(9)
                    .setFont(PdfUtils.genBaseFont())
                    .setMarginLeft(10f)
                    .setFontSize(Constant.DEFAULT_TEXT_FONT_SIZE);

            Set<Map.Entry<String, String>> entries = data.getCdMap().entrySet();
            for (Map.Entry<String, String> entry : entries) {
                if (Constant.isYesInput(entry.getValue())) {
                    cdList.add(new ListItem(entry.getKey()));
                }
            }
            Cell cdCell = PdfUtils.addSubTitleCell(detailTable, cdList, false);

            // hide border
            PdfUtils.hideHzBorderBtCell(cdTitleCell, cdCell);

            // Add sub title: 'Project Suitable for'
            Paragraph psfTitle = PdfUtils.genSubTitle("Project Suitable for");
            prjDescTitle.setTextAlignment(TextAlignment.LEFT);
            Cell psfTitleCell = PdfUtils.addSubTitleCell(detailTable, psfTitle, true);

            // Add project suitable for
            String suitYesKeys = getYesKeysToStr(data.getSuitMap());
            Paragraph psf = PdfUtils.genParagraph(suitYesKeys, Constant.DEFAULT_TEXT_FONT_SIZE);
            Cell psfCell = PdfUtils.addSubTitleCell(detailTable, psf, true);

            // hide border
            PdfUtils.hideHzBorderBtCell(psfTitleCell, psfCell);

            doc.add(detailTable);
        }
    }

    /**
     * Get the dynamic keys which value equals 1 and return a string join with ', '<br>
     *
     * @param [map]
     * @return void
     * @author Zihao Long
     */
    private static String getYesKeysToStr(Map<String, String> map) {
        List<String> yesKeyList = new ArrayList<>();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            if (Constant.isYesInput(entry.getValue())) {
                yesKeyList.add(entry.getKey());
            }
        }
        return String.join(", ", yesKeyList);
    }

}
