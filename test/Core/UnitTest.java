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
 * @author 1NA Grupo44
 */
public class UnitTest {

    ArrayList<Unit> units;
    Unit u1;
    Unit u2;

    public UnitTest() throws Exception {
        u1 = new Unit("Algebra Linear e Geometria Analitica", "ALGAN", 1, "1", new String[]{"5"}, new String[]{""});
        u2 = new Unit("Algoritmia Avancada", "ALGAV", 3, "1", new String[]{"5"}, new String[]{""});
        units = new ArrayList<>();
        units.add(u1);
        units.add(u2);
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
     * Test of getUnit method, of class Unit.
     */
    @Test
    public void testGetUnit() throws Exception {
        System.out.println("getUnit");
        try {
            String uID = "ALGAV";
            String expResult = "ALGAV";
            String result = Unit.getUnit(units, uID).getId();
            assertEquals(expResult, result);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Test of toString method, of class Unit.
     */
    @Test
    public void testToString() throws Exception {
        System.out.println("toString");
        Unit instance = u1;
        String expResult = "Unit{name=Algebra Linear e Geometria Analitica, id=ALGAN, year=1, semester=1, extraColumnsValues=[5]}";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}