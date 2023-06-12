package D4C.encentral.dao.user;


import D4C.encentral.dto.mapper.user.TeacherMapper;
import D4C.encentral.dto.user.teacher.TeacherCreationDTO;
import D4C.encentral.dto.user.teacher.TeacherDTO;
import D4C.encentral.dto.user.teacher.TeacherStudentsDTO;
import D4C.encentral.dto.user.teacher.TeacherSubjectsDTO;
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
import java.util.Random;


/**
 * DAO Class for Teacher object
 */
public class TeacherDAOImpl implements UserDAO<TeacherDTO, TeacherCreationDTO> {

    private static EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    private QTeacher teacher;

    private QStudent student;

    private TeacherMapper teacherMapper;


    public TeacherDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.teacher = QTeacher.teacher;
        this.student = QStudent.student;
        this.teacherMapper = TeacherMapper.INSTANCE;

    }

    @Override
    public Optional<TeacherDTO> get(long regNo, String password) {
        Preconditions.checkArgument(regNo > 0, "Invalid User Id");
        Preconditions.checkNotNull(regNo);
        Teacher t = queryFactory.selectFrom(teacher)
                .where(teacher.regNo.eq(regNo))
                .fetchOne();
        TeacherDTO tDTO = teacherMapper.teacherToDTO(t);
        return Optional.ofNullable(tDTO);
    }

    @Override
    public List<TeacherDTO> getAll() {
        List<Teacher> teachers = queryFactory.selectFrom(teacher)
                .orderBy(teacher.regNo.asc())
                .fetch();
        List<TeacherDTO> teacherDTOList = new ArrayList<>();
        for (Teacher t : teachers) {
            teacherDTOList.add(teacherMapper.teacherToDTO(t));
        }
        return teacherDTOList;
    }

    public Optional<TeacherDTO> get(long regNo) {
        Preconditions.checkArgument(regNo > 0, "Invalid User Id");
        Preconditions.checkNotNull(regNo);
        Teacher t = queryFactory.selectFrom(teacher)
                .where(teacher.regNo.eq(regNo))
                .fetchOne();
        TeacherDTO sDTO = teacherMapper.teacherToDTO(t);
        return Optional.ofNullable(sDTO);
    }



    @Override
    public void save(TeacherCreationDTO teacherCreationDTO) {

        Preconditions.checkNotNull(teacherCreationDTO, "Invalid argument");
        Preconditions.checkArgument(teacherCreationDTO.getRegNo() > 0, "Invalid Registration number");

        Teacher t = teacherMapper.creationDTOtoTeacher(teacherCreationDTO);
        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.insert(teacher).set(teacher.firstName, t.getFirstName())
                    .set(teacher.lastName, t.getLastName())
                    .set(teacher.password, t.getPassword())
                    .set(teacher.regNo, t.getRegNo())
                    .execute();
        });
    }


    @Override
    public void updateName(Long regNo, String firstname, String lastname) {

        Preconditions.checkNotNull(regNo, "Invalid argument");
        Preconditions.checkArgument(regNo > 0);
        Preconditions.checkNotNull(firstname);
        Preconditions.checkNotNull(lastname);

        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.update(teacher)
                    .where(teacher.regNo.eq(regNo))
                    .set(teacher.firstName, firstname)
                    .set(teacher.lastName, lastname)
                    .execute();
        });
    }

    @Override
    public void updatePassword(Long regNo, String password) {

        Preconditions.checkNotNull(regNo, "Invalid argument");
        Preconditions.checkArgument(regNo > 0);
        Preconditions.checkNotNull(password);

        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.update(teacher)
                    .where(teacher.regNo.eq(regNo))
                    .set(teacher.password, password)
                    .execute();
        });
    }


    @Override
    public void delete(Long regNo) {

        Preconditions.checkNotNull(regNo, "Invalid argument");
        Preconditions.checkArgument(regNo > 0);

        HibernateUtil.executeInsideTransaction(queryFactory -> {
            queryFactory.delete(teacher)
                    .where(teacher.regNo.eq(regNo))
                    .execute();
        });
    }

    /**
     * Gets the list of students a teacher is currently in charge of
     *
     * @param regNo
     * @return DTO containing a Teacher and a list of students
     */
    public Optional<TeacherStudentsDTO> getStudents(Long regNo) {

        Preconditions.checkNotNull(regNo, "Invalid argument");
        Preconditions.checkArgument(regNo > 0);

        Optional<Teacher> t = Optional.ofNullable(queryFactory.selectFrom(teacher)
                .where(teacher.regNo.eq(regNo))
                .fetchOne());

        TeacherStudentsDTO tsDTO = teacherMapper.toTeacherStudentsDTO(t.get());
        return Optional.ofNullable(tsDTO);

    }

    /**
     * Removes a student from a teacher's list
     *
     * @param teacherRegNo
     * @param studentRegNo
     */
    public void removeStudent(Long teacherRegNo, Long studentRegNo) {

        Preconditions.checkNotNull(teacherRegNo, "Invalid argument");
        Preconditions.checkArgument(teacherRegNo > 0);
        Preconditions.checkNotNull(studentRegNo, "Invalid argument");
        Preconditions.checkArgument(studentRegNo > 0);

        HibernateUtil.executeInsideTransaction(queryFactory -> {
            //Get teacher
            Optional<Teacher> t = Optional.ofNullable(queryFactory.selectFrom(teacher)
                    .where(teacher.regNo.eq(teacherRegNo))
                    .fetchOne());
            //Get student
            Optional<Student> s = Optional.ofNullable(queryFactory.selectFrom(student)
                    .where(student.regNo.eq(studentRegNo))
                    .fetchOne());
            t.ifPresent((tr -> tr.addStudent(s.get())));
        });
    }


    /**
     * Removes a student from a teacher's list
     *
     * @param teacherRegNo
     * @param studentRegNo
     */
    public void addStudent(Long teacherRegNo, Long studentRegNo) {

        Preconditions.checkNotNull(teacherRegNo, "Invalid argument");
        Preconditions.checkArgument(teacherRegNo > 0);
        Preconditions.checkNotNull(studentRegNo, "Invalid argument");
        Preconditions.checkArgument(studentRegNo > 0);

        HibernateUtil.executeInsideTransaction(queryFactory -> {
            //Get teacher
            Optional<Teacher> t = Optional.ofNullable(queryFactory.selectFrom(teacher)
                    .where(teacher.regNo.eq(teacherRegNo))
                    .fetchOne());
            //Get student
            Optional<Student> s = Optional.ofNullable(queryFactory.selectFrom(student)
                    .where(student.regNo.eq(studentRegNo))
                    .fetchOne());
            t.ifPresent((tr -> tr.addStudent(s.get())));
        });
    }

    /**
     * Gets the list of subjects a teacher is currently taking
     *
     * @param regNo
     * @return DTO containing a Teacher and a list of subjects
     */
    public Optional<TeacherSubjectsDTO> getSubjects(Long regNo) {

        Preconditions.checkNotNull(regNo, "Invalid argument");
        Preconditions.checkArgument(regNo > 0);

        Optional<Teacher> t = Optional.ofNullable(queryFactory.selectFrom(teacher)
                .where(teacher.regNo.eq(regNo))
                .fetchOne());

        TeacherSubjectsDTO tsDTO = teacherMapper.toTeacherSubjectsDTO(t.get());
        return Optional.ofNullable(tsDTO);

    }

    /**
     * Gets the number of teachers in the system
     * @return int
     */
    public int getTeacherCount(){
        List<Teacher> teachers = queryFactory.selectFrom(teacher)
                .fetch();
        return teachers.size();
    }

    /**
     * Method to assign a student to a random teacher
     * @param regNo - student regNo
     */
    public void assignStudent(Long regNo){
        List<TeacherDTO> teachers = getAll();
        //Randomly select teacher
        Random rand = new Random();
        int randomInt = rand.nextInt(teachers.size());
        Long teacherRegNo = teachers.get(randomInt).getRegNo();
        addStudent(teacherRegNo,regNo);
    }
}
