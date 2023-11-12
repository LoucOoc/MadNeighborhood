package tech.madneighborhood.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.madneighborhood.accounts.entity.User;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name="Posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate checkin;

    @Column(nullable = false)
    private LocalDate checkout;

    // Constructors, getters, setters, and other methods...
}
