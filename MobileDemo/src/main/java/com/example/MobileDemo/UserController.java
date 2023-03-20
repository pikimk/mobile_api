package com.example.MobileDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;


    //get all users
    @RequestMapping(value = "/users",method =  RequestMethod.GET)
    public Iterable<User> getAll(){
        return userRepository.findAll();
    }

    //register new user
    @RequestMapping(value = "/register", method = RequestMethod.POST,
    consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity register(@RequestBody User user){
        if(     (user.getEmail() == null || user.getPassword() == null)     ||
                (user.getEmail().isEmpty() || user.getPassword().isEmpty()) ||
                (user.getEmail().isBlank() || user.getPassword().isBlank())
            ) {
           return ResponseEntity.status(HttpStatus.OK).body(
                   new RegisterResponse(false, "Fields email and password cannot be empty", null)
           );
        }

        //check email regex
        String regex =
                "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user.getEmail());
        if (!matcher.matches()){
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setMessage("Email not valid!");
            registerResponse.setSuccsess(false);
            return ResponseEntity.status(HttpStatus.OK).body(registerResponse);
        }

        String table = user.getEmail()
                .replaceAll("[^a-zA-Z0-9]","_") + "_table";
        User u = new User();
        u.setUser(passwordEncoder().encode(user.getPassword()), user.getEmail(), table);

        //user already exists
        if (userRepository.findById(u.getEmail()).isPresent()){

            System.out.println();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new RegisterResponse(false, "User already exists", null)
            );
        }else{

            User saved = userRepository.save(u);
            //create table
            jdbcTemplate.execute("CREATE TABLE " +
                    saved.getTable() +
                    " ( id SERIAL PRIMARY KEY," +
                    "manufacturer VARCHAR(50) NOT NULL," +
                    "model VARCHAR(50) NOT NULL," +
                    "part VARCHAR(50) NOT NULL," +
                    "quantity INT NOT NULL," +
                    "description TEXT )");

            return ResponseEntity.status(HttpStatus.OK).body(
                    new RegisterResponse(true, "created", saved )
            );
        }
    }

}
