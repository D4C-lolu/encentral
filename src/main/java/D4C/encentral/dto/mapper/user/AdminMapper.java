package D4C.encentral.dto.mapper.user;

import D4C.encentral.dto.user.admin.AdminCreationDTO;
import D4C.encentral.dto.user.admin.AdminDTO;
import D4C.encentral.model.user.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Class for Mapping Admin DTOs and Admin objects
 */
@Mapper
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper( AdminMapper.class );

    Admin creationDTOtoAdmin(AdminCreationDTO adminCreationDTO);

    @Mapping(source = "regDate", target = "regDate", dateFormat = "dd.MM.yyyy")
    Admin dtoToAdmin(AdminDTO adminDTO);
}
