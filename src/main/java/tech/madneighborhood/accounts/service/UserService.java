package tech.madneighborhood.accounts.service;

import tech.madneighborhood.accounts.dto.UserInfo;
import tech.madneighborhood.accounts.dto.UserSignup;
import tech.madneighborhood.accounts.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void saveUser(UserSignup userDto);

    User findUserByEmail(String email);

    List<UserInfo> findAllUsers();

    Optional<User> findUserById(Long id);


}
