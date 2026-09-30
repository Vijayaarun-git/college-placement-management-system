package service;
import java.util.*;
import dao.JobDAO;
import dao.StudentDAO;
import model.Job;
import model.Student;
public class EligibilityService{
    private StudentDAO studentDAO=new StudentDAO();
    private JobDAO jobDAO=new JobDAO();
    // Check one student against one job
    public boolean isEligible(Student s,Job j){
        if(s.getCgpa()<j.getMinCgpa()) return false;
        if(s.getBacklogs()>j.getMaxBacklogs()) return false;
        if(!hasDepartment(s,j)) return false;
        return hasSkills(s,j);
    }
    // Get all eligible students for a job
    public List<Student> getEligibleStudents(int jobId){
        List<Student> result=new ArrayList<>();
        Job job=jobDAO.getJobById(jobId);
        if(job==null){
            System.out.println("Job not found");
            return result;
        }
        for(Student s:studentDAO.getAllStudents()){
            if(isEligible(s,job)){
                result.add(s);
            }
        }
        return result;
    }
    // Rule 3: department must be in the allowed list
    private boolean hasDepartment(Student s,Job j){
        Set<String> allowed=toSet(j.getAllowedDepartments());
        if(allowed.isEmpty()) return true;
        if(s.getDepartment()==null) return false;
        return allowed.contains(s.getDepartment().trim().toLowerCase());
    }
    // Rule 4: student must have every required skill
    private boolean hasSkills(Student s,Job j){
        Set<String> required=toSet(j.getRequiredSkills());
        Set<String> have=toSet(s.getSkills());
        return have.containsAll(required);
    }
    // Helper: "Java, SQL" -> {"java","sql"}
    private Set<String> toSet(String text){
        Set<String> set=new HashSet<>();
        if(text==null) return set;
        for(String part:text.split(",")){
            String p=part.trim().toLowerCase();
            if(!p.isEmpty()){
                set.add(p);
            }
        }
        return set;
    }
}