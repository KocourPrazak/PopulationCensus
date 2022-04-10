package Census;

import java.util.Comparator;

public class PersonComparator implements Comparator<Person> {
    public int compare(Person a, Person b) {

        return a.getFamily().toUpperCase().compareTo(b.getFamily().toUpperCase());
    }
}
