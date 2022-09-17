package ru.simplegroup.wells.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@IdClass(Parameter.class)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "parameter")
public class Parameter implements Serializable {

    @Id
    @Min(1)
    @Column(name = "well_id")
    @NotNull
    private Long wellId;

    @Id
    @Size(min = 2, message = "parameterName must be min 2 symbol")
    @NotNull
    @Column(name = "parameter_name")
    private String parameterName;

    @Id
    @Min(value = 0, message = "value must be over 0")
    @Column(name = "value")
    @NotNull
    private Double value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Parameter parameter = (Parameter) o;
        return wellId != null && Objects.equals(wellId, parameter.wellId)
                && parameterName != null && Objects.equals(parameterName, parameter.parameterName)
                && value != null && Objects.equals(value, parameter.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wellId, parameterName, value);
    }
}
