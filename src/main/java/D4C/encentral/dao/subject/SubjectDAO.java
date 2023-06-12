package D4C.encentral.dao.subject;

import D4C.encentral.dto.subject.SubjectDTO;
import D4C.encentral.dto.subject.SubjectStudentsDTO;
import D4C.encentral.dto.subject.SubjectTeachersDTO;
import D4C.encentral.dto.user.student.StudentSubjectsDTO;
import D4C.encentral.dto.user.teacher.TeacherDTO;
import D4C.encentral.dto.user.teacher.TeacherSubjectsDTO;

import java.util.List;
import java.util.Optional;

/**
 * DAO Interface for Subject
 */
public interface SubjectDAO {

    /**
     * Fetches the subject DTO for a given subject code
     * @param courseCode - Subject code for subject
     * @return Subject DTO
     */
    Optional<SubjectDTO> get(String courseCode);

    /**
     * Fetches a list of all subjects
     * @return List of subject DTO
     */
    List<SubjectDTO> getAll();


    /**
     * Adds a subject to the DB
     * @param subjectDTO
     */
    void save(SubjectDTO subjectDTO);

    /**
     * Update name field of subject
     * @param courseCode -Subject's subjectCode
     * @param name
     */
    void updateName(String courseCode, String name);

    /**
     * Remove subject from list
     * @param courseCode - Subject's subjectCode
     */
    void delete(String courseCode);

    /**
     * Add teacher to list of teachers taking a subject
     * @param courseCode - Subject's subjectCode
     * @param teacherRegNo - Teacher's registration number
     */
    void addTeacher(String courseCode,Long teacherRegNo);

    /**
     * Remove teacher from list of teachers taking a subject
     * @param courseCode - Subject's subjectCode
     * @param teacherRegNo - Teacher's registration number
     */
    void removeTeacher(String courseCode,Long teacherRegNo);

    /**
     * Add student to list of students offering a subject
     * @param courseCode - Subject's subjectCode
     * @param studentRegNo - Student's registration number
     */
    void addStudent(String courseCode,Long studentRegNo);

    /**
     * Remove student from list of students offering a subject
     * @param courseCode - Subject's subjectCode
     * @param studentRegNo - Student's registration number
     */
    void removeStudent(String courseCode,Long studentRegNo);

    /**
     * Gets the List of Teachers teaching a course
     * @param courseCode - Subject's subjectCode
     * @return DTO containing a list of Teachers and a subject
     */
    Optional<SubjectTeachersDTO> getTeachers(String courseCode);

    /**
     * Gets the List of Students offering a course
     * @param courseCode - Subject's subjectCode
     * @return DTO containing a list of Students and a subject
     */
    Optional<SubjectStudentsDTO> getStudents(String courseCode);

}
