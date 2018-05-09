INSERT INTO SportEvent (DTYPE, id, beginingDate, designation, nParticipants, nRefereesPerMatch, periodicity) VALUES ('Cup', 1, '2017-05-12', 'taca', 2, 4, 1);
INSERT INTO Participant (sequencialId, name) VALUES (1, 'manelinho');
INSERT INTO Participant (sequencialId, name) VALUES (2, 'jose');
INSERT INTO SportEvent_Participant (SportEvent_id, participants_sequencialId) VALUES (1, 1);
INSERT INTO SportEvent_Participant (SportEvent_id, participants_sequencialId) VALUES (1, 2);
INSERT INTO MATCHTABLE (number, matchDate, phase, participant1_sequencialId, participant2_sequencialId, SPORTEVENT_ID) VALUES (1, '2017-05-12', 1, 1, 2, 1);
INSERT INTO Referee (id, licenseNumber, name) VALUES (1, 123, 'goncalo');

INSERT INTO SportEvent (DTYPE, id, beginingDate, designation, nParticipants, nRefereesPerMatch, periodicity) VALUES ('Championship', 2, '2017-09-02', 'campeonato', 4, 2, 1);
INSERT INTO Participant (sequencialId, name) VALUES (3, 'catarina');
INSERT INTO Participant (sequencialId, name) VALUES (4, 'lilas');
INSERT INTO SportEvent_Participant (SportEvent_id, participants_sequencialId) VALUES (2, 3);
INSERT INTO SportEvent_Participant (SportEvent_id, participants_sequencialId) VALUES (2, 4);
INSERT INTO MATCHTABLE (number, matchDate, phase, participant1_sequencialId, participant2_sequencialId, SPORTEVENT_ID) VALUES (2, '2017-09-03', 1, 3, 4, 2);
INSERT INTO MATCHTABLE (number, matchDate, phase, participant1_sequencialId, participant2_sequencialId, SPORTEVENT_ID) VALUES (3, '2017-09-03', 1, 4, 3, 2);
INSERT INTO Referee (id, licenseNumber, name) VALUES (2, 234, 'godinho');
INSERT INTO MATCHTABLE_Referee (matches_number, referees_id) VALUES (2,2);
