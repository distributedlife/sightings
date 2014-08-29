package com.distributedlife.animalwiki.filters;

import com.distributedlife.animalwiki.db.Sightings;
import com.distributedlife.animalwiki.model.Animal;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotSeenFilterTest {

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
        when(sightings.hasSighting(animal)).thenReturn(true);

        assertEquals(new NotSeenFilter(true, sightings).exclude(animal), false);
    }

    @Test
    public void whenEnabledShouldNotExcludeIfAnimalHasNoSightings() {
        when(sightings.hasSighting(animal)).thenReturn(false);

        assertEquals(new NotSeenFilter(true, sightings).exclude(animal), false);
    }

    @Test
    public void whenDisabledShouldNotExcludeIfAnimalHasSighting() {
        when(sightings.hasSighting(animal)).thenReturn(true);

        assertEquals(new NotSeenFilter(false, sightings).exclude(animal), false);
    }

    @Test
    public void whenDisabledShouldExcludeIfAnimalHasNoSighting() {
        when(sightings.hasSighting(animal)).thenReturn(false);

        assertEquals(new NotSeenFilter(false, sightings).exclude(animal), true);
    }
}
