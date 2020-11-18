package ru.klonwar.checkers.models.database;

import org.junit.Assert;
import org.junit.Test;

public class MD5Test {

    @Test
    public void md5WorksCorrectly() {
        String plain = "test";
        String correctMD5 = "098f6bcd4621d373cade4e832627b4f6";

        Assert.assertEquals(MD5.getMD5(plain), correctMD5);
    }
}
