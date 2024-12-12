package ui;
import dataaccess.DatabaseException;
import dataaccess.MyCourseRepository;
import dataaccess.MyStudentRepository;
import domain.Course;
import domain.CourseType;
import domain.InvalidValueException;
import domain.Student;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.sql.Date;


public class Cli {
    Scanner scan;
    MyCourseRepository repo;
    MyStudentRepository rep;

    public Cli(MyCourseRepository repo, MyStudentRepository rep) {
        this.scan = new Scanner(System.in);
        this.repo = repo;
        this.rep=rep;
    }


    public void start() {
        String input = "-";
        while (!input.equals("x")) {
            showMenue();
            input = scan.nextLine();
            switch (input) {
                case "1":
                    addCourse();
                    break;

                case "2":
                    showAllCourses();
                    System.out.println("Alle Kurse anzeigen");
                    break;

                case "3":
                    showCourseDetails();
                    break;

                case "4":
                    updateCourseDetails();
                    break;

                case "5":
                    deleteCourseDetails();
                    break;

                case "6":
                    courseSearch();
                    break;

                case "7":
                    runningCourses();
                    break;

                case "8":
                    findByVn();
                    break;

                case "9":
                    showStudentById();
                    break;

                case "10":
                    searchStudentByBirthdate();
                    break;

                case "x":
                    System.out.println("Auf wiedersehen!");
                    break;

                default:inputError();
            }

        }
        scan.close();
    }

