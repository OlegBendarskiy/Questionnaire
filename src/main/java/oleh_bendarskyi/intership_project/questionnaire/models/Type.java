package oleh_bendarskyi.intership_project.questionnaire.models;

public enum Type {

    SINGLE_LINE("single line text"),

    MULTILINE("multiline text"),

    RADIO_BUTTON("radio button"),

    CHECKBOX("checkbox"),

    COMBOBOX("combobox"),

    DATE("date");

    private String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

