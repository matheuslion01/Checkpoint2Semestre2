CREATE TABLE brasileiroes (
    id BIGINT NOT NULL PRIMARY KEY,
    campeao VARCHAR(255),
    artilheiro VARCHAR(255),
    revelacao VARCHAR(255),
    goleada VARCHAR(255)
);

CREATE TABLE olimpiadas (
    id BIGINT NOT NULL PRIMARY KEY,
    ano VARCHAR(255),
    sede VARCHAR(255),
    maior_medalhista VARCHAR(255),
    maior_vencedor VARCHAR(255)
);