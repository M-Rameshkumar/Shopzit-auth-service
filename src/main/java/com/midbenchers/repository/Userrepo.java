package com.midbenchers.repository;


import com.midbenchers.entity.User;
import com.midbenchers.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Userrepo extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String username);

    List<User> findAllByRole(UserRole userRole);
}
