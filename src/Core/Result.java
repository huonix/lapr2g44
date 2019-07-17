package Core;

import Core.Student.StudentInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is responsible for treating and saving a result of a search.
 *
 * @author Group 44 LAPR2
 */
public final class Result implements Serializable {

    private ArrayList<Pattern> patterns = new ArrayList<>();
    private HashMap<String, Pattern> patternListMap;
    private String searchName;
    private String projectName;
    private Date Date;
    private ArrayList<Unit> unitList;
    private ArrayList<Student> studentList;
    private int studentsMinNumber;
    private int unitsMinNumber;
    private long runTime;

    /**
     * Constructor responsible for the creation of a new result
     *
     * @param projectName Name of the project
     * @param search Object Search
     */
    public Result(String projectName, Search search) {
        setPatternListMap(search.getPatternListMap());
        setPatterns(search.getPatternListMap().values());
        setProjectName(search.getProjectName());
        setSearchName(search.getName());
        setStudentsMinNumber(search.getMinStudents());
        setUnitsMinNumber(search.getMinUnits());
        setUnitList(search.getUnitList());
        setStudentList(search.getStudentList());
        setDate(search.getDate());
        setRunTime(search.getRunTime());
    }

    /**
     * Constructor responsible for the creation of a new result
     *
     * @param projectName Name of the project
     * @param search Object Search
     * @param newPatternList New pattern list
     * @param minStudents Minimum number of students
     * @param minUnits Minimum number of units
     */
    public Result(String projectName, Search search, ArrayList<Pattern> newPatternList, int minStudents, int minUnits) {
        HashMap<String, Pattern> listMap = new HashMap<>();
        for (Pattern pattern : newPatternList) {
            listMap.put(Arrays.toString(pattern.getPattern()), pattern);
        }
        setPatternListMap(listMap);
        setPatterns(newPatternList);
        setProjectName(search.getProjectName());
        setSearchName(search.getName());
        setStudentsMinNumber(minStudents);
        setUnitsMinNumber(minUnits);
        setUnitList(search.getUnitList());
        setStudentList(search.getStudentList());
        setDate(new Date());
        setRunTime(0);
    }

    /**
     * Constructor responsible for the creation of a new result from a list of
     * patterns
     *
     * @param patternListMap HashMap of patterns
     */
    public Result(HashMap<String, Pattern> patternListMap) {
        setPatternListMap(patternListMap);
        setPatterns(patternListMap.values());
    }

    /**
     * Method used to get the patterns from this result
     *
     * @return ArrayList with the patterns
     */
    public ArrayList<Pattern> getPatterns() {
        return patterns;
    }

    /**
     * Method used to initialize a list of patterns
     *
     * @param patterns Collection with the patterns
     */
    public final void setPatterns(Collection<Pattern> patterns) {
        this.patterns = new ArrayList<>(patterns);
    }

    /**
     * Get the hash map of patterns
     *
     * @return Hash map of patterns
     */
    public HashMap<String, Pattern> getPatternListMap() {
        return patternListMap;
    }

    /**
     * Method used to initialize a hash map of patterns
     *
     * @param patternListMap Hash map with patterns
     */
    public void setPatternListMap(HashMap<String, Pattern> patternListMap) {
        this.patternListMap = patternListMap;
    }

    /**
     * Method responsible for filtering an existing list of patterns and get all
     * the patterns that are between the minimum and maximum number provided
     *
     * @param list List of pattern numbers
     * @return ArrayList with the patterns found
     */
    public ArrayList<Pattern> getPatternByNumbers(ArrayList<Integer> list) {
        ArrayList<Pattern> tempPatternList = new ArrayList<>();
        if (list.isEmpty()) {
            return tempPatternList;
        }
        Collections.sort(list);
        if (list.get(list.size() - 1) > patterns.size() - 1) {
            return tempPatternList;
        }
        for (Integer number : list) {
            tempPatternList.add(patterns.get(number));
        }
        return tempPatternList;
    }

