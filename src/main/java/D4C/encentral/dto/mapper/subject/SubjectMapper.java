package D4C.encentral.dto.mapper.subject;

import D4C.encentral.dto.mapper.user.StudentMapper;
import D4C.encentral.dto.mapper.user.TeacherMapper;
import D4C.encentral.dto.subject.SubjectDTO;
import D4C.encentral.dto.subject.SubjectStudentsDTO;
import D4C.encentral.dto.subject.SubjectTeachersDTO;
import D4C.encentral.model.subject.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * DTO Mapper for Subject to SubjectDTO
 */
@Mapper(uses = {StudentMapper.class, TeacherMapper.class})
public interface SubjectMapper {

    SubjectMapper INSTANCE = Mappers.getMapper( SubjectMapper.class );
    SubjectDTO toSubjectDTO(Subject subject);
    Subject dtoToSubject(SubjectDTO subjectDTO);

    @Mapping(target="subject.name",source="name")
    @Mapping(target="subject.subjectCode",source="subjectCode")
    SubjectStudentsDTO toSubjectStudentsDTO(Subject subject);

    SubjectTeachersDTO toSubjectTeachersDTO(Subject subject);


}
