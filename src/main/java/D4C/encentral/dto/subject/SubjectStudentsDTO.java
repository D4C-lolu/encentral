package D4C.encentral.dto.subject;

import D4C.encentral.dto.user.student.StudentDTO;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

/**
 * DTO representing a list of all students offering a subject
 */
public class SubjectStudentsDTO {
    @JsonProperty("subject_students")
    private Set<StudentDTO> students;

    @JsonProperty("subject")
    private SubjectDTO subject;

    public SubjectStudentsDTO(){

    }

    public Set<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentDTO> students) {
        this.students = students;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "SubjectStudentsDTO{" +
                "students=" + students +
                ", subject=" + subject +
                '}';
    }
}
