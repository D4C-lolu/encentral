package D4C.encentral.model.subject;

import D4C.encentral.model.user.Student;
import D4C.encentral.model.user.Teacher;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="subject_code",unique = true,nullable = false)
    @NaturalId
    private String subjectCode;

    @ManyToMany(mappedBy = "subjects",fetch = FetchType.LAZY)
    private Set<Teacher> teachers = new HashSet<>();

    @ManyToMany(mappedBy = "subjects",fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();

    public Subject(){}

    public Subject(String name, String subjectCode){
        this.name = name;
        this.subjectCode=subjectCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void addTeacher(Teacher teacher){
        this.getTeachers().add(teacher);
        teacher.getSubjects().add(this);
    }

    public void removeTeacher(Teacher teacher){
        this.getTeachers().remove(teacher);
        teacher.getSubjects().remove(this);
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student){
        this.getStudents().add(student);
        student.getSubjects().add(this);
    }

    public void removeStudent(Student student){
        this.getStudents().remove(student);
        student.getSubjects().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        Subject subject = (Subject) o;
        return Objects.equals(getSubjectCode(), subject.getSubjectCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubjectCode());
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subjectCode='" + subjectCode + '\'' +
                ", teachers=" + teachers +
                '}';
    }
}
