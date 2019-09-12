package org.eclipse.yasson.serializers.model;

import java.util.Objects;

public class PoJoWithAllTypes {
    private String firstName;
    private String lastName;
    private int age;
    private byte numberOfchildren;
    private boolean married;
    private boolean employed;

    public PoJoWithAllTypes() {
    }

    public PoJoWithAllTypes(String firstName, String lastName, int age, byte numberOfchildren, boolean married, boolean employed) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.numberOfchildren = numberOfchildren;
        this.married = married;
        this.employed = employed;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public byte getNumberOfchildren() {
        return numberOfchildren;
    }

    public void setNumberOfchildren(byte numberOfchildren) {
        this.numberOfchildren = numberOfchildren;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public boolean isEmployed() {
        return employed;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }

    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append('"').append("firstName").append('"').append(':').append('"').append(firstName).append('"').append(',');
        sb.append('"').append("lastName").append('"').append(':').append('"').append(lastName).append('"').append(',');
        sb.append('"').append("age").append('"').append(':').append(age).append(',');
        sb.append('"').append("numberOfchildren").append('"').append(':').append(numberOfchildren).append(',');
        sb.append('"').append("married").append('"').append(':').append(married).append(',');
        sb.append('"').append("employed").append('"').append(':').append(employed);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof PoJoWithAllTypes) {
            return Objects.equals(this.firstName, ((PoJoWithAllTypes)o).firstName)
                    && Objects.equals(this.lastName, ((PoJoWithAllTypes)o).lastName)
                    && this.age == ((PoJoWithAllTypes)o).age
                    && this.numberOfchildren == ((PoJoWithAllTypes)o).numberOfchildren
                    && this.married == ((PoJoWithAllTypes)o).married
                    && this.employed == ((PoJoWithAllTypes)o).employed;

        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, numberOfchildren, married, employed);
    }

}
