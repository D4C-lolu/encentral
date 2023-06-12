package D4C.encentral.model.user;

import D4C.encentral.model.subject.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

/**
 * A class representing a Student user
 * Multiple students can offer multiple subjects
 * Multiple students can have a single teacher as a guide
 */
@Entity
@Table(name = "student")
public class Student extends User {

    @Convert(converter = YearAttributeConverter.class)
    @Column(name = "class_year")
    private Year year;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_subject",
            joinColumns = {@JoinColumn(name = "student")},
            inverseJoinColumns = {@JoinColumn(name = "subject")})
    @Size(max=7)
    private Set<Subject> subjects = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="guide")
    private Teacher teacher;

    public Student(){
        super();
    }

    public Student(String firstName, String lastName, String password, Long regNo,Year year) {
        super(firstName, lastName, password, regNo);
        this.year = year;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Student{" +
                super.toString() +
                " year=" + year +
                ", teacher=" + teacher +
                '}';
    }
}
