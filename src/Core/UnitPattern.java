package Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class is responsible for creating unique patterns of unit and number of
 * years to complete the unit.
 *
 * @author Group 44 LAPR2
 */
public class UnitPattern implements Serializable {

    private int[] pattern;

    /**
     * Create a unit pattern by reading info of student (StudentInfo)
     *
     * @param student Student
     * @param unitList List of units
     * @throws Exception If unitList is null
     */
    public UnitPattern(Student student, ArrayList<Unit> unitList) throws Exception {
        if (unitList == null) {
            throw new Exception("The list of units is empty");
        }
        pattern = new int[unitList.size()];
        HashMap<Unit, Student.StudentInfo> studentInfoListMap = student.getStudentInfoListMap();
        int count = 0;
        for (Unit unit : unitList) {
            Student.StudentInfo tempInfo = studentInfoListMap.get(unit);
            if (tempInfo != null) {
                pattern[count] = tempInfo.getYear();
            }
            count++;
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
     * Define the unit pattern
     *
     * @param pattern Unit pattern
     */
    public final void setPattern(int[] pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "UnitPattern{" + "pattern=" + Arrays.toString(pattern) + '}';
    }
}