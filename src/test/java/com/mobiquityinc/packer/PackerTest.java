package com.mobiquityinc.packer;

import com.mobiquityinc.packer.exception.APIException;
import com.mobiquityinc.packer.exception.Error;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for {@link com.mobiquityinc.packer.Packer}  Packer} class
 */
public class PackerTest {
    private static final String COMMON_INPUT_SOLUTION = "4\n-\n2,7\n8,9\n";
    private static final String COMMON_INPUT = PackerTest.class
            .getResource("common_input").getPath();
    private static final String TOO_MANY_ITEMS_TEST = PackerTest.class
            .getResource("too_many_items_test").getPath();
    private static final String MUST_CONTAIN_ONE_CONON_TEST= PackerTest.class
            .getResource("must_contain_one_colon_test").getPath();
    private static final String MAX_WEIGHT_SHOULD_BE_NUMBER_TEST = PackerTest.class
            .getResource("max_weight_should_be_number_test").getPath();
    private static final String NO_SUCH_FILE_TEST = "no_such_file_test";
    private static final String MAX_WEIGHT_TOO_BIG_TEST = PackerTest.class
            .getResource("max_weight_too_big_test").getPath();

    @Test
    public void commonTest() {
        try {
            assertEquals(Packer.pack(COMMON_INPUT), COMMON_INPUT_SOLUTION);
        } catch (APIException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void fileNotFoundTest() {
        try {
            Packer.pack(NO_SUCH_FILE_TEST);
            fail();
        } catch (APIException e) {
            assertEquals(e.getError(), Error.FILE_NOT_FOUND);
        }
    }

    @Test
    public void tooManyItemsTest() {
        try {
            Packer.pack(TOO_MANY_ITEMS_TEST);
            fail();
        } catch (APIException e) {
            assertEquals(e.getError(), Error.TOO_MANY_ITEMS);
        }
    }

    @Test
    public void mustContainOneColonTest() {
        try {
            Packer.pack(MUST_CONTAIN_ONE_CONON_TEST);
            fail();
        } catch (APIException e) {
            assertEquals(e.getError(), Error.MUST_CONTAIN_ONE_COLON);
        }
    }

    @Test
    public void maxWeightShouldBeNumberTest() {
        try {
            Packer.pack(MAX_WEIGHT_SHOULD_BE_NUMBER_TEST);
            fail();
        } catch (APIException e) {
            assertEquals(e.getError(), Error.MAX_WEIGHT_MUST_BE_NUMBER);
        }
    }

    @Test
    public void maxWeightTooBigTest() {
        try {
            Packer.pack(MAX_WEIGHT_TOO_BIG_TEST);
            fail();
        } catch (APIException e) {
            assertEquals(e.getError(), Error.WRONG_MAX_WEIGHT);
        }
    }
}