package tech.madneighborhood.accounts.service;

import tech.madneighborhood.accounts.dto.UserDto;
import tech.madneighborhood.accounts.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
