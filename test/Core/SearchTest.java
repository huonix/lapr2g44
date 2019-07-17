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
public class SearchTest {

    Search s;
    Unit u1, u2, u3, u4, u5, u6, u7, u8, u9, u10;
    ArrayList<Unit> unitList = new ArrayList<>();
    Student s1, s2, s3, s4, s5, s6, s7;
    ArrayList<Student> studentList = new ArrayList<>();

    public SearchTest() throws Exception {
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
        s1.addInfo(u2, 2, "1");
        s1.addInfo(u3, 1, "1");
        s1.addInfo(u4, 1, "1");
        s1.addInfo(u5, 1, "1");
        s1.addInfo(u6, 2, "1");
        s1.addInfo(u7, 2, "1");
        s1.addInfo(u8, 2, "1");
        s1.addInfo(u9, 3, "1");
        s1.addInfo(u10, 2, "1");

        s2 = new Student(2, u1, 1, "12");
        s2.addInfo(u2, 2, "1");
        s2.addInfo(u3, 1, "1");
        s2.addInfo(u4, 1, "1");
        s2.addInfo(u5, 1, "1");
        s2.addInfo(u6, 2, "1");
        s2.addInfo(u7, 2, "1");
        s2.addInfo(u8, 2, "1");
        s2.addInfo(u9, 2, "1");
        s2.addInfo(u10, 2, "1");

        s3 = new Student(3, u1, 1, "12");
        s3.addInfo(u2, 1, "1");
        s3.addInfo(u3, 2, "1");
        s3.addInfo(u4, 1, "1");
        s3.addInfo(u5, 1, "1");
        s3.addInfo(u6, 2, "1");
        s3.addInfo(u7, 2, "1");
        s3.addInfo(u8, 3, "1");
        s3.addInfo(u9, 2, "1");
        s3.addInfo(u10, 3, "1");

        s4 = new Student(4, u1, 1, "12");
        s4.addInfo(u2, 1, "1");
        s4.addInfo(u3, 1, "1");
        s4.addInfo(u4, 1, "1");
        s4.addInfo(u5, 1, "1");
        s4.addInfo(u6, 2, "1");
        s4.addInfo(u7, 2, "1");
        s4.addInfo(u8, 2, "1");
        s4.addInfo(u9, 2, "1");
        s4.addInfo(u10, 2, "1");

        s5 = new Student(5, u1, 1, "12");
        s5.addInfo(u2, 1, "1");
        s5.addInfo(u3, 1, "1");
        s5.addInfo(u4, 1, "1");
        s5.addInfo(u5, 1, "1");
        s5.addInfo(u6, 2, "1");
        s5.addInfo(u7, 2, "1");
        s5.addInfo(u8, 2, "1");
        s5.addInfo(u9, 3, "1");
        s5.addInfo(u10, 2, "1");

        s6 = new Student(6, u1, 1, "12");
        s6.addInfo(u2, 1, "1");
        s6.addInfo(u3, 1, "1");
        s6.addInfo(u4, 1, "1");
        s6.addInfo(u5, 1, "1");
        s6.addInfo(u6, 3, "1");
        s6.addInfo(u7, 2, "1");
        s6.addInfo(u8, 2, "1");
        s6.addInfo(u9, 2, "1");
        s6.addInfo(u10, 2, "1");

        s7 = new Student(7, u1, 1, "12");
        s7.addInfo(u2, 2, "1");
        s7.addInfo(u3, 1, "1");
        s7.addInfo(u4, 1, "1");
        s7.addInfo(u5, 1, "1");
        s7.addInfo(u6, 3, "1");
        s7.addInfo(u7, 3, "1");
        s7.addInfo(u8, 3, "1");
        s7.addInfo(u9, 2, "1");
        s7.addInfo(u10, 3, "1");

        studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);
        studentList.add(s4);
        studentList.add(s5);
        studentList.add(s6);
        studentList.add(s7);

