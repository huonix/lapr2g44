package Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class with unitary tests
 *
 * @author Group 44 LAPR2
 */
public class PatternTest {

    Pattern p1;
    Pattern p2;
    Student s1;
    Unit u1;
    Unit u2;
    ArrayList<Student> studentList;
    ArrayList<Unit> unitList;

    public PatternTest() throws Exception {
        u1 = new Unit("Algebra Linear e Geometria Analitica", "ALGAN", 1, "1", new String[]{"5"}, new String[]{""});
        u2 = new Unit("Analise Matematica", "AMATA", 1, "1", new String[]{"5"}, new String[]{""});

        unitList = new ArrayList<>();
        unitList.add(u1);
        unitList.add(u2);

        s1 = new Student(1, u1, 1, "10");
        s1.addInfo(u2, 2, "10");

        studentList = new ArrayList<>();
        studentList.add(s1);
        p1 = new Pattern(new int[]{1, 2}, s1, unitList);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of hasMinElements method, of class Pattern.
     */
    @Test
    public void testHasMinElements() {
        System.out.println("hasMinElements");
        int[] newPatt = new int[]{1, 1};
        int minElements = 1;
        Pattern instance = p1;
        boolean expResult = true;
        boolean result = instance.hasMinElements(newPatt, minElements);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPattern method, of class Pattern.
     */
    @Test
    public void testGetPattern() {
        System.out.println("getPattern");
        Pattern instance = p1;
        int[] expResult = new int[]{1, 2};
        int[] result = instance.getPattern();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getStudentList method, of class Pattern.
     */
    @Test
    public void testGetStudentList() {
        System.out.println("getStudentList");
        Pattern instance = p1;
        ArrayList expResult = studentList;
        ArrayList result = instance.getStudentList();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPatternSize method, of class Pattern.
     */
    @Test
    public void testGetPatternSize() {
        System.out.println("getPatternSize");
        Pattern instance = p1;
        int expResult = 2;
        int result = instance.getPatternSize();
        assertEquals(expResult, result);
    }

    /**
     * Test of existInPatternList method, of class Pattern.
     */
    @Test
    public void testExistInPatternList() throws Exception {
        System.out.println("existInPatternList");
        HashMap<String, Pattern> patternListMap = new HashMap<>();
        patternListMap.put(Arrays.toString(p1.getPattern()), p1);
        Pattern instance = p1;
        boolean expResult = true;
        boolean result = instance.existInPatternList(patternListMap);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeStudents method, of class Pattern.
     */
    @Test
    public void testRemoveStudents() throws Exception {
        System.out.println("removeStudents");
        HashMap<String, Pattern> patternListMap = new HashMap<>();
        patternListMap.put(Arrays.toString(p1.getPattern()), p1);
        int minStudents = 1;
        Pattern.removeStudents(patternListMap, minStudents);
        int expResult = 1;
        assertEquals(expResult, 1);

    }
}