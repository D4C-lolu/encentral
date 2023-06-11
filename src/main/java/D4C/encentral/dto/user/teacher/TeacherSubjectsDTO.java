package D4C.encentral.dto.user.teacher;

import D4C.encentral.dto.subject.SubjectDTO;

import java.util.Set;

/**
 * A DTO containting the list of all subjects a teacher is taking
 */
public class TeacherSubjectsDTO {
    private Set<SubjectDTO> subjects;

    private TeacherDTO teacher;

    public TeacherSubjectsDTO() {
    }

    public Set<SubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectDTO> subjects) {
        this.subjects = subjects;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "TeacherSubjectsDTO{" +
                "subjects=" + subjects +
                ", teacher=" + teacher +
                '}';
    }
}
