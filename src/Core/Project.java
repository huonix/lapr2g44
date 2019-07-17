package Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is responsible to define the necessary parameters to create a
 * project, such as, scale, units involved, export type, etc.
 *
 * @author Group 44 LAPR2
 */
public class Project implements Serializable {

    public enum ExportType {

        HTML, CSV
    };
    private String name;
    private ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Search> searchList = new ArrayList<>();
    private ExportType exportType;
    private int equivalenceYear;
    private int headerIndexECTS;
    private HashMap<String, Double> selectedScale = new HashMap<>();
    private String selectedScaleInfo;
    private HashMap<String, HashMap<String, Double>> predefinedScales = new HashMap<>();
    private HashMap<String, String> predefinedScalesInfo = new HashMap<>();

    /**
     * Project Construct. Set default scales.
     */
    public Project() {
        // Add predefined Scales
        HashMap<String, Double> tempScale = new HashMap<>();
        // 0...5
        tempScale.put("0", 0.0);
        tempScale.put("1", 1.0);
        tempScale.put("2", 2.0);
        tempScale.put("3", 3.0);
        tempScale.put("4", 4.0);
        tempScale.put("5", 5.0);
        predefinedScales.put("0...5", tempScale);
        predefinedScalesInfo.put("0...5", "Scale: 0 to 5");
        // 0...10
        tempScale.put("0", 0.0);
        tempScale.put("1", 1.0);
        tempScale.put("2", 2.0);
        tempScale.put("3", 3.0);
        tempScale.put("4", 4.0);
        tempScale.put("5", 5.0);
        tempScale.put("6", 6.0);
        tempScale.put("7", 7.0);
        tempScale.put("8", 8.0);
        tempScale.put("9", 9.0);
        tempScale.put("10", 10.0);
        predefinedScales.put("0...10", tempScale);
        predefinedScalesInfo.put("0...10", "Scale: 0 to 10");
        // 0...20
        tempScale.put("0", 0.0);
        tempScale.put("1", 1.0);
        tempScale.put("2", 2.0);
        tempScale.put("3", 3.0);
        tempScale.put("4", 4.0);
        tempScale.put("5", 5.0);
        tempScale.put("6", 6.0);
        tempScale.put("7", 7.0);
        tempScale.put("8", 8.0);
        tempScale.put("9", 9.0);
        tempScale.put("10", 10.0);
        tempScale.put("11", 11.0);
        tempScale.put("12", 12.0);
        tempScale.put("13", 13.0);
        tempScale.put("14", 14.0);
        tempScale.put("15", 15.0);
        tempScale.put("16", 16.0);
        tempScale.put("17", 17.0);
        tempScale.put("18", 18.0);
        tempScale.put("19", 19.0);
        tempScale.put("20", 20.0);
        predefinedScales.put("0...20", tempScale);
        predefinedScalesInfo.put("0...20", "Scale: 0 to 20");
        // A...F
        tempScale.put("A", 1.0);
        tempScale.put("B", 2.0);
        tempScale.put("C", 3.0);
        tempScale.put("D", 4.0);
        tempScale.put("E", 5.0);
        tempScale.put("F", 6.0);
        predefinedScales.put("A...F", tempScale);
        predefinedScalesInfo.put("A...F", "Scale: A - 1, B - 2, C - 3, D - 4, E - 5, F - 6");
        // O - [17, 20]
        // E - [14, 16]
        // A - [10, 13]
        // P - [7, 9]
        // D - [4, 6]
        // T - [0, 3]
        tempScale.put("T", (double) (0 + 3) / 2);
        tempScale.put("D", (double) (4 + 6) / 2);
        tempScale.put("P", (double) (7 + 9) / 2);
        tempScale.put("A", (double) (10 + 13) / 2);
        tempScale.put("E", (double) (14 + 16) / 2);
        tempScale.put("O", (double) (17 + 20) / 2);
        predefinedScales.put("T.D.P.A.E.O", tempScale);
        predefinedScalesInfo.put("T.D.P.A.E.O", "Scale: T - [0, 3], D - [4, 6], P - [7, 9] A - [10, 13], E - [14, 16], O - [17, 20]");
    }

    /**
     * Method used to get the project name
     *
     * @return String with the project name
     */
    public String getName() {
        return name;
    }

    /**
     * Method used to set the project name
     *
     * @param name String with the name of the project
     */
    public final void setName(String name) throws Exception {
        if (name == null || name.isEmpty()) {
            throw new Exception("Name of project must be defined.");
        }
        if (name.length() > 150) {
            throw new Exception("The name of the project is too big. (max 150 chars)");
        }
        this.name = name;
    }

