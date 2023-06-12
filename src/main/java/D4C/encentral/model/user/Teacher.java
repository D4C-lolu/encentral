package D4C.encentral.model.user;

import D4C.encentral.model.subject.Subject;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


/**
 * Class representing Teacher object
 * <p>
 * Multiple teachers can take multiple courses
 * e.g 2 teachers may take a single course/subject together and 1 teacher may take 2 different courses/subjects.
 * <p>
 * Each teacher can have a list of students to guide
 */
@Entity
@Table(name = "teacher")
public class Teacher extends User {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "teacher_subject",
            joinColumns = {@JoinColumn(name = "teacher")},
            inverseJoinColumns = {@JoinColumn(name = "subject")})
    private Set<Subject> subjects = new HashSet<>();

    @OneToMany(mappedBy = "teacher")
    private Set<Student> students = new HashSet<>();

    public Teacher() {
        super();
    }

    public Teacher(String firstName, String lastName, String password, Long regNo) {
        super(firstName, lastName, password, regNo);
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        this.getStudents().add(student);
        student.setTeacher(this);
    }

    public void removeStudent(Student student) {
        this.getStudents().remove(student);
        student.setTeacher(null);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                super.toString() +
                "subjects=" + subjects +
                ", students=" + students +
                '}';
    }
}