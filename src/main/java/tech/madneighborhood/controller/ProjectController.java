package tech.madneighborhood.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import tech.madneighborhood.accounts.dto.UserDto;
import tech.madneighborhood.communities.repository.CommunityRepository;
import tech.madneighborhood.post.Post;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    CommunityRepository communityRepository;


    @GetMapping({"/signup"})
    public void signup(@Valid @ModelAttribute("user") UserDto userDto,
                       BindingResult result,
                       Model model) {
    }

    @GetMapping("/test")
    public String test(){
        return "Hello World!";
    }


    @GetMapping({"/posts"})
    public List<Post> getPosts(@RequestParam(name = "community") String community, @RequestParam(name = "page") Integer page) {
        Community community = communityRepository.findByName(community);


    }


}
