package dao;
import java.util.*;
import util.QueryUtil;
public class ReportDAO{
    public List<String> getJobListing(){
        return QueryUtil.run("SELECT j.job_id, c.name, c.location, j.role, j.package_lpa, j.min_cgpa, j.max_backlogs, j.allowed_departments, j.required_skills "
            +"FROM jobs j JOIN companies c ON j.company_id=c.company_id");
    }
    public List<String> getApplicantsByJob(int jobId){
        return QueryUtil.run("SELECT a.application_id, s.name, s.department, s.cgpa, a.status "
            +"FROM applications a JOIN students s ON a.student_id=s.student_id WHERE a.job_id=?",jobId);
    }
    public List<String> getApplicationsByStatus(String status){
        return QueryUtil.run("SELECT a.application_id, s.name, c.name, j.role, a.status "
            +"FROM applications a JOIN students s ON a.student_id=s.student_id "
            +"JOIN jobs j ON a.job_id=j.job_id JOIN companies c ON j.company_id=c.company_id WHERE a.status=?",status);
    }
    public List<String> getStudentApplications(int studentId){
        return QueryUtil.run("SELECT a.application_id, c.name, j.role, a.status, a.applied_date "
            +"FROM applications a JOIN jobs j ON a.job_id=j.job_id JOIN companies c ON j.company_id=c.company_id WHERE a.student_id=?",studentId);
    }
    public List<String> getStudentInterviews(int studentId){
        return QueryUtil.run("SELECT i.interview_id, c.name, j.role, i.interview_date, i.interview_time, i.venue, i.result "
            +"FROM interviews i JOIN applications a ON i.application_id=a.application_id "
            +"JOIN jobs j ON a.job_id=j.job_id JOIN companies c ON j.company_id=c.company_id WHERE a.student_id=?",studentId);
    }
    public List<String> getAllInterviews(){
        return QueryUtil.run("SELECT i.interview_id, a.application_id, s.name, c.name, j.role, i.interview_date, i.interview_time, i.venue, i.result "
            +"FROM interviews i JOIN applications a ON i.application_id=a.application_id "
            +"JOIN students s ON a.student_id=s.student_id JOIN jobs j ON a.job_id=j.job_id "
            +"JOIN companies c ON j.company_id=c.company_id ORDER BY i.interview_date");
    }
    public List<String> getAllPlacements(){
        return QueryUtil.run("SELECT p.placement_id, s.name, s.department, c.name, j.role, p.package_lpa, p.placed_date "
            +"FROM placements p JOIN students s ON p.student_id=s.student_id "
            +"JOIN jobs j ON p.job_id=j.job_id JOIN companies c ON j.company_id=c.company_id");
    }
    public List<String> getPlacementSummary(){
        return QueryUtil.run("SELECT COUNT(*), (SELECT COUNT(*) FROM placements), COUNT(*)-(SELECT COUNT(*) FROM placements) FROM students");
    }
    public List<String> getUnplacedStudents(){
        return QueryUtil.run("SELECT student_id, name, department, cgpa FROM students WHERE student_id NOT IN (SELECT student_id FROM placements)");
    }
    public List<String> getCompanyWiseCount(){
        return QueryUtil.run("SELECT c.name, COUNT(*) FROM placements p JOIN jobs j ON p.job_id=j.job_id "
            +"JOIN companies c ON j.company_id=c.company_id GROUP BY c.name");
    }
    public List<String> getDepartmentWiseCount(){
        return QueryUtil.run("SELECT s.department, COUNT(*) FROM placements p JOIN students s ON p.student_id=s.student_id GROUP BY s.department");
    }
    public List<String> getPackageStats(){
        return QueryUtil.run("SELECT MAX(package_lpa), ROUND(AVG(package_lpa),2), MIN(package_lpa) FROM placements");
    }
}