    /**
     * Method used to reset the project
     */
    public final void reset() {
        this.students = new ArrayList<>();
        this.units = new ArrayList<>();
        this.searchList = new ArrayList<>();
        this.equivalenceYear = -1;
        this.headerIndexECTS = 0;
        this.exportType = ExportType.CSV;
        this.selectedScale = new HashMap<>();
        Student.equivalenceYear = 0;
    }

    /**
     * Get Units used on project
     *
     * @return Units used on project
     */
    public ArrayList<Unit> getUnits() {
        return units;
    }

    /**
     * Set units to be used on project
     *
     * @param units Units to be used on project
     */
    public void setUnits(ArrayList<Unit> units) {
        this.units = (units != null) ? units : new ArrayList<Unit>();
    }

    /**
     * Get students who belong to the project
     *
     * @return Students who belong to project
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Set students who belong to the project
     *
     * @param students students who belong to the project
     */
    public void setStudents(ArrayList<Student> students) {
        this.students = (students != null) ? students : new ArrayList<Student>();
    }

    /**
     * Get the list of searches that are in the project
     *
     * @return searches that are in the project
     */
    public ArrayList<Search> getSearchList() {
        return searchList;
    }

    /**
     * Set the list of searches that are in the project
     *
     * @param searchList searches that are in the project
     */
    public void setSearchList(ArrayList<Search> searchList) {
        this.searchList = (searchList != null) ? searchList : new ArrayList<Search>();
    }

    /**
     * Get equivalence year for units
     *
     * @return Equivalence year for units
     */
    public int getEquivalenceYear() {
        return equivalenceYear;
    }

    /**
     * Set equivalence year for units
     *
     * @param equivalenceYear Equivalence year for units
     */
    public void setEquivalenceYear(int equivalenceYear) {
        this.equivalenceYear = (equivalenceYear >= 0) ? equivalenceYear : 99;
    }

    /**
     * Get column index for ECTS
     *
     * @return Column index for ECTS
     */
    public int getHeaderIndexECTS() {
        return headerIndexECTS;
    }

    /**
     * Set column index for ECTS
     *
     * @param headerIndexECTS Column index for ECTS
     */
    public void setHeaderIndexECTS(int headerIndexECTS) {
        this.headerIndexECTS = (headerIndexECTS >= 0) ? headerIndexECTS : 0;
    }

    /**
     * Get predefined Scales
     *
     * @return predefined Scales
     */
    public HashMap<String, HashMap<String, Double>> getPredefinedScales() {
        return predefinedScales;
    }

    /**
     * Set predefined Scales
     *
     * @param predefinedScales predefined Scales
     */
    public void setPredefinedScales(HashMap<String, HashMap<String, Double>> predefinedScales) {
        this.predefinedScales = predefinedScales == null ? this.predefinedScales : predefinedScales;
    }

    /**
     * Get predefined Scales informations
     *
     * @return Scales informations
     */
    public HashMap<String, String> getPredefinedScalesInfo() {
        return predefinedScalesInfo;
    }

    /**
     * Set predefined Scales informations
     *
     * @param predefinedScalesInfo Scales informations
     */
    public void setPredefinedScalesInfo(HashMap<String, String> predefinedScalesInfo) {
        this.predefinedScalesInfo = predefinedScalesInfo == null ? this.predefinedScalesInfo : predefinedScalesInfo;
    }

    /**
     * Get selected scale for the project
     *
     * @return Selected scale for the project
     */
    public HashMap<String, Double> getSelectedScale() {
        return selectedScale;
    }

    /**
     * Set selected scale for the project
     *
     * @param selectedScale Scale to be used on the project
     */
    public void setSelectedScale(HashMap<String, Double> selectedScale) {
        this.selectedScale = selectedScale == null ? new HashMap<String, Double>() : selectedScale;
    }

    /**
     * Get information about selected scale
     *
     * @return String
     */
    public String getSelectedScaleInfo() {
        return selectedScaleInfo;
    }

    /**
     * Set information about selected scale
     *
     * @param selectedScaleInfo Information about scale
     */
    public void setSelectedScaleInfo(String selectedScaleInfo) {
        this.selectedScaleInfo = selectedScaleInfo == null || selectedScaleInfo.isEmpty() ? "No information" : selectedScaleInfo;
    }

    /**
     * Get export type
     *
     * @return Export type
     */
    public ExportType getExportType() {
        return exportType;
    }

    /**
     * Set export type
     *
     * @param exportType HTML or CSV
     */
    public void setExportType(ExportType exportType) {
        this.exportType = exportType == null ? ExportType.CSV : exportType;
    }
}
