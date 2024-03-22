package com.movie.movieinfo.Repository;

import com.movie.movieinfo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(String userId);
    Optional<User> findByUserName(String userName);

    List<User> findByUserEmail(String email);
}