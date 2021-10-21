package common.exception;

/**
 * The business exception <br>
 *
 * @author Zihao Long
 * @version 1.0, 2021年09月26日 05:54
 * @since excelToPdf 0.0.1
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
