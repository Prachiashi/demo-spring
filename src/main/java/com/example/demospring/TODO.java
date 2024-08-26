package com.example.demospring;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.Setter;
import java.time.LocalDateTime;
@Data
@Setter
@JsonDeserialize
public class TODO {
    int id;
    String description;
    LocalDateTime  dateTime;
    boolean isCompleted;

    public TODO(int _id, String _desc, LocalDateTime dateTime, boolean _value){
        id=_id;
        description = _desc;
        this.dateTime  = dateTime ;
        isCompleted = _value;

    }
}