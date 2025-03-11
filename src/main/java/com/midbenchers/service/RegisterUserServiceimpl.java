package com.midbenchers.service;



import com.midbenchers.dto.RegisterUser;
import com.midbenchers.dto.Userdto;
import com.midbenchers.entity.User;
import com.midbenchers.enums.UserRole;
import com.midbenchers.mongo.Profile_entity;
import com.midbenchers.mongo.Profilerepo;
import com.midbenchers.repository.Userrepo;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RegisterUserServiceimpl{



    @Autowired
    private Userrepo userrepo;


    @Autowired
    private Profilerepo profilerepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final Logger logger= LoggerFactory.getLogger(RegisterUserServiceimpl.class);


    @Transactional
    public ResponseEntity<?> createuser(RegisterUser user){


      Optional<User> userrecords= userrepo.findByEmail(user.getEmail());
      if(userrecords.isPresent()){
          return new ResponseEntity<>("user already Exist",HttpStatus.CONFLICT);
      }


            User data= new User();

        data.setName(user.getName());
        data.setEmail(user.getEmail());
        data.setRole(UserRole.CUSTOMER);
        data.setPassword(passwordEncoder.encode(user.getPassword()));


        User createduser= userrepo.save(data);


        Profile_entity profile= new Profile_entity(

                createduser.getId(),"","","",0l
        );

        profilerepo.save(profile);


         Userdto dto= new Userdto();
         dto.setId(createduser.getId());
        return new ResponseEntity<>(dto,HttpStatus.CREATED);


    }

    @Transactional
    @PostConstruct
    public  void Adminlogin() {

        List<User> list = userrepo.findAllByRole(UserRole.ADMIN);

        if (list.isEmpty()) {  // Check if admin exists
            User u = new User();
            u.setName("ramesh");
            u.setEmail("r@gmail.com");
            u.setPassword(passwordEncoder.encode("ramesh123"));
            u.setRole(UserRole.ADMIN);
            System.out.println("Admin user created successfully.");

            userrepo.save(u);
        } else {
            System.out.println("Admin already exists. Skipping creation.");
        }
    }



}