        s = new Search("Project 01", "New Search", studentList, unitList, 5, 5);
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
     * Test of runSearch method, of class Search.
     */
    @Test
    public void testRunSearch() throws Exception {
        System.out.println("runSearch");
        Search instance = s;
        instance.runSearch();
        String result = "";
        for (Pattern patt : s.getPatternListMap().values()) {
            String students = "";
            int num = patt.getStudentList().size();
            for (int i = 0; i < num; i++) {
                students += patt.getStudentList().get(i).getNumber();
                if (i != num - 1) {
                    students += " | ";
                }
            }
            result += "{" + Arrays.toString(patt.getPattern()) + "; size => " + patt.getPatternSize() + "; numStudents => " + patt.getStudentList().size() + "; students => " + students + "} ";
        }
        result += " numPatterns => " + s.getUnitUniquePatterns().size();
        String expResult = "{[1, 0, 0, 1, 1, 2, 2, 0, 0, 0]; size => 5; numStudents => 5; students => 3 | 4 | 5 | 2 | 1} {[1, 0, 1, 1, 1, 0, 2, 2, 0, 2]; size => 7; numStudents => 5; students => 6 | 4 | 5 | 2 | 1}  numPatterns => 7";
        assertEquals(expResult, result);
    }

    /**
     * Test of getUnitUniquePatterns method, of class Search.
     */
    @Test
    public void testGetUnitUniquePatterns() throws Exception {
        System.out.println("getUnitUniquePatterns");
        Search instance = s;
        String expResult = "[[1, 1, 1, 1, 1, 3, 2, 2, 2, 2], [1, 1, 1, 1, 1, 2, 2, 2, 2, 2], [1, 2, 1, 1, 1, 2, 2, 2, 3, 2], [1, 1, 2, 1, 1, 2, 2, 3, 2, 3], [1, 1, 1, 1, 1, 2, 2, 2, 3, 2], [1, 2, 1, 1, 1, 3, 3, 3, 2, 3], [1, 2, 1, 1, 1, 2, 2, 2, 2, 2]]";
        HashMap resultTemp = instance.getUnitUniquePatterns();
        String result = resultTemp.keySet().toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUnitList method, of class Search.
     */
    @Test
    public void testGetUnitList() {
        System.out.println("getUnitList");
        Search instance = s;
        String expResult = "[Unit{name=Algebra Linear e Geometria Analitica, id=ALGAN, year=1, semester=1, extraColumnsValues=[5]}, Unit{name=Analise Matematica, id=AMATA, year=1, semester=1, extraColumnsValues=[5]}, Unit{name=Algoritmia e Prohgramacao, id=APROG, year=1, semester=1, extraColumnsValues=[6]}, Unit{name=Principios da Computacao, id=PRCMP, year=1, semester=1, extraColumnsValues=[6]}, Unit{name=Laboratorio / Projecto I, id=LAPR1, year=1, semester=1, extraColumnsValues=[8]}, Unit{name=Arquitectura de Computadores, id=ARQCP, year=2, semester=1, extraColumnsValues=[5]}, Unit{name=Bases de Dados, id=BDDAD, year=2, semester=1, extraColumnsValues=[6]}, Unit{name=Estruturas de Informacao, id=ESINF, year=2, semester=1, extraColumnsValues=[6]}, Unit{name=Fisica Aplicada, id=FSIAP, year=2, semester=1, extraColumnsValues=[5]}, Unit{name=Laboratorio / Projecto III, id=LAPR3, year=2, semester=1, extraColumnsValues=[8]}]";
        String result = instance.getUnitList().toString();
        assertEquals(expResult, result);
    }

    @Test(expected = Exception.class)
    public void testException() throws Exception {
        System.out.println("Throw Exception (null parameters)");
        s = new Search("New Project 01", "New Search", null, null, 5, 5);
    }
}