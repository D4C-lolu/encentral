package D4C.encentral.service;

import D4C.encentral.dao.subject.SubjectDAOImpl;
import D4C.encentral.dao.user.AdminDAOImpl;
import D4C.encentral.dao.user.StudentDAOImpl;
import D4C.encentral.dao.user.TeacherDAOImpl;
import D4C.encentral.dto.subject.SubjectDTO;
import D4C.encentral.dto.subject.SubjectStudentsDTO;
import D4C.encentral.dto.subject.SubjectTeachersDTO;
import D4C.encentral.dto.user.admin.AdminDTO;
import D4C.encentral.dto.user.student.StudentDTO;
import D4C.encentral.dto.user.student.StudentSubjectsDTO;
import D4C.encentral.dto.user.teacher.TeacherCreationDTO;
import D4C.encentral.dto.user.teacher.TeacherDTO;
import D4C.encentral.dto.user.teacher.TeacherStudentsDTO;
import D4C.encentral.dto.user.teacher.TeacherSubjectsDTO;
import D4C.util.HibernateUtil;
import com.google.common.base.Preconditions;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class AdminService {

    StudentDAOImpl studentDAO;

    SubjectDAOImpl subjectDAO;

    TeacherDAOImpl teacherDAO;

    AdminDAOImpl adminDAO;

    private static final AdminService _instance;

    private EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();

    private AdminService() {
        this.studentDAO = new StudentDAOImpl(entityManager);
        this.subjectDAO = new SubjectDAOImpl(entityManager);
        this.teacherDAO = new TeacherDAOImpl(entityManager);
    }


    static {
        try {
            _instance = new AdminService();
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong creating the StudentService");
        }
    }

    public static AdminService getInstance() {
        return _instance;
    }

    /**
     * Sign in method for Admin User
     *
     * @param regNo
     * @param password
     * @return Teacher DTO
     */
    public Optional<AdminDTO> signIn(Long regNo, String password) {

        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);
        Preconditions.checkNotNull(password);
        Preconditions.checkArgument(password.length() != 0);

        Optional<AdminDTO> aDTO = adminDAO.get(regNo, password);
        return aDTO;
    }

    /**
     * Method creates a new Teacher object and persists it
     *
     * @param firstname
     * @param lastname
     * @param password
     */
    public void addTeacher(String firstname, String lastname, String password) {
        Preconditions.checkNotNull(password, "Invalid argument");
        Preconditions.checkArgument(password.length() > 8, "Password must be at least 8 characters long");
        Preconditions.checkArgument(firstname.length() > 0);
        Preconditions.checkArgument(lastname.length() > 0);


        //compute regNo
        String serial = String.valueOf(teacherDAO.getTeacherCount() + 1);
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
        Long regNo = Long.parseLong(serial);

        TeacherCreationDTO tDTO = new TeacherCreationDTO(firstname, lastname, password, regNo);

        teacherDAO.save(tDTO);

    }

    /**
     * Method to save a subject object
     *
     * @param subjectTitle
     * @param subjectCode
     */
    public void addSubject(String subjectTitle, String subjectCode) {
        Preconditions.checkNotNull(subjectTitle);
        Preconditions.checkArgument(subjectTitle.length() > 0);
        Preconditions.checkNotNull(subjectCode);
        Preconditions.checkArgument(subjectCode.length() > 3);


        SubjectDTO sDTO = new SubjectDTO(subjectTitle, subjectCode);
        subjectDAO.save(sDTO);
    }


    public Optional<TeacherSubjectsDTO> viewTeacherSubjects(Long regNo) {
        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);

        Optional<TeacherSubjectsDTO> tsDTO = teacherDAO.getSubjects(regNo);
        return tsDTO;
    }

    public Optional<TeacherStudentsDTO> viewTeacherStudents(Long regNo) {
        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);

        Optional<TeacherStudentsDTO> tsDTO = teacherDAO.getStudents(regNo);
        return tsDTO;
    }

    public Optional<StudentSubjectsDTO> viewStudentEnrollments(Long regNo) {
        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);

        Optional<StudentSubjectsDTO> ssDTO = studentDAO.getSubjects(regNo);
        return ssDTO;
    }

    public Optional<SubjectStudentsDTO> viewSubjectEnrollments(String subjectCode) {
        Preconditions.checkNotNull(subjectCode);
        Preconditions.checkArgument(subjectCode.length() > 3);

        Optional<SubjectStudentsDTO> ssDTO = subjectDAO.getStudents(subjectCode);
        return ssDTO;
    }

    public Optional<SubjectTeachersDTO> viewSubjectTeachers(String subjectCode) {
        Preconditions.checkNotNull(subjectCode);
        Preconditions.checkArgument(subjectCode.length() > 3);

        Optional<SubjectTeachersDTO> stDTO = subjectDAO.getTeachers(subjectCode);
        return stDTO;
    }


    public List<TeacherDTO> getAllTeachers() {
        return teacherDAO.getAll();
    }

    public List<StudentDTO> getAllStudents() {
        return studentDAO.getAll();
    }

    public List<SubjectDTO> getAllSubjects() {
        return subjectDAO.getAll();
    }

    public void deleteStudent(Long regNo) {
        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);

        studentDAO.delete(regNo);
    }

    public void deleteSubject(String subjectCode) {
        Preconditions.checkNotNull(subjectCode);
        Preconditions.checkArgument(subjectCode.length() > 3);

        subjectDAO.delete(subjectCode);
    }

    public void deleteTeacher(Long regNo) {
        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);

        teacherDAO.delete(regNo);

        //Re-assign students to different mentors
        Optional<TeacherStudentsDTO> studentsList = teacherDAO.getStudents(regNo);

        for(StudentDTO sDTO:studentsList.get().getStudents()){
            teacherDAO.assignStudent(sDTO.getRegNo());
        }

    }

    public void updatePassword(Long regNo, String password) {
        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);
        Preconditions.checkNotNull(password);
        Preconditions.checkArgument(password.length() != 0);
        adminDAO.updatePassword(regNo, password);
    }


    /**
     * Method to enroll a student in the school
     *
     * @param regNo
     * @param registeredSubjects
     */
    public void enrollCourses(Long regNo, List<SubjectDTO> registeredSubjects) {

        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);
        Preconditions.checkNotNull(registeredSubjects);
        Preconditions.checkArgument(registeredSubjects.size() >= 5, "A minimum of 5 courses must be enrolled");
        Preconditions.checkArgument(registeredSubjects.size() <= 7, "A maximum of 7 courses can be enrolled");

        //Add courses
        for (SubjectDTO s : registeredSubjects) {
            subjectDAO.addStudent(s.getSubjectCode(), regNo);
        }

        //Assign to teacher
        teacherDAO.assignStudent(regNo);

    }

    /**
     * Method to edit the list of enrolled subjects
     *
     * @param regNo
     * @param registeredSubjects
     */
    public void editCourses(Long regNo, List<SubjectDTO> registeredSubjects) {
        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);
        Preconditions.checkNotNull(registeredSubjects);
        Preconditions.checkArgument(registeredSubjects.size() >= 5, "A minimum of 5 courses must be enrolled");
        Preconditions.checkArgument(registeredSubjects.size() <= 7, "A maximum of 7 courses can be enrolled");

        //Remove from all courses
        Optional<StudentSubjectsDTO> ssDTO = studentDAO.getSubjects(regNo);
        ssDTO.ifPresent(sDTO -> {
            for (SubjectDTO subjectDTO : sDTO.getSubjects()) {
                subjectDAO.removeStudent(subjectDTO.getSubjectCode(), regNo);
            }
        });

        //Register new courses
        for (SubjectDTO subjectDTO : registeredSubjects) {
            subjectDAO.addStudent(subjectDTO.getSubjectCode(), regNo);
        }
    }

    public Optional<StudentSubjectsDTO> getCourses(Long regNo) {
        Preconditions.checkNotNull(regNo);
        Preconditions.checkArgument(regNo > 0);

        Optional<StudentSubjectsDTO> ssDTO = studentDAO.getSubjects(regNo);
        return ssDTO;
    }

}
