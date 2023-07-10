package com.hcc.repositories;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcc.entities.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);


    User findByVerificationCode(String code);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.username = :#{#updatedUser.username}, u.password = :#{#updatedUser.password}, u.cohortStartDate = :#{#updatedUser.cohortStartDate}, u.isVerified = :#{#updatedUser.isVerified}, u.verificationCode = :#{#updatedUser.verificationCode}, u.verificationExpiry = :#{#updatedUser.verificationExpiry} WHERE u.id = :id")
    void updateUserById(Long id, @Param("updatedUser") User updatedUser);

}
