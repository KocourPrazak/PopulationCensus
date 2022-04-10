import Census.Education;
import Census.Person;
import Census.Sex;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        long count = persons.stream()
                .filter(x -> x.getAge() < 18)
                .count();
        System.out.format("Количество несовершеннолетних: %,d%n", count);

        List<String> inductee = persons.stream()
                .filter(x -> x.getSex().equals(Sex.MALE))
                .filter(x -> x.getAge() >= 18 & x.getAge() < 27)
                .map(x -> x.getFamily())
                .collect(Collectors.toList());
        System.out.println("Фамилии призывников:");
        inductee.forEach(System.out::println);

        Predicate<Person> isNotRetirees = x -> x.getAge() < 65 & x.getSex().equals(Sex.MALE) ||
                x.getAge() < 60 & x.getSex().equals(Sex.FEMALE);
        Comparator<Person> personFamilyComparator
                = Comparator.comparing(Person::getFamily);

        List<Person> skilledPeoples = persons.stream()
                .filter(x -> x.getEducation().equals(Education.HIGHER))
                .filter(x -> x.getAge() >= 18)
                .filter(x -> isNotRetirees.test(x))
                .sorted(personFamilyComparator)
                .collect(Collectors.toList());
        System.out.println("Список трудоспособного населения с ВО:");

        /*try(FileWriter writer = new FileWriter("notes3.txt", false))
        {
            skilledPeoples.forEach(p -> {
                try {
                    writer.write(p.toString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }*/

        skilledPeoples.forEach(System.out::println);

    }
}
