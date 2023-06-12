package Entities;

import D4C.encentral.dao.subject.SubjectDAO;
import D4C.encentral.dao.subject.SubjectDAOImpl;
import D4C.encentral.dao.user.StudentDAOImpl;
import D4C.encentral.dao.user.TeacherDAOImpl;
import D4C.encentral.dto.user.student.StudentCreationDTO;
import D4C.encentral.dto.user.student.StudentDTO;
import D4C.encentral.model.user.Year;
import D4C.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestApplication {

    private EntityManagerFactory emf;

    private EntityManager em;

    private StudentDAOImpl studentDAO;

    private SubjectDAOImpl subjectDAO;

    private TeacherDAOImpl teacherDAO;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("postgres");

    @Before
    public void init() {
        System.setProperty("db.port", postgreSQLContainer.getFirstMappedPort().toString());
        emf = HibernateUtil.getSessionFactory();
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        studentDAO = new StudentDAOImpl(em);
        subjectDAO = new SubjectDAOImpl(em);
        teacherDAO = new TeacherDAOImpl(em);
    }

    @AfterEach
    void tearDown(){
        HibernateUtil.shutdown();
    }

    @Rule
    public PostgreSQLContainer postgresContainer = new PostgreSQLContainer();

    @Test
    public void whenSelectQueryExecuted_thenResulstsReturned()
            throws Exception {
        String jdbcUrl = postgresContainer.getJdbcUrl();
        String username = postgresContainer.getUsername();
        String password = postgresContainer.getPassword();
        Connection conn = DriverManager
                .getConnection(jdbcUrl, username, password);
        ResultSet resultSet =
                conn.createStatement().executeQuery("SELECT 1");
        resultSet.next();
        int result = resultSet.getInt(1);

        assertEquals(1, result);
    }

    @Test
    public void studentSavesSuccessfully(){
        Long regNo = 1232L;
        StudentCreationDTO sDTO = new StudentCreationDTO("name","lastname","passwordd",regNo, Year.JS1);
        studentDAO.save(sDTO);
        Optional<StudentDTO> studentDTO= studentDAO.get(regNo);

        assert(studentDTO.isPresent());
    }


}
