package com.distributedlife.animalwiki.model;

public enum ConservationStatus {
    Extinct,
    ExtinctInWild,
    CriticallyEndangered,
    Endangered,
    Vulnerable,
    NearThreatened,
    LeastConcern,
    NotEvaluated,
    DataDeficient;


    public static ConservationStatus fromString(String status) {
        status = status.toLowerCase();

        if (status.equals("extinct")) {
            return ConservationStatus.Extinct;
        }
        if (status.equals("extinct in wild")) {
            return ConservationStatus.ExtinctInWild;
        }
        if (status.equals("critically endangered")) {
            return ConservationStatus.CriticallyEndangered;
        }
        if (status.equals("endangered")) {
            return ConservationStatus.Endangered;
        }
        if (status.equals("vulnerable")) {
            return ConservationStatus.Vulnerable;
        }
        if (status.equals("near threatened")) {
            return ConservationStatus.NearThreatened;
        }
        if (status.equals("least concern")) {
            return ConservationStatus.LeastConcern;
        }
        if (status.equals("not evaluated")) {
            return ConservationStatus.NotEvaluated;
        }
        if (status.equals("data deficient")) {
            return ConservationStatus.DataDeficient;
        }

        if (status.equals("ex")) {
            return ConservationStatus.Extinct;
        }
        if (status.equals("ew")) {
            return ConservationStatus.ExtinctInWild;
        }
        if (status.equals("cr")) {
            return ConservationStatus.CriticallyEndangered;
        }
        if (status.equals("en")) {
            return ConservationStatus.Endangered;
        }
        if (status.equals("vu")) {
            return ConservationStatus.Vulnerable;
        }
        if (status.equals("nt")) {
            return ConservationStatus.NearThreatened;
        }
        if (status.equals("lc")) {
            return ConservationStatus.LeastConcern;
        }
        if (status.equals("ne")) {
            return ConservationStatus.NotEvaluated;
        }
        if (status.equals("dd")) {
            return ConservationStatus.DataDeficient;
        }

        return ConservationStatus.NotEvaluated;
    }

    public String toString() {
        switch (this) {
            case Extinct:
                return "Extinct";
            case ExtinctInWild:
                return "Extinct In Wild";
            case CriticallyEndangered:
                return "Critically Endangered";
            case Endangered:
                return "Endangered";
            case NearThreatened:
                return "Near Threatened";
            case Vulnerable:
                return "Vulnerable";
            case LeastConcern:
                return "Least Concern";
            case DataDeficient:
                return "Data Deficient";
            case NotEvaluated:
                return "Not Evaluated";
            default:
                return "Not Evaluated";
        }
    }

    public String toAbbreviation() {
        switch (this) {
            case Extinct:
                return "EX";
            case ExtinctInWild:
                return "EW";
            case CriticallyEndangered:
                return "CR";
            case Endangered:
                return "EN";
            case Vulnerable:
                return "VU";
            case NearThreatened:
                return "NT";
            case LeastConcern:
                return "LC";
            case DataDeficient:
                return "DD";
            case NotEvaluated:
                return "NE";
            default:
                return "NE";
        }
    }
}
