package D4C.encentral.dao.user;

import D4C.encentral.dto.user.admin.AdminCreationDTO;
import D4C.encentral.dto.user.admin.AdminDTO;
import D4C.encentral.dto.user.student.StudentCreationDTO;
import D4C.encentral.dto.user.student.StudentDTO;
import D4C.encentral.dto.user.teacher.TeacherCreationDTO;
import D4C.encentral.dto.user.teacher.TeacherDTO;
import D4C.encentral.dto.user.teacher.TeacherSubjectsDTO;

import java.util.*;

/**
 * Interface for User Data Access Object
 */
public interface UserDAO {

    StudentDTO getStudent(Long regNo);

    TeacherDTO getTeacher(Long regNo);

    AdminDTO getAdmin(Long regNo);

    TeacherDTO getGuide(Long regNo);

    TeacherSubjectsDTO getTeacherStudents(Long regNo);

    boolean createStudent(StudentCreationDTO studentCreationDTO);

    boolean createTeacher(TeacherCreationDTO teacherCreationDTO);

    boolean createAdmin(AdminCreationDTO adminCreationDTO);

    boolean assignStudentToTeacher(Long regNo);

    boolean registerSubject(Long regNo, String subjectCode);

    boolean removeSubject(Long regNo, String subjectCode);

    boolean registerSubjects(Long regNo, List<String> subjectCodes);

    boolean removeSubjects(Long regNo,List<String> subjectCodes);

    boolean assignSubject(Long regNo,String subjectCode);

    boolean unassignSubject(Long regNo, String subjectCode);

    boolean editName(Long regNo);

    boolean editPassword(Long regNo);

    boolean deleteStudent(Long regNo);

    boolean deleteTeacher(Long regNo);

}
