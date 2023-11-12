package tech.madneighborhood.accounts.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfo {


    private Long id;

    private String name;

    private String email;

    private String phone;

    private List<Long> posts;

    private List<Long> item_others_checked;

    private List<Long> item_checked_out;


}
