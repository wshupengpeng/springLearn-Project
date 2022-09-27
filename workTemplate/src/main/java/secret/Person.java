package secret;

import lombok.Data;

@Data
public class Person {
    boolean isSuccess;
    boolean success;

    public static void main(String[] args) {
        Person person = new Person();
    }
}
