package D4C.encentral.dto.user.student;

import D4C.encentral.dto.subject.SubjectDTO;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

/**
 * DTO containing the list of subjects a student is offering
 */
public class StudentSubjectsDTO {

    @JsonProperty("student_subjects")
    private Set<SubjectDTO> subjects;

    @JsonProperty("student")
    private StudentDTO student;

    public StudentSubjectsDTO(){

    }

    public Set<SubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectDTO> subjects) {
        this.subjects = subjects;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "StudentSubjectsDTO{" +
                "subjects=" + subjects +
                ", student=" + student +
                '}';
    }
}
