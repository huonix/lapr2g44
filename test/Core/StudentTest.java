package Core;

import java.util.ArrayList;
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
public class StudentTest {

    ArrayList<Student> studentList;
    Unit u1;
    Unit u2;
    Student s1;

    public StudentTest() throws Exception {
        studentList = new ArrayList<>();
        u1 = new Unit("Algebra Linear e Geometria Analitica", "ALGAN", 1, "1", new String[]{"5"}, new String[]{""});
        u2 = new Unit("Algoritmia Avancada", "ALGAV", 3, "1", new String[]{"5"}, new String[]{""});
        s1 = new Student(496, u1, 99, "12");
        studentList.add(s1);
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
     * Test of getNumber method, of class Student.
     */
    @Test
    public void testGetNumber() {
        System.out.println("getNumber");
        Student instance = s1;
        int expResult = 496;
        int result = instance.getNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of addInfo method, of class Student.
     */
    @Test
    public void testAddInfo() throws Exception {
        System.out.println("addInfo");
        Unit unit = u2;
        int year = 3;
        String mark = "12";
        Student instance = s1;
        instance.addInfo(unit, year, mark);
        assertNotNull(instance.getStudentInfoListMap().get(unit));
    }

    /**
     * Test of getUnitPattern method, of class Student.
     */
    @Test
    public void testGetUnitPattern() throws Exception {
        System.out.println("getUnitPattern");
        ArrayList<Unit> unitList = new ArrayList<>();
        unitList.add(u1);
        Student instance = s1;
        String expResult = "UnitPattern{pattern=[99]}";
        String result = instance.getUnitPattern(unitList).toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStudent method, of class Student.
     */
    @Test
    public void testGetStudent() throws Exception {
        System.out.println("getStudent");
        int numStudent = 496;
        Student result = Student.getStudent(studentList, numStudent);
        assertNotNull(result);
    }

    /**
     * Test of getStudentInfoListMap method, of class Student.
     */
    @Test
    public void testGetStudentInfoListMap() {
        System.out.println("getStudentInfoListMap");
        Student instance = s1;
        String expResult = "{Unit{name=Algebra Linear e Geometria Analitica, id=ALGAN, year=1, semester=1, extraColumnsValues=[5]}=StudentInfo{unit=Unit{name=Algebra Linear e Geometria Analitica, id=ALGAN, year=1, semester=1, extraColumnsValues=[5]}, year=99, mark=12}}";
        String result = instance.getStudentInfoListMap().toString();
        assertEquals(expResult, result);
    }
}