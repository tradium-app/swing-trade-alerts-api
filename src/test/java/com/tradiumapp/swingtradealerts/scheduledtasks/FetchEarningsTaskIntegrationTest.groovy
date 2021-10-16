package com.tradiumapp.swingtradealerts.scheduledtasks


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FetchEarningsTaskIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private FetchEarningsTask task;

    @Test(groups = "integration")
    void testFetchAllStocks() {
        task.fetchUpcomingEarnings()
    }
}
