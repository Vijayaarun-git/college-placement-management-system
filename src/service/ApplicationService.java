package service;
import java.util.*;
import dao.ApplicationDAO;
import dao.JobDAO;
import dao.StudentDAO;
import model.Job;
import model.Student;
public class ApplicationService{
    private StudentDAO studentDAO=new StudentDAO();
    private JobDAO jobDAO=new JobDAO();
    private ApplicationDAO applicationDAO=new ApplicationDAO();
    private EligibilityService eligibilityService=new EligibilityService();
    private static final List<String> VALID_STATUS=Arrays.asList("Applied","Shortlisted","Interview Scheduled","Selected","Rejected");
    // Student applies for a job. Returns a message to show the user.
    public String apply(int studentId,int jobId){
        Student s=studentDAO.getStudentById(studentId);
        if(s==null) return "Student not found";
        Job j=jobDAO.getJobById(jobId);
        if(j==null) return "Job not found";
        if(!eligibilityService.isEligible(s,j)) return "You are not eligible for this job";
        if(applicationDAO.hasApplied(studentId,jobId)) return "You have already applied for this job";
        if(applicationDAO.addApplication(studentId,jobId)) return "Applied successfully";
        return "Could not apply, try again";
    }
    // Admin changes the status of an application
    public String changeStatus(int applicationId,String status){
        if(!VALID_STATUS.contains(status)) return "Invalid status";
        if(applicationDAO.updateStatus(applicationId,status)) return "Status updated to "+status;
        return "Application not found";
    }
}