    /**
     * Method responsible for filtering an existing list of patterns and get all
     * the patterns that the number of students is equal to the number provided
     *
     * @param number The exact number of students that a pattern must have
     * @return ArrayList with the patterns found
     */
    public ArrayList<Pattern> getPatternByNumberOfStudents(int number) {
        ArrayList<Pattern> tempPatternList = new ArrayList<>();
        if (number <= 0) {
            return tempPatternList;
        }
        for (int i = 0; i < patterns.size(); i++) {
            if (patterns.get(i).getStudentList().size() == number) {
                tempPatternList.add(patterns.get(i));
            }
        }
        return tempPatternList;
    }

    /**
     * Method responsible for filtering an existing list of patterns and get all
     * the patterns that the number of common units are more of less than the
     * number provided
     *
     * @param number Integer with the number of units to consider
     * @param more Boolean if true search for more, if false search for less
     * @return ArrayList with the patterns found
     */
    public ArrayList<Pattern> getPatternByCommonUnits(int number, boolean more) {
        ArrayList<Pattern> tempPatternList = new ArrayList<>();
        if (number < 0) {
            return tempPatternList;
        }
        for (Pattern pattern : patterns) {
            if (more && pattern.getPatternSize() >= number) {
                tempPatternList.add(pattern);
            } else if (!more && pattern.getPatternSize() <= number) {
                tempPatternList.add(pattern);
            }
        }
        return tempPatternList;
    }

    /**
     * Method responsible for filtering an existing list of patterns and get all
     * the patterns that the number of common students are more or less than the
     * number provided
     *
     * @param number Integer with the number of students to consider
     * @param more Boolean if true search for more, if false search for less
     * @return ArrayList with the patterns found
     */
    public ArrayList<Pattern> getPatternByCommonStudents(int number, boolean more) {
        ArrayList<Pattern> tempPatternList = new ArrayList<>();
        if (number < 0) {
            return tempPatternList;
        }
        for (Pattern pattern : patterns) {
            if (more && pattern.getStudentList().size() >= number) {
                tempPatternList.add(pattern);
            } else if (!more && pattern.getStudentList().size() <= number) {
                tempPatternList.add(pattern);
            }
        }
        return tempPatternList;
    }

    /**
     * Get name of search
     *
     * @return Name of search
     */
    public String getSearchName() {
        return searchName;
    }

    /**
     * Set name of search
     *
     * @param searchName Name of search
     */
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    /**
     * Get name of project
     *
     * @return Name of project
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Set project name
     *
     * @param projectName Name of project
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Get run date
     *
     * @return Run date
     */
    public Date getDate() {
        return Date;
    }

