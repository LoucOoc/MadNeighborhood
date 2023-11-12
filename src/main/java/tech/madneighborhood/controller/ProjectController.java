package tech.madneighborhood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.madneighborhood.accounts.authentication.UserAuthenticationManager;
import tech.madneighborhood.accounts.dto.UserInfo;
import tech.madneighborhood.accounts.entity.User;
import tech.madneighborhood.accounts.repository.UserRepository;
import tech.madneighborhood.accounts.service.UserServiceImpl;
import tech.madneighborhood.post.Comment;
import tech.madneighborhood.post.CommentRepository;
import tech.madneighborhood.post.Post;
import tech.madneighborhood.post.PostRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/test")
    public String test() {
        return "index";
    }


    @GetMapping({"/posts"})
    public ResponseEntity<List<Post>> getPosts(@RequestParam(name = "page") Integer page) {
        System.out.println("page: " + page);
        List<Post> posts = postRepository.findAll();
        posts.add(new Post(1L, "title", "content", LocalDate.now(), LocalDate.now(), 1L, "name", false, 0L, new ArrayList<>()));
        int lowerBound = page * 10;
        int upperBound = page * 10 + 10;
        if (lowerBound >= posts.size()) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        if (upperBound >= posts.size()) {
            upperBound = posts.size();
        }
        posts = posts.subList(lowerBound, upperBound);

        return ResponseEntity.ok(posts);
    }

    @PostMapping({"/create_post"})
    public ResponseEntity<String> createPost(@RequestParam String personal_token, @RequestParam String title, @RequestParam String content, @RequestParam String start_avail, @RequestParam String end_avail) {


        Long userId = UserAuthenticationManager.getUserId(personal_token);
        if (userId == null) {
            throw new RuntimeException("Invalid token");
        }


        // TODO: Remove hardcoding before pushing
        User user = userService.findUserById(userId).orElseThrow(); // userRepository.findByEmail("a@gmail.com"); //
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setStart_avail(LocalDate.parse(start_avail));
        post.setEnd_avail(LocalDate.parse(end_avail));
        post.setUser_name(user.getName());
        post.setUser_id(user.getId());
        post.setChecked_out(false);
        post.setUser_checkout_id(0L);
        post.setComments(new ArrayList<>());

        postRepository.save(post);

        String posts = user.getPosts();
        if (posts.isBlank()) {
            posts = post.getId().toString();
        } else {
            posts = posts + "," + post.getId().toString();
        }
        user.setPosts(posts);
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping({"/delete_post"})
    public ResponseEntity<String> deletePost(@RequestParam String personal_token, @RequestParam Integer post_id) {

        Long userId = UserAuthenticationManager.getUserId(personal_token);
        if (userId == null) {
            throw new RuntimeException("Invalid token");
        }



        // TODO: Remove hardcoding before pushing
        User user = /*userRepository.findAll().get(0);*/userRepository.findById(userId.intValue()).orElseThrow();

        Post post = postRepository.findById(post_id.longValue()).orElseThrow();
        postRepository.delete(post);

        //
        UserInfo userInfo = userService.mapToUserInfo(user);
        userInfo.getItem_others_checked().remove(post_id.longValue());
        String item_others_checked = convertListToString(userInfo.getItem_others_checked());
        user.setItem_others_checked(item_others_checked);
        userRepository.save(user);

        if(post.isChecked_out()) {
            User user2 = userRepository.findById(post.getUser_checkout_id().intValue()).orElseThrow();
            UserInfo userInfo2 = userService.mapToUserInfo(user2);
            userInfo2.getItem_checked_out().remove(post_id.longValue());
            String item_checked_out = convertListToString(userInfo2.getItem_checked_out());
            user2.setItem_checked_out(item_checked_out);
            userRepository.save(user2);
        }

        userInfo.getPosts().remove(post_id.longValue());
        String posts = convertListToString(userInfo.getPosts());
        user.setPosts(posts);
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping({"/create_checkout"})
    public ResponseEntity<String> createCheckout(@RequestParam String personal_token, @RequestParam Integer post_id, @RequestParam String end) {

        Long userId = UserAuthenticationManager.getUserId(personal_token);
        if (userId == null) {
            throw new RuntimeException("Invalid token");
        }


        User user = /* userRepository.findAll().get(0);*/userRepository.findById(userId.intValue()).orElseThrow();
        Post post = postRepository.findById(post_id.longValue()).orElseThrow();
        User postOwner = userRepository.findById(post.getUser_id().intValue()).orElseThrow();
        if (post.isChecked_out()) {
            throw new RuntimeException("Post is already checked out");
        }
        if (postOwner.getId().equals(user.getId())) {
            throw new RuntimeException("Cannot check out your own post");
        }
        post.setChecked_out(true);
        post.setUser_checkout_id(user.getId());
        post.setStart_avail(LocalDate.now());
        post.setEnd_avail(LocalDate.parse(end));
        postRepository.save(post);

        UserInfo userInfo = userService.mapToUserInfo(user);
        userInfo.getItem_checked_out().add(post_id.longValue());
        String item_checked_out = convertListToString(userInfo.getItem_checked_out());
        user.setItem_checked_out(item_checked_out);
        userRepository.save(user);

        userInfo = userService.mapToUserInfo(postOwner);
        userInfo.getItem_others_checked().add(post_id.longValue());
        String item_others_checked = convertListToString(userInfo.getItem_others_checked());
        postOwner.setItem_others_checked(item_others_checked);
        userRepository.save(postOwner);
        return ResponseEntity.ok().build();
    }

    // Create this method
    // /checkout_returned?personal_token={}&checkout_id={}
    @PostMapping({"/checkout_returned"})
    public ResponseEntity<String> checkoutReturned(@RequestParam String personal_token, @RequestParam Integer checkout_id) {

        Long userId = UserAuthenticationManager.getUserId(personal_token);
        if (userId == null) {
            throw new RuntimeException("Invalid token");
        }

        User postOwner = userRepository.findById(userId.intValue()).orElseThrow();
        Post post = postRepository.findById(checkout_id.longValue()).orElseThrow();
        if(!postOwner.getId().equals(post.getUser_id())){
            throw new RuntimeException("You are not the owner of this post");
        }
        post.setChecked_out(false);
        postRepository.save(post);

        UserInfo postOwnerInfo = userService.mapToUserInfo(postOwner);
        postOwnerInfo.getItem_others_checked().remove(checkout_id.longValue());
        String item_others_checked = convertListToString(postOwnerInfo.getItem_others_checked());
        postOwner.setItem_others_checked(item_others_checked);
        userRepository.save(postOwner);

        User user = userRepository.findById(post.getUser_checkout_id().intValue()).orElseThrow();
        UserInfo userInfo = userService.mapToUserInfo(user);
        userInfo.getItem_checked_out().remove(checkout_id.longValue());
        String item_checked_out = convertListToString(userInfo.getItem_checked_out());
        user.setItem_checked_out(item_checked_out);
        userRepository.save(user);

        return ResponseEntity.ok().build();

    }

    @GetMapping({"/get_post"})
    public ResponseEntity<Post> getPost(@RequestParam Integer id) {
        Post post = postRepository.findById(id.longValue()).orElseThrow();
        return ResponseEntity.ok(post);
    }


    // implement this method: /get_comments?personal_token={}&post_id={}
    @GetMapping({"/get_comments"})
    public ResponseEntity<List<Comment>> getComments(@RequestParam String personal_token, @RequestParam Integer post_id) {

        Long userId = UserAuthenticationManager.getUserId(personal_token);
        if (userId == null) {
            throw new RuntimeException("Invalid token");
        }

        Post post = postRepository.findById(post_id.longValue()).orElseThrow();
        return ResponseEntity.ok(post.getComments());
    }

    @PostMapping("/create_comment")
    public ResponseEntity<String> createComment(@RequestParam String personal_token, @RequestParam Long post_id, @RequestParam String content) {

        Long userId = UserAuthenticationManager.getUserId(personal_token);
        if (userId == null) {
            throw new RuntimeException("Invalid token");
        }

        User user = userRepository.findById(userId.intValue()).orElseThrow();
        Post post = postRepository.findById(post_id.longValue()).orElseThrow();

        Comment comment = new Comment();
        comment.setComment(content);
        comment.setUser_id(user.getId());
        comment.setUser_name(user.getName());
        commentRepository.save(comment);

        post.getComments().add(comment);
        postRepository.save(post);

        return ResponseEntity.ok().build();
    }

    private String convertListToString(List<Long> list) {
        StringBuilder sb = new StringBuilder();
        for (Long l : list) {
            sb.append(l.toString());
            sb.append(",");
        }
        if (!sb.isEmpty())
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
