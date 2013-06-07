package fr.legunda.tournoyons.server.core;

import java.util.HashMap;
import java.util.Map;

public class MapParam extends HashMap<MapParam.Parameter, String> implements Map<MapParam.Parameter, String> {

    public enum Parameter {
        GAIN1("Gain1"), GAIN2("Gain2"),     // Représente le gain des joueurs
        GAME("Game"),                       // Identifiant de la partie en cours
        LEVEL("Level"),                     // Niveau de la règle
        MOVE1("Move1"), MOVE2("Move2"),     // Dernier coup joué par l'adversaire
        MOVEID("MoveId"),                   // Identifiant du coup demandé
        OPPONENT("Opponent"),               // Identifiant de l'adversaire
        REFEREE("Referee"),                 // Spécifie l'URL à émettre avec le coup joué
        SET("Set"),                         // A quel jeu joue-t-on?
        STATUS("Status"),                   // Etat de la partie
        TIMEOUT("TimeOut"),                 // Durée max accordée au moteur
        TRAY("Tray"),                       // Etat du plateau
        TURN("Turn"),                       // Nombre de coups
        VALUE("Value")                      // Non utilisé
        ;


        private final String name;

        Parameter(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Parameter getParamByName(String name){
            for (Parameter parameter : values()) {
                if(parameter.getName().equals(name)) {
                    return parameter;
                }
            }

            throw new IllegalArgumentException(String.format("Unknown name [%s]", name));
        }
    }

}
