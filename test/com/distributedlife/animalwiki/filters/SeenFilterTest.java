package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.model.Animal;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SeenFilterTest {

    private Animal animal;
    private Sightings sightings;

    @Before
    public void setup() {
        animal = mock(Animal.class);
        when(animal.getCommonName()).thenReturn("animal");

        sightings = mock(Sightings.class);
    }

    @Test
    public void whenEnabledShouldNotExcludeIfAnimalHasSighting() {
        when(sightings.hasSighting("animal")).thenReturn(true);

        assertEquals(new SeenFilter(true, sightings).exclude(animal), false);
    }

    @Test
    public void whenEnabledShouldNotExcludeIfAnimalHasNoSightings() {
        when(sightings.hasSighting("animal")).thenReturn(false);

        assertEquals(new SeenFilter(true, sightings).exclude(animal), false);
    }

    @Test
    public void whenDisabledShouldExcludeIfAnimalHasSighting() {
        when(sightings.hasSighting("animal")).thenReturn(true);

        assertEquals(new SeenFilter(false, sightings).exclude(animal), true);
    }

    @Test
    public void whenDisabledShouldNotExcludeIfAnimalHasNoSighting() {
        when(sightings.hasSighting("animal")).thenReturn(false);

        assertEquals(new SeenFilter(false, sightings).exclude(animal), false);
    }
}
