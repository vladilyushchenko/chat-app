package org.softarex.chat.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    @Id
    protected String id;
}