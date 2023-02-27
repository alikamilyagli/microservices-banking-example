package com.cenoa.history.domain.document;

import com.cenoa.common.event.EventType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document("history")
public class History {

    @Id
    private String id;
    private String resource;
    private EventType eventType;
    private String description;
    private LocalDateTime createdAt;

}
