package IO;

import Core.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Class for read CSV files
 *
 * @author Group 44 LAPR2
 */
public class CSV implements Serializable {

    private static final String SEPARATOR = ";";
    private static final String PATH_SEPARATOR = "\\";
    private static TreeMap<Integer, Integer> patternStudentSizes = new TreeMap<>();
    private static TreeMap<Integer, Integer> patternUnitSizes = new TreeMap<>();

    /**
     * Read a file and pass all lines to ArrayList of string
     *
     * @param filePath File path of file to read
     * @return lines on file
     * @throws FileNotFoundException If file not found
     * @throws IOException If some error happened during reading
     */
    private static ArrayList<String> readFile(String filePath) throws FileNotFoundException, IOException {
        ArrayList<String> lines = new ArrayList<>();
        try {
            File userInput = new File(filePath);
            if (userInput.isFile()) {
                try (Scanner fi = new Scanner(userInput, "ISO-8859-15")) {
                    while (fi.hasNextLine()) {
                        String currentLine = fi.nextLine();
                        if (currentLine != null && !currentLine.isEmpty()) {
                            lines.add(currentLine);
                        }
                    }
                    fi.close();
                } catch (IOException ex) {
                    throw new FileNotFoundException("An error occurred while reading the file." + ex.getMessage());
                }
            }
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("File not found. (" + filePath + ")");
        }
        return lines;
    }

    /**
     * Read a file with units and transform to ArrayList of units
     *
     * @param filePath File path of units file
     * @return List of units
     * @throws FileNotFoundException If file not found
     * @throws IOException If some error happened during reading
     * @throws Exception If read bad data
     */
    public static ArrayList<Unit> readUnitFile(String filePath) throws FileNotFoundException, IOException, Exception {
        ArrayList<Unit> units = new ArrayList<>();
        ArrayList<String> file = CSV.readFile(filePath);
        String[] lineValues;
        String[] specialColumns;
        String[] headers = file.get(0).split(SEPARATOR);

        if (!headers[0].equalsIgnoreCase("Unit Name")
                || !headers[1].equalsIgnoreCase("Unit ID")
                || !headers[2].equalsIgnoreCase("Year")
                || !headers[3].equalsIgnoreCase("Semester")) {
            throw new Exception("Wrong unit file.\nThe first 4 headers should be: Unit Name, Unit ID, Year and Semester");
        }

        for (String unit : file) {
            if (file.indexOf(unit) != 0) {
                lineValues = unit.split(SEPARATOR);
                specialColumns = new String[lineValues.length - 4];
                for (int i = 4; i < lineValues.length; i++) {
                    specialColumns[i - 4] = lineValues[i];
                }
                units.add(new Unit(lineValues[0], lineValues[1], Integer.parseInt(lineValues[2]), lineValues[3], specialColumns, headers));
            }
        }
        return units;
    }

