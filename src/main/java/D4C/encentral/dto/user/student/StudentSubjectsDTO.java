package D4C.encentral.dto.user.student;

import D4C.encentral.dto.subject.SubjectDTO;

import java.util.Set;

/**
 * DTO containing the list of subjects a student is offering
 */
public class StudentSubjectsDTO {

    private Set<SubjectDTO> subjects;

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
