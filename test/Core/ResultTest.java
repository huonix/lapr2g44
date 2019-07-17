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
public class ResultTest {

    Search s;
    Result r;
    Unit u1, u2, u3, u4, u5, u6, u7, u8, u9, u10;
    ArrayList<Unit> unitList = new ArrayList<>();
    Student s1, s2, s3, s4, s5, s6, s7;
    ArrayList<Student> studentList = new ArrayList<>();

    public ResultTest() throws Exception {
        u1 = new Unit("Algebra Linear e Geometria Analitica", "ALGAN", 1, "1", new String[]{"5"}, new String[]{""});
        u2 = new Unit("Analise Matematica", "AMATA", 1, "1", new String[]{"5"}, new String[]{""});
        u3 = new Unit("Algoritmia e Prohgramacao", "APROG", 1, "1", new String[]{"6"}, new String[]{""});
        u4 = new Unit("Principios da Computacao", "PRCMP", 1, "1", new String[]{"6"}, new String[]{""});
        u5 = new Unit("Laboratorio / Projecto I", "LAPR1", 1, "1", new String[]{"8"}, new String[]{""});
        u6 = new Unit("Arquitectura de Computadores", "ARQCP", 2, "1", new String[]{"5"}, new String[]{""});
        u7 = new Unit("Bases de Dados", "BDDAD", 2, "1", new String[]{"6"}, new String[]{""});
        u8 = new Unit("Estruturas de Informacao", "ESINF", 2, "1", new String[]{"6"}, new String[]{""});
        u9 = new Unit("Fisica Aplicada", "FSIAP", 2, "1", new String[]{"5"}, new String[]{""});
        u10 = new Unit("Laboratorio / Projecto III", "LAPR3", 2, "1", new String[]{"8"}, new String[]{""});
        unitList = new ArrayList<>();
        unitList.add(u1);
        unitList.add(u2);
        unitList.add(u3);
        unitList.add(u4);
        unitList.add(u5);
        unitList.add(u6);
        unitList.add(u7);
        unitList.add(u8);
        unitList.add(u9);
        unitList.add(u10);

        s1 = new Student(1, u1, 1, "12");
        s1.addInfo(u2, 2, "10");
        s1.addInfo(u3, 1, "10");
        s1.addInfo(u4, 1, "10");
        s1.addInfo(u5, 1, "10");
        s1.addInfo(u6, 2, "11");
        s1.addInfo(u7, 2, "12");
        s1.addInfo(u8, 2, "13");
        s1.addInfo(u9, 3, "15");
        s1.addInfo(u10, 2, "11");

        s2 = new Student(2, u1, 1, "12");
        s2.addInfo(u2, 2, "10");
        s2.addInfo(u3, 1, "11");
        s2.addInfo(u4, 1, "13");
        s2.addInfo(u5, 1, "15");
        s2.addInfo(u6, 2, "16");
        s2.addInfo(u7, 2, "14");
        s2.addInfo(u8, 2, "10");
        s2.addInfo(u9, 2, "11");
        s2.addInfo(u10, 2, "11");

        s3 = new Student(3, u1, 1, "12");
        s3.addInfo(u2, 1, "16");
        s3.addInfo(u3, 2, "13");
        s3.addInfo(u4, 1, "14");
        s3.addInfo(u5, 1, "15");
        s3.addInfo(u6, 2, "16");
        s3.addInfo(u7, 2, "12");
        s3.addInfo(u8, 3, "13");
        s3.addInfo(u9, 2, "14");
        s3.addInfo(u10, 3, "15");

        s4 = new Student(4, u1, 1, "12");
        s4.addInfo(u2, 1, "12");
        s4.addInfo(u3, 1, "13");
        s4.addInfo(u4, 1, "15");
        s4.addInfo(u5, 1, "14");
        s4.addInfo(u6, 2, "13");
        s4.addInfo(u7, 2, "14");
        s4.addInfo(u8, 2, "12");
        s4.addInfo(u9, 2, "14");
        s4.addInfo(u10, 2, "15");

        s5 = new Student(5, u1, 1, "12");
        s5.addInfo(u2, 1, "12");
        s5.addInfo(u3, 1, "12");
        s5.addInfo(u4, 1, "13");
        s5.addInfo(u5, 1, "14");
        s5.addInfo(u6, 2, "14");
        s5.addInfo(u7, 2, "14");
        s5.addInfo(u8, 2, "15");
        s5.addInfo(u9, 3, "13");
        s5.addInfo(u10, 2, "12");

        s6 = new Student(6, u1, 1, "12");
        s6.addInfo(u2, 1, "12");
        s6.addInfo(u3, 1, "14");
        s6.addInfo(u4, 1, "15");
        s6.addInfo(u5, 1, "14");
        s6.addInfo(u6, 3, "13");
        s6.addInfo(u7, 2, "15");
        s6.addInfo(u8, 2, "14");
        s6.addInfo(u9, 2, "13");
        s6.addInfo(u10, 2, "14");

        s7 = new Student(7, u1, 1, "12");
        s7.addInfo(u2, 2, "14");
        s7.addInfo(u3, 1, "14");
        s7.addInfo(u4, 1, "14");
        s7.addInfo(u5, 1, "13");
        s7.addInfo(u6, 3, "13");
        s7.addInfo(u7, 3, "13");
        s7.addInfo(u8, 3, "12");
        s7.addInfo(u9, 2, "13");
        s7.addInfo(u10, 3, "14");

        studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);
        studentList.add(s4);
        studentList.add(s5);
        studentList.add(s6);
        studentList.add(s7);

