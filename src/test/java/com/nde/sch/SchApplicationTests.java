package com.nde.sch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@EnableScheduling
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class SchApplicationTests {
    @Test
    void contextLoads() {
    }

}
