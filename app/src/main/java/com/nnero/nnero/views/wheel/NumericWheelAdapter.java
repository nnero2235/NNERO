
package com.nnero.nnero.views.wheel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *  Copyright 2010 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
 * Numeric Wheel adapter.
 */
public class NumericWheelAdapter implements WheelAdapter {

    /** The default min value */
    public static final int DEFAULT_MAX_VALUE = 9;

    /** The default max value */
    private static final int DEFAULT_MIN_VALUE = 0;

    public static final int FlOAT_TYPE = 11;

    public static final int INT_TYPE = 10;

    // Values
    private int minValue;

    private int maxValue;

    // format
    private String format;

    private int valueType;

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Default constructor
     */
    public NumericWheelAdapter() {
        this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
    }

    /**
     * Constructor
     * 
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     */
    public NumericWheelAdapter(int minValue, int maxValue) {
        this(minValue, maxValue, null);
    }

    /**
     * Constructor
     * 
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     * @param format the format string
     */
    public NumericWheelAdapter(int minValue, int maxValue, String format) {
        this(minValue,maxValue,format,INT_TYPE);
    }

    public NumericWheelAdapter(int minValue, int maxValue, String format, int type) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.format = format;
        this.valueType = type;

    }

    @Override
    public String getItem(int index) {
        if (valueType == INT_TYPE) {
            if (index >= 0 && index < getItemsCount()) {
                int value = minValue + index;
                return format != null ? String.format(format, value) : Integer.toString(value);
            }
        } else if (valueType == FlOAT_TYPE) {
            float devide = 0.5f;
            String weight;
            Pattern pattern = Pattern.compile("[\\d]+[\\.][0]");

            if (index >= 0 && index < getItemsCount()) {
                if (index < 16) {
                    weight = String.valueOf(minValue + ((index + 1) * devide));
                    Matcher matcher = pattern.matcher(weight);
                    if (matcher.matches()) {
                        weight = weight.split("\\.")[0];
                    }
                    return format != null ? String.format(format, weight) : weight;
                } else {
                    weight = String.valueOf(10 + (index - 15));
                    return format != null ? String.format(format, weight) : weight;

                }
            }

        }

        return null;
    }

    @Override
    public int getItemsCount() {
        return maxValue - minValue + 1;
    }

    @Override
    public int getMaximumLength() {
        int max = Math.max(Math.abs(maxValue), Math.abs(minValue));
        int maxLen = Integer.toString(max).length();
        if (minValue < 0) {
            maxLen++;
        }
        return maxLen;
    }
}
