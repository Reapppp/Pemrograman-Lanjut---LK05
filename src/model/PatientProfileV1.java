package model;

import interfaces.Confidential;
import interfaces.MedicalRecord;
import interfaces.Versioned;

public class PatientProfileV1 implements MedicalRecord, Versioned, Confidential {
    private String patientId;
    private String name;
    private String nik;
    private String diagnosa;
    private int securityLevel;

    public PatientProfileV1(String patientId, String name, String nik, String diagnosa, int securityLevel) {
        this.patientId = patientId;
        this.name = name;
        this.nik = nik;
        this.diagnosa = diagnosa;
        this.securityLevel = securityLevel;
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public String getPatientName() {
        return name;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public int getSecurityLevel() {
        return securityLevel;
    }

    @Override
    public void maskSensitiveData() {
        nik = "******";
        diagnosa = "******";
    }

    @Override
    public String toString() {
        return "PatientProfileV1" +
                "\n  Patient ID : " + patientId +
                "\n  Name       : " + name +
                "\n  NIK        : " + nik +
                "\n  Diagnosa   : " + diagnosa +
                "\n  Version    : " + getVersion() +
                "\n  Sec Level  : " + securityLevel;
    }
}
