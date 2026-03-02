package com.example.backend.service;

import com.example.backend.model.Reservation;
import com.example.backend.model.ReservationStatus;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ReservationClassifier {

    public enum Type {
        HARD_OVERLAP,
        SOFT_OVERLAP,
        DATA_CONFLICT,
        IGNORED,
        NO_OVERLAP
    }

    public static Type classify(Reservation res, LocalDate reqFrom, LocalDate reqTo) {
        ReservationStatus status = ReservationStatus.fromString(res.status());

        if (status == ReservationStatus.CANCELLED) {
            return Type.IGNORED;
        }

        if (res.from() == null || res.from().isBlank() || res.to() == null || res.to().isBlank()) {
            return Type.DATA_CONFLICT;
        }

        LocalDate rFrom;
        LocalDate rTo;
        try {
            rFrom = LocalDate.parse(res.from());
            rTo = LocalDate.parse(res.to());
        } catch (DateTimeParseException e) {
            return Type.DATA_CONFLICT;
        }

        if (!rTo.isAfter(rFrom)) {
            return Type.DATA_CONFLICT;
        }

        boolean overlaps = rFrom.isBefore(reqTo) && rTo.isAfter(reqFrom);

        if (!overlaps) {
            return Type.NO_OVERLAP;
        }

        if (status == ReservationStatus.CONFIRMED || status == ReservationStatus.PICKED_UP) {
            return Type.HARD_OVERLAP;
        }

        return Type.SOFT_OVERLAP;
    }
}
