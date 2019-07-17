package Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is responsible for create a Unit, with all unit information’s.
 *
 * @author Group 44 LAPR2
 */
public final class Unit implements Serializable, Comparable<Unit> {

    private String name;
    private String id;
    private int year;
    private String semester;
    private String[] headers;
    private String[] extraColumnsValues;

    /**
     * Constructor of unit
     *
     * @param name Name of unit
     * @param id Identification of unit
     * @param year Year of unit
     * @param semester Semester of unit
     * @param extraColumnsValues Values for extra columns in unit file
     * @param headers Columns headers names
     * @throws Exception If name, id, semester is null or empty and extra
     * columns is empty
     */
    public Unit(String name, String id, int year, String semester, String[] extraColumnsValues, String[] headers) throws Exception {
        setName(name);
        setId(id);
        setYear(year);
        setSemester(semester);
        setHeaders(headers);
        setExtraColumnsValues(extraColumnsValues);
    }

    /**
     * Get the name of unit
     *
     * @return Name of unit
     */
    public String getName() {
        return name;
    }

    /**
     * Set de name of unit
     *
     * @param name Name of unit
     * @throws Exception If name is null or empty
     */
    public void setName(String name) throws Exception {
        if (name == null || name.isEmpty()) {
            throw new Exception("Name of unit is null or empty.");
        }
        this.name = name;
    }

    /**
     * Get the ID of unit
     *
     * @return The id of unit
     */
    public String getId() {
        return id;
    }

    /**
     * Set the ID of unit
     *
     * @param id The id of unit
     * @throws Exception If id is null or empty
     */
    public void setId(String id) throws Exception {
        if (id == null || id.isEmpty()) {
            throw new Exception("Id of unit is null or empty.");
        }
        this.id = id;
    }

    /**
     * Get the year of unit
     *
     * @return Year of unit
     */
    public int getYear() {
        return year;
    }

    /**
     * Set the year of unit
     *
     * @param year Year of unit
     * @throws Exception If year is zero or less
     */
    public void setYear(int year) throws Exception {
        if (year <= 0) {
            throw new Exception("Bad year for unit " + this.getId());
        }
        this.year = year;
    }

    /**
     * Get the semester of unit
     *
     * @return Semester of unit
     */
    public String getSemester() {
        return semester;
    }

    /**
     * Set the semester of unit
     *
     * @param semester Semester of unit
     * @throws Exception If semester is null or empty
     */
    public void setSemester(String semester) throws Exception {
        if (semester == null || semester.isEmpty()) {
            throw new Exception("Semester of unit is null or empty.");
        }
        this.semester = semester;
    }

    /**
     * Get the values of extra columns
     *
     * @return Values of extra columns
     */
    public String[] getExtraColumnsValues() {
        return extraColumnsValues;
    }

    /**
     * Set the values of extra columns
     *
     * @param extraColumnsValues Values of extra columns
     * @throws Exception If extracolumnsValues is empty
     */
    public void setExtraColumnsValues(String[] extraColumnsValues) throws Exception {
        if (extraColumnsValues.length <= 0) {
            throw new Exception("Empty extra columns");
        }
        this.extraColumnsValues = extraColumnsValues;
    }

    /**
     * Get the headers name of columns
     *
     * @return Headers name
     */
    public String[] getHeaders() {
        return headers;
    }

    /**
     * Set the values of headers columns
     *
     * @param headers Headers name of columns
     * @throws Exception If headers names are empty
     */
    public void setHeaders(String[] headers) throws Exception {
        if (headers.length <= 0) {
            throw new Exception("Empty extra columns");
        }
        this.headers = headers;
    }

    /**
     * Get the unit (object)
     *
     * @param units List of units
     * @param uID ID of unit
     * @return Unit
     * @throws Exception If units is null or UID is null or empty
     */
    public static Unit getUnit(ArrayList<Unit> units, String uID) throws Exception {
        if (units == null) {
            throw new Exception("List of units is null.");
        }
        if (uID == null || uID.isEmpty()) {
            throw new Exception("Empty unit id");
        }
        for (Unit u : units) {
            if (u.id.equalsIgnoreCase(uID)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Unit{" + "name=" + name + ", id=" + id + ", year=" + year + ", semester=" + semester + ", extraColumnsValues=" + Arrays.toString(extraColumnsValues) + '}';
    }

    @Override
    public int compareTo(Unit o) {
        return (int) (this.id.compareToIgnoreCase(o.getId()));
    }
}