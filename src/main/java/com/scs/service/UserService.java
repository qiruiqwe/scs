package com.scs.service;
import com.scs.po.Activity;
import com.scs.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User checkUser(String username,String password);

    User saveUser(User user);

    User getUserById(Long id);

    Page<User> getUsersByClubId(Long id , Pageable pageable);

}
