package com.alan.generator.junit.testcase.base;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@Disabled("This is a base class for controller test cases!")
public abstract class BaseControllerTest {


}