package com.Impelsys.UserDemo.Repository;

import com.Impelsys.UserDemo.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    @Query(value = "SELECT * FROM userdata " +
            "where CONCAT(id, ' ', first_name, ' ', last_name, ' ', email, ' ', phone)" +
            " LIKE %?1% ",
            nativeQuery = true)
   Page <User> search(String keyword, Pageable pageable);

}