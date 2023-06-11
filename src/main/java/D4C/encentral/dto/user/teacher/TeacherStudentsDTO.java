package D4C.encentral.dto.user.teacher;

import D4C.encentral.dto.user.student.StudentDTO;

import java.util.Set;

/**
 * A DTO for returning the list of students a teacher is currently responsible for
 */
public class TeacherStudentsDTO {
    private Set<StudentDTO> students;

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
