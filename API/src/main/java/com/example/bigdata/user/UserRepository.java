package com.example.bigdata.user;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    List<User> findByNameStartsWith(String name);

    @Query("SELECT u.Address, COUNT(u) FROM User u WHERE u.Address IN :addresses GROUP BY u.Address")
    List<Object[]> countUsersGroupByAddress(@Param("addresses") List<String> addresses);
}
