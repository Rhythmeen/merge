package com.company;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // getting parameters from command line
        Cli cli = new Cli();
        try {
            cli.tryBuild(args);
        } catch (Exception e) {
            System.exit(1);
        }
        List<String> inputFiles = cli.getInputFiles();
        String outputFile = cli.getOutputFile();
        Boolean isDescend = cli.getIsDescend(); // sorting order
        Boolean isInteger = cli.getIsInteger(); // data type

        System.out.println("Validating files...");
        Validator validator = new Validator(isInteger, isDescend);
        Map<String, List<String>> validationResult = validator.validateFiles(inputFiles);
        List<String> filesToProcess = validationResult.get("ToProcess");
        List<String> badFiles = validationResult.get("Failed");
        List<String> partiallyBadFiles = validationResult.get("PartiallyFailed");

        System.out.println(String.format("\nFinished validating %s files.", inputFiles.size()));
        System.out.println(String.format("%s files to sort overall.", filesToProcess.size()));
        System.out.println(String.format("%s files will be only partially sort.", partiallyBadFiles.size()));
        System.out.println(String.format("%s files will be skipped.", badFiles.size()));

        if (!filesToProcess.isEmpty()) {
            System.out.println("\nSorting...");
            Sorter.mergeFiles(filesToProcess, !isInteger, isDescend, outputFile);
            System.out.println("Finished sorting.");
        }
        if (!badFiles.isEmpty()) {
            System.out.println("\nSkipped files (empty, corrupted or invalid data):");
            for (String badFile : badFiles) {
                System.out.println(badFile);
            }
        }

        if (!partiallyBadFiles.isEmpty()) {
            System.out.println("\nPartially processed files (data is partially invalid):");
            for (String partiallyBadFile : partiallyBadFiles) {
                System.out.println(partiallyBadFile);
            }
        }


    }

}
