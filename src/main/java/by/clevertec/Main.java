package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.model.Car;
import by.clevertec.model.Examination;
import by.clevertec.model.Flower;
import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.model.Student;
import by.clevertec.util.Util;

import java.security.KeyPair;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
        task17();
        task18();
        task19("C-3");
        task20();
        task21();
        task22();
    }

    public static void task1() {
        System.out.println("TASK 1");

        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getAge() >= 10 && a.getAge() < 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        l -> IntStream.range(0, l.size())
                                .mapToObj(i -> new SimpleEntry<>(i, l.get(i)))
                                .collect(Collectors.groupingBy(
                                        e -> e.getKey() / 7,
                                        Collectors.mapping(SimpleEntry::getValue, Collectors.toList())
                                ))))
                .forEach((k, v) -> System.out.println("\t Зоопарк №" + (k + 1) + " --- 7 животных:" + v));
    }

    public static void task2() {
        System.out.println("TASK 2");

        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> "Japanese".equals(a.getOrigin()))
                .peek(a -> {
                    if ("Female".equals(a.getGender())) {
                        a.setBread(a.getBread().toUpperCase());
                    }
                })
                .map(Animal::getBread)
                .forEach(b -> System.out.println("\t" + b));
    }

    public static void task3() {
        System.out.println("TASK 3");

        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(o -> o.startsWith("A"))
                .distinct()
                .forEach(o -> System.out.println("\t" + o));
    }

    public static void task4() {
        System.out.println("TASK 4");

        List<Animal> animals = Util.getAnimals();
        System.out.println("\tколичество всех животных пола = Female: "
                + animals.stream()
                .filter(a -> "Female".equals(a.getGender()))
                .count());
    }

    public static void task5() {
        System.out.println("TASK 5");

        List<Animal> animals = Util.getAnimals();
        System.out.println("\tЕсть ли хотя бы одно животное среди >=20 и <=30 лет из Венгрии? --- "
                + animals.stream()
                .filter(a -> a.getAge() >= 20 && a.getAge() <= 30)
                .anyMatch(a -> "Hungarian".equals(a.getOrigin())));
    }

    public static void task6() {
        System.out.println("TASK 6");

        List<Animal> animals = Util.getAnimals();
        System.out.println("\tВсе ли животные полов Male или Female? --- "
                + animals.stream()
                .allMatch(a -> "Male".equals(a.getGender()) || "Female".equals(a.getGender())));
    }

    public static void task7() {
        System.out.println("TASK 7");

        List<Animal> animals = Util.getAnimals();
        System.out.println("\tПравда, что ни одно животное страну происхождения \"Oceania\"? --- "
                + animals.stream()
                .noneMatch(a -> "Oceanian".equals(a.getOrigin())));
    }

    public static void task8() {
        System.out.println("TASK 8");

        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .reduce((r, a) -> r.getAge() > a.getAge() ? r : a)
                .ifPresentOrElse(a -> System.out.println("\tВозраст самого старого из 100 первых отсортированных по породе животных: --- " + a.getAge()),
                        () -> System.out.println("\tЖивотных нет"));
    }

    public static void task9() {
        System.out.println("TASK 9");

        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .reduce((r, c) -> r.length < c.length ? r : c)
                .ifPresentOrElse(c -> System.out.println("\tДлина самого короткого массива: --- " + c.length),
                        () -> System.out.println("\tЖивотных нет"));
    }

    public static void task10() {
        System.out.println("TASK 10");

        List<Animal> animals = Util.getAnimals();
        System.out.println("\tСумма всех возрастов: --- "
                + animals.stream()
                .mapToLong(Animal::getAge)
                .sum());
    }

    public static void task11() {
        System.out.println("TASK 11");

        List<Animal> animals = Util.getAnimals();
        System.out.println("\tСредний возраст животных из Индонезии: --- "
                + animals.stream()
                .filter(a -> "Indonesian".equals(a.getOrigin()))
                .mapToLong(Animal::getAge)
                .average()
                .orElse(-1)
        );
    }

    public static void task12() {
        System.out.println("TASK 12");

        List<Person> persons = Util.getPersons();
        persons.stream()
                .filter(p -> "Male".equals(p.getGender()))
                .sorted((p1, p2) -> {
                    var g1 = p1.getRecruitmentGroup();
                    var g2 = p2.getRecruitmentGroup();
                    if (g1 != 1 && g1 != 2 && g1 != 3)
                        return g2;
                    else if (g2 != 1 && g2 != 2 && g2 != 3)
                        return g1;
                    else
                        return g1 - g2;
                })
                .limit(200)
                .forEach(p -> System.out.println("\t " + p));


    }

    public static void task13() {
        System.out.println("TASK 13");

        List<House> houses = Util.getHouses();
        houses.stream()
                .flatMap(h -> h.getPersonList().stream()
                        .map(p -> new SimpleEntry<>(h.getBuildingType(), p)))
                .sorted((e1, e2) -> {
                    if ("Hospital".equals(e1.getKey()))
                        return -1;
                    else if ("Hospital".equals(e2.getKey()))
                        return 1;

                    var age1 = -ChronoUnit.YEARS.between(LocalDate.now(), e1.getValue().getDateOfBirth());
                    if (age1 < 18 || age1 > 60)
                        return -1;

                    var age2 = -ChronoUnit.YEARS.between(LocalDate.now(), e2.getValue().getDateOfBirth());
                    if (age2 < 18 || age2 > 60)
                        return 1;

                    return 0;
                })
                .map(SimpleEntry::getValue)
                .limit(500)
                .forEach(p -> System.out.println("\t" + p));


    }

    public static void task14() {
        System.out.println("TASK 14");

        List<Car> cars = Util.getCars();
        cars.stream()
                .collect(Collectors.groupingBy(
                        c -> {
                            if ("White".equals(c.getColor()) || "Jaguar".equals(c.getCarMake()))
                                return 1;
                            else if (c.getMass() < 1500 && (
                                    "BWM".equals(c.getCarMake())
                                            || "Lexus".equals(c.getCarMake())
                                            || "Chrysler".equals(c.getCarMake())
                                            || "Toyota".equals(c.getCarMake())
                            ))
                                return 2;
                            else if ("Black".equals(c.getColor()) && c.getMass() > 4000
                                    || "GMC".equals(c.getCarMake())
                                    || "Dodge".equals(c.getCarMake()))
                                return 3;
                            else if (1982 > c.getReleaseYear()
                                    || "Civic".equals(c.getCarModel())
                                    || "Cherokee".equals(c.getCarModel()))
                                return 4;
                            else if (!"Yellow".equals(c.getColor())
                                    && !"Red".equals(c.getColor())
                                    && !"Green".equals(c.getColor())
                                    && !"Blue".equals(c.getColor())
                                    || 40000 < c.getPrice())
                                return 5;
                            else if (c.getVin().contains("59"))
                                return 6;

                            return -1;
                        },
                        Collectors.toList()))
                .entrySet()
                .stream()
                .filter(e -> !e.getKey().equals(-1))
                .map(e -> new SimpleEntry<>(e.getKey(),
                        new SimpleEntry<>(
                                e.getValue().stream()
                                        .mapToLong(Car::getMass)
                                        .sum(),
                                e.getValue().stream()
                                        .mapToLong(Car::getPrice)
                                        .sum()
                        )
                ))
                .map(e -> {
                    double totalTransportExpense = e.getValue().getKey() * 7.14 / 1000d;
                    return new SimpleEntry<>(e.getKey(),
                            new SimpleEntry<>(totalTransportExpense,
                                    e.getValue().getValue() - totalTransportExpense));
                })
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(), l -> {
                            l.forEach(e ->
                                    System.out.printf("""
                                                Страна: %s, Транспортные расходы: %.2f, Общая выручка по стране(общая стоимость минус транспортные расходы): %.2f
                                            """, e.getKey(), e.getValue().getKey(), e.getValue().getValue())
                            );
                            double totalIncome = l.stream()
                                    .mapToDouble(e -> e.getValue().getValue())
                                    .sum();
                            System.out.printf("\tОбщая выручка по всем странам: %.2f%n", totalIncome);
                            return l;
                        }
                ));
    }

    public static void task15() {
        System.out.println("TASK 15");

        List<Flower> flowers = Util.getFlowers();
        System.out.printf("\t%.2f%n",
                flowers.stream()
                        .sorted(Comparator.comparing(Flower::getOrigin).reversed()
                                .thenComparing(Flower::getPrice)
                                .thenComparing(Comparator.comparing(Flower::getWaterConsumptionPerDay).reversed()))
                        .filter(f -> f.getCommonName().compareTo("S") < 0
                                && f.getCommonName().compareTo("C") >= 0)
                        .filter(f -> f.isShadePreferred()
                                && !Collections.disjoint(
                                f.getFlowerVaseMaterial(),
                                List.of("Glass", "Aluminum", "Steel")))
                        .mapToDouble(f -> f.getPrice() + f.getWaterConsumptionPerDay() * 365 * 5 * 1.39)
                        .sum()
        );
    }

    public static void task16() {
        System.out.println("TASK 16");
        System.out.println("\tСтуденты младше 18 лет в алфавитном порядке: ");

        List<Student> students = Util.getStudents();
        students.stream()
                .filter(s -> s.getAge() < 18)
                .sorted(Comparator.comparing(Student::getSurname))
                .forEach(s -> System.out.println("\tВозраст: " + s.getAge() + "; Студент: " + s));
    }

    public static void task17() {
        System.out.println("TASK 17");

        List<Student> students = Util.getStudents();
        students.stream()
                .map(Student::getGroup)
                .distinct()
                .forEach(g -> System.out.println("\t" + g));
    }

    public static void task18() {
        System.out.println("TASK 18");

        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        students.stream()
                .collect(Collectors.groupingBy(
                                Student::getFaculty, Collectors.averagingInt(Student::getAge)
                        )
                )
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println("\tФакультет: " + e.getKey() + "; Средний возраст: " + e.getValue()));
    }

    public static void task19(String group) {
        System.out.println("TASK 19");

        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        List<Examination> passedExaminations = examinations.stream()
                .filter(ex -> ex.getExam3() > 4)
                .toList();

        students.stream()
                .filter(s -> s.getGroup().equals(group))
                .filter(s -> passedExaminations.stream()
                        .anyMatch(ex -> ex.getStudentId() == s.getId()))
                .forEach(s -> System.out.println("\t" + s));

    }

    public static void task20() {
        System.out.println("TASK 20");

        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        System.out.println("\t"
                + students.stream()
                .filter(s -> examinations.stream()
                        .anyMatch(ex -> ex.getStudentId() == s.getId()))
                .map(s ->
                        new SimpleEntry<>(s, examinations.stream()
                                .filter(ex -> ex.getStudentId() == s.getId())
                                .findAny()
                                .get()
                                .getExam1()
                        )
                )
                .collect(Collectors.groupingBy(
                                e -> e.getKey().getFaculty(),
                                Collectors.averagingInt(SimpleEntry::getValue)
                        )
                )
                .entrySet()
                .stream()
                .max(Map.Entry.<String, Double>comparingByValue().reversed())
                .orElse(null));
    }

    public static void task21() {
        System.out.println("TASK 21");
        List<Student> students = Util.getStudents();
        System.out.println("\t" + students.stream()
                .collect(Collectors.groupingBy(
                        Student::getGroup, Collectors.counting()
                )));
    }

    public static void task22() {
        System.out.println("TASK 22");

        List<Student> students = Util.getStudents();
        students.stream()
                .collect(
                        Collectors.groupingBy(
                                Student::getFaculty,
                                Collectors.mapping(Student::getAge, Collectors.minBy(Integer::compare))
                        )
                )
                .forEach((f, minAge) -> System.out.println("\tФакультет: " + f + "; Мин. возраст: " + minAge.orElse(-1)));
    }
}
