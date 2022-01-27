package common.uitils;

import cn.afterturn.easypoi.csv.CsvImportUtil;
import cn.afterturn.easypoi.csv.entity.CsvImportParams;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import common.exception.BusinessException;
import common.pojo.ProjectInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import service.MyService;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * The excel utils<br>
 *
 * @author Zihao Long
 * @version 1.0, 2021-09-27 00:04
 * @since excelToPdf 0.0.1
 */
public class ExcelUtils {

    /**
     * Read excel or csv by path and return data list<br>
     *
     * @param [filePath, titleRows, headerRows, pojoClass]
     * @return java.util.List<T>
     * @author Zihao Long
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }

        try {
            File inputFile = new File(filePath);
            File[] files = new File[]{inputFile};
            if (inputFile.isDirectory()) {
                files = inputFile.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return MyService.filterExcelFile(file);
                    }
                });
            }
            List<T> dataList = new ArrayList<>();
            for (File file : files) {
                System.out.println(file.getPath());
                if (file.getPath().endsWith(".csv")) {
                    CsvImportParams params = new CsvImportParams();
                    params.setTitleRows(titleRows);
                    params.setHeadRows(headerRows);
                    dataList.addAll(CsvImportUtil.importCsv(new FileInputStream(file), pojoClass, params));
                } else {
                    ImportParams params = new ImportParams();
                    params.setTitleRows(titleRows);
                    params.setHeadRows(headerRows);
                    dataList.addAll(ExcelImportUtil.importExcel(file, pojoClass, params));
                }
            }

            if (CollectionUtils.isEmpty(dataList)) {
                throw new BusinessException("The excel content is empty.");
            }
            return dataList;
        } catch (NoSuchElementException e) {
            throw new BusinessException("The excel content is empty.");
        } catch (FileNotFoundException e) {
            throw new BusinessException("The excel file is not found.");
        }
    }

    /**
     * Import excel and generate data for PDF<br>
     *
     * @param [filePath, titleRows, headerRows]
     * @return java.util.List<common.pojo.ProjectInfo>
     * @author Zihao Long
     */
    public static List<ProjectInfo> importExcelForPdf(String filePath, Integer titleRows, Integer headerRows) throws Exception {
        List<ProjectInfo> dataList = new ArrayList<>();
        List<Map> mapList = importExcel(filePath, titleRows, headerRows, Map.class);
        for (Map map : mapList) {
            ProjectInfo projectInfo = new ProjectInfo();
            Field[] allFields = projectInfo.getClass().getDeclaredFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                // Get actual value from field annotation
                Excel excelAn = field.getAnnotation(Excel.class);
                if (excelAn == null) {
                    continue;
                }
                String anName = excelAn.name();
                Object valObj = map.get(anName);
                String filedValue;
                if (valObj instanceof Number) {
                    filedValue = valObj.toString();
                } else {
                    filedValue = (String) map.get(anName);
                }
                if ("Q07_Project Type".equals(anName)) {
                    // 1 : Standard project ==>> Standard project
                    String[] splitArr = filedValue.split(" : ");
                    if (splitArr.length > 1) {
                        filedValue = splitArr[1].trim();
                    }
                }
                field.set(projectInfo, filedValue);
            }
            setDynamicValues(map, projectInfo);
            dataList.add(projectInfo);
        }
        return dataList;
    }

    /**
     * Set the dynamic values to map<br>
     *
     * @param [map, projectInfo]
     * @return void
     * @author Zihao Long
     */
    private static void setDynamicValues(Map map, ProjectInfo projectInfo) {
        Map<String, String> laMap = new LinkedHashMap<>(2);
        Map<String, String> cdMap = new LinkedHashMap<>(2);
        Map<String, String> suitMap = new LinkedHashMap<>(2);
        Set set = map.keySet();
        for (Object key : set) {
            String keyStr = (String) key;
            if (keyStr.startsWith("Q06_P1 Languages and areas")) {
                splitKeyToMap(keyStr, map, laMap);
            } else if (keyStr.startsWith("Q14_P1 Suitability")) {
                splitKeyToMap(keyStr, map, suitMap);
            } else if (keyStr.startsWith("Q16_P1 Contact details")) {
                splitKeyToMap(keyStr, map, cdMap);
            }
        }
        projectInfo.setLaMap(laMap);
        projectInfo.setCdMap(cdMap);
        projectInfo.setSuitMap(suitMap);
    }

    /**
     * Split keys to dynamic map<br>
     *
     * @param [key, map, dynMap]
     * @return void
     * @author Zihao Long
     */
    private static void splitKeyToMap(String key, Map map, Map<String, String> dynMap) {
        Object valObj = map.get(key);
        String valStr;
        if (valObj instanceof Number) {
            valStr = valObj.toString();
        } else {
            valStr = (String) valObj;
        }
        // Key E.g. Q06_P1 Languages and areas->C/C++
        dynMap.put(key.split("->")[1], valStr);
    }
}
