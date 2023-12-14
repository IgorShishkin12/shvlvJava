
class Teacher {
    private String name;

    public Teacher(String name) {
        this.name = name;
    }
}

enum CourseType {
    FULL_TIME, DISTANCE_LEARNING
}

class Subject {
    private Teacher teacher;
    private double minAverageGrade;
    private CourseType courseType;

    public Subject(Teacher teacher, double minAverageGrade, CourseType courseType) {
        this.teacher = teacher;
        this.minAverageGrade = minAverageGrade;
        this.courseType = courseType;
    }

    public double getMinAverageGrade() {
        return minAverageGrade;
    }

    public CourseType getCourseType() {
        return courseType;
    }
}

class Student {
    private String name;
    private double averageGrade;
    private CourseType courseType;

    public Student(String name, double averageGrade, CourseType courseType) {
        this.name = name;
        this.averageGrade = averageGrade;
        this.courseType = courseType;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public CourseType getCourseType() {
        return courseType;
    }
}

class Institute {
    public static boolean canAttendSubject(Student student, Subject subject) {
        // Проверка, соответствует ли тип обучения студента типу предмета
        if (student.getCourseType() != subject.getCourseType()) {
            return false;
        }

        // Проверка, достаточен ли средний балл студента для посещения предмета
        if (student.getAverageGrade() >= subject.getMinAverageGrade()) {
            return true;
        }

        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        Teacher mathTeacher = new Teacher("Mr. Smith");
        Subject mathSubject = new Subject(mathTeacher, 4.5, CourseType.FULL_TIME);

        Student john = new Student("John Doe", 4.7, CourseType.FULL_TIME);
        Student jane = new Student("Jane Doe", 4.0, CourseType.DISTANCE_LEARNING);

        System.out.println("John can attend math subject: " + Institute.canAttendSubject(john, mathSubject));
        System.out.println("Jane can attend math subject: " + Institute.canAttendSubject(jane, mathSubject));
    }
}
