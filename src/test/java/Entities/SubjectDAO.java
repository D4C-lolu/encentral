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
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SubjectDAO {
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
    void testThatSubjectSaves() {
        String title = "Advanced Chemistry";
        String courseCode ="CHM401";

        subjectDAO.save(new SubjectDTO(title,courseCode));
        Optional<SubjectDTO> s =subjectDAO.get(courseCode);
        assertNotNull(s.get());
    }

    @Test
    void testStudentsTableisNotNull() {
        List<SubjectDTO> subjectList = subjectDAO.getAll();
        assert(subjectList.size()>0);
    }

}
