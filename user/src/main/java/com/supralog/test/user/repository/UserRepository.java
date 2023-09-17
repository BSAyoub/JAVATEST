package com.supralog.test.user.repository;

import com.supralog.test.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    User findByFirstNameAndLastName(String firstName, String lastName);
    User findByEmail(String email);
}
