package com.protomaps.basemap.layers;

import com.onthegomap.planetiler.FeatureCollector;
import com.onthegomap.planetiler.ForwardingProfile;
import com.onthegomap.planetiler.VectorTile;
import com.onthegomap.planetiler.reader.SourceFeature;
import com.protomaps.basemap.feature.FeatureId;
import com.protomaps.basemap.names.OsmNames;
import java.util.List;

public class Transit implements ForwardingProfile.LayerPostProcessor {

  public static final String LAYER_NAME = "transit";

  @Override
  public String name() {
    return LAYER_NAME;
  }

  public void processOsm(SourceFeature sf, FeatureCollector features) {
    // Handle highway crossing points
    if (sf.isPoint() && sf.hasTag("highway", "crossing")) {
      String kind = "crossing";
      int minZoom = 16; // High zoom level for pedestrian navigation
      
      var feature = features.point(LAYER_NAME)
        .setId(FeatureId.create(sf))
        .setAttr("kind", kind)
        .setAttr("min_zoom", minZoom)
        .setMinZoom(minZoom);
      
      // Add names if present
      OsmNames.setOsmNames(feature, sf, 0);
      
      // Add crossing-specific attributes
      if (sf.hasTag("crossing")) {
        feature.setAttr("crossing", sf.getString("crossing"));
      }
      if (sf.hasTag("crossing:signals")) {
        feature.setAttr("crossing_signals", sf.getString("crossing:signals"));
      }
      if (sf.hasTag("tactile_paving")) {
        feature.setAttr("tactile_paving", sf.getString("tactile_paving"));
      }
      
      // Add bicycle-specific crossing attributes
      if (sf.hasTag("bicycle")) {
        feature.setAttr("bicycle", sf.getString("bicycle"));
      }
      if (sf.hasTag("crossing:bicycle")) {
        feature.setAttr("crossing_bicycle", sf.getString("crossing:bicycle"));
      }
    }
    
    // Handle bicycle barriers and bollards
    if (sf.isPoint() && sf.hasTag("barrier", "cycle_barrier", "bollard", "chicane")) {
      String kind = "barrier";
      int minZoom = 16;
      
      var feature = features.point(LAYER_NAME)
        .setId(FeatureId.create(sf))
        .setAttr("kind", kind)
        .setAttr("barrier_type", sf.getString("barrier"))
        .setAttr("min_zoom", minZoom)
        .setMinZoom(minZoom);
      
      // Add bicycle access information for barriers
      if (sf.hasTag("bicycle")) {
        feature.setAttr("bicycle", sf.getString("bicycle"));
      }
      if (sf.hasTag("access")) {
        feature.setAttr("access", sf.getString("access"));
      }
    }
  }

  @Override
  public List<VectorTile.Feature> postProcess(int zoom, List<VectorTile.Feature> items) {
    return items;
  }
}
