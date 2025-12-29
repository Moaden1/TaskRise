public class User {
    //fields
    // metadata:
    private final String name;
    private final Integer age;
    private final String trUse; // taskrise use: Professional, Personal, Academic, all/or combination of each
    //Private timezone (optional?)

    // const -- determine user types and if some users may need more fields
    public User(String name, int age, String trUse) {
        this.name = name;
        this.age = age;
        this.trUse = trUse;
       // this.timezone = timezone;
    }

    public String getName() {
        return this.name;
    }

    public String getTrUse() {
        return this.trUse;
    }

    public int getAge() {
        return this.age;
    }

    //methods
    @Override
    public String toString() {
        return "User:" + getName() + "\nAge:" +
                getAge() + "\n Use of TaskRise: " + getTrUse();
    }
}