    private void searchStudentByBirthdate() {
        System.out.println("Bitte geben Sie das Geburtsdatum des Studenten ein (YYYY-MM-DD):");
        String birthdateInput = scan.nextLine();
        try {
            Date birthdate = Date.valueOf(birthdateInput);
            List<Student> students = rep.searchStudentByBirthdate(birthdate);

            if (students.isEmpty()) {
                System.out.println("Keine Studenten mit diesem Geburtsdatum gefunden.");
            } else {
                for (Student student : students) {
                    System.out.println(student);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ungültiges Datumsformat: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Datenbankfehler bei der Suche nach Studenten: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unbekannter Fehler bei der Suche nach Studenten: " + e.getMessage());
        }
    }


    private void showStudentById() {
        System.out.println("Studenten-ID eingeben: ");
        Long studentId = Long.parseLong(scan.nextLine()); // Eingabe der Studenten-ID

        try {
            // Verwende `rep` statt `repo` für Studenten
            Optional<Student> studentOptional = rep.getByID(studentId);

            if (studentOptional.isPresent()) {
                // Zeige die Details des Studenten an
                System.out.println(studentOptional.get());
            } else {
                System.out.println("Student mit der ID " + studentId + " wurde nicht gefunden!");
            }
        } catch (DatabaseException databaseException) {
            // Behandle Datenbankfehler
            System.out.println("Datenbankfehler bei Studentendetails: " + databaseException.getMessage());
        } catch (Exception exception) {
            // Behandle unbekannte Fehler
            System.out.println("Unbekannter Fehler bei Studentendetails: " + exception.getMessage());
        }
    }


    private void findByVn() {
        System.out.println("Bitte geben Sie den Vornamen des Studenten ein: ");
        String vn =scan.nextLine();
        List<Student> studentList = null;

        try{
            studentList = rep.findByVn(vn);
            for(Student student : studentList){
                System.out.println(student);
            }

        }
        catch (DatabaseException databaseException){
            System.out.println("Datenbanfehler bei der Studentensuche: "+databaseException.getMessage());

        }
        catch (Exception exception){

            System.out.println("Ubenkannter Fehler bei der Kurssuche: "+exception.getMessage());
        }
    }



    private void runningCourses() {
        System.out.println("Aktuell laufende Kurse: ");
        List<Course> list;
        try{
            list = repo.findAllRunningCourses();
            for(Course course : list){
                System.out.println(course);
            }
        }
        catch (DatabaseException databaseException){
            System.out.println("Datenbankfehler bei Kursanzeige für laufende  Kurse: "+databaseException.getMessage());

        }
        catch (Exception exception){
            System.out.println("Unbekannter Fehler bei Kursanzeige für laufende Kurse: "+ exception.getMessage());
        }
    }

    private void courseSearch() {
        System.out.println("Geben Sie einen Suchbergiff an!");
        String searchString = scan.nextLine();
        List<Course> courseList;
        try{
            courseList = repo.findAllCoursesByNameOrDescription(searchString);
            for(Course course : courseList){
                System.out.println(course);
            }

        }
        catch (DatabaseException databaseException){
            System.out.println("Datenbanfehler bei der Kurssuche: "+databaseException.getMessage());

        }
        catch (Exception exception){

            System.out.println("Ubenkannter Fehler bei der Kurssuche: "+exception.getMessage());
        }
    }

    private void deleteCourseDetails() {
        System.out.println("Welchen Kurs möchten Sie löschen? Bitte ID eingeben: ");
        Long courseIdToDelete = Long.parseLong(scan.nextLine());

        try{
            repo.deleteById(courseIdToDelete);
            //System.out.println("Kurs mit ID "+courseIdToDelete+" gelöscht!");
        }
        catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim löschen: "+databaseException.getMessage());
        }
        catch (Exception e){
            System.out.println("Unbekannter beim löschen: "+e.getMessage());
        }
    }

    private void updateCourseDetails() {
        System.out.println("Für welchen Kurs-ID möchten Sie die Kursdetails ändern?");
        Long courseId = Long.parseLong(scan.nextLine());

        try{
            Optional<Course> courseOptional = repo.getByID(courseId);
            if(courseOptional.isEmpty()) {
                System.out.println("Kurs mit der gegebenen ID nicht in der Datenbank");

            }
            else {
                Course course = courseOptional.get();

                System.out.println("Änderungen für folgenden Kurs: ");
                System.out.println(course);

                String name, description, hours, dateFrom, dateTo, courseType;

                System.out.println("Bitte neue Kursdaten angeben");

                System.out.println("Bitte neue Kursdaten angeben (Enter, falls keine Eingabe gewünscht ist): ");
                System.out.println("Name: ");
                name = scan.nextLine();
                System.out.println("Beschreibung: ");
                description = scan.nextLine();
                System.out.println("Stundenanzahl: ");
                hours = scan.nextLine();
                System.out.println("Startdatum (YYYY-MM-DD):");
                dateFrom = scan.nextLine();
                System.out.println("Enddatum (YYYY-MM-DD):");
                dateTo = scan.nextLine();
                System.out.println("Kurstyp (ZA/BF/FF/OE)");
                courseType = scan.nextLine();

                Optional<Course> optionalCourseUpdated = repo.update(new Course(course.getId(), name.equals("") ? course.getName() : name, description.equals("") ? course.getDescription() : description, hours.equals("") ? course.getHours() : Integer.parseInt(hours), dateFrom.equals("") ? course.getBeginDate():Date.valueOf(dateFrom), dateTo.equals("") ? course.getEndDate() : Date.valueOf(dateTo), courseType.equals("")?course.getCourseType():CourseType.valueOf(courseType)));
                optionalCourseUpdated.ifPresentOrElse((c)-> System.out.println("Kurs aktualisiert: " + c), ()-> System.out.println("Kurs konnte nicht aktualisiert werden!"));
            }
        }
        catch(IllegalArgumentException illegalArgumentException){
            System.out.println("Eingabefehler: "+illegalArgumentException.getMessage());
        }
        catch (InvalidValueException invalidValueException){
            System.out.println("Kursdaten nicht korrekt angegeben: "+invalidValueException.getMessage());
        }
        catch (DatabaseException databaseException){
            System.out.println("Datenbankfehler beim einfügen: "+databaseException.getMessage());

        }
        catch (Exception exception){
            System.out.println("Unbekannter Fehler beim einfügen: "+exception.getMessage());
        }

    }

    private void addCourse() {

        String name, description;
        int hours;
        Date dateFrom, dateTo;
        CourseType courseType;

        try{
            System.out.println("Bitte alle Kursdaten angeben:");
            System.out.println("Name: ");
            name = scan.nextLine();
            if(name.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Beschreibung: ");
            description = scan.nextLine();
            if(description.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Stundenanzahl: ");
            hours = Integer.parseInt(scan.nextLine());
            System.out.println("Startdatum (YYYY-MM-DD): ");
            dateFrom = Date.valueOf(scan.nextLine());
            System.out.println("Enddatum (YYYY-MM-DD): ");
            dateTo = Date.valueOf(scan.nextLine());
            System.out.println("Kurstyp: (ZA/BF/FF/OE)");
            courseType = CourseType.valueOf(scan.nextLine());

            Optional<Course> optionalCourse = repo.insert(new Course(name, description, hours, dateFrom, dateTo, courseType));

            if(optionalCourse.isPresent()) {
                System.out.println("Kurs angelegt "+ optionalCourse.get());

            }
            else {
                System.out.println("Kurs konnte nicht nagelegt werden!");
            }

        }catch(IllegalArgumentException illegalArgumentException){
            System.out.println("Eingabefehler: "+illegalArgumentException.getMessage());
        }
        catch (InvalidValueException invalidValueException){
            System.out.println("Kursdaten nicht korrekt angegeben: "+invalidValueException.getMessage());
        }
        catch (DatabaseException databaseException){
            System.out.println("Datenbankfehler beim einfügen: "+databaseException.getMessage());

        }
        catch (Exception exception){
            System.out.println("Unbekannter Fehler beim einfügen: "+exception.getMessage());
        }

    }

    private void showCourseDetails() {
        System.out.println("Für welchen Kurs möchten Sie die Kursdetails anzeigen");
        Long courseId = Long.parseLong(scan.nextLine());
        try{
            Optional<Course> courseOptional = repo.getByID(courseId);
            if(courseOptional.isPresent()){
                System.out.println(courseOptional.get());
            }
            else{
                System.out.println("Kurs mit der ID "+courseId+" wurde nicht gefunden!");
            }
        }
        catch (DatabaseException databaseException){
            System.out.println("Datenbankfehler bei Kursdetailanzeige"+databaseException.getMessage());

        }
        catch (Exception exception){
            System.out.println("Unbekannter fehler bei Kursdetailanzeige: "+exception.getMessage());
        }
    }

    private void showAllCourses() {
        List<Course> list = null;

        try {
            list = repo.getAll();
            if (list.size() > 0) {
                for (Course course : list) {
                    System.out.println(course);
                }
            } else {
                System.out.println("Kursliste leer!");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Anzeigen aller Kurse: "+databaseException.getMessage());

        }
        catch (Exception exception){
            System.out.println("Unbekannter Fehler beim Anzeigen aller Kurse: "+exception);
        }
    }

    private void showMenue() {
        System.out.println("--------------------------Kursmanagement------------------------");
        System.out.println("(1) Kurs eingeben \t (2) Alle Kurse anzeigen \t (3) Kursdetails anzeigen");
        System.out.println("(4) Kursdetails ändern \t (5) Kurs löschen \t (6) Kurssuche");
        System.out.println("(7) Laufende Kurse \t (8) Student Vn suchen  \t (9) Student mit Id suchen");
        System.out.println("(10) Laufende Kurse \t (-) ---  \t (-) ---");
        System.out.println("(x) ENDE");
    }

    private void inputError(){
        System.out.println("Bitte nur die Zahlen der Menüauswahl eingeben");
    }


}