package main;
import java.util.*;
import dao.*;
import model.*;
import service.*;
import util.*;
public class Main{
    static Scanner sc=new Scanner(System.in);
    static StudentDAO studentDAO=new StudentDAO();
    static CompanyDAO companyDAO=new CompanyDAO();
    static JobDAO jobDAO=new JobDAO();
    static AdminDAO adminDAO=new AdminDAO();
    static ReportDAO reportDAO=new ReportDAO();
    static EligibilityService eligibilityService=new EligibilityService();
    static ApplicationService applicationService=new ApplicationService();
    static PlacementService placementService=new PlacementService();
    static final String JOB_HEAD="Jobs (ID | Company | Location | Role | LPA | Min CGPA | Max backlogs | Departments | Skills)";
    static final String INTERVIEW_HEAD="Interviews (ID | App ID | Student | Company | Role | Date | Time | Venue | Result)";

    public static void main(String[] args){
        while(true){
            System.out.println("\n===== COLLEGE PLACEMENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Admin login");
            System.out.println("2. Student login");
            System.out.println("3. Student registration");
            System.out.println("0. Exit");
            int choice=readInt("Enter choice: ");
            switch(choice){
                case 1: adminLogin(); break;
                case 2: studentLogin(); break;
                case 3: registerStudent(); break;
                case 0:
                    System.out.println("Goodbye!");
                    return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    // ---------- input helpers ----------
    static String readStr(String msg){
        System.out.print(msg);
        return sc.nextLine().trim();
    }
    static int readInt(String msg){
        while(true){
            try{
                return Integer.parseInt(readStr(msg));
            }catch(NumberFormatException e){
                System.out.println("Enter a valid whole number");
            }
        }
    }
    static double readDouble(String msg){
        while(true){
            try{
                return Double.parseDouble(readStr(msg));
            }catch(NumberFormatException e){
                System.out.println("Enter a valid number");
            }
        }
    }
    static String askStr(String label,String current){
        String v=readStr(label+" ["+current+"]: ");
        return v.isEmpty()?current:v;
    }
    static double askDouble(String label,double current){
        String v=readStr(label+" ["+current+"]: ");
        if(v.isEmpty()) return current;
        try{
            return Double.parseDouble(v);
        }catch(NumberFormatException e){
            System.out.println("Invalid number, kept old value");
            return current;
        }
    }
    static int askInt(String label,int current){
        String v=readStr(label+" ["+current+"]: ");
        if(v.isEmpty()) return current;
        try{
            return Integer.parseInt(v);
        }catch(NumberFormatException e){
            System.out.println("Invalid number, kept old value");
            return current;
        }
    }
    static void printList(String heading,List<?> list){
        System.out.println("\n--- "+heading+" ---");
        if(list.isEmpty()){
            System.out.println("No records found");
            return;
        }
        for(Object o:list){
            System.out.println(o);
        }
    }
    static String chooseStatus(){
        String[] statuses={"Applied","Shortlisted","Interview Scheduled","Selected","Rejected"};
        for(int i=0;i<statuses.length;i++){
            System.out.println((i+1)+". "+statuses[i]);
        }
        int n=readInt("Choose status: ");
        if(n<1||n>statuses.length){
            System.out.println("Invalid choice");
            return null;
        }
        return statuses[n-1];
    }

    // ---------- login and registration ----------
    static void adminLogin(){
        String user=readStr("Username: ");
        String pass=readStr("Password: ");
        if(adminDAO.login(user,PasswordUtil.hash(pass))){
            System.out.println("Login successful");
            adminMenu();
        }else{
            System.out.println("Invalid username or password");
        }
    }
    static void studentLogin(){
        String email=readStr("Email: ");
        String pass=readStr("Password: ");
        Student s=studentDAO.login(email,PasswordUtil.hash(pass));
        if(s!=null){
            System.out.println("Welcome, "+s.getName());
            studentMenu(s);
        }else{
            System.out.println("Invalid email or password");
        }
    }
    static void registerStudent(){
        System.out.println("\n--- Student Registration ---");
        String name=readStr("Name: ");
        String email=readStr("Email: ");
        String password=readStr("Password: ");
        String phone=readStr("Phone (10 digits): ");
        String dept=readStr("Department: ");
        double cgpa=readDouble("CGPA: ");
        int backlogs=readInt("Backlogs: ");
        String skills=readStr("Skills (comma separated): ");
        if(!Validator.notEmpty(name)||!Validator.notEmpty(password)||!Validator.notEmpty(dept)){
            System.out.println("Name, password and department cannot be empty");
            return;
        }
        if(!Validator.isValidEmail(email)){
            System.out.println("Invalid email");
            return;
        }
        if(!Validator.isValidPhone(phone)){
            System.out.println("Phone must be 10 digits");
            return;
        }
        if(!Validator.isValidCgpa(cgpa)){
            System.out.println("CGPA must be between 0 and 10");
            return;
        }
        if(backlogs<0){
            System.out.println("Backlogs cannot be negative");
            return;
        }
        Student s=new Student(0,name,email,PasswordUtil.hash(password),phone,dept,cgpa,backlogs,skills);
        System.out.println(studentDAO.addStudent(s)?"Registered successfully":"Registration failed (email may already exist)");
    }

    // ---------- admin menu ----------
    static void adminMenu(){
        String[] menu={"Add student","View all students","Search students","Update student","Delete student",
            "Add company","View companies","Update company","Delete company",
            "Add job","View jobs","Update job criteria","Delete job",
            "View eligible students for a job","View applicants of a job","View applications by status","Update application status",
            "Schedule interview","View interviews","Record interview result","Select student (final selection)",
            "View placement records","Reports"};
        while(true){
            System.out.println("\n===== ADMIN MENU =====");
            for(int i=0;i<menu.length;i++){
                System.out.println((i+1)+". "+menu[i]);
            }
            System.out.println("0. Logout");
            int choice=readInt("Enter choice: ");
            switch(choice){
                case 1: registerStudent(); break;
                case 2: printList("All students",studentDAO.getAllStudents()); break;
                case 3: printList("Search results",studentDAO.searchStudents(readStr("Name or department: "))); break;
                case 4: updateStudentById(); break;
                case 5: deleteStudent(); break;
                case 6: addCompany(); break;
                case 7: printList("Companies",companyDAO.getAllCompanies()); break;
                case 8: updateCompany(); break;
                case 9: deleteCompany(); break;
                case 10: addJob(); break;
                case 11: printList(JOB_HEAD,reportDAO.getJobListing()); break;
                case 12: updateJob(); break;
                case 13: deleteJob(); break;
                case 14: showEligible(); break;
                case 15: printList("Applicants (App ID | Name | Dept | CGPA | Status)",reportDAO.getApplicantsByJob(readInt("Job id: "))); break;
                case 16: applicationsByStatus(); break;
                case 17: updateStatus(); break;
                case 18: scheduleInterview(); break;
                case 19: printList(INTERVIEW_HEAD,reportDAO.getAllInterviews()); break;
                case 20: recordResult(); break;
                case 21: selectStudent(); break;
                case 22: printList("Placements (ID | Student | Dept | Company | Role | LPA | Date)",reportDAO.getAllPlacements()); break;
                case 23: reportsMenu(); break;
                case 0: return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    // ---------- student management ----------
    static void updateStudentById(){
        Student s=studentDAO.getStudentById(readInt("Student id: "));
        if(s==null){
            System.out.println("Student not found");
            return;
        }
        editStudent(s);
    }
    static void editStudent(Student s){
        System.out.println("Current: "+s);
        System.out.println("Press Enter to keep the current value");
        s.setName(askStr("Name",s.getName()));
        String phone=askStr("Phone",s.getPhone());
        if(Validator.isValidPhone(phone)) s.setPhone(phone);
        else System.out.println("Phone must be 10 digits, kept old value");
        s.setDepartment(askStr("Department",s.getDepartment()));
        double cgpa=askDouble("CGPA",s.getCgpa());
        if(Validator.isValidCgpa(cgpa)) s.setCgpa(cgpa);
        else System.out.println("CGPA must be between 0 and 10, kept old value");
        int backlogs=askInt("Backlogs",s.getBacklogs());
        if(backlogs>=0) s.setBacklogs(backlogs);
        else System.out.println("Backlogs cannot be negative, kept old value");
        s.setSkills(askStr("Skills",s.getSkills()));
        System.out.println(studentDAO.updateStudent(s)?"Profile updated":"Update failed");
    }
    static void deleteStudent(){
        int id=readInt("Student id: ");
        System.out.println(studentDAO.deleteStudent(id)?"Student deleted":"Could not delete (student not found or has applications)");
    }

    // ---------- company management ----------
    static void addCompany(){
        String name=readStr("Company name: ");
        if(!Validator.notEmpty(name)){
            System.out.println("Name cannot be empty");
            return;
        }
        String location=readStr("Location: ");
        String contact=readStr("Contact email: ");
        System.out.println(companyDAO.addCompany(new Company(0,name,location,contact))?"Company added":"Could not add company");
    }
    static void updateCompany(){
        printList("Companies",companyDAO.getAllCompanies());
        int id=readInt("Company id: ");
        Company c=null;
        for(Company x:companyDAO.getAllCompanies()){
            if(x.getCompanyId()==id) c=x;
        }
        if(c==null){
            System.out.println("Company not found");
            return;
        }
        c.setName(askStr("Name",c.getName()));
        c.setLocation(askStr("Location",c.getLocation()));
        c.setContact(askStr("Contact",c.getContact()));
        System.out.println(companyDAO.updateCompany(c)?"Company updated":"Update failed");
    }
    static void deleteCompany(){
        printList("Companies",companyDAO.getAllCompanies());
        int id=readInt("Company id: ");
        System.out.println(companyDAO.deleteCompany(id)?"Company deleted":"Could not delete (delete this company's jobs first)");
    }

    // ---------- job management ----------
    static void addJob(){
        printList("Companies",companyDAO.getAllCompanies());
        int companyId=readInt("Company id: ");
        String role=readStr("Job role: ");
        double pkg=readDouble("Package (LPA): ");
        double minCgpa=readDouble("Minimum CGPA: ");
        int maxBacklogs=readInt("Maximum backlogs allowed: ");
        String depts=readStr("Allowed departments (comma separated): ");
        String skills=readStr("Required skills (comma separated): ");
        if(!Validator.notEmpty(role)||pkg<0||!Validator.isValidCgpa(minCgpa)||maxBacklogs<0){
            System.out.println("Invalid input");
            return;
        }
        System.out.println(jobDAO.addJob(new Job(0,companyId,role,pkg,minCgpa,maxBacklogs,depts,skills))?"Job added":"Could not add job (check the company id)");
    }
    static void updateJob(){
        printList(JOB_HEAD,reportDAO.getJobListing());
        Job j=jobDAO.getJobById(readInt("Job id: "));
        if(j==null){
            System.out.println("Job not found");
            return;
        }
        System.out.println("Press Enter to keep the current value");
        j.setRole(askStr("Role",j.getRole()));
        j.setPackageLpa(askDouble("Package (LPA)",j.getPackageLpa()));
        j.setMinCgpa(askDouble("Minimum CGPA",j.getMinCgpa()));
        j.setMaxBacklogs(askInt("Maximum backlogs",j.getMaxBacklogs()));
        j.setAllowedDepartments(askStr("Allowed departments",j.getAllowedDepartments()));
        j.setRequiredSkills(askStr("Required skills",j.getRequiredSkills()));
        System.out.println(jobDAO.updateJob(j)?"Job updated":"Update failed");
    }
    static void deleteJob(){
        printList(JOB_HEAD,reportDAO.getJobListing());
        int id=readInt("Job id: ");
        System.out.println(jobDAO.deleteJob(id)?"Job deleted":"Could not delete (job not found or has applications)");
    }
    static void showEligible(){
        printList(JOB_HEAD,reportDAO.getJobListing());
        int jobId=readInt("Job id: ");
        printList("Eligible students",eligibilityService.getEligibleStudents(jobId));
    }

    // ---------- applications, interviews, selection ----------
    static void applicationsByStatus(){
        String status=chooseStatus();
        if(status==null) return;
        printList(status+" applications (App ID | Student | Company | Role | Status)",reportDAO.getApplicationsByStatus(status));
    }
    static void updateStatus(){
        int appId=readInt("Application id: ");
        String status=chooseStatus();
        if(status==null) return;
        if(status.equals("Selected")){
            System.out.println("Use 'Select student' (option 21) so the placement record is created");
            return;
        }
        System.out.println(applicationService.changeStatus(appId,status));
    }
    static void scheduleInterview(){
        printList("Shortlisted applications (App ID | Student | Company | Role | Status)",reportDAO.getApplicationsByStatus("Shortlisted"));
        int appId=readInt("Application id: ");
        String date=readStr("Date (yyyy-mm-dd): ");
        String time=readStr("Time (HH:mm): ");
        String venue=readStr("Venue or mode: ");
        System.out.println(placementService.scheduleInterview(appId,date,time,venue));
    }
    static void recordResult(){
        printList(INTERVIEW_HEAD,reportDAO.getAllInterviews());
        int id=readInt("Interview id: ");
        int r=readInt("1. Passed  2. Failed : ");
        if(r!=1&&r!=2){
            System.out.println("Invalid choice");
            return;
        }
        System.out.println(placementService.recordResult(id,r==1?"Passed":"Failed"));
    }
    static void selectStudent(){
        printList("Applications awaiting selection (App ID | Student | Company | Role | Status)",reportDAO.getApplicationsByStatus("Interview Scheduled"));
        int appId=readInt("Application id to select: ");
        System.out.println(placementService.selectStudent(appId));
    }

    // ---------- reports ----------
    static void reportsMenu(){
        while(true){
            System.out.println("\n--- REPORTS ---");
            System.out.println("1. Placed vs unplaced summary");
            System.out.println("2. Unplaced students");
            System.out.println("3. Company-wise placements");
            System.out.println("4. Department-wise placements");
            System.out.println("5. Package statistics");
            System.out.println("0. Back");
            int choice=readInt("Enter choice: ");
            switch(choice){
                case 1: printList("Total students | Placed | Unplaced",reportDAO.getPlacementSummary()); break;
                case 2: printList("Unplaced students (ID | Name | Dept | CGPA)",reportDAO.getUnplacedStudents()); break;
                case 3: printList("Company-wise placements (Company | Count)",reportDAO.getCompanyWiseCount()); break;
                case 4: printList("Department-wise placements (Department | Count)",reportDAO.getDepartmentWiseCount()); break;
                case 5: printList("Package statistics in LPA (Highest | Average | Lowest)",reportDAO.getPackageStats()); break;
                case 0: return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    // ---------- student menu ----------
    static List<Job> getEligibleJobs(Student s){
        List<Job> list=new ArrayList<>();
        for(Job j:jobDAO.getAllJobs()){
            if(eligibilityService.isEligible(s,j)){
                list.add(j);
            }
        }
        return list;
    }
    static void studentMenu(Student s){
        String[] menu={"View my profile","Update my profile","View all available jobs","View jobs I am eligible for",
            "Apply for a job","View my application status","View my interviews"};
        while(true){
            System.out.println("\n===== STUDENT MENU ("+s.getName()+") =====");
            for(int i=0;i<menu.length;i++){
                System.out.println((i+1)+". "+menu[i]);
            }
            System.out.println("0. Logout");
            int choice=readInt("Enter choice: ");
            switch(choice){
                case 1: System.out.println(s); break;
                case 2: editStudent(s); break;
                case 3: printList(JOB_HEAD,reportDAO.getJobListing()); break;
                case 4: printList("Jobs you are eligible for",getEligibleJobs(s)); break;
                case 5:
                    printList("Jobs you are eligible for",getEligibleJobs(s));
                    int jobId=readInt("Job id to apply: ");
                    System.out.println(applicationService.apply(s.getStudentId(),jobId));
                    break;
                case 6: printList("My applications (App ID | Company | Role | Status | Date)",reportDAO.getStudentApplications(s.getStudentId())); break;
                case 7: printList("My interviews (ID | Company | Role | Date | Time | Venue | Result)",reportDAO.getStudentInterviews(s.getStudentId())); break;
                case 0: return;
                default: System.out.println("Invalid choice");
            }
        }
    }
}