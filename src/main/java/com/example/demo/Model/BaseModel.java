package com.example.demo.Model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

//import javax.persistence.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data

public abstract class BaseModel implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
//    @Convert(converter = UUIDToStringConverter.class)
    @Column(unique = true, updatable = true, nullable = false)
    private UUID id;

    public void setId(UUID id) {
        this.id = id;
    }
}
