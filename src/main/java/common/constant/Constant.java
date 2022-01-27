package common.constant;

import gui.Frame;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ResourceBundle;

/**
 * The Constant<br>
 *
 * @author Zihao Long
 * @version 1.0, 2021-09-27 01:17
 * @since excelToPdf 0.0.1
 */
public class Constant {


    /**
     * The text fields on frame
     */
    public static String YEAR_INPUT_LABEL;
    public static String EXCEL_FILES_SELECTION;
    public static String SAVE_FOLDER_SELECTION;
    static {
        ResourceBundle resource = ResourceBundle.getBundle("frame");
        YEAR_INPUT_LABEL = resource.getString("year.input.label");
        EXCEL_FILES_SELECTION = resource.getString("excel.files.selection");
        SAVE_FOLDER_SELECTION = resource.getString("save.folder.selection");
    }

    /**
     * The academic year entered from user interface
     */
    public static String ACADEMIC_YEAR = null;

    /**
     * Define current system, the default is windows
     */
    public static boolean IS_WINDOWS = false;
    public static boolean IS_MAC_OS = false;

    /**
     * Define the PDF types
     */
    public static boolean IS_STUDENT_DESC_TYPE = false;
    public static boolean IS_INTERNAL_CHECK_TYPE = false;

    /**
     * The file path notation, the default is windows, if current system is MacOs, it will be '/'
     *
     */
    public static String FILE_PATH_NOTATION = null;

    /**
     * The pdf absolute file path
     */
    public static String PDF_FILE_PATH;

    /**
     * The logger<br>
     *
     * @param 
     * @return 
     * @author Zihao Long
     */
    public static Logger logger = Logger.getLogger(Frame.class);
    
    /**
     * The project no prefix
     */
    public static String PROJECT_NO_PREFIX = "CS22-";

    /**
     * The default text font size in pdf
     */
    public static int DEFAULT_TEXT_FONT_SIZE = 10;

    /**
     * Check the input value in cell whether it is yes (1 means yes, 0 means no) <br><br>
     *
     * @param [inputVal]
     * @return boolean
     * @author Zihao Long
     */
    public static boolean isYesInput(String inputVal) {
        if (!StringUtils.isEmpty(inputVal)
                && "1".equals(inputVal.trim())) {
            return true;
        }
        return false;
    }

}
