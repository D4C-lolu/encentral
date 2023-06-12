package D4C.encentral.dao.subject;

import D4C.encentral.dto.subject.SubjectDTO;
import D4C.encentral.dto.subject.SubjectStudentsDTO;
import D4C.encentral.dto.subject.SubjectTeachersDTO;
import D4C.encentral.dto.user.student.StudentSubjectsDTO;
import D4C.encentral.dto.user.teacher.TeacherSubjectsDTO;

import java.util.List;

public interface SubjectDAO {

    boolean createSubject(SubjectDTO subjectDTO);

    StudentSubjectsDTO getStudentSubjects(Long regNo);

    TeacherSubjectsDTO getTeacherSubjects(Long regNo);

    SubjectStudentsDTO getAllStudents(String subjectCode);

    SubjectTeachersDTO getTeachers(String subjectCode);


}
