package com.rookies3.myspringbootlab;

public class MyEnvironment {

    private String mode;

    public MyEnvironment(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "MyEnvironment [mode=\" + mode + \"]";
    }
}
