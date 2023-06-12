package D4C;


import D4C.encentral.dao.subject.SubjectDAOImpl;
import D4C.encentral.dao.user.AdminDAOImpl;
import D4C.encentral.dao.user.TeacherDAOImpl;
import D4C.encentral.dto.subject.SubjectDTO;
import D4C.encentral.dto.user.admin.AdminCreationDTO;
import D4C.encentral.dto.user.admin.AdminDTO;
import D4C.encentral.dto.user.student.StudentDTO;
import D4C.encentral.dto.user.teacher.TeacherCreationDTO;
import D4C.encentral.service.AdminService;
import D4C.encentral.service.StudentService;
import D4C.encentral.view.AdminView;
import D4C.encentral.view.StudentView;
import D4C.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    private static final StudentService studentService = StudentService.getInstance();

    private static final AdminService adminService = AdminService.getInstance();

    private static final StudentView studentView = new StudentView();

    private static final AdminView adminView = new AdminView();

    private static final Scanner sc = new Scanner(System.in);

    public static void logIn() {

        logger.info("Please enter your details as specified to log in");
        logger.info("Please enter your registration number: ");
        String regNo = sc.nextLine();
        logger.info("Now enter your password: ");
        String password = sc.nextLine();
        try {
            Long reg = Long.parseLong(regNo);
            Optional<StudentDTO> sDTO = studentService.signIn(reg, password);
            if (sDTO.isPresent()) {
                studentView.home(sDTO.get());
            } else {
                logger.info("Invalid User details");
            }

        } catch (NumberFormatException e) {
            logger.error("Invalid registration number");
        } catch (Exception e) {
            logger.error("Invalid input");
        }
    }

    public static void signUp() {
        logger.info("Welcome new user");
        logger.info("Please enter your details in the order prompted to register");

        try {
            logger.info("Please enter your first name: ");
            String firstname = sc.nextLine();
            logger.info("Now enter your last name: ");
            String lastname = sc.nextLine();
            logger.info("Please enter your class as a number from 1-6 (eg 1 for JS1 and 4 for SS1: ");
            int year = Integer.parseInt(sc.nextLine());
            logger.info("Finally, enter your password. (Password should be 8 digits or more):");
            String password = sc.nextLine();
            Optional<StudentDTO> sDTO = studentService.signUp(firstname, lastname, password, year);
            if (sDTO.isPresent()) {
                studentView.home(sDTO.get());
            } else {
                logger.info("Invalid User details");
            }
        } catch (NumberFormatException e) {
            logger.error("Please enter a valid number");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid user details. Password length should be at least 8 digits");
        } catch (Exception e) {
            logger.error("Invalid input");
        }
    }

    public static void superUserLogin() {
        logger.info("Please enter your details as specified to log in");
        logger.info("Please enter your registration number: ");
        String regNo = sc.nextLine();
        logger.info("Now enter your password: ");
        String password = sc.nextLine();
        try {
            Long reg = Long.parseLong(regNo);
            Optional<AdminDTO> aDTO = adminService.signIn(reg, password);
            if (aDTO.isPresent()) {
                adminView.home(aDTO.get());
            } else {
                logger.info("Invalid User details");
            }

        } catch (NumberFormatException e) {
            logger.error("Invalid registration number");
        } catch (Exception e) {
            logger.error("Invalid input");
        }
    }

    public static void shutdown() {
        logger.info("Exiting the program...");
        System.exit(0);
    }

    public static void main(String[] args) {
        init();
        logger.info("Encentral Schools Student Portal");
        welcomeMessage();
        enterChoice();
    }

    public static void welcomeMessage() {
        logger.info("Welcome user!");
        logger.info("Please enter 1 to enroll as a new student");
        logger.info("Or enter 2 to log in");
        logger.info("Enter 0 to end the program");
    }

    public static void enterChoice() {

        int choice = 1;
        do {
            try {
                String input = sc.nextLine();
                logger.info("Please enter your response here: ");
                choice = Integer.parseInt(input);
                switch (choice) {
                    case 0:
                        shutdown();
                        enterChoice();
                        break;
                    case 1:
                        signUp();
                        enterChoice();
                        break;
                    case 2:
                        logIn();
                        enterChoice();
                        break;
                    //special hidden case for admin sign in
                    case 999:
                        superUserLogin();
                    default:
                        logger.info("Please enter a valid number");
                        break;
                }
            } catch (final NumberFormatException e) {
                logger.warn("\nPlease enter a valid number: ");
            }
        } while (choice != 0);
        shutdown();
    }

    public static void init() {
        logger.info("Initializing...");
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        AdminDAOImpl adminDAO = new AdminDAOImpl(em);
        TeacherDAOImpl teacherDAO = new TeacherDAOImpl(em);
        SubjectDAOImpl subjectDAO = new SubjectDAOImpl(em);


        String firstname = "Demi";
        String lastname = "Lade";
        String password = "passpasspass";
        Long regNo = 1000L;
        adminDAO.save(new AdminCreationDTO(firstname, lastname, password, regNo));

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

        for (TeacherCreationDTO tDTO : teachers) {
            teacherDAO.save(tDTO);
        }
    }
}


