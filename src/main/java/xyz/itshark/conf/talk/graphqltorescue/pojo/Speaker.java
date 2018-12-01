package xyz.itshark.conf.talk.graphqltorescue.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Speaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String twitter;
}