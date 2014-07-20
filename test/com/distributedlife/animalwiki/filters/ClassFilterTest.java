package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.model.Animal;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClassFilterTest {
    private Animal animal;

    @Before
    public void setup() {
        animal = mock(Animal.class);
        when(animal.getCommonName()).thenReturn("animal");
        when(animal.getKlass()).thenReturn("aves");
    }

    @Test
    public void whenEnabledShouldNotExcludeAnimalsWhoseClassDoesNotMatch() {
        assertThat(new ClassFilter("aves", true).exclude(animal), is(false));
    }

    @Test
    public void whenEnabledShouldNotExcludeAnimalsWhoseClassMatches() {
        assertThat(new ClassFilter("mammalia", true).exclude(animal), is(false));
    }

    @Test
    public void whenDisabledShouldExcludeAnimalsWhoseClassDoesNotMatch() {
        assertThat(new ClassFilter("aves", false).exclude(animal), is(true));
    }

    @Test
    public void whenDisabledShouldNotExcludeAnimalsWhoseClassMatches() {
        assertThat(new ClassFilter("mammalia", false).exclude(animal), is(false));
    }
}
