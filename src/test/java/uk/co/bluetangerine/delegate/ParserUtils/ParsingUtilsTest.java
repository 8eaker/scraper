package uk.co.bluetangerine.delegate.ParserUtils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tony on 15/11/2016.
 */
public class ParsingUtilsTest {
    ParsingUtils underTest = new ParsingUtils();

    @Test
    public void givenStringContainsValidvalueThenextractPriceFromString() {
        String stringContainingCurrencyValue = "£10.99GBP";
        String result = underTest.extractPriceFromString(stringContainingCurrencyValue);
        Assert.assertEquals(result, "10.99");
    }

    @Test
    public void givenStringContainsNoMantissaThenReturnCharacteristicSpotZero() {
        String stringWithNoValue = "NoFactionalValue23Here";
        String result = underTest.extractPriceFromString(stringWithNoValue);
        Assert.assertEquals(result, "23.00");
    }

    @Test
    public void givenStringContainsNoValueThenReturnZero() {
        String stringWithNoValue = "NoValueHere";
        String result = underTest.extractPriceFromString(stringWithNoValue);
        Assert.assertEquals(result, "0.00");
    }

}