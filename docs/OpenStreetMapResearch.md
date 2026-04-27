# OpenStreetMap

-https://leafletjs.com/examples/quick-start/
-https://www.digitalocean.com/community/tutorials/angular-angular-and-leaflet
-https://github.com/lilcodelab/angular-osm-leaflet

## Why OpenStreetMap ?

- Highly customizable compared to Google Maps
- Open-source and free (ODbL license)
- Community-driven with global coverage


# Leaflet Integration

- Leaflet is the leading open-source library for web and mobile-friendly interactive maps
- Used to render OSM(OpenStreetMap) tiles and visualize event locations on an interactive map

## Technical Approach

### Backend Implementation

- Data Model: EventMapDto (record) used to transfer only necessary geospatial data (id, title, latitude, longitude)
- Service Layer: EventService exposes getAllEventsForMap() to retrieve the event list
- Mapper: EventMapper transforms the Event entity to EventMapDto to keep API responses lightweight

### Frontend Implementation

- Component: HomePage component acts as the map container
- Map initialization occurs within ngAfterViewInit (ensures DOM is ready)
- Leaflet core (import * as L from 'leaflet') is used directly for rendering
- Data Flow: Frontend consumes EventMapDto stream to dynamically populate markers

## Setup

- npm install leaflet
- npm install @types/leaflet --save-dev

## API usage

- Leaflet does not provide native geocoding (address to coordinates conversion)
- Current implementation uses existing latitude/longitude fields persisted in the database(Location entity)
- So, EventMapDto is used for data transfer and EventService for Data Retrieval and HomePage for map rendering and
user interface

## Error handling

- Make sure that you have the <div> for the map has a non hero height or else the rendering will appear as invisible
