--
--

INSERT INTO COMEDIAN (id, name)
VALUES (1, 'Julia'),
       (2, 'Zuzanna'),
       (3, 'Antoni'),
       (4, 'Jan'),
       (5, 'Jakub')
;

--
--

INSERT INTO JOKE (id, owner_id, title, question, answer)
VALUES (1, 1, 'Łyżka', 'Co ma wspólnego łyżka z jesienią?', 'Je się nią'),
       (2, 2, 'Mechanik', 'Ile zarabia mechanik?', null),
       (3, 3, 'Fryzjerka', 'Co fryzjerka ma w lodówce?', null),
       (4, 4, 'Magik', 'Gdy magik stracił pracę, jak się poczuł?', null),
       (5, 5, 'Wojna', 'Co na wojnie nigdy się nie psuje?', null)
;

--
--

-- INSERT INTO JOKE_OWNER (id, comedian_id, joke_id, since, until)
-- VALUES (OWNER_SEQ.nextval, 1, 1, '2020-12-31', null),
--        (OWNER_SEQ.nextval, 2, 2, '2020-12-31', null),
--        (OWNER_SEQ.nextval, 3, 3, '2020-12-31', null),
--        (OWNER_SEQ.nextval, 4, 4, '2020-12-31', null),
--        (OWNER_SEQ.nextval, 5, 5, '2020-12-31', null)
-- ;

--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
--
-- SELECT 1
-- FROM INFORMATION_SCHEMA.HELP;
--
--