        s = new Search("New Project 01", "New Search", studentList, unitList, 8, 2);
        s.runSearch();
        r = s.getResult().get(0);
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
     * Test of getPatternByNumbers method, of class Result.
     */
    @Test
    public void testGetPatternByNumbers() throws Exception {
        System.out.println("getPatternByNumbers");
        ArrayList<Integer> arrayInt = new ArrayList<>();
        arrayInt.add(1);
        arrayInt.add(2);
        arrayInt.add(3);
        arrayInt.add(4);
        arrayInt.add(5);
        arrayInt.add(6);
        arrayInt.add(7);

        String result = "";
        String expResult = "[1, 1, 1, 1, 1, 0, 2, 2, 0, 2]";
        Result instance = r;
        ArrayList<Pattern> resultTemp = instance.getPatternByNumbers(arrayInt);
        for (Pattern pattern : resultTemp) {
            result = Arrays.toString(pattern.getPattern());
        }
        assertEquals(expResult, result);

    }

    /**
     * Test of getPatternByNumberOfStudents method, of class Result.
     */
    @Test
    public void testGetPatternByNumberOfStudents() {
        System.out.println("getPatternByNumberOfStudents");
        int number = 4;
        Result instance = r;
        ArrayList<Pattern> result = instance.getPatternByNumberOfStudents(number);
        assertEquals(1, result.size());
    }

    /**
     * Test of getPatternByCommonUnits method, of class Result.
     */
    @Test
    public void testGetPatternByCommonUnits() {
        System.out.println("getPatternByCommonUnits");
        int number = 8;
        boolean more = true;
        Result instance = r;
        ArrayList<Pattern> result = instance.getPatternByCommonUnits(number, more);
        assertEquals(8, result.size());
    }

    /**
     * Test of getPatternByCommonStudents method, of class Result.
     */
    @Test
    public void testGetPatternByCommonStudents() {
        System.out.println("getPatternByCommonStudents");
        int number = 3;
        boolean more = true;
        Result instance = r;
        ArrayList<Pattern> result = instance.getPatternByCommonStudents(number, more);
        assertEquals(3, result.size());
    }

    /**
     * Test of getAverageStdByUnit method, of class Result.
     */
    @Test
    public void testGetAverageStdByUnit() throws Exception {
        System.out.println("getAverageStdByUnit");
        int[] pat = new int[]{1, 0, 1, 1, 1, 2, 2, 2, 0, 2};
        HashMap<String, Double> scale = new HashMap<>();
        scale.put("10", 10.0);
        scale.put("11", 11.0);
        scale.put("12", 12.0);
        scale.put("13", 13.0);
        scale.put("14", 14.0);
        scale.put("15", 15.0);
        scale.put("16", 16.0);
        scale.put("17", 17.0);
        scale.put("18", 18.0);
        scale.put("19", 19.0);
        scale.put("20", 20.0);

        Result instance = r;
        String expResult = "Unit: AMATA Average: 11,00 Std: 1,00";
        ArrayList<String> result = instance.getAverageStdByUnit(pat, scale);
        assertEquals(expResult, result.get(1).toString().trim());
    }
}