package site.luisicj.ejerciciotablapersonas;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa una persona en un registro de personas.
 */
public class Person {

    /**
     * Variables globales para la secuencia de identificación de personas.
     */
    private static AtomicInteger personSequence = new AtomicInteger(0);
    private int personId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    /**
     * Categorías de edad.
     */
    public enum AgeCategory {
        BABY, CHILD, TEEN, ADULT, SENIOR, UNKNOWN
    };

    /**
     * Persona sin identificación.
     */
    public Person() {
        this(null, null, null);
    }

    /**
     * Person con identificación.
     * @param firstName - nombre
     * @param lastName - apellidos
     * @param birthDate - fecha de nacimiento
     */
    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    /**
     * Crea una persona con identificación.
     * @param personId - identificación
     * @param firstName - nombre
     * @param lastName - apellidos
     * @param birthDate - fecha de nacimiento
     */
    public Person(int personId, String firstName, String lastName, LocalDate birthDate) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    /**
     * getPersonId - obtiene la identificación de la persona
     * @return personId
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * setPersonId - establece la identificación de la persona
     * @param personId - identificación
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    /**
     * getFirstName - devuelve el nombre
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setFirstName - establece el nombre
     * @param firstName - nombre
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * getLastName - devuelve los apellidos
     * @return getLastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setLastName - establece los apellidos
     * @param lastName - apellidos
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * getBirthDate - devuelve la fecha de nacimiento
     * @return getBirthDate
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * setBirthDate - establece la fecha de nacimiento
     * @param birthDate - fecha de nacimiento
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * isValidBirthDate
     * @param bdate - fecha de nacimiento
     * @return true si la fecha de nacimiento es valida, false en caso contrario
     */
    public boolean isValidBirthDate(LocalDate bdate) {
        return isValidBirthDate(bdate, new ArrayList<>());
    }

    /**
     * isValidBirthDate
     * @param bdate - fecha de nacimiento
     * @param errorList - lista de errores
     * @return true si la fecha de nacimiento es valida, false en caso contrario
     */
    public boolean isValidBirthDate(LocalDate bdate, List<String> errorList) {
        if (bdate == null) {
            return true;
        }
        // Birthdate cannot be in the future
        if (bdate.isAfter(LocalDate.now())) {
            errorList.add("Birth date must not be in future.");
            return false;
        }
        return true;
    }

    /**
     * isValidPerson
     * @param errorList - lista de errores
     * @return true si la persona es válida, false en caso contrario
     */
    public boolean isValidPerson(List<String> errorList) {
        return isValidPerson(this, errorList);
    }

    /**
     * isValidPerson
     * @param p - persona
     * @param errorList - lista de errores
     * @return true si la persona es válida, false en caso contrario
     */
    public boolean isValidPerson(Person p, List<String> errorList) {
        boolean isValid = true;
        String fn = p.getFirstName();
        if (fn == null || fn.trim().length() == 0) {
            errorList.add("First name must contain minimum one character.");
            isValid = false;
        }
        String ln = p.getLastName();
        if (ln == null || ln.trim().length() == 0) {
            errorList.add("Last name must contain minimum one character.");
            isValid = false;
        }
        if (!isValidBirthDate(this.getBirthDate(), errorList)) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * getAgeCategory - devuelve la categoría de la persona
     * @return la categoría de la persona
     */
    public AgeCategory getAgeCategory() {
        if (birthDate == null) {
            return AgeCategory.UNKNOWN;
        }
        long years = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (years >= 0 && years < 2) {
            return AgeCategory.BABY;
        } else if (years >= 2 && years < 13) {
            return AgeCategory.CHILD;
        } else if (years >= 13 && years <= 19) {
            return AgeCategory.TEEN;
        } else if (years > 19 && years <= 50) {
            return AgeCategory.ADULT;
        } else if (years > 50) {
            return AgeCategory.SENIOR;
        } else {
            return AgeCategory.UNKNOWN;
        }
    }

    /**
     * save - Guarda una persona
     * @param errorList - lista de errores
     * @return true si la persona es guardada, false en caso contrario
     */
    public boolean save(List<String> errorList) {
        boolean isSaved = false;
        if (isValidPerson(errorList)) {
            System.out.println("Saved " + this.toString());
            isSaved = true;
        }
        return isSaved;
    }

    /**
     * toString - devuelve los datos de una persona como cadena de caracteres
     * @return los datos de una persona como cadena de caracteres
     */
    @Override
    public String toString() {
        return "[personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + "]";
    }

}
