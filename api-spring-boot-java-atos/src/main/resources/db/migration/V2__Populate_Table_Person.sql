INSERT INTO `person` (id, first_name, last_name, address, gender)
SELECT * FROM (
    SELECT 1 AS id, 'Avohai' AS fn, 'Teixeira' AS ln, 'Pontezinha' AS ad, 'Male' AS ge UNION ALL
    SELECT 4, 'Bob', 'Marley', 'Jamaica', 'Male' UNION ALL
    SELECT 5, 'Michael', 'Jackson', 'USA', 'Male' UNION ALL
    SELECT 6, 'Ton', 'Ranks', 'Blooclin', 'Male' UNION ALL
    SELECT 7, 'Roberto', 'Carlos', 'Santos', 'Male'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM `person` LIMIT 1);




