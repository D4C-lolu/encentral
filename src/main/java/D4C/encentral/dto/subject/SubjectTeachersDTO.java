package D4C.encentral.dto.subject;

import D4C.encentral.dto.user.teacher.TeacherDTO;
import java.util.Set;

/**
 * DTO representing a list of all the teachers in charge of a subject
 */
public class SubjectTeachersDTO {
    private Set<TeacherDTO> teachers;

    private SubjectDTO subject;

    public SubjectTeachersDTO(){

    }

    public Set<TeacherDTO> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<TeacherDTO> teachers) {
        this.teachers = teachers;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "SubjectTeachersDTO{" +
                "teachers=" + teachers +
                ", subject=" + subject +
                '}';
    }
}
