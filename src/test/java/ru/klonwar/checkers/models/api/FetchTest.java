package ru.klonwar.checkers.models.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.klonwar.checkers.models.database.APIDatabase;

import java.io.FileInputStream;
import java.util.Properties;

public class FetchTest {
    String testLink;
    @Before
    public void initObjects() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("./app.properties"));
            testLink = p.get("api-test-link").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void fetchWorksCorrectly() {
        Fetch fetch = new Fetch(testLink + "/search-game/" + 0);
        String response = fetch.fetch();

        Assert.assertEquals(response.trim(), "[]");
    }
}
