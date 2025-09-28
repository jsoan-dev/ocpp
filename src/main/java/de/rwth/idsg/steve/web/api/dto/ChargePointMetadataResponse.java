package de.rwth.idsg.steve.web.api.dto;

import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class ChargePointMetadataResponse {
    Map<String, String> countryCodes;
    List<String> defaultRegistrationStatuses;
    List<String> ocpp16RegistrationStatuses;
    List<String> upToOcpp15RegistrationStatuses;
    List<String> unknownChargeBoxIds;
}
