package D4C.encentral.view;

import D4C.encentral.dto.subject.SubjectDTO;
import D4C.encentral.dto.subject.SubjectStudentsDTO;
import D4C.encentral.dto.subject.SubjectTeachersDTO;
import D4C.encentral.dto.user.admin.AdminDTO;
import D4C.encentral.dto.user.student.StudentDTO;
import D4C.encentral.dto.user.student.StudentSubjectsDTO;
import D4C.encentral.dto.user.teacher.TeacherDTO;
import D4C.encentral.dto.user.teacher.TeacherSubjectsDTO;
import D4C.encentral.service.AdminService;
import D4C.util.FormatJSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Class containing functions to format Results for Admin User
 * Returns all results in JSON
 */
public class AdminView {
    private static final Logger logger = LogManager.getLogger(AdminView.class);

    private static final AdminService adminService = AdminService.getInstance();

    private static final Scanner sc = new Scanner(System.in);

    public void menuOptions(AdminDTO aDTO) {
        logger.info("==========Main Menu==========");
        logger.info("Enter 1 to view all students");
        logger.info("Enter 2 to view all teachers");
        logger.info("Enter 3 to view all subjects");
        logger.info("Enter 4 to add a teacher");
        logger.info("Enter 5 to add a subject");
        logger.info("Enter 6 to view list of subjects for a teacher");
        logger.info("Enter 7 to view list of subjects for a student");
        logger.info("Enter 8 to view list of students for a subject");
        logger.info("Enter 9 to view list of teachers for a subject");
        logger.info("or Enter 0 to exit");
        selectOption(aDTO);
    }

    public void selectOption(AdminDTO aDTO) {
        logger.info("Please enter your choice here :");
        int input = -1;
        do {
            try {
                input = Integer.parseInt(sc.nextLine());
                switch (input) {
                    case 0:
                        exit();
                        break;
                    case 1:
                        viewStudents();
                        menuOptions(aDTO);
                        break;
                    case 2:
                        viewTeachers();
                        menuOptions(aDTO);
                        break;
                    case 3:
                        viewSubjects();
                        menuOptions(aDTO);
                        break;
                    case 4:
                        addTeacher();
                        menuOptions(aDTO);
                        break;
                    case 5:
                        addSubject();
                        menuOptions(aDTO);
                        break;
                    case 6:
                        getTeacherSubjects();
                        menuOptions(aDTO);
                        break;
                    case 7:
                        getStudentSubjects();
                        menuOptions(aDTO);
                        break;
                    case 8:
                        getSubjectStudents();
                        menuOptions(aDTO);
                        break;
                    case 9:
                        getSubjectTeachers();
                        menuOptions(aDTO);
                        break;
                    default:
                        logger.info("Please enter a valid number");
                }
            } catch (NumberFormatException e) {
                logger.info("Please enter a valid number");
            }

        }
        while (input != 0);
        exit();
    }

    public void exit() {
        logger.info("Exiting the program");
        System.exit(0);
    }

    public void home(AdminDTO adminDTO) {
        logger.info("Welcome {}", adminDTO.getName());
        menuOptions(adminDTO);
    }

    private void logJSON(Object o) {
        try {
            String result = FormatJSON.toJSON(o);
            logger.info("{}", result);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing response ");
        }
    }


    public void viewStudents() {
        List<StudentDTO> students = adminService.getAllStudents();
        logger.info("Students currently enrolled are: ");
        logJSON(students);
    }

    public void viewTeachers() {
        List<TeacherDTO> teachers = adminService.getAllTeachers();
        logger.info("Curently employed teachers are: ");
        logJSON(teachers);
    }

    public void viewSubjects() {
        List<SubjectDTO> subjects = adminService.getAllSubjects();
        logger.info("Currently available subjects are: ");
        logJSON(subjects);

    }

    public void addTeacher() {
        try {

            logger.info("Please enter the details as prompted");
            logger.info("Please enter the Teacher's first name: ");
            String firstname = sc.nextLine();
            logger.info("Now enter the teacher's last name");
            String lastname = sc.nextLine();
            logger.info("Enter the user password(8 digits or more)");
            String password = sc.nextLine();
            adminService.addTeacher(firstname, lastname, password);
            logger.info("Teacher added");

        } catch (Exception e) {
            logger.error("Invalid input");
        }
    }

    public void addSubject() {
        try {
            logger.info("Please enter the subject's title: ");
            String title = sc.nextLine();
            logger.info("Now enter the subject's course code: ");
            String courseCode = sc.nextLine();
            adminService.addSubject(title, courseCode);
            logger.info("Teacher added");
        } catch (Exception e) {
            logger.error("Invalid input");
        }
    }

    public void getTeacherSubjects() {
        try {
            logger.info("Please enter the teacher's registration number: ");
            Long num = Long.parseLong(sc.nextLine());
            Optional<TeacherSubjectsDTO> tsDTO = adminService.viewTeacherSubjects(num);
            logJSON(tsDTO.get());
        } catch (Exception e) {
            logger.error("Invalid input");
        }
    }

    public void getStudentSubjects() {
        try {
            logger.info("Please enter the student's registration number: ");
            Long num = Long.parseLong(sc.nextLine());
            Optional<StudentSubjectsDTO> ssDTO = adminService.viewStudentEnrollments(num);
            logJSON(ssDTO.get());
        } catch (Exception e) {
            logger.error("Invalid input");
        }
    }

    public void getSubjectStudents() {
        try {
            logger.info("Please enter the subject's subject code: ");
            String code = sc.nextLine();
            Optional<SubjectStudentsDTO> ssDTO = adminService.viewSubjectEnrollments(code);
            logJSON(ssDTO.get());
        } catch (Exception e) {
            logger.error("Invalid input");
        }
    }

    public void getSubjectTeachers() {
        try {
            logger.info("Please enter the subject's subject code: ");
            String code = sc.nextLine();
            Optional<SubjectTeachersDTO> stDTO = adminService.viewSubjectTeachers(code);
            logJSON(stDTO.get());
        } catch (Exception e) {
            logger.error("Invalid input");
        }

    }
}
