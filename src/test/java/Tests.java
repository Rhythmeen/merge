import com.company.Validator;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.company.Sorter.mergeFiles;
import static com.company.Sorter.mergeTwoFiles;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    private String unsortedIntegerFile = getClass().getResource("Integer/Ascend/in3unsorted.txt").getPath();
    private String emptyFile = getClass().getResource("Integer/Ascend/in5empty.txt").getPath();
    private String validDescendIntegerFile1 = getClass().getResource("Integer/Descend/in1.txt").getPath();
    private String validAscendIntegerFile1 = getClass().getResource("Integer/Ascend/in1.txt").getPath();
    private String validAscendIntegerFile2 = getClass().getResource("Integer/Ascend/in2.txt").getPath();
    private String validAscendIntegerFile3 = getClass().getResource("Integer/Ascend/in3.txt").getPath();
    private String fileWithStringsOnly = getClass().getResource("Integer/Ascend/in6StringsOnly.txt").getPath();
    private String integerDescendDir = getClass().getResource("Integer/Descend").getPath();

    //integer по возрастанию
    @Test // 6,7 true
    public void ValidateLineIntegerTest() {
        Validator validator = new Validator(true, false);
        String prevLine = "6";
        String nextLine = "7";
        Boolean expected = true;
        Boolean actual = null;
        try {
            actual = validator.validateLine(nextLine, prevLine);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(expected, actual);
    }

    @Test // 7,6 false
    public void ValidateLineIntegerTest2() {

        Validator validator = new Validator(true, false);
        String prevline = "7";
        String nextLine = "6";
        Boolean expected = false;
        Boolean actual = null;
        try {
            actual = validator.validateLine(nextLine, prevline);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(expected, actual);
    }

    @Test //-6,-6 true
    public void ValidateLineIntegerTest3() {

        Validator validator = new Validator(true, false);
        String prevline = "-6";
        String nextLine = "-6";
        Boolean expected = true;
        Boolean actual = null;
        try {
            actual = validator.validateLine(nextLine, prevline);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(expected, actual);
    }

    //Integer по убыванию
    @Test // 7,6 false
    public void ValidateLineIntegerTest4() {
        Validator validator = new Validator(true, true);
        String prevline = "6";
        String nextLine = "7";
        Boolean expected = false;
        Boolean actual = null;
        try {
            actual = validator.validateLine(nextLine, prevline);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(expected, actual);
    }

    @Test // 6,7 true
    public void ValidateLineIntegerTest5() {

        Validator validator = new Validator(true, true);
        String prevline = "7";
        String nextLine = "6";
        Boolean expected = true;
        Boolean actual = null;
        try {
            actual = validator.validateLine(nextLine, prevline);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(expected, actual);
    }

    @Test //-6,-6 true
    public void ValidateLineIntegerTest6() {

        Validator validator = new Validator(true, true);
        String prevline = "-6";
        String nextLine = "-6";
        Boolean expected = true;
        Boolean actual = null;
        try {
            actual = validator.validateLine(nextLine, prevline);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(expected, actual);
    }

    @Test //not Integer, false
    public void ValidateLineIntegerTest7() {

        Validator validator = new Validator(true, false);
        String prevline = "7";
        String nextLine = "gg";
        Boolean expected = false;
        Boolean actual = null;
        try {
            actual = validator.validateLine(nextLine, prevline);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(expected, actual);
    }

    //String по возрастанию
    @Test // A,B true
    public void ValidateLineStringTest() {
        Validator validator = new Validator(false, false);
        String prevline = "Anton";
        String nextLine = "Boris";
        Boolean expected = true;
        Boolean actual = null;
        try {
            actual = validator.validateLine(nextLine, prevline);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(expected, actual);
    }

    @Test //
    // B,A false
    public void ValidateLineStringTest2() {
        Validator validator = new Validator(false, false);
        String prevline = "Boris";
        String nextLine = "Anton";
        Boolean expected = false;
        Boolean actual = null;
        try {
            actual = validator.validateLine(nextLine, prevline);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(expected, actual);
    }

    @Test // tt,tt true
    public void ValidateLineStringTest3() {
        Validator validator = new Validator(false, false);
        String prevline = "tt";
        String nextLine = "tt";
        Boolean expected = true;
        Boolean actual = null;
        try {
            actual = validator.validateLine(nextLine, prevline);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(expected, actual);
    }

    //String по убыванию
    @Test // B,A true
    public void ValidateLineStringTest4() {
        Validator validator = new Validator(false, true);
        String prevline = "Boris";
        String nextLine = "Anton";
        Boolean expected = true;
        Boolean actual = null;
        try {
            actual = validator.validateLine(nextLine, prevline);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(expected, actual);
    }

    @Test // A,B false
    public void ValidateLineStringTest5() {
        Validator validator = new Validator(false, true);
        String prevline = "Anton";
        String nextLine = "Boris";
        Boolean expected = false;
        Boolean actual = null;
        try {
            actual = validator.validateLine(nextLine, prevline);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(expected, actual);
    }

    @Test // tt,tt true
    public void ValidateLineStringTest6() {
        Validator validator = new Validator(false, true);
        String prevline = "Denis";
        String nextLine = "Denis";
        Boolean expected = true;
        Boolean actual = null;
        try {
            actual = validator.validateLine(nextLine, prevline);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(expected, actual);
    }

    @Test// тест на пробел, false
    public void ValidateLineStringTestSpace() {
        Validator validator = new Validator(false, true);
        String prevline = "fd65df";
        String nextLine = "f f";
        Boolean expected = false;
        Boolean actual = null;
        try {
            actual = validator.validateLine(nextLine, prevline);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(expected, actual);
    }

    //TryFixFile Integer Ascend with Mistakes
    @Test  //good file
    public void TryFixFileTest() {
        Validator validator = new Validator(true, true);

        File newFile = null;
        try {
            Map<String, String> validationResult = validator.tryFixFile(validDescendIntegerFile1);
            assertTrue(validationResult.containsKey("Error"));
            assertEquals("0", validationResult.get("Error"));

            assertTrue(validationResult.containsKey("FileName"));
            String tempName = validationResult.get("FileName");
            assertNotNull(tempName);

            newFile = new File(tempName);
            assertTrue(newFile.length() > 1);
        } finally {
            if (newFile != null) {
                newFile.delete();
            }
        }
    }

    @Test //unsorted
    public void TryFixFileTest2() {
        Validator validator = new Validator(true, true);

        File newFile = null;
        try {
            Map<String, String> validationResult = validator.tryFixFile(unsortedIntegerFile);
            assertTrue(validationResult.containsKey("Error"));
            assertEquals("2", validationResult.get("Error"));

            assertTrue(validationResult.containsKey("FileName"));
            String tempName = validationResult.get("FileName");
            assertNotNull(tempName);

            newFile = new File(tempName);
            assertTrue(newFile.length() > 1);
        } finally {
            if (newFile != null) {
                newFile.delete();
            }
        }
    }

    @Test //emptyFile
    public void TryFixFileTest3() {
        Validator validator = new Validator(true, true);

        File newFile = null;
        try {
            Map<String, String> validationResult = validator.tryFixFile(emptyFile);
            assertTrue(validationResult.containsKey("Error"));
            assertEquals("1", validationResult.get("Error"));
            assertFalse(validationResult.containsKey("FileName"));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test //fileWithStringsOnly
    public void TryFixFileTest4() {
        Validator validator = new Validator(true, true);
        try {
            Map<String, String> validationResult = validator.tryFixFile(fileWithStringsOnly);
            assertTrue(validationResult.containsKey("Error"));
            assertEquals("1", validationResult.get("Error"));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test  // validate files
    public void ValidateFilesTest() {
        Validator validator = new Validator(true, true);
        List<String> files = new ArrayList<>();
        files.add(validDescendIntegerFile1);
        files.add(unsortedIntegerFile);
        files.add(emptyFile);
        files.add(fileWithStringsOnly);

        List<String> filesToProcess = new ArrayList<>();

        try {
            Map<String, List<String>> validationResult = validator.validateFiles(files);
            assertTrue(validationResult.containsKey("ToProcess"));
            assertTrue(validationResult.containsKey("Failed"));
            assertTrue(validationResult.containsKey("PartiallyFailed"));

            filesToProcess = validationResult.get("ToProcess");
            List<String> badFiles = validationResult.get("Failed");
            List<String> partiallyBadFiles = validationResult.get("PartiallyFailed");

            assertNotNull(filesToProcess);
            assertNotNull(badFiles);
            assertNotNull(partiallyBadFiles);

            assertEquals(2, filesToProcess.size());
            assertEquals(2, badFiles.size());
            assertEquals(1, partiallyBadFiles.size());

        } finally {
            for (String fileName : filesToProcess) {
                File file = new File(fileName);
                file.delete();
            }
        }
    }

    @Test // merge files
    public void ValidateMergeTwoFiles() {
        Scanner sc1 = null;
        Scanner sc2 = null;
        BufferedWriter writer = null;

        File file1 = new File(validAscendIntegerFile1);
        File file2 = new File(validAscendIntegerFile2);

        File newFile = null;
        try {
            newFile = File.createTempFile("sort_", null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer = new BufferedWriter(new PrintWriter(newFile));
            sc1 = new Scanner(file1);
            sc2 = new Scanner(file2);
            mergeTwoFiles(sc1, sc2, writer, false, false);

        } catch (IOException e) {
            e.printStackTrace();
            newFile.delete();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (sc1 != null) {
                    sc1.close();
                }
                if (sc2 != null) {
                    sc2.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        String[] check = {"-1000", "-100", "13", "15", "16", "16", "20", "20", "28", "128", "256", "500", "512", "1487"};

        Scanner sc3 = null;
        try {
            sc3 = new Scanner(newFile);
            int i = 0;
            while (sc3.hasNext()) {
                assertEquals(check[i], sc3.nextLine());
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (sc3 != null) {
                sc3.close();
            }
            newFile.delete();
        }

    }

    @Test
    public void MergeFilesTest() {
        List<String> files = new ArrayList<>();
        files.add(validAscendIntegerFile1);
        files.add(validAscendIntegerFile2);
        files.add(validAscendIntegerFile3);
        files.add(unsortedIntegerFile);
        files.add(emptyFile);
        files.add(fileWithStringsOnly);

        Validator validator = new Validator(true, false);

        Map<String, List<String>> validationResult = validator.validateFiles(files);
        List<String> filesToMerge = validationResult.get("ToProcess");
        List<String> badFiles = validationResult.get("Failed");
        List<String> partiallyBadFiles = validationResult.get("PartiallyFailed");

        assertEquals(2, badFiles.size());
        assertEquals(1, partiallyBadFiles.size());
        System.out.println(badFiles);
        System.out.println(partiallyBadFiles);

        String outputPath = getClass().getResource("Integer/Ascend/merged.txt").getPath();
        mergeFiles(filesToMerge, false, false, outputPath);

        String expected = "-1000\n-100\n0\n13\n15\n16\n16\n20\n20" +
                "\n20\n25\n28\n56\n60\n70\n120\n128\n150\n180\n192\n210\n240\n256\n320\n500\n512\n1487\n";

        try {
            String actual = readFile(outputPath);
            assertEquals(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String readFile(String file) throws IOException {
        try (Scanner sc = new Scanner(new File(file))) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");
            while (sc.hasNext()) {
                line = sc.nextLine();
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            return stringBuilder.toString();
        }
    }

}