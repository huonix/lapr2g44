package IO;

import Core.Pattern;
import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

/**
 * This class has methods that are shared in other classes on IO package, such
 * as comparators.
 *
 * @author Group 44 LAPR2
 */
public class SharedFunctions implements Serializable {

    public static final String PATH_SEPARATOR = "\\";

    /**
     * Method used to create a directory
     *
     * @param pathDir Path of the directory to be created
     * @param dirName The name of the Directory
     * @return True if the directory were created
     * @throws Exception If any error occurs
     */
    public static boolean createDir(String pathDir, String dirName) throws Exception {
        boolean flag;
        String directoryName = pathDir + PATH_SEPARATOR + dirName;
        try {
            File theDir = new File(directoryName);
            if (!theDir.exists()) {
                flag = theDir.mkdirs();
            } else {
                flag = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return flag;
    }
    /**
     * Compare Patterns by number of students
     */
    public static Comparator comparePatternNumStudentsDesc = new Comparator<Pattern>() {
        @Override
        public int compare(Pattern o1, Pattern o2) {
            return o1.getStudentList().size() - o2.getStudentList().size();
        }
    };
}