    /**
     * Read a file with students and transform to ArrayList of student
     *
     * @param filePath File path of students file
     * @param unitList List of units
     * @return List of students
     * @throws FileNotFoundException If file not found
     * @throws IOException If some error happened during reading
     * @throws Exception If read bad data
     */
    public static ArrayList<Student> readStudentsFile(String filePath, ArrayList<Unit> unitList) throws FileNotFoundException, IOException, Exception {
        ArrayList<Student> studentList = new ArrayList<>();
        try {
            ArrayList<String> linesFile = CSV.readFile(filePath);
            String[] headers = linesFile.get(0).split(SEPARATOR);
            if (!headers[0].equalsIgnoreCase("Number")
                    || !headers[1].equalsIgnoreCase("Unit")
                    || !headers[2].equalsIgnoreCase("Year")
                    || !headers[3].equalsIgnoreCase("Mark")) {
                throw new Exception("Wrong student file.\nThe headers should be: Number, Unit, Year and Mark");
            }

            // Remove headers line
            linesFile.remove(0);
            for (String line : linesFile) {
                String[] res = line.split(SEPARATOR);
                if (res.length == 4) {
                    int number = Integer.parseInt(res[0]);
                    Unit unit = Unit.getUnit(unitList, res[1]);
                    int year = Integer.parseInt(res[2]);
                    String mark = res[3];
                    Student stu = Student.getStudent(studentList, number);
                    if (stu != null) {
                        stu.addInfo(unit, year, mark);
                    } else {
                        studentList.add(new Student(number, unit, year, mark));
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException(ex.getMessage());
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        } catch (Exception exc) {
            throw new Exception(exc.getMessage());
        }
        return studentList;
    }

    // EXPORTS
    /**
     * Method used to export the search log
     *
     * @param path String with the path to export
     * @param result Object with results
     * @return True if the export is successful
     * @throws Exception IF any error occurs
     */
    public static boolean exportSearchLog(String path, Result result) throws Exception {
        boolean flag;

        String pathProject = path + PATH_SEPARATOR + result.getProjectName();
        SharedFunctions.createDir(path, result.getProjectName());

        String csv = "";
        csv += "Search Log: " + result.getSearchName() + "\n"
                + "Search was performed at:;" + result.getDate() + "\n"
                + "Total Units:;" + result.getUnitList().size() + "\n"
                + "Total Students:;" + result.getStudentList().size() + "\n"
                + "Search Runtime:;" + result.getRunTime() + " ms\n"
                + "Found Patterns:;" + result.getPatterns().size() + "\n"
                + "Min. Units:;" + result.getUnitsMinNumber() + "\n"
                + "Min. Students:;" + result.getStudentsMinNumber() + "\n";

        csv += "\nUnits Description\n";


        if (result.getUnitList() == null) {
            throw new Exception("Unit List not found.");
        }

        for (String unitHeader : result.getUnitList().get(0).getHeaders()) {
            csv += unitHeader + ";";
        }
        csv = csv.substring(0, csv.length() - 1);
        csv += "\n";

        for (Unit unit : result.getUnitList()) {
            csv += unit.getName() + ";" + unit.getId() + ";" + unit.getYear() + ";" + unit.getSemester() + ";";
            csv += Arrays.toString(unit.getExtraColumnsValues()).replace("[", "").replace("]", "\n").replace(",", ";");
        }

        generateHistograms(result);

        csv += "\nPattern unit size histogram;\n";
        csv += "Unit Size;Count;\n";
        for (int key : patternUnitSizes.keySet()) {
            csv += key + String.format(";%0" + patternUnitSizes.get(key) + "d\n", 0).replace("0", "*");
        }
        csv += "\n"
                + "\nPattern student number histogram;\n"
                + "Number Students;Count;\n";
        for (int key : patternStudentSizes.keySet()) {
            csv += key + String.format(";%0" + patternStudentSizes.get(key) + "d\n", 0).replace("0", "*");
        }

        String pathExport = pathProject + PATH_SEPARATOR + result.getSearchName();
        SharedFunctions.createDir(pathProject, result.getSearchName());

        String formatDate = new SimpleDateFormat("EEE, d MMM yyyy HH.mm.ss").format(result.getDate());
        SharedFunctions.createDir(pathExport, formatDate);
        pathExport += PATH_SEPARATOR + formatDate;

        String filePath = pathExport + PATH_SEPARATOR + "SearchLog.csv";

        try {
            try (Formatter fo = new Formatter(new File(filePath))) {
                flag = true;
                fo.format(csv);
                fo.flush();
                fo.close();
            }
        } catch (Exception e) {
            throw new Exception("An error occurred while exporting." + e.toString());
        }
        return flag;
    }

    /**
     * Method used to generate the data for the histogram
     */
    private static void generateHistograms(Result result) {
        patternStudentSizes = new TreeMap<>();
        patternUnitSizes = new TreeMap<>();
        for (Pattern pattern : result.getPatterns()) {
            if (patternStudentSizes.get(pattern.getStudentList().size()) == null) {
                patternStudentSizes.put(pattern.getStudentList().size(), 1);
            } else {
                patternStudentSizes.put(pattern.getStudentList().size(), patternStudentSizes.get(pattern.getStudentList().size()).intValue() + 1);
            }
            if (patternUnitSizes.get(pattern.getPatternSize()) == null) {
                patternUnitSizes.put(pattern.getPatternSize(), 1);
            } else {
                patternUnitSizes.put(pattern.getPatternSize(), patternUnitSizes.get(pattern.getPatternSize()).intValue() + 1);
            }
        }
    }

    /**
     * Method used to export the pattern list
     *
     * @param path String with the path to export
     * @param result Object with results
     * @return True if the export is successful
     * @throws Exception IF any error occurs
     */
    public static boolean exportPatternList(String path, Result result) throws Exception {
        boolean flag;

        String pathProject = path + PATH_SEPARATOR + result.getProjectName();
        SharedFunctions.createDir(path, result.getProjectName());

        String csv = "";
        csv += "Pattern List: " + result.getSearchName() + "\n";
        csv += "Search was performed at:;" + result.getDate() + "\n";
        csv += "Total Units:;" + result.getUnitList().size() + "\n";
        csv += "Total Students:;" + result.getStudentList().size() + "\n";
        csv += "Search Runtime:;" + result.getRunTime() + " ms\n";
        csv += "Found Patterns:;" + result.getPatterns().size() + "\n";
        csv += "Min. Units:;" + result.getUnitsMinNumber() + "\n";
        csv += "Min. Students:;" + result.getStudentsMinNumber() + "\n";
        csv += "\nPattern List\n";
        csv += "number;numStudents;size;";
        for (Unit unit : result.getUnitList()) {
            csv += unit.getId() + ";";
        }
        csv = csv.substring(0, csv.length() - 1);
        csv += "\n";
        int index = 1;
        ArrayList<Pattern> patterns = result.getPatterns();
        if (patterns == null) {
            throw new Exception("Patterns List not found.");
        }

        Collections.sort(patterns, SharedFunctions.comparePatternNumStudentsDesc);
        for (Pattern pattern : patterns) {
            csv += index + ";" + pattern.getStudentList().size() + ";" + pattern.getPatternSize() + ";";
            csv += Arrays.toString(pattern.getPattern()).replace("[", "").replace("]", "\n").replace(",", ";").replace(" ", "");
            index++;
        }

        String pathExport = pathProject + PATH_SEPARATOR + result.getSearchName();
        SharedFunctions.createDir(pathProject, result.getSearchName());

        String formatDate = new SimpleDateFormat("EEE, d MMM yyyy HH.mm.ss").format(result.getDate());
        SharedFunctions.createDir(pathExport, formatDate);
        pathExport += PATH_SEPARATOR + formatDate;

        String filePath = pathExport + PATH_SEPARATOR + "PatternList.csv";
        try {
            try (Formatter fo = new Formatter(new File(filePath))) {
                flag = true;
                fo.format(csv);
                fo.flush();
                fo.close();
            }
        } catch (Exception e) {
            throw new Exception("An error occurred while exporting." + e.toString());
        }
        return flag;
    }

    /**
     * Method used to export the student list on patterns
     *
     * @param path String with the path to export
     * @param result Object with results
     * @return True if the export is successful
     * @throws Exception IF any error occurs
     */
    public static boolean exportStudentList(String path, Result result) throws Exception {
        /*
         * Student List, including basic search	information and	a table	with all the students in each pattern.
         * The students shall be sorted ascending and the patterns sorted descending by	the number of students.	
         */
        boolean flag;
        String pathProject = path + PATH_SEPARATOR + result.getProjectName();
        SharedFunctions.createDir(path, result.getProjectName());
        String csv = "";
        csv += "Student List: " + result.getSearchName() + "\n";
        csv += "Search was performed at:;" + result.getDate() + "\n";
        csv += "Total Units:;" + result.getUnitList().size() + "\n";
        csv += "Total Students:;" + result.getStudentList().size() + "\n";
        csv += "Search Runtime:;" + result.getRunTime() + " ms\n";
        csv += "Found Patterns:;" + result.getPatterns().size() + "\n";
        csv += "Min. Units:;" + result.getUnitsMinNumber() + "\n";
        csv += "Min. Students:;" + result.getStudentsMinNumber() + "\n";

        int index = 1;
        ArrayList<Pattern> patterns = result.getPatterns();
        if (patterns == null) {
            throw new Exception("Patterns List not found.");
        }

        Collections.sort(patterns, SharedFunctions.comparePatternNumStudentsDesc);
        for (Pattern pattern : patterns) {
            ArrayList<Student> students = pattern.getStudentList();
            Collections.sort(students);
            csv += index + ";";
            for (Student student : students) {
                csv += student.getNumber() + ",";
            }
            csv = csv.substring(0, csv.length() - 1);
            csv += ";\n";
            index++;
        }

        String pathExport = pathProject + PATH_SEPARATOR + result.getSearchName();
        SharedFunctions.createDir(pathProject, result.getSearchName());

        String formatDate = new SimpleDateFormat("EEE, d MMM yyyy HH.mm.ss").format(result.getDate());
        SharedFunctions.createDir(pathExport, formatDate);
        pathExport += PATH_SEPARATOR + formatDate;

        String filePath = pathExport + PATH_SEPARATOR + "StudentList.csv";
        try {
            try (Formatter fo = new Formatter(new File(filePath))) {
                flag = true;
                fo.format(csv);
                fo.flush();
                fo.close();
            }
        } catch (Exception e) {
            throw new Exception("An error occurred while exporting." + e.toString());
        }
        return flag;
    }
}
