package com.example.queriesmantenimientos.utils;

public enum QueryStatus {
    STATUS_REQUESTED,
    STATUS_AUTHORIZED;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
