package com.example.MobileDemo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,String> {

    //@Query(value = "SELECT user_email FROM users", nativeQuery = true)


}
