package com.cenoa.history.service.model;

import com.cenoa.history.domain.document.History;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDto {

    private String id;
    private String resource;
    private String eventType;
    private String description;

    public static HistoryDto from(final History history) {
        return HistoryDto.builder()
            .id(history.getId())
            .resource(history.getResource())
            .eventType(history.getEventType().name())
            .description(history.getDescription())
            .build();
    }

}
