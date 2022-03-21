package service;

import common.constant.Constant;
import common.exception.BusinessException;
import common.pojo.ProjectInfo;
import common.utils.ExcelUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The MyService<br>
 *
 * @author Zihao Long
 * @version 1.0, 2022-01-27 11:14
 * @since excelToPdf 0.0.1
 */
public class MyService {

    /**
     * Generate by selection<br>
     *
     * @param [academicYear, excelFilePath, savingFolderPath, selectedStr]
     * @return void
     * @author Zihao Long
     */
    public static void genBySelection(String academicYear, String excelFilePath, String savingFolderPath, String selectedStr) throws Exception {
        Constant.ACADEMIC_YEAR = academicYear;

        // get system info
        if (Constant.FILE_PATH_NOTATION == null) {
            String osName = System.getProperty("os.name");
            if (osName.startsWith("Windows")) {
                Constant.IS_WINDOWS = true;
                Constant.FILE_PATH_NOTATION = "\\";
            } else {
                // MacOs, linux
                Constant.IS_MAC_OS = true;
                Constant.FILE_PATH_NOTATION = "/";
            }
        }

        // Reset type
        Constant.IS_STUDENT_DESC_TYPE = false;
        Constant.IS_INTERNAL_CHECK_TYPE = false;

        String pdfFileName = null;
        if ("Project Descriptions for Students".equalsIgnoreCase(selectedStr)) {
            Constant.IS_STUDENT_DESC_TYPE = true;
            pdfFileName = "Project_Descriptions_for_Students_" + academicYear;
        } else if ("Project Descriptions for Internal Check".equalsIgnoreCase(selectedStr)) {
            Constant.IS_INTERNAL_CHECK_TYPE = true;
            pdfFileName = "Project_Descriptions_for_Internal_Check_" + academicYear;
        }

        // gen pdf absolute path
        Constant.PDF_FILE_PATH = savingFolderPath + Constant.FILE_PATH_NOTATION + pdfFileName + ".pdf";

        Constant.logger.info("Reading Excel file...");

        // Read data from Excel
        List<ProjectInfo> dataList = ExcelUtils.importExcelForPdf(excelFilePath, 0, 1);


        Constant.logger.info("Successfully parsed Excel data...");
        Constant.logger.info("Generating PDF file...");

        // Generate pdf
        PdfService.genPdf(dataList);
    }

    /**
     * Open file by command line<br>
     *
     * @param []
     * @return void
     * @author Zihao Long
     */
    public static void openFile(String filePath) throws IOException {
        if (Constant.IS_WINDOWS) {
            Runtime.getRuntime().exec("explorer.exe /select, " + filePath);
            return;
        } else if (Constant.IS_MAC_OS) {
            Runtime.getRuntime().exec("open " + filePath);
            return;
        }
        throw new BusinessException("Unsupported system");
    }

    /**
     * Filter Excel file<br>
     *
     * @param [file]
     * @return boolean
     * @author Zihao Long
     */
    public static boolean filterExcelFile(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith("csv")
                || fileName.endsWith("xls")
                || fileName.endsWith("xlsx")) {
            if (fileName.startsWith(".")) {
                return false;
            }
            return true;
        }
        return false;
    }
}
