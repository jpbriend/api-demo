package fr.mudak.apidemo;

import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatefulRedisConnection<String, String> connection;

    private RedisCommands<String, String> command;

    @Before
    public void setUup() {
        command = mock(RedisCommands.class);
        when(connection.sync()).thenReturn(command);
    }


    @Test
    public void message() throws Exception {
        when(command.get("1")).thenReturn("Unit Testing GET message");

        this.mockMvc.perform(get("/message/1").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{'id': 1, 'content': 'Unit Testing GET message'}"));
    }

    @Test
    public void message1() throws Exception {
        when(command.set("777", "Unit Test content")).thenReturn("");

        String content = "{\"id\": \"777\", \"content\": \"Unit Test content\"}";
        this.mockMvc.perform(put("/message")
                .contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andExpect(content().bytes("Message added".getBytes()));
    }

    @Test
    public void messages() throws Exception {
        List<String> keys = Arrays.asList("42", "666");
        when(command.keys("*")).thenReturn(keys);
        when(command.get("42")).thenReturn("I love unit tests !");
        when(command.get("666")).thenReturn("UT all the things");

        this.mockMvc.perform(get("/messages").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("[{'id': 42, 'content': 'I love unit tests !'}, {'id': 666, 'content': 'UT all the things'}]"));
    }
}
