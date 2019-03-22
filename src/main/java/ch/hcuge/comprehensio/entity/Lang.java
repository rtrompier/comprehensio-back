package ch.hcuge.comprehensio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("users")
public class Lang {

    @Id
    private String id;
    private String label;

    @ManyToMany(mappedBy = "langs")
    private Set<User> users = new HashSet<>();

}
