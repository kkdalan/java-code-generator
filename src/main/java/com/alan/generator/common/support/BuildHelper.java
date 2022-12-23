package com.alan.generator.common.support;

import com.cmeza.sdgenerator.support.maker.values.CommonValues;

public class BuildHelper {

    public static String buildBodyAtNewLine(String bodyLine) {
        return CommonValues.TAB.getValue() + bodyLine + CommonValues.NEWLINE.getValue();
    }
}
