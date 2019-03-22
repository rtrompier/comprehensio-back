package ch.hcuge.comprehensio.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = "users",ignoreUnknown=true)
public class Lang {

    @Id
    private String id;
    private String label;

    @ManyToMany(mappedBy = "langs")
    private Set<User> users = new HashSet<>();

}
