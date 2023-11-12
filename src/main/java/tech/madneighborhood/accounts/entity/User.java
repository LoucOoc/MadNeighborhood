package tech.madneighborhood.accounts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty
    @Column(nullable = false)
    private String phone;

    @NotEmpty
    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String posts = "";

    @Column(nullable = true)
    private String item_others_checked = "";

    @Column(nullable = true)
    private String item_checked_out = "";

}
