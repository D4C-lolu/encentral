package D4C.encentral.service;

import D4C.encentral.dao.subject.SubjectDAOImpl;
import D4C.encentral.dao.user.StudentDAOImpl;
import D4C.encentral.dao.user.TeacherDAOImpl;
import D4C.encentral.dto.subject.SubjectDTO;
import D4C.encentral.dto.user.student.StudentCreationDTO;
import D4C.encentral.dto.user.student.StudentDTO;
import D4C.encentral.dto.user.student.StudentSubjectsDTO;
import D4C.encentral.model.user.Year;
import D4C.util.HibernateUtil;
import com.google.common.base.Preconditions;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class StudentService {

    StudentDAOImpl studentDAO;

    SubjectDAOImpl subjectDAO;

    TeacherDAOImpl teacherDAO;
    private final static StudentService _instance;

    private EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();

    private StudentService() {
        this.studentDAO = new StudentDAOImpl(entityManager);
        this.subjectDAO = new SubjectDAOImpl(entityManager);
        this.teacherDAO = new TeacherDAOImpl(entityManager);
    }

    static {
        try {
            _instance = new StudentService();
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong creating the StudentService");
        }
    }

    public static StudentService getInstance() {
        return _instance;
    }

    /**
     * Sign in method for Student User
     * @param regNo
     * @param password
     * @return Student DTO
     */
    public Optional<StudentDTO> signIn(Long regNo, String password) {

        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);
        Preconditions.checkNotNull(password);
        Preconditions.checkArgument(password.length() != 0);

        Optional<StudentDTO> sDTO = studentDAO.get(regNo, password);
        return sDTO;
    }

    /**
     * Sign up Method for Student User
     * Creates a new Student object and persists it
     * @param firstname
     * @param lastname
     * @param password
     * @param year - a value between 1 and 6 representing the student's class
     * @return StudentDTO
     */
    public Optional<StudentDTO> signUp(String firstname, String lastname, String password, int year) {
        Preconditions.checkNotNull(password, "Invalid argument");
        Preconditions.checkArgument(password.length() >= 8, "Password must be at least 8 characters long");
        Preconditions.checkArgument(firstname.length() > 0);
        Preconditions.checkArgument(lastname.length() > 0);
        Preconditions.checkArgument(year > 0 && year < 7);

        Year class_year = Year.fromInteger(year);

        //compute regNo
        String serial = String.valueOf(studentDAO.getClassCount(class_year) + 1);
        switch (serial.length()) {
            case (1):
                serial = "00" + serial;
                break;
            case (2):
                serial = "0" + serial;
                break;
            default:
                break;
        }
        String regString = year + "00" + serial;
        Long regNo = Long.parseLong(regString);

        StudentCreationDTO sDTO = new StudentCreationDTO(firstname, lastname, password, regNo, class_year);

        studentDAO.save(sDTO);

        Optional<StudentDTO> s = studentDAO.get(regNo, password);

        return s;
    }

    /**
     * Method to enroll a student in the school
     * @param regNo
     * @param registeredSubjects
     */
    public void enrollCourses(Long regNo, List<SubjectDTO> registeredSubjects) {

        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);
        Preconditions.checkNotNull(registeredSubjects);
        Preconditions.checkArgument(registeredSubjects.size()>=5, "A minimum of 5 courses must be enrolled");
        Preconditions.checkArgument(registeredSubjects.size()<=7,"A maximum of 7 courses can be enrolled");

        //Add courses
        for (SubjectDTO s : registeredSubjects) {
            subjectDAO.addStudent(s.getSubjectCode(), regNo);
        }

        //Assign to teacher
        teacherDAO.assignStudent(regNo);

    }

    /**
     * Method to edit the list of enrolled subjects
     * @param regNo
     * @param registeredSubjects
     */
    public void editCourses(Long regNo, List<SubjectDTO> registeredSubjects){
        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);
        Preconditions.checkNotNull(registeredSubjects);
        Preconditions.checkArgument(registeredSubjects.size()>=5, "A minimum of 5 courses must be enrolled");
        Preconditions.checkArgument(registeredSubjects.size()<=7,"A maximum of 7 courses can be enrolled");

        //Remove from all courses
        Optional<StudentSubjectsDTO>  ssDTO = studentDAO.getSubjects(regNo);
        ssDTO.ifPresent(sDTO->{
            for(SubjectDTO subjectDTO:sDTO.getSubjects()){
                subjectDAO.removeStudent(subjectDTO.getSubjectCode(),regNo);
            }
        });

        //Register new courses
        for(SubjectDTO subjectDTO:registeredSubjects){
            subjectDAO.addStudent(subjectDTO.getSubjectCode(),regNo);
        }
    }

    public Optional<StudentSubjectsDTO> getCourses(Long regNo){
        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);

        Optional<StudentSubjectsDTO> ssDTO = studentDAO.getSubjects(regNo);
        return ssDTO;
    }

    /**
     * Method to update Student password
     * @param regNo
     * @param password
     */
    public void updatePassword(Long regNo, String password){

        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);
        Preconditions.checkNotNull(password);
        Preconditions.checkArgument(password.length() != 0);
        studentDAO.updatePassword(regNo, password);
    }

    /**
     * Method to update Student name
     * @param regNo
     * @param firstname
     * @param lastname
     */
    public void updateName(Long regNo, String firstname,String lastname){
        Preconditions.checkNotNull(regNo, "Invalid argument");
        Preconditions.checkArgument(regNo > 0);
        Preconditions.checkNotNull(firstname);
        Preconditions.checkArgument(firstname.length()>0);
        Preconditions.checkNotNull(lastname);
        Preconditions.checkArgument(lastname.length()>0);

        studentDAO.updateName(regNo, firstname, lastname);
    }
}
