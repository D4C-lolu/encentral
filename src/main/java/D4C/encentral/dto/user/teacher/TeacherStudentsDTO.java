package D4C.encentral.dto.user.teacher;

import D4C.encentral.dto.user.student.StudentDTO;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

/**
 * A DTO for returning the list of students a teacher is currently responsible for
 */
public class TeacherStudentsDTO {
    @JsonProperty("teacher_students")
    private Set<StudentDTO> students;

    @JsonProperty("teacher")
    private TeacherDTO teacher;

    public TeacherStudentsDTO() {
    }

    public Set<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentDTO> students) {
        this.students = students;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "TeacherStudentsDTO{" +
                "students=" + students +
                ", teacher=" + teacher +
                '}';
    }
}
