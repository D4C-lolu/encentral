package Entities;


import D4C.encentral.dao.subject.SubjectDAOImpl;
import D4C.encentral.dao.user.StudentDAOImpl;
import D4C.encentral.dao.user.TeacherDAOImpl;
import D4C.encentral.dto.subject.SubjectDTO;
import D4C.encentral.dto.user.student.StudentCreationDTO;
import D4C.encentral.dto.user.student.StudentDTO;
import D4C.encentral.dto.user.teacher.TeacherCreationDTO;
import D4C.encentral.model.user.Year;
import D4C.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StudentDAOTest {
    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("postgres");
    private static StudentDAOImpl studentDAO;
    private static TeacherDAOImpl teacherDAO;

    private static SubjectDAOImpl subjectDAO;

    @Before
    public void init() {

        postgreSQLContainer.start();
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        studentDAO = new StudentDAOImpl(em);
        subjectDAO = new SubjectDAOImpl(em);
        teacherDAO = new TeacherDAOImpl(em);

        List<SubjectDTO> subjects = new ArrayList<>(List.of(
                new SubjectDTO("Beginner Math", "MTH101"),
                new SubjectDTO("Beginner English ", "ENG105"),
                new SubjectDTO("AP Physics", "PHS501"),
                new SubjectDTO("Chemistry", "301"),
                new SubjectDTO("Biology", "BIO301"),
                new SubjectDTO("Further Nathematics", "MTH401")
        ));

        for (SubjectDTO s : subjects) {
            subjectDAO.save(s);
        }

        List<TeacherCreationDTO> teachers = new ArrayList<>(
                List.of(
                        new TeacherCreationDTO("Ali", "Mezor", "ababababaaa", 1L),
                        new TeacherCreationDTO("Scott", "Major", "passworddos", 123L)
                ));

        for(TeacherCreationDTO tDTO:teachers){
            teacherDAO.save(tDTO);
        }
    }

    @After
    public void tearDown() {
        HibernateUtil.shutdown();
        postgreSQLContainer.stop();
    }

    @Test
    void testThatStudentSaves() {
        String firstname= "Sam";
        String lastname = "Lolu";
        String password = "qknkndwnkfnrk";
        Long regNo = 123312L;
        Year year = Year.JS2;
        studentDAO.save(new StudentCreationDTO(firstname,lastname,password,regNo,year));
        Optional<StudentDTO> s =studentDAO.get(regNo);
        assertNotNull(s.get());
    }

    @Test
    void testStudentsTableisNotNull() {
        List<StudentDTO> studentList = studentDAO.getAll();
        assert(studentList.size()>0);
    }




}