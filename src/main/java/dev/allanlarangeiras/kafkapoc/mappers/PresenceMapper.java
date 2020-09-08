package dev.allanlarangeiras.kafkapoc.mappers;

import dev.allanlarangeiras.kafkapoc.dto.PresenceDTO;
import dev.allanlarangeiras.kafkapoc.proto.Presence;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PresenceMapper {

    public List<PresenceDTO> mapFrom(Presence.presence_event presenceEvent) {
        long timestamp = presenceEvent.getTimestamp();
        List<Presence.proximity> proximityList = presenceEvent.getPaProximityEvent().getProximityList();
        return proximityList
                .stream()
                .map(proximity -> {
                    return PresenceDTO
                            .builder()
                            .eventTimestamp(timestamp)
                            .clientMacAddress(proximity.getStaEthMac().getAddr().toStringUtf8())
                            .apMacAddress(proximity.getApEthMac().getAddr().toStringUtf8())
                            .build();
                }).collect(Collectors.toList());

    }

}
