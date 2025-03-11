package com.midbenchers.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface Profilerepo extends MongoRepository<Profile_entity,String> {
    Optional<Profile_entity> findByUserId(Long userid);

}
