package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Sorter {

    /**
     * Merges list of files into one applying sorting.
     * The algorithm itself is an adaptation of the classic merge sort algorithm.
     * Input files should be completely valid in terms of sorting and data type.
     *
     * WARNING! METHOD REMOVES THE ORIGINAL FILES, so be sure to provide copies!
     *
     * @param fileNames - list of files to merge with sorting. All files should be completely valid
     * @param isDescend - apply sorting in descend order
     * @param isLexically - apply sorting in alphabetical order rather then by integer value (for strings)
     * @param outputFileName - path to which the result will be written.
     */
    public static void mergeFiles(List<String> fileNames, boolean isLexically, boolean isDescend, String outputFileName) {

        while (fileNames.size() > 1) {
            File file1 = new File(fileNames.get(fileNames.size() - 1));
            File file2 = new File(fileNames.get(fileNames.size() - 2));
            Scanner sc1 = null;
            Scanner sc2 = null;
            BufferedWriter writer = null;

            File newFile;
            try {
                newFile = File.createTempFile("sort_", null, null);
                writer = new BufferedWriter(new PrintWriter(newFile));
                sc1 = new Scanner(file1);
                sc2 = new Scanner(file2);
                mergeTwoFiles(sc1, sc2, writer, isLexically, isDescend);
                fileNames.remove(fileNames.size() - 1);
                fileNames.set(fileNames.size() - 1, newFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
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
                    file1.delete();
                    file2.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

        if (outputFileName != null) {
            File resultFile = new File(fileNames.get(0));
            File outputFile = new File(outputFileName);
            resultFile.renameTo(outputFile);
        }

    }

    /**
     * Merges two files into one applying sorting.
     * The algorithm itself is an adaptation of the classic merge sort algorithm.
     * Input files should be completely valid in terms of sorting and data type.
     *
     * @param sc1 - scanner of the first file (should have at least one next line)
     * @param sc2 - scanner of the second file (should have at least one next line)
     * @param writer - writer to the resulting file
     * @param isLexically - apply sorting in alphabetical order rather then by integer value (for strings)
     */
    public static void mergeTwoFiles(Scanner sc1, Scanner sc2, BufferedWriter writer, boolean isLexically, boolean isDescend) throws IOException {

        String a = sc1.nextLine();
        String b = sc2.nextLine();

        while (true) {
            if (compare(a, b, isLexically, isDescend) < 0) {
                writer.write(a);
                writer.newLine();
                if (sc1.hasNext()) {
                    a = sc1.nextLine();
                } else {
                    writer.write(b);
                    writer.newLine();
                    break;
                }
            } else {
                writer.write(b);
                writer.newLine();
                if (sc2.hasNext()) {
                    b = sc2.nextLine();
                } else {
                    writer.write(a);
                    writer.newLine();
                    break;
                }
            }

        }

        while (sc2.hasNext()) {
            writer.write(sc2.nextLine());
            writer.newLine();
        }

        while (sc1.hasNext()) {
            writer.write(sc1.nextLine());
            writer.newLine();
        }

    }

    public static int compare(String a, String b, boolean isLexically, boolean isDescend) {
        if (isDescend) {
            return compare(b, a, isLexically, false);
        }

        if (isLexically) {
            return a.compareTo(b);
        }

        Integer aInt = Integer.parseInt(a);
        Integer bInt = Integer.parseInt(b);
        return aInt.compareTo(bInt);
    }

}
