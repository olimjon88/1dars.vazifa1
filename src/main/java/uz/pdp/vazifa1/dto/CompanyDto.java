package uz.pdp.vazifa1.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {

    @NotNull
    private String street;

    private String homeNumber;
    @NotNull
    private String directorName;

    @NotNull
    private String corpName;
}
