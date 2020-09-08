package dev.allanlarangeiras.kafkapoc.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PresenceDTO {

    private Long eventTimestamp;
    private String clientMacAddress;
    private String apMacAddress;

}
