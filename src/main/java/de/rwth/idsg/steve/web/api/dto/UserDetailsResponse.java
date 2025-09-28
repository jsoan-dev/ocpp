package de.rwth.idsg.steve.web.api.dto;

import de.rwth.idsg.steve.repository.dto.User;
import de.rwth.idsg.steve.utils.mapper.UserFormMapper;
import de.rwth.idsg.steve.web.dto.UserForm;
import jooq.steve.db.tables.records.AddressRecord;
import jooq.steve.db.tables.records.UserRecord;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Accessors(fluent = true)
public final class UserDetailsResponse {

    private final UserForm form;
    private final List<String> availableIdTags;
    private final UserRecord userRecord;
    private final AddressRecord addressRecord;

    public static UserDetailsResponse of(User.Details details, List<String> availableIdTags) {
        UserForm form = UserFormMapper.toForm(details);
        return new UserDetailsResponse(form, availableIdTags, details.getUserRecord(), details.getAddress());
    }
}
