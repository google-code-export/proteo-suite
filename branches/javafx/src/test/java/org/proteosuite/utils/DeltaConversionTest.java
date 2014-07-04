/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.utils;

import org.proteosuite.utils.compression.DeltaConversion;
import java.util.Arrays;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.proteosuite.utils.compression.DeltaConversion.DeltaEncodedDataFormatException;
import org.proteosuite.utils.compression.DeltaConversion.DeltaSourceDataFormatException;

/**
 *
 * @author sperkins
 */
public class DeltaConversionTest {

    private static double[] testData = new double[]{300.0009368894972, 300.00191827736586, 300.0028996700501, 300.00388106755, 300.28967246821986, 300.29065527323615, 300.29163808307743,
        300.29262089774363, 300.29360371723465, 300.2945865415508, 300.29556937069196, 300.29655220465804, 300.2975350434493, 300.29851788706566, 300.29949990872547,
        300.3004827619921, 300.30146562008395, 300.30244848300094, 301.2452648331065, 301.2462523331433, 301.24723983803574, 301.2482273477837, 301.2492148623875,
        301.250202381847, 301.2511899061622, 301.2521774353333, 301.2531649693602, 301.25415118803494, 301.2551387317737, 301.2561262803683, 301.2571138338188,
        302.733980093058, 302.7349749223015, 302.7359697564489, 302.73696459550007, 302.7379594394549, 302.7389542883138};
    private static Number[] testDataAsNumbers;

    @BeforeClass
    public static void setUp() {
        testDataAsNumbers = new Number[testData.length];
        for (int i = 0; i < testData.length; i++) {
            testDataAsNumbers[i] = testData[i];
        }
    }

    @Test
    public void testConversion() {
        try {
            Number[] testDataAsNumbersConvertedToDeltas = Arrays.copyOf(testDataAsNumbers, testDataAsNumbers.length);
            DeltaConversion.toDeltaNumberFormat(testDataAsNumbersConvertedToDeltas);
            Number[] testDataAsNumbersConvertedBackFromDeltas = Arrays.copyOf(testDataAsNumbersConvertedToDeltas, testDataAsNumbersConvertedToDeltas.length);
            DeltaConversion.fromDeltaNumberFormat(testDataAsNumbersConvertedBackFromDeltas);

            for (int i = 0; i < testDataAsNumbers.length; i++) {
                assertEquals((Double) testDataAsNumbers[i], (Double) testDataAsNumbersConvertedBackFromDeltas[i], 0.0d);
            }
        } catch (DeltaSourceDataFormatException | DeltaEncodedDataFormatException d) {
            System.out.println(d.getLocalizedMessage());
        }
    }
}
