import lombok.Getter;

@Getter
public enum CommonAttributes {

    CLASS("class"),
    ID("id"),
    ;
    public final String value;

    CommonAttributes(String value) {
        this.value = value;
    }
}
