package common.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Map;

/**
 * The project info<br>
 *
 * @author Zihao Long
 * @version 1.0, 2021-09-27 00:14
 * @since excelToPdf 0.0.1
 */
public class ProjectInfo {

    private String projectNo;

    @Excel(name = "Q01_1: Title")
    private String projectTitle;

    @Excel(name = "Full name")
    private String supervisor;

    @Excel(name = "Q02_P1 Description")
    private String projectDesc;

    @Excel(name = "Q03_P1 Single picture")
    private String singlePicture;

    @Excel(name = "Q04_P1 References")
    private String references;

    @Excel(name = "Q05_P1 Co-supervisor")
    private String coSupervisor;

    @Excel(name = "Q07_Project Type")
    private String projectType;

    @Excel(name = "Q08_P1 Harware requirements")
    private String specialRequirements;

    @Excel(name = "Q09_P1 Ethics")
    private String ethics;

    @Excel(name = "Q10_Running Project Remotely (1)")
    private String runPrjRemotely1;

    @Excel(name = "Q11_Running Project Remotely (2)")
    private String runPrjRemotely2;

    @Excel(name = "Q12_Running Project Remotely (3)")
    private String runPrjRemotely3;

    @Excel(name = "Q13_meetings")
    private String meetings;

    @Excel(name = "Q17_P1 Contact times")
    private String contactTimes;

    /**
     * The languagesAndAreas is appended by ',' from la_1 to la_24 in get method<br>
     */
    private Map<String, String> laMap;

    /**
     * The contactDetailList created in get method consists of cd_1 to cd_3 <br>
     */
    private Map<String, String> cdMap;
    
    /**
     * The projectSuitableFor is appended by ',' from suit_1 to suit_12 in get method<br>
     */
    private Map<String, String> suitMap;

    @Excel(name = "Q15_P1 Student Id")
    private String studentId;

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getSinglePicture() {
        return singlePicture;
    }

    public void setSinglePicture(String singlePicture) {
        this.singlePicture = singlePicture;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public String getCoSupervisor() {
        return coSupervisor;
    }

    public void setCoSupervisor(String coSupervisor) {
        this.coSupervisor = coSupervisor;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getSpecialRequirements() {
        return specialRequirements;
    }

    public void setSpecialRequirements(String specialRequirements) {
        this.specialRequirements = specialRequirements;
    }

    public String getEthics() {
        return ethics;
    }

    public void setEthics(String ethics) {
        this.ethics = ethics;
    }

    public String getRunPrjRemotely1() {
        return runPrjRemotely1;
    }

    public void setRunPrjRemotely1(String runPrjRemotely1) {
        this.runPrjRemotely1 = runPrjRemotely1;
    }

    public String getRunPrjRemotely2() {
        return runPrjRemotely2;
    }

    public void setRunPrjRemotely2(String runPrjRemotely2) {
        this.runPrjRemotely2 = runPrjRemotely2;
    }

    public String getRunPrjRemotely3() {
        return runPrjRemotely3;
    }

    public void setRunPrjRemotely3(String runPrjRemotely3) {
        this.runPrjRemotely3 = runPrjRemotely3;
    }

    public String getMeetings() {
        return meetings;
    }

    public void setMeetings(String meetings) {
        this.meetings = meetings;
    }

    public String getContactTimes() {
        return contactTimes;
    }

    public void setContactTimes(String contactTimes) {
        this.contactTimes = contactTimes;
    }

    public Map<String, String> getLaMap() {
        return laMap;
    }

    public void setLaMap(Map<String, String> laMap) {
        this.laMap = laMap;
    }

    public Map<String, String> getCdMap() {
        return cdMap;
    }

    public void setCdMap(Map<String, String> cdMap) {
        this.cdMap = cdMap;
    }

    public Map<String, String> getSuitMap() {
        return suitMap;
    }

    public void setSuitMap(Map<String, String> suitMap) {
        this.suitMap = suitMap;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}