package com.example.bigdata.user;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Long userCount() {
        log.info("userCount()");
        return userRepository.count();
    }

//    @PostConstruct
    public void tempUsers() {
        log.info("tempUsers");
        List<User> users = UserGenerator.generateUsers(1000);
        userRepository.saveAll(users);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersStartsWith(String name) {
        return userRepository.findByNameStartsWith(name);
    }

    @Transactional(readOnly = true)
    public Map<String, Long> countUsersByAddress() {
        List<String> capitals = Arrays.asList("Tokyo", "London", "Paris", "New York", "Beijing", "Moscow", "Sydney", "Cairo", "Rome", "Rio de Janeiro");
        List<Object[]> results = userRepository.countUsersGroupByAddress(capitals);

        Map<String, Long> countByAddress = new HashMap<>();
        for (Object[] result : results) {
            String address = (String) result[0];
            Long count = (Long) result[1];
            countByAddress.put(address, count);
        }

        return countByAddress;
    }



}
