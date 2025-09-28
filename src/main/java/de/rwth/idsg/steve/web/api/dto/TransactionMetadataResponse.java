package de.rwth.idsg.steve.web.api.dto;

import lombok.Value;

import java.util.List;

@Value
public class TransactionMetadataResponse {
    List<String> chargeBoxIds;
    List<String> idTags;
}
