package kg.nurtelecom.opinion.payload.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public record UserSignUpResponse(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        String nickname,
        String email,
        @JsonProperty("birth_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(type = "string", format = "date", example = "2000-01-21")
        Date birthDate
) {
}
