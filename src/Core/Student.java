package Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is responsible for create a Student, that contains all information
 * of him, such as, number, number of years it takes to complete an unit, etc.
 *
 * @author Group 44 LAPR2
 */
public class Student implements Serializable, Comparable<Student> {

    private int number;
    private HashMap<Unit, StudentInfo> studentInfoListMap = new HashMap<>();
    private HashMap<ArrayList<Unit>, UnitPattern> unitPatternCache = new HashMap<>();
    public static int equivalenceYear = 0;

    /**
     * Create a Student
     *
     * @param number Student Number
     * @param unit Unit
     * @param year Year that ended the unit
     * @param mark Unit mark
     * @throws Exception If Unit is null, year 0 or less and mark is null or
     * empty
     */
    public Student(int number, Unit unit, int year, String mark) throws Exception {
        setNumber(number);
        addInfo(unit, year, mark);
        addEquivalenceYear(year);
    }

    /**
     * Get number of student
     *
     * @return Number of student
     */
    public int getNumber() {
        return number;
    }

    /**
     * Set number of student
     *
     * @param number Number of student
     */
    public final void setNumber(int number) {
        this.number = number;
    }

    /**
     * Add information of student. This method creates a new StudentInfo with
     * information of unit, year and mark and put this information on hashmap
     * with unit as key
     *
     * @param unit Unit
     * @param year Year that ended the unit
     * @param mark Unit mark
     * @throws Exception If Unit is null, year 0 or less and mark is null or
     * empty
     */
    public final void addInfo(Unit unit, int year, String mark) throws Exception {
        StudentInfo info = new StudentInfo(unit, year, mark);
        studentInfoListMap.put(unit, info);
    }

    /**
     * Get unit pattern of student. This method check first if a pattern is
     * already on cache.
     *
     * @param unitList List of unit patterns
     * @return Unit pattern
     * @throws Exception if unitList is null
     */
    public UnitPattern getUnitPattern(ArrayList<Unit> unitList) throws Exception {
        if (unitList == null) {
            throw new Exception("Unit pattern list is null.");
        }
        UnitPattern unitPattern = unitPatternCache.get(unitList);
        if (unitPattern != null) {
            return unitPattern;
        }
        unitPattern = new UnitPattern(this, unitList);
        unitPatternCache.put(unitList, unitPattern);
        return unitPattern;
    }

    /**
     * Get the student from a list of students
     *
     * @param studentList List of students
     * @param numStudent Number of student
     * @return Student
     * @throws Exception If student list is null
     */
    public static Student getStudent(ArrayList<Student> studentList, int numStudent) throws Exception {
        if (studentList == null) {
            throw new Exception("Student list is null.");
        }
        if (numStudent >= 0) {
            for (Student student : studentList) {
                if (student.getNumber() == numStudent) {
                    return student;
                }
            }
        }
        return null;
    }

    /**
     * Get the information of student (Unit, years and mark)
     *
     * @return Information of student
     */
    public HashMap<Unit, StudentInfo> getStudentInfoListMap() {
        return studentInfoListMap;
    }

    /**
     * Define the information of student (Unit, years and mark)
     *
     * @param studentInfoListMap Information of student (Unit, years and mark)
     * @throws Exception If studentInfoListMap is null
     */
    public final void setStudentInfoListMap(HashMap<Unit, StudentInfo> studentInfoListMap) throws Exception {
        if (studentInfoListMap == null) {
            throw new Exception("Student info map is null.");
        }
        this.studentInfoListMap = studentInfoListMap;
    }

    /**
     * Method used to auto update the equivalence year for the project, used
     * when creating a new project
     *
     * @param year Year of equivalence
     */
    public static void addEquivalenceYear(int year) {
        if (year > equivalenceYear) {
            equivalenceYear = year;
        }
    }

    @Override
    public String toString() {
        return "Student{" + "number=" + number + ", info=" + studentInfoListMap.values().toString() + '}';
    }

    @Override
    public int compareTo(Student o) {
        return this.number - o.getNumber();
    }

    /**
     * Aux Class
     */
    public final class StudentInfo implements Serializable {

        private Unit unit;
        private int year;
        private String mark;

        /**
         * Create new info of student
         *
         * @param unit Unit
         * @param year Year that ended the unit
         * @param mark Unit mark
         */
        public StudentInfo(Unit unit, int year, String mark) throws Exception {
            setUnit(unit);
            setYear(year);
            setMark(mark);
        }

        /**
         * Get the unit
         *
         * @return The unit
         */
        public Unit getUnit() {
            return unit;
        }

        /**
         * Define the unit
         *
         * @param unit Unit
         * @throws Exception If Unit is null
         */
        public void setUnit(Unit unit) throws Exception {
            if (unit == null) {
                throw new Exception("Unit not found.");
            }
            this.unit = unit;
        }

        /**
         * Get number of years to complete the unit
         *
         * @return Number of years to complete the unit
         */
        public int getYear() {
            return year;
        }

        /**
         * Defines the number of years to complete the unit
         *
         * @param year Number of years to complete the unit
         * @throws Exception If year is zero or less
         */
        public void setYear(int year) throws Exception {
            if (year <= 0) {
                throw new Exception("Bad year for unit " + unit.getId());
            }
            this.year = year;
        }

        /**
         * Get the mark of unit
         *
         * @return mark of unit
         */
        public String getMark() {
            return mark;
        }

        /**
         * Define the mark of unit
         *
         * @param mark Mark of unit
         * @throws Exception mark is null or empty
         */
        public void setMark(String mark) throws Exception {
            if (mark == null || mark.isEmpty()) {
                throw new Exception("Bad mark for unit " + unit.getId());
            }
            this.mark = mark;
        }

        @Override
        public String toString() {
            return "StudentInfo{" + "unit=" + unit + ", year=" + year + ", mark=" + mark + '}';
        }
    }
}