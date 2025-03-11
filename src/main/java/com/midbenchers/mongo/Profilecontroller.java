package com.midbenchers.mongo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userinfo")
public class Profilecontroller {


    @Autowired
    private Profileservice service;



    @GetMapping("/{id}/profile")
    public Profile_entity profiledata(@PathVariable Long id){

        return service.getprofiledata(id);
    }


}
