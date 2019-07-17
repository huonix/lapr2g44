package Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 * This class is responsible for create a search, based on some parameters, such
 * as, units involved, minimum common units and minimum number of students on
 * pattern.
 *
 * @author Group 44 LAPR2
 */
public final class Search implements Serializable {

    private String projectName;
    private String name;
    private ArrayList<Unit> unitList = new ArrayList<>();
    private ArrayList<Student> studentList = new ArrayList<>();
    private int minUnits;
    private int minStudents;
    private HashMap<String, Pattern> patternListMap = new HashMap<>();
    private ArrayList<Result> resultList = new ArrayList<>();
    private long runTime;
    private Date date;

    /**
     * Create a search based on parameters
     *
     * @param name Name of search
     * @param studentList List of students
     * @param unitList List of units
     * @param minUnits Minimum of units
     * @param minStudents Minimum of students
     * @throws Exception Name is null or empty, studentList or unitList is null
     */
    public Search(String projectName, String name, ArrayList<Student> studentList, ArrayList<Unit> unitList, int minUnits, int minStudents) throws Exception {
        setProjectName(projectName);
        setName(name);
        setStudentList(studentList);
        setUnitList(unitList);
        setMinUnits(minUnits);
        setMinStudents(minStudents);
    }

    /**
     * Clone constructor
     *
     * @param search Search object
     */
    public Search(Search search) {
        projectName = search.getProjectName();
        name = search.getName();
        unitList = (ArrayList<Unit>) search.getUnitList().clone();
        studentList = (ArrayList<Student>) search.getStudentList().clone();
        minUnits = search.getMinUnits();
        minStudents = search.getMinStudents();
    }

    /**
     * This method pick a list of unique unit pattern, and for each student
     * compare if student have the unit pattern (based on parameters). After
     * found all unit patterns and add students to unit pattern, remove the unit
     * patterns that do not meet the parameters.
     *
     * @throws Exception If unit pattern list, student or unit list is null
     */
    public void runSearch() throws Exception {
        setDate(new Date());
        long beginTime = System.currentTimeMillis();

        patternListMap = getUnitUniquePatterns();
        for (Student student : studentList) {
            ArrayList<Pattern> tempList = new ArrayList<>();
            for (Pattern patt : patternListMap.values()) {
                Pattern commonPatt = new Pattern(patt.getPattern().clone(), patt.getStudentList(), patt.getUnitList());
                if (commonPatt.hasMinElements(student.getUnitPattern(unitList).getPattern(), minUnits)) {
                    tempList.add(commonPatt);
                }
            }
            for (Pattern tempPatt : tempList) {
                if (!tempPatt.existInPatternList(patternListMap)) {
                    patternListMap.put(Arrays.toString(tempPatt.getPattern()), tempPatt);
                }
            }
        }
        Pattern.removeStudents(patternListMap, minStudents);
        setRunTime(System.currentTimeMillis() - beginTime);
        addResults(this);
    }

    /**
     * Get the unique unit pattern list. This method runs all unit patterns and
     * pick only the unique unit patterns (no repetitions)
     *
     * @return List of unique unit patterns
     * @throws Exception If unit pattern list is null, student is null
     */
    public HashMap<String, Pattern> getUnitUniquePatterns() throws Exception {
        patternListMap = new HashMap<>();
        for (Student student : studentList) {
            UnitPattern unitPattern = student.getUnitPattern(unitList);
            Pattern pattTemp = new Pattern(unitPattern.getPattern(), student, unitList);
            if (!pattTemp.existInPatternList(patternListMap)) {
                patternListMap.put(Arrays.toString(pattTemp.getPattern()), pattTemp);
            }
        }
        return patternListMap;
    }

    /**
     * Get the name of search
     *
     * @return Name of search
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of search
     *
     * @param name Name of search
     * @throws Exception If name is null or empty
     */
    public final void setName(String name) throws Exception {
        if (name == null || name.isEmpty()) {
            throw new Exception("Empty name for search.");
        }
        this.name = name;
    }

    /**
     * Get list of units
     *
     * @return List of units
     */
    public ArrayList<Unit> getUnitList() {
        return unitList;
    }

    /**
     * Set the list of units
     *
     * @param unitList List of units
     * @throws Exception If unitList is null
     */
    public final void setUnitList(ArrayList<Unit> unitList) throws Exception {
        if (unitList == null) {
            throw new Exception("List of units is null.");
        }
        this.unitList = unitList;
    }

