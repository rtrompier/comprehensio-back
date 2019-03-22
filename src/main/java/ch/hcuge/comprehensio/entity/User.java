package ch.hcuge.comprehensio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"langs"})
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isAvailable;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_langs", 
    		joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, 
    		inverseJoinColumns = {@JoinColumn(name = "lang_id", referencedColumnName = "id")}
    )
    private Set<Lang> langs;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

}
