package ru.simplegroup.wells.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "well")
public class Well {

    @Id
    @Min(1)
    @NotNull
    private Long id;

    @Size(min = 2, message = "parameterName must be min 2 symbol")
    @NotNull
    private String name;

    @Min(value = 0, message = "value must be over 0")
    @NotNull
    private Double x;

    @Min(value = 0, message = "value must be over 0")
    @NotNull
    private Double y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Well well = (Well) o;
        return id != null && Objects.equals(id, well.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
