package com.ambrosoft.test;

/**
 * Created on 3/21/18
 */
public class TestEnum {
    enum Letters {

        A("a"),
        B("b"),
        C("c");

        private String name;

        Letters(String letter) {
            this.name = letter;
        }
    }
}
