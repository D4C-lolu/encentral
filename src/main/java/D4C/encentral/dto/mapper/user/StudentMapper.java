package D4C.encentral.dto.mapper.user;

import D4C.encentral.dto.mapper.subject.SubjectMapper;
import D4C.encentral.dto.user.student.StudentCreationDTO;
import D4C.encentral.dto.user.student.StudentDTO;
import D4C.encentral.dto.user.student.StudentSubjectsDTO;
import D4C.encentral.model.subject.Subject;
import D4C.encentral.model.user.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Class for Mapping Student DTOs and Student objects
 */
@Mapper(uses={SubjectMapper.class})
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper( StudentMapper.class );

    Student creationDTOtoStudent(StudentCreationDTO studentCreationDTO);


    Student dtoToStudent(StudentDTO studentDTO);


    @Mapping(source = "regDate", target = "regDate", dateFormat = "dd.MM.yyyy")
    StudentDTO studentToDTO(Student student);

    @Mapping(source="firstName",target="student.firstName")
    @Mapping(source="lastName",target="student.lastName")
    @Mapping(source="regNo",target="student.regNo")
    @Mapping(source="regDate",target="student.regDate", dateFormat = "dd.MM.yyyy")
    @Mapping(source="year",target="student.year")
    @Mapping(source="teacher",target="student.teacher")
    StudentSubjectsDTO toStudentSubjectsDTO(Student student);
}
