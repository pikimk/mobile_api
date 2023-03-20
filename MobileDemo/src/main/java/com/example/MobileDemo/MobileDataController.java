package com.example.MobileDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MobileDataController {

    @Autowired
    private JdbcTemplateQueryService jdbcTemplateQueryService;

    @Autowired
    private UserRepository uRepository;

    @Autowired
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    };

    //get data
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    Object getMobileData(@RequestHeader(value = "Email") String email,
                         @RequestHeader(value = "Password") String password){
        Optional<User> user = uRepository.findById(email);
        if (user.isPresent()){
           User u = user.get();

           //check if pass match
           if( passwordEncoder().matches(password, u.getPassword())){
               //all ok
               //return all from table
               return jdbcTemplateQueryService.getMobileData(u.getTable());

           }else{
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                       new DefaultResponse("Incorrect Password", false)
               );
           }

        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new DefaultResponse("User not found", false)
            );
        }


    }

    //add record
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    Object insertMobileData(@RequestHeader(value = "Email") String email,
                            @RequestHeader(value = "Password") String password,
                            @RequestBody MobileData data){

        //check if all is empty
        if(data.getManufacturer() == null && data.getQuantity() == null
          && data.getPart() == null && data.getModel() == null){
            //all are null
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DefaultResponse("All fields are empty!", false));
        }
        //check every part of data
        if (data.getManufacturer() == null || data.getManufacturer().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DefaultResponse("Empty manufacturer field!", false));
        } else if (data.getModel() == null || data.getModel().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DefaultResponse("Empty model field!", false));
        } else if (data.getPart() == null || data.getPart().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DefaultResponse("Empty part field!", false));
        } else if(data.getQuantity() == null || data.getQuantity() < 1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DefaultResponse("Quantity cannot be empty or less than zero!", false));
        }


        Optional<User> user = uRepository.findById(email);
        if (user.isPresent()) {
            User u = user.get();
            if( passwordEncoder().matches(password, u.getPassword())){
                //all ok
                return jdbcTemplateQueryService.insertMobileData(data, u.getTable());

            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new DefaultResponse("Incorrect Password", false)
                );
            }

        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DefaultResponse("User not found", false)
            );
        }

    }

    //update
    @RequestMapping(value = "/data", method = RequestMethod.PUT)
    Object updateMobileData(@RequestHeader(value = "Email") String email,
                            @RequestHeader(value = "Password") String password,
                            @RequestBody MobileData data) {
        //check user


        //check if id is empty or null
        if (data.getId() == null || data.getId() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DefaultResponse("ID must not be empty or less than 0", false)
            );
        }

        Optional<User> user = uRepository.findById(email);
        if (user.isPresent()) {
            User u = user.get();
            if( passwordEncoder().matches(password, u.getPassword())){
                //all ok
                return jdbcTemplateQueryService.updateRecordById(u.getTable(),data);

            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new DefaultResponse("Incorrect Password", false)
                );
            }

        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DefaultResponse("User not found", false)
            );
        }

    }

    //delete
    @RequestMapping(value = "/data", method = RequestMethod.DELETE)
    Object deleteMobileData(@RequestHeader(value = "Email") String email,
                            @RequestHeader(value = "Password") String password,
                            @RequestBody IdSingle idSingle) {
        //check user

        Long id = idSingle.getId();
        //check if id is empty or null
        if (id == null || id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DefaultResponse("ID must not be empty or less than 0", false)
            );
        }

        Optional<User> user = uRepository.findById(email);
        if (user.isPresent()) {
            User u = user.get();
            if( passwordEncoder().matches(password, u.getPassword())){
                //all ok
                return jdbcTemplateQueryService.deleteRecordById(u.getTable(),id);

            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new DefaultResponse("Incorrect Password", false)
                );
            }

        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DefaultResponse("User not found", false)
            );
        }

    }
}
