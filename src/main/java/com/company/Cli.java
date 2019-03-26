package com.company;
import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;

public class Cli {
    /**
     * Class for parsing and validating command line arguments
     */

    private static String USAGE = "USAGE: sort-it -a | -d  -i | -s out.txt in1.txt in2.txt";
    private List<String> inputFiles;
    private String outputFile;
    private boolean isDescend;
    private boolean isInteger;
    private boolean isBuilt;

    /**
     * Parses command line arguments.
     * Throws exception in case of any invalid input.
     * If succeeds, any parameters are available through corresponding getters.
     */
    public void tryBuild(String[] args) throws Exception {
        if (isBuilt) {
            return;
        }

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        Options options = this.buildOptions();

        // try parsing cli
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
            // on fail print usage and throw exception
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.out.println(USAGE);
            formatter.printHelp("sort-it", options);
            throw e;
        }

        // check if file paths are given
        List<String> argList = cmd.getArgList();
        if (argList.size() < 2) {
            Exception e = new ParseException("Specify output file path and at least one input file path as arguments");
            System.out.println(e.getMessage());
            System.out.println(USAGE);
            throw e;
        }

        // initialize all parameters
        isDescend = cmd.hasOption('d');
        isInteger = cmd.hasOption('i');
        outputFile = argList.remove(0);
        inputFiles = argList;
        isBuilt = true;

    }

    public List<String> getInputFiles() {
        if (inputFiles == null) {
            return new ArrayList<>();
        }
        return inputFiles;
    }

    public Boolean getIsDescend() {
        return isDescend;
    }

    public Boolean getIsInteger() {
        return isInteger;
    }

    public String getOutputFile() {
        return this.outputFile;
    }

    private Options buildOptions() {
        Options options = new Options();

        // add type option group
        Option intType = new Option("i", false, "integer data type");
        Option stringType = new Option("s", false, "string data type");
        OptionGroup typeGroup = new OptionGroup();
        typeGroup.addOption(intType);
        typeGroup.addOption(stringType);
        options.addOptionGroup(typeGroup);

        // add order option group
        Option ascending= new Option("a", false, "ascending order");
        Option descending = new Option("d", false, "descending order");
        OptionGroup orderGroup = new OptionGroup();
        orderGroup.addOption(ascending);
        orderGroup.addOption(descending);
        options.addOptionGroup(orderGroup);

        return options;
    }
}
