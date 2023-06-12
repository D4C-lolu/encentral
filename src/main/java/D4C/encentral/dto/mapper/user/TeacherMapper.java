package D4C.encentral.dto.mapper.user;

import D4C.encentral.dto.mapper.subject.SubjectMapper;
import D4C.encentral.dto.user.teacher.TeacherCreationDTO;
import D4C.encentral.dto.user.teacher.TeacherDTO;
import D4C.encentral.dto.user.teacher.TeacherStudentsDTO;
import D4C.encentral.dto.user.teacher.TeacherSubjectsDTO;
import D4C.encentral.model.user.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Class for Mapping Teacher DTOs and Teacher objects
 */
@Mapper(uses={SubjectMapper.class})
public interface TeacherMapper {
    TeacherMapper INSTANCE = Mappers.getMapper( TeacherMapper.class );

    Teacher creationDTOtoTeacher(TeacherCreationDTO teacherCreationDTO);

    Teacher dtoToTeacher(TeacherDTO teacherDTO);

    @Mapping(source = "regDate", target = "regDate", dateFormat = "dd.MM.yyyy")
    TeacherDTO teacherToDTO(Teacher teacher);

    @Mapping(source="firstName",target="teacher.firstName")
    @Mapping(source="lastName",target="teacher.lastName")
    @Mapping(source="regNo",target="teacher.regNo")
    @Mapping(source="regDate",target="teacher.regDate", dateFormat = "dd.MM.yyyy")
    TeacherStudentsDTO toTeacherStudentsDTO(Teacher teacher);

    @Mapping(source="firstName",target="teacher.firstName")
    @Mapping(source="lastName",target="teacher.lastName")
    @Mapping(source="regNo",target="teacher.regNo")
    @Mapping(source="regDate",target="teacher.regDate", dateFormat = "dd.MM.yyyy")
    TeacherSubjectsDTO toTeacherSubjectsDTO(Teacher teacher);

}
