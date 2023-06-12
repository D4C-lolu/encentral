package D4C.encentral.dto.subject;

import D4C.encentral.model.subject.Subject;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO used for representing/creating a Subject object
 */
public class SubjectDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("subject_code")
    private String subjectCode;

    public SubjectDTO(){

    }
    public SubjectDTO(String name, String subjectCode){
        this.name=name;
        this.subjectCode=subjectCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    @Override
    public String toString() {
        return "SubjectDTO{" +
                "name='" + name + '\'' +
                ", subjectCode='" + subjectCode + '\'' +
                '}';
    }
}
