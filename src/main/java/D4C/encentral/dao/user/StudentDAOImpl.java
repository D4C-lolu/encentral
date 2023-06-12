package D4C.encentral.dao.user;

import D4C.encentral.dto.mapper.user.StudentMapper;
import D4C.encentral.dto.mapper.user.TeacherMapper;
import D4C.encentral.dto.subject.SubjectStudentsDTO;
import D4C.encentral.dto.user.student.StudentCreationDTO;
import D4C.encentral.dto.user.student.StudentDTO;
import D4C.encentral.dto.user.student.StudentSubjectsDTO;
import D4C.encentral.dto.user.teacher.TeacherDTO;
import D4C.encentral.model.subject.Subject;
import D4C.encentral.model.user.QStudent;
import D4C.encentral.model.user.Student;
import D4C.encentral.model.user.Teacher;
import D4C.encentral.model.user.Year;
import D4C.util.HibernateUtil;
import com.google.common.base.Preconditions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * DAO Class for Student object
 */
public class StudentDAOImpl implements UserDAO<StudentDTO, StudentCreationDTO> {

    private static EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    private QStudent student;

    private StudentMapper studentMapper;


    public StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.student = QStudent.student;
        this.studentMapper = StudentMapper.INSTANCE;

    }

    @Override
    public Optional<StudentDTO> get(long regNo, String password) {
        Preconditions.checkArgument(regNo > 0, "Invalid User Id");
        Preconditions.checkNotNull(regNo);
        Preconditions.checkNotNull(password);
        Student s = queryFactory.selectFrom(student)
                .where(student.regNo.eq(regNo))
                .fetchOne();
        if (!s.getPassword().equals(password)) return null;
        StudentDTO sDTO = studentMapper.studentToDTO(s);
        return Optional.ofNullable(sDTO);
    }


    @Override
    public Optional<StudentDTO> get(long regNo) {
        Preconditions.checkArgument(regNo > 0, "Invalid User Id");
        Preconditions.checkNotNull(regNo);
        Student s = queryFactory.selectFrom(student)
                .where(student.regNo.eq(regNo))
                .fetchOne();
        StudentDTO sDTO = studentMapper.studentToDTO(s);
        return Optional.ofNullable(sDTO);
    }

    public int getClassCount(Year year){
        List<Student> studentSet = queryFactory.selectFrom(student)
                .where(student.year.eq(year))
                .fetch();
        return studentSet.size();
    }


    @Override
    public List<StudentDTO> getAll() {
        List<Student> students = queryFactory.selectFrom(student)
                .orderBy(student.regNo.asc())
                .fetch();
        List<StudentDTO> studentDTOList = new ArrayList<>();
        for (Student s : students) {
            studentDTOList.add(studentMapper.studentToDTO(s));
        }
        return studentDTOList;
    }

    @Override
    public void save(StudentCreationDTO studentCreationDTO) {

        Preconditions.checkNotNull(studentCreationDTO, "Invalid argument");
        Preconditions.checkArgument(studentCreationDTO.getRegNo() > 0, "Invalid Registration number");
        Preconditions.checkArgument(studentCreationDTO.getYear() != null);

        Student s = studentMapper.creationDTOtoStudent(studentCreationDTO);
        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.insert(student).set(student.firstName, s.getFirstName())
                    .set(student.lastName, s.getLastName())
                    .set(student.password, s.getPassword())
                    .set(student.regNo, s.getRegNo())
                    .set(student.year, s.getYear()).execute();
        });
    }


    @Override
    public void updateName(Long regNo, String firstname, String lastname) {

        Preconditions.checkNotNull(regNo, "Invalid argument");
        Preconditions.checkArgument(regNo > 0);
        Preconditions.checkNotNull(firstname);
        Preconditions.checkNotNull(lastname);

        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.update(student)
                    .where(student.regNo.eq(regNo))
                    .set(student.firstName, firstname)
                    .set(student.lastName, lastname)
                    .execute();
        });
    }

    @Override
    public void updatePassword(Long regNo, String password) {

        Preconditions.checkNotNull(regNo, "Invalid argument");
        Preconditions.checkArgument(regNo > 0);
        Preconditions.checkNotNull(password);

        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.update(student)
                    .where(student.regNo.eq(regNo))
                    .set(student.password, password)
                    .execute();
        });
    }


    @Override
    public void delete(Long regNo) {

        Preconditions.checkNotNull(regNo, "Invalid argument");
        Preconditions.checkArgument(regNo > 0);

        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.delete(student)
                    .where(student.regNo.eq(regNo))
                    .execute();
        });
    }

    /**
     * Gets the teacheracting as a  student's guide
     *
     * @param studentRegNo
     * @return TeacherDTO
     */
    public TeacherDTO getTeacher( Long studentRegNo) {

        Preconditions.checkNotNull(studentRegNo, "Invalid argument");
        Preconditions.checkArgument(studentRegNo > 0);


        Teacher t = queryFactory.select(student.teacher)
                .from(student).where(student.regNo.eq(studentRegNo))
                .fetchOne();

        TeacherDTO tDTO = TeacherMapper.INSTANCE.teacherToDTO(t);

        return tDTO;

    }

    /**
     * Returns a list of all subjects offered by a student
     * @param regNo -Student regNo
     * @return - DTO containing list of subjects and student
     */
    public Optional<StudentSubjectsDTO> getSubjects(Long regNo){

        Optional<Student> s = Optional.ofNullable(queryFactory.selectFrom(student)
                .where(student.regNo.eq(regNo))
                .fetchOne());

        StudentSubjectsDTO ssDTO = studentMapper.toStudentSubjectsDTO(s.get());
        return Optional.ofNullable(ssDTO);
    }


}
