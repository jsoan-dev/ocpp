package de.rwth.idsg.steve.web.api.dto;

import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class UserMetadataResponse {
    Map<String, String> countryCodes;
    List<String> availableIdTags;
}
