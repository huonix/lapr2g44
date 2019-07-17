package Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class is responsible for create a Pattern. One pattern have an unit
 * pattern and one list with students who belong to the pattern
 *
 * @author Group 44 LAPR2
 */
public final class Pattern implements Serializable {

    private int[] pattern;
    private ArrayList<Student> studentList = new ArrayList<>();
    private ArrayList<Unit> unitList = new ArrayList<>();
    private int patternSize;

    /**
     * Create a pattern
     *
     * @param pattern Unit Pattern
     * @param stu Student
     * @throws Exception If stu is null
     */
    public Pattern(int[] pattern, Student stu, ArrayList<Unit> unitList) throws Exception {
        if (stu == null) {
            throw new Exception("Student is null.");
        }
        setPattern(pattern);
        setUnitList(unitList);
        addStudent(stu);
        setPatternSize(pattern.length);
    }

    /**
     * Create a pattern
     *
     * @param pattern Unit Pattern
     * @param stuList List of students
     * @throws Exception If stuList is null
     */
    public Pattern(int[] pattern, ArrayList<Student> stuList, ArrayList<Unit> unitList) throws Exception {
        if (stuList == null) {
            throw new Exception("Student list is null");
        }
        setPattern(pattern);
        setUnitList(unitList);
        addStudent(stuList);
    }

    /**
     * Checks if the unit pattern passed by parameter have the minimum number of
     * elements in common with the actual unit pattern
     *
     * @param newPatt Unique Pattern
     * @param minElements Minimum number of elements in common
     * @return True if the unit pattern have minimum number of elements or false
     * if the unit pattern hasn't minimum number of elements
     */
    public boolean hasMinElements(int[] newPatt, int minElements) {
        int count = 0;
        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i] == newPatt[i]) {
                count++;
            } else {
                pattern[i] = 0;
            }
        }
        if (count >= minElements) {
            patternSize = count;
            return true;
        }
        return false;
    }

    /**
     * Add a student to unit pattern
     *
     * @param stu Student
     * @throws Exception If stu is null
     */
    public final void addStudent(Student stu) throws Exception {
        if (stu == null) {
            throw new Exception("Student is null.");
        }
        if (!studentList.contains(stu)) {
            studentList.add(stu);
        }
    }

    /**
     * Add student to unit pattern
     *
     * @param stuList List of students
     * @throws Exception If stuList is null
     */
    public final void addStudent(ArrayList<Student> stuList) throws Exception {
        if (stuList == null) {
            throw new Exception("Student list is null.");
        }
        for (Student stu : stuList) {
            if (!studentList.contains(stu)) {
                studentList.add(stu);
            }
        }
    }

    /**
     * Get the unit pattern
     *
     * @return Unit pattern
     */
    public int[] getPattern() {
        return pattern;
    }

    /**
     * Set unit pattern
     *
     * @param pattern Unit pattern
     */
    public final void setPattern(int[] pattern) {
        this.pattern = pattern;
    }

    /**
     * Get student list who belongs to this unit pattern
     *
     * @return List of students
     */
    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    /**
     * Set student list who belongs to this unit pattern
     *
     * @param studentList List of students
     * @throws Exception If studentList is null
     */
    public final void setStudentList(ArrayList<Student> studentList) throws Exception {
        if (studentList == null) {
            throw new Exception("Student list is null.");
        }
        this.studentList = studentList;
    }

    /**
     * Get the size of unit pattern
     *
     * @return Size of unit pattern
     */
    public int getPatternSize() {
        return patternSize;
    }

    /**
     * Set size of unit pattern
     *
     * @param patternSize Size of unit pattern
     * @throws Exception If unit pattern size is zero or less
     */
    public final void setPatternSize(int patternSize) throws Exception {
        if (patternSize <= 0) {
            throw new Exception("The unit pattern size must be greater than zero.");
        }
        this.patternSize = patternSize;
    }

    /**
     * Check if a unit pattern exist on hashmap, if exits add student to unit
     * pattern
     *
     * @param patternListMap List of unit patterns
     * @return True if unit pattern is on list or false if unit pattern isn't on
     * list
     * @throws Exception If patternListMap is null
     */
    public boolean existInPatternList(HashMap<String, Pattern> patternListMap) throws Exception {
        if (patternListMap == null) {
            throw new Exception("Unit pattern list is null.");
        }
        Pattern newPattern = patternListMap.get(Arrays.toString(pattern));
        if (newPattern != null) {
            newPattern.addStudent(studentList);
            return true;
        }
        return false;
    }

    /**
     * Remove from unit pattern list, the unit pattern, the unit patterns that
     * have fewer than number passed by parameter
     *
     * @param patternListMap List of unit patterns
     * @param minStudents Minimum number of students
     * @throws Exception If unit pattern list is null or minStudents is 0 or
     * less
     */
    public static void removeStudents(HashMap<String, Pattern> patternListMap, int minStudents) throws Exception {
        if (patternListMap == null) {
            throw new Exception("Empty unit pattern list.");
        }
        if (minStudents <= 0) {
            throw new Exception("The number of students must be greater than zero.");
        }

        ArrayList<Pattern> newList = new ArrayList<>();
        for (Pattern newPatt : patternListMap.values()) {
            if (newPatt.getStudentList().size() < minStudents) {
                newList.add(newPatt);
            }
        }
        patternListMap.values().removeAll(newList);
    }

    /**
     * Method used to get the list of units in the pattern
     *
     * @return ArrayList of units
     */
    public ArrayList<Unit> getUnitList() {
        return unitList;
    }

    /**
     * Method used to set the list of units to the pattern
     *
     * @param unitList ArrayList of units
     */
    public void setUnitList(ArrayList<Unit> unitList) {
        this.unitList = (unitList != null) ? unitList : new ArrayList<Unit>();
    }

    @Override
    public String toString() {
        return "Pattern{" + "pattern=" + Arrays.toString(pattern) + ", studentList=" + studentList + ", patternSize=" + patternSize + '}';
    }
}