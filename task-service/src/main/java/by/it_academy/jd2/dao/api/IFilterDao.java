package by.it_academy.jd2.dao.api;

import java.util.List;

public interface IFilterDao<T> {

    List<T> filterByName(String text);

}
