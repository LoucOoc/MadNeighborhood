package tech.madneighborhood.post;

import jakarta.persistence.*;
import lombok.*;
import tech.madneighborhood.accounts.dto.UserInfo;
import tech.madneighborhood.accounts.entity.User;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name="start_avail", nullable = false)
    private LocalDate start_avail;

    @Column(nullable = false)
    private LocalDate end_avail;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private String user_name;

    @Column(nullable = false)
    private boolean checked_out;

    @Column(nullable = false)
    private Long user_checkout_id;

    @OneToMany
    @Column(nullable = false)
    private List<Comment> comments;

    // Constructors, getters, setters, and other methods...
}
