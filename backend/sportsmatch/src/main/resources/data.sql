-- Cleanup
drop function if exists get_places_by_distance_and_sports_names;
drop type if exists event_rt;

-- Create record type           olyannak kell lennie mint az entity, amit vissza akarunk visszaadni, sorrend matcheljen a queryvel
CREATE TYPE event_rt AS (
    id BIGINT,
    date_start TIMESTAMP,
    date_end TIMESTAMP,
    min_elo INT,
    max_elo INT,
    title VARCHAR(255),
    sport_id BIGINT,
    place_id BIGINT
    );

-- Create function, that returns the places by distance
CREATE FUNCTION get_places_by_distance_and_sports_names(p_longitude FLOAT,
                                                        p_latitude FLOAT,
                                                        sport_names VARCHAR [])
    RETURNS SETOF event_rt AS
'
BEGIN
RETURN QUERY (
    SELECT
    e.id,
    e.date_start,
    e.date_end,
    e.min_elo,
    e.max_elo,
    e.title,
    e.sport_id,
    e.place_id
FROM
    events e
JOIN
    sports s ON e.sport_id = s.id
JOIN
    places p ON e.place_id = p.id
WHERE
	(:sport_name IS NULL OR LOWER(s.name) = LOWER(:sport_name))
ORDER BY (
      6371 * acos(  -- Haversine distance calculation
        cos(radians(p.latitude)) * cos(radians(:latitude)) *
        cos(radians(:longitude) - radians(p.longitude)) +
        sin(radians(p.latitude)) * sin(radians(:latitude))
      )
    ) ASC;
);
END;
' language plpgsql;