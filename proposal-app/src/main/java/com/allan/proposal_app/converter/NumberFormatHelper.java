package com.allan.proposal_app.converter;

import org.springframework.stereotype.Component;

import java.text.NumberFormat;

@Component
public class NumberFormatHelper {
    public String format(double value) {
        return NumberFormat.getNumberInstance().format(value);
    }
}