package com.GreetingApp.GreetingApp.Repository;

import com.GreetingApp.GreetingApp.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface GreetingRepository extends JpaRepository<User,Long> {
}
