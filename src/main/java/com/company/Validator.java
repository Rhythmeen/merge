package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    /**
     * Class for validating and making temporary copies of initial files
     */

    private boolean isInteger;
    private boolean isDescent;

    public Validator(boolean isInteger, boolean isDescent) {
        this.isInteger = isInteger;
        this.isDescent = isDescent;
    }

    /**
     * Validates list of files and creates their temporary copies for later use.
     * @return a map with three keys: "ToProcess", "Failed", "PartiallyFailed"
     * All keis are guaranteed.
     *
     * "ToProcess" - complete or partial temporary copies of the initial files which were suitable for merge sort
     * "Failed" - list of completely invalid files
     * "PartiallyFailed" - list of partially corrupted files (their partial copies are listed among "ToProcess"
     *
     */
    public Map<String, List<String>> validateFiles(List<String> fileNameList) {

        Map<String, List<String>> result = new HashMap<>();
        List<String> filesToProcess = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();
        List<String> partiallyFailedFiles = new ArrayList<>();
        result.put("ToProcess", filesToProcess);
        result.put("Failed", failedFiles);
        result.put("PartiallyFailed", partiallyFailedFiles);

        for (String fileName: fileNameList) {
            Map<String, String> validationResult = tryFixFile(fileName);
            if (validationResult.get("Error").equals("1")) {
                failedFiles.add(fileName);
                continue;
            }

            if (validationResult.get("Error").equals("0")) {
                filesToProcess.add(validationResult.get("FileName"));
                continue;
            }

            if (validationResult.get("Error").equals("2")) {
                partiallyFailedFiles.add(fileName);
                filesToProcess.add(validationResult.get("FileName"));
            }

        }

        return result;
    }

    /**
     * Validates the file and creates it's temporary copy for later use.
     * @return a dictionary with two keys: "Error", "FileName".
     * "Error" key is guaranteed, "FileName" will only be present if file was successfully copied.
     * There are three cases:
     * 1) If the file is perfectly valid:
     * - it is copied as is
     * - "Error" value set to "0"
     * - "FileName" value is set
     *
     * 2) If the file is completely corrupted (i.e. exists, not empty, all data is valid):
     * - it is not copied
     * - "Error"
     *
     * 3) If the file was partially corrupted (i.e. some but not all data was invalid):
     * - only valid part of the file is copied
     * - "Error" value set to "2"
     * - "FileName" value is set
     */
    public Map<String, String> tryFixFile(String fileName) {

        Map<String, String> result = new HashMap<>();
        result.put("Error", "0");

        File file = new File(fileName);
        if (file.length() == 0) {
            result.put("Error", "1");
            return result;
        }

        File newFile = null;
        try {
            newFile = File.createTempFile("sort_", null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner sc = null;
        BufferedWriter writer = null;

        try {
            sc = new Scanner(file);
            writer = new BufferedWriter(new FileWriter(newFile));

            String prev = null;
            while (sc.hasNext()) {
                String next = sc.nextLine();
                boolean isValid = validateLine(next, prev);
                if (isValid) {
                    writer.write(next);
                    writer.newLine();
                    prev = next;
                } else {
                    result.put("Error", "2");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (sc != null) {
                    sc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (newFile.length() == 0) {
            result.put("Error", "1");
            newFile.delete();
            return result;
        }

        result.put("FileName", newFile.getAbsolutePath());
        return result;
    }

    /**
     * Checks if current line satisfies given data type and sorting order
     */
    public boolean validateLine(String nextLine, String prevLine) {
        if (this.isInteger) {
            return this.checkInteger(nextLine, prevLine);
        }
        return this.checkString(nextLine, prevLine);
    }

    /**
     * Checks if current line does not contain space symbols and satisfies given order
     */
    private boolean checkString(String next, String prev) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(next);
        if (matcher.find()) {
            return false;
        }

        if (prev == null) {
            return true;
        }

        if (this.isDescent) {
            return next.compareTo(prev) <= 0;
        }

        return next.compareTo(prev) >= 0;
    }

    /**
     * Checks if current line represents integer number and satisfies given order
     */
    private boolean checkInteger(String next, String prev) {
        if (!next.matches("-?\\d+")) {
            return false;
        }

        if (prev == null) {
            return true;
        }

        Integer nextInt = Integer.parseInt(next);
        Integer prevInt = Integer.parseInt(prev);

        if (this.isDescent) {
            return nextInt <= prevInt;
        }

        return nextInt >= prevInt;

    }

}
