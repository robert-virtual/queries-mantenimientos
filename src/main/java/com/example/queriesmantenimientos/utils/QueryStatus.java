package com.example.queriesmantenimientos.utils;

public enum QueryStatus {
    REQUESTED,
    AUTHORIZED;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
