package com.arimayi.facturation_module.Entities.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Tva {
    TVA_0(0), TVA_5_5(5.5), TVA_10(10), TVA_20(20);

    public final double valeur;

    Tva(double valeur) {
        this.valeur = valeur;
    }

    @JsonValue
    public double getValeur() {
        return valeur;
    }

    @JsonCreator
    public static Tva fromValeur(double valeur) {
        for (Tva tva : Tva.values()) {
            if (tva.valeur == valeur) {
                return tva;
            }
        }
        throw new IllegalArgumentException("Valeur TVA invalide: " + valeur);
    }
}