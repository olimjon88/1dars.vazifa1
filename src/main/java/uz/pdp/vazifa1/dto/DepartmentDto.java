package uz.pdp.vazifa1.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDto {
    @NotNull
    private String name;

    @NotNull
    private Integer companyId;
}
