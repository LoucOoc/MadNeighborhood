package tech.madneighborhood.accounts.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.madneighborhood.accounts.dto.UserInfo;
import tech.madneighborhood.accounts.dto.UserSignup;
import tech.madneighborhood.accounts.entity.User;
import tech.madneighborhood.accounts.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserSignup userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhone(userDto.getPhone());

        userRepository.save(user);
    }


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserInfo> findAllUsers() {
        return userRepository.findAll().stream().map(this::mapToUserInfo).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id.intValue());
    }

    public UserInfo mapToUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setName(user.getName());
        userInfo.setEmail(user.getEmail());
        userInfo.setPhone(user.getPhone());


        userInfo.setPosts(parseStringToList(user.getPosts()));
        userInfo.setItem_checked_out(parseStringToList(user.getItem_checked_out()));
        userInfo.setItem_others_checked(parseStringToList(user.getItem_others_checked()));

        return userInfo;
    }

    private List<Long> parseStringToList(String string){
        if(string.isBlank()){
            return new ArrayList<>(1);
        }else{
            return Arrays.stream(string.split(",")).map(Long::parseLong).collect(Collectors.toList());
        }
    }

}
