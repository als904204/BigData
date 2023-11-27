package com.example.bigdata.user;

import com.example.bigdata.test.MonthlySales;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/count")
    public ResponseEntity<Long> geUsersCount() {
        Long userCount = userService.userCount();
        return ResponseEntity.ok(userCount);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<User>> getUserInfoByName(@PathVariable("name") String name) {
        List<User> usersStartsWith = userService.getUsersStartsWith(name);
        return ResponseEntity.ok(usersStartsWith);
    }

    @GetMapping("/address")
    public ResponseEntity<Map<String, Long>> userCountByAddress() {
        log.info("endpoint : /api/user/address");
        Map<String, Long> userCount = userService.countUsersByAddress();
        return ResponseEntity.ok(userCount);
    }

    @GetMapping
    public String Hello() {
        log.info("HELLO");
        return "HELLO";
    }
}
