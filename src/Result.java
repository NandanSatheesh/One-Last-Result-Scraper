import java.util.regex.Pattern;

public class Result implements Comparable {


    private String subjectCode ;
    private String subjectName ;
    private String internalMarks ;
    private String grade ;
    private int gradePoint = 0 ;
    private int subjectCredits ;

    public void setSubjectCredits() {




        if(this.subjectCode.equalsIgnoreCase("15CSP78")){

            this.subjectCredits = 2 ;

        }
        else if(Pattern.matches("15[A-Z]{2}[L][1-9]{2}", this.subjectCode) )
            this.subjectCredits = 2 ;
        else if(Pattern.matches("15[A-Z]{2}[7][1-3]", this.subjectCode) )
            this.subjectCredits = 4 ;
        else if(Pattern.matches("15[A-Z]{2}[7][4|5][1-9]", this.subjectCode))
            this.subjectCredits = 3 ;

    }

    public int getSubjectCredits() {
        return subjectCredits;
    }

    public void setSubjectCredits(int subjectCredits) {
        this.subjectCredits = subjectCredits;
    }

    public Result() {
    }

    public Result(String subjectCode, String subjectName, String internalMarks, String grade) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.internalMarks = internalMarks;
        this.grade = grade;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getInternalMarks() {
        return internalMarks;
    }

    public void setInternalMarks(String internalMarks) {
        this.internalMarks = internalMarks;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
        setGradePoint();
        setSubjectCredits();
    }

    public void setGradePoint() {

        switch(this.getGrade().trim()) {

            case "S+":
                this.gradePoint = 10;
                break ;
            case "S":
                this.gradePoint = 9;
                break ;
            case "A":
                this.gradePoint = 8;
                break ;
            case "B":
                this.gradePoint = 7;
                break ;
            case "C":
                this.gradePoint = 6;
                break ;
            case "D":
                this.gradePoint = 5;
                break ;
            case "E":
                this.gradePoint = 4;
        }
    }

    public int getGradePoint() {
        return gradePoint;
    }

    @Override
    public int compareTo(Object o) {
        Result result = (Result) o ;
        return this.subjectCode.compareTo(result.subjectCode);
    }

    @Override
    public String toString() {
        return getSubjectCode()+","+getSubjectName()
                +","+getInternalMarks()+","
                +getGrade()+","+getGradePoint()+","+getSubjectCredits();
    }
}
