package org.proteosuite.jopenms.util;

import java.util.ListIterator;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 *
 * @author Da Qi
 * @institute University of Liverpool
 * @time 21-Feb-2014 15:55:12
 */
public class ExtendedPosixParser extends PosixParser {

    private boolean ignoreUnrecognizedOption;

    public ExtendedPosixParser(final boolean ignoreUnrecognizedOption) {
        this.ignoreUnrecognizedOption = ignoreUnrecognizedOption;
    }

    @Override
    protected void processOption(final String arg, final ListIterator iter)
            throws ParseException {
        boolean hasOption = getOptions().hasOption(arg);

        if (hasOption || !ignoreUnrecognizedOption) {
            super.processOption(arg, iter);
        }
    }

}
