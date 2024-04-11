package com.movie.movieinfo.Repository;

import com.movie.movieinfo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(String userId);
    Optional<User> findByUserName(String userName);

    List<User> findByDbStsAndUserEmail(String dbSts,String userEmail);
    
    //가장 최근에 회원가입 된 계정으로 찿아오기
    Optional<User> findOneByDbStsAndUserEmailOrderBySignDateDesc(String dbSts,String userEmail);


    Integer countByUserEmail(String userEmail);

    User findByUserId(String userId);

    Optional<User> findByUserIdAndDbSts(String userId,String dbSts);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.dbSts = :dbSts WHERE u.userId = :userId")
    int updateUserDbSts(@Param("userId") String userId, @Param("dbSts") String dbSts);
}