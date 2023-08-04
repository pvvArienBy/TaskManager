package by.it_academy.jd2.dao.repositories;

import java.util.List;

public interface IFilterDao<T>  {

    List<T> filterByName(String text);

}
