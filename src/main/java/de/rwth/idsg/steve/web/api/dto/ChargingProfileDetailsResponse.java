package de.rwth.idsg.steve.web.api.dto;

import de.rwth.idsg.steve.repository.dto.ChargingProfile;
import de.rwth.idsg.steve.utils.mapper.ChargingProfileDetailsMapper;
import de.rwth.idsg.steve.web.dto.ChargingProfileForm;
import jooq.steve.db.tables.records.ChargingProfileRecord;
import jooq.steve.db.tables.records.ChargingSchedulePeriodRecord;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Accessors(fluent = true)
public final class ChargingProfileDetailsResponse {

    private final ChargingProfileForm form;
    private final ChargingProfileRecord profileRecord;
    private final List<ChargingSchedulePeriodRecord> schedulePeriods;

    public static ChargingProfileDetailsResponse of(ChargingProfile.Details details) {
        ChargingProfileForm form = ChargingProfileDetailsMapper.mapToForm(details);
        return new ChargingProfileDetailsResponse(form, details.getProfile(), details.getPeriods());
    }
}