    /**
     * Set run date
     *
     * @param Date Run date
     */
    public void setDate(Date Date) {
        this.Date = Date;
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
     * Set list of units
     *
     * @param unitList List of units
     */
    public void setUnitList(ArrayList<Unit> unitList) {
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
     * Set list of students
     *
     * @param studentList List of students
     */
    public void setStudentList(ArrayList<Student> studentList) {
        this.studentList = studentList;
    }

    /**
     * Get minimum number of students on pattern
     *
     * @return Minimum number of students on pattern
     */
    public int getStudentsMinNumber() {
        return studentsMinNumber;
    }

    /**
     * Set minimum number of students on pattern
     *
     * @param studentsMinNumber Minimum number of students
     */
    public void setStudentsMinNumber(int studentsMinNumber) {
        this.studentsMinNumber = studentsMinNumber;
    }

    /**
     * Get minimum number of common units on pattern
     *
     * @return Minimum number of common units on pattern
     */
    public int getUnitsMinNumber() {
        return unitsMinNumber;
    }

    /**
     * Set minimum number of common units on pattern
     *
     * @param unitsMinNumber Minimum number of common units on pattern
     */
    public void setUnitsMinNumber(int unitsMinNumber) {
        this.unitsMinNumber = unitsMinNumber;
    }

    /**
     * Get runtime
     *
     * @return runtime
     */
    public long getRunTime() {
        return runTime;
    }

    /**
     * Set runtime
     *
     * @param runTime runtime
     */
    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    /**
     * Calculate average and standard deviation of students’ grades in a pattern
     * by unit
     *
     * @param pat Pattern
     * @param scale Grade scale
     * @return List of strings with results
     * @throws Exception If scale is null or if pattern was not found on
     * patternListMap
     */
    public ArrayList<String> getAverageStdByUnit(int[] pat, HashMap<String, Double> scale) throws Exception {
        try {
            ArrayList<String> results = new ArrayList<>();
            if (scale == null) {
                throw new Exception("Scale is null.");
            }
            Pattern p = patternListMap.get(Arrays.toString(pat));
            if (p == null) {
                throw new Exception("Pattern doesn't exist.");
            }
            ArrayList<Unit> units = p.getUnitList();
            if (units == null) {
                throw new Exception("Units list is null.");
            }
            Collections.sort(units);
            for (Unit unit : units) {
                List<Double> marks = new LinkedList<>();
                ArrayList<Student> students = p.getStudentList();
                if (students == null) {
                    throw new Exception("Students list is null.");
                }
                for (Student stu : students) {
                    if (stu.getStudentInfoListMap().get(unit) != null) {
                        Object mark = scale.get(stu.getStudentInfoListMap().get(unit).getMark());
                        if (mark == null) {
                            throw new Exception("Mark doesn't exist on scale");
                        }
                        if ((double) mark != 0) {
                            marks.add((double) mark);
                        }
                    }
                }
                if (marks.size() > 0) {
                    results.add(String.format("Unit: %s Average: %.2f Std: %.2f", unit.getId(), Stats.getMean(marks), Stats.getStdDev(marks)));
                }
            }
            return results;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Calculate Average and standard deviation of students’ number of years to
     * graduate included in a pattern
     *
     * @param pat Pattern
     * @return String with result
     * @throws Exception
     */
    public String getAvgStdYearsGraduate(int[] pat, int equivalenceYear) throws Exception {
        try {
            String result = "";
            Pattern p = patternListMap.get(Arrays.toString(pat));
            if (p == null) {
                throw new Exception("Pattern doesn't exist.");
            }

            List<Double> marks = new LinkedList<>();
            ArrayList<Student> students = p.getStudentList();
            if (students == null) {
                throw new Exception("Students list is null.");
            }
            for (Student stu : students) {
                int currentYear = 0;
                int maxYear = 0;
                HashMap<Unit, StudentInfo> studentInfoMap = stu.getStudentInfoListMap();
                if (studentInfoMap == null) {
                    throw new Exception("Student Info Map is null.");
                }
                for (Unit unit : studentInfoMap.keySet()) {
                    currentYear = studentInfoMap.get(unit).getYear();
                    if (currentYear != equivalenceYear && currentYear > maxYear) {
                        maxYear = currentYear;
                    }
                }
                marks.add((double) maxYear);
            }
            result = String.format("Average: %.2f Std: %.2f", Stats.getMean(marks), Stats.getStdDev(marks));
            return result;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Average and standard deviation of students’ grades in a pattern By
     * programme’s average grade (weighted average based on ECTS credits)
     *
     * @param pat Pattern
     * @param indexEctsColumn Index of column that contains ECTS
     * @param scale Scale
     * @return List of strings with results
     * @throws Exception If pattern doesn't exist
     */
    public String getAvgStdGrade(int[] pat, int indexEctsColumn, HashMap<String, Double> scale) throws Exception {
        try {
            Pattern p = patternListMap.get(Arrays.toString(pat));
            if (p == null) {
                throw new Exception("Pattern doesn't exist.");
            }

            List<Double> marks;
            List<Double> weights;
            List<Double> means = new LinkedList<>();
            ArrayList<Student> students = p.getStudentList();
            if (students == null) {
                throw new Exception("Students list is null.");
            }
            for (Student stu : students) {
                marks = new LinkedList<>();
                weights = new LinkedList<>();
                HashMap<Unit, StudentInfo> studentInfoMap = stu.getStudentInfoListMap();
                if (studentInfoMap == null) {
                    throw new Exception("Student Info Map is null.");
                }
                for (Unit unit : studentInfoMap.keySet()) {
                    if (studentInfoMap.get(unit) != null) {
                        Object mark = scale.get(studentInfoMap.get(unit).getMark());
                        if (mark == null) {
                            throw new Exception("Mark doesn't exist on scale!");
                        }
                        if ((double) mark != 0) {
                            marks.add((double) mark);
                            weights.add(Double.parseDouble(unit.getExtraColumnsValues()[indexEctsColumn]));
                        }
                    }
                }
                double mean = Stats.getWeightedMean(weights, marks);
                means.add(mean);
            }
            return String.format("Average: %.2f Std: %.2f%n", Stats.getMean(means), Stats.getStdDev(means));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}