package com.mobiquityinc.packer;

import com.mobiquityinc.packer.exception.APIException;
import com.mobiquityinc.packer.exception.Error;
import com.mobiquityinc.packer.structure.Item;
import com.mobiquityinc.packer.structure.PackingTask;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mobiquityinc.packer.exception.Error.*;

/**
 * Class Packer contains static {@link com.mobiquityinc.packer.Packer#pack}  pack} method
 * for solving multiple knapsack problem defined in file.
 * Solves 0-1 Knapsack Problems.
 * Have maximum weight, weight, value and items number constraints.
 * Accepts floating point weight values up to n digits after the decimal point (weights will be multiplied by 100
 * to make them integer which is required for solving using dynamic programming algorithm)
 *
 */
public class Packer {
    private static final String INDEX = "index";
    private static final String WEIGHT = "weight";
    private static final String VALUE = "value";
    private static final int MAX_ITEMS = 15;
    private static final int MAX_WEIGHT = 100 * 100;
    private static final int MAX_VALUE = 100;
    private static final String PACKAGE_REGEX = String
            .format("\\((?<%s>\\d+),(?<%s>\\d+(\\.\\d{1,2})?),€(?<%s>\\d+(\\.\\d{1,2})?)\\)", INDEX, WEIGHT, VALUE);

    private static final String NEW_LINE = "\n";
    private static final String COLON = ":";

    /** Accept a path to file contains several lines.
     * Each line is one test case.
     * Each line contains the weight that the package can take (before the colon) and the list of things you
     * need to choose.
     * Each thing is enclosed in parentheses where the 1st number is a thing's index number,
     * the 2nd is its weight and the 3rd is its cost.
     * e.g: '81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)'
     *
     * @param filePath path to input task file
     * @return a string representation of solutions for every line in input file
     * @throws APIException in case of missing input file, wrong inputs, unparsable text, etc.
     */
    public static String pack(String filePath) throws APIException {
        // Reading file line by line
        StringBuilder result = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = bufferedReader.readLine()) != null) {
                PackingTask packingTask = parseLine(lineNumber, line);
                result.append(packingTask.solve().toString());
                result.append(NEW_LINE);
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            throw new APIException(e, FILE_NOT_FOUND, filePath);
        } catch (IOException e) {
            throw new APIException(e, CANNOT_READ_LINE);
        }

        return result.toString();
    }

    private static PackingTask parseLine(long lineNumber, String line) throws APIException {
        String[] spited = line.split(COLON);
        if (spited.length != 2) {
            throw new APIException(MUST_CONTAIN_ONE_COLON, lineNumber);
        }

        final int maxWeight;
        try {
            maxWeight = (int) (Double.parseDouble(spited[0]) * 100);
            if(maxWeight > MAX_WEIGHT) {
                throw new APIException(WRONG_MAX_WEIGHT, lineNumber, MAX_WEIGHT);
            }
        } catch (NumberFormatException e) {
            throw new APIException(e, MAX_WEIGHT_MUST_BE_NUMBER, lineNumber);
        }

        Pattern pattern = Pattern.compile(PACKAGE_REGEX);
        Matcher matcher = pattern.matcher(spited[1]);
        List<Item> items = new ArrayList<>();
        while (matcher.find()) {
            if (items.size() >= MAX_ITEMS) {
                throw new APIException(TOO_MANY_ITEMS, lineNumber, MAX_ITEMS);
            }

            int index = Integer.valueOf(matcher.group(INDEX));
            int weight = (int) (Double.valueOf(matcher.group(WEIGHT)) * 100);
            double value = Double.valueOf(matcher.group(VALUE));
            if (weight > MAX_WEIGHT || weight < 0) {
                throw new APIException(WRONG_WEIGHT, lineNumber, MAX_WEIGHT);
            }
            if (value > MAX_VALUE || value < 0) {
                throw new APIException(WRONG_VALUE, MAX_VALUE, lineNumber);
            }
            items.add(new Item(index, weight, value));
        }

        return new PackingTask(maxWeight, items);
    }
}
