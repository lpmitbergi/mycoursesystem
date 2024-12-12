# Dokumentation der Klassen

## 1. Assert

Die Klasse `Assert` dient als Helfer, um sicherzustellen, dass bestimmte Bedingungen vor dem Programmablauf erfüllt sind. Sie wird vor allem zur Validierung von Eingaben genutzt.

### Funktionalitäten
- **`public static void notNull(Object o)`**  
  Diese Methode prüft, ob das übergebene Objekt `null` ist. Falls ja, wird eine `IllegalArgumentException` ausgelöst. Das ist besonders nützlich, um sicherzustellen, dass wichtige Objekte nicht fehlen.

---

## 2. DatabaseException

Die `DatabaseException`-Klasse ist eine benutzerdefinierte Ausnahme für den Umgang mit Datenbankfehlern. Sie hilft dabei, Fehlermeldungen klar und verständlich zu gestalten.

### Konstruktor
- **`public DatabaseException(String message)`**  
  Erstellt eine neue Ausnahme mit einer spezifischen Fehlermeldung, die den Fehler beschreibt.

---

## 3. MysqlDatabaseConnection

Diese Klasse verwaltet die Verbindung zur MySQL-Datenbank und stellt sicher, dass nur eine einzige Verbindung (Singleton) aktiv ist.

### Funktionalitäten
- **`public static Connection getConnection(String url, String user, String pwd)`**  
  Baut eine Verbindung zur Datenbank auf, falls noch keine existiert. Wenn bereits eine Verbindung besteht, wird diese zurückgegeben.

---

## 4. BaseRepository<T, I>

Das `BaseRepository`-Interface definiert grundlegende Datenbankoperationen (CRUD: Create, Read, Update, Delete), die von konkreten Implementierungen genutzt werden.

### Methoden
- **`Optional<T> insert(T entity)`**  
  Fügt ein neues Objekt in die Datenbank ein.

- **`Optional<T> getById(I id)`**  
  Ruft ein Objekt anhand seiner ID ab.

- **`List<T> getAll()`**  
  Gibt eine Liste aller Objekte zurück.

- **`Optional<T> update(T entity)`**  
  Aktualisiert ein bestehendes Objekt.

- **`void deleteById(I id)`**  
  Löscht ein Objekt mit der angegebenen ID aus der Datenbank.

---

## 5. MyCourseRepository

Die Klasse `MyCourseRepository` erweitert das `BaseRepository`-Interface und fügt spezifische Methoden für den Umgang mit Kursen hinzu.

### Methoden
- **`List<Course> findAllByCourseName(String name)`**  
  Sucht nach Kursen mit einem bestimmten Namen.

- **`List<Course> findAllCoursesByDescription(String description)`**  
  Findet Kurse basierend auf einer Beschreibung.

- **`List<Course> findAllCoursesByNameOrDescription(String searchText)`**  
  Gibt Kurse zurück, die entweder im Namen oder in der Beschreibung den Suchtext enthalten.

- **`List<Course> findAllCoursesByCourseType(CourseType courseType)`**  
  Sucht Kurse eines bestimmten Typs.

- **`List<Course> findAllCoursesByStartDate(Date startDate)`**  
  Gibt Kurse zurück, die an einem bestimmten Datum starten.

- **`List<Course> findAllRunningCourses()`**  
  Gibt alle derzeit laufenden Kurse zurück.

---

## 6. MySqlCourseRepository

Die Klasse `MySqlCourseRepository` implementiert das `MyCourseRepository` und enthält die SQL-Logik zur Verwaltung der Kursdaten in einer MySQL-Datenbank.

### Wichtige Attribute
- **`private Connection con`**  
  Die Datenbankverbindung, die für Abfragen verwendet wird.

### Methoden

#### Konstruktor
- **`public MySqlCourseRepository()`**  
  Initialisiert die Datenbankverbindung.

