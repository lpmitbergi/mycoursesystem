package domain;

import java.sql.Date;

public class Student extends BaseEntity {
    private String vn;
    private String nn;
    private Date birthdate;

    public Student(Long id, String vn, String nn, Date birthdate) {
        super(id);
        this.vn = vn;
        this.nn = nn;
        this.birthdate = birthdate;
    }

    public Student(long id, String vn, String nn, Date birthdate, String email) {
        super(null);
        this.vn = vn;
        this.nn = nn;
        this.birthdate = birthdate;
    }

    public String getVn() {
        return vn;
    }

    public void setVn(String vn) {
        if (vn != null && vn.length() > 0) {
            this.vn = vn;
        } else {
            throw new InvalidValueException("Vorname darf nicht leer sein!");
        }
    }

    public String getNn() {
        return nn;
    }

    public void setNn(String nn) {
        if (nn != null && nn.length() > 0) {
            this.nn = nn;
        } else {
            throw new InvalidValueException("Nachname darf nicht leer sein!");

        }
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() + // Ruft die ID aus BaseEntity ab
                ", vn='" + vn + '\'' +
                ", nn='" + nn + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}