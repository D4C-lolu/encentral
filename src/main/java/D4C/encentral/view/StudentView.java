package D4C.encentral.view;


import D4C.encentral.dto.subject.SubjectDTO;
import D4C.encentral.dto.user.student.StudentDTO;
import D4C.encentral.dto.user.student.StudentSubjectsDTO;
import D4C.encentral.service.AdminService;
import D4C.encentral.service.StudentService;
import D4C.util.FormatJSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;

/**
 * Class containing all the functions for presenting user output to the end user
 */
public class StudentView {

    private static final Logger logger = LogManager.getLogger(StudentView.class);

    private static final StudentService studentService = StudentService.getInstance();
    private static final AdminService adminService = AdminService.getInstance();

    private static final Scanner sc = new Scanner(System.in);

    public void menuOptions(StudentDTO sDTO) {
        logger.info("==========Main Menu==========");
        logger.info("Enter 1 to edit your subject registration");
        logger.info("Enter 2 to view your registrations");
        logger.info("Enter 3 to view your academic profile");
        logger.info("Enter 4 to update your user details");
        logger.info("or Enter 0 to exit");
        selectOption(sDTO);
    }

    public void selectOption(StudentDTO sDTO) {
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
                        editRegistrations(sDTO);
                        menuOptions(sDTO);
                        break;
                    case 2:
                        viewRegistrations(sDTO);
                        menuOptions(sDTO);
                        break;
                    case 3:
                        viewAcademicProfile(sDTO);
                        menuOptions(sDTO);
                        break;
                    case 4:
                        updateProfile(sDTO);
                        menuOptions(sDTO);
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

    public void home(StudentDTO sDTO) {
        logger.info("Welcome {}", sDTO.getName());
        menuOptions(sDTO);

    }

    public void exit() {
        logger.info("Exiting the program");
        System.exit(0);
    }

    /**
     * Method to edit Student's subject registration
     *
     * @param sDTO -Student's DTO
     */
    public void viewRegistrations(StudentDTO sDTO) {
        //Get Current registrations
        Optional<StudentSubjectsDTO> ssDTO = studentService.getCourses(sDTO.getRegNo());
        if (ssDTO.isPresent()) {
            //Display DTO as json
            logger.info("You have registered the following courses");
            try {
                String result = FormatJSON.toJSON(ssDTO);
                logger.info("{}", result);
            } catch (JsonProcessingException e) {
                logger.error("Error parsing response ");
            }
        } else {
            logger.info("You have not registered any courses this semester");
        }

    }

    /**
     * Method to register subjects for user
     * @param sDTO- Student DTO
     */
    public void editRegistrations(StudentDTO sDTO) {
        //Get all courses
        logger.info("List of all available courses: ");
        List<SubjectDTO> subjectList = adminService.getAllSubjects();

        int i =1;
        for(SubjectDTO sbDTO:subjectList){
            logger.info("Number: {}, Subject: {}, Subject code:{}",i,sbDTO.getName(),sbDTO.getSubjectCode());
            i++;
        }

        logger.info("Please enter the numbers of the subjects you wish to register: ");
        logger.info("Enter -1 to finish registration ");
        Set<SubjectDTO> subjects = new HashSet<>();
        int count=7;
        while(count>0){
           try{
               int response=Integer.parseInt(sc.nextLine());
               if(response == -1){
                   break;
               }
               SubjectDTO s = subjectList.get(response-1);
               subjects.add(s);
               logger.info("Subject {} - ({}) has been added.",s.getName(),s.getSubjectCode());
               count--;
           }
           catch(Exception e){
               logger.error("Please enter a valid number");
           }
        }

        if (subjects.size()<5 || subjects.size()>7){
            logger.info("Invalid Subject registration.");
            logger.info("Students can only register 5-7 courses a semester");
        }
        else{
            List<SubjectDTO> courseList = new ArrayList<>(subjects);
            studentService.enrollCourses(sDTO.getRegNo(),courseList);
            logger.info("Registration complete");
        }
    }

    /**
     * Method to view a Student's profile
     * Shows Student's details and the teacher they are assigned to
     * @param sDTO - Student DTO
     */
    public void viewAcademicProfile(StudentDTO sDTO) {
        logger.info("Your academic profile: ");
        logger.info("Name: {}",sDTO.getName());
        logger.info("Registration Number: {}",sDTO.getRegNo());
        logger.info("Class: {}",sDTO.getYear());
        logger.info("Teacher : {}",sDTO.getTeacher().getName());
        try {
            String result = FormatJSON.toJSON(sDTO.getTeacher());
            logger.info("Teacher details: {}",result);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing response ");
        }

    }

    /**
     * Method to update a user's profile
     * @param sDTO -Student DTO
     */
    public void updateProfile(StudentDTO sDTO) {
        logger.info("Update Profile Menu: ");
        logger.info("Enter 1 to update your name: ");
        logger.info("Enter 2 to update your password: ");
        logger.info("Enter 0 to go back to the main menu ");
        int choice =-1;
        do{
           try{
               choice =Integer.parseInt(sc.nextLine());
               switch(choice){
                   case 0:
                       break;
                   case 1:
                       logger.info("Please enter your new password: ");
                       String password = sc.nextLine();
                       studentService.updatePassword(sDTO.getRegNo(),password);
                       logger.info("Password updated. New password is : {}",password);
                       break;
                   case 2:
                       logger.info("Please enter your firstname: ");
                       String firstname = sc.nextLine();
                       logger.info("Now enter your lastname: ");
                       String lastname = sc.nextLine();
                       studentService.updateName(sDTO.getRegNo(),firstname,lastname);
                       logger.info("Name updated");
                       break;
                   default:
                       logger.info("Please enter a valid number");
               }
           }
           catch(NumberFormatException e){
               logger.info("Please enter a valid number");
           }
           catch(Exception e){
               logger.info("Please enter valid input");
           }
        }
        while(choice!=0);

    }

}
