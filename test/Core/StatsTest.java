package Core;

import java.util.LinkedList;
import java.util.List;
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
public class StatsTest {

    private List<Double> values = new LinkedList<>();
    private List<Double> weights = new LinkedList<>();
    private List<Double> data = new LinkedList<>();

    public StatsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        values.add(2.0);
        values.add(5.0);

        data.add(10.0);
        data.add(8.0);
        data.add(5.0);
        data.add(4.0);
        data.add(3.0);
        data.add(2.0);
        data.add(1.0);
        data.add(0.0);

        weights.add(2.0);
        weights.add(2.0);
        weights.add(1.0);
        weights.add(10.0);
        weights.add(8.0);
        weights.add(7.0);
        weights.add(68.0);
        weights.add(2.0);
    }

    @After
    public void tearDown() {
        values.clear();
    }

    /**
     * Test of getMean method, of class Stats.
     */
    @Test
    public void testGetMean_List() throws Exception {
        System.out.println("getMean");
        double expResult = 3.5;
        double result = Stats.getMean(values);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getStdDev method, of class Stats.
     */
    @Test
    public void testGetStdDev_List() throws Exception {
        System.out.println("getStdDev");
        double expResult = 1.5;
        double result = Stats.getStdDev(values);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getWeightedMean method, of class Stats.
     */
    @Test
    public void testGetWeightedMean() throws Exception {
        System.out.println("getWeightedMean");
        double expResult = 1.87;
        double result = Stats.getWeightedMean(weights, data);
        assertEquals(expResult, result, 0.01);
    }

    /**
     * Test of getWeightedStd method, of class Stats.
     */
    @Test
    public void testGetWeightedStd() throws Exception {
        System.out.println("getWeightedStd");
        double expResult = 1.9;
        double result = Stats.getWeightedStd(weights, data);
        assertEquals(expResult, result, 0.1);
    }
}