package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    static Faker faker = new Faker ((new Locale("ru")));

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(String locale) {
        return faker.address().city();

    }
    public static String generateName(String locale) {
        return faker.name().fullName();
    }

    public static String generatePhone(String locale) {
        return faker.phoneNumber().cellPhone();
    }

    public static class Registration {
        private Registration() {
        }
        public static UserInfo generateUser(String locale) {
            return new UserInfo(
                    generateName("ru"),
                    generatePhone("ru"),
                    generateCity("ru")
            );
        }
    }
    @Value
    public static class UserInfo {
        String name;
        String phone;
        String city;
    }
}