package com.moj.digital.laa.common.constants;

public enum StandardString {
    NEW_LINE("<br>"),
    COLON_WITH_SPACE(" : ");

    private String str;

    StandardString(String str) {
        this.str = str;
    }

    public String str() {
        return str;
    }
}
