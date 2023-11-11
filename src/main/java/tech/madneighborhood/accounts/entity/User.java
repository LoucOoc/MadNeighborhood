package tech.madneighborhood.accounts.entity;

import jakarta.persistence.*;
import lombok.*;
import tech.madneighborhood.post.Post;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name="users")
public class User {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany
    private List<Post> posts = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

}
