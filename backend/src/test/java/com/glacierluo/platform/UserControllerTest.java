package com.glacierluo.platform;

import com.glacierluo.platform.controllers.MainController;
import org.apache.commons.collections4.MultiValuedMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {


    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Before
    public  void setUp() throws Exception{
        mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void changePassword() throws Exception{
        String password = "asfasf";
        String uri="/changePassword";


    }

}
