package evaluator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Entity {
    private String text;
    private int startIndex;
    private int endIndex;

    public Entity(String text, int startIndex, int endIndex) {
        this.text = text;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public String getText() {
        return text;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return new EqualsBuilder()
            .append(startIndex, entity.startIndex)
            .append(endIndex, entity.endIndex)
            .append(text, entity.text)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(text)
            .append(startIndex)
            .append(endIndex)
            .toHashCode();
    }

    @Override
    public String toString() {
        return "Entity{" +
            "text='" + text + '\'' +
            ", startIndex=" + startIndex +
            ", endIndex=" + endIndex +
            '}';
    }
}
