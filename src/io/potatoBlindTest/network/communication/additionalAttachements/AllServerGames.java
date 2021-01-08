package io.potatoBlindTest.network.communication.additionalAttachements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllServerGames implements Serializable {

    private List<SpecificServerGame> specificServerGames;

    public AllServerGames(ArrayList<SpecificServerGame> specificServerGames) {
        this.specificServerGames = specificServerGames;
    }

    public List<SpecificServerGame> getSpecificServerGames() {
        return specificServerGames;
    }
}
