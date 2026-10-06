CREATE TABLE brasileiroes (
    id BIGINT NOT NULL,
    campeao VARCHAR(255),
    artilheiro VARCHAR(255),
    revelacao VARCHAR(255),
    goleada VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE olimpiadas (
    id BIGINT NOT NULL,
    ano VARCHAR(255),
    sede VARCHAR(255),
    maior_medalhista VARCHAR(255),
    maior_vencedor VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;