    /**
     * Get list of students
     *
     * @return List of students
     */
    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    /**
     * Set the list of students
     *
     * @param studentList List of students
     * @throws Exception If studentList is null
     */
    public final void setStudentList(ArrayList<Student> studentList) throws Exception {
        if (studentList == null) {
            throw new Exception("List of students is null.");
        }
        this.studentList = studentList;
    }

    /**
     * Get the minimum of units per unit pattern
     *
     * @return Minimum of units
     */
    public int getMinUnits() {
        return minUnits;
    }

    /**
     * Set minimum of units per unit pattern
     *
     * @param minUnits Minimum of units
     */
    public final void setMinUnits(int minUnits) {
        this.minUnits = (minUnits > 0) ? minUnits : 1;
    }

    /**
     * Get minimum of students per unit pattern
     *
     * @return Minimum of students
     */
    public int getMinStudents() {
        return minStudents;
    }

    /**
     * Set minimum of students per unit pattern
     *
     * @param minStudents Minimum of students
     */
    public final void setMinStudents(int minStudents) {
        this.minStudents = (minStudents > 0) ? minStudents : 1;
    }

    /**
     * Get a hash map of patterns
     *
     * @return Hash map with all patterns
     */
    public HashMap<String, Pattern> getPatternListMap() {
        return patternListMap;
    }

    /**
     * Set hash map of patterns
     *
     * @param patternListMap Hash map of patterns
     */
    public void setPatternListMap(HashMap<String, Pattern> patternListMap) {
        this.patternListMap = patternListMap;
    }

    /**
     * Method used to get the result from this search
     *
     * @return The Result of the search
     */
    public ArrayList<Result> getResult() {
        return resultList;
    }

    /**
     * Method used to set the result of the search
     *
     * @param patternListMap Hash map with the patterns
     */
    public void addResults(HashMap<String, Pattern> patternListMap) {
        this.resultList.add(new Result(patternListMap));
    }

    /**
     * Method used to set the result of the search
     *
     * @param search Object Search
     */
    public void addResults(Search search) {
        this.resultList.add(new Result(projectName, search));
    }

    /**
     * Method used to set the result of the search
     *
     * @param result Object Result
     */
    public void addResult(Result result) {
        this.resultList.add(result);
    }

    /**
     * Method used to get the time spend by the search in milliseconds
     *
     * @return Long with the runtime
     */
    public long getRunTime() {
        return runTime;
    }

    /**
     * Method used to set the runtime of a search
     *
     * @param runTime Long with the runtIme
     */
    public void setRunTime(long runTime) {
        this.runTime = (runTime > 0) ? runTime : 0;
    }

    /**
     * Method used to returns the run date and time of the search
     *
     * @return Object Date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Method used to set the date and time of the search
     *
     * @param date Object date
     */
    public void setDate(Date date) {
        this.date = (date != null) ? date : new Date();
    }

    /**
     * Method used to get the project name
     *
     * @return String
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Method used to set the project name
     *
     * @param projectName String
     */
    public void setProjectName(String projectName) {
        this.projectName = (projectName != null && !projectName.isEmpty()) ? projectName : "";
    }

    /**
     * Method used to get the search by name
     *
     * @param list List of search
     * @param name name of the search
     * @return Search
     */
    public static Search getSearchByName(ArrayList<Search> list, String name) {
        for (Search obj : list) {
            if (obj.getName().equalsIgnoreCase(name)) {
                return obj;
            }
        }
        return null;
    }

    /**
     * Get the result by search name and index
     *
     * @param list List of searches
     * @param searchName Name of search
     * @param index Index of search
     * @return Result
     */
    public static Result getResultBySearchAndIndex(ArrayList<Search> list, String searchName, int index) {
        for (Search obj : list) {
            if (obj.getName().equalsIgnoreCase(searchName)) {
                if (obj.getResult().size() > 0 && obj.getResult().size() > index) {
                    return obj.getResult().get(index);
                }
                return null;
            }
        }
        return null;
    }

    /**
     * Clear all results on array
     */
    public void removeResults() {
        this.resultList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Search{" + "name=" + name + ", unitList=" + unitList + ", studentList=" + studentList + ", minUnits=" + minUnits + ", minStudents=" + minStudents + ", patternListMap=" + patternListMap + '}';
    }
}