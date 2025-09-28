package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.ocpp.OcppVersion;
import lombok.Value;

import java.util.List;

@Value
public class OperationsChargePointMetadata {
    OcppVersion version;
    List<String> chargeBoxIds;
}
