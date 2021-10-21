package common.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * The Constant<br>
 *
 * @author Zihao Long
 * @version 1.0, 2021-09-27 01:17
 * @since excelToPdf 0.0.1
 */
public interface Constant {

    /**
     * The project no prefix
     */
    String PROJECT_NO_PREFIX = "CS22-";

    /**
     * The default text font size in pdf
     */
    int DEFAULT_TEXT_FONT_SIZE = 10;

    /**
     * Check the input value in cell whether it is yes (1 means yes, 0 means no) <br><br>
     *
     * @param [inputVal]
     * @return boolean
     * @author Zihao Long
     */
    static boolean isYesInput(String inputVal) {
        if (!StringUtils.isEmpty(inputVal)
                && "1".equals(inputVal.trim())) {
            return true;
        }
        return false;
    }

}
