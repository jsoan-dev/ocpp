package de.rwth.idsg.steve.web.api.dto;

import de.rwth.idsg.steve.repository.dto.ChargingProfile;
import lombok.Value;

import java.util.List;

@Value
public class ChargingProfileMetadataResponse {
    List<ChargingProfile.BasicInfo> profiles;
    List<String> chargeBoxIds;
}
