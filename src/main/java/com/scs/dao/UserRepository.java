package com.scs.dao;

import com.scs.po.Activity;
import com.scs.po.User;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> , JpaSpecificationExecutor<User> {

    User findUserByUsernameAndPassword(String username,String password);

    User findUserById(Long id);

}
