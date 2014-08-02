package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.model.Animal;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnimalFiltererTest {

    private Animal animal;
    private List<Animal> animals;
    private Filter filter1;
    private Filter filter2;
    private List<Filter> filterList;

    @Before
    public void setup() {
        animal = mock(Animal.class);

        animals = new ArrayList<Animal>();
        animals.add(animal);

        filter1 = mock(Filter.class);
        filter2 = mock(Filter.class);

        filterList = new ArrayList<Filter>();
        filterList.add(filter1);
        filterList.add(filter2);
    }

    @Test
    public void shouldIncludeOnlyAnimalsThatPassAllFilters() {
        when(filter1.exclude(animal)).thenReturn(false);
        when(filter2.exclude(animal)).thenReturn(false);

//        List<Animal> included = AnimalFilterer.apply(filterList, animals);
//        assertThat(included.get(0), is(animal));
    }

    @Test
    public void shouldRejectAnimalsThatFailAnyFilter() {
        when(filter1.exclude(animal)).thenReturn(false);
        when(filter2.exclude(animal)).thenReturn(true);

//        List<Animal> included = AnimalFilterer.apply(filterList, animals);
//        assertThat(included.isEmpty(), is(true));
    }
}
