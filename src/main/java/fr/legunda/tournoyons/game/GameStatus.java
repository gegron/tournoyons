package fr.legunda.tournoyons.game;

public enum GameStatus {
    PARTIE_EN_COURS(0),
    PARTIE_GAGNEE_EN_TANT_QUE_PREMIER_JOUEUR(1),
    PARTIE_GAGNEE_EN_TANT_QUE_SECOND_JOUEUR(2),
    PARTIE_PERDUE_EN_TANT_QUE_PREMIER_JOUEUR(3),
    PARTIE_PERDUE_EN_TANT_QUE_SECOND_JOUEUR(4),
    MATCH_NUL(5),
    ERREUR(9)
    ;

    private final int status;

    GameStatus(int status) {
        this.status = status;
    }
}
