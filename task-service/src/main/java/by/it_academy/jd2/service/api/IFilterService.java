package by.it_academy.jd2.service.api;

import java.util.List;

public interface IFilterService<T> {
    List<T> filterByName(String text);
}
