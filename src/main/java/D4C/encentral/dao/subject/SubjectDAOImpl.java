package D4C.encentral.dao.subject;

import D4C.encentral.dto.mapper.subject.SubjectMapper;
import D4C.encentral.dto.subject.SubjectDTO;
import D4C.encentral.dto.subject.SubjectStudentsDTO;
import D4C.encentral.dto.subject.SubjectTeachersDTO;
import D4C.encentral.model.subject.QSubject;
import D4C.encentral.model.subject.Subject;
import D4C.encentral.model.user.QStudent;
import D4C.encentral.model.user.QTeacher;
import D4C.encentral.model.user.Student;
import D4C.encentral.model.user.Teacher;
import D4C.util.HibernateUtil;
import com.google.common.base.Preconditions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubjectDAOImpl implements SubjectDAO{

    private static EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    private QSubject subject;

    private QTeacher teacher;

    private QStudent student;

    private SubjectMapper subjectMapper;


    public SubjectDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.subject = QSubject.subject;
        this.teacher = QTeacher.teacher;
        this.student = QStudent.student;
        this.subjectMapper = SubjectMapper.INSTANCE;

    }


    @Override
    public Optional<SubjectDTO> get(String courseCode) {

        Preconditions.checkNotNull(courseCode);

        Subject s = queryFactory.selectFrom(subject)
                .where(subject.subjectCode.eq(courseCode))
                .fetchOne();
        SubjectDTO sDTO = subjectMapper.toSubjectDTO(s);
        return Optional.ofNullable(sDTO);
    }

    @Override
    public List<SubjectDTO> getAll() {
        List<Subject> subjects = queryFactory.selectFrom(subject)
                .orderBy(subject.subjectCode.asc())
                .fetch();
        List<SubjectDTO> subjectDTOList = new ArrayList<>();
        for (Subject s : subjects) {
            subjectDTOList.add(subjectMapper.toSubjectDTO(s));
        }
        return subjectDTOList;
    }

    @Override
    public void save(SubjectDTO subjectDTO) {
        Preconditions.checkNotNull(subjectDTO, "Invalid argument");
        Preconditions.checkArgument(subjectDTO.getSubjectCode().length()>3);

        Subject s = subjectMapper.dtoToSubject(subjectDTO);
        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.insert(subject).set(subject.name, s.getName())
                    .set(subject.subjectCode,s.getSubjectCode())
                    .execute();
        });
    }

    @Override
    public void updateName(String courseCode, String name) {
        Preconditions.checkNotNull(courseCode, "Invalid argument");
        Preconditions.checkArgument(courseCode.length()>3);
        Preconditions.checkNotNull(name,"Invalid argument");
        Preconditions.checkArgument(name.length()>3);

        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.update(subject)
                    .where(subject.subjectCode.eq(courseCode))
                    .set(subject.name, name)
                    .execute();
        });
    }

    @Override
    public void delete(String courseCode) {
        Preconditions.checkNotNull(courseCode, "Invalid argument");
        Preconditions.checkArgument(courseCode.length() > 3);

        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.delete(subject)
                    .where(subject.subjectCode.eq(courseCode))
                    .execute();
        });
    }

    @Override
    public void addTeacher(String courseCode, Long teacherRegNo) {

        Preconditions.checkNotNull(courseCode, "Invalid argument");
        Preconditions.checkArgument(courseCode.length()>3);
        Preconditions.checkNotNull(teacherRegNo, "Invalid argument");
        Preconditions.checkArgument(teacherRegNo > 0);


        HibernateUtil.executeInsideTransaction(queryFactory -> {
            //Get teacher
            Optional<Teacher> t = Optional.ofNullable(queryFactory.selectFrom(teacher)
                    .where(teacher.regNo.eq(teacherRegNo))
                    .fetchOne());
            //Get Subject
            Optional<Subject> s = Optional.ofNullable(queryFactory.selectFrom(subject)
                    .where(subject.subjectCode.eq(courseCode))
                    .fetchOne());
            s.ifPresent((st -> st.addTeacher(t.get())));
        });
    }

    @Override
    public void removeTeacher(String courseCode, Long teacherRegNo) {
        Preconditions.checkNotNull(courseCode, "Invalid argument");
        Preconditions.checkArgument(courseCode.length()>3);
        Preconditions.checkNotNull(teacherRegNo, "Invalid argument");
        Preconditions.checkArgument(teacherRegNo > 0);


        HibernateUtil.executeInsideTransaction(queryFactory -> {
            //Get teacher
            Optional<Teacher> t = Optional.ofNullable(queryFactory.selectFrom(teacher)
                    .where(teacher.regNo.eq(teacherRegNo))
                    .fetchOne());
            //Get Subject
            Optional<Subject> s = Optional.ofNullable(queryFactory.selectFrom(subject)
                    .where(subject.subjectCode.eq(courseCode))
                    .fetchOne());
            s.ifPresent((st -> st.removeTeacher(t.get())));
        });

    }

    @Override
    public void addStudent(String courseCode, Long studentRegNo) {
        Preconditions.checkNotNull(courseCode, "Invalid argument");
        Preconditions.checkArgument(courseCode.length()>3);
        Preconditions.checkNotNull(studentRegNo, "Invalid argument");
        Preconditions.checkArgument(studentRegNo > 0);


        HibernateUtil.executeInsideTransaction(queryFactory -> {
            //Get student
            Optional<Student> sd = Optional.ofNullable(queryFactory.selectFrom(student)
                    .where(student.regNo.eq(studentRegNo))
                    .fetchOne());
            //Get Subject
            Optional<Subject> s = Optional.ofNullable(queryFactory.selectFrom(subject)
                    .where(subject.subjectCode.eq(courseCode))
                    .fetchOne());
            s.ifPresent((st -> st.addStudent(sd.get())));
        });
    }

    @Override
    public void removeStudent(String courseCode, Long studentRegNo) {
        Preconditions.checkNotNull(courseCode, "Invalid argument");
        Preconditions.checkArgument(courseCode.length()>3);
        Preconditions.checkNotNull(studentRegNo, "Invalid argument");
        Preconditions.checkArgument(studentRegNo > 0);


        HibernateUtil.executeInsideTransaction(queryFactory -> {
            //Get student
            Optional<Student> sd = Optional.ofNullable(queryFactory.selectFrom(student)
                    .where(student.regNo.eq(studentRegNo))
                    .fetchOne());
            //Get Subject
            Optional<Subject> s = Optional.ofNullable(queryFactory.selectFrom(subject)
                    .where(subject.subjectCode.eq(courseCode))
                    .fetchOne());
            s.ifPresent((st -> st.removeStudent(sd.get())));
        });
    }

    @Override
    public Optional<SubjectTeachersDTO> getTeachers(String courseCode) {

        Preconditions.checkNotNull(courseCode, "Invalid argument");
        Preconditions.checkArgument(courseCode.length()>3);

        Optional<Subject> s = Optional.ofNullable(queryFactory.selectFrom(subject)
                .where(subject.subjectCode.eq(courseCode))
                .fetchOne());

        SubjectTeachersDTO stDTO = subjectMapper.toSubjectTeachersDTO(s.get());
        return Optional.ofNullable(stDTO);
    }

    @Override
    public Optional<SubjectStudentsDTO> getStudents(String courseCode) {

        Preconditions.checkNotNull(courseCode, "Invalid argument");
        Preconditions.checkArgument(courseCode.length()>3);

        Optional<Subject> s = Optional.ofNullable(queryFactory.selectFrom(subject)
                .where(subject.subjectCode.eq(courseCode))
                .fetchOne());

        SubjectStudentsDTO ssDTO = subjectMapper.toSubjectStudentsDTO(s.get());
        return Optional.ofNullable(ssDTO);
    }
}
