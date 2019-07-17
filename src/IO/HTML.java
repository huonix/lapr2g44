package IO;

import Core.Pattern;
import Core.Result;
import Core.Unit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Class responsible for exporting patterns to HTML
 *
 * @author Group 44 LAPR2
 */
public final class HTML implements Serializable {

    private static final String PATH_SEPARATOR = "\\";
    private Result result;
    private TreeMap<Integer, Integer> patternStudentSizes = new TreeMap<>();
    private TreeMap<Integer, Integer> patternUnitSizes = new TreeMap<>();

    /**
     * Constructor responsible for creating a HTML object with the results
     *
     * @param result Result of a search
     * @throws Exception If any error occurs
     */
    public HTML(Result result) throws Exception {
        setResult(result);
    }

    /**
     * Method responsible for creating the header of the HTML page
     *
     * @param title Title of the document
     * @param date The date of the document creation
     * @return The header of the HTML page
     */
    public String getHeader(String title, String date) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"pt\" lang=\"pt\">\n"
                + "<head>\n"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"
                + "<title>Group 44 | " + title + "</title>\n"
                + "<meta name=\"Author\" content=\"LAPR2, Grupo 44\" />\n"
                + "<meta name=\"copyright\" content=\"Copyright &copy; " + Calendar.getInstance().get(Calendar.YEAR) + " isep.pt\" />\n"
                + "<link rel=\"icon\" type=\"image/ico\" href=\"../../include/images/favicon.ico\" />\n"
                + "<link rel=\"shortcut icon\" href=\"../include/images/favicon.ico\" />\n"
                + "<link rel=\"stylesheet\" href=\"../../include/css/style.css\" type=\"text/css\" media=\"screen\" />\n"
                + "<link rel=\"stylesheet\" href=\"../../include/css/jquery.dataTables_themeroller.css\" type=\"text/css\" media=\"screen\" />\n"
                + "<link rel=\"stylesheet\" href=\"../../include/css/jquery-ui-1.8.4.custom.css\" type=\"text/css\" media=\"screen\" />\n"
                + "<script type=\"text/javascript\" src=\"../../include/js/jquery-1.10.1.min.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"../../include/js/main.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"../../include/js/jquery.dataTables.min.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"../../include/js/chart/jqBarGraph.1.1.min.js\"></script>\n"
                + "<script type=\"text/javascript\" charset=\"utf-8\">\n"
                + "$(document).ready(function() {\n"
                + "  $('#patterns').dataTable({\n"
                + "     \"bJQueryUI\": true,\n"
                + "     \"sPaginationType\": \"full_numbers\",\n"
                + "     \"bStateSave\": true,\n"
                + "      'iDisplayLength': 15,\n"
                + "     \"aLengthMenu\": [[15, 40, 80, -1], [15, 40, 80, \"All\"]],\n"
                + "     \"bScrollCollapse\": true,\n"
                + "     \"sScrollX\": \"100%\""
                + "   });\n"
                + "});\n"
                + "</script>\n"
                + "</head>\n"
                + "<body>\n"
                + "<div id=\"contentFullBg-image\">\n"
                + "<img src=\"../../include/images/bg.png\" alt=\"bg\" />\n"
                + "</div>\n"
                + "<div id=\"contentFullWrap\">\n"
                + "<div id=\"loader\"></div>\n"
                + "<div id=\"header\">\n"
                + "<div class=\"headerCenter\">\n"
                + "<div class=\"logo\"><img src=\"../../include/images/logo.jpg\" alt=\"ISEP Logo\" /></div>\n"
                + "<div class=\"docDescription\">\n"
                + "<div id=\"title\"><h1>" + title + "</h1></div>\n"
                + "<div id=\"generated\">created: " + date + "</div>\n"
                + "</div>\n"
                + "</div>\n"
                + "</div>\n"
                + "<div id=\"content\">\n"
                + "<div id=\"contentCenter\">\n";
    }

    /**
     * Method used to get the footer of the HTML page
     *
     * @return The footer of the HTML page
     */
    public String getFooter() {
        return "</div>\n"
                + "</div>\n"
                + "<div id=\"clearFooter\"></div>\n"
                + "<div id=\"footer\">\n"
                + "<div class=\"footerCenter\">\n"
                + "<div class=\"footerCopy\">ISEP &copy; " + Calendar.getInstance().get(Calendar.YEAR) + " All rights Reserved</div>\n"
                + "<div class=\"footerDev\">Design and Dev By Group 44 (LAPR2)</div>\n"
                + "</div>\n"
                + "</div>\n"
                + "</div>\n"
                + "</body>\n"
                + "</html>";
    }

    /**
     * Method used to export the pattern list
     *
     * @param path String with the path to export
     * @return True if the export is successful
     * @throws Exception IF any error occurs
     */
    public boolean exportPatternList(String path) throws Exception {
        boolean flag;

        String pathProject = path + PATH_SEPARATOR + result.getProjectName();
        SharedFunctions.createDir(path, result.getProjectName());
        createIncludes(pathProject);

        String html = "";
        html += getHeader("Pattern List: " + result.getSearchName(), result.getDate().toString());
        html += "<div class=\"grid-28\">\n"
                + "<div class=\"grid-4\">Total Units: " + result.getUnitList().size() + "</div>\n"
                + "<div class=\"grid-1\">&nbsp;</div>\n"
                + "<div class=\"grid-4\">Total Students: " + result.getStudentList().size() + "</div>\n"
                + "<div class=\"grid-1\">&nbsp;</div>\n"
                + "<div class=\"grid-6\">Search Runtime: " + result.getRunTime() + "ms</div>\n"
                + "<div class=\"grid-1\">&nbsp;</div>\n"
                + "<div class=\"grid-4\">Found Patterns: " + result.getPatterns().size() + " </div>\n"
                + "<div class=\"grid-3\">Min. Units: " + result.getUnitsMinNumber() + "</div>\n"
                + "<div class=\"grid-4\">Min. Students: " + result.getStudentsMinNumber() + "</div>\n"
                + "</div>\n"
                + "<div class=\"grid-28\">&nbsp;</div>";

        html += "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"patterns\" width=\"100%\">\n"
                + "<thead>\n"
                + "<tr>\n"
                + "<th>number</th>\n"
                + "<th>numStudents</th>\n"
                + "<th>size</th>\n";

        if (result.getUnitList() == null) {
            throw new Exception("Unit List not found.");
        }

        for (Unit unit : result.getUnitList()) {
            html += "<th>" + unit.getId() + "</th>\n";
        }
        html += "</tr>\n"
                + "</thead>\n"
                + "<tbody>\n";
        int index = 1;
        ArrayList<Pattern> patterns = result.getPatterns();
        if (patterns == null) {
            throw new Exception("Patterns List not found.");
        }

        Collections.sort(patterns, SharedFunctions.comparePatternNumStudentsDesc);

        HashSet<Pattern> b = new HashSet<>(patterns);
        Iterator iter = b.iterator();
        while (iter.hasNext()) {
            Pattern pattern;
            pattern = (Pattern) iter.next();
            html += "<tr>\n<td>"
                    + index
                    + "</td>\n<td>"
                    + pattern.getStudentList().size()
                    + "</td>\n<td>"
                    + pattern.getPatternSize()
                    + "</td>\n"
                    + Arrays.toString(pattern.getPattern()).replace("[", "<td>").replace("]", "</td>\n").replace(",", "</td><td>")
                    + "</tr>\n";
            index++;
        }

        html += "</tbody>\n</table>\n" + getFooter();

        String pathExport = pathProject + PATH_SEPARATOR + result.getSearchName();
        SharedFunctions.createDir(pathProject, result.getSearchName());

        String formatDate = new SimpleDateFormat("EEE, d MMM yyyy HH.mm.ss").format(result.getDate());
        SharedFunctions.createDir(pathExport, formatDate);
        pathExport += PATH_SEPARATOR + formatDate;

        String filePath = pathExport + PATH_SEPARATOR + "PatternList.html";
        try (PrintWriter newFile = new PrintWriter(filePath, "UTF-8")) {
            flag = true;
            newFile.print(html);
            newFile.close();
        } catch (Exception e) {
            throw new Exception("An error occurred while exporting.");
        }
        return flag;
    }

    /**
     * Method used to export the search log
     *
     * @param path String with the path to export
     * @return True if the export is successful
     * @throws Exception IF any error occurs
     */
    public boolean exportSearchLog(String path) throws Exception {
        boolean flag;

        String pathProject = path + PATH_SEPARATOR + result.getProjectName();
        SharedFunctions.createDir(path, result.getProjectName());
        createIncludes(pathProject);

        String html = "";
        html += getHeader("Search Log: " + result.getSearchName(), result.getDate().toString());
        html += "<div class=\"grid-28\"><h3>This Search was performed at: " + result.getDate() + "</h3></div>";
        html += "<div class=\"grid-28\">&nbsp;</div>";
        html += "<div class=\"grid-28\">\n"
                + "<div class=\"grid-4\">Total Units: " + result.getUnitList().size() + "</div>\n"
                + "<div class=\"grid-1\">&nbsp;</div>\n"
                + "<div class=\"grid-4\">Total Students: " + result.getStudentList().size() + "</div>\n"
                + "<div class=\"grid-1\">&nbsp;</div>\n"
                + "<div class=\"grid-6\">Search Runtime: " + result.getRunTime() + "ms</div>\n"
                + "<div class=\"grid-1\">&nbsp;</div>\n"
                + "<div class=\"grid-4\">Found Patterns: " + result.getPatterns().size() + " </div>\n"
                + "<div class=\"grid-3\">Min. Units: " + result.getUnitsMinNumber() + "</div>\n"
                + "<div class=\"grid-4\">Min. Students: " + result.getStudentsMinNumber() + "</div>\n"
                + "</div>\n"
                + "<div class=\"grid-28\">&nbsp;</div>";

        html += "<div class=\"grid-28\">&nbsp;</div>";
        html += "<div class=\"grid-28\">&nbsp;</div>";

        html += "<div class=\"grid-28\"><h3>Units Description</h3></div>";
        html += "<div class=\"grid-28\">";
        html += "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"patterns\" width=\"100%\">\n";
        html += "<thead>\n";
        html += "<tr>\n";


        if (result.getUnitList() == null) {
            throw new Exception("Unit List not found.");
        }

        for (String unitHeader : result.getUnitList().get(0).getHeaders()) {
            html += "<th>" + unitHeader + "</th>\n";
        }
        html += "</tr>\n";
        html += "</thead>\n";
        html += "<tbody>\n";

        for (Unit unit : result.getUnitList()) {
            html += "<tr>\n";
            html += "<td>" + unit.getName() + "</td>\n";
            html += "<td>" + unit.getId() + "</td>\n";
            html += "<td>" + unit.getYear() + "</td>\n";
            html += "<td>" + unit.getSemester() + "</td>\n";
            html += Arrays.toString(unit.getExtraColumnsValues()).replace("[", "<td>").replace("]", "</td>\n").replace(",", "</td><td>");
            html += "</tr>\n";
        }
        html += "</tbody>\n";
        html += "</table>\n";
        html += "</div>";
        html += "<div class=\"grid-28\">&nbsp;</div>";

        generateHistograms();

        html += "<div class=\"grid-28\">&nbsp;</div>";

        html += "<div class=\"grid-28\">";
        html += "<div class=\"grid-28\"><h3>Pattern unit size histogram</h3></div>";
        html += "<div class=\"grid-28\">&nbsp;</div>";
        html += "<div id=\"graph1\" class=\"grid-28\">&nbsp;</div>";
        html += "<div class=\"grid-28\">&nbsp;</div>";
        html += "<div class=\"grid-28\" style=\"text-align:center;\">Pattern unit size</div>";
        html += "</div>";

        html += "<div class=\"grid-28\">";
        html += "<div class=\"grid-v\"><h3>Pattern student number histogram</h3></div>";
        html += "<div class=\"grid-28\">&nbsp;</div>";
        html += "<div id=\"graph2\" class=\"grid-28\">&nbsp;</div>";
        html += "<div class=\"grid-28\">&nbsp;</div>";
        html += "<div class=\"grid-28\" style=\"text-align:center;\">Number of students</div>";
        html += "</div>";

        String unitChart = "";
        unitChart += "unitData = new Array(";
        for (int key : patternUnitSizes.keySet()) {
            int value = patternUnitSizes.get(key);
            unitChart += "[" + value + ",'" + key + "'],";
        }
        unitChart = (patternUnitSizes.size() > 0) ? unitChart.substring(0, unitChart.length() - 1) : unitChart;
        unitChart += ");";
        unitChart += "$('#graph1').jqbargraph({\n"
                + "   data: unitData ,\n"
                + "   barSpace: 5,\n"
                + "   width: 980,\n"
                + "   height: 300,\n"
                + "   colors: ['#af5b29','#a34005']\n"
                + "});";
        html += "<script>" + unitChart + "</script>";

        String studentChart = "";
        studentChart += "studentData = new Array(";
        for (int key : patternStudentSizes.keySet()) {
            int value = patternStudentSizes.get(key);
            studentChart += "[" + value + ",'" + key + "'],";
        }
        studentChart = (patternStudentSizes.size() > 0) ? studentChart.substring(0, studentChart.length() - 1) : studentChart;
        studentChart += ");";
        studentChart += "$('#graph2').jqbargraph({\n"
                + "   data: studentData ,\n"
                + "   barSpace: 5,\n"
                + "   width: 980,\n"
                + "   height: 300,\n"
                + "   colors: ['#af5b29','#a34005']\n"
                + "});";
        html += "<script>" + studentChart + "</script>";

        html += getFooter();

        String pathExport = pathProject + PATH_SEPARATOR + result.getSearchName();
        SharedFunctions.createDir(pathProject, result.getSearchName());

        String formatDate = new SimpleDateFormat("EEE, d MMM yyyy HH.mm.ss").format(result.getDate());
        SharedFunctions.createDir(pathExport, formatDate);
        pathExport += PATH_SEPARATOR + formatDate;

        String filePath = pathExport + PATH_SEPARATOR + "SearchLog.html";
        try (PrintWriter newFile = new PrintWriter(filePath, "UTF-8")) {
            flag = true;
            newFile.print(html);
            newFile.close();
        } catch (Exception e) {
            throw new Exception("An error occurred while exporting.");
        }
        return flag;
    }

    /**
     * Method used to generate the data for the histogram
     */
    private void generateHistograms() {
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
     * Method used to include the HTML file to support the page
     *
     * @param path Path to be exported
     * @throws Exception If any error occurs
     */
    public void createIncludes(String path) throws Exception {
        String nameDir = "/include.zip";
        String destinationPath = path;
        try (InputStream source = getClass().getResourceAsStream(nameDir); ZipInputStream sourceZip = new ZipInputStream(source)) {
            ZipEntry entry;
            while ((entry = sourceZip.getNextEntry()) != null) {
                String target = destinationPath + PATH_SEPARATOR + entry.getName();
                if (entry.isDirectory()) {
                    File destDir = new File(target);
                    destDir.mkdirs();
                } else {
                    try (OutputStream targetOutput = new FileOutputStream(target)) {
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = sourceZip.read(buf)) > 0) {
                            targetOutput.write(buf, 0, len);
                        }
                        targetOutput.close();
                    } catch (Exception e) {
                        throw new Exception("Error while parsing include files." + e.toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error while parsing include files." + e.toString());
        }
    }

    /**
     * Method used to get the Result
     *
     * @return Object Result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Method used to set the result
     *
     * @param result Result of a search
     * @throws Exception If the result is null
     */
    public void setResult(Result result) throws Exception {
        if (result == null) {
            throw new Exception("Result is null.");
        }
        this.result = result;
    }
}