package com.protomaps.basemap.layers;

import static com.onthegomap.planetiler.TestUtils.*;

import com.onthegomap.planetiler.reader.SimpleFeature;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class TransitTest extends LayerTest {
  
  @Test
  void crossing() {
    assertFeatures(15,
      List.of(Map.of("kind", "crossing", "min_zoom", 16)),
      process(SimpleFeature.create(
        newPoint(1, 1),
        new HashMap<>(Map.of("highway", "crossing")),
        "osm",
        null,
        0
      )));
  }

  @Test
  void crossingWithName() {
    assertFeatures(15,
      List.of(Map.of("kind", "crossing", "min_zoom", 16, "name", "Main Street Crossing")),
      process(SimpleFeature.create(
        newPoint(1, 1),
        new HashMap<>(Map.of("highway", "crossing", "name", "Main Street Crossing")),
        "osm",
        null,
        0
      )));
  }

  @Test
  void crossingWithDetails() {
    assertFeatures(15,
      List.of(Map.of(
        "kind", "crossing", 
        "min_zoom", 16,
        "crossing", "traffic_signals",
        "crossing_signals", "yes",
        "tactile_paving", "yes"
      )),
      process(SimpleFeature.create(
        newPoint(1, 1),
        new HashMap<>(Map.of(
          "highway", "crossing",
          "crossing", "traffic_signals",
          "crossing:signals", "yes",
          "tactile_paving", "yes"
        )),
        "osm",
        null,
        0
      )));
  }

  @Test
  void cycleBarrier() {
    assertFeatures(16,
      List.of(Map.of(
        "kind", "barrier", 
        "barrier_type", "cycle_barrier",
        "min_zoom", 16,
        "bicycle", "dismount"
      )),
      process(SimpleFeature.create(
        newPoint(1, 1),
        new HashMap<>(Map.of(
          "barrier", "cycle_barrier",
          "bicycle", "dismount"
        )),
        "osm",
        null,
        0
      )));
  }

  @Test
  void bollardBarrier() {
    assertFeatures(16,
      List.of(Map.of(
        "kind", "barrier", 
        "barrier_type", "bollard",
        "min_zoom", 16,
        "bicycle", "yes",
        "access", "no"
      )),
      process(SimpleFeature.create(
        newPoint(1, 1),
        new HashMap<>(Map.of(
          "barrier", "bollard",
          "bicycle", "yes",
          "access", "no"
        )),
        "osm",
        null,
        0
      )));
  }

  @Test
  void crossingWithBicycle() {
    assertFeatures(16,
      List.of(Map.of(
        "kind", "crossing", 
        "min_zoom", 16,
        "crossing", "traffic_signals",
        "bicycle", "yes",
        "crossing_bicycle", "separate"
      )),
      process(SimpleFeature.create(
        newPoint(1, 1),
        new HashMap<>(Map.of(
          "highway", "crossing",
          "crossing", "traffic_signals",
          "bicycle", "yes",
          "crossing:bicycle", "separate"
        )),
        "osm",
        null,
        0
      )));
  }

  @Test
  void notACrossing() {
    // Test that non-crossing highway features are ignored
    assertFeatures(15,
      List.of(),
      process(SimpleFeature.create(
        newPoint(1, 1),
        new HashMap<>(Map.of("highway", "traffic_signals")),
        "osm",
        null,
        0
      )));
  }
}
