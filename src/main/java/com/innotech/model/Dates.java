package com.innotech.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dates{
    private List<Date> dates;

    public Boolean equals(Dates dates) {
        Iterator<Date> it1 = this.dates.iterator();
        Iterator<Date> it2 = dates.getDates().iterator();

        while (it1.hasNext() && it2.hasNext()) {
            Date date1 = it1.next();
            Date date2 = it2.next();
            if (!date1.equals(date2))
                return false;
        }
        
        return true;
    }
}
