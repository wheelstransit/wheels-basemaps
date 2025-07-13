# Cycling Infrastructure Features Added

This document describes the comprehensive cycling features that have been added to the Protomaps Basemap tiles.

## Overview

All cycling infrastructure features have been added to the tiles layer without any styling changes. The styling can be implemented later in the client applications using the provided attributes.

## Road Layer (`roads`) Cycling Features

### New Road Types

1. **Dedicated Cycleways** (`highway=cycleway`)
   - Kind: `cycleway`
   - Minimum zoom: 12
   - These are separate cycling paths that appear earlier than regular paths

2. **Bicycle Roads** (`bicycle_road=yes` or `cyclestreet=yes`)
   - Kind: `bicycle_road`
   - Minimum zoom: 11
   - Streets where bicycles have priority over cars

### Cycling Infrastructure Attributes

All cycling attributes are available from zoom level 14 and higher:

#### Basic Bicycle Access
- `bicycle`: Bicycle access permission (yes/no/designated/dismount)

#### Cycleway Infrastructure
- `cycleway`: General cycleway type (lane/track/shared_lane/etc.)
- `cycleway_left`: Left-side cycleway type
- `cycleway_right`: Right-side cycleway type  
- `cycleway_both`: Both-side cycleway type

#### Physical Infrastructure Details
- `cycleway_width`: Width of cycleway
- `cycleway_left_width`: Width of left cycleway
- `cycleway_right_width`: Width of right cycleway
- `cycleway_buffer`: Buffer/separation distance
- `cycleway_left_buffer`: Left-side buffer distance
- `cycleway_right_buffer`: Right-side buffer distance

#### Road Designations
- `bicycle_road`: Boolean indicating if this is a bicycle road
- `cyclestreet`: Boolean indicating if this is a cycle street

#### Surface and Quality Information
- `surface`: Road surface type (important for cycling route planning)
- `smoothness`: Surface smoothness (excellent/good/intermediate/bad/etc.)
- `segregated`: Whether cycle path is physically separated

#### Traffic Management
- `cycleway_lane`: Lane marking type for cycle lanes
- `cycleway_oneway`: Oneway restrictions for cycleways
- `oneway_bicycle`: Bicycle-specific oneway restrictions

## Transit Layer (`transit`) Cycling Features

### Bicycle Crossings
Enhanced crossing points with bicycle-specific attributes:

#### Crossing Attributes
- `bicycle`: Bicycle access at crossings
- `crossing_bicycle`: Bicycle-specific crossing type (separate/shared)

### Bicycle Barriers
New barrier types for cycling infrastructure:

#### Barrier Types
- `kind`: "barrier"
- `barrier_type`: Type of barrier (cycle_barrier/bollard/chicane)
- `bicycle`: Bicycle access through barrier
- `access`: General access restrictions

## Points of Interest (`pois`) Layer

The POI layer already includes existing bicycle-related amenities:
- `bicycle_parking`: Bicycle parking facilities
- `bicycle_rental`: Bike sharing stations
- `bicycle_repair_station`: Bike repair facilities

## Usage Examples

### Styling Dedicated Cycleways
```javascript
{
  "id": "cycleways",
  "type": "line",
  "source": "basemap",
  "source-layer": "roads",
  "filter": ["==", "kind", "cycleway"],
  "paint": {
    "line-color": "#00ff00",
    "line-width": 2
  }
}
```

### Highlighting Bicycle Roads
```javascript
{
  "id": "bicycle-roads", 
  "type": "line",
  "source": "basemap",
  "source-layer": "roads",
  "filter": ["==", "kind", "bicycle_road"],
  "paint": {
    "line-color": "#0066ff",
    "line-width": 3
  }
}
```

### Showing Cycle Lanes
```javascript
{
  "id": "cycle-lanes",
  "type": "line", 
  "source": "basemap",
  "source-layer": "roads",
  "filter": ["has", "cycleway"],
  "paint": {
    "line-color": "#00aa00",
    "line-width": ["case", 
      ["==", ["get", "cycleway"], "track"], 3,
      ["==", ["get", "cycleway"], "lane"], 1,
      2
    ]
  }
}
```

### Displaying Bicycle Barriers
```javascript
{
  "id": "bike-barriers",
  "type": "circle",
  "source": "basemap", 
  "source-layer": "transit",
  "filter": ["==", "kind", "barrier"],
  "paint": {
    "circle-color": "#ff6600",
    "circle-radius": 4
  }
}
```

## Data Sources

These features extract and structure data from OpenStreetMap tags related to cycling infrastructure:

- `highway=cycleway`
- `bicycle_road=yes`
- `cyclestreet=yes`
- `cycleway=*`
- `cycleway:left=*`, `cycleway:right=*`, `cycleway:both=*`
- `bicycle=*`
- `surface=*`
- `smoothness=*` 
- `segregated=*`
- `oneway:bicycle=*`
- `barrier=cycle_barrier|bollard|chicane`
- Plus width, buffer, and lane marking attributes

## Testing

Comprehensive test coverage has been added for all new cycling features:
- Road type tests for cycleways and bicycle roads
- Attribute tests for cycling infrastructure
- Transit tests for barriers and bicycle crossings
- All 229 tests pass successfully

## Building

To build the tiles with cycling features:

```bash
cd tiles
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
mvn package
```

The resulting JAR will include all cycling infrastructure features in the generated tiles.