#### CRUD-Methoden
- **`@Override public Optional<Course> insert(Course entity)`**  
  Fügt einen neuen Kurs ein.

- **`@Override public Optional<Course> getById(Long id)`**  
  Holt einen Kurs anhand seiner ID.

- **`@Override public List<Course> getAll()`**  
  Gibt alle Kurse zurück.

- **`@Override public Optional<Course> update(Course entity)`**  
  Aktualisiert einen Kurs.

- **`@Override public void deleteById(Long id)`**  
  Löscht einen Kurs nach ID, prüft aber vorher, ob der Kurs existiert.

#### Zusätzliche Methoden
- **`@Override public List<Course> findAllByCourseName(String name)`**  
  Sucht nach Kursen mit einem bestimmten Namen.

- **`@Override public List<Course> findAllCoursesByDescription(String description)`**  
  Sucht nach Kursen basierend auf ihrer Beschreibung.

- **`@Override public List<Course> findAllCoursesByNameOrDescription(String searchText)`**  
  Sucht nach Kursen mit einem bestimmten Text im Namen oder in der Beschreibung.

- **`private int countCoursesInDbWithId(Long id)`**  
  Zählt, wie viele Kurse mit einer bestimmten ID in der Datenbank existieren.

---

## 7. Course

Die Klasse `Course` repräsentiert eine Kursentität mit verschiedenen Attributen, wie Name, Beschreibung, Dauer, Start-/Enddatum und Kurstyp.

### Attribute
- **`private String name`**  
  Der Name des Kurses.

- **`private String description`**  
  Eine Beschreibung des Kurses.

- **`private int hours`**  
  Die Anzahl der Stunden, die der Kurs umfasst.

- **`private Date beginDate`**  
  Das Startdatum des Kurses.

- **`private Date endDate`**  
  Das Enddatum des Kurses.

- **`private CourseType courseType`**  
  Der Typ des Kurses (z. B. Onlinekurs, Präsenzkurs).

### Konstruktoren
- **`public Course(Long id, String name, String description, int hours, Date beginDate, Date endDate, CourseType courseType)`**  
  Erstellt eine Kursinstanz mit allen Attributen.

- **`public Course(String name, String description, int hours, Date beginDate, Date endDate, CourseType courseType)`**  
  Erstellt eine Kursinstanz ohne ID.

### Funktionalitäten
- **Getter und Setter:**  
  Stellen sicher, dass nur gültige Werte zugewiesen werden (z. B. der Name muss mindestens zwei Zeichen lang sein).

- **`@Override public String toString()`**  
  Gibt eine lesbare Darstellung des Kurses zurück.

---

# Aufgabe 5

# Buchung Management

## Domänenklasse: Buchung

### Attribute:
- **buchungId** (Primärschlüssel)
- **studentId** (Fremdschlüssel zur Student-Tabelle)
- **kursId** (Fremdschlüssel zur Kurs-Tabelle)
- **buchungsdatum** (Datum der Buchung)

## DAO für Buchungen

### Interface: BuchungRepository
Ein eigenes Interface **BuchungRepository**, abgeleitet von einem generischen **BaseRepository**.

#### Methoden:
- `buchungErstellen(Buchung buchung)`
- `buchungenFuerStudent(int studentId)`
- `buchungenFuerKurs(int kursId)`

### MySQL-Implementierung

Eine Klasse **MySqlBuchungRepository**, die die Methoden des Interfaces implementiert und CRUD-Operationen auf der Tabelle `buchung` ausführt.

## Erweiterung der CLI

### Neue Optionen:
- **Buchung erstellen**
- **Alle Buchungen eines Studenten anzeigen**
- **Alle Buchungen eines Kurses anzeigen**

## UML-Diagramme zur Veranschaulichung

### UML-Diagramme:
- **Klasse Buchung** und ihre Beziehung zu **Student** und **Kurs**.
- **Das BuchungRepository-Interface** und seine Implementierung.