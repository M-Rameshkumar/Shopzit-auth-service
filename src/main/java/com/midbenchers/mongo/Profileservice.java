package com.midbenchers.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Profileservice {



  @Autowired
  private Profilerepo repo;


    public Profile_entity getprofiledata(Long id) {

        Optional<Profile_entity> profile=repo.findByUserId(id);

        return  profile.orElse(null);

    }
}
