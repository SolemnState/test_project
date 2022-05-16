package api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Date {
    private String date;

    public Boolean equals(Date date) {
        return this.date.equals(date.getDate());
    }
}
