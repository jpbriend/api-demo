package fr.mudak.apidemo;

import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MessageController {

    @Autowired
    private StatefulRedisConnection<String, String>  connection;

    @RequestMapping("/message/{id}")
    public Message message(@PathVariable String id) {
        RedisCommands<String, String> commands = connection.sync();
        return new Message(Integer.valueOf(id), commands.get(String.valueOf(id)));
    }

    @PutMapping("/message")
    public ResponseEntity<?> message(@RequestBody Message message) {
        RedisCommands<String, String> commands = connection.sync();
        commands.set(String.valueOf(message.getId()), message.getContent());
        return ResponseEntity.ok("Message added");
    }

    @RequestMapping("/messages")
    public List<Message> messages() {
        RedisCommands<String, String> commands = connection.sync();
        List<String> keys = commands.keys("*");
        return keys.stream()
                .map(k -> new Message(Long.valueOf(k), commands.get(k)))
                .collect(Collectors.toList());
    }